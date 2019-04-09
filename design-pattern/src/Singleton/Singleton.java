package Singleton;

public class Singleton {

	private final int id =1;
	private static Singleton instance = new Singleton();
	
	private Singleton(){}
	
	public static Singleton getInstance(){
		return instance;
	}
	public int getId(){
		return this.id;
	}
}
