package com.shared_parking.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import com.mysql.*;

import com.shared_parking.utility.Utility;

public class DBConnection {
	public static void main(String [] args) {
		String url = "jdbc:mysql://localhost:3306/users";
		String username = "simon";
		String password = "Password";

		System.out.println("Connecting database...");
		
		try {
			Connection con = createConnection();
			System.out.println("Database connected!");
		} catch (Exception e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}

//		try (Connection connection = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPwd)) {
//		    System.out.println("Database connected!");
//		} catch (SQLException e) {
//		    throw new IllegalStateException("Cannot connect the database!", e);
//		}
	}
	
	/**
	 * Method to create DB Connection
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public static Connection createConnection() throws Exception {
		Connection con = null;
		try {

			Class.forName(Constants.dbClass);
			
			con = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPwd);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			return con;
		}
	}
    /**
     * Method to check whether uname and pwd combination are correct
     * 
     * @param uname
     * @param pwd
     * @return
     * @throws Exception
     */
	public static boolean checkLogin(String mail, String password) throws Exception {
		boolean isUserAvailable = false;
		
		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "SELECT * FROM user WHERE mail = '" + mail
				+ "' AND password=" + "'" + password + "'";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			isUserAvailable = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}
		
		return isUserAvailable;
	}
	/**
	 * Method to insert uname and pwd in DB
	 * 
	 * @param name
	 * @param uname
	 * @param pwd
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static boolean insertUser(String mail, String firstname, String lastname, String password) throws SQLException, Exception {
		boolean insertStatus = false;

		Connection dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "INSERT into user(mail, firstname, lastname, password) values('"+mail+ "','"
				+ firstname + "','"+ lastname + "','" + password + "')";
		System.out.println(query);
		int records = stmt.executeUpdate(query);
		if (records > 0) {
			insertStatus = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}
		
		return insertStatus;
	}

	public static boolean addAuthtoken(String mail, String generatedString) throws SQLException, Exception{
		boolean changed = false;
		
		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "UPDATE user SET auth_token = '"+generatedString+"' WHERE mail = '"+mail+"';";
		System.out.println(query);
		int records = stmt.executeUpdate(query);
		if (records > 0) {
			changed = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}
		
		return changed;
	}


	public static int getUserID(String auth_token) {
		//-1 means no user found
		int userid = -1;
		Connection dbConn = null;
		try {
			dbConn = DBConnection.createConnection();
			Statement stmt = dbConn.createStatement();
			String query = "SELECT ID FROM user WHERE auth_token = '" + auth_token + "';";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				userid = rs.getInt("ID");
			}
		} catch (SQLException sqle) {
			userid = -1;
			sqle.printStackTrace();
		} catch (Exception e) {
			userid = -1;
			e.printStackTrace();
		} finally {
			if (dbConn != null) {
				try {
					dbConn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return userid;
	}
	
	public static boolean insertParkingSpace(String city, String street, int postcode, int number, int userid, double lat, double lng) throws SQLException, Exception{
		boolean inserted = false;
		
		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "INSERT into parkingspace(city, street, postcode, number, userid, lat, lng) values("
				+ Utility.sqlInsertHelperString(city, false)
				+ Utility.sqlInsertHelperString(street, false)
				+ Utility.sqlInsertHelperInt(postcode, false)
				+ Utility.sqlInsertHelperInt(number, false)
				+ Utility.sqlInsertHelperInt(userid, false)
				+ Utility.sqlInsertHelperDouble(lat, false)
				+ Utility.sqlInsertHelperDouble(lng, true);
		System.out.println(query);
		int records = stmt.executeUpdate(query);
		if (records > 0) {
			inserted = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}
		
		return inserted;
	}

	public static String getParkingSpace(int userid) throws SQLException, Exception{		
		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "SELECT * FROM parkingspace WHERE userid="+Integer.toString(userid)+";";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		String string = Utility.constructJSONParkingSpaceResponse(rs);
		if (dbConn != null) {
			dbConn.close();
		}
		
		return string;
	}

	public static boolean insertRating(int ratingbytenant, int ratingbylandlord, String textbytenant,
			String textbylandlord, int parkingtradeid) throws SQLException, Exception {
		boolean insertStatus = false;

		Connection dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "INSERT into rating(ratingbytenant, ratingbylandlord, textbytenant, textbylandlord, parkingtradeid) values("
				+ Utility.sqlInsertHelperInt(ratingbytenant, false)
				+ Utility.sqlInsertHelperInt(ratingbylandlord, false)
				+ Utility.sqlInsertHelperString(textbytenant, false)
				+ Utility.sqlInsertHelperString(textbylandlord, false)
				+ Utility.sqlInsertHelperInt(parkingtradeid, true);
		System.out.println(query);
		int records = stmt.executeUpdate(query);
		if (records > 0) {
			insertStatus = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}
		
		return insertStatus;
	}

	public static String getRatingAsTenant(int userid) throws SQLException, Exception{
		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "select r.* from rating r, parkingtrade pt where r.parkingtradeid=pt.ID and pt.tenantid="+Integer.toString(userid)+";";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		String string = Utility.constructJSONRatingResponse(rs);
		if (dbConn != null) {
			dbConn.close();
		}
		
		return string;
	}
	
	public static String getRatingAsLandlord(int userid) throws SQLException, Exception{
		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "select r.* from rating r, parkingtrade pt, parkingoffer po, parkingspace ps where r.parkingtradeid=pt.ID and pt.parkingofferid=po.ID and po.parkingspaceid=ps.ID and ps.userid="+Integer.toString(userid)+";";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		String string = Utility.constructJSONRatingResponse(rs);
		if (dbConn != null) {
			dbConn.close();
		}
		
		return string;
	}

	public static String getParkingTradeAsTenant(int userid) throws SQLException, Exception{
		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "SELECT * FROM parkingtrade pt, parkingspace ps, car c WHERE pt.parkingspaceid=ps.ID AND pt.carid=c.ID AND tenantid="+Integer.toString(userid)+";";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		String string = Utility.constructJSONParkingTradeAsTenantResponse(rs);
		if (dbConn != null) {
			dbConn.close();
		}
		
		return string;
	}
	
	public static String getParkingTradeAsLandlord(int userid) throws SQLException, Exception{
		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "select * from parkingtrade pt, parkingspace ps, car c where pt.parkingspaceid=ps.ID AND pt.carid=c.ID AND30 ps.userid="+Integer.toString(userid)+";";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		String string = Utility.constructJSONParkingTradeAsLandlordResponse(rs);
		if (dbConn != null) {
			dbConn.close();
		}
		
		return string;
	}

	public static boolean insertParkingTrade(int price, String start_dt, String end_dt, int parkingspaceid,
			int tenantid) throws SQLException, Exception {
		boolean insertStatus = false;

		Connection dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "INSERT into parkingtrade(carid, price, start_dt, end_dt, parkingspaceid, tenantid) values((SELECT ID FROM car WHERE active=true AND userid= "+ Integer.toString(tenantid) + "), "
				+ Utility.sqlInsertHelperInt(price, false)
				+ Utility.sqlInsertHelperString(start_dt, false)
				+ Utility.sqlInsertHelperString(end_dt, false)
				+ Utility.sqlInsertHelperInt(parkingspaceid, false)
				+ Utility.sqlInsertHelperInt(tenantid, true);
		System.out.println(query);
		int records = stmt.executeUpdate(query);
		if (records > 0) {
			insertStatus = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}
		
		return insertStatus;
	}

	public static String getParkingOfferbyUser(int userid) throws SQLException, Exception{
		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "SELECT po.* FROM parkingoffer po, parkingspace ps WHERE ps.ID=po.parkingspaceid AND ps.userid="+Integer.toString(userid)+";";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		String string = Utility.constructJSONParkingOfferResponse(rs);
		if (dbConn != null) {
			dbConn.close();
		}
		
		return string;
	}
	
	public static String getParkingOfferbyTime(int userid, String start_dt, String end_dt) throws SQLException, Exception{
		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "SELECT * FROM parkingoffer po, parkingspace ps WHERE ps.ID=po.parkingspaceid AND ps.userid<>"+Integer.toString(userid)+ " AND start_dt<='"+start_dt+"' and end_dt>='"+end_dt+"' AND NOT EXISTS (SELECT * FROM parkingtrade ptt, parkingspace pss WHERE pss.ID=ptt.parkingspaceid AND ptt.parkingspaceid=po.parkingspaceid AND ptt.start_dt>='"+start_dt+"' and ptt.start_dt<='"+end_dt+"') AND NOT EXISTS (SELECT * FROM parkingtrade ptt, parkingspace pss WHERE pss.ID=ptt.parkingspaceid AND ptt.parkingspaceid=po.parkingspaceid AND ptt.end_dt>='"+start_dt+"' and ptt.end_dt<='"+end_dt+"');";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		String string = Utility.constructJSONParkingOfferbyTimeResponse(rs);
		if (dbConn != null) {
			dbConn.close();
		}
		
		return string;
	}

	public static boolean insertParkingOffer(int price, String start_dt, String end_dt, int parkingspaceid) throws SQLException, Exception {
		boolean insertStatus = false;

		Connection dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "INSERT into parkingoffer(price, start_dt, end_dt, parkingspaceid) values("
				+ Utility.sqlInsertHelperInt(price, false)
				+ Utility.sqlInsertHelperString(start_dt, false)
				+ Utility.sqlInsertHelperString(end_dt, false)
				+ Utility.sqlInsertHelperInt(parkingspaceid, true);
		System.out.println(query);
		int records = stmt.executeUpdate(query);
		if (records > 0) {
			insertStatus = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}
		
		return insertStatus;
	}
	
	public static boolean editParkingOffer(int ID, int price, String start_dt, String end_dt, int parkingspaceid) throws SQLException, Exception {
		boolean inserted = false;

		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "UPDATE parkingoffer SET "
				+ "price = '" + price + "', "
				+ "start_dt = '" + start_dt + "', "
				+ "end_dt = '" + end_dt + "', "
				+ "parkingspaceid = '" + parkingspaceid + "' WHERE ID = " + ID + ";";
		System.out.println(query);
		int records = stmt.executeUpdate(query);
		if (records > 0) {
			inserted = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}

		return inserted;
	}
	
	public static boolean deleteParkingOffer(int ID) throws SQLException, Exception {
		boolean inserted = false;

		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "DELETE FROM parkingoffer WHERE ID = " + ID + ";"; 
		System.out.println(query);
		int records = stmt.executeUpdate(query);
		if (records > 0) {
			inserted = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}

		return inserted;
	}

	public static String getCar(int userid) throws SQLException, Exception{
		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "SELECT * FROM car WHERE userid="+Integer.toString(userid)+";";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		String string = Utility.constructJSONCarResponse(rs);
		if (dbConn != null) {
			dbConn.close();
		}
		
		return string;
	}
	
	public static String getCarActive(int userid) throws SQLException, Exception{
		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "SELECT * FROM car WHERE car.active=true AND userid="+Integer.toString(userid)+";";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		String string = Utility.constructJSONCarResponse(rs);
		if (dbConn != null) {
			dbConn.close();
		}
		
		return string;
	}

	public static boolean insertCar(String licenseplate, int userid, boolean active) throws SQLException, Exception {
		boolean insertStatus = false;

		Connection dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "INSERT into car(licenseplate, active, userid) values("
				+ Utility.sqlInsertHelperString(licenseplate, false)
				+ Utility.sqlInsertHelperBoolean(active, false)
				+ Utility.sqlInsertHelperInt(userid, true);
		System.out.println(query);
		int records = stmt.executeUpdate(query);
		if (records > 0) {
			insertStatus = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}
		
		return insertStatus;
	}

	public static boolean editParkingSpace(int ID, String city, String street, int postcode, int number, int userid, double lat, double lng) throws SQLException, Exception {
		boolean inserted = false;

		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "UPDATE parkingspace SET "
				+ "city = '" + city + "', "
				+ "street = '" + street + "', "
				+ "postcode = '" + postcode + "', "
				+ "number = '" + number + "', "
				+ "userid = '" + userid + "', "
				+ "lat = '" + lat + "', "
				+ "lng = '" + lng + "' WHERE ID = " + ID + ";";
		System.out.println(query);
		int records = stmt.executeUpdate(query);
		if (records > 0) {
			inserted = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}

		return inserted;
	}

	public static boolean deleteParkingSpace(int ID) throws SQLException, Exception {
		boolean inserted = false;

		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "DELETE FROM parkingspace WHERE ID = " + ID + ";"; 
		System.out.println(query);
		int records = stmt.executeUpdate(query);
		if (records > 0) {
			inserted = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}

		return inserted;
	}
	
	public static boolean editCar(int ID, String licenseplate) throws SQLException, Exception {
		boolean inserted = false;

		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "UPDATE car SET "
				+ "licenseplate = '" + licenseplate + "' WHERE ID = " + ID + ";";
		System.out.println(query);
		int records = stmt.executeUpdate(query);
		if (records > 0) {
			inserted = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}

		return inserted;
	}

	public static boolean deleteCar(int ID) throws SQLException, Exception {
		boolean inserted = false;

		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "DELETE FROM car WHERE ID = " + ID + ";"; 
		System.out.println(query);
		int records = stmt.executeUpdate(query);
		if (records > 0) {
			inserted = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}

		return inserted;
	}

	public static String getUser(int userid) throws SQLException, Exception {
		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "SELECT * FROM user WHERE ID="+Integer.toString(userid)+";";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		String string = Utility.constructJSONUserResponse(rs);
		if (dbConn != null) {
			dbConn.close();
		}
		
		return string;
	}
	
	public static boolean setBalance(int userid, int change) throws SQLException, Exception {
		boolean inserted = false;

		Connection dbConn = null;
		dbConn = DBConnection.createConnection();
		Statement stmt = dbConn.createStatement();
		String query = "UPDATE user SET balance = balance +" + Integer.toString(change) + " WHERE ID=" + Integer.toString(userid) + ";";
		System.out.println(query);
		int records = stmt.executeUpdate(query);
		if (records > 0) {
			inserted = true;
		}
		if (dbConn != null) {
			dbConn.close();
		}

		return inserted;
	}
}