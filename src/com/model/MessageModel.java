package com.model;

import com.constant.ECConstant;





public class MessageModel {
	private Integer resultCode=ECConstant.OPTION_SUCCESS_CODE;
	
	private String msg=ECConstant.OPTION_SUCCESS_MSG;
	
	private Object result;
	
	
	

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
	

}
