package airparking.model;

import java.util.ArrayList;

public class SearchResult {

	private ArrayList<ParkingReport> results=new ArrayList<>();
	
	public SearchResult(ArrayList<ParkingReport> results) {
		super();
		this.results = results;
	}
	
	public ArrayList<ParkingReport> getResults() {
		return results;
	}
	
	public void setResults(ArrayList<ParkingReport> results) {
		this.results = results;
	}

	
	
	
	
	
}
