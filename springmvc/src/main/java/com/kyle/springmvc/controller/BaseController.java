package com.kyle.springmvc.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseController {

	@ExceptionHandler({ Exception.class })
	public ModelAndView exception(Exception e) {
		ModelAndView modelView = new ModelAndView("/Error500");
		e.printStackTrace();
		return modelView;
	}

	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public ServletContext getServletContext() {
		return ContextLoader.getCurrentWebApplicationContext().getServletContext();
	}

	public int getInt(String name) {
		return getInt(name, 0);
	}

	public int getInt(String name, int defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return Integer.parseInt(resultStr);
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public BigDecimal getBigDecimal(String name) {
		return getBigDecimal(name, null);
	}

	public BigDecimal getBigDecimal(String name, BigDecimal defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return BigDecimal.valueOf(Double.parseDouble(resultStr));
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public String getString(String name) {
		return getString(name, null);
	}

	public String getString(String name, String defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr == null || "".equals(resultStr) || "null".equals(resultStr) || "undefined".equals(resultStr)) {
			return defaultValue;
		} else {
			return resultStr;
		}
	}

	public String getParam(String name, String defaultValue) {
		Object obj = getRequest().getAttribute(name);
		if (obj == null) {
			return defaultValue;
		} else {
			String resultStr = obj.toString();
			if (resultStr == null || "".equals(resultStr) || "null".equals(resultStr) || "undefined".equals(resultStr)) {
				return defaultValue;
			} else {
				return resultStr;
			}
		}
	}

	public void outPrint(HttpServletResponse response, Object result) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public String redirect(HttpServletRequest request, HttpServletResponse response, String path, String ftlName) {
		try {
			response.sendRedirect(request.getContextPath() + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ftlName;
	}

	public String redirect(String path) {
		return "redirect:" + path;
	}

	public String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
						break;
					}
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(str.indexOf("MAC 地址") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}

	public double getDouble(String name) {
		return getDouble(name, 0);
	}

	public double getDouble(String name, double defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return Double.parseDouble(resultStr);
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

}
