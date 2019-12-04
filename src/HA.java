import java.util.ArrayList;

public class HA {
    double threshold; // 计算需求的阈值
    double accuracy; // 精度限制，算法停止条件
    double balancedTime; // 平衡衡任务响应时间
    ArrayList<Cloudlet> overloadSet=new ArrayList<>(); // 过载微云集合
    ArrayList<Cloudlet> underloadedSet=new ArrayList<>(); // // 不过载微云集合
    Problem objProb=new Problem();
    Cloudlet[] cloudlet=new Cloudlet[objProb.num];


    // 构造函数
    void HA(double e,double t){
        threshold=e;
        accuracy=t;
    }


    // 启发式算法
    void heuristicAlgorithm(){

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
                overloadSet.add(cloudlet[i]);
            }else{
                underloadedSet.add(cloudlet[i]);
            }
        }
    }


    // 计算微云迁入迁出需求
    void calDemand() {
        double tmp = balancedTime, dem = 0;
        for (int i = 0; i < overloadSet.size(); i++) {
            while (Math.abs(tmp / balancedTime) > threshold) {
                dem += overloadSet.get(i).arrivalRate / 10;
                tmp = balancedTime - objProb.calTaskWaitTime(
                        overloadSet.get(i), overloadSet.get(i).arrivalRate - dem);

            }
            overloadSet.get(i).demand = dem;
        }

        tmp=balancedTime;
        dem=0;
        for(int i=0;i<underloadedSet.size();i++){
            while (Math.abs(tmp / balancedTime) > threshold) {
                dem += underloadedSet.get(i).arrivalRate / 10;
                tmp = balancedTime - objProb.calTaskWaitTime(
                        underloadedSet.get(i), underloadedSet.get(i).arrivalRate - dem);

            }
            underloadedSet.get(i).demand = dem;
        }
    }

    // 最小延迟流
    void minLatencyFlow(){

    }
}
