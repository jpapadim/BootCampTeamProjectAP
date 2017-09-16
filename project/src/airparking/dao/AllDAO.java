package airparking.dao;

import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import airparking.connection.DB;
import airparking.model.*;

public class AllDAO {

	DB dataBase;
	Connection con;
	
	ArrayList<Integer> parkingIDs = new ArrayList<>();
	ArrayList<Double> distances = new ArrayList<>();

	public AllDAO() {
		dataBase = new DB();
	}

	public void openDBconnection() throws Exception {
		if (con == null) {
			dataBase.open();
			con = dataBase.getConnection();
		}
	}

	public void closeDBconnection() throws Exception {
		if (con != null) {
			dataBase.close();
			con = null;
		}
	}

	
	public ArrayList<Integer> getParkingIDs() {
		return parkingIDs;
	}

	public ArrayList<Double> getDistances() {
		return distances;
	}

	public void setParkingIDs(ArrayList<Integer> parkingIDs) {
		this.parkingIDs = parkingIDs;
	}

	public void setDistances(ArrayList<Double> distances) {
		this.distances = distances;
	}

	public User authenticateUser(String email, String password) throws Exception {
	
		try {
			openDBconnection();
			PreparedStatement stmt = con.prepareStatement(
					"SELECT *,owner.userID as ownerID  FROM user LEFT JOIN owner ON user.userID=owner.userID  WHERE email=?;");
			stmt.setString(1, email);
			ResultSet rsUser = stmt.executeQuery();
			if (rsUser.next()) {
				if (rsUser.getString("password").equals(password)) {
					User user = new User(rsUser.getInt(1), rsUser.getString("fname"), rsUser.getString("lname"),
							rsUser.getString("password"), rsUser.getString("email"));
					if (rsUser.getObject("ownerID") != null) {
						user = new Owner(user, rsUser.getString("account"), rsUser.getString("bank"),
								rsUser.getString("taxregistrationnumber"), rsUser.getString("taxoffice"));
					}
					stmt.close();
					return user;
				} else {
					stmt.close();
					throw new Exception("Incorrect password!");
				}
			} else {
				stmt.close();
				throw new Exception("No account found with the email entered!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			closeDBconnection();
		}
	}

	public ArrayList<User> getUsers() throws Exception {
		openDBconnection();
		Statement stmt = con.createStatement();
		ResultSet rsUsers = stmt.executeQuery(
				"SELECT user.*,owner.userID as ownerID,account,bank,taxregistrationnumber,taxoffice FROM user LEFT JOIN owner on user.userID=owner.userID;");
		ArrayList<User> array = new ArrayList<>();
		User user = null;
		while (rsUsers.next()) {
			user = new User(rsUsers.getInt("userID"), rsUsers.getString("fname"), rsUsers.getString("lname"),
					rsUsers.getString("password"), rsUsers.getString("email"));
			if (rsUsers.getObject("ownerID") != null) {
				user = new Owner(user, rsUsers.getString("account"), rsUsers.getString("bank"),
						rsUsers.getString("taxregistrationnumber"), rsUsers.getString("taxoffice"));
			}
			array.add(user);
		}
		rsUsers.close();
		stmt.close();
		closeDBconnection();
		return array;
	}
	
	public User getUser(int id) throws Exception {
		openDBconnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM user WHERE userID=?;");
		stmt.setInt(1, id);
		ResultSet rsUsers = stmt.executeQuery();
		User user = null;
		if (rsUsers.next()) {
			user = new User(rsUsers.getInt("userID"), rsUsers.getString("fname"), rsUsers.getString("lname"),
					rsUsers.getString("password"), rsUsers.getString("email"));
		}
		rsUsers.close();
		stmt.close();
		closeDBconnection();
		return user;
	}

	public ArrayList<Schedule> getAvailabilities() throws Exception {
		openDBconnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM availability;");
		ArrayList<Schedule> array = new ArrayList<>();
		while (rs.next()) {
			array.add(new Schedule(rs.getInt("parkingID"), rs.getBoolean("isSimple"), rs.getTimestamp("startDateTime"), rs.getTimestamp("endDateTime")));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}
	
	public ArrayList<Schedule> getBookingPeriods() throws Exception {
		openDBconnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM bookingperiod;");
		ArrayList<Schedule> array = new ArrayList<>();
		while (rs.next()) {
			array.add(new Schedule(rs.getInt("bookingID"), rs.getBoolean("isSimple"), rs.getTimestamp("startDateTime"), rs.getTimestamp("endDateTime")));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}

	public void getParkingIds(double distance, double lat, double lng, double offer) throws Exception {
		ArrayList<Integer> parkingIDs = new ArrayList<>();
		ArrayList<Double> distances = new ArrayList<>();
		openDBconnection();
		PreparedStatement stmt = con.prepareStatement("SELECT parking.parkingID, "
				+ "(6371 * 2 * ASIN(SQRT(POWER(SIN((parking.lat - abs(?)) * pi()/180 / 2),2) + COS(parking.lat * pi()/180 ) * COS(abs(?) * pi()/180) * POWER(SIN((parking.lng - ?) * pi()/180 / 2), 2) ))) AS distance "
				+ "FROM parking INNER JOIN offer ON (parking.parkingID=offer.parkingID) WHERE (offer.value<? AND offer.type='hour') HAVING distance<? ORDER BY distance;");
		stmt.setDouble(1, lat);
		stmt.setDouble(2, lat);
		stmt.setDouble(3, lng);
		stmt.setDouble(4, offer);
		stmt.setDouble(5, distance);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()){
			parkingIDs.add(rs.getInt("parkingID"));
			distances.add(rs.getDouble("distance"));
		}
		closeDBconnection();
		this.parkingIDs=parkingIDs;
		this.distances=distances;
		
	}

	public ArrayList<Integer> getParkingIds(int ownerID) throws Exception {
		ArrayList<Integer> parkingIDs = new ArrayList<>();
		openDBconnection();
		PreparedStatement stmt = con.prepareStatement("SELECT parking.parkingID "
				+ "FROM parking WHERE ownerID=?;");
		stmt.setInt(1, ownerID);
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			parkingIDs.add(rs.getInt("parkingID"));
		closeDBconnection();
		return parkingIDs;
	}
	public ArrayList<Parking> getParkings() throws Exception {
		openDBconnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM parking;");
		ArrayList<Parking> array = new ArrayList<>();
		while (rs.next()) {
			array.add(new Parking(rs.getInt("parkingID"),rs.getInt("ownerID"),rs.getString("address"), rs.getDouble("lat"), rs.getDouble("lng"),rs.getString("photo")));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}
	
	public ArrayList<Parking> getOpenParkings() throws Exception {
		openDBconnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM parking INNER JOIN availability ON (parking.parkingID=availability.parkingID) "
				+ "										WHERE enddatetime>=NOW();");
		
		ResultSet rs = stmt.executeQuery();
		ArrayList<Parking> array = new ArrayList<>();
		while (rs.next()) {
			array.add(new Parking(rs.getInt("parkingID"),rs.getInt("ownerID"),rs.getString("address"), rs.getDouble("lat"), rs.getDouble("lng"),rs.getString("photo")));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}

	public ArrayList<Offer> getOffers() throws Exception {
		openDBconnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM offer;");
		ArrayList<Offer> array = new ArrayList<>();
		while (rs.next()) {
			array.add(new Offer(rs.getInt("offerID"), rs.getString("type"),rs.getDouble("value"), rs.getInt("parkingID")));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}
	
	public Offer getOffer(int id) throws Exception {
		openDBconnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM offer WHERE offerID=?;");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		Offer offer=null;
		if (rs.next()) {
			offer=new Offer(rs.getInt("offerID"), rs.getString("type"),rs.getDouble("value"), rs.getInt("parkingID"));
		}
		stmt.close();
		closeDBconnection();
		 return offer; 
	}
	
	public ArrayList<Offer> getOffers(int parkingID) throws Exception {
		openDBconnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM offer WHERE parkingID=?;");
		stmt.setInt(1, parkingID);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Offer> offers=new ArrayList<>();
		while (rs.next()) {
			Offer offer=new Offer(rs.getInt("offerID"), rs.getString("type"),rs.getDouble("value"), rs.getInt("parkingID"));
			offers.add(offer);
		}
		stmt.close();
		closeDBconnection();
		 return offers; 
	}

	public ArrayList<Advancedschedule> getAdvancedschedules() throws Exception {
		openDBconnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM advancedschedule;");
		ArrayList<Advancedschedule> array = new ArrayList<>();
		while (rs.next()) {
			Date  start=new Date(rs.getTime("sTimeOfDay").getTime());
			Date  end=new Date(rs.getTime("eTimeOfDay").getTime());
			array.add(new Advancedschedule(rs.getInt("advschID"),rs.getInt("parkingID"), start, end,
					rs.getInt("sDayOfWeek"), rs.getInt("eDayOfWeek"), rs.getInt("sMonthOfYear"),
					rs.getInt("eMonthOfYear")));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}

	public ArrayList<Review> getReviews() throws Exception {
		openDBconnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM review;");
		ArrayList<Review> array = new ArrayList<>();
		while (rs.next()) {
			array.add(new Review(rs.getInt("reviewID"), rs.getString("comment"), rs.getInt("rating"),
					rs.getInt("parkingID"), rs.getInt("userID")));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}

	public ArrayList<Booking> getBookings() throws Exception {
		openDBconnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM booking;");
		ArrayList<Booking> array = new ArrayList<>();
		while (rs.next()) {
			array.add(new Booking(rs.getInt("bookingID"), new Date(rs.getTimestamp("timestamp").getTime()), rs.getInt("parkingID"),
					 rs.getInt("userID"),rs.getInt("offerID")));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}
	
	public ArrayList<Integer> getBookingsIds(int userID) throws Exception {
		openDBconnection();
		PreparedStatement stmt = con.prepareStatement("SELECT booking.bookingID FROM booking WHERE booking.userID=? ORDER BY booking.timestamp DESC;");
				stmt.setInt(1, userID);
				ResultSet rs=stmt.executeQuery();
		ArrayList<Integer> array = new ArrayList<>();
		while (rs.next()) {
			array.add(rs.getInt("bookingID"));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}

	public BookingReport getBookingReport(int bookingID) throws Exception {
		openDBconnection();

		PreparedStatement stmt = con.prepareStatement("SELECT parking.address, parking.lat, parking.lng, booking.offerID, booking.parkingID, booking.`timestamp`, booking.userID, bookingperiod.startdatetime, bookingperiod.enddatetime, bookingperiod.isSimple, `user`.email, `user`.fName, `user`.lName, owner.userID AS ownerID, owner.bank, owner.account, offer.type, offer.value   FROM air_parking.booking " 
+"INNER JOIN bookingperiod ON booking.bookingID=bookingperiod.bookingID "
+"INNER JOIN offer ON booking.offerID=offer.offerID "
+"INNER JOIN parking ON booking.parkingID=parking.parkingID "
+"INNER JOIN air_parking.`user` ON `user`.userID=parking.ownerID "
+"INNER JOIN air_parking.`owner` ON `user`.userID=owner.userID "
+"WHERE  booking.bookingID=?;");
stmt.setInt(1, bookingID);
ResultSet rs=stmt.executeQuery();
Booking booking;
Offer offer;
Schedule bookingPeriod;
Owner owner;
Parking parking;
if(rs.next()){
	int parkingID=rs.getInt("parkingID");
	int offerID=rs.getInt("offerID");
	int ownerID=rs.getInt("ownerID");
	booking=new Booking(bookingID, rs.getTimestamp("timestamp"), parkingID, rs.getInt("userID"), offerID);
	offer= new  Offer(offerID, rs.getString("type"),rs.getDouble("value"), parkingID);
	Date startDateTime=rs.getTimestamp("startdatetime");
	Date endDateTime=rs.getTimestamp("enddatetime");
	bookingPeriod=new Schedule(bookingID,rs.getBoolean("isSimple"),startDateTime, endDateTime);
	owner= new Owner(ownerID, rs.getString("fName"), rs.getString("lName"), "unknown", rs.getString("email"),  rs.getString("account"), rs.getString("bank"),
			"unknown", "unknown");
	parking=new Parking(parkingID, ownerID, rs.getString("address"), rs.getDouble("lat"), rs.getDouble("lng"), "");
	BookingReport br=new BookingReport(booking, offer, bookingPeriod, owner,parking);
	closeDBconnection();
	return br;
}
else{
	closeDBconnection();
	return null;
}
		
		
	}
	/**
	 * Saves student in the database
	 * 
	 * @param student,
	 *            Student
	 * @throws Exception
	 */
	public void registerUser(User user) throws Exception {

		openDBconnection();
		PreparedStatement stmt = con
				.prepareStatement("INSERT INTO user (fname,lname,password,email) VALUES (?,?,?,?);");
		stmt.setString(1, user.getfName());
		stmt.setString(2, user.getlName());
		stmt.setString(3, user.getPassword());
		stmt.setString(4, user.getEmail());
		stmt.executeUpdate();
		stmt.close();
		closeDBconnection();
	}// End of saveStudent

	public void registerUser(Owner owner) throws Exception {

		openDBconnection();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO user (fname,lname,password,email) VALUES (?,?,?,?);");
		stmt.setString(1, owner.getfName());
		stmt.setString(2, owner.getlName());
		stmt.setString(3, owner.getPassword());
		stmt.setString(4, owner.getEmail());
		stmt.executeUpdate();
		stmt = con.prepareStatement("INSERT INTO owner (userID,account,bank,taxregistrationnumber,taxoffice) VALUES ((SELECT MAX(userID) FROM user),?,?,?,?);");
		stmt.setString(1, owner.getAccount());
		stmt.setString(2, owner.getBank());
		stmt.setString(3, owner.getTaxregistrationnumber());
		stmt.setString(4, owner.getTaxoffice());
		stmt.executeUpdate();
		stmt.close();
		closeDBconnection();
	}//

	public void upgradeUserAccound(User user, Owner owner) throws Exception {

		DB dataBase = new DB();
		dataBase.open();
		Connection con = dataBase.getConnection();
		PreparedStatement stmt = con.prepareStatement(
				"INSERT INTO owner (userID,account,bank,taxregistrationnumber,taxoffice) VALUES (?,?,?,?,?);");
		stmt.setInt(1, user.getUserID());
		stmt.setString(2, owner.getAccount());
		stmt.setString(3, owner.getBank());
		stmt.setString(4, owner.getTaxregistrationnumber());
		stmt.setString(5, owner.getTaxoffice());
		stmt.executeUpdate();
		stmt.close();
		dataBase.close();

		/*
		 * You must finish method's body
		 */

	}

	public boolean isUserRegistered(String email) throws Exception {

		openDBconnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM user WHERE email=?;");
		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			closeDBconnection();
			return true;
		} else {
			closeDBconnection();
			return false;
		}

		/*
		 * You must finish method's body
		 */

	}// End of saveStudent
	
	public void registerParking(Parking parking ,Schedule availability, ArrayList<Advancedschedule> advancedschedules, ArrayList<Offer> offers) throws Exception {

		openDBconnection();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO parking (ownerID,address,lat,lng,photo) VALUES (?,?,?,?,?);");
		stmt.setInt(1, parking.getOwnerID());
		stmt.setString(2, parking.getAddress());
		stmt.setDouble(3, parking.getLat());
		stmt.setDouble(4, parking.getLng());
		stmt.setString(5, parking.getPhoto());
		stmt.executeUpdate();
		stmt.clearBatch();
		int isSimple=1;
		if(!advancedschedules.isEmpty())isSimple=0;
		stmt = con.prepareStatement("INSERT INTO availability (parkingID,isSimple,startdatetime,enddatetime) VALUES ((SELECT MAX(parkingID) FROM parking),?,?,?);");
		stmt.setInt(1,isSimple);
		stmt.setTimestamp(2, new java.sql.Timestamp(availability.getStartDateTime().getTime()));
		stmt.setTimestamp(3, new java.sql.Timestamp(availability.getEndDateTime().getTime()));
		
		stmt.executeUpdate();
		stmt.clearBatch();
		if(!advancedschedules.isEmpty()){
			for(int i=0;i<advancedschedules.size();i++){
				Advancedschedule ad=advancedschedules.get(i);
				stmt = con.prepareStatement("INSERT INTO advancedschedule (parkingID,sTimeOfDay,eTimeOfDay,sDayOfWeek,eDayOfWeek,sMonthOfYear,eMonthOfYear) VALUES ((SELECT MAX(parkingID) FROM parking),?,?,?,?,?,?);");
				stmt.setTime(1,new Time(ad.getsTimeOfDay().getTime()));
				stmt.setTime(2,new Time(ad.geteTimeOfDay().getTime()));
				System.out.println("ad.getsDayOfWeek() = "+ad.getsDayOfWeek());
				System.out.println("ad.geteDayOfWeek() = "+ad.geteDayOfWeek());
				System.out.println("ad.getsMonthOfYear() = "+ad.getsMonthOfYear());
				System.out.println("ad.geteMonthOfYear() = "+ad.geteMonthOfYear());
				stmt.setInt(3,ad.getsDayOfWeek());
				stmt.setInt(4,ad.geteDayOfWeek());
				stmt.setInt(5,ad.getsMonthOfYear());
				stmt.setInt(6,ad.geteMonthOfYear());
				stmt.executeUpdate();
				stmt.clearBatch();
			}
			
		}
	
		if(!offers.isEmpty()){
			for(int i=0;i<offers.size();i++){
				Offer of=offers.get(i);
				stmt = con.prepareStatement("INSERT INTO offer (type,value,parkingID) VALUES (?,?,(SELECT MAX(parkingID) FROM parking));");
				stmt.setString(1,of.getType());
				stmt.setDouble(2,of.getValue());
				stmt.executeUpdate();
				stmt.clearBatch();
			}
			
		}
		
		stmt.close();
		closeDBconnection();
	}//

	public void updateParking(Parking parking ,Schedule availability, ArrayList<Advancedschedule> advancedschedules, ArrayList<Offer> offers) throws Exception {

		openDBconnection();
		PreparedStatement stmt = con.prepareStatement("UPDATE `air_parking`.`parking` SET `address`=?, `lat`=?, `lng`=? WHERE `parkingID`=?;");
		stmt.setString(1, parking.getAddress());
		stmt.setDouble(2, parking.getLat());
		stmt.setDouble(3, parking.getLng());
		stmt.setInt(4, parking.getParkingID());
		stmt.executeUpdate();
		stmt.clearBatch();
		
		stmt = con.prepareStatement("DELETE FROM `air_parking`.`advancedschedule` WHERE `parkingID`=?;");
		stmt.setInt(1, parking.getParkingID());
		stmt.executeUpdate();
		
		stmt = con.prepareStatement("DELETE FROM `air_parking`.`availability` WHERE `parkingID`=?;");
		stmt.setInt(1, parking.getParkingID());
		stmt.executeUpdate();
		
		stmt = con.prepareStatement("DELETE FROM `air_parking`.`offer` WHERE `parkingID`=?;");
		stmt.setInt(1, parking.getParkingID());
		stmt.executeUpdate();
		
		int isSimple=1;
		if(!advancedschedules.isEmpty())isSimple=0;
		stmt = con.prepareStatement("INSERT INTO availability (parkingID,isSimple,startdatetime,enddatetime) VALUES (?,?,?,?);");
		stmt.setInt(1,parking.getParkingID());
		stmt.setInt(2,isSimple);
		stmt.setTimestamp(3, new java.sql.Timestamp(availability.getStartDateTime().getTime()));
		stmt.setTimestamp(4, new java.sql.Timestamp(availability.getEndDateTime().getTime()));
		
		stmt.executeUpdate();
		stmt.clearBatch();
		if(!advancedschedules.isEmpty()){
			for(int i=0;i<advancedschedules.size();i++){
				Advancedschedule ad=advancedschedules.get(i);
				stmt = con.prepareStatement("INSERT INTO advancedschedule (parkingID,sTimeOfDay,eTimeOfDay,sDayOfWeek,eDayOfWeek,sMonthOfYear,eMonthOfYear) VALUES (?,?,?,?,?,?,?);");
				stmt.setInt(1,parking.getParkingID());
				stmt.setTime(2,new Time(ad.getsTimeOfDay().getTime()));
				stmt.setTime(3,new Time(ad.geteTimeOfDay().getTime()));
				System.out.println("ad.getsDayOfWeek() = "+ad.getsDayOfWeek());
				System.out.println("ad.geteDayOfWeek() = "+ad.geteDayOfWeek());
				System.out.println("ad.getsMonthOfYear() = "+ad.getsMonthOfYear());
				System.out.println("ad.geteMonthOfYear() = "+ad.geteMonthOfYear());
				stmt.setInt(4,ad.getsDayOfWeek());
				stmt.setInt(5,ad.geteDayOfWeek());
				stmt.setInt(6,ad.getsMonthOfYear());
				stmt.setInt(7,ad.geteMonthOfYear());
				stmt.executeUpdate();
				stmt.clearBatch();
			}
			
		}
	
		if(!offers.isEmpty()){
			for(int i=0;i<offers.size();i++){
				Offer of=offers.get(i);
				stmt = con.prepareStatement("INSERT INTO offer (type,value,parkingID) VALUES (?,?,?);");
				stmt.setString(1,of.getType());
				stmt.setDouble(2,of.getValue());
				stmt.setInt(3,parking.getParkingID());
				stmt.executeUpdate();
				stmt.clearBatch();
			}
			
		}
		
		stmt.close();
		closeDBconnection();
	}//
	

public ParkingReport getParkingReport(int parkingID) throws Exception {
	openDBconnection();
	PreparedStatement stmt = con.prepareStatement("SELECT `parking`.`ownerID`, `parking`.`address`, `parking`.`lat`, `parking`.`lng`, `parking`.`photo`, "+
			 											 "`availability`.`issimple`, `availability`.`startdatetime`, `availability`.`enddatetime` "+
												  "FROM   `air_parking`.`parking` "+
												  "INNER JOIN `air_parking`.`availability` ON  `parking`.`parkingID`=`availability`.`parkingID` "+
											      "WHERE  `parking`.`parkingID` = ?;");
	stmt.setInt(1, parkingID);
	ResultSet rs=stmt.executeQuery();
	boolean isSimple;
	Parking parking;
	Schedule availability;
	if(rs.next()){
		isSimple=rs.getBoolean("issimple");
		parking=new Parking(parkingID,rs.getInt("ownerID"),rs.getString("address"),rs.getDouble("lat"),rs.getDouble("lng"),rs.getString("photo"));
		availability=new Schedule(parkingID,isSimple,rs.getTimestamp("startdatetime"),rs.getTimestamp("enddatetime"));
	}
	else return null;
	rs.close();
	ArrayList<Advancedschedule> advancedschedule=new ArrayList<>();
	ArrayList<Offer> offer=new ArrayList<>();
	ArrayList<Booking> booking=new ArrayList<>();
	ArrayList<Schedule> bookingperiod=new ArrayList<>();
	ArrayList<Review> review=new ArrayList<>();
	if(!isSimple){
		stmt.clearBatch();
		stmt = con.prepareStatement("SELECT advancedschedule.advschID, advancedschedule.sTimeOfDay, advancedschedule.eTimeOfDay, advancedschedule.sDayOfWeek, advancedschedule.eDayOfWeek, advancedschedule.sMonthOfYear, advancedschedule.eMonthOfYear "+ 
									"FROM  air_parking.advancedschedule "+ 
									"WHERE advancedschedule.parkingID = ?;");
		stmt.setInt(1, parkingID);
		rs=stmt.executeQuery();
		while(rs.next()){
			advancedschedule.add(new Advancedschedule(rs.getInt("advschID"),parkingID,new Date(rs.getTime("sTimeOfDay").getTime()),
					new Date(rs.getTime("eTimeOfDay").getTime()),
					rs.getInt("sDayOfWeek"),
					rs.getInt("eDayOfWeek"),
					rs.getInt("sMonthOfYear"),
					rs.getInt("eMonthOfYear")));
		}
		rs.close();
	}
	
	stmt.clearBatch();
	stmt = con.prepareStatement("SELECT `offer`.`offerID`, `offer`.`type`, `offer`.`value` "+ 
								"FROM  `air_parking`.`offer` "+ 
								"WHERE `offer`.`parkingID` = ?;");
	stmt.setInt(1, parkingID);
	rs=stmt.executeQuery();
	while(rs.next()){
		offer.add(new Offer(rs.getInt("offerID"), rs.getString("type"), rs.getDouble("value"),parkingID));
	}
	rs.close();
	
	stmt.clearBatch();
	stmt = con.prepareStatement("SELECT `booking`.`bookingID`, `booking`.`offerID`, `booking`.`timestamp`, `booking`.`userID`, `bookingperiod`.`startdatetime`, `bookingperiod`.`enddatetime`, `bookingperiod`.`isSimple` "+
								"FROM  `air_parking`.`booking` INNER JOIN  `air_parking`.`bookingperiod` ON `booking`.`bookingID`=`bookingperiod`.`bookingID` "+ 
								"WHERE `booking`.`parkingID` = ?;");
	stmt.setInt(1, parkingID);
	rs=stmt.executeQuery();
	while(rs.next()){
		int bid=rs.getInt("bookingID");
		booking.add(new Booking(bid, new Date(rs.getTimestamp("timestamp").getTime()), parkingID, rs.getInt("userID"), rs.getInt("offerID")));
		bookingperiod.add(new Schedule(bid, rs.getBoolean("issimple"),rs.getTimestamp("startdatetime"),rs.getTimestamp("enddatetime")));
	}
	rs.close();
	
	stmt.clearBatch();
	stmt = con.prepareStatement("SELECT `review`.`reviewID`, `review`.`userID`, `review`.`rating`, `review`.`comment` "+
								"FROM  `air_parking`.`review` "+ 
								"WHERE `review`.`parkingID` = ?;");
	stmt.setInt(1, parkingID);
	rs=stmt.executeQuery();
	while(rs.next()){
		review.add(new Review(rs.getInt("reviewID"), rs.getString("comment"), rs.getInt("rating"), parkingID, rs.getInt("userID")));
	}
	rs.close();
	stmt.close();
	closeDBconnection();
	ParkingReport parkingReport = new ParkingReport(parking, availability, advancedschedule, offer, booking, bookingperiod, review);
	return parkingReport;
 }

public void deleteOwner(int id) throws Exception{
	openDBconnection();
	 PreparedStatement stmt;
	 ResultSet rs;
		 
		ArrayList<Integer> parkingIDs = new ArrayList<>();
		stmt = con.prepareStatement("SELECT parking.parkingID FROM parking WHERE ownerID=?;");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		while (rs.next()) parkingIDs.add(rs.getInt("parkingID"));
		rs.close();
		for(Integer parkingID:parkingIDs){
			ArrayList<Integer> bookingIDs = new ArrayList<>();
			stmt = con.prepareStatement("SELECT booking.bookingID FROM booking WHERE parkingID=?;");
			stmt.setInt(1, parkingID);
			rs = stmt.executeQuery();
			while (rs.next()) bookingIDs.add(rs.getInt("bookingID"));
			rs.close();
			for(Integer bookingID:bookingIDs){
				stmt = con.prepareStatement("DELETE FROM `air_parking`.`bookingperiod` WHERE `bookingID`=?;");
				stmt.setInt(1, bookingID);
				stmt.executeUpdate();
			}
			stmt = con.prepareStatement("DELETE FROM `air_parking`.`booking` WHERE `parkingID`=?;");
			stmt.setInt(1, parkingID);
			stmt.executeUpdate();
			
			stmt = con.prepareStatement("DELETE FROM `air_parking`.`advancedschedule` WHERE `parkingID`=?;");
			stmt.setInt(1, parkingID);
			stmt.executeUpdate();
			
			stmt = con.prepareStatement("DELETE FROM `air_parking`.`availability` WHERE `parkingID`=?;");
			stmt.setInt(1, parkingID);
			stmt.executeUpdate();
			
			stmt = con.prepareStatement("DELETE FROM `air_parking`.`offer` WHERE `parkingID`=?;");
			stmt.setInt(1, parkingID);
			stmt.executeUpdate();
			
			stmt = con.prepareStatement("DELETE FROM `air_parking`.`review` WHERE `parkingID`=?;");
			stmt.setInt(1, parkingID);
			stmt.executeUpdate();
		}
		stmt = con.prepareStatement("DELETE FROM `air_parking`.`parking` WHERE `ownerID`=?;");
		stmt.setInt(1, id);
		stmt.executeUpdate();
		
	 stmt = con.prepareStatement("DELETE FROM `air_parking`.`owner` WHERE `userID`=?;");
	 stmt.setInt(1, id);
	 stmt.executeUpdate();
	 
	
	 
	 
	 
	 closeDBconnection();
}
 public void deleteUser(int id) throws Exception{
	 openDBconnection();
	 PreparedStatement stmt;
	 ResultSet rs;
	 boolean isOwner=false;
	 stmt = con.prepareStatement("SELECT `userID` FROM `air_parking`.`owner` WHERE `userID`=?;");
	 stmt.setInt(1, id);
	 rs = stmt.executeQuery();
	 
	 if(rs.next()) isOwner=true;
	 rs.close();
		 
	if(isOwner){ 
		ArrayList<Integer> parkingIDs = new ArrayList<>();
		stmt = con.prepareStatement("SELECT parking.parkingID FROM parking WHERE ownerID=?;");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		while (rs.next()) parkingIDs.add(rs.getInt("parkingID"));
		rs.close();
		for(Integer parkingID:parkingIDs){
			ArrayList<Integer> bookingIDs = new ArrayList<>();
			stmt = con.prepareStatement("SELECT booking.bookingID FROM booking WHERE parkingID=?;");
			stmt.setInt(1, parkingID);
			rs = stmt.executeQuery();
			while (rs.next()) bookingIDs.add(rs.getInt("bookingID"));
			rs.close();
			for(Integer bookingID:bookingIDs){
				stmt = con.prepareStatement("DELETE FROM `air_parking`.`bookingperiod` WHERE `bookingID`=?;");
				stmt.setInt(1, bookingID);
				stmt.executeUpdate();
			}
			stmt = con.prepareStatement("DELETE FROM `air_parking`.`booking` WHERE `parkingID`=?;");
			stmt.setInt(1, parkingID);
			stmt.executeUpdate();
			
			stmt = con.prepareStatement("DELETE FROM `air_parking`.`advancedschedule` WHERE `parkingID`=?;");
			stmt.setInt(1, parkingID);
			stmt.executeUpdate();
			
			stmt = con.prepareStatement("DELETE FROM `air_parking`.`availability` WHERE `parkingID`=?;");
			stmt.setInt(1, parkingID);
			stmt.executeUpdate();
			
			stmt = con.prepareStatement("DELETE FROM `air_parking`.`offer` WHERE `parkingID`=?;");
			stmt.setInt(1, parkingID);
			stmt.executeUpdate();
			
			stmt = con.prepareStatement("DELETE FROM `air_parking`.`review` WHERE `parkingID`=?;");
			stmt.setInt(1, parkingID);
			stmt.executeUpdate();
		}
		stmt = con.prepareStatement("DELETE FROM `air_parking`.`parking` WHERE `ownerID`=?;");
		stmt.setInt(1, id);
		stmt.executeUpdate();
		
	 stmt = con.prepareStatement("DELETE FROM `air_parking`.`owner` WHERE `userID`=?;");
	 stmt.setInt(1, id);
	 stmt.executeUpdate();
	 }
	ArrayList<Integer> bookingIDs = new ArrayList<>();
	stmt = con.prepareStatement("SELECT booking.bookingID FROM booking WHERE userID=?;");
	stmt.setInt(1, id);
	rs = stmt.executeQuery();
	while (rs.next()) bookingIDs.add(rs.getInt("bookingID"));
	rs.close();
	for(Integer bookingID:bookingIDs){
		stmt = con.prepareStatement("DELETE FROM `air_parking`.`bookingperiod` WHERE `bookingID`=?;");
		stmt.setInt(1, bookingID);
		stmt.executeUpdate();
	}
	stmt = con.prepareStatement("DELETE FROM `air_parking`.`booking` WHERE `userID`=?;");
	stmt.setInt(1, id);
	stmt.executeUpdate();
	
	stmt = con.prepareStatement("DELETE FROM `air_parking`.`review` WHERE `userID`=?;");
	stmt.setInt(1, id);
	stmt.executeUpdate();
	
	 stmt = con.prepareStatement("DELETE FROM `air_parking`.`user` WHERE `userID`=?;");
	 stmt.setInt(1, id);
	 stmt.executeUpdate();
	 
	 
	 
	 closeDBconnection();
 }
	
	public Double getOffersByParkingID(int id) throws Exception {
		openDBconnection();
		PreparedStatement stmt = con.prepareStatement("select offer.value from offer inner join parking on offer.parkingID=parking.parkingID where parking.parkingID=?;");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		Double offer=0.0;
		while(rs.next()){
			offer = rs.getDouble("value");
		
		}
		rs.close();
		stmt.close();
		closeDBconnection();
		return offer;
	}
	
public ArrayList<Parking> searchParkingResults(double lat, double lng) throws Exception {

		
		ArrayList<Parking> array = getParkings();

		ArrayList<Parking> results =  new ArrayList<>();
		for(int i=0; i<array.size(); i++){
			if(array.get(i).findDistance(lat,lng)<5000){
				results.add(array.get(i));
			}
			
		}
		
		return results;
	}
public ArrayList<Parking> searchParkingResultsNotIncluded(double lat, double lng) throws Exception {

	
	ArrayList<Parking> array = getParkingsWithoutBookingPeriods();

	ArrayList<Parking> results =  new ArrayList<>();
	for(int i=0; i<array.size(); i++){
		if(array.get(i).findDistance(lat,lng)<1500){
			results.add(array.get(i));
		}
		
	}
	
	return results;
}
	
	public ArrayList<Parking> advancedSearchParkingResults(double lat, double lng, double maxDist , double offer) throws Exception {

		
		ArrayList<Parking> array = getParkingsBySpecificOffer(offer);

		ArrayList<Parking> results =  new ArrayList<>();
		for(int i=0; i<array.size(); i++){
			if(array.get(i).findDistance(lat,lng)<maxDist){
				results.add(array.get(i));
			}
			
		}
		
		return results;
	}
	
	public ArrayList<Parking> advancedSearchParkingResultsWithoutBookingPeriods(double lat, double lng, double maxDist , double offer) throws Exception {

		
		ArrayList<Parking> array = getParkingsBySpecificOfferWithoutBookingPeriods(offer);

		ArrayList<Parking> results =  new ArrayList<>();
		for(int i=0; i<array.size(); i++){
			if(array.get(i).findDistance(lat,lng)<maxDist){
				results.add(array.get(i));
			}
			
		}
		
		return results;
	}
	
	public ArrayList<Schedule> getParkingsOfAvailability() throws Exception{
		openDBconnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM availability;");
		ArrayList<Schedule> array = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.m");
		while (rs.next()) {
			//System.out.println("rs.getString(\"startdatetime\")->" + rs.getString("startdatetime") );
			array.add(new Schedule(rs.getInt("parkingID"), rs.getBoolean("issimple"), dateFormat.parse(rs.getString("startdatetime")) , dateFormat.parse(rs.getString("enddatetime")) ) );
		}
		stmt.close();
		closeDBconnection();
		return array;
	}
	
	public ArrayList<Schedule> getParkingsOfBookingPeriods() throws Exception{
		openDBconnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT booking.parkingID, bookingperiod.issimple ,bookingperiod.startdatetime, bookingperiod.enddatetime FROM bookingperiod JOIN booking ON booking.bookingID=bookingperiod.bookingID;");
		ArrayList<Schedule> array = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.m");
		while (rs.next()) {
			array.add(new Schedule(rs.getInt("parkingID"), rs.getBoolean("issimple"),dateFormat.parse(rs.getString("startdatetime")), dateFormat.parse(rs.getString("enddatetime"))));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}
	
	public ArrayList<Parking> getParkingsBySpecificOffer(double offer) throws Exception{
		openDBconnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM parking WHERE parkingID IN(SELECT parkingID FROM  offer WHERE value<?);");
		stmt.setDouble(1, offer);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Parking> array = new ArrayList<>();
		while (rs.next()) {
			array.add(new Parking(rs.getInt("parkingID"),rs.getInt("ownerID"),rs.getString("address"), rs.getDouble("lat"), rs.getDouble("lng"),rs.getString("photo")));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}
	
	
	public ArrayList<Parking> getParkingsBySpecificOfferWithoutBookingPeriods(double offer) throws Exception{
		openDBconnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM parking WHERE ((parkingID IN(SELECT parkingID FROM  offer WHERE value<?))AND (parking.parkingID not in (select booking.parkingID from booking))) ;");
		stmt.setDouble(1, offer);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Parking> array = new ArrayList<>();
		while (rs.next()) {
			array.add(new Parking(rs.getInt("parkingID"),rs.getInt("ownerID"),rs.getString("address"), rs.getDouble("lat"), rs.getDouble("lng"),rs.getString("photo")));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}
	
	public ArrayList<Integer> getParkingIdsOfAvailability() throws Exception{
		openDBconnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT parkingID FROM availability;");
		ArrayList<Integer> availPIds= new ArrayList<>();
		while (rs.next()) {
			availPIds.add(rs.getInt("parkingID"));
		}
		stmt.close();
		closeDBconnection();
		return availPIds;
	}
	public ArrayList<Integer> getParkingIDsOfBookingPeriods() throws Exception{
		openDBconnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT booking.parkingID FROM booking JOIN bookingperiod ON booking.bookingID=bookingperiod.bookingID;");
		ArrayList<Integer> array = new ArrayList<>();
		while (rs.next()) {
			array.add(rs.getInt("parkingID"));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}
	
	public ArrayList<Schedule> getAvailabilitiesWithoutBookingPeriods() throws Exception{
		openDBconnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM availability WHERE availability.parkingID NOT IN (SELECT booking.parkingID FROM booking);");
		ArrayList<Schedule> array = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.m");
		while (rs.next()) {
			array.add(new Schedule(rs.getInt("parkingID"), rs.getBoolean("issimple"),dateFormat.parse(rs.getString("startdatetime")), dateFormat.parse(rs.getString("enddatetime"))));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}
	public ArrayList<Parking> getParkingsWithoutBookingPeriods() throws Exception{
		openDBconnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM parking WHERE parking.parkingID NOT IN (SELECT booking.parkingID FROM booking);");
		ArrayList<Parking> array = new ArrayList<>();
		while (rs.next()) {
			array.add(new Parking(rs.getInt("parkingID"),rs.getInt("ownerID"),rs.getString("address"), rs.getDouble("lat"), rs.getDouble("lng"),rs.getString("photo")));
		}
		stmt.close();
		closeDBconnection();
		return array;
	}
	public void book(Booking booking, Schedule bookingPeriod) throws Exception {
		openDBconnection();
		PreparedStatement stmt = con
				.prepareStatement("INSERT INTO booking (parkingID,userID,offerID,timestamp) VALUES (?,?,?,?);");
		stmt.setInt(1, booking.getParkingID());
		stmt.setInt(2, booking.getUserID());
		stmt.setInt(3, booking.getOfferID());
		stmt.setTimestamp(4, new java.sql.Timestamp(booking.getTimestamp().getTime()));
		stmt.executeUpdate();
		
		stmt = con
				.prepareStatement("INSERT INTO bookingperiod (bookingID,isSimple,startdatetime,enddatetime) VALUES ((SELECT MAX(bookingID) FROM booking),?,?,?);");
		stmt.setBoolean(1, true);
		stmt.setTimestamp(2, new java.sql.Timestamp(bookingPeriod.getStartDateTime().getTime()));
		stmt.setTimestamp(3, new java.sql.Timestamp(bookingPeriod.getEndDateTime().getTime()));
		stmt.executeUpdate();
		stmt.close();
		closeDBconnection();
	}
}// End of class
