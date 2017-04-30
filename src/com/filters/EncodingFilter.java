package com.filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author lp
 * 编码过滤器定义
 *
 */
@WebFilter(filterName="encodingFilter",urlPatterns="/*",initParams={@WebInitParam(name="charset",value="utf-8")})
public class EncodingFilter implements Filter {

	private FilterConfig filterConfig;
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		/**
		 * 1. tomcat 版本判断
		 *    7 以及以下版本编码  iso-8859-1 
		 *    8 以及以上  utf-8
		 * 获取服务器 版本 拿到对应版本
		 * 2.  编码设置
		 *    8以及以上  utf-8
		 *    7 以及以下  
		 *      get post 分别判断
		 *         post  请求 响应 utf-8
		 *         get   httpservletrequestwrapper getParameter  ISO-8859-1  转到utf-8
		 * 
		 */
		HttpServletRequest request=(HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse) arg1;
		
		String uri= request.getRequestURI();
		
		
		// 获取服务器信息  apache tomcat/7.0.59   apache tomcat/8.0.9 
		String serverInfo=request.getServletContext().getServerInfo();
		System.out.println("服务器信息:"+serverInfo);
		Pattern pattern=Pattern.compile("cat/(\\d)");
		Matcher matcher= pattern.matcher(serverInfo);
		// 如果匹配成功
		if(matcher.find()){
			int version=Integer.parseInt(matcher.group(1));
			String charset=this.filterConfig.getInitParameter("charset");
			System.out.println("编码:"+charset);
			request.setCharacterEncoding(charset);
			response.setCharacterEncoding(charset);
			if(version>=8){
				chain.doFilter(request, response);
				return;
			}
			String method=request.getMethod();
			if(!method.equalsIgnoreCase("get")){
				chain.doFilter(request, response);
				return;
			}
			//  get 请求
			chain.doFilter(new EncodeWrapper(request), response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		this.filterConfig=filterConfig;
	}

}


class  EncodeWrapper extends HttpServletRequestWrapper{
	private HttpServletRequest request;

	public EncodeWrapper(HttpServletRequest request) {
		super(request);
		this.request=request;
	}
	
	@Override
	public String getParameter(String name) {
		String param=request.getParameter(name);
		if(null==param){
			return "";
		}
		
		try {
			param=new String(param.getBytes("ISO-8859-1"),this.request.getCharacterEncoding());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return param;
	}
	
	
	
	
}


