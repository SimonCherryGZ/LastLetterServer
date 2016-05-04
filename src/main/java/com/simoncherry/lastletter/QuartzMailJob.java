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


public class QuartzMailJob implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		QuartzSendMail();
	}
	
	private void QuartzSendMail(){
		System.out.println("SendMail start." + new Date().toString());
		
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
				int countdown = rs.getInt("countdown");
				int lost = rs.getInt("lost");
				if(countdown == 0 && lost == 0){
					String username = rs.getString("name");
					String password = rs.getString("password");
					
					MailUtil mail = new MailUtil(username, password);
					try {
						mail.sendMail();
						System.out.println("send mail by " + username);
						
						String updateSql = "update user set lost=1 where name='"
								+ username + "'";
						PreparedStatement ps = conn.prepareStatement(updateSql);
						int res = ps.executeUpdate();
						if(res > 0){
							System.out.println("修改数据成功");
						}else{
							System.out.println("修改数据失败");
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
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
		
		System.out.println("SendMail end." + new Date().toString());
	}
}