#include<cstdio>

int n, top;
int line[100];
void func(int last)
{
	if (n == 0)
	{
		if(top!=1)
			printf("%d", line[1]);
		for (int i = 2; i <= top; ++i)
			printf("+%d", line[i]);
		printf("\n");
		return;
	}

	for (int i = last; i <= n; ++i)
	{
		top++;
		line[top] = i;
		n -= i;
		func(i);
		n += i;
		top--;
	}

	return;
}

int main()
{
	scanf("%d", &n);
	top = 0;
	func(1);
	return 0;
}

