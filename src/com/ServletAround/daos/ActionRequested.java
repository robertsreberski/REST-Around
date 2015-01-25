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
import com.ServletAround.utils.BCrypt;

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
			            int userAJDI = res.getInt(1);
			            if(BCrypt.checkpw(password, res.getString(4))){
				        	res.close();
				        		ResultSet kupa = st.executeQuery("SELECT * FROM users WHERE login LIKE '" + friend + "'");
				        		kupa.next();
				        		int user_id = kupa.getInt(1);
				        		String login2=kupa.getString(3);
				        		String status2 =kupa.getString(7);
				        		int activity2 = kupa.getInt(8);
				        		Double x2 = kupa.getDouble(5);
				        		Double y2 = kupa.getDouble(6);
				        		String photo2 = kupa.getString(9);
				        		int score2 = kupa.getInt(11);
				        		kupa.close();
				        		ResultSet fList = st.executeQuery("SELECT * FROM requests WHERE requests_id='" + userAJDI + "'");

				                fList.next();

				            
				                System.out.println(friend);
				                
				                int number = 0;
								for(int i = 2; i <= 41; i++){
									String comparer = fList.getString(i);
	            	            	 if (comparer != null){
	            	                 

				                    if(comparer
				                    					                    		
				                    		.equals(friend)){
				                        number = i;
				                        i = 41;
				                    }
				                }}
				                number-=1;
				                fList.close();
				                System.out.println(number);
				        		
				        		st.executeUpdate("UPDATE requests SET request" + number +"=NULL WHERE requests_id='" + userAJDI + "'");
				        		ResultSet minus = st.executeQuery("SELECT * FROM requests WHERE requests_id='" + userAJDI + "'");
				        		minus.next();
				        		for (int c = number; c <=40; c++){
				        			String comparer = minus.getString(c+2);
				        			if(comparer != null){
				        				st.executeUpdate("UPDATE requests SET request" + c + "='" + comparer + "' WHERE requests_id='" + userAJDI + "'");
				        				st.executeUpdate("UPDATE requests SET request" + (c+1) + "=NULL WHERE requests_id='" + userAJDI + "'");
				        			}else{
				        				c=40;
				        			}
				        			
				        		}
				        		minus.close();
				        		if(valid == true){
				        			
				        		map.put("login", login2);
				        		map.put("status", status2);
				        		map.put("activity", activity2);
				        		map.put("x", x2);
				        		map.put("y", y2);
				        		map.put("photo", photo2);
				        		map.put("score", score2);
				        			//first
				        			ResultSet e1 = st.executeQuery("SELECT * FROM friends WHERE friends_id='" + userAJDI + "'");
				        			e1.next();
				        			for(int j = 2; j <= 41; j++){

					                    if(e1.getString(j) == null){
					                        number = j;
					                        j = 41;
					                    }
					                }
				        			number -=1;
				        			
				        			st.executeUpdate("UPDATE friends SET friend_" + number + "='" + friend + "' WHERE friends_id='" + userAJDI +"'");
				        			
				        			//second
				        			
				        			ResultSet f2 = st.executeQuery("SELECT users_id FROM users WHERE login LIKE '" + friend + "'");
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
				        			
				        			st.executeUpdate("UPDATE friends SET friend_" + number2 + "='" + login + "' WHERE friends_id='" + user_friend +"'");
				        			
				        			
				        			//photostuff
				        			
				        			ResultSet prob = st.executeQuery("SELECT * FROM friends WHERE friends_id='" + userAJDI + "'");
				            		prob.next();
				            		int number5 = 0;
									for(int e = 2; e <= 41; e++){

		            	                 if(prob.getString(e) == null){
		            	                     number5 = e;
		            	                     e = 41;
		            	                 }
		            	             }
		            	             number5 -= 2;
		            	             
		            	             for(int i =1; i <= number5; i++){
		            	            	
		            	            	 ResultSet okurczaki = st.executeQuery("SELECT users_id FROM users WHERE login LIKE '" + friend + "'");
		            	            	 okurczaki.next();
		            	            	 int jprdl = okurczaki.getInt(1);
		            	            	 okurczaki.close();
		            	            	 ResultSet ileichjeszcze = st.executeQuery("SELECT * FROM photos WHERE photo_id='" + jprdl +"'");
		            	            	 ileichjeszcze.next();
		            	            	 int number6 = 0;
		            	            
		 								for(int f = 2; f <= 41; f++){

		 	            	                 if(ileichjeszcze.getString(f) == null){
		 	            	                     number6 = f;
		 	            	                     f = 41;
		 	            	                 }
		 	            	             }
		 	            	             number6 -= 1;
		 	            	             ileichjeszcze.close();
		 	            	             st.executeUpdate("UPDATE photos SET photo" + number6 + "='" + login + "' WHERE photo_id='" + jprdl+"'");
		            	             }
					        
				        			
				        			
				        			
				        			
				        			
				        			
				        			
				        			
				        			
				        			
				        			map.put("valid", true);
				        		}else{
				        			map.put("valid", false);
				        			map.put("exception", "jprdl");
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
