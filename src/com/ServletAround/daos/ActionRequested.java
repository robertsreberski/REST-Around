package com.ServletAround.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ServletAround.income.JSONFile;
import com.ServletAround.main.ServletTest;

public class ActionRequested {
	private static final Logger logger = Logger.getLogger(ServletTest.class.getName());
	public static Map<String, Object> Requested(JSONFile jsonFile){
		String login = jsonFile.getLogin();
		String password = jsonFile.getPass();
		String friend = jsonFile.getFriend();
		boolean valid = jsonFile.isValid();
		Map<String, Object> map = new HashMap<String, Object>();
		 try{			
				    //  Establishing connection
				        Class.forName("com.mysql.jdbc.Driver");

					    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arounddb", "dataDisposal", "hwVvfcYCJMMHH4yp");

				        Statement st = con.createStatement();

				        ResultSet res = st.executeQuery("SELECT * FROM users WHERE login LIKE '" + login + "'");
			            
			            res.next();
			            
			            if(res.getString(4).equals(password)){
				        	
				        		
				        	
				        		
				        		
				        		map.put("login", res.getString(3));
				        		map.put("status", res.getString(7));
				        		map.put("activity", res.getInt(8));
				        		map.put("x", res.getDouble(5));
				        		map.put("y", res.getDouble(6));
				        		int user_id = res.getInt(1);
				        		ResultSet fList = st.executeQuery("SELECT * FROM requests WHERE requests_id=" + user_id );

				                fList.next();

				            

				                int number = 0;
								for(int i = 2; i <= 41; i++){

				                    if(fList.getString(i) == friend){
				                        number = i;
				                        i = 41;
				                    }
				                }
				                number -= 1;
				        		
				        		st.executeUpdate("UPDATE requests SET request" + number +"=NULL WHERE requests_id='" + user_id + "'");
				        			
				        		if(valid = true){
				        			
				        			//first
				        			ResultSet e1 = st.executeQuery("SELECT * FROM friends WHERE friends_id='" + user_id + "'");
				        			e1.next();
				        			for(int j = 2; j <= 41; j++){

					                    if(e1.getString(j) == null){
					                        number = j;
					                        j = 41;
					                    }
					                }
				        			number -=1;
				        			
				        			st.executeUpdate("UDPATE friends SET friend_" + number + "='" + friend + "' WHERE friends_id='" + user_id +"'");
				        			
				        			//second
				        			
				        			ResultSet f2 = st.executeQuery("SELECT user_id FROM users WHERE login LIKE '" + friend + "'");
				        			f2.next();
				        			String user_friend = f2.getString(1);
				        			ResultSet e2 = st.executeQuery("SELECT * FROM friends WHERE friends_id='" + user_friend + "'");
				        			e2.next();
				        			int number2 = 0;
									for(int k = 2; k <= 41; k++){

					                    if(e2.getString(k) == null){
					                        number2 = k;
					                        k = 41;
					                    }
					                }
				        			number2 -=1;
				        			
				        			st.executeUpdate("UDPATE friends SET friend_" + number2 + "='" + login + "' WHERE friends_id='" + user_friend +"'");
				        			
				        			map.put("valid", true);
				        		}else{
				        			map.put("valid", false);
				        		}
				        		con.close();
				        		return map;
				        }else{
				        	map.put("valid", false);
				        	con.close();
				        	return map;
				        }
			 
			 
			 
		 }catch(Exception e){
	            logger.log(Level.WARNING, "An exception appeared while adding a friend", e);
	            map.put("valid", "false");
	            
	            return map;}
	}
}
