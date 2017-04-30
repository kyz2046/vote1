package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.ChannelService;
import com.service.VoteService;
import com.utils.JsonUtil;
import com.utils.PageInfo;
import com.utils.StringUtil;
import com.vo.Channel;
import com.vo.Vote;
@WebServlet(urlPatterns="/main")
public class MainController extends HttpServlet {
	VoteService voteService ;
	ChannelService channelService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		voteService = new VoteService();
		channelService = new ChannelService();
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
			if(act.equals("queryVoteByParams")){
				queryVoteByParams(req,resp);
			}else if(act.equals("queryChannelInfo")){
				queryChannelInfo(req,resp);
			}
			
		}
	}
	private void queryChannelInfo(HttpServletRequest req,
			HttpServletResponse resp) {
		List<Channel> channelInfo = channelService.queryChannelInfo();
		req.setAttribute("channels", channelInfo);
		JsonUtil.toJson(resp, channelInfo);
	}
	private void queryVoteByParams(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String pageNum = req.getParameter("pageNum");
		if(StringUtil.isNullOrEmpty(pageNum)){
			pageNum="1";
		}
		String voteId = req.getParameter("voteId");
		String voteName = req.getParameter("voteName");
		String channelId = req.getParameter("channelId");
		if("-1".equals(channelId)){
			channelId=null;
		}
		PageInfo<Vote> pageInfo = voteService.queryVoteByParams(voteId, voteName, channelId, Integer.parseInt(pageNum));
		req.setAttribute("pageInfo", pageInfo);
		req.setAttribute("change", "vote_list.jsp");
		req.getRequestDispatcher("main.jsp").forward(req, resp);
		
				
		
	}
	
}
