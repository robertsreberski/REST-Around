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
public class ActionDelete {
	private static final Logger logger = Logger.getLogger(ServletTest.class.getName());
	public static Map<String, Object> Delete(JSONFile jsonFile){
		String login = jsonFile.getLogin();
		String password = jsonFile.getPass();
		String friend = jsonFile.getFriend();
		Map<String, Object> map = new HashMap<String, Object>();
		String forEachQuery_1= "SELECT friend_";
        String forEachQuery_2= "UPDATE friends SET friend_";
        int j=1;
        int j2 = 1;
		 try{			
				    //  Establishing connection
				        Class.forName("com.mysql.jdbc.Driver");

					    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arounddb", "dataDisposal", "hwVvfcYCJMMHH4yp");

				        Statement st = con.createStatement();
				     // Deleting a friend from the users friend list
				        ResultSet iff = st.executeQuery("SELECT password FROM users WHERE login LIKE '" + login + "'");
			            
			            iff.next();
			            
			            	if(BCrypt.checkpw(password, iff.getString(1))){
				        ResultSet user = st.executeQuery("SELECT users_id FROM users WHERE login LIKE '" + login + "'");

				        user.next();

				        int id = user.getInt(1);
				        
				        ResultSet nvm = st.executeQuery("SELECT * FROM friends WHERE friends_id='" + id +"'");
				        
				        nvm.next();

				        int number = 0;
						for(int e = 2; e <= 41; e++){

        	                 if(nvm.getString(e) == null){
        	                     number = e;
        	                     e = 41;
        	                 }
        	             }
        	             number -= 2;

				        String ids = Integer.toString(id);



				        
						for(int i = 1; i<=number;i++){

				        ResultSet rs = st.executeQuery(forEachQuery_1 + Integer.toString(i) +" FROM friends WHERE friends_id='" + ids + "'");

				        rs.next();

				        if (rs.getString(1).equals(friend)){

				            ResultSet next = st.executeQuery("SELECT friend_" + Integer.toString((i+1)) + " FROM friends WHERE friends_id='" + ids + "'" );

				            next.next();

				            String friend1 = next.getString(1);

				            if(friend1 == null){
				                st.executeUpdate(forEachQuery_2 + Integer.toString(i) + "=NULL WHERE friends_id='" + ids + "'");
				            }
				            else {
				                st.executeUpdate(forEachQuery_2 + Integer.toString(i) + "='" + friend1 + "' WHERE friends_id='" + ids + "'");
				            }

				            j=i+1;

				            i = number;

				        }
				        }

				        for(int h = j; h <=number; h++ ){

				            ResultSet next = st.executeQuery("SELECT friend_" + Integer.toString((h+1)) + " FROM friends WHERE friends_id='" + ids + "'" );

				            next.next();

				            String friend1 = next.getString(1);

				            st.executeUpdate(forEachQuery_2 + Integer.toString(h) + "='" + friend1 +"' WHERE friends_id='" + ids + "'");

				        }
				    //End of deleting

				    //Deleting the user from a friend's friend list

				        ResultSet user2 = st.executeQuery("SELECT users_id FROM users WHERE login LIKE '" + friend + "'");

				        user2.next();

				        int id2 = user2.getInt(1);
				        
				        ResultSet nvm2 = st.executeQuery("SELECT * FROM friends WHERE friends_id='" + id2 + "'");
				        
				        nvm2.next();
				        
				        int number2 = 0;
						for(int e = 2; e <= 41; e++){

        	                 if(nvm2.getString(e) == null){
        	                     number2 = e;
        	                     e = 41;
        	                 }
        	             }
        	             number2 -= 2;


				        String ids2 = Integer.toString(id2);



				        for(int i = 1; i<=number2;i++){

				            ResultSet rs = st.executeQuery(forEachQuery_1 + Integer.toString(i) +" FROM friends WHERE friends_id='" + ids2 + "'");

				            rs.next();

				            if (rs.getString(1).equals(login)){

				                ResultSet next = st.executeQuery("SELECT friend_" + Integer.toString((i+1)) + " FROM friends WHERE friends_id='" + ids2 + "'" );

				                next.next();

				                String friend1 = next.getString(1);

				                if(friend1 == null){
				                    st.executeUpdate(forEachQuery_2 + Integer.toString(i) + "=NULL WHERE friends_id='" + ids2 + "'");
				                }
				                else {
				                    st.executeUpdate(forEachQuery_2 + Integer.toString(i) + "='" + friend1 + "' WHERE friends_id='" + ids2 + "'");
				                }
				                j2=i+1;

				                i = number2;

				            }
				        }

				        for(int h = j2; h <=number2; h++ ){

				            ResultSet next = st.executeQuery("SELECT friend_" + Integer.toString((h+1)) + " FROM friends WHERE friends_id='" + ids2 + "'" );

				            next.next();

				            String friend1 = next.getString(1);

				            st.executeUpdate(forEachQuery_2 + Integer.toString(h) + "='" + friend1 +"' WHERE friends_id='" + ids2 + "'");

				        }
				    // End of second deleting
				        map.put("valid", true);
				        con.close();
				        }else {
				        	map.put("valid", false);
				        	con.close();
				        	
				        }
				        return map;
		 }catch(Exception e){
	            logger.log(Level.WARNING, "An exception appeared while deleting a friend", e);
	            map.put("valid", "false");
	            
	            return map;}
	}
}