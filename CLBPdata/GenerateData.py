import numpy as np

K=40

# n
numServer=[]

# mu
serviceRate=[]
# lamda
arrivalRate=[]

# network
net=[]
cnt = 0


def genNet():
    net.clear()
    cnt = 0
    while cnt < K * K:
        tmp = np.random.normal(0.15, 0.05)
        if 0.1 < tmp < 0.2:
            net.append(tmp)
            cnt+=1
    sum = 0

    print("{")
    print("{",end="")
    for i in range(K*K):
        if i%K==K-1:
            print(net[i], end="")
        else:
            print(net[i],end=",")
        if i%K==K-1 and i<K*K-1:
            print("},")
            print("{",end="")
        if i==K*K-1:
            print("}")
    print("}")


genNet()

while cnt < K:
    tmpNumServer = np.random.poisson(3)
    tmpServerRate=np.random.normal(5,2)
    tmpArrivalRate=np.random.normal(15,6)
    if tmpNumServer > 0 and tmpServerRate>0 and 0<tmpArrivalRate<tmpNumServer*tmpServerRate-0.25:
        numServer.append(tmpNumServer)
        serviceRate.append(tmpServerRate)
        arrivalRate.append(tmpArrivalRate)
        cnt = cnt + 1

print("lamda: ",arrivalRate)
print("mu:",serviceRate)
print("n:",numServer)