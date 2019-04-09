import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class TestBase64 {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		
		String message="1234567890"; //串
		System.out.println("明文字符串:"+message);
		
		System.out.println("字节明文:");
		byte[] bs = message.getBytes();
		for(byte b:bs){
			System.out.print(b);
		}
	
		
		String encodeMessage=null; 

		try {
			
			encodeMessage=encryptBASE64(bs);//参数为字节 返回串
			System.out.println("\n密文字符串:"+encodeMessage);
			
			System.out.println("解密后字节明文：");//参数为串 返回字节
			bs = decryptBASE64(encodeMessage);
			for(byte b:bs){
				System.out.print(b);
			}
			
			String str= new String(bs);
			System.out.println("\n解密后明文字符串:"+str);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	/**  
	* BASE64加密  
	*   
	* @param key  
	* @return  
	* @throws Exception  
    */  
	public static String encryptBASE64(byte[] key) throws Exception {   
		    return (new BASE64Encoder()).encodeBuffer(key);   
	}  
	/**  
	 * BASE64解密  
	*   
	* @param key  
	* @return  
	* @throws Exception  
	*/  
	public static byte[] decryptBASE64(String key) throws Exception {   
		    return (new BASE64Decoder()).decodeBuffer(key);   
		}   
		  


}
