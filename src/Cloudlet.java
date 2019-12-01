public class Cloudlet {

    int numServers;
    double serviceRate;
    double arrivalRate;

    // 计算微云的任务平均等待时间
    double taskWaitTime(){
        return 0;
    }

    // Erlang C公式，用以计算平均等待时间
    double  formulaErlangC(int n,double p){
        double result=0;
        double numerator; // 公式的分子
        double denominator; // 公式的分母
        double factorial=2; // 阶乘

        // 计算n的阶乘
        for(int i=3;i<=n;i++){
            factorial*=i;
        }

        // 快速幂计算n*p的n次方
        double base=n*p;
        int res=1;
        int m=n;

        while(m!=0){
            if((m&1)==1){
                res*=base;
            }
            base*=base;
            m = m >> 1;
        }
        System.out.println(res);
        return result;
    }
}
