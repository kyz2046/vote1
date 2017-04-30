package com.dao;


public class ManagerDao<T> extends BaseDao<T>{

	/**
	 * 设置投票间隔时间
	 */
	public int setUserVoteTime(String id,String voteTime) {
		String sql="UPDATE t_user SET vote_time=? WHERE id=?";
		return executeUpdate(sql, voteTime,id);
	}

}
