package com.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.utils.MyDbUtil;



/**
 * 
 * @author lp
 * jdbc 封装
 *
 */
public class BaseDao<T> {


	//  select count(1) from t_user 
	/**
	 * 查询单行单列  适用于统计
	 * @param sql
	 * @param params
	 * @return
	 */
	public Object querySingleValue(String sql,Object... params){
		Connection connection=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Object object=null;
		try {
			connection=MyDbUtil.getConnection();
			ps=connection.prepareStatement(sql);
			// 如果参数存在
			if(null!=params&&params.length>0){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
			}
			rs= ps.executeQuery();
			if(rs.next()){
				object=rs.getObject(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MyDbUtil.close(rs, ps, connection);
		}
		return object;
	}



	/**
	 * 执行更新方法封装
	 * insert update delete  ddl 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int executeUpdate(String sql,Object... params){
		Connection connection=null;
		PreparedStatement ps=null;
		int result=0;
		try {
			connection=MyDbUtil.getConnection();
			connection.setAutoCommit(false);
			ps=connection.prepareStatement(sql);
			// 如果参数存在
			if(null!=params&&params.length>0){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
			}
			result=ps.executeUpdate();
		} catch (SQLException e) {
			result=0;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			MyDbUtil.close(ps, connection);
		}
		return result;
	}


	// 查询多行记录  查询单行记录 List<User>  user.class
	// sql  params Class
	public List<T>  queryRows(String sql,Class<T> clazz,Object... params){
		Connection connection=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<T> result=null;
		try {
			connection=MyDbUtil.getConnection();
			ps=connection.prepareStatement(sql);
			// 如果参数存在
			if(null!=params&&params.length>0){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
			}
			rs=ps.executeQuery();
			// 结果集元数据  结果集返回的列名 列的数量
			ResultSetMetaData rsm=rs.getMetaData();
			result=new ArrayList<T>();
			//  select id,user_name as userName,user_pwd as userPwd from t_user
			while(rs.next()){
				T obj=clazz.newInstance();
				for(int j=0;j<rsm.getColumnCount();j++){
					String columnName=rsm.getColumnLabel(j+1);// 获取字段名
					//clazz.getDeclaredField(name)
					Field field=getField(clazz,columnName);
					
					field.setAccessible(true);
					field.set(obj, rs.getObject(j+1));				
				}
				result.add(obj);
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}finally{
			MyDbUtil.close(rs, ps, connection);
		}		
		return (null==result||result.size()==0)?null:result;
	}
	
	/**
	 * 返回单行记录
	 * @param sql
	 * @param clazz
	 * @param params
	 * @return
	 */
	public T querySingleRow(String sql,Class<T> clazz,Object... params){
		List<T> result=queryRows(sql, clazz, params);
		return null==result?null:result.get(0);
	}
	
	
	



	//  获取字段名 包括继承情况 user id
	private Field getField(Class<T> clazz, String columnName) {
		for(Class<T> cc=clazz;cc!=Object.class;cc=(Class<T>) cc.getSuperclass()){
			try {
				return  cc.getDeclaredField(columnName);
			} catch (Exception e) {
				// 字段找不到  抛出异常   循环向上查找父类当抛出异常时
			}
		}
		
		return null;
	}

	
	
	//  多条sql 事物统一控制  
	@SuppressWarnings("resource")
	public int executeUpdateBatch(List<SqlParam> list){
		
		Connection connection=null;
		PreparedStatement ps=null;
		int result=0;
		try {
			connection=MyDbUtil.getConnection();
			connection.setAutoCommit(false);	
			if(null!=list&&list.size()>0){
				for(SqlParam sqlParam:list){
					String sql=sqlParam.getSql();
					Object[] params=sqlParam.getParams();
					ps=connection.prepareStatement(sql);
					if(null!=params&&params.length>0){
						for(int i=0;i<params.length;i++){
							ps.setObject(i+1, params[i]);
						}
					}				
					result=ps.executeUpdate();
					if(result<1){
						throw new Exception();
					}
				}
				
			}	
		} catch (Exception e) {
			result=0;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			MyDbUtil.close(ps, connection);
		}
		return result;
	}



}
