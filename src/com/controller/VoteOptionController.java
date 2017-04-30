package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import com.model.MessageModel;
import com.service.VoteOptionService;
import com.utils.JsonUtil;
import com.utils.SessionUtil;
import com.utils.StringUtil;
import com.vo.User;
import com.vo.Vote;
import com.vo.VoteOption;
@WebServlet(urlPatterns="/voteOption")
public class VoteOptionController extends  HttpServlet{
	private VoteOptionService voteOptionService;
	@Override
	public void init() throws ServletException {
		voteOptionService =new VoteOptionService();
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
			if(act.equals("saveOrUpdateVoteOption")){
				saveOrUpdateVoteOption(req,resp);
			}
		}
	}
	
	private void saveOrUpdateVoteOption(HttpServletRequest req,
			HttpServletResponse resp) {
		MessageModel messageModel=new MessageModel();
		
		String voteOptionName=req.getParameter("voteOptionName");
		String voteId=req.getParameter("voteId");
		
			VoteOption voteOption=new VoteOption();
			voteOption.setVoteOptionName(voteOptionName);
			voteOption.setVoteId(Integer.parseInt(voteId));
			
			messageModel=voteOptionService.saveOrUpdateVoteOption(voteOption);
			JsonUtil.toJson(resp, messageModel);
				
	}
}
