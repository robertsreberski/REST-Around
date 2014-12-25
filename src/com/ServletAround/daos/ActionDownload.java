package com.ServletAround.daos;
import com.ServletAround.income.JSONFile;
import com.ServletAround.main.ServletTest;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

import org.json.JSONArray;
import org.json.JSONObject;
public class ActionDownload {
	private static final Logger logger = Logger.getLogger(ServletTest.class.getName());
	public static Map<String, Object> Download(JSONFile jsonFile){
		String login = jsonFile.getLogin();
		String password = jsonFile.getPass();
		
	
		Map<String, Object> map = new HashMap<String, Object>();
	
		try{
		 Class.forName("com.mysql.jdbc.Driver");

	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arounddb", "root", "KF=WQqTbV%6m");

	        Statement st = con.createStatement();
	
	        ResultSet iff = st.executeQuery("SELECT * FROM users WHERE login LIKE '" + login + "'");
            
            iff.next();
            
            	if(iff.getString(4).equals(password)){
            		int user_id = iff.getInt(1);
            		ResultSet logins = st.executeQuery("SELECT * FROM photos WHERE photos_id='" + user_id + "'");
            		logins.next();
            		int number = 0;
					for(int i = 2; i <= 41; i++){

	                    if(logins.getString(i) == null){
	                        number = i;
	                        i = 41;
	                    }
	                }
	                number -= 1;
	                JSONArray ja = new JSONArray();
            		for(int e = 2; e <=number; e++){
            			String first = logins.getString(e);
            			ResultSet photos = st.executeQuery("SELECT photo FROM users WHERE login LIKE '" + first + "'");
            			photos.next();
            			String photography = photos.getString(1);
            			JSONObject jo = new JSONObject();
            			jo.put("login", first);
            			jo.put("photo", photography);
            			ja.put(jo);
            			st.executeUpdate("UPDATE photos SET photo"+ (e-1) + "=NULL WHERE photos_id='" + user_id+"'");
            		}
            		map.put("valid", true);
            		map.put("photo_list", ja);
            		con.close();
            		
            	
            	
            	}else{
            	map.put("valid", false);
            	con.close();
            	}
            	return map;
}catch(Exception e){
    logger.log(Level.WARNING, "An exception appeared while downloading a photo", e);
    map.put("valid", "false");
    
    return map;
}
		
	}
}