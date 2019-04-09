import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;


/*
RSA加密解密一个大整数
1.随机选取两个大素数p,q     n=p*q    pn=(p-1)(q-1)   
2.选一整数e  0<e<pn  并且e和pn最大公约数为1 
3.d为e的逆元模pn
4.则公匙为e,n  私匙为d,n   
5.加密时 M的e次方模n得到密文C
6.解密时 C的d次方模n得到明文M
*/

public class Test {	
public static BigInteger n,p,q,pn,pn1,e,d,M,C,M1;
static BigInteger prime[];
static BigInteger xishu[];


	public static void testOne( ){
		System.out.println("输入一个数字:");
		Scanner in = new Scanner(System.in);
		M = in.nextBigInteger();
		
		int bitLength=64;
		
		Random rnd=new Random();//准备一个随机生成器
		p=BigInteger.probablePrime(bitLength,rnd);//随机大素数
		q=BigInteger.probablePrime(bitLength+2,rnd);//
		e=BigInteger.probablePrime(bitLength+1,rnd);
		n=p.multiply(q);//p*q
		pn=p.subtract(BigInteger.ONE);//p-1
		pn=pn.multiply(q.subtract(BigInteger.ONE));//(p-1)*(q-1)
		
		int temp=0,i=0;
		BigInteger bigInt=new BigInteger(String.valueOf(temp));
		pn1=pn;
		prime=new BigInteger[200];
		xishu=new BigInteger[200];
        prime[0]=pn.mod(e);
        xishu[0]=pn.divide(e);
        while(!prime[i].equals(bigInt))
        {   
        	pn=e;
        	e=prime[i]; System.out.println("解密后的明文"+prime[i]+"   "+bigInt+"  "+i);
        	prime[i+1]=pn.mod(e);
        	xishu[i+1]=pn.divide(e);
        	i++;
        }
        System.out.println("解密后的明文"+i);
        /*int j=i-1;
        BigInteger t1,t2,t;
        t1=(xishu[j].multiply(xishu[j-1])).add(BigInteger.ONE);
        t2=xishu[j].multiply(new BigInteger("-1"));
        while(j>=2)
        {
        	t=t2.multiply(new BigInteger("-1"));
        	t2=t1;
        	t1=(t1.multiply(xishu[j-2]).add(t)).multiply(new BigInteger("1"));
        	j--;
        }
		d=t1.add(pn1);
		//d=e.modInverse(pn);//e的逆元模pn
		        
		System.out.println("公匙e,n: "+e+","+n);
		System.out.println("私匙d,n: "+d+","+n);
		        
		//加密
		//C=M.modPow(e,n);//加密  得到加密的数C
	   // System.out.println("密文为:"+C);
	    
		//解密
	    //M1=C.modPow(d,n);//解密 得到解密数M1	 		    
	    //System.out.println("解密后的明文为:"+M1); */
	        
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testOne();
	}

}
