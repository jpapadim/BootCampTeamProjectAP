package airparking.model;

public class Review {

	private int reviewID;
	private String comment;
	private int rating;
	private int parkingID;
	private int userID;
	public Review(int reviewID, String comment, int rating, int parkingID, int userID) {
		super();
		this.reviewID = reviewID;
		this.comment = comment;
		this.rating = rating;
		this.parkingID = parkingID;
		this.userID = userID;
	}
	public int getReviewID() {
		return reviewID;
	}
	public String getComment() {
		return comment;
	}
	public int getRating() {
		return rating;
	}
	public int getParkingID() {
		return parkingID;
	}
	public int getUserID() {
		return userID;
	}
	public void setReviewID(int reviewID) {
		this.reviewID = reviewID;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public void setParkingID(int parkingID) {
		this.parkingID = parkingID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	@Override
	public String toString() {
		return "Review [reviewID=" + reviewID + ", comment=" + comment + ", rating=" + rating + ", parkingID="
				+ parkingID + ", userID=" + userID + "]";
	}
	
	
	
}
