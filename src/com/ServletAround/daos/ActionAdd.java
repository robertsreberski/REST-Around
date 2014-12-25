package com.ServletAround.daos;
import com.ServletAround.income.JSONFile;
import com.ServletAround.main.ServletTest;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;


public class ActionAdd {
	private static final Logger logger = Logger.getLogger(ServletTest.class.getName());
	public static Map<String, Object> Add(JSONFile jsonFile){
		String login = jsonFile.getLogin();
		String password = jsonFile.getPass();
		String friend = jsonFile.getFriend();
	
		Map<String, Object> map = new HashMap<String, Object>();
	
		try{
		 Class.forName("com.mysql.jdbc.Driver");

	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arounddb", "root", "KF=WQqTbV%6m");

	        Statement st = con.createStatement();
	
	        ResultSet iff = st.executeQuery("SELECT password FROM users WHERE login LIKE '" + login + "'");
            
            iff.next();
            
            	if(iff.getString(1).equals(password)){
            	//checking if this friend exists
            		ResultSet res = st.executeQuery("SELECT * FROM users WHERE login LIKE '"+ friend + "'");
            		
            	   
            	        if(res.isBeforeFirst()){
            	        	int req_id = res.getInt(1);
            	        	 ResultSet req = st.executeQuery("SELECT * FROM requests WHERE request_id='" + req_id + "'");
            	             req.next();
            	             int number = 0;
							for(int e = 2; e <= 41; e++){

            	                 if(req.getString(e) == null){
            	                     number = e;
            	                     e = 41;
            	                 }
            	             }
            	             number -= 1;
            	        	st.executeUpdate("UDPATE requests SET request" + number + "='" + login + "' WHERE requests_id='"+req_id+"'");
            	        	
//            	        	res.next();
//            	        	int requested_id = res.getInt(1);
//            	        	ResultSet reque = st.executeQuery("SELECT * FROM requests WHERE request_id='" + requested_id + "'");
//           	             	reque.next();
//           	             	int number2 = 0;
//							for(int e = 2; e <= 41; e++){
//
//           	                 if(reque.getString(e) == null){
//           	                     number2 = e;
//           	                     e = 41;
//           	                 }
//           	             }
//           	             number2 -= 1;
//           	             st.executeUpdate("UPDATE requested SET requested"+ number2 + "='"+ friend +"' WHERE requested_id='" + requested_id +"'");
//		
		
		
            	        }
            	        
            	map.put("valid", true);
            	con.close();
            	return map;
            	}else{
            	map.put("valid", "false");
            	con.close();
            	return map;
            	}
			}catch(Exception e){
	            logger.log(Level.WARNING, "An exception appeared while adding a friend", e);
	            map.put("valid", "false");
	            
	            return map;
		}
		
	
	

}}
