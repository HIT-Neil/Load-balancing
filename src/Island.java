public class Island {
    int numGenes;  // 基因数
    int numOver;   // 过载数量
    int numUnder;  // 不过载数量
    int numFromMig=0; // 迁移来的基因数
    // 基因池，genepool[i][j][k]表示
    // 第i个基因是g[j][k]
    double[][][] genePool;

    // 标记该基因是否可用
    boolean[] available;

    // 其他节点迁移来的基因
    double[][][] geneFromMig;

    void initGenePool(){
        for(int num=0;num<numGenes;num++){
            available[num]=true;
            for(int i=0;i<numOver;i++){
                for(int j=0;j<numUnder;j++){
                    genePool[num][i][j]=0;
                }
            }
        }
    }

}
