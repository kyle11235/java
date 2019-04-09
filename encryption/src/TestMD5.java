import java.security.MessageDigest;


public class TestMD5 {

	/**  
	* MD5¼ÓÃÜ  
	*   
	* @param data  
	* @return  
	* @throws Exception  
	*/  
	public static byte[] encryptMD5(byte[] data) throws Exception {   

	   MessageDigest md5 = MessageDigest.getInstance("MD5");   
	   md5.update(data);   
	 
	   return md5.digest();   
	  
	}  
	public static void main(String[] args){
		try {
			
			byte[] bs = encryptMD5("abcabcabc".getBytes());
			
			for(byte b:bs){
				System.out.print(b);
			}
			
			System.out.println("");
			
			
			bs = encryptMD5("abcabcabc".getBytes());
			
			for(byte b:bs){
				System.out.print(b);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	
}
