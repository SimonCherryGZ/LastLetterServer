package com.simoncherry.lastletter;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;

public class GsonUtil{
	
	public String getGsonObject(ResultSet rs){
		UserBean bean = new UserBean();
		Gson gson = new Gson();
		
		try {
			bean.setId(rs.getString("id"));
			bean.setUserName(rs.getString("name"));
			bean.setUserPassword(rs.getString("password"));
			bean.setUserLetter(rs.getString("letter"));
			bean.setTotalLost(rs.getInt("lost"));
			bean.setCountdown(rs.getInt("countdown"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return gson.toJson(bean);
	}
}