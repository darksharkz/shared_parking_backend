package com.shared_parking.jersey;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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

@Path("/parkingtrade")
public class ParkingTrade {
	// HTTP Get Method
	@POST 
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/getbyuserastenant")  
	// Consumes JSON as parameter
	@Consumes(MediaType.APPLICATION_JSON) 
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
	public String getParkingSpaceAsTenant(JSONObject datajson){
		String response = "";
		String auth_token = "";
		int userid = -1;
		try {
			auth_token = datajson.getString("auth_token");
			userid = DBConnection.getUserID(auth_token);
		} catch (JSONException e) {
			response = Utility.constructJSON("get parkingtrade from user as tenant",false, "Error occured");
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("get parkingtrade from user as tenant",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				response = DBConnection.getParkingTradeAsTenant(userid);
			} catch (Exception e) {
				response = Utility.constructJSON("get parkingtrade from user as tenant",false, "Error occured");
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
	public String getParkingSpaceAsLandlord(JSONObject datajson){
		String response = "";
		String auth_token = "";
		int userid = -1;
		try {
			auth_token = datajson.getString("auth_token");
			userid = DBConnection.getUserID(auth_token);
		} catch (JSONException e) {
			response = Utility.constructJSON("get parkingtrade from user as landlord",false, "Error occured");
			e.printStackTrace();
		}
		if(userid == -1) {
			response = Utility.constructJSON("get parkingtrade from user as landlord",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				response = DBConnection.getParkingTradeAsLandlord(userid);
			} catch (Exception e) {
				response = Utility.constructJSON("get parkingtrade from user as landlord",false, "Error occured");
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
		String start_dt = "", end_dt = "", auth_token = "";
		int price = 0, parkingspaceid = 0, tenantid = -1, landlordid = -1;
		try {
			start_dt = datajson.getString("start_dt");
			end_dt = datajson.getString("end_dt");
			auth_token = datajson.getString("auth_token");
			tenantid = DBConnection.getUserID(auth_token);
			price = datajson.getInt("price");
			parkingspaceid = datajson.getInt("parkingspaceid");
			landlordid = datajson.getInt("userid");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime ldtstart = LocalDateTime.parse(start_dt, formatter);
		LocalDateTime ldtend = LocalDateTime.parse(end_dt, formatter);		
        int minutes = (int) ldtstart.until(ldtend, ChronoUnit.MINUTES);
        minutes = minutes + 1;
        int gesamtprice = (int) (((double)price/60)*minutes);
		
		if(tenantid == -1) {
			response = Utility.constructJSON("/parkingtrade/create",false, "Authentifizierung mit auth_token fehlgeschlagen!");
		}
		else {
			try {
				if(DBConnection.insertParkingTrade(gesamtprice, start_dt, end_dt, parkingspaceid, tenantid) & DBConnection.setBalance(tenantid, -gesamtprice) & DBConnection.setBalance(landlordid, gesamtprice)) response = Utility.constructJSON("/parkingtrade/create",true);
				else response = Utility.constructJSON("/parkingtrade/create",false, "Error occured");
			} catch (Exception e) {
				response = Utility.constructJSON("/parkingtrade/create",false, "Error occured");
				e.printStackTrace();
			}
		}
		return response;

	}	

}