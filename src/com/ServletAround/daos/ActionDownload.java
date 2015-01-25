package com.ServletAround.daos;
import com.ServletAround.income.JSONFile;
import com.ServletAround.main.ServletTest;
import com.ServletAround.utils.BCrypt;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class ActionDownload {
	private static final Logger logger = Logger.getLogger(ServletTest.class.getName());
	public static JSONObject Download(JSONFile jsonFile){
		String login = jsonFile.getLogin();
		String password = jsonFile.getPass();
		
	
		JSONObject map = new JSONObject();
	
		try{
		 Class.forName("com.mysql.jdbc.Driver");

		 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arounddb", "dataDisposal", "hwVvfcYCJMMHH4yp");

	        Statement st = con.createStatement();
	
	        ResultSet iff = st.executeQuery("SELECT * FROM users WHERE login LIKE '" + login + "'");
            
            iff.next();
            
            	if(BCrypt.checkpw(password, iff.getString(4))){
            		int user_id = iff.getInt(1);
            		ResultSet logins = st.executeQuery("SELECT * FROM photos WHERE photo_id='" + user_id + "'");
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
            			System.out.println("UPDATE photos SET photo"+ (e-1) + "=NULL WHERE photo_id='" + user_id+"'");
            			st.executeUpdate("UPDATE photos SET photo"+ (e-1) + "=NULL WHERE photo_id='" + user_id+"'");
            		}
            		map.put("valid", true);
            		map.put("photo_list", ja);
            		con.close();
            		System.out.println(map);
            	
            	
            	}else{
            	map.put("valid", false);
            	con.close();
            	System.out.println(map);
            	}
            	return map;
}catch(Exception e){
    logger.log(Level.WARNING, "An exception appeared while downloading a photo", e);
    
		try {
			map.put("valid", false);
		} catch (JSONException e1) {
			
		}
	
	
    
    return map;
}
		
	}
}