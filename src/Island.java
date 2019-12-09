public class Island {
    int numGenes;  // 基因数
    int numOver;   // 过载数量
    int numUnder;  // 不过载数量
    // 基因池，genepool[i][j][k]表示
    // 第i个基因是g[j][k]

    double[][][] genePool=new double[numGenes][numOver][numUnder];

    void initGenePool(){
        for(int num=0;num<numGenes;num++){
            for(int i=0;i<numOver;i++){
                for(int j=0;j<numUnder;j++){
                    genePool[num][i][j]=0;
                }
            }
        }
    }
}
