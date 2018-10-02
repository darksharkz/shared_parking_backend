package com.shared_parking.jersey;

import java.sql.SQLException;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.shared_parking.dbconnection.DBConnection;
import com.shared_parking.utility.Utility;

@Path("/user")
public class User {
	
	// HTTP Get Method
		@POST 
		// Path: http://localhost/<appln-folder-name>/register/doregister
		@Path("/register")
		// Consumes JSON as parameter
		@Consumes(MediaType.APPLICATION_JSON) 
		// Produces JSON as response
		@Produces(MediaType.APPLICATION_JSON) 
		// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
		public String doLoginBetter(JSONObject datajson){
			String response = "";
			String mail = "";
			String firstname = "";
			String lastname = "";
			String password = "";
			try {
				mail = datajson.getString("mail");
				firstname = datajson.getString("firstname");
				lastname = datajson.getString("lastname");
				password = datajson.getString("password");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("Inside doLogin "+ name + username + password);
			int retCode = registerUser(mail, firstname, lastname, password);
			if(retCode == 0){
				response = Utility.constructJSON("register",true, "You have successfully registered!");
			}else if(retCode == 1){
				response = Utility.constructJSON("register",false, "You are already registered");
			}else if(retCode == 2){
				response = Utility.constructJSON("register",false, "Special Characters are not allowed in Username and Password");
			}else if(retCode == 3){
				response = Utility.constructJSON("register",false, "Error occured");
			}
			return response;

		}	

		private int registerUser(String mail, String firstname, String lastname, String password){
			int result = 3;

			try {
				if(DBConnection.insertUser(mail, firstname, lastname, password)){
					//System.out.println("RegisterUSer if");
					result = 0;
				}
			} catch(SQLException sqle){
				//System.out.println("RegisterUSer catch sqle");
				//When Primary key violation occurs that means user is already registered
				if(sqle.getErrorCode() == 1062){
					result = 1;
				} 
				//When special characters are used in name,username or password
				else if(sqle.getErrorCode() == 1064){
					System.out.println(sqle.getErrorCode());
					result = 2;
				}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				//System.out.println("Inside checkCredentials catch e ");
				result = 3;
			}
			

			return result;
		}
	
	@POST 
	// Path: http://localhost/<appln-folder-name>/login/dologin
	@Path("/login")
	// Consumes JSON as response
	@Consumes(MediaType.APPLICATION_JSON) 
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/login/dologin?username=abc&password=xyz
	public String doLogin(JSONObject datajson){
		String response = "";
		String mail = "";
		String password = "";
		try {
			mail = datajson.getString("mail");
			password = datajson.getString("password");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(checkCredentials(mail, password)){
			//response = Utility.constructJSON("login",true);
			JSONObject obj = new JSONObject();
			try {
				obj.put("status", true);
				obj.put("auth_token", generateAuthToken(mail));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}
			response = obj.toString();
		}else{
			response = Utility.constructJSON("login", false, "Incorrect Email or Password");
		}
	return response;		
	}
	
	/**
	 * Method to generate a random auth token and put it into the database
	 * 
	 * @param mail
	 * @param auth token
	 * @return
	 */
	private String generateAuthToken(String mail) {
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 15;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedString = buffer.toString();
	    
	    try {
			DBConnection.addAuthtoken(mail, generatedString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return generatedString;
	}

	/**
	 * Method to check whether the entered credential is valid
	 * 
	 * @param uname
	 * @param pwd
	 * @return
	 */
	private boolean checkCredentials(String mail, String password){

		boolean result = false;
		try {
			result = DBConnection.checkLogin(mail, password);

		} catch (Exception e) {
			e.printStackTrace();

			result = false;
		}

		return result;
	}
	
	// HTTP Get Method
	@POST 
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/get")  
	// Consumes JSON as parameter
	@Consumes(MediaType.APPLICATION_JSON) 
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
	public String getUser(JSONObject datajson){
		String response = "";
		String auth_token = "";
		int userid = -1;
		try {
			auth_token = datajson.getString("auth_token");
			userid = DBConnection.getUserID(auth_token);
		} catch (JSONException e) {
			response = Utility.constructJSON("get user",false, "Error occured");
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("get user",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				response = DBConnection.getUser(userid);
			} catch (Exception e) {
				response = Utility.constructJSON("get user",false, "Error occured");
				e.printStackTrace();
			}
		}		
		return response;

	}
	
	// HTTP Get Method
		@POST 
		// Path: http://localhost/<appln-folder-name>/register/doregister
		@Path("/setbalance")  
		// Consumes JSON as parameter
		@Consumes(MediaType.APPLICATION_JSON) 
		// Produces JSON as response
		@Produces(MediaType.APPLICATION_JSON) 
		// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
		public String setBalace(JSONObject datajson){
			String response = "";
			String auth_token = "";
			int userid = -1, change = 0;
			try {
				auth_token = datajson.getString("auth_token");
				userid = DBConnection.getUserID(auth_token);
				change = datajson.getInt("change");
			} catch (JSONException e) {
				response = Utility.constructJSON("change balance",false, "Error occured");
				e.printStackTrace();
			}
			if(userid == -1) {
				response = Utility.constructJSON("change balance",false, "Authentifizierung mit auth_token fehlgeschlagen!");
			}
			else {
				try {
					if(DBConnection.setBalance(userid, change)) response = Utility.constructJSON("change balance",true);
				} catch (Exception e) {
					response = Utility.constructJSON("change balance",false, "Error occured");
					e.printStackTrace();
				}
			}		
			return response;

		}
}
