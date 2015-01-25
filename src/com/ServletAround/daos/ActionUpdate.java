package com.ServletAround.daos;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

import org.json.*;

import com.ServletAround.income.JSONFile;
import com.ServletAround.main.ServletTest;
import com.ServletAround.utils.BCrypt;
public class ActionUpdate {
	private static final Logger logger = Logger.getLogger(ServletTest.class.getName());
	
	
	public static JSONObject Update(JSONFile jsonFile){
	String login = jsonFile.getLogin();
	String password = jsonFile.getPass();
	String x = jsonFile.getX();
	String y = jsonFile.getY();
	String status = jsonFile.getStatus();
	int activity = jsonFile.getActivity();
	JSONObject map = new JSONObject();
	
	
	
	try{

	    // Establishing connection

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arounddb", "dataDisposal", "hwVvfcYCJMMHH4yp");

	        Statement st = con.createStatement();
	        
ResultSet iff = st.executeQuery("SELECT * FROM users WHERE login LIKE '" + login + "'");
            
            iff.next();
            
            if(BCrypt.checkpw(password, iff.getString(4))){

	        st.executeUpdate("UPDATE users SET x='" + x + "', y='"+ y +"', status='"+status+"', activity='"+activity+"' WHERE login LIKE '" + login +"'");

	        ResultSet rs = st.executeQuery("SELECT friends_id FROM users WHERE login LIKE '" + login + "'");

            rs.next();

            String fid = rs.getString(1);

            ResultSet fList = st.executeQuery("SELECT * FROM friends WHERE friends_id=" + fid );

            fList.next();

        // Here is something to change when maximum number of friends will change

            int number = 0;
            int number2 = 0;
            int number3 = 0;
			for(int i = 2; i <= 41; i++){

                if(fList.getString(i) == null){
                    number = i;
                    i = 41;
                }
            }
            number -=2;
            
           JSONArray ja = new JSONArray();
          
           
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
                  jo.put("score", f_l.getInt(11));
                 
                  ja.put(jo);
            }

            map.put("friend_list", ja);
            
            ResultSet req = st.executeQuery("SELECT * FROM requests WHERE requests_id='" + fid + "'");
            req.next();
            for(int e = 2; e <= 41; e++){

                if(req.getString(e) == null){
                    number2 = e;
                    e = 41;
                }
            }
            number2 -= 2;
            
            JSONArray ja2 = new JSONArray();
            for (int f = 1; f <= number2; f++){
            	String friend_login= /*This is the name of a friend*/ req.getString(f+1);
                 
                  JSONObject jo = new JSONObject();
                  jo.put("login", friend_login);
                  ja2.put(jo);
            }
            
            map.put("request_list", ja2);

            ResultSet ph = st.executeQuery("SELECT photo1 FROM photos WHERE photo_id='" + fid + "'");
            ph.next();
            if(ph.getString(1) != null){
            map.put("photo_list", true);	
            }else{
            	map.put("photo_list", false);
            }
            
            
            
            
            ResultSet poke = st.executeQuery("SELECT * FROM pokes WHERE poke_id='" + fid + "'");
            poke.next();
            for(int z = 2; z <= 41; z++){

                if(poke.getString(z) == null){
                    number3 = z;
                    z = 41;
                }
            }
            number3 -= 2;
            
            JSONArray ja3 = new JSONArray();
            for (int u = 1; u <= number3; u++){
            	String friend_login= /*This is the name of a friend*/ poke.getString(u+1);
                 
                  JSONObject jo = new JSONObject();
                  jo.put("login", friend_login);
                  ja3.put(jo);
            }
            
            
            for(int o = 1; o <=number3; o++ ){
            st.executeUpdate("UPDATE pokes SET poke" + o + "=NULL WHERE poke_id='" + fid + "'");
            		
            }
            map.put("poke_list", ja3);

            
            
            
//            for(int g = 2; g <= 41; g++){
//
//                if(req.getString(g) == null){
//                    number3 = g;
//                   g = 41;
//                }
//            }
//            number3 -= 2;
//            
//            JSONArray ja3 = new JSONArray();
//            for (int h = 1; h <= number3; h++){
//            	String friend_login= /*This is the name of a friend*/ ph.getString(h+1);
//            	ResultSet photo = st.executeQuery("SELECT photo WHERE login LIKE '" + friend_login +"'" );
//            	photo.next();
//            	JSONObject jo = new JSONObject();
//                jo.put("login", friend_login);
//                jo.put("photo", photo.getString(1));
//                ja3.put(jo);
//                 
//                  
//            }
//            
//            map.put("photo_list", ja3);
            
            
            
            
            
            
            
            map.put("valid", true);
            
			con.close();
			System.out.println(map);
	        return map;
            }else{
            	
            	map.put("valid", false);
            	con.close();
            	return map;
            }
		}catch(Exception e){

			logger.log(Level.WARNING, " An exception appeared, problem with updating the database", e);

			
				try {
					map.put("valid", false);
				} catch (JSONException e1) {
					
				}
			

	        return map;

			}
	
	
	
	
	
	
	
	
	}
}
