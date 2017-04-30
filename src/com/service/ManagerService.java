package com.service;

import com.constant.ECConstant;
import com.dao.ManagerDao;
import com.model.MessageModel;
import com.utils.StringUtil;

/**
 * 管理员Service
 */
public class ManagerService {
	ManagerDao md=new ManagerDao();
	MessageModel m=new MessageModel();
	
	public MessageModel setUserVoteTime(String id,String voteTime) {
		if(StringUtil.isNullOrEmpty(voteTime)){
			voteTime="10";
		}
		if(StringUtil.isNullOrEmpty(id)){
			m.setMsg("必须选择一个想要修改间隔时间的用户");
			m.setResultCode(ECConstant.OPTION_FAILED_CODE);
			return m;
			
		}
		int result=md.setUserVoteTime(id,voteTime);
		if(result<1){
			m.setResultCode(ECConstant.OPTION_FAILED_CODE);
			m.setMsg("设置失败");
		}
		return m;
	}
	
}
