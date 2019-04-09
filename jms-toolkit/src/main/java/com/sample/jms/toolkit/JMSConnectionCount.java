package com.sample.jms.toolkit;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSConnectionCount {

	public static void main(String[] args) throws NamingException, InterruptedException {
		String JNDIFactory = "weblogic.jndi.WLInitialContextFactory";
		String providerUrl = "t3://localhost:7003";
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDIFactory);
		env.put(Context.PROVIDER_URL, providerUrl);
		Context ctx = new InitialContext(env);

		String connFactoryJNDI = "ConnectionFactory-0";
		QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup(connFactoryJNDI);
		QueueConnection qConn = null;

		int i = 0;
		while (i++ < 10) {
			try {
				qConn = (QueueConnection) connFactory.createConnection();
				qConn.setClientID(String.valueOf(i));
				System.out.println(i);
			} catch (JMSException e) {
				e.printStackTrace();
				try {
					qConn.close();
				} catch (JMSException e1) {
					e1.printStackTrace();
				}
			}
		}

		Thread.sleep(1000 * 1000);
	}
}