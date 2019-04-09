package Strategy;

import util.P;

//本例只演示策略，似乎就是在说分离以下的process方法和具体的process动作，让这个方法可以接受变化的策略
public class Foo {

	//此方法是公共部分，传入的是策略p和要处理的数据s
	public static void process(Processor p,Object s){
		P.p("Using processor "+p.getName());
		P.p(p.process(s));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		process(new Upcase(),"hello");
		process(new Downcase(),"worLD");
	}

}
