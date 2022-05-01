n=int(input())
A=[]
le=0
chan=0
for i in range(n):
    x=int(input())
    A.append(x)
    if A[i]%2==0:
        chan=chan+A[i]
    else:
        le=le+A[i]
print("Tong cac so le la: ",le)
print("Tong cac so chan la: ",chan)
