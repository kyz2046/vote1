package com.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.MessageModel;
import com.utils.StringUtil;



@WebFilter(urlPatterns="/21")
public class AutoLogin implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse resp = (HttpServletResponse) arg1;
		// 获取session 用户信息
		MessageModel message = (MessageModel) req.getSession().getAttribute("userInfo");
		// 读取cookie 获取用户信息  用户名 用户密码
		Cookie[] cookies = req.getCookies();
		if(null!=cookies&&cookies.length>0){
			for (Cookie cookie : cookies) {
				String cName = cookie.getName();
				if(!StringUtil.isNullOrEmpty(cName)&&cName.equals("userInfo")){
					String value = cookie.getValue();
					String[] strs = value.split("-");
					// 转发到登录请求
					req.getRequestDispatcher("user?act=login&name="+strs[0]+"&pwd="+strs[1]).forward(req, resp);
					return;
				}
			}
		}
				
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
