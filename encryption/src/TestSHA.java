import java.security.MessageDigest;


public class TestSHA {
	/**  
	  * SHA加密  
   *   
	     * @param data  
	    * @return  
	     * @throws Exception  
	    */  
	    public static byte[] encryptSHA(byte[] data) throws Exception {   
	
	      MessageDigest sha = MessageDigest.getInstance("SHA");   
	      sha.update(data);   
	
	    return sha.digest();   
	 
	    }   
  

	/**
	 * @param args
	 */
	public static void main(String[] args) {
try {
			
			byte[] bs = encryptSHA("abcabcabc".getBytes());
			
			for(byte b:bs){
				System.out.print(b);
			}
			
			System.out.println("");
			
			
			bs = encryptSHA("abcabcabc".getBytes());
			
			for(byte b:bs){
				System.out.print(b);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}

}
