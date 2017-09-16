package airparking.model;

public class Parking {

	private int parkingID;
	private int ownerID;
	private String address;
	private double lat;
	private double lng;
	private String photo;
	public Parking(int parkingID, int ownerID, String address, double lat, double lng, String photo) {
		super();
		this.parkingID = parkingID;
		this.ownerID = ownerID;
		this.address = address;
		this.lat = lat;
		this.lng = lng;
		this.photo = photo;
	}
	public int getParkingID() {
		return parkingID;
	}
	public int getOwnerID() {
		return ownerID;
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
	public void setParkingID(int parkingID) {
		this.parkingID = parkingID;
	}
	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public double distanceFrom(double lat, double lng){
		double d=6371 * 2 * Math.asin(Math.sqrt(
	            Math.pow(Math.sin((this.lat - Math.abs(lat)) * Math.PI/180 / 2),
	            2) + Math.cos(this.lat * Math.PI/180 ) * Math.cos(Math.abs(lat) *
	            		Math.PI/180) * Math.pow(Math.sin((this.lng - lng) *
	            				Math.PI/180 / 2), 2) ));
		return d;
	}
	
@Override
	public String toString() {
		return "Parking [parkingID=" + parkingID + ", ownerID=" + ownerID + ", address=" + address + ", lat=" + lat
				+ ", lng=" + lng + ", photo=" + photo + "]";
	}
public double findDistance(double lat, double lng){
		
		double theta = this.lng - lng;
		double dist = Math.sin((this.lat)* Math.PI / 180.0) * Math.sin((lat)* Math.PI / 180.0) + Math.cos((this.lat)* Math.PI / 180.0) * Math.cos((lat)* Math.PI / 180.0) * Math.cos((theta)* Math.PI / 180.0);
		dist = Math.acos(dist);
		dist = ((dist)* 180 / Math.PI);
		dist = dist * 60 * 1.1515*1.609344*1000; //meters

		dist = Math.floor(dist * 100) / 100;
		
		return (dist);		
		
	}	
}
