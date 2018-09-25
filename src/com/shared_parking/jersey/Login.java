package com.shared_parking.jersey;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.shared_parking.dbconnection.DBConnection;
import com.shared_parking.utility.Utility;
//Path: http://localhost/<appln-folder-name>/login
@Path("/login")
public class Login {
	// HTTP Get Method
	@POST 
	// Path: http://localhost/<appln-folder-name>/login/dologin
	@Path("/dologin")
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

}