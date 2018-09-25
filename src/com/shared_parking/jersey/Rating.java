package com.shared_parking.jersey;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.shared_parking.dbconnection.DBConnection;
import com.shared_parking.utility.Utility;

import org.codehaus.jettison.json.JSONArray;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/rating")
public class Rating {
	// HTTP Get Method
	@POST 
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/getbyuserastenant")  
	// Consumes JSON as parameter
	@Consumes(MediaType.APPLICATION_JSON) 
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
	public String getRatingAsTenant(JSONObject datajson){
		String response = "";
		String auth_token = "";
		int userid = -1;
		try {
			auth_token = datajson.getString("auth_token");
			userid = DBConnection.getUserID(auth_token);
		} catch (JSONException e) {
			response = Utility.constructJSON("get ratings from user as tenant",false, "Error occured");
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("get ratings from user as tenant",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				response = DBConnection.getRatingAsTenant(userid);
			} catch (Exception e) {
				response = Utility.constructJSON("get ratings from user as tenant",false, "Error occured");
				e.printStackTrace();
			}
		}		
		return response;

	}
	
	// HTTP Get Method
		@POST 
		// Path: http://localhost/<appln-folder-name>/register/doregister
		@Path("/getbyuseraslandlord")  
		// Consumes JSON as parameter
		@Consumes(MediaType.APPLICATION_JSON) 
		// Produces JSON as response
		@Produces(MediaType.APPLICATION_JSON) 
		// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
		public String getRatingAyLandlord(JSONObject datajson){
			String response = "";
			String auth_token = "";
			int userid = -1;
			try {
				auth_token = datajson.getString("auth_token");
				userid = DBConnection.getUserID(auth_token);
			} catch (JSONException e) {
				response = Utility.constructJSON("get ratings from user as landlord",false, "Error occured");
				e.printStackTrace();
			}
			if(userid == -1) {
				response = Utility.constructJSON("get ratings from user as landlord",false, "Authentifizierung mit auth_token fehlgeschlagen!");
			}
			else {
				try {
					response = DBConnection.getRatingAsLandlord(userid);
				} catch (Exception e) {
					response = Utility.constructJSON("get ratings from user as landlord",false, "Error occured");
					e.printStackTrace();
				}
			}		
			return response;

		}	
	
	// HTTP Get Method
	@POST 
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/create")
	// Consumes JSON as parameter
	@Consumes(MediaType.APPLICATION_JSON) 
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
	public String insertParkingSpace(JSONObject datajson){
		String response = "";
		String auth_token = "", textbytenant = "", textbylandlord = "";
		int userid = -1, ratingbytenant = 0, ratingbylandlord = 0, parkingtradeid = 0;
		try {
			textbytenant = datajson.getString("textbytenant");
			textbylandlord = datajson.getString("textbylandlord");
			auth_token = datajson.getString("auth_token");
			userid = DBConnection.getUserID(auth_token);
			ratingbytenant = datajson.getInt("ratingbytenant");
			ratingbylandlord = datajson.getInt("ratingbylandlord");
			parkingtradeid = datajson.getInt("parkingtradeid");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("insert rating",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				if(DBConnection.insertRating(ratingbytenant, ratingbylandlord, textbytenant, textbylandlord, parkingtradeid)) response = Utility.constructJSON("/rating/create",true);
				else response = Utility.constructJSON("insert rating",false, "Error occured");
			} catch (Exception e) {
				response = Utility.constructJSON("insert rating",false, "Error occured");
				e.printStackTrace();
			}
		}
		return response;

	}	

}