
public class Cloudlet {
    int numServers; // 微云的服务器个数
    double serviceRate; // 每个服务器的服务速率
    double arrivalRate; // 微云上的任务到达率
    double taskWaitTime; // 微云上任务平均等待时间
    double sumNetDelay; // 微云上的总网络延迟
    double taskResTime; // 微云上任务的平均响应时间
    double finalFlow; // 微云上最终剩余的工作流量
}
