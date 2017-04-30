package com.service;

import java.util.List;

import com.constant.ECConstant;
import com.dao.VoteDao;
import com.model.MessageModel;
import com.utils.PageInfo;
import com.utils.StringUtil;
import com.vo.Channel;
import com.vo.Vote;
import com.vo.VoteOption;

public class VoteService {
	VoteDao voteDao = new VoteDao();
	public PageInfo<Vote> queryVoteByParams (String voteId,String voteName,String channelId,Integer pageNum){
		return voteDao.queryVoteByParams(voteId, voteName, channelId, pageNum);
	}
//	添加投票结果
	public  MessageModel addVoteResult(String voteName,String channelId ,String endTime,String voteOption01,String voteOption02,String voteOption03){
		MessageModel messageModel=new MessageModel();
		//vote_name,channel_id,end_time不能为空
		if(StringUtil.isNullOrEmpty(voteName)){
			messageModel.setMsg("投票名称不能为空");
			messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		if(StringUtil.isNullOrEmpty(channelId)){
			messageModel.setMsg("频道不能为空");
			messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		if(null==endTime){
			messageModel.setMsg("截止日期不能为空");
			messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		if(StringUtil.isNullOrEmpty(voteOption01)){
			messageModel.setMsg("投票选项不能为空");
			messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		if(StringUtil.isNullOrEmpty(voteOption02)){
			messageModel.setMsg("投票选项不能为空");
			messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		if(StringUtil.isNullOrEmpty(voteOption03)){
			messageModel.setMsg("投票选项不能为空");
			messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		
		
		
		
		int result01=voteDao.addVote(voteName, channelId, endTime);
		
		if(result01<1){
			messageModel.setMsg(ECConstant.OPTION_FAILED_MSG);
			messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		
		PageInfo<Vote>  pageInfo = voteDao.queryVoteByParams(null, voteName, null, 1);
		
//		获取查询的数据
		 List datas = pageInfo.getCurrentDatas();
		 
		 Vote vote11=(Vote) datas.toArray()[0];
//		获取新增的Vote的ID
		 Integer voteId=vote11.getVoteId();
		 
//		 创建VoteOPtion对象
		VoteOption voteOption=new VoteOption();
//				设置Vote对象的ID
				voteOption.setVoteId(voteId);
//				设置选项名称
				
		voteOption.setVoteOptionName(voteOption01);
//				添加选项
		voteDao.addVoteOption(voteOption);
		
		voteOption.setVoteOptionName(voteOption02);
//		添加选项
		voteDao.addVoteOption(voteOption);
		
		voteOption.setVoteOptionName(voteOption03);
//			添加选项
		voteDao.addVoteOption(voteOption);
		
		return messageModel;
	}
	
	
	
	public  String queryVoteNameByVoteId(int voteId){
		return voteDao.queryVoteNameByVoteId(voteId);
	}
	
	public String queryVoteOptionNameByVoteId(int voteId){
		return voteDao.queryVoteOptionNameByVoteId(voteId);
	}
	
	public PageInfo<Channel> queryChannelByParams(int channelId,int pageNum){
		return voteDao.queryChannelByParams(channelId, pageNum);
	}
	
	public MessageModel deleteVoteByVoteId(int voteId){
		
		MessageModel messageModel=new MessageModel();
		
		if(null==voteDao.queryVoteNameByVoteId(voteId)){
			messageModel.setMsg("待删除的记录不存在!");
			messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		int result=voteDao.deleteVote(voteId);
		if(result<1){
			messageModel.setMsg(ECConstant.OPTION_FAILED_MSG);
			messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
			return messageModel;
		}
		int result01=voteDao.deleteVoteOption(voteId);
		if(result01<1){
			messageModel.setMsg(ECConstant.OPTION_FAILED_MSG);
			messageModel.setResultCode(ECConstant.OPTION_FAILED_CODE);
		}
		return messageModel;
	}
	
}



