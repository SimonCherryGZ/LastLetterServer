package com.simoncherry.lastletter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DataBaseUtil{
	
	public String queryDB(String type, String data){
		InitialContext context = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String result = "";
		
		try{
			context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc/mysql");  
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			String strSql = "select * from user where " 
					+ type + " like" + " '" + data + "'";
			rs = stmt.executeQuery(strSql);
			
			if(rs.next()){
				System.out.println("MySQL中存在此记录");
				GsonUtil gson = new GsonUtil();
				result = gson.getGsonObject(rs);
			}else{
				System.out.println("MySQL中不存在此记录");
				result = "null";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			try {
				rs.close();
				stmt.close();
				conn.close();
				context.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public String loginDB(String type, String data){
		InitialContext context = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String result = "";
		
		try{
			context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc/mysql");  
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			String strSql = "select * from user where " 
					+ type + " like" + " '" + data + "'";
			rs = stmt.executeQuery(strSql);
			
			if(rs.next()){
				System.out.println("MySQL中存在此记录");
				result = rs.getString("password");
				
				DataBaseUtil db = new DataBaseUtil();
				int res = db.updateDB(data);
				if(res > 0){
					System.out.println("修改数据成功");
				}else{
					System.out.println("修改数据失败");
				}
			}else{
				System.out.println("MySQL中不存在此记录");
				result = "null";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			try {
				rs.close();
				stmt.close();
				conn.close();
				context.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public int updateDB(String data){
		InitialContext context = null;
		Connection conn = null;
		PreparedStatement ps = null;
		int res = 0;
		
		try{
			context = new InitialContext();  
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc/mysql");  
			conn = dataSource.getConnection();
			String updateSql = "update user set countdown=10" 
					+ " where name='" + data + "'";
			ps = conn.prepareStatement(updateSql);
			res = ps.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			try {
				ps.close();
				conn.close();
				context.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
	
	public int insertDB(String username, String password){
		InitialContext context = null;
		Connection conn = null;
		PreparedStatement ps = null;
		int res = 0;
		
		try{
			context = new InitialContext();  
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc/mysql");  
			conn = dataSource.getConnection();
			String updateSql = "insert into user (name, password, letter, lost, countdown) value (?,?,?,?,?)";
			ps = conn.prepareStatement(updateSql);
			ps.setString(1, username); 
			ps.setString(2, password); 
			ps.setString(3, " ");
			ps.setInt(4, 0);
			ps.setInt(5, 10);
			res = ps.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			try {
				ps.close();
				conn.close();
				context.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
}