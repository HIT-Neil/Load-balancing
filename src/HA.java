import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class HA {
    double theta; // 精度限制，算法停止条件
    double epsilon; // 计算需求的阈值

    // 构造函数
    HA(double t,double e){
        theta =t;
        epsilon =e;
    }

    double balancedTime; // 平衡衡任务响应时间
    ArrayList<Integer> overloadSet=new ArrayList<>(); // 过载微云集合
    ArrayList<Integer> underloadedSet=new ArrayList<>(); // // 不过载微云集合
    Problem objProb=new Problem();
    Cloudlet[] cloudlet=new Cloudlet[objProb.num];
    int cnt=0; // 记录当前边号

    // 启发式算法
    void heuristicAlgorithm(){
        objProb.initFlow();
        objProb.initCloudlet(cloudlet);

        // 初始化平衡任务响应时间
        initBalancedTime();
        // 根据balancedTime将微云划分为两个集合
        partition(cloudlet);
        System.out.println("过载集合数量："+overloadSet.size());
        System.out.println("不过载集合数量："+underloadedSet.size());
        double blnTime=Double.MAX_VALUE; // 用于判断循环结束的条件

        while(Math.abs(balancedTime-blnTime)> theta){
            // 计算迁入迁出需求
            calDemand();
            objProb.initFlow();
            cnt=0;
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

        double maxResTime=0;
        for(int i=0;i<objProb.num;i++){
            objProb.calCloudlet(cloudlet[i],i);
            if(cloudlet[i].taskResTime>maxResTime){
                maxResTime=cloudlet[i].taskResTime;
            }
        }

        System.out.println("用启发式算法得到的平均响应时间为："+maxResTime);
        System.out.println("方案为：");
        for(int i=0;i<objProb.num;i++){
            for(int j=0;j<objProb.num;j++){
                System.out.print(objProb.flow[i][j]+" ");
            }
            System.out.println(" ");
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
            while (Math.abs(tmp / balancedTime) > epsilon) {
                dem += cloudlet[overloadSet.get(i)].arrivalRate / 150;
                objProb.calTaskWaitTime(
                        cloudlet[overloadSet.get(i)], cloudlet[overloadSet.get(i)].arrivalRate - dem);
                tmp = balancedTime -cloudlet[overloadSet.get(i)].taskWaitTime;
            }
            cloudlet[overloadSet.get(i)].demand = dem;
        }

        tmp=balancedTime;
        dem=0;
        for(int i=0;i<underloadedSet.size();i++){
            while (Math.abs(tmp / balancedTime) > epsilon&&dem<=cloudlet[underloadedSet.get(i)].arrivalRate) {
                dem += cloudlet[underloadedSet.get(i)].arrivalRate / 150;
                objProb.calTaskWaitTime(
                        cloudlet[underloadedSet.get(i)], cloudlet[underloadedSet.get(i)].arrivalRate - dem);
                tmp = balancedTime - cloudlet[underloadedSet.get(i)].taskWaitTime;
            }
            cloudlet[underloadedSet.get(i)].demand = dem;
        }
    }

    // 最小延迟流
    void minLatencyFlow(){
        int sumEdge=(overloadSet.size()+underloadedSet.size()+ overloadSet.size()*underloadedSet.size())*2;
        int N= objProb.num+2; // 节点数
        FlowEdges[] edges=new FlowEdges[sumEdge]; // 所以边的集合
        int[] head=new int[N]; // 记录以当前节点为起点的最后一条边号
        int[] pre=new int[N];  // 记录增广路径
        boolean[] vis=new boolean[N]; // 已经加入队列的节点
        double[] dis=new double[N]; // 从源点到当前节点的距离
        // 构造最小费用最大流问题的网络
        // 源点编号num，汇点编号num+1
        for (int i = 0; i < N; i++) {
            head[i] = -1;
        }
        // 添加从源点到过载集合的边
        for (int i = 0; i < overloadSet.size(); i++) {
            addEdge(head,edges,objProb.num,overloadSet.get(i), cloudlet[overloadSet.get(i)].demand, 0);
        }
        // 添加从过载集合到不过载集合的边
        for (int i = 0; i < overloadSet.size(); i++) {
            for(int j=0;j<underloadedSet.size();j++){
                addEdge(head,edges,overloadSet.get(i), underloadedSet.get(j),
                        Math.min(cloudlet[overloadSet.get(i)].demand,cloudlet[underloadedSet.get(j)].demand), objProb.netDelay[i][j]);
            }
        }
        // 添加从不过在集合到汇点的边
        for (int i = 0; i < underloadedSet.size(); i++) {
            addEdge(head,edges,underloadedSet.get(i), objProb.num+1,cloudlet[underloadedSet.get(i)].demand, 0);
        }
        // 调用最小费用最大流算法求解
        minCostMaxFlow(head,edges,N,vis,dis,pre,objProb.num, objProb.num+1);
    }

    // 最小费用最大流算法
    void minCostMaxFlow(int[] head,FlowEdges[] edges,int N,boolean[] vis,double[] dis,int[] pre,int s, int t){
       // double incFlow = 0, incCost = 0;
        while (spfa(head,edges, N,vis,dis,pre,objProb.num, objProb.num+1)) {
            double Min = Double.MAX_VALUE;
            for (int i = pre[t]; i != -1; i = pre[edges[i ].from])
                Min = Math.min(Min, edges[i].capacity - edges[i].eFlow);
            for (int i = pre[t]; i != -1; i = pre[edges[i ].from]) {
                edges[i].eFlow += Min;
                edges[i ^ 1].eFlow -= Min;
                objProb.flow[edges[i].from][edges[i].to]+=Min;
                objProb.flow[edges[i].to][edges[i].from]-=Min;
                // incCost += edges[i].cost*Min;
            }
            // incFlow += Min;
        }
    }

    void addEdge(int[] head,FlowEdges[] edges,int x,int y,double w,double c){
        // 添加前向边
        FlowEdges newEdge=new FlowEdges();

        newEdge.from = x;
        newEdge.to = y;
        newEdge.capacity = w;
        newEdge.cost = c;
        newEdge.eFlow = 0;
        newEdge.next = head[x];
        edges[cnt]=newEdge;
        head[x] = cnt;
        cnt+=1;
        // 添加反向边
        FlowEdges antiEdge=new FlowEdges();
        antiEdge.from = y;
        antiEdge.to = x;
        antiEdge.capacity = 0;
        antiEdge.cost = -c;
        antiEdge.eFlow = 0;
        antiEdge.next = head[y];
        edges[cnt]=antiEdge;
        head[y] = cnt;
        cnt+=1;
    }

    // spfa算法，求解最短路径
    boolean spfa(int[] head,FlowEdges[] edges,int N,boolean[] vis,double[] dis,int[] pre,int s, int t)
    {
        Queue<Integer> q = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            vis[i] = false;
            dis[i] = Double.MAX_VALUE;
            pre[i] = -1;
        }

        vis[s] = true;
        dis[s] = 0;
        q.offer(s);

        while (q.peek()!=null) {
            int u = q.poll();
            vis[u] = false;

            for (int i = head[u]; i != -1; i = edges[i].next) {
                int v = edges[i].to;

                if (edges[i].capacity > edges[i].eFlow&&dis[v] > dis[u] + edges[i].cost) {
                    dis[v] = dis[u] + edges[i].cost;
                    pre[v] = i;

                    if (!vis[v]) {
                        vis[v] = true;
                        q.offer(v);
                    }
                }
            }
        }
        if (pre[t] == -1) return false;
        return true;
    }
}
