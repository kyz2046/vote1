package com.dao;

public class SqlParam {

	private String sql;
	
	private Object[] params;
	
	

	public SqlParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SqlParam(String sql, Object[] params) {
		super();
		this.sql = sql;
		this.params = params;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object... params) {
		this.params = params;
	}
	
	
}
