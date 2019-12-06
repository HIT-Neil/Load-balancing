import java.util.ArrayList;

public class HA {
    double threshold; // 计算需求的阈值
    double accuracy; // 精度限制，算法停止条件
    double balancedTime; // 平衡衡任务响应时间
    ArrayList<Integer> overloadSet=new ArrayList<>(); // 过载微云集合
    ArrayList<Integer> underloadedSet=new ArrayList<>(); // // 不过载微云集合
    Problem objProb=new Problem();
    Cloudlet[] cloudlet=new Cloudlet[objProb.num];


    // 构造函数
    void HA(double e,double t){
        threshold=e;
        accuracy=t;
    }


    // 启发式算法
    void heuristicAlgorithm(){
        // 初始化平衡任务响应时间
        initBalancedTime();
        // 根据balancedTime将微云划分为两个集合
        partition(cloudlet);
        double blnTime=Double.MAX_VALUE; // 用于判断循环结束的条件

        while(Math.abs(balancedTime-blnTime)>accuracy){
            // 计算迁入迁出需求
            calDemand();
            objProb.initFlow();
            // 计算最小延迟流
            minLatencyFlow();
            double maxTmp=0;
            for(int j=0;j<underloadedSet.size();j++){
                objProb.calCloudlet(cloudlet[underloadedSet.get(j)],underloadedSet.get(j));
                if(cloudlet[underloadedSet.get(j)].taskResTime>maxTmp){
                    maxTmp=cloudlet[underloadedSet.get(j)].taskResTime;
                }
            }
            blnTime=maxTmp;
            balancedTime=(balancedTime+blnTime)/2;
        }
    }


    // 初始化平衡任务响应时间
    void initBalancedTime(){
        double max=0,min=Double.MAX_VALUE;
        for(int i=0;i<objProb.num;i++){
            if(cloudlet[i].taskResTime>max){
                max=cloudlet[i].taskResTime;
            }
            if(cloudlet[i].taskResTime<min){
                min=cloudlet[i].taskResTime;
            }
        }
        balancedTime=(max+min)/2;
    }


    // 根据balancedTime将微云划分为两个集合
    void partition(Cloudlet[] cloudlet){
        overloadSet.clear();
        underloadedSet.clear();
        for(int i=0;i<objProb.num;i++){
            if(cloudlet[i].taskResTime>balancedTime){
                overloadSet.add(i);
            }else{
                underloadedSet.add(i);
            }
        }
    }


    // 计算微云迁入迁出需求
    void calDemand() {
        double tmp = balancedTime, dem = 0;
        for (int i = 0; i < overloadSet.size(); i++) {
            while (Math.abs(tmp / balancedTime) > threshold) {
                dem += cloudlet[overloadSet.get(i)].arrivalRate / 10;
                objProb.calTaskWaitTime(
                        cloudlet[overloadSet.get(i)], cloudlet[overloadSet.get(i)].arrivalRate - dem);
                tmp = balancedTime -cloudlet[overloadSet.get(i)].taskWaitTime;

            }
            cloudlet[overloadSet.get(i)].demand = dem;
        }

        tmp=balancedTime;
        dem=0;
        for(int i=0;i<underloadedSet.size();i++){
            while (Math.abs(tmp / balancedTime) > threshold) {
                dem += cloudlet[underloadedSet.get(i)].arrivalRate / 10;
                objProb.calTaskWaitTime(
                        cloudlet[underloadedSet.get(i)], cloudlet[underloadedSet.get(i)].arrivalRate - dem);
                tmp = balancedTime - cloudlet[underloadedSet.get(i)].taskWaitTime;

            }
            cloudlet[underloadedSet.get(i)].demand = dem;
        }
    }

    // 最小延迟流
    void minLatencyFlow(){
        // 构造最小费用最大流问题的网络

        // 调用最小费用最大流算法求解
        minCostMaxFlow();
    }

    // 最小费用最大流算法
    void minCostMaxFlow(){

    }
}
