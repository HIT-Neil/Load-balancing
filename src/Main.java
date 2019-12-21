import java.util.*;

public class Main {
    static void addself(int a){
        a++;
    }
    public static void main(String[] args) {
        // 参数theta、epsilon
        HA ha = new HA(0.1, 0.05);
        ha.heuristicAlgorithm();

        /*
         * S: int numSurvive; // 保留到下一代的数量
         * P: int numGenes; // 每个岛屿上的基因数量
         * Rmut: double mutRate; // 变异率
         * Rmig: double migRate; // 迁移率
         * Pmig: double migPercentage; // 迁移百分比
         * N: int numGeneration; // 迭代次数*/
        //DGA dga=new DGA(10,100,0.02,0.04,0.02,1000);
        //dga.distributedAlgorithm();

    }
}