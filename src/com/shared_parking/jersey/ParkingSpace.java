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

@Path("/parkingspace")
public class ParkingSpace {
	// HTTP Get Method
	@POST 
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/getbyuser")  
	// Consumes JSON as parameter
	@Consumes(MediaType.APPLICATION_JSON) 
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
	public String getParkingSpace(JSONObject datajson){
		String response = "";
		String auth_token = "";
		int userid = -1;
		try {
			auth_token = datajson.getString("auth_token");
			userid = DBConnection.getUserID(auth_token);
		} catch (JSONException e) {
			response = Utility.constructJSON("/parkingspace/getbyuser",false, "Error occured");
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("/parkingspace/getbyuser",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				response = DBConnection.getParkingSpace(userid);
			} catch (Exception e) {
				response = Utility.constructJSON("/parkingspace/getbyuser",false, "Error occured");
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
		String city = "", street = "", auth_token = "";
		int postcode = 0, number = 0, userid = -1;
		double lat = 0.0, lng = 0.0;
		try {
			city = datajson.getString("city");
			street = datajson.getString("street");
			auth_token = datajson.getString("auth_token");
			postcode = datajson.getInt("postcode");
			number = datajson.getInt("number");
			userid = DBConnection.getUserID(auth_token);
			lat = datajson.getDouble("lat");
			lng = datajson.getDouble("lng");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("/parkingspace/create",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				if(DBConnection.insertParkingSpace(city, street, postcode, number, userid, lat, lng)) response = Utility.constructJSON("/parkingspace/create",true);
				else response = Utility.constructJSON("/parkingspace/create",false, "Error occured");
			} catch (Exception e) {
				response = Utility.constructJSON("/parkingspace/create",false, "Error occured");
				e.printStackTrace();
			}
		}
		return response;

	}
	
	// HTTP Get Method
	@POST 
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/edit")
	// Consumes JSON as parameter
	@Consumes(MediaType.APPLICATION_JSON) 
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
	public String editParkingSpace(JSONObject datajson){
		String response = "";
		String city = "", street = "", auth_token = "";
		int postcode = 0, number = 0, userid = -1, ID = 0;
		double lat = 0.0, lng = 0.0;
		try {
			ID = datajson.getInt("ID");
			city = datajson.getString("city");
			street = datajson.getString("street");
			auth_token = datajson.getString("auth_token");
			postcode = datajson.getInt("postcode");
			number = datajson.getInt("number");
			userid = DBConnection.getUserID(auth_token);
			lat = datajson.getDouble("lat");
			lng = datajson.getDouble("lng");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("edit parkingspace",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				if(DBConnection.editParkingSpace(ID, city, street, postcode, number, userid, lat, lng)) response = Utility.constructJSON("edit parkingspace",true);
				else response = Utility.constructJSON("edit parkingspace",false, "Error occured");
			} catch (Exception e) {
				response = Utility.constructJSON("edit parkingspace",false, "Error occured");
				e.printStackTrace();
			}
		}
		return response;

	}
	
	// HTTP Get Method
	@POST 
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/delete")
	// Consumes JSON as parameter
	@Consumes(MediaType.APPLICATION_JSON) 
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
	public String deleteParkingSpace(JSONObject datajson){
		String response = "";
		String auth_token = "";
		int ID = 0, userid = -1;
		try {
			ID = datajson.getInt("ID");
			auth_token = datajson.getString("auth_token");
			userid = DBConnection.getUserID(auth_token);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("/parkingspace/create",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				if(DBConnection.deleteParkingSpace(ID)) response = Utility.constructJSON("/parkingspace/create",true);
				else response = Utility.constructJSON("/parkingspace/create",false, "Error occured");
			} catch (Exception e) {
				response = Utility.constructJSON("/parkingspace/create",false, "Error occured");
				e.printStackTrace();
			}
		}
		return response;

	}

}

