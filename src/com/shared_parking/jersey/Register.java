package com.shared_parking.jersey;



import java.sql.SQLException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.shared_parking.dbconnection.DBConnection;
import com.shared_parking.utility.Utility;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
//Path: http://localhost/<appln-folder-name>/register
@Path("/register")
public class Register {
	// HTTP Get Method
	@POST 
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/doregister")
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
			response = Utility.constructJSON("register",true);
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

}
