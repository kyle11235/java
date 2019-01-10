package com.kyle.springmvc.freemarker;


import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * 扩展FreeMarkerViewResolver
 * 
 * @author Administrator
 * 
 */
public class FreeMarkerViewResolverExt extends FreeMarkerViewResolver {
	public String getPrefix() {
		return super.getPrefix();
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Class requiredViewClass() {
		return FreeMarkerViewExt.class;
	}

}
