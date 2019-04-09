package Adapter;

import util.P;
import Strategy.Processor;

//这个Foo类是策略模式演示中的，放在这用一下，它有个process方法，接受一个Processor接口，
//假设现在发现了Filter这个类，注意到Foo类的方法内调用接口的process方法，而这个Filter也有个process方法，
//因此似乎可以复用现有的Foo类，但是Filter不是参数的接口类型，那么可以用适配器来实现这个接口
//另外需要注意的是，Foo的方法参数是个接口，如果是类的话，而新发现的类已经继承自某个类了，那么此时适配器就用不上了
public class Foo {

	//本例中，这个方法得到了复用
	public static void process(Processor p,Object s){
		P.p("Using processor "+p.getName());
		P.p(p.process(s));
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		process(new FilterAdapter(new Filter()),10);
		
	}

}
