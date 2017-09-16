package airparking.model;

import java.util.Date;

public class Booking {

	private int bookingID;
	private Date timestamp;
	private int parkingID;
	private int userID;
	private int offerID;
	public Booking(int bookingID, Date timestamp, int parkingID, int userID, int offerID) {
		super();
		this.bookingID = bookingID;
		this.timestamp = timestamp;
		this.parkingID = parkingID;
		this.userID = userID;
		this.offerID = offerID;
	}
	public int getBookingID() {
		return bookingID;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public int getParkingID() {
		return parkingID;
	}
	public int getUserID() {
		return userID;
	}
	public int getOfferID() {
		return offerID;
	}
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public void setParkingID(int parkingID) {
		this.parkingID = parkingID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public void setOfferID(int offerID) {
		this.offerID = offerID;
	}
	@Override
	public String toString() {
		return "Booking [bookingID=" + bookingID + ", timestamp=" + timestamp + ", parkingID=" + parkingID + ", userID="
				+ userID + ", offerID=" + offerID + "]";
	}
	
	
	
	
}
