package com.ServletAround.daos;

import com.ServletAround.income.JSONFile;
import com.ServletAround.main.ServletTest;
import com.ServletAround.utils.BCrypt;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

import org.json.*;

public class ActionLogin {
private static final Logger logger = Logger.getLogger(ServletTest.class.getName());
	
	public static JSONObject Login(JSONFile jsonFile){
		String login = jsonFile.getLogin();
		String password = jsonFile.getPass();
		String x = jsonFile.getX();
		String y = jsonFile.getY();
		String status = jsonFile.getStatus();
		int activity = jsonFile.getActivity();
		String email = jsonFile.getEmail();
		JSONObject map = new JSONObject();
		int number = 0;
		try {

            //  Establishing connection

            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arounddb", "dataDisposal", "hwVvfcYCJMMHH4yp");

            Statement st = con.createStatement();
            
            ResultSet iff = st.executeQuery("SELECT * FROM users WHERE email LIKE '" + email + "';");
            
            iff.next();
            String mylog = iff.getString(3);
            String pass = iff.getString(4);
            int myscore = iff.getInt(11);
            if(BCrypt.checkpw(password, pass)){
            	map.put("photo", iff.getString(9));
            	map.put("login", mylog);
            	map.put("score", myscore);
            st.executeUpdate("UPDATE users SET x='"+ x + "', y='"  + y +"', status='" + status +"', activity='" + activity +"' WHERE login LIKE '" + login +"'");

            ResultSet rs = st.executeQuery("SELECT friends_id FROM users WHERE email LIKE '" + email + "'");

            rs.next();

            String fid = rs.getString(1);

            ResultSet fList = st.executeQuery("SELECT * FROM friends WHERE friends_id=" + fid );

            fList.next();

        // Here is something to change when maximum number of friends will change

            for(int i = 2; i <= 41; i++){

                if(fList.getString(i) == null){
                    number = i;
                    i = 41;
                }
            }
            number -=2;
            
           JSONArray ja = new JSONArray();
           String[] array = new String[number];
          // for(int k =1; k <= number; k++){
        	//   array[k] = /*This is the name of a friend*/ fList.getString(k+1);
          // }
           
            for (int j = 1; j <= number; j++){
            	ResultSet fList2 = st.executeQuery("SELECT * FROM friends WHERE friends_id=" + fid );

                fList2.next();
            	String friend_login= /*This is the name of a friend*/ fList2.getString(j+1);
            	fList2.close();
                 ResultSet f_l = st.executeQuery("SELECT * FROM users WHERE login LIKE '"
                		 + friend_login + "'");
                 f_l.next();
                  JSONObject jo = new JSONObject();
                  jo.put("login", f_l.getString(3));
                  jo.put("x", f_l.getDouble(5));
                  jo.put("y", f_l.getDouble(6));
                  jo.put("status", f_l.getString(7));
                  jo.put("activity", f_l.getInt(8));
                  jo.put("photo", f_l.getString(9));
                  ja.put(jo);
            }

            map.put("valid", true);
            
            map.put("friend_list", ja);
            con.close();
            System.out.println(map.toString());
            return map;

            }else {
            	map.put("valid", false);
            	
            	con.close();
            	return map;
            }
        }catch(Exception e){
            logger.log(Level.WARNING, "An exception appeared while refreshing a friend list", e);
           
				try {
					map.put("valid", false);
				} catch (JSONException e1) {
					
				}
          
            
            return map;
        }
		
		
		
	}
	
}
