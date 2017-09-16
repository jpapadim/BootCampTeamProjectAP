package airparking.model;

public class BookingReport {

	private Booking booking;
	private Offer offer;
	private Schedule bookingPeriod;
	private Owner owner;
	private Parking parking;
	public BookingReport(Booking booking, Offer offer, Schedule bookingPeriod, Owner owner, Parking parking) {
		super();
		this.booking = booking;
		this.offer = offer;
		this.bookingPeriod = bookingPeriod;
		this.owner = owner;
		this.parking = parking;
	}
	public Booking getBooking() {
		return booking;
	}
	public Offer getOffer() {
		return offer;
	}
	public Schedule getBookingPeriod() {
		return bookingPeriod;
	}
	public Owner getOwner() {
		return owner;
	}
	public Parking getParking() {
		return parking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	public void setBookingPeriod(Schedule bookingPeriod) {
		this.bookingPeriod = bookingPeriod;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	public void setParking(Parking parking) {
		this.parking = parking;
	}
	
	
	
}
