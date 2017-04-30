package com.dao;

import java.util.ArrayList;
import java.util.List;

import com.utils.PageInfo;
import com.utils.StringUtil;
import com.vo.Channel;
import com.vo.Vote;
import com.vo.VoteOption;

public class VoteDao extends BaseDao<Vote> {
	public PageInfo<Vote> queryVoteByParams (String voteId,String voteName,String channelId,Integer pageNum){
		String sqlQuery = "select vote_id as voteId,vote_name as voteName,channel_id as channelId,end_time as endTime"
				+ " from t_vote where 1=1";
		String sqlCount = "select count(1) from t_vote where 1=1";
		StringBuffer sb = new StringBuffer();
		List params =new ArrayList<>();
		if(!StringUtil.isNullOrEmpty(voteId)){
			sb.append(" and vote_id =?");
			params.add(voteId);
		}
		if(!StringUtil.isNullOrEmpty(voteName)){
			sb.append(" and vote_name like concat('%',?,'%')");
			params.add(voteName);
		}
		if(!StringUtil.isNullOrEmpty(channelId)){
			sb.append(" and channel_id=?");
			params.add(channelId);
		}
		PageInfo<Vote> pageInfo = new PageInfo<Vote>(sqlCount+sb.toString(),sqlQuery+sb.toString(),params.toArray(),pageNum,Vote.class);
		return pageInfo;
	}
	public int addVote(String voteName,String channelId ,String endTime ){
		String sql="insert into t_vote(vote_name,channel_id,end_time) values (?,?,?)";
		return executeUpdate(sql, voteName,channelId,endTime);
	}
	/*public Vote queryVoteByVoteId(int voteId){
		String sql="select vote_id as voteId ,vote_name as voteName,channel_id as channelId"
				+"end_time as endTime ,vote_option_name as voteOptionName,vote_option_id as voteOptionId"
				+"ticket_num as ticketNum from t_vote tv LEFT JOIN t_vote_option tvp on tvp.vote_option_id=tv.vote_id"
				+"where tv.vote_id=?";
		return  querySingleRow(sql, Vote.class,voteId);
	}*/
	public  String queryVoteNameByVoteId(int voteId){
		String sql="select vote_name from t_vote where vote_id=?";
		return (String) querySingleValue(sql, voteId);
	}
	public String queryVoteOptionNameByVoteId(int voteId){
		String sql="select vote_option_name from t_vote_option where vote_id=?";
		return (String) querySingleValue(sql, voteId);
	}
	public int addVoteOption(VoteOption voteOption){
		String sql="insert  into t_vote_option (vote_id,vote_option_name) values (?,?) ";
		return executeUpdate(sql, voteOption.getVoteId(),voteOption.getVoteOptionName());
	}
	public int deleteVote(int voteId){
		String sql="delete from t_vote where vote_id=?";
		return executeUpdate(sql, voteId);
	}
	public int deleteVoteOption(int voteOptionId){
		String sql="delete from t_vote_option where vote_id=?";
		return executeUpdate(sql, voteOptionId);
	}
	public PageInfo<Channel> queryChannelByParams(int channelId ,int pageNum){
		String sqlQuery="select channel_id as channelId ,channel_name as channelName from t_channel where channel_id=?";
		String sqlCount="select  count(1)  from t_channel where channel_id=?";
		 List params=new ArrayList<>();
		  params.add(channelId);
		  PageInfo<Channel> pageInfo=new PageInfo<Channel>(sqlCount, sqlQuery, params.toArray(), pageNum, Channel.class);
		  return pageInfo;
		
	}
}
