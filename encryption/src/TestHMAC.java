import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class TestHMAC {
	/**  
	* 初始化HMAC密钥  
	*
    * MAC算法可选以下多种算法  
	*   
	* <pre>  
	* HmacMD5   
	* HmacSHA1   
	* HmacSHA256   
	* HmacSHA384   
	* HmacSHA512  
    * </pre>  
	* @return  
    * @throws Exception  
	*/  
	public static String initMacKey() throws Exception {   
	   KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");   
	  
	    SecretKey secretKey = keyGenerator.generateKey();   
	  return TestBase64.encryptBASE64(secretKey.getEncoded());   
		}   
		  
	/**  
	* HMAC加密  
	*   
	* @param data  
	* @param key  
    * @return  
	* @throws Exception  
	*/  
		public static byte[] encryptHMAC(byte[] data, String key) throws Exception {   

		SecretKey secretKey = new SecretKeySpec(TestBase64.decryptBASE64(key), "HmacMD5");   
	    Mac mac = Mac.getInstance(secretKey.getAlgorithm());   
	    mac.init(secretKey);   
	  
	    return mac.doFinal(data);   
		}  
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		
		try {
			//产生密匙
			String str = initMacKey();//每次运行产生不同的密匙
			String str1= initMacKey();
			
			byte[] bs = encryptHMAC("abcabcabc".getBytes(),str);
			
			for(byte b:bs){
				System.out.print(b);
			}
			
			System.out.println("");
			
			
			bs = encryptHMAC("abcabcabc".getBytes(),str);//同一个密匙str
			
			for(byte b:bs){
				System.out.print(b);
			}
			
			bs = encryptHMAC("abcabcabc".getBytes(),str1);//不同密匙的str1
			
			for(byte b:bs){
				System.out.print(b);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
