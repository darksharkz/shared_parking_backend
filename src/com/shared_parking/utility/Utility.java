package com.shared_parking.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Utility {
	/**
	 * Null check Method
	 * 
	 * @param txt
	 * @return
	 */
	public static boolean isNotNull(String txt) {
		// System.out.println("Inside isNotNull");
		return txt != null && txt.trim().length() >= 0 ? true : false;
	}

	/**
	 * Method to construct JSON
	 * 
	 * @param tag
	 * @param status
	 * @return
	 */
	public static String constructJSON(String tag, boolean status) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", tag);
			obj.put("status", new Boolean(status));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		}
		return obj.toString();
	}

	/**
	 * Method to construct JSON with Error Msg
	 * 
	 * @param tag
	 * @param status
	 * @param err_msg
	 * @return
	 */
	public static String constructJSON(String tag, boolean status,String err_msg) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", tag);
			obj.put("status", new Boolean(status));
			obj.put("error_msg", err_msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		}
		return obj.toString();
	}
	
	public static String sqlInsertHelperString(String query, boolean end) {
		if(end) return "'"+query+"');";
		else return "'"+query+"',";
	}
	
	public static String sqlInsertHelperInt(int query, boolean end) {
		if(end) return "'"+Integer.toString(query)+"');";
		else return "'"+Integer.toString(query)+"',";
	}
	
	public static String sqlInsertHelperDouble(Double query, boolean end) {
		if(end) return "'"+Double.toString(query)+"');";
		else return "'"+Double.toString(query)+"',";
	}
	
	public static String sqlInsertHelperBoolean(boolean query, boolean end) {
		if(end) return Boolean.toString(query)+");";
		else return Boolean.toString(query)+",";
	}
	
	public static String constructJSONParkingSpaceResponse(ResultSet rs) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", "/parkingspace/getbyuser");
			obj.put("status", true);
			JSONArray array = new JSONArray();
			while(rs.next()) {
				JSONObject parkingspacejson = new JSONObject();
				parkingspacejson.put("ID", rs.getInt("ID"));
				parkingspacejson.put("postcode", rs.getInt("postcode"));
				parkingspacejson.put("city", rs.getString("city"));
				parkingspacejson.put("street", rs.getString("street"));
				parkingspacejson.put("number", rs.getInt("number"));
				parkingspacejson.put("lat", rs.getDouble("lat"));
				parkingspacejson.put("lng", rs.getDouble("lng"));
				array.put(parkingspacejson);
			}
			obj.put("result", array);
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		}		
		return obj.toString();
	}

	public static String constructJSONParkingTradeAsTenantResponse(ResultSet rs) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", "get parking trade by user as tenant");
			obj.put("status", true);
			JSONArray array = new JSONArray();
			while(rs.next()) {
				JSONObject parkingspacejson = new JSONObject();
				parkingspacejson.put("ID", rs.getInt("ID"));
				parkingspacejson.put("contractpartnerid", rs.getInt("userid"));
				parkingspacejson.put("price", rs.getInt("price"));
				parkingspacejson.put("start_dt", rs.getString("start_dt"));
				parkingspacejson.put("end_dt", rs.getString("end_dt"));
				parkingspacejson.put("parkingspaceid", rs.getInt("parkingspaceid"));
				parkingspacejson.put("postcode", rs.getInt("postcode"));
				parkingspacejson.put("city", rs.getString("city"));
				parkingspacejson.put("street", rs.getString("street"));
				parkingspacejson.put("number", rs.getInt("number"));
				parkingspacejson.put("lat", rs.getDouble("lat"));
				parkingspacejson.put("lng", rs.getDouble("lng"));
				parkingspacejson.put("carid", rs.getInt("carid"));
				parkingspacejson.put("licenseplate", rs.getString("licenseplate"));
				array.put(parkingspacejson);
			}
			obj.put("result", array);
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		}		
		return obj.toString();
	}
	
	public static String constructJSONParkingTradeAsLandlordResponse(ResultSet rs) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", "get parking trade by user as landlord");
			obj.put("status", true);
			JSONArray array = new JSONArray();
			while(rs.next()) {
				JSONObject parkingspacejson = new JSONObject();
				parkingspacejson.put("ID", rs.getInt("ID"));
				parkingspacejson.put("contractpartnerid", rs.getInt("tenantid"));
				parkingspacejson.put("price", rs.getInt("price"));
				parkingspacejson.put("start_dt", rs.getString("start_dt"));
				parkingspacejson.put("end_dt", rs.getString("end_dt"));
				parkingspacejson.put("parkingspaceid", rs.getInt("parkingspaceid"));
				parkingspacejson.put("postcode", rs.getInt("postcode"));
				parkingspacejson.put("city", rs.getString("city"));
				parkingspacejson.put("street", rs.getString("street"));
				parkingspacejson.put("number", rs.getInt("number"));
				parkingspacejson.put("lat", rs.getDouble("lat"));
				parkingspacejson.put("lng", rs.getDouble("lng"));
				parkingspacejson.put("carid", rs.getInt("carid"));
				parkingspacejson.put("licenseplate", rs.getString("licenseplate"));
				array.put(parkingspacejson);
			}
			obj.put("result", array);
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		}		
		return obj.toString();
	}

	public static String constructJSONCarResponse(ResultSet rs) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", "get cars by user");
			obj.put("status", true);
			JSONArray array = new JSONArray();
			while(rs.next()) {
				JSONObject parkingspacejson = new JSONObject();
				parkingspacejson.put("ID", rs.getInt("ID"));
				parkingspacejson.put("licenseplate", rs.getString("licenseplate"));
				parkingspacejson.put("userid", rs.getInt("userid"));
				parkingspacejson.put("active", rs.getBoolean("active"));
				array.put(parkingspacejson);
			}
			obj.put("result", array);
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		}		
		return obj.toString();
	}

	public static String constructJSONParkingOfferResponse(ResultSet rs) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", "get parking offer by user");
			obj.put("status", true);
			JSONArray array = new JSONArray();
			while(rs.next()) {
				JSONObject parkingspacejson = new JSONObject();
				parkingspacejson.put("ID", rs.getInt("ID"));
				parkingspacejson.put("price", rs.getInt("price"));
				parkingspacejson.put("start_dt", rs.getString("start_dt"));
				parkingspacejson.put("end_dt", rs.getString("end_dt"));
				parkingspacejson.put("parkingspaceid", rs.getInt("parkingspaceid"));
				array.put(parkingspacejson);
			}
			obj.put("result", array);
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		}		
		return obj.toString();
	}
	
	public static String constructJSONParkingOfferbyTimeResponse(ResultSet rs) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", "get parking offer by user");
			obj.put("status", true);
			JSONArray array = new JSONArray();
			while(rs.next()) {
				System.out.println(rs.toString());
				JSONObject parkingspacejson = new JSONObject();
				parkingspacejson.put("ID", rs.getInt("ID"));
				parkingspacejson.put("price", rs.getInt("price"));
				parkingspacejson.put("start_dt", rs.getString("start_dt"));
				parkingspacejson.put("end_dt", rs.getString("end_dt"));
				parkingspacejson.put("parkingspaceid", rs.getInt("parkingspaceid"));
				parkingspacejson.put("postcode", rs.getInt("postcode"));
				parkingspacejson.put("city", rs.getString("city"));
				parkingspacejson.put("street", rs.getString("street"));
				parkingspacejson.put("number", rs.getInt("number"));
				parkingspacejson.put("lat", rs.getDouble("lat"));
				parkingspacejson.put("lng", rs.getDouble("lng"));
				parkingspacejson.put("userid", rs.getDouble("userid"));
				array.put(parkingspacejson);
			}
			obj.put("result", array);
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		}		
		return obj.toString();
	}

	public static String constructJSONRatingResponse(ResultSet rs) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", "get ratings from user as x");
			obj.put("status", true);
			JSONArray array = new JSONArray();
			while(rs.next()) {
				JSONObject parkingspacejson = new JSONObject();
				parkingspacejson.put("ratingbytenant", rs.getInt("ratingbytenant"));
				parkingspacejson.put("ratingbylandlord", rs.getInt("ratingbylandlord"));
				parkingspacejson.put("textbytenant", rs.getString("textbytenant"));
				parkingspacejson.put("entextbylandlordd_dt", rs.getString("textbylandlord"));
				parkingspacejson.put("parkingtradeid", rs.getInt("parkingtradeid"));
				array.put(parkingspacejson);
			}
			obj.put("result", array);
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		}		
		return obj.toString();
	}

	public static String constructJSONUserResponse(ResultSet rs) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", "get user");
			obj.put("status", true);
			while(rs.next()) {
				JSONObject userjson = new JSONObject();
				userjson.put("ID", rs.getInt("ID"));
				userjson.put("mail", rs.getString("mail"));
				userjson.put("firstname", rs.getString("firstname"));
				userjson.put("lastname", rs.getString("lastname"));
				userjson.put("balance", rs.getInt("balance"));
				obj.put("result", userjson);
			}
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		}		
		return obj.toString();
	}

}