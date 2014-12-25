package com.ServletAround.daos;


import com.ServletAround.income.JSONFile;
import com.ServletAround.main.ServletTest;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionRegister {
private static final Logger logger = Logger.getLogger(ServletTest.class.getName());

	public static  Map<String, Object> Register (JSONFile jsonFile){
		

			String login = jsonFile.getLogin();
			String password = jsonFile.getPass();
			Double x = jsonFile.getX();
			Double y = jsonFile.getY();
			String status = jsonFile.getStatus();
			int activity = jsonFile.getActivity();
			String photo = jsonFile.getPhoto();
			String query = "INSERT INTO users (friends_id, login, password, x, y, status, activity, photo) VALUES (";
			Map<String, Object> map = new HashMap<String, Object>();


		try{
	    //  Establishing connection
	        Class.forName("com.mysql.jdbc.Driver");

		    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arounddb", "dataDisposal", "hwVvfcYCJMMHH4yp");

	        Statement st = con.createStatement();

	        ResultSet res = st.executeQuery("SELECT * FROM users WHERE login LIKE '"+ login+ "'");

	    //  Checking if the login is occupied
	        if(!res.isBeforeFirst()){

	            st.executeUpdate("INSERT INTO friends (friend_1) VALUES (NULL)");

	            ResultSet resint = st.executeQuery("SELECT friends_id FROM friends ORDER BY friends_id DESC LIMIT 1");

	            resint.next();

	            int fr = resint.getInt(1);


	            st.executeUpdate(

	                    query

	                    + "'"
	                    
	                    + fr + "', '"

	                    + login + "', '"

	                    + password + "', '"                    

	                    + x + "', '"

	                    + y + "', '"

	                    + status + "', '"

	                    + activity + "', '"
	                    
	                    + photo + "');");

		    con.close();
//	      Returns true only if everything gone right
		    map.put("valid", true);
		    con.close();
		    return map;

		} else {

			map.put("valid", false);
			con.close();
		    return map;

		}
		
		}catch(Exception e) {

			logger.log(Level.WARNING, " An exception appeared, problem with connecting with database", e);

			map.put("valid", false);
		    return map;

		}

		}
	}


