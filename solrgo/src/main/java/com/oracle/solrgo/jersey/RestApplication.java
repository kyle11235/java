package com.oracle.solrgo.jersey;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class RestApplication extends ResourceConfig {

	public RestApplication() {
		super.packages("com.oracle");
		super.register(JacksonFeature.class);
	}
}
