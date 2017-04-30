package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.MessageModel;
import com.service.ManagerService;
import com.utils.JsonUtil;
import com.utils.StringUtil;

/**
 * 管理员
 *	 设置普通用户两次投票间隔
 */

@WebServlet("/manager")
public class ManagerController extends HttpServlet{

	ManagerService ms;
	@Override
	public void init() throws ServletException {
		ms=new ManagerService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String act=req.getParameter("act");
		if(!StringUtil.isNullOrEmpty(act)){
			if(act.equals("setUserVoteTime")){
				setUserVoteTime(req,resp);
				
			}
		}
	}

	private void setUserVoteTime(HttpServletRequest req,HttpServletResponse resp) {
		MessageModel msg =new MessageModel();
		String voteTime=req.getParameter("voteTime");
		String id=req.getParameter("id");
		msg=ms.setUserVoteTime(id,voteTime);
		JsonUtil.toJson(resp, msg);
	}
	
}
