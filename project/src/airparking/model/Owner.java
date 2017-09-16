package airparking.model;

public class Owner extends User {

	private String account;
	private String bank;
	private String taxregistrationnumber;
	private String taxoffice;
	
	public Owner(int userID, String fName, String lName, String password, String email, String account, String bank,
			String taxregistrationnumber, String taxoffice) {
		super(userID, fName, lName, password, email);
		this.account = account;
		this.bank = bank;
		this.taxregistrationnumber = taxregistrationnumber;
		this.taxoffice = taxoffice;
	}
	
	public Owner(User user, String account, String bank,
			String taxregistrationnumber, String taxoffice) {
		super(user.getUserID(), user.getfName(), user.getlName(), user.getPassword(), user.getEmail());
		this.account = account;
		this.bank = bank;
		this.taxregistrationnumber = taxregistrationnumber;
		this.taxoffice = taxoffice;
	}

	public String getAccount() {
		return account;
	}

	public String getBank() {
		return bank;
	}

	public String getTaxregistrationnumber() {
		return taxregistrationnumber;
	}

	public String getTaxoffice() {
		return taxoffice;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public void setTaxregistrationnumber(String taxregistrationnumber) {
		this.taxregistrationnumber = taxregistrationnumber;
	}

	public void setTaxoffice(String taxoffice) {
		this.taxoffice = taxoffice;
	}

	@Override
	public String toString() {
		return "Owner [account=" + account + ", bank=" + bank + ", taxregistrationnumber=" + taxregistrationnumber
				+ ", taxoffice=" + taxoffice + ", getfName()=" + getfName() + ", getlName()=" + getlName()
				+ ", getPassword()=" + getPassword() + ", getEmail()=" + getEmail() + "]";
	}

	
	
	
	
}
