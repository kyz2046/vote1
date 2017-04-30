package com.dao;

import java.util.List;

import com.vo.VoteOption;

public class VoteOptionDao extends BaseDao<VoteOption> {
	//查询
	public List<VoteOption> queryVoteOptionByVid(Integer vid){
		String sql="select vote_potion_id as voteOptionId, vote_option_name as voteOptionName,ticket_num as tickNum from t_vote_option where vote_id=?";
		return queryRows(sql,VoteOption.class,vid);
	}
	//添加
	public int addVoteOption(VoteOption voteOption){
		String sql="insert into t_vote_option(vote_id,vote_option_name,ticket_num) values(?,?,0)";
		return executeUpdate(sql,voteOption.getVoteId(),voteOption.getVoteOptionName()) ;
	}
	public VoteOption queryVoteOptionByVidName(String voteOptionName){
		String sql="select vote_option_id as voteOptionId,vote_id as voteId, vote_option_name as voteOptionName from t_vote_option where "
				+"vote_option_name=?";
		
		return querySingleRow(sql,VoteOption.class,voteOptionName);
		}
}
