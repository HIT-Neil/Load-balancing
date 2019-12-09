import java.util.Random;

public class DGA {
    Problem prob=new Problem();
    int K =prob.num; // 微云数量
    Cloudlet[] clouds=new Cloudlet[K];
    int[] cloudId=new int[K];
    Island[] islands=new Island[K];
    int numGenes; // 每个岛屿上的基因数量

    void DGA(int num){
        numGenes=num;
    }

    // 分布式遗传算法
    void distributedAlgorithm(){
        prob.initFlow();
        prob.initCloudlet(clouds);
        for(int i = 0; i< K; i++){
            cloudId[i]=i;
        }
        sortByResTime();
        for(int p = 1; p< K-1 ; p++){
            for(int id=0;id<K;id++){
                createIsland(id,p);
            }
        }

    }

    // 创建岛屿
    void createIsland(int id,int ref){
        islands[id].numGenes=numGenes;
        islands[id].numUnder=ref+1;
        islands[id].numOver=K-ref-1;

        islands[id].initGenePool();

        // 随机生成基因
        Random r = new Random();
        double tmp;
        // 对于基因g[i][j]，p是划分的依据节点，有
        // i对应于cloudId[p+1+i](过载)
        // j对应于cloudId[j](不过载)
        for(int num=0;num<islands[id].numGenes;num++){
            for(int i=0;i<islands[id].numOver;i++){
                double arrRate=clouds[cloudId[ref+1+i]].arrivalRate;
                for(int j=0;j<islands[id].numUnder;j++){
                    islands[id].genePool[num][i][j]=r.nextDouble()*arrRate;
                }
            }
            adjGene(islands[id],ref);
        }

        



    }

    // 根据平均响应时间给微云排序
    void sortByResTime(){
        for(int i = 0; i< K; i++){
            prob.calCloudlet(clouds[i],i);
        }

        for(int i = 0; i< K -1; i++){
            for(int j = i+1; j< K; j++){
                if(clouds[cloudId[j]].taskResTime<clouds[cloudId[i]].taskResTime){
                    int tmp=cloudId[i];
                    cloudId[i]=cloudId[j];
                    cloudId[i]=tmp;
                }
            }
        }
    }

    /// 检查是否满足约束条件，第i行的和小于对应微云的任务到达率
    // 第j列的和小于对应微云的服务器数与效率的乘积
    // 如果不满足需要随机减小值，使满足约束条件
    void adjGene(Island island,int ref){
        for(int num=0;num<island.numGenes;num++){
            for(int i=0;i<island.numOver;i++){
                double sumRow=0;
                for(int j=0;j<island.numUnder;j++){
                    sumRow+=island.genePool[num][i][j];
                }
                if(sumRow>clouds[cloudId[ref+1+i]].arrivalRate){
                    double des=clouds[cloudId[ref+1+i]].arrivalRate/sumRow;
                    for(int j=0;j<island.numUnder;j++){
                        Random r=new Random();
                        island.genePool[num][i][j]*=(r.nextDouble()*(des));
                    }
                }

            }
            for (int j = 0; j < island.numUnder; j++) {
                double sumColumn=0;
                for (int i = 0; i < island.numOver; i++) {
                    sumColumn += island.genePool[num][i][j];
                }
                double sp=clouds[cloudId[j]].numServers*clouds[cloudId[j]].serviceRate;
                if(sumColumn>sp){
                    double des=sp/sumColumn;
                    for(int i=0;i<island.numOver;i++){
                        Random r=new Random();
                        island.genePool[num][i][j]*=(r.nextDouble()*des);
                    }
                }
            }
        }
    }

}
