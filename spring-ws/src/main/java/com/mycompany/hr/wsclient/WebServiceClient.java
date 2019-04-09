package com.mycompany.hr.wsclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

public class WebServiceClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	private String soapAction;

	public void setDefaultUri(String defaultUri) {
		webServiceTemplate.setDefaultUri(defaultUri);
	}

	public void marshalWithSoapActionHeader(Object o) {

		webServiceTemplate.marshalSendAndReceive(o, new WebServiceMessageCallback() {

			public void doWithMessage(WebServiceMessage message) {
				((SoapMessage) message).setSoapAction(webServiceTemplate.getDefaultUri() + "/" + soapAction);
			}
		});
	}

	public String getSoapAction() {
		return soapAction;
	}

	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}

	

}