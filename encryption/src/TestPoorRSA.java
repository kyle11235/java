import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

import junit.framework.TestCase;


public class TestPoorRSA extends TestCase {

    public static BigInteger n,p,q,phi,e,d,ming,codeMing,decodeMing;
    //随机选取两个大素数p,q     n=p*q    phi=(p-1)(q-1)   
    //选一整数e  0<e<phi  并且e和phi最大公约数为1 
    //d为e的逆元模phi
    //则公匙为e,n  私匙为d,n   
    //加密时 ming的e次方模n得到密文mi
    //解密时 mi的d次方模n得到明文ming
    
	/*
	 *RSA加密解密一个大整数
	 */
  public void testFive(){
	  e=new BigInteger("11111");
	  phi=new BigInteger("12345");
	  d=e.modInverse(phi);//e的逆元模phi
      
		System.out.println("公匙e,n: "+e+","+n);
		System.out.println("私匙d,n: "+d+","+n);
  }
	public void testOne( ){
		System.out.println("请输入:");
		Scanner in = new Scanner(System.in);
		ming = in.nextBigInteger();
		
		int bitLength=64;
		
		Random rnd=new Random();//准备一个随机生成器
		p=BigInteger.probablePrime(bitLength,rnd);//随机大素数
		q=BigInteger.probablePrime(bitLength+2,rnd);//
		e=BigInteger.probablePrime(bitLength+1,rnd);
		n=p.multiply(q);//p*q
		phi=p.subtract(BigInteger.ONE);//p-1
		phi=phi.multiply(q.subtract(BigInteger.ONE));//(p-1)*(q-1)
		d=e.modInverse(phi);//e的逆元模phi
		        
		System.out.println("公匙e,n: "+e+","+n);
		System.out.println("私匙d,n: "+d+","+n);
		        
		//加密
		codeMing=ming.modPow(e,n);//加密  得到加密的数mi
	    System.out.println("密文为:"+codeMing);
	    
    	//解密
	    decodeMing=codeMing.modPow(d,n);//解密 得到解密数ming	 		    
	    System.out.println("解密后的明文为:"+decodeMing); 
	        
    }
    	
			
	
	//测试求最大公约数
	public void testTwo(){
		
		//gcd(12345,11111);
		whileGcd(12345,11111);

	}
	
	/*a>=0 e>0 循环求最大公约数*/
	public  void whileGcd(int a,int e){
		int yu = a%e;
		int xi = a/e;
		
		while(yu!=0){
			System.out.println(" 原式："+a+" = "+xi+" * "+e+" + "+yu+"    变换后："+yu+" = "+a+" - "+xi+" * "+e);
			System.out.println("\n");
			a=e;
			e=yu;
			yu = a%e;
		    xi = a/e;
		}

		if(yu==0)
		{
			System.out.println("最大公约数是:"+e);
			return;
		}
		
	}

	/*a>=0 e>0 递归求最大公约数*/
	public  void gcd(int a,int e){
		int yu = a%e;
		int xi = a/e;
		System.out.println(" 原式："+a+" = "+xi+" * "+e+" + "+yu+"    变换后："+yu+" = "+a+" - "+xi+" * "+e);
		System.out.println("\n");
		if(yu==0)
		{
			System.out.println("最大公约数是:"+e);
			return;
		}
		gcd(e,yu);		
		//System.out.println("递归");	
	}
}
