package com.simoncherry.lastletter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataBaseServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public DataBaseServlet() {
		super();
	}
	
	public void init() throws ServletException {
		// Put your code here
	}
	
	public void destroy() {
		super.destroy();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");// 乱码-->中文格式化
		PrintWriter out = response.getWriter();
		String result = request.getParameter("str");
		
		if(result !=null){
			String[] pieces = result.split(":");
			String command = pieces[0];
			String data = pieces[1];
			
			if("query".equalsIgnoreCase(command)){
				DataBaseUtil db = new DataBaseUtil();
				String rs = db.queryDB("name", data);
				if(rs.equals("") || rs.equals("null")){
					out.write("");
				}else{
					System.out.println("访问MySQL成功！");
					out.write(rs);
				}
				
			}else if("login".equalsIgnoreCase(command)){
				DataBaseUtil dbutil = new DataBaseUtil();
				String rs = dbutil.loginDB("name", data);
				if(rs.equals("")){
					out.write("");
				}else if(rs.equals("null")){
					out.write("null:null");
				}else{
					System.out.println("发送密码： " + rs);
					out.write("exist:" + rs);
				}
				
			}else if("signup".equalsIgnoreCase(command)){
				DataBaseUtil dbutil = new DataBaseUtil();
				String rs = dbutil.queryDB("name", data);
				if(rs.equals("")){
					System.out.println("创建新用户出错");
					out.write("error");
				}else if(rs.equals("null")){
					System.out.println("MySQL中不存在此记录");
					DataBaseUtil db = new DataBaseUtil();
					int res = db.insertDB(pieces[1], pieces[2]);
					if(res > 0){
						out.write("success");
						System.out.println("创建新用户 " + pieces[1] +" 成功");
					}else{
						out.write("failed");
						System.out.println("创建新用户失败");
					}
				}else{
					System.out.println("MySQL中存在此记录");
					out.write("exist");
				}
			}
		}else{
			System.out.println(result);
			System.out.println("空！");
		}
		
		out.flush();
		out.close();
	}
}