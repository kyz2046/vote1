package com.service;

import com.constant.ECConstant;
import com.dao.UserDao;
import com.model.MessageModel;
import com.utils.Md5Util;
import com.utils.PageInfo;
import com.utils.StringUtil;
import com.vo.User;
import com.vo.VoteResult;

public class UserService {
	UserDao userDao = new UserDao();
	
	
	public PageInfo<User> queryUserByParams (Integer id,String name,Integer pageNum){
		return userDao.queryUserByParams(id, name, pageNum);
	}
	
	public MessageModel setAdmin(User user,Integer id){
		MessageModel messageModel =new MessageModel();
		User user2 = userDao.queryUserById(id);
		if(null==user2 ){
			messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
			messageModel.setMsg("查询目标不存在");
			return messageModel;
		}
		if(user2.getManId() ){
			messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
			messageModel.setMsg("查询目标为管理员");
			return messageModel;
		}
		user = userDao.queryUserById(user.getId());
		if(user.getAddRight()<=0){
			messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
			messageModel.setMsg("没有设置管理员的次数了");
			return messageModel;
		}
		int result = userDao.setAdmin(id, user.getId());
		if(result<1){
			messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
			messageModel.setMsg(ECConstant.OPTION_FAILED_MSG);
		}
		return messageModel;
	}
	/**
	 * 通过voteID查看投票选项
	 */
	public MessageModel queryVoteResult(String voteId) {
		MessageModel m =new MessageModel();
		if(StringUtil.isNullOrEmpty(voteId)){
			m.setMsg("voteId为空");
			m.setResultCode(ECConstant.OPTION_FAILED_CODE);
			return m;
		}
	//
		VoteResult result=userDao.queryVoteResult(voteId);
		if(null==result){
			m.setMsg("没有相关记录");
			m.setResultCode(ECConstant.OPTION_FAILED_CODE);
			return m;
		}
		m.setResult(result);
		return m;
	}

	/**
	 * 获取投票间隔时间
	 */
	public long getVoteTime(Integer id) {
		return userDao.getVoteTime(id);
	}
	//判断用户名 密码 是否为空
		public MessageModel loginCheck(String name,String pwd){
			MessageModel messageModel=new MessageModel();
			//校验参数合法性
			if (StringUtil.isNullOrEmpty(name.trim())) {
				messageModel.setMsg("用户名不能为空！");
				messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
				return messageModel;
			}
			if (StringUtil.isNullOrEmpty(pwd.trim())) {
				messageModel.setMsg("密码不能为空！");
				messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
				return messageModel;
			}
			
			//执行用户查询
					User user=userDao.queryUserByUserName(name);
					if (null==user) {
						messageModel.setMsg("用户记录不存在！");
						messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
						return messageModel;
					}
					
					
					//校验密码 md5加密
					if (!user.getPwd().equals(Md5Util.encode(pwd))) {
						
						messageModel.setMsg("密码不匹配！");
						messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
						return messageModel;
					}
					
					// 登录成功 将user 对象放入messagemodel
					messageModel.setResult(user);
					return messageModel;
		}
		
		
		//查询用户是否注册
		public MessageModel userRegister(User user){
			
			MessageModel messageModel=new MessageModel();
			
			if(null==user.getName()||"".equals(user.getName().trim())){
				messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
				messageModel.setMsg("用户名不能为空!");
				return messageModel;
			}		
			if (null==user.getPwd()||"".equals(user.getPwd().trim())) {
				messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
				messageModel.setMsg("密码不能为空！");
				return messageModel;
			}
			User temp= userDao.queryUserByUserName(user.getName());
			if (null!=temp) {
				messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
				messageModel.setMsg("该用户已注册！");
				return messageModel;
			}
			int result=userDao.saveUser(user);
			if (result<1) {
				messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
				messageModel.setMsg(ECConstant.OPTION_FAILED_MSG);
				return messageModel;
			}
			return messageModel;
			
		}
		//判断频道是否为空
		public MessageModel addChannel(String channelName){
			MessageModel messageModel =new MessageModel();
			if (StringUtil.isNullOrEmpty(channelName)) {
				messageModel.setMsg("频道名称不能为空！");
				messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
				return messageModel;
			}
			/**
			 * 查询通道是否存在
			 */
			int result=userDao.queryChannelIdByChannelName( channelName);
			
			if(result<1){
				messageModel.setMsg("频道名称已经存在！");
				messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
				return messageModel;
			}
			result = userDao.addChannel(channelName);
			if(result<1){
				messageModel.setMsg(ECConstant.OPTION_FAILED_MSG);
				messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
			}
			return messageModel;
		}
		
		
		
		public int queryChannelIdByChannelName(String channelName){
			int result=userDao.queryChannelIdByChannelName( channelName);
			return result;
		}
		
		
		/**
		 * 用户投票业务逻辑处理层
		 */
		public MessageModel addTivket(String voteOptionName, Integer id) {
			MessageModel m =new MessageModel();
			if(StringUtil.isNullOrEmpty(voteOptionName)){
				m.setMsg("空的投票选项名称！！");
				m.setResultCode(ECConstant.OPTION_FAILED_CODE);
				return m;
			}
			if(null==id){
				m.setMsg("空的用户ID！！");
				m.setResultCode(ECConstant.OPTION_FAILED_CODE);
				return m;
			}
			
			return userDao.addTicket(id, voteOptionName);
		}
	
	
}
