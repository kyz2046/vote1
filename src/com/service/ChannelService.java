package com.service;

import java.util.List;

import com.dao.ChannelDao;
import com.vo.Channel;

public class ChannelService {
	ChannelDao channelDao = new  ChannelDao();
	public List<Channel> queryChannelInfo(){
		return channelDao.queryChannelInfo();
	}
}
