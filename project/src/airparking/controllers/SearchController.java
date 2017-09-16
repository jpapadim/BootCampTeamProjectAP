package airparking.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import airparking.dao.AllDAO;
import airparking.model.Parking;
import airparking.model.Schedule;

/**
 * Servlet implementation class SearchController
 */
@WebServlet("/searchcontroller")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/intro.jsp");
		boolean incompleteSearch=false;
		if(request.getParameter("lat")==null || request.getParameter("lat")=="" || request.getParameter("lng")==null || request.getParameter("lng")==""){
			 request.setAttribute("errorsearch", "&nbsp;&nbsp;Παρακαλώ εισάγεται μια έγκυρη διεύθυνση (Τουλάχιστον 3 χαρακτήρες)");
			 incompleteSearch=true;
		 }
		//String search=request.getParameter("Search");
		Date sDate =new Date();
		Date eDate =new Date();
		
		boolean completed=true;
		
		boolean checkAdvanced = !(request.getParameter("checkAdvancedSearch") == null
				|| !((request.getParameter("checkAdvancedSearch")).equals("true")));
		//advanced
		
		/*if(search==null){
			request.setAttribute("errorsearch", "&nbsp;&nbsp;Please enter a valid address (At least 3 characters)");
		}*/
		
		
		
		
			//simple
		if(checkAdvanced==false&&incompleteSearch==false){
			eDate.setTime(sDate.getTime()+14400000);
			Schedule schedule=new Schedule(0, true, sDate, eDate);
			double lat=Double.parseDouble(request.getParameter("lat"));
			double lng=Double.parseDouble(request.getParameter("lng"));
			
			AllDAO dao=new AllDAO();
					
			try {
				ArrayList<Parking> results =  dao.searchParkingResults(lat, lng);
				ArrayList<Parking> finalResults =  new ArrayList<>();
				ArrayList<Parking> notIncluded=dao.searchParkingResultsNotIncluded(lat, lng);
				for(int i=0; i<results.size(); i++) {
					System.out.println("park:"+results.get(i).getParkingID());
					schedule.setId(results.get(i).getParkingID());
						
					if(schedule.isAvailable(dao.getParkingsOfAvailability(), dao.getParkingsOfBookingPeriods())){
						
						finalResults.add(results.get(i));
						for(int k=0; k<finalResults.size(); k++){
							//System.out.println("parkFinal:"+results.get(k).getParkingID());
							}
											
					}
				}
				for(int i=0; i<notIncluded.size(); i++) {
					System.out.println("park:"+notIncluded.get(i).getParkingID());
					schedule.setId(notIncluded.get(i).getParkingID());
						
					if(schedule.isAvailableWithoutBookingPeriod(dao.getParkingsOfAvailability())){
						
						finalResults.add(notIncluded.get(i));
						for(int k=0; k<finalResults.size(); k++){
							//System.out.println("parkFinal:"+results.get(k).getParkingID());
							}
											
					}
				}
				
				request.setAttribute("resultset", finalResults);

				request.setAttribute("request",schedule);
				request.setAttribute("lat", lat);
				request.setAttribute("lng", lng);
				rd = request.getRequestDispatcher("/jsp/ParkingSearchResults.jsp");
			} catch (Exception f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
			}
			
		}
		
		//advanced
		else{
			if(incompleteSearch==false){

				double lat=Double.parseDouble(request.getParameter("lat"));
				double lng=Double.parseDouble(request.getParameter("lng"));
			String maxDistString=(request.getParameter("maxDist"));
			String offerString=request.getParameter("maxOffer");
			String s = request.getParameter("datetimepicker1");
			String e = request.getParameter("datetimepicker2");
			
			if(maxDistString==null||maxDistString.length()<2 || maxDistString.isEmpty()){
				request.setAttribute("errordist", "Εισάγετε τουλάχιστον 2 χαρακτήρες!");
				completed=false;
			}
			if(offerString==null ||offerString.length()<1 || offerString.isEmpty()){
				request.setAttribute("erroroffer", "Εισάγεται τουλάχιστον 1 χαρακτήρα!");
				completed=false;
			}
			if(s==null){
				request.setAttribute("errorstart", "Παρακαλώ εισάγετε ημέρα και ώρα έναρξης χρήσης");
				completed=false;
			}
			if(e==null){
				request.setAttribute("errorend", "Παρακαλώ εισάγετε ημέρα και ώρα διακοπής χρήσης");
				completed=false;
			}
			
			if(completed==true){

				double maxDist=Double.parseDouble(request.getParameter("maxDist"));
				double offer=Double.parseDouble(request.getParameter("maxOffer"));
				
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			Date startDate=new Date();
			try {
				startDate = (Date)dateFormat.parse(s);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Date endDate=new Date();
			try {
				endDate = (Date)dateFormat.parse(e);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			Schedule schedule1=new Schedule(0, true, startDate, endDate);			
			AllDAO dao1=new AllDAO();
			try{
			ArrayList<Parking> results1 =  dao1.advancedSearchParkingResults(lat, lng, maxDist, offer);
			ArrayList<Parking> finalResults1 =  new ArrayList<>();
			ArrayList<Parking> notIncluded1=dao1.advancedSearchParkingResultsWithoutBookingPeriods(lat, lng, maxDist, offer);
			for(int i=0; i<results1.size(); i++) {
				schedule1.setId(results1.get(i).getParkingID());
				//System.out.println("parkOffer&dist:"+results1.get(i).getParkingID());
				
				if(schedule1.isAvailable(dao1.getParkingsOfAvailability(), dao1.getParkingsOfBookingPeriods())){
					
					finalResults1.add(results1.get(i));	
					for(int k=0; k<finalResults1.size(); k++){
					//System.out.println("parkFinal:"+results1.get(k).getParkingID());
					}
										
				}
			}
			for(int i=0; i<notIncluded1.size(); i++) {
				System.out.println("park:"+notIncluded1.get(i).getParkingID());
				schedule1.setId(notIncluded1.get(i).getParkingID());
					
				if(schedule1.isAvailableWithoutBookingPeriod(dao1.getParkingsOfAvailability())){
					
					finalResults1.add(notIncluded1.get(i));
					for(int k=0; k<finalResults1.size(); k++){
						//System.out.println("parkFinal:"+results.get(k).getParkingID());
						}
										
				}
			}
			request.setAttribute("resultset", finalResults1);

			request.setAttribute("lat", lat);
			request.setAttribute("lng", lng);
			request.setAttribute("request",schedule1);
			rd = request.getRequestDispatcher("/jsp/ParkingSearchResults.jsp");
			} catch (Exception f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
			}
			}
			
		}
		}
		
		rd.forward(request, response);
		
		
	}

}
