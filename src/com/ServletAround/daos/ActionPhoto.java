package com.ServletAround.daos;
import com.ServletAround.income.JSONFile;
import com.ServletAround.main.ServletTest;
import com.ServletAround.utils.BCrypt;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;
public class ActionPhoto {
	private static final Logger logger = Logger.getLogger(ServletTest.class.getName());
	public static Map<String, Object> Photo(JSONFile jsonFile){
		String login = jsonFile.getLogin();
		String password = jsonFile.getPass();
		String photo = jsonFile.getPhoto();
		
		Map<String, Object> map = new HashMap<String, Object>();
		 try{			
				    //  Establishing connection
				        Class.forName("com.mysql.jdbc.Driver");

					    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arounddb", "dataDisposal", "hwVvfcYCJMMHH4yp");

				        Statement st = con.createStatement();
				        
				        ResultSet iff = st.executeQuery("SELECT * FROM users WHERE login LIKE '" + login + "'");
			            
			            iff.next();
			            
			            	if(BCrypt.checkpw(password, iff.getString(4))){
			            		int id = iff.getInt(1);
			            		st.executeUpdate("UPDATE users SET photo='" + photo + "' WHERE login LIKE '" + login + "'");
			            		ResultSet prob = st.executeQuery("SELECT * FROM friends WHERE friends_id='" + id + "'");
			            		prob.next();
			            		int number = 0;
								for(int e = 2; e <= 41; e++){

	            	                 if(prob.getString(e) == null){
	            	                     number = e;
	            	                     e = 41;
	            	                 }
	            	             }
	            	             number -= 2;
	            	             
	            	             for(int i =1; i <= number; i++){
	            	            	 ResultSet okurde = st.executeQuery("SELECT friend_" + i + " FROM friends WHERE friends_id='" + id + "'");
	            	            	 okurde.next();
	            	            	 String ups = okurde.getString(1);
	            	            	 okurde.close();
	            	            	 ResultSet okurczaki = st.executeQuery("SELECT users_id FROM users WHERE login LIKE '" + ups + "'");
	            	            	 okurczaki.next();
	            	            	 int jprdl = okurczaki.getInt(1);
	            	            	 okurczaki.close();
	            	            	 ResultSet ileichjeszcze = st.executeQuery("SELECT * FROM photos WHERE photo_id='" + jprdl +"'");
	            	            	 ileichjeszcze.next();
	            	            	 int number2 = 0;
	            	            
	 								for(int f = 2; f <= 41; f++){

	 	            	                 if(ileichjeszcze.getString(f) == null){
	 	            	                     number2 = f;
	 	            	                     f = 41;
	 	            	                 }
	 	            	             }
	 	            	             number2 -= 1;
	 	            	             ileichjeszcze.close();
	 	            	             st.executeUpdate("UPDATE photos SET photo" + number2 + "='" + login + "' WHERE photo_id='" + jprdl+"'");
	            	             }
				        
				        
				        
	            	             map.put("valid", true);
	            	             con.close();
				        
			            	}else{
			            		map.put("valid", false);
			            		con.close();
			            	}
				        return map;
		 }catch(Exception e) {

				logger.log(Level.WARNING, " An exception appeared, problem with updating the photo", e);

				map.put("valid", false);
			    return map;

			}
	}
}
