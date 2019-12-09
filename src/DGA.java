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
    void createIsland(int id,int reference){
        islands[id].numGenes=numGenes;
        islands[id].numUnder=reference+1;
        islands[id].numOver=K-reference-1;

        islands[id].initGenePool();

        // 对于基因g[i][j]，p是划分的依据节点，有
        // i对应于cloudId[p+1+i](过载)
        // j对应于cloudId[j](不过载)
        for(int num=0;num<islands[id].numGenes;num++){
            for(int i=0;i<islands[id].numOver;i++){
                for(int j=0;j<islands[id].numUnder;j++){
                    islands[id].genePool[num][i][j]=0;
                }
            }
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


}
