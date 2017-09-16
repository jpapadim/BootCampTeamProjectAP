package airparking.model;

public class User {

	private int userID;
	private String fName;
	private String lName;
	private String password;
	private String email;
	public User(int userID, String fName, String lName, String password, String email) {
		super();
		this.userID = userID;
		this.fName = fName;
		this.lName = lName;
		this.password = password;
		this.email = email;
	}
	public int getUserID() {
		return userID;
	}
	public String getfName() {
		return fName;
	}
	public String getlName() {
		return lName;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [userID=" + userID + ", fName=" + fName + ", lName=" + lName + ", password=" + password
				+ ", email=" + email + "]";
	}
	
	
	
	
	

}
