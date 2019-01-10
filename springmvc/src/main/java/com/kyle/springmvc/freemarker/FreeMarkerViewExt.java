package com.kyle.springmvc.freemarker;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import freemarker.template.SimpleHash;

public class FreeMarkerViewExt extends FreeMarkerView {

	@Override
	protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		// use ${ctx} in template
		model.put("ctx", request.getContextPath());
		
		// use ${base} in template, it points to /themes
		model.put("base", request.getContextPath() + "/themes");
		
		return super.buildTemplateModel(model, request, response);
	}

	@Override
	protected void doRender(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Auto-generated method stub
		// super.doRender(model, request, response);

		// Expose model to JSP tags (as request attributes).
		exposeModelAsRequestAttributes(model, request);
		
		// Expose all standard FreeMarker hash models.
		SimpleHash fmModel = buildTemplateModel(model, request, response);

		// Grab the locale-specific version of the template.
		Locale locale = RequestContextUtils.getLocale(request);

		processTemplate(getTemplate(locale), fmModel, response);

	}

}
