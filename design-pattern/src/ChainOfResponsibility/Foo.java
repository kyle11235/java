package ChainOfResponsibility;

import util.P;

public class Foo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Mail m1 = new Mail();
		m1.setTo("tracy");
		
		Mail m2 = new Mail();
		m2.setFrom("zhang");
		//通过遍历职责链，来处理邮件
		P.p("---begin handle---");
		for(MailHandler handler:MailHandler.values()){
			
			handler.handle(m1);
		}
		P.p("---begin handle---");
		for(MailHandler handler:MailHandler.values()){
			handler.handle(m2);
		}
	}

}
