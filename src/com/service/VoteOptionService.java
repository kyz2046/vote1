package com.service;

import java.util.List;

import com.constant.NoteConstant;
import com.dao.VoteOptionDao;
import com.model.MessageModel;
import com.utils.StringUtil;
import com.vo.VoteOption;

public class VoteOptionService {
	private  VoteOptionDao voteOptionDao=new VoteOptionDao();
	public List<VoteOption> queryVoteOptionByVid(Integer vid){
		return voteOptionDao.queryVoteOptionByVid(vid);
	}
	
	public MessageModel saveOrUpdateVoteOption(VoteOption voteOption){
		MessageModel messageModel=new MessageModel();
		if(StringUtil.isNullOrEmpty(voteOption.getVoteOptionName())){
			messageModel.setMsg("选项名称不能为空");
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
//			查询
			VoteOption vo=voteOptionDao.queryVoteOptionByVidName(voteOption.getVoteOptionName());
			if(null!=vo){
				messageModel.setMsg("选项名已经存在");		
				messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
				return messageModel;
			}
			
			//执行添加
			int result = voteOptionDao.addVoteOption(voteOption);
			//查询
		if(result<1){
			messageModel.setMsg(NoteConstant.OPTION_FAILED_MSG);		
			messageModel.setResultCode(NoteConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		
		return messageModel;
	}
}
