package com.utils;

import javax.servlet.http.HttpServletRequest;

import com.model.MessageModel;
import com.vo.User;

public class SessionUtil {
	public static User queryUserFromSession (HttpServletRequest req){
		MessageModel message = (MessageModel) req.getSession().getAttribute("userInfo");
		User user = null;
		if(null!=message){
			user=(User) message.getResult();
		}
		return user;
	}
}
