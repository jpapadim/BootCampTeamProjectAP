package airparking.model;

public class Offer {

	private int offerID;
	private String type;
	private Double value;
	private int parkingID;
	public Offer(int offerID, String type, Double value, int parkingID) {
		super();
		this.offerID = offerID;
		this.type = type;
		this.value = value;
		this.parkingID = parkingID;
	}
	public int getOfferID() {
		return offerID;
	}
	public String getType() {
		return type;
	}
	public double getValue() {
		return value;
	}
	public int getParkingID() {
		return parkingID;
	}
	public void setOfferID(int offerID) {
		this.offerID = offerID;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public void setParkingID(int parkingID) {
		this.parkingID = parkingID;
	}
	@Override
	public String toString() {
		return "Offer [offerID=" + offerID + ", type=" + type + ", value=" + value + ", parkingID=" + parkingID + "]";
	}
	
	
}
