package com.controller;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constant.ECConstant;
import com.google.gson.GsonBuilder;
import com.model.MessageModel;
import com.service.UserService;
import com.utils.JsonUtil;
import com.utils.Md5Util;
import com.utils.PageInfo;
import com.utils.SessionUtil;
import com.utils.StringUtil;
import com.vo.Channel;
import com.vo.User;
import com.vo.Vote;
import com.vo.VoteOption;
import com.vo.VoteResult;
@WebServlet(urlPatterns="/user")
public class UserController extends HttpServlet {
	UserService userService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		userService= new UserService();
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
			if(act.equals("setAdmin")){
				setAdmin(req,resp);
			}else if(act.equals("userVote")){
				userVote(req,resp);
//				查询投票选项
			}else if(act.equals("showVoteResult")){
				showVoteResult(req,resp);
			}else if (act.equals("addUser")) {
				addUser(req,resp);
			}else if(act.equals("login")){
				Login(req,resp);
			}else if (act.equals("userLoginOut")) {
				userLoginOut(req,resp);
			}else if (act.equals("addChannel")) {
				addChannel(req,resp);
			}else if(act.equals("queryUserByParams")){
				queryUserByParams(req,resp);
			}
		}
	}
	
	
	private void queryUserByParams(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String pageNum = req.getParameter("pageNum");
		if(StringUtil.isNullOrEmpty(pageNum)){
			pageNum="1";
		}
		String id = req.getParameter("id");
		Integer id1=null;
		if(!StringUtil.isNullOrEmpty(id)){
			id1 = Integer.parseInt(id);
		}
		String name = req.getParameter("name");
		
		PageInfo<User> pageInfo = userService.queryUserByParams(id1, name, Integer.parseInt(pageNum));
		req.setAttribute("pageInfo", pageInfo);
		req.setAttribute("change", "admin.jsp");
		req.getRequestDispatcher("main.jsp").forward(req, resp);
		
				
		
	}
	
	private void setAdmin(HttpServletRequest req, HttpServletResponse resp) {
		User user =SessionUtil.queryUserFromSession(req);
		String id = req.getParameter("id");
		
		MessageModel messageModel = userService.setAdmin(user, Integer.parseInt(id));
		JsonUtil.toJson(resp, messageModel);
	}
	/**
	 * 用户投票
	 * 	voteTime  设置投票间隔的Cookie名称
	 */
	private void userVote(HttpServletRequest req, HttpServletResponse resp) {
		MessageModel m=new MessageModel();
		/**
		 * 获取用户投票的参数
		 */
		User user=SessionUtil.queryUserFromSession(req);
		if(null==user){
			m.setMsg("在Session中未找到用户记录");
			m.setResultCode(ECConstant.OPTION_FAILED_CODE);
			JsonUtil.toJson(resp, m);
			return;
		}
		
		Integer id=user.getId();
		
///////、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、/新增
		String voteOptionName = req.getParameter("voteOptionName");
		
//		后台获取间隔时间
		long voteTime=userService.getVoteTime(id);
		/**
		 * 查询Cookie是否存在
		 */
		Cookie[] cookies = req.getCookies();
		if(null!=cookies||cookies.length>0){
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if(!StringUtil.isNullOrEmpty(cookieName)&&"voteTime".equals(cookieName)){
					m=new MessageModel();
					m.setMsg("距离上一次投票间隔时间不足"+voteTime+"分钟");
					m.setResultCode(ECConstant.OPTION_FAILED_CODE);
					JsonUtil.toJson(resp, m);
					return ;
				}
			}
		}
///////、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、/新增
		m=userService.addTivket(voteOptionName,user.getId());

//		对投票结果进行判断
		if(m.getResultCode()==300){
			JsonUtil.toJson(resp, m);
			return;
		}
		
//		成功后设置Cookie
		StringBuffer sb=new StringBuffer();
		String strVoteTime = sb.append(voteTime).toString();
		Cookie cookie=new Cookie("voteTime",strVoteTime);
		cookie.setMaxAge((int)(60*voteTime));
		cookie.setPath(req.getContextPath());
		resp.addCookie(cookie);
		JsonUtil.toJson(resp, m);
	}
	
	
	
	
	/**
	 * 查看投票结果
	 */
	private void showVoteResult(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String voteId = req.getParameter("voteId");
		MessageModel msg=userService.queryVoteResult(voteId);
		if(msg.getResultCode()==300){
			req.setAttribute("msg", msg);
			return ;
		}
		
		VoteResult vr = (VoteResult) msg.getResult();
		String voteName = vr.getVote().getVoteName();
		List<VoteOption> voteOptions = vr.getVoteOptions();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		int yMax =5;
		for (VoteOption voteOption : voteOptions) {
			sb.append(voteOption.getVoteOptionName()+",");
			sb2.append(voteOption.getTicketNum()+",");
			if(voteOption.getTicketNum()>yMax){
				yMax=voteOption.getTicketNum();
			}
			
		}
		String str =sb.toString();
		String str2 =sb2.toString();
		String[] ss  = str.split(",");
		String[] ss2  = str2.split(",");
		String categories = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(ss);
		String values = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(ss2);
		yMax=yMax+5;
		req.setAttribute("yMax", yMax);
		req.setAttribute("categories", categories);
		req.setAttribute("values", values);
		req.setAttribute("voteResultInfo", msg.getResult());
		req.setAttribute("change", "vote.jsp");
		req.getRequestDispatcher("main.jsp").forward(req, resp);
	}

	//添加频道
	private void addChannel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String channelName=req.getParameter("channelName");
		MessageModel messageModel=userService.addChannel(channelName);
		req.setAttribute("msg",  messageModel);
	}
	
	//用户退出
	private void userLoginOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 清除session
				req.getSession().setAttribute("userInfo", null);
				// 清除浏览器cookie
				Cookie cookie=new Cookie("userInfo", null);
				cookie.setMaxAge(0);
				resp.addCookie(cookie);
				resp.sendRedirect("login.jsp");
		
	}
		//用户登录方法
	private void Login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//接收参数
		String name=req.getParameter("name");
		String pwd=req.getParameter("pwd");
		
		MessageModel messageModel=userService.loginCheck(name, pwd);
		
		//登录失败情况
		if (messageModel.getResultCode()==ECConstant.OPTION_FAILED_CODE) {
			req.setAttribute("name", name);
			req.setAttribute("msg", messageModel.getMsg());
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}
		
		//用户信息加入session
		req.getSession().setAttribute("userInfo", messageModel);
		// 判断  是否勾选记住我
		String rm=req.getParameter("rm");
		if(!StringUtil.isNullOrEmpty(rm)&&rm.equals("1")){
			// 用户信息 存入cookie  用户名  用户密码
			Cookie cookie=new Cookie("userInfo", name+"-"+pwd);
			cookie.setMaxAge(60*60*24*3);// 有效时间 3天
			cookie.setPath(req.getContextPath());
			resp.addCookie(cookie);
		}
		
		req.getRequestDispatcher("main.jsp").forward(req, resp);
		
	}

	
	//注册方法
	private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name=req.getParameter("name");
		String pwd=req.getParameter("pwd");
		User user=new User();
		user.setName(name);
		user.setPwd(Md5Util.encode(pwd));
		MessageModel messageModel= userService.userRegister(user);
		if(messageModel.getResultCode()==ECConstant.OPTION_FAILED_CODE){
			req.setAttribute("msg", messageModel.getMsg());
			//req.getRequestDispatcher("main.jsp").forward(req, resp);
			return;
		}
		req.getSession().setAttribute("userInfo", messageModel);
		req.setAttribute("name", name);
		req.getRequestDispatcher("main.jsp").forward(req, resp);
	}
	
}
