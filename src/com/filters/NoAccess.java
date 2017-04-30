package com.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.MessageModel;




@WebFilter(urlPatterns="/123")
public class NoAccess implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse) arg1;
		/**
		 * 1.静态资源放行处理  /js/  /skin/  login.jsp  register.jsp
		 * 2. user addUser  userLogin  userLoginOut 放行处理
		 * 3. 从session  获取用户信息  如果获取用户信息  null  未登录状态 重定向到登录页面  非null  放行处理
   		 */
		
		String uri= request.getRequestURI();
		if(uri.contains("/statics/")||uri.contains("login.jsp")||
				uri.contains("register.jsp")){
			chain.doFilter(request, response);
			return;
		}
		
		// user   http://localhost:8080/wangc05/user?op=userLogin
		if(uri.contains("user")){
			String op=request.getParameter("act");
			//  合法请求
			if(op.equals("addUser")||op.equals("login")||op.equals("userLoginOut")){
				chain.doFilter(request, response);
				return;
			}		
		}
		// 获取用户信息 从session
		MessageModel user= (MessageModel) request.getSession().getAttribute("userInfo");
		if(null==user){
			// 重定向到登录页面
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		// 已登录状态
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
