package com.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MyDbUtil {
	static Properties p=new Properties();

	//  加载类时  读取properties  到 Properties 中
	static{
		try {
			p.load(MyDbUtil.class.getClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对外提供连接获取
	 * @return
	 */
	public static Connection getConnection(){
		Connection connection=null;
		try {
			Class.forName(p.getProperty("jdbc.driver"));
			connection=DriverManager.getConnection(p.getProperty("jdbc.url"),
					p.getProperty("jdbc.user"),p.getProperty("jdbc.password") );
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}


	public static  void  close(ResultSet rs,PreparedStatement ps,Connection connection){
		// 释放资源
		try {
			if(null!=rs){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if(null!=ps){
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(null!=connection){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static  void  close(PreparedStatement ps,Connection connection){
		// 释放资源
		

		try {
			if(null!=ps){
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(null!=connection){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	






}
