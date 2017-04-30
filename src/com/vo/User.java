package com.vo;

import java.util.Date;

public class User {
	private Integer id;
	private String name;
	private String pwd;
	private Boolean manId;
	private Date lastTime;
	private int addRight;
	private int voteTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Boolean getManId() {
		return manId;
	}
	public void setManId(Boolean manId) {
		this.manId = manId;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public int getAddRight() {
		return addRight;
	}
	public void setAddRight(int addRight) {
		this.addRight = addRight;
	}
	public int getVoteTime() {
		return voteTime;
	}
	public void setVoteTime(int voteTime) {
		this.voteTime = voteTime;
	}
	
	
}
