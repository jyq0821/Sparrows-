package com.withstars.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("EncodingFilter.doFilter()....");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		request.setCharacterEncoding("utf-8");

		response.setContentType("text/html;charset=utf-8");

		HttpServletRequest myRequest = new MyHttpServletRequest(req);

		chain.doFilter(myRequest, response);
	}

	@Override
	public void destroy() {
	}
}

class MyHttpServletRequest extends HttpServletRequestWrapper {
	private boolean isEncode = true;
	private HttpServletRequest request;

	public MyHttpServletRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	@Override
	public String getParameter(String name) {
		return this.getParameterValues(name) == null ? null : this.getParameterValues(name)[0];
	}

	@Override
	public String[] getParameterValues(String name) {
		return this.getParameterMap().get(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {

		if ("POST".equalsIgnoreCase(request.getMethod())) {// ��POST�ύ
			return request.getParameterMap();
		} else if ("GET".equalsIgnoreCase(request.getMethod())) {

			Map<String, String[]> map = request.getParameterMap();
			if (isEncode) {
				for (Map.Entry<String, String[]> entry : map.entrySet()) {

					String[] vs = entry.getValue();
					for (int i = 0; i < vs.length; i++) {
						try {
							vs[i] = new String(vs[i].getBytes("iso8859-1"), "utf-8");
						} catch (Exception e) {
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					}
				}
				isEncode = false;
			}
			return map;
		} else {
			return request.getParameterMap();
		}
	}
}
