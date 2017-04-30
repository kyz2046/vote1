package com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.constant.ECConstant;
import com.model.MessageModel;
import com.utils.Md5Util;
import com.utils.PageInfo;
import com.utils.StringUtil;
import com.vo.Channel;
import com.vo.User;
import com.vo.Vote;
import com.vo.VoteOption;
import com.vo.VoteResult;

public class UserDao extends BaseDao{
	public User queryUserById(Integer id){
		String sql ="select id,name,pwd,man_id as manId,last_time as lastTime,add_right as addRight ,vote_time as voteTime"
				+ " from t_user where id =?";
		return (User)querySingleRow(sql, User.class, id);
		
	}
	public PageInfo<User> queryUserByParams (Integer id,String name,Integer pageNum){
		String sqlQuery = "select id ,name,pwd,man_id as manId, add_right as addRight ,vote_time as voteTime"
				+ " from t_user where 1=1";
		String sqlCount = "select count(1) from t_user where 1=1";
		StringBuffer sb = new StringBuffer();
		List params =new ArrayList<>();
		if(null!=id&&id>0){
			sb.append(" and id =?");
			params.add(id);
		}
		if(!StringUtil.isNullOrEmpty(name)){
			sb.append(" and name like concat('%',?,'%')");
			params.add(name);
		}
		
		PageInfo<User> pageInfo = new PageInfo<User>(sqlCount+sb.toString(),sqlQuery+sb.toString(),params.toArray(),pageNum,User.class);
		return pageInfo;
	}
	public int setAdmin(Integer id1,Integer id2){
		SqlParam sp1 =new SqlParam();
		SqlParam sp2 =new SqlParam();
		List<SqlParam> params =new  ArrayList<SqlParam>();
		
		String sql1 = " update t_user set man_id =true where id=?";
		sp1.setSql(sql1);
		sp1.setParams(id1);
		params.add(sp1);
		String sql2 =" update t_user set add_right =add_right-1 where id=?";
		
		
		sp2.setSql(sql2);
		sp2.setParams(id2);
		params.add(sp2);
		int result = executeUpdateBatch(params);
		return result;
	}
	
	
	
	
	
	/**
	 * 查询投票项的票数结果
	 */
	public VoteResult queryVoteResult(String voteId) {
		String sql="SELECT vote_id AS voteId,vote_name AS voteName,channel_id AS channelId,"
				+ " end_time AS endTime FROM t_vote "
				+ "	WHERE vote_id=?";
		Vote vote=(Vote)querySingleRow(sql, Vote.class, voteId);
		
		
		String sql1="SELECT vote_option_id AS voteOptionId,vote_id AS voteId,vote_option_name AS voteOptionName, "
				+ "	ticket_num AS ticketNum FROM t_vote_option WHERE vote_id=?";
		List<VoteOption> voteOption = queryRows(sql1, VoteOption.class, voteId);
		VoteResult voteResult=new VoteResult();
		voteResult.setVote(vote);
		voteResult.setVoteOptions(voteOption);
		
		return voteResult;
	}
	
	public int getVoteTime(Integer id) {
		String sql="SELECT vote_time FROM t_user WHERE id=? ";
		return (int) querySingleValue(sql, id);
	}

	/**
	 * 根据用户名查询用户记录
	 * @param name
	 * @return
	 */
	public User queryUserByUserName(String name){
		String sql="select id,name,pwd,man_id as manId,last_time as lastTime,add_right as addRight,"
				+ " vote_time as voteTime from t_user where name=? ";
		return (User) querySingleRow(sql, User.class, name);
	}
	
	/**
	 * 用户添加
	 */
	public int saveUser(User user){
		String sql="insert into t_user(name,pwd,man_id,last_time) values(?,?,?,?)";
		return executeUpdate(sql, user.getName(),user.getPwd(),0,new Date());
				
	}
	//频道添加
	public int  addChannel(String channelName){
		String sql="insert into t_channel(channel_name) values(?)";
		return executeUpdate(sql, channelName);
		
	}
	//通过频道名称查询频道id
	public int queryChannelIdByChannelName(String channelName){
		String sql="select channel_id as channelId from t_channel where channel_name=?";
			Channel c=(Channel) querySingleRow(sql, Channel.class,channelName);
			if(null==c){
				return 1;
			}
		return  0;
	}

	/**
	 * 添加投票
	 */
	public MessageModel addTicket(Integer Id,String voteName) {
			MessageModel m=new MessageModel();
		
		/**
		 * 先查询vote表的ID 通过vote_option_name
		 */
		String query="SELECT vote_id FROM t_vote_option WHERE vote_option_name=?";
		Integer voteId = (Integer) querySingleValue(query, voteName);
		
		/**
		 * 查询投票结束时间
		 */
		String queryEndTime="SELECT count(1) FROM t_vote WHERE vote_id=? AND end_time>? ";
		long endTime = (long) querySingleValue(queryEndTime, voteId,new Date());
		
		/**
		 * 结束时间返回结果大于0 表示可以继续投票操作，结束时间返回结果小于1表示投票已经结束
		 */
		if(endTime<1){
			m.setMsg("该投票项目不存在或已经结束投票");
			m.setResultCode(ECConstant.OPTION_FAILED_CODE);
			return m;
		}
		
		/**
		 * 进行投票
		 */
		int result = addTickets(Id,voteName);
		
		if(result<1){
			m.setMsg("投票失败");
			m.setResultCode(ECConstant.OPTION_FAILED_CODE);
			return m;
		}
		m.setMsg("投票成功！");
		return m;
		
	}
	
	
	/**
	 * 最终投票
	 */
	private int addTickets(Integer Id,String voteName){
		
		SqlParam sp=new SqlParam();
		SqlParam sp1=new SqlParam();
		List<SqlParam> list=new ArrayList<SqlParam>();
		
		/**
		 * 进行投票操作
		 */
		String addTicket="UPDATE t_vote_option SET ticket_num = ticket_num+1 WHERE vote_option_name=?";
		sp.setSql(addTicket);
		sp.setParams(voteName);
		list.add(sp);
		
		/**
		  * 投票用户的last_time更新
		  */
		 String updateUser="UPDATE t_user SET last_time=? WHERE id=?";
		 sp1.setSql(updateUser);
		 sp1.setParams(new Date(),Id);
		 list.add(sp1);
		 int result = executeUpdateBatch(list);
		return result;
	}

}

	

