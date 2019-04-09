package ChainOfResponsibility;

import util.P;

//两种处理策略构成了一个职责链
public enum MailHandler {

	//正常发送
	General_DELIVERY{
		boolean handle(Mail m){
			if(m.getTo()!=null){
				P.p("General_DELIVERY success");
				return true;
			}
			else{
				P.p("General_DELIVERY fail");
				return false;
			}
		}
	},
	//发送不了，返回给寄信人
	RETURN_TO_SENDER{
		boolean handle(Mail m){
			if(m.getFrom()!=null){
				P.p("RETURN_TO_SENDER success");
				return true;
			}
			else{
				P.p("RETURN_TO_SENDER success");
				return false;
			}
		}
	};
	abstract boolean handle(Mail m);
}
