package com.dao;

import java.util.List;

import com.vo.Channel;

public class ChannelDao extends BaseDao<Channel> {
	public List<Channel> queryChannelInfo(){
		String sql="select channel_id as channelId ,channel_name as channelName from t_channel ";
		return queryRows(sql, Channel.class, null);
	}
}
