package com.simoncherry.lastletter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

public class LetterServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public LetterServlet() {
		super();
	}
	
	public void init() throws ServletException {
		// Put your code here
	}
	
	public void destroy() {
		super.destroy();
		// Put your code here
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/json;charset=utf-8");// 乱码-->中文格式化
		String reqMessage, respMessage;
		
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(  
                    request.getInputStream(), "UTF-8"));  
            StringBuffer sb = new StringBuffer("");  
            String temp;  
            while ((temp = br.readLine()) != null) {  
                sb.append(temp);  
            }  
            br.close();  
            reqMessage = sb.toString();  
            System.out.println("请求报文:" + reqMessage);
            
            Gson gson = new Gson();
            UserBean bean = gson.fromJson(reqMessage, UserBean.class);
            String username = bean.getUserName();
            String letter = bean.getUserLetter();
            String strSql = "update user set letter='" + letter + "' where name='" + username + "'";
            
            Connection conn = null;
			PreparedStatement ps = null;
            try{
            	InitialContext context = new InitialContext();  
				DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc/mysql");  
				conn = dataSource.getConnection();
				
				ps=conn.prepareStatement(strSql);
				int res = ps.executeUpdate();
				if(res > 0){
					System.out.println("修改数据成功");
				}else{
					System.out.println("修改数据失败");
				}
            	
            }catch(Exception e) {  
            	System.out.println("修改数据出错");
                e.printStackTrace();  
            }
            
			
		}catch (Exception e) {  
            e.printStackTrace();  
        }
	}
}