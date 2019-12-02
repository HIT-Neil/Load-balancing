import java.util.*;
public class Problem {
    int num;   // 微云数量
    // flow[i][j]表示从微云i到微云j的工作流大小
    // 且flow[i][j]=-flow[j][i]
    double[][] flow=new double[num][num];
    // 网络延迟，netDelay[i][j]代表微云i到微云j的网络延迟
    double[][] netDelay=new double[num][num];


    // 计算微云的任务平均等待时间
    double calTaskWaitTime(Cloudlet cloudlet){
        int numServers=cloudlet.numServers;
        double serviceRate=cloudlet.serviceRate;
        double arrivalRate=cloudlet.arrivalRate;
        double tmp=formulaErlangC(numServers,arrivalRate/serviceRate);
        double result=tmp/(numServers*serviceRate-arrivalRate)+1/serviceRate;

        return result;
    }

    // 计算微云i上的总网络延迟
    double calSumNetDelay(int i){
        double sum=0;
        for(int j=0;j<num;j++){
            sum+=Math.max(flow[j][i],0)*netDelay[j][i];
        }
        return sum;
    }

    // 计算微云i上的平均响应时间
    double calTaskResTime(Cloudlet cloudlet){
        return cloudlet.taskWaitTime+cloudlet.sumNetDelay;
    }

    // 计算微云i上最终剩余的工作流量
    double calFinalFlow(Cloudlet cloudlet,int i){
        double inFlow=0;
        for(int j=0;j<num;j++){
            inFlow-=flow[i][j];
        }
        return cloudlet.arrivalRate-inFlow;
    }


    // Erlang C公式，用以计算平均等待时间
    double  formulaErlangC(int n,double p){
        double result=0;  // 函数的返回结果
        double numerator; // 公式的分子
        double denominator; // 公式的分母
        double sum=0; // 公式中分母的求和部分

        for(int k=0;k<n;k++){
            sum+=fastPow(n*p,k)/factorial(k);
        }

        numerator=fastPow(n*p,n)/(factorial(n)*(1-p));
        denominator=sum+numerator;
        result=numerator/denominator;

        return result;
    }


    // 快速幂计算a的b次方
    double fastPow(double a,int b){
        double base=a;
        int m=b;
        int res=1;
        while(m!=0){
            if((m&1)==1){
                res*=base;
            }
            base*=base;
            m = m >> 1;
        }
        return res;
    }


    // 计算n的阶乘
    double factorial(int n){
        int tmp=2;
        for(int i=3;i<=n;i++){
            tmp*=i;
        }
        return tmp;
    }
}
