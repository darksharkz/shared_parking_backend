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

@Path("/car")
public class Car {
	// HTTP Get Method
	@POST 
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/getbyuser")  
	// Consumes JSON as parameter
	@Consumes(MediaType.APPLICATION_JSON) 
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
	public String getCar(JSONObject datajson){
		String response = "";
		String auth_token = "";
		int userid = -1;
		try {
			auth_token = datajson.getString("auth_token");
			userid = DBConnection.getUserID(auth_token);
		} catch (JSONException e) {
			response = Utility.constructJSON("/car/getbyuser",false, "Error occured");
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("/car/getbyuser",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				response = DBConnection.getCar(userid);
			} catch (Exception e) {
				response = Utility.constructJSON("/car/getbyuser",false, "Error occured");
				e.printStackTrace();
			}
		}		
		return response;

	}
	
	@POST 
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/getactivebyuser")  
	// Consumes JSON as parameter
	@Consumes(MediaType.APPLICATION_JSON) 
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
	public String getCarActive(JSONObject datajson){
		String response = "";
		String auth_token = "";
		int userid = -1;
		try {
			auth_token = datajson.getString("auth_token");
			userid = DBConnection.getUserID(auth_token);
		} catch (JSONException e) {
			response = Utility.constructJSON("/car/getactivebyuser",false, "Error occured");
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("/car/getactivebyuser",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				response = DBConnection.getCarActive(userid);
			} catch (Exception e) {
				response = Utility.constructJSON("/car/getactivebyuser",false, "Error occured");
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
		String licenseplate = "", auth_token = "";
		boolean active = false;
		int userid = -1;
		try {
			auth_token = datajson.getString("auth_token");
			userid = DBConnection.getUserID(auth_token);
			licenseplate = datajson.getString("licenseplate");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("/car/create",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				if(DBConnection.insertCar(licenseplate, userid, active)) response = Utility.constructJSON("/car/create",true);
				else response = Utility.constructJSON("/car/create",false, "Error occured");
			} catch (Exception e) {
				response = Utility.constructJSON("/car/create",false, "Error occured");
				e.printStackTrace();
			}
		}
		return response;

	}	
	
	@POST 
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON) 
	public String editParkingOffer(JSONObject datajson){
		String response = "";
		String licenseplate = "", auth_token = "";
		int ID = 0, userid = -1;
		try {
			ID = datajson.getInt("ID");
			auth_token = datajson.getString("auth_token");
			userid = DBConnection.getUserID(auth_token);
			licenseplate = datajson.getString("licenseplate");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("edit parkingoffer",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				if(DBConnection.editCar(ID, licenseplate)) response = Utility.constructJSON("edit parkingspace",true);
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
				if(DBConnection.deleteCar(ID)) response = Utility.constructJSON("delete parkingoffer",true);
				else response = Utility.constructJSON("delete parkingoffer",false, "Error occured");
			} catch (Exception e) {
				response = Utility.constructJSON("delete parkingoffer",false, "Error occured");
				e.printStackTrace();
			}
		}
		return response;

	}

}