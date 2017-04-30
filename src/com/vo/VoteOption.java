package com.vo;

public class VoteOption {
	private Integer voteOptionId;
	private Integer voteId;
	private String voteOptionName;
	private Integer ticketNum;
	public Integer getVoteOptionId() {
		return voteOptionId;
	}
	public void setVoteOptionId(Integer voteOptionId) {
		this.voteOptionId = voteOptionId;
	}
	public Integer getVoteId() {
		return voteId;
	}
	public void setVoteId(Integer voteId) {
		this.voteId = voteId;
	}
	public String getVoteOptionName() {
		return voteOptionName;
	}
	public void setVoteOptionName(String voteOptionName) {
		this.voteOptionName = voteOptionName;
	}
	public Integer getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(Integer ticketNum) {
		this.ticketNum = ticketNum;
	}
}
