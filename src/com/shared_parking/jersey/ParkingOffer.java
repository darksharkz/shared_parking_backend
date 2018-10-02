package com.shared_parking.jersey;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

@Path("/parkingoffer")
public class ParkingOffer {
	// HTTP Get Method
	@POST 
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/getbyuser")  
	// Consumes JSON as parameter
	@Consumes(MediaType.APPLICATION_JSON) 
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
	public String getParkingOfferbyUser(JSONObject datajson){
		String response = "";
		String auth_token = "";
		int userid = -1;
		try {
			auth_token = datajson.getString("auth_token");
			userid = DBConnection.getUserID(auth_token);
		} catch (JSONException e) {
			response = Utility.constructJSON("/parkingoffer/getbyuser",false, "Error occured");
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("/parkingoffer/getbyuser",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				response = DBConnection.getParkingOfferbyUser(userid);
			} catch (Exception e) {
				response = Utility.constructJSON("/parkingoffer/getbyuser",false, "Error occured");
				e.printStackTrace();
			}
		}		
		return response;

	}
	
	// HTTP Get Method
	@POST 
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/getbytime")  
	// Consumes JSON as parameter
	@Consumes(MediaType.APPLICATION_JSON) 
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
	public String getParkingOfferbyTime(JSONObject datajson){
		String response = "";
		String auth_token = "", start_dt = "", end_dt = "";
		int userid = -1;
		try {
			auth_token = datajson.getString("auth_token");
			start_dt = datajson.getString("start_dt");
			end_dt = datajson.getString("end_dt");
			userid = DBConnection.getUserID(auth_token);
		} catch (JSONException e) {
			response = Utility.constructJSON("/parkingoffer/getbytime",false, "Error occured");
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("/parkingoffer/getbytime",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				response = DBConnection.getParkingOfferbyTime(userid, start_dt, end_dt);
			} catch (Exception e) {
				response = Utility.constructJSON("/parkingoffer/getbytime",false, "Error occured");
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
	public String insertParkingOffer(JSONObject datajson){
		String response = "";
		String start_dt = "", end_dt = "", auth_token = "";
		int price = 0, parkingspaceid = 0, userid = -1;
		try {
			start_dt = datajson.getString("start_dt");
			end_dt = datajson.getString("end_dt");
			auth_token = datajson.getString("auth_token");
			userid = DBConnection.getUserID(auth_token);
			price = datajson.getInt("price");
			parkingspaceid = datajson.getInt("parkingspaceid");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime ldtstart = LocalDateTime.parse(start_dt, formatter);
		LocalDateTime ldtend = LocalDateTime.parse(end_dt, formatter);	
		
		try {
			if(userid == -1) {
				response = Utility.constructJSON("/parkingoffer/create",false, "Authentifizierung mit auth_token fehlgeschlagen!");
			}
			else if (ldtend.isBefore(ldtstart)) {
				response = Utility.constructJSON("/parkingoffer/create",false, "Enddate and time has to be after Startdate and time!");
			}
			else if (!DBConnection.isOwned(parkingspaceid, userid)) {
				response = Utility.constructJSON("/parkingoffer/create",false, "Parkingspace is not owned by you!!");
			}
			else {
				if(DBConnection.insertParkingOffer(price, start_dt, end_dt, parkingspaceid)) response = Utility.constructJSON("/parkingoffer/create",true, "Created Parking Offer!");
				else response = Utility.constructJSON("/parkingoffer/create",false, "Error occured");
			} 
		} catch (Exception e) {
				response = Utility.constructJSON("/parkingoffer/create",false, "Error occured");
				e.printStackTrace();
			}
		return response;

	}	
	
	// HTTP Get Method
	@POST 
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON) 
	public String editParkingOffer(JSONObject datajson){
		String response = "";
		String start_dt = "", end_dt = "", auth_token = "";
		int price = 0, parkingspaceid = 0, userid = -1, ID = 0;
		double lat = 0.0, lng = 0.0;
		try {
			ID = datajson.getInt("ID");
			start_dt = datajson.getString("start_dt");
			end_dt = datajson.getString("end_dt");
			auth_token = datajson.getString("auth_token");
			userid = DBConnection.getUserID(auth_token);
			price = datajson.getInt("price");
			parkingspaceid = datajson.getInt("parkingspaceid");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("edit parkingoffer",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				if(DBConnection.editParkingOffer(ID, price, start_dt, end_dt, parkingspaceid)) response = Utility.constructJSON("edit parkingspace",true);
				else response = Utility.constructJSON("edit parkingoffer",false, "Error occured");
			} catch (Exception e) {
				response = Utility.constructJSON("edit parkingoffer",false, "Error occured");
				e.printStackTrace();
			}
		}
		return response;
	}
	

	@POST 
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON) 
	public String deleteParkingOffer(JSONObject datajson){
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
			response = Utility.constructJSON("delete parkingoffer",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				if(DBConnection.deleteParkingOffer(ID)) response = Utility.constructJSON("delete parkingoffer",true);
				else response = Utility.constructJSON("delete parkingoffer",false, "Error occured");
			} catch (Exception e) {
				response = Utility.constructJSON("delete parkingoffer",false, "Error occured");
				e.printStackTrace();
			}
		}
		return response;

	}

}
