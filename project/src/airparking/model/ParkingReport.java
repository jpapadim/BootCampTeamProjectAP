package airparking.model;

import java.util.ArrayList;
import java.util.Date;

public class ParkingReport {

	private Parking parking;
	private Schedule availability;
	private ArrayList<Advancedschedule> advancedschedule;
	private ArrayList<Offer> offer;
	private ArrayList<Booking> booking;
	private ArrayList<Schedule> bookingperiod;
	private ArrayList<Review> review;
	private double distance;
	private ArrayList<Date> availabilityTimeLine =new ArrayList<>();
	
	
	
	
	public ParkingReport(Parking parking, Schedule availability, ArrayList<Advancedschedule> advancedschedule,
			ArrayList<Offer> offer, ArrayList<Booking> booking, ArrayList<Schedule> bookingperiod,
			ArrayList<Review> review) {
		super();
		this.parking = parking;
		this.availability = availability;
		this.advancedschedule = advancedschedule;
		this.offer = offer;
		this.booking = booking;
		this.bookingperiod = bookingperiod;
		this.review = review;
		this.distance=0.0;
	}
	
	public ParkingReport(Parking parking, Schedule availability, ArrayList<Advancedschedule> advancedschedule,
			ArrayList<Offer> offer, ArrayList<Booking> booking, ArrayList<Schedule> bookingperiod,
			ArrayList<Review> review, double distance) {
		super();
		this.parking = parking;
		this.availability = availability;
		this.advancedschedule = advancedschedule;
		this.offer = offer;
		this.booking = booking;
		this.bookingperiod = bookingperiod;
		this.review = review;
		this.distance=distance;
	}
	
	
	public double getDistance() {
		return distance;
	}

	public ArrayList<Date> getAvailabilityTimeLine() {
		return availabilityTimeLine;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void setAvailabilityTimeLine(ArrayList<Date> availabilityTimeLine) {
		this.availabilityTimeLine = availabilityTimeLine;
	}

	public ArrayList<Schedule> getBookingperiod() {
		return bookingperiod;
	}


	public void setBookingperiod(ArrayList<Schedule> bookingperiod) {
		this.bookingperiod = bookingperiod;
	}


	public Parking getParking() {
		return parking;
	}
	public Schedule getAvailability() {
		return availability;
	}
	public ArrayList<Advancedschedule> getAdvancedschedule() {
		return advancedschedule;
	}
	public ArrayList<Offer> getOffer() {
		return offer;
	}
	public ArrayList<Booking> getBooking() {
		return booking;
	}
	public ArrayList<Review> getReview() {
		return review;
	}
	public void setParking(Parking parking) {
		this.parking = parking;
	}
	public void setAvailability(Schedule availability) {
		this.availability = availability;
	}
	public void setAdvancedschedule(ArrayList<Advancedschedule> advancedschedule) {
		this.advancedschedule = advancedschedule;
	}
	public void setOffer(ArrayList<Offer> offer) {
		this.offer = offer;
	}
	public void setBooking(ArrayList<Booking> booking) {
		this.booking = booking;
	}
	public void setReview(ArrayList<Review> review) {
		this.review = review;
	}
	
	
	
	
}
