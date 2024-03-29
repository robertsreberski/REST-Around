package com.ServletAround.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ServletAround.daos.ActionAdd;
import com.ServletAround.daos.ActionDelete;
import com.ServletAround.daos.ActionDownload;
import com.ServletAround.daos.ActionLogin;
import com.ServletAround.daos.ActionPhoto;
import com.ServletAround.daos.ActionPoke;
import com.ServletAround.daos.ActionRegister;
import com.ServletAround.daos.ActionRequested;
import com.ServletAround.daos.ActionUpdate;
import com.ServletAround.income.JSONFile;
import com.ServletAround.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ServletTest
 */
@WebServlet("/ServletTest")
public class ServletTest extends HttpServlet implements Constants{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ServletTest.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 
        response.getOutputStream().println("Hurray !! This Servlet Works");
 
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    	//starting input stream
    	InputStream requestBodyInput = request.getInputStream();
    	
    	ObjectMapper mapper = new ObjectMapper();
    	@SuppressWarnings("unchecked")
		Map<String, Object> jsonMap =  mapper.readValue(requestBodyInput, Map.class);
    	System.out.println(jsonMap);
    	JSONFile jsonFile = new JSONFile((String)jsonMap.get("login"), (String)jsonMap.get("password") );
    	String action = (String) jsonMap.get("action");
    	switch(action){
    	case UPDATE_SERVER_ACTION:{
    		
    		// adding some specific variables 
    		jsonFile.setX(jsonMap.get("x").toString());
    		jsonFile.setY(jsonMap.get("y").toString());
    		jsonFile.setStatus((String)jsonMap.get("status"));
    		jsonFile.setActivity((int)jsonMap.get("activity"));
    		requestBodyInput.close();
    		PrintWriter out = response.getWriter();
    		// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
    		out.print(ActionUpdate.Update(jsonFile));
    		out.flush();
    		
    		break;
    	}
    	case DOWNLOAD_PHOTO_ACTION:{
    		
    		requestBodyInput.close();
    		PrintWriter out = response.getWriter();
    		// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
    		out.print(ActionDownload.Download(jsonFile));
    		out.flush();
    		
    		
    		
    		break;
    	}
    	
    	case RESPONSE_REQUEST_SERVER_ACTION:{
    		jsonFile.setValid((boolean)jsonMap.get("valid"));
    		jsonFile.setFriend((String)jsonMap.get("friend"));
    		requestBodyInput.close();
    		OutputStream out = response.getOutputStream();
    		mapper.writeValue(out, ActionRequested.Requested(jsonFile));
    		out.close();
    		
    		
    		break;
    	}
    	
    	case REGISTER_SERVER_ACTION:{
    		
    		jsonFile.setX(jsonMap.get("x").toString());
    		jsonFile.setY(jsonMap.get("y").toString());
    		jsonFile.setStatus((String)jsonMap.get("status"));
    		jsonFile.setActivity((int)jsonMap.get("activity"));
    		jsonFile.setPhoto((String)jsonMap.get("photo"));
    		jsonFile.setEmail((String)jsonMap.get("email"));
    		requestBodyInput.close();
    		//starting output stream
    		OutputStream out = response.getOutputStream();
    		mapper.writeValue(out, ActionRegister.Register(jsonFile));
    		out.close();
    		break;
    	}
    
    	case LOGIN_SERVER_ACTION:{
    		System.out.println(jsonMap);
    		jsonFile.setX(jsonMap.get("x").toString());
    		jsonFile.setY(jsonMap.get("y").toString());
    		jsonFile.setStatus((String)jsonMap.get("status"));
    		jsonFile.setActivity((int)jsonMap.get("activity"));
    		jsonFile.setEmail((String)jsonMap.get("email"));
    		requestBodyInput.close();
    		//starting output stream
    		PrintWriter out = response.getWriter();
    		// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
    		out.print(ActionLogin.Login(jsonFile));
    		out.flush();
    		
    		
    		break;
    	}
    	case ADD_SERVER_ACTION:{
    		jsonFile.setFriend((String)jsonMap.get("friend"));
    		requestBodyInput.close();
    		//starting output stream
    		OutputStream out = response.getOutputStream();
    		mapper.writeValue(out, ActionAdd.Add(jsonFile));
    		out.close();
    		break;
    	}
    	case DELETE_SERVER_ACTION:{
    		jsonFile.setFriend((String)jsonMap.get("friend"));
    		requestBodyInput.close();
    		//starting output stream
    		OutputStream out = response.getOutputStream();
    		mapper.writeValue(out, ActionDelete.Delete(jsonFile));
    		
    		out.close();
    		
    		break;
    	}
    	case PHOTO_UPDATE_SERVER_ACTION:{
    		jsonFile.setPhoto((String)jsonMap.get("photo"));
    		requestBodyInput.close();
    		//starting output stream
    		OutputStream out = response.getOutputStream();
    		mapper.writeValue(out, ActionPhoto.Photo(jsonFile));
    		
    		out.close();
    		
    		break;
    	}
    	
    	case POKE_SERVER_ACTION:{
    		jsonFile.setFriend((String)jsonMap.get("friend"));
    		requestBodyInput.close();
    		//starting output stream
    		OutputStream out = response.getOutputStream();
    		mapper.writeValue(out, ActionPoke.Poke(jsonFile));
    		
    		out.close();
    		
    		
    		
    		
    		
    		break;
    	}
    	
    	
    	
    	
    	}
    	
    	
    	
    	
    	
    	
            
 
        } catch (IOException e) {
 
 
           
        }   
        }
    	
    	
    	
       
    }
 

