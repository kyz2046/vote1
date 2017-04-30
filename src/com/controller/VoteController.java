package com.controller;

import java.io.IOException;




import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.MessageModel;
import com.service.VoteService;
import com.utils.JsonUtil;
import com.utils.PageInfo;
import com.utils.StringUtil;
import com.vo.Channel;
import com.vo.VoteOption;
@WebServlet(urlPatterns="/vote")
public class VoteController extends HttpServlet {
	VoteService voteService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		voteService= new VoteService();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String act = req.getParameter("act");
		if(!StringUtil.isNullOrEmpty(act)){
			if(act.equals("addVoteResult")){
				addVoteResult(req,resp);
			}else 	if(act.equals("qureyVoteNameByVoteId")){
				qureyVoteNameByVoteId(req,resp);
			}else 	if(act.equals("qureyVoteOptionNameByVoteId")){
				qureyVoteOptionNameByVoteId(req,resp);
			}else if(act.equals("queryChannelByParams")){
				queryChannelByParams(req,resp);
			}else if(act.equals("delectVoteResult")){
				delectVoteResult(req,resp);
			}
		}
	}
	
	
	private void delectVoteResult(HttpServletRequest req, HttpServletResponse resp) {
		String voteId=req.getParameter("voteId");
		MessageModel messageModel=voteService.deleteVoteByVoteId(Integer.parseInt(voteId));
		JsonUtil.toJson(resp, messageModel);
		
	}

	private void queryChannelByParams(HttpServletRequest req,
			HttpServletResponse resp) {
		String pageNum=req.getParameter("pageNum");
		if(StringUtil.isNullOrEmpty(pageNum)){
			pageNum="1";
		}
		Channel channel=(Channel) req.getSession().getAttribute("channel");
		PageInfo<Channel> pageInfo=voteService.queryChannelByParams(channel.getChannelId(),Integer.parseInt(pageNum));
	}
	
	private void qureyVoteNameByVoteId(HttpServletRequest req,
			HttpServletResponse resp) {
			String voteId=req.getParameter("voteId");
			String voteName=voteService.queryVoteNameByVoteId(Integer.parseInt(voteId));
			JsonUtil.toJson(resp, voteName);
	}
	private void qureyVoteOptionNameByVoteId(HttpServletRequest req,
			HttpServletResponse resp) {
		String voteId=req.getParameter("voteId");
		String voteOptionName=voteService.queryVoteNameByVoteId(Integer.parseInt(voteId));
		JsonUtil.toJson(resp, voteOptionName);
	}
	
	private void addVoteResult(HttpServletRequest req, HttpServletResponse resp) {
			String voteName=req.getParameter("voteName");
			String channelId=req.getParameter("channelId");
			String endTime=req.getParameter("endTime");
			String voteOption01=req.getParameter("voteOption01");
			String voteOption02=req.getParameter("voteOption02");
			String voteOption03=req.getParameter("voteOption03");
			/*String voteId=req.getParameter("voteId");*/
			String voteOptionName=req.getParameter("voteOptionName");
			VoteOption voteOption=new VoteOption();
			/*voteOption.setVoteId(Integer.parseInt(voteId));*/
			voteOption.setVoteOptionName(voteOptionName);
			MessageModel messageModel=voteService.addVoteResult(voteName,channelId,endTime,voteOption01,voteOption02,voteOption03);
			JsonUtil.toJson(resp, messageModel);
			
	}
	
}
