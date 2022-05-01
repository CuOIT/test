'''A=[]
n=int(input())
for i in range(n):
    x=int(input())
    A.append(x)
tongchan=0
for i in range(n):
    if A[i]%2==0:
        tongchan+=A[i]
print('Tong cac so chan: ',tongchan)
min=A[0]
for i in range(n):
    if min>A[i]:
        min=A[i]
print('Gia tri nho nhat cua day: ',min)
for i in range(n-1):
        for j in range(n-i-1):
            if A[j] > A[j + 1] :
                A[j], A[j + 1] = A[j + 1], A[j]
print('Day tang dan: ')
for i in range(n):
    print(A[i],' ')'''
n=3
m=7
print(2*(n+4)-m//3%7 )  