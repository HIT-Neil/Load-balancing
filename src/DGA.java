import java.util.Random;

public class DGA {
    int numSurvive; // 保留到下一代的数量
    int numGenes=100; // 每个岛屿上的基因数量
    double mutRate; // 变异率
    double migRate; // 迁移率
    double migPercentage; // 迁移百分比
    int numGeneration; // 迭代次数

    // 构造函数
    DGA(int s ,int p,double rMut,double rMig,double pMig,int n){
        numSurvive=s;
        numGenes=p;
        mutRate=rMut;
        migRate=rMig;
        migPercentage=pMig;
        numGeneration=n;
    }

    Problem prob=new Problem();
    Cloudlet[] clouds=new Cloudlet[Problem.num];
    int[] cloudId=new int[Problem.num]; // 微云编号
    Island[] islands=new Island[Problem.num]; // 岛屿集合
    int[] geneId; // 基因编号
    double[] fitness; // 适应度值
    ResultTable[][] result=new ResultTable[Problem.num][Problem.num]; // 保存结果的二维数组

    // 分布式遗传算法
    void distributedAlgorithm(){
        prob.initFlow();
        prob.initCloudlet(clouds);
        for(int i = 0; i< Problem.num; i++){
            cloudId[i]=i;
        }
        geneId=new int[numGenes];
        fitness=new double[numGenes];
        sortByResTime();
        for(int p = 1; p< Problem.num-1 ; p++){
            for(int id=0;id<Problem.num;id++){
                createIsland(id,p);
            }
            for(int id=0;id<Problem.num;id++){
                evolve(id,p);
            }
        }
    }

    // 创建岛屿
    void createIsland(int id,int ref){
        Island land=new Island();
        land.numGenes=numGenes;
        land.numUnder=ref+1;
        land.numOver=Problem.num-ref-1;
        land.genePool=new double[numGenes][Problem.num-ref-1][ref+1];
        land.available=new boolean[numGenes];
        land.geneFromMig=new double[numGenes][Problem.num-ref-1][ref+1];
        land.initGenePool();
        islands[id]=land;
    }

    void evolve(int id,int ref){
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

        for(int gen=1;gen<numGeneration;gen++){
            System.out.println("generation:"+gen);
            // 根据适应度函数排序
            sortByFitness(id,ref);
            // 保存最佳个体基因的适应度值
            ResultTable best=new ResultTable();
            best.fitness=fitness[geneId[0]];
            System.out.println("best_fitness:"+best.fitness);
            geneToFlow(geneId[0],id,ref);
            // 保存最佳个体基因的方案
            for(int i=0;i<Problem.num;i++){
                for(int j=0;j<Problem.num;j++){
                    best.result[i][j]=prob.flow[i][j];
                    System.out.print(prob.flow[i][j]+" ");
                }
            }
            result[ref][id]=best;

            // 轮盘赌选择固定数量的基因进行迁移到其他节点
            int cur=0;
            Random random=new Random();
            if(random.nextDouble()<migRate){
                // 迁移数量
                double numMig=migPercentage*numGenes;
                for(int cnt=0;cnt<numMig;){
                    cur+=random.nextInt(numGenes);
                    cur%=numGenes;
                    if(islands[id].available[cur]){
                        for(int ii=0;ii<islands[id].numOver;ii++){
                            for(int jj=0;jj<islands[id].numUnder;jj++){
                                islands[(cur+id+1)%Problem.num].geneFromMig[islands[(cur+id+1)%Problem.num].numFromMig][ii][jj]
                                        =islands[id].genePool[cur][ii][jj];
                            }
                        }
                        islands[id].available[cur]=false;
                        islands[(cur+id+1)%Problem.num].numFromMig++;
                        cnt++;
                    }
                }
            }

            double[][][] tmpGenePool=new double[islands[id].numGenes][islands[id].numOver][islands[id].numUnder];
            int numTmpPool=0;
            // 获取来自迁移的基因
            if(islands[id].numFromMig>0){
                numTmpPool=islands[id].numFromMig;
                for(int c=0;c<islands[id].numFromMig;c++){
                    for(int i=0;i<islands[id].numFromMig;i++){
                        for(int j=0;j<islands[id].numUnder;j++){
                            tmpGenePool[c][i][j]=islands[id].genePool[c][i][j];
                        }
                    }
                }
                islands[id].numFromMig=0;
            }

            // 选择固定数量的基因保留到下一代
            for(int c=0;c<numSurvive;c++){
                for(int i=0;i<islands[id].numFromMig;i++){
                    for(int j=0;j<islands[id].numUnder;j++){
                        tmpGenePool[numTmpPool+c][i][j]=islands[id].genePool[geneId[c]][i][j];
                    }
                }
            }
            numTmpPool+=numSurvive;
            // 交叉扩充子代数量，子代为父代的平均值
            for (int i = numTmpPool; i < numGenes; i++) {
                int p1 = random.nextInt(i-1);
                int p2 = random.nextInt(i-1);
                for (int ii = 0; ii < islands[id].numFromMig; ii++) {
                    for (int jj = 0; jj < islands[id].numUnder; jj++) {
                        tmpGenePool[i][ii][jj]
                                = (islands[id].genePool[p1][ii][jj] + islands[id].genePool[p2][ii][jj]) / 2;
                    }
                }
                // 变异算子,随机交换两行两列
                if(random.nextDouble()<mutRate){
                    int r1=random.nextInt(islands[id].numOver-1);
                    int r2=random.nextInt(islands[id].numOver-1);
                    if(r1!=r2){
                        for(int j=0;j<islands[id].numUnder;j++){
                            swap(tmpGenePool[i][r1][j],tmpGenePool[i][r2][j]) ;
                        }
                    }
                    int c1=random.nextInt(islands[id].numUnder-1);
                    int c2=random.nextInt(islands[id].numUnder-1);
                    if(c1!=c2){
                        for(int j=0;j<i;j++){
                            swap(tmpGenePool[i][j][c1],tmpGenePool[i][j][c2]);
                        }
                    }
                }
            }

            // 用子代基因替换父代基因
            for(int c=0;c<islands[id].numGenes;c++){
                for(int i=0;i<islands[id].numOver;i++){
                    for(int j=0;j<islands[id].numUnder;j++){
                        islands[id].genePool[c][i][j]= tmpGenePool[c][i][j];
                    }
                }
            }
            adjGene(islands[id], ref);
        }
    }

    void swap(double a,double b){
        double tmp=a;
        a=b;
        b=tmp;
    }


    // 基因对应的方案
    void geneToFlow(int l,int id,int ref){
        prob.initFlow();
        for(int i=0;i<islands[id].numOver;i++){
            for(int j=0;j<islands[id].numUnder;j++){
                prob.flow[cloudId[ref+1+i]][cloudId[j]]=islands[id].genePool[l][i][j];
                prob.flow[cloudId[j]][cloudId[ref+1+i]]=-islands[id].genePool[l][i][j];
            }
        }
    }

    // 根据适应度函数（平均响应时间）给基因排序
    void sortByFitness(int id,int ref){
        for(int l=0;l<numGenes;l++){
            geneId[l]=l;
            fitness[l]=0;
           geneToFlow(l,id,ref);
            for(int i=0;i<Problem.num;i++){
                prob.calCloudlet(clouds[i],i);
            }

            for(int i = 0; i< Problem.num ; i++){
                if(clouds[i].taskResTime>fitness[l]){
                    fitness[l]=clouds[i].taskResTime;
                }
            }
        }

        for(int i=0;i<numGenes-1;i++){
            for(int j=i+1;j<numGenes;j++){
                if(fitness[j]>fitness[i]){
                    int tmp=geneId[i];
                    geneId[i]=geneId[j];
                    geneId[j]=tmp;
                }
            }
        }
    }

    // 根据平均响应时间给微云排序
    void sortByResTime(){
        for(int i = 0; i< Problem.num; i++){
            prob.calCloudlet(clouds[i],i);
        }

        for(int i = 0; i< Problem.num -1; i++){
            for(int j = i+1; j< Problem.num; j++){
                if(clouds[cloudId[j]].taskResTime<clouds[cloudId[i]].taskResTime){
                    int tmp=cloudId[i];
                    cloudId[i]=cloudId[j];
                    cloudId[j]=tmp;
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
