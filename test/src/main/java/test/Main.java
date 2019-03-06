package test;

public class Main {

	public static void main(String[] args) {

		System.out.println("system=" + ClassLoader.getSystemClassLoader());
		System.out.println("extention=" + ClassLoader.getSystemClassLoader().getParent());
		System.out.println("bootstrap=" + ClassLoader.getSystemClassLoader().getParent().getParent());
		System.out.println("bootstrap=" + String.class.getClassLoader());

	}
}
