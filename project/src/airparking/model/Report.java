package airparking.model;

public class Report {

	private	int parkingId;
	private String address;
	private double lat;
	private double lng;
	private String photo;
	private String name;
	private String comment;
	private int rating;
	private String duration;
	private String lastBooking;
	private String offerType;
	private double value;
	private double totalCost;
	
	public Report (int parkingId, String address, double lat, double lng, String photo, String fName, String lName,
			String comment, int rating, String duration, String lastBooking, String offerType, double value, double totalCost) {
		super();
		this.parkingId = parkingId;
		this.address = address;
		this.lat = lat;
		this.lng = lng;
		this.photo = photo;
		this.name = fName+" "+lName;
		this.comment = comment;
		this. rating = rating;
		this.duration = duration;
		this.lastBooking = lastBooking;
		this.offerType = offerType;
		this.value = value;
		this.totalCost = totalCost;
	}
	public int getParkingID() {
		return parkingId;
	}
	public String getAddress() {
		return address;
	}
	public double getLat() {
		return lat;
	}
	public double getLng() {
		return lng;
	}
	public String getPhoto() {
		return photo;
	}
	public String getName() {
		return name;
	}
	public String getComment() {
		return comment;
	}
	public int getRating() {
		return rating;
	}
	public String getduration() {
		return duration;
	}
	public String getLastBooking() {
		return lastBooking;
	}
	public String getOfferType() {
		return offerType;
	}
	public double getvalue() {
		return value;
	}
	public double getTotalCost(){
		return totalCost;
	}
}
