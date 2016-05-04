package com.simoncherry.lastletter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzCountDownJob implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		PrintUserDataBases();
	}
	
	private void PrintUserDataBases(){
		System.out.println("Query start." + new Date().toString());
				
		InitialContext context = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc/mysql");  
			conn = dataSource.getConnection();
			stmt =conn.createStatement();
			
			String strSql = "select * from user";
			rs =stmt.executeQuery(strSql);
			while(rs.next()){
				String username = rs.getString("name");
				String password = rs.getString("password");
				int countdown = rs.getInt("countdown");
				
				System.out.println("id: " + rs.getString("id") 
				+ "  name: " + username 
				+ " countdown: " + countdown);

				if(countdown > 0) countdown-=1;
				
				String updateSql = "update user set countdown=" 
						+ String.valueOf(countdown) + " where name='" 
						+ username + "'";
				PreparedStatement ps = conn.prepareStatement(updateSql);
				int res = ps.executeUpdate();
				if(res > 0){
					System.out.println("修改数据成功");
				}else{
					System.out.println("修改数据失败");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				stmt.close();
				conn.close();
				context.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Query end." + new Date().toString());
	}
}