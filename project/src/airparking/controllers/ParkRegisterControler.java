package airparking.controllers;

import airparking.model.*;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import airparking.dao.AllDAO;

/**
 * Servlet implementation class ParkRegisterControler
 */
@WebServlet("/parkregister")
public class ParkRegisterControler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ParkRegisterControler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean proceed = true;
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		try {
		HttpSession session = request.getSession(true);
		request.setCharacterEncoding("UTF-8");
		Owner owner = (Owner) session.getAttribute("user");
		System.out.println("street="+request.getParameter("street"));
		System.out.println("number="+request.getParameter("number"));
		System.out.println("city="+request.getParameter("city"));
		System.out.println("zipcode="+request.getParameter("zipcode"));
		System.out.println("lat="+request.getParameter("lat"));
		System.out.println("lng="+request.getParameter("lng"));
		System.out.println("availabilityStart="+request.getParameter("availabilityStart"));
		System.out.println("availabilityEnd="+request.getParameter("availabilityEnd"));
		System.out.println("numSchedules="+request.getParameter("numSchedules"));
		System.out.println("numOffers="+request.getParameter("numOffers"));
		
		
		String street = request.getParameter("street");
		String number = request.getParameter("number");
		String city = request.getParameter("city");
		String zipcode = request.getParameter("zipcode");
		String lat = request.getParameter("lat");
		String lng = request.getParameter("lng");
		if(street==null || street.isEmpty() || city==null || city.isEmpty() || zipcode==null || zipcode.isEmpty() || lat==null || lat.isEmpty() || lng==null || lng.isEmpty()){
			request.setAttribute("addressNotFound", "Address not complete or not found.");
			proceed=false;
		}
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		DateFormat formatterTime = new SimpleDateFormat("HH:mm");
		String availabilityStartString = request.getParameter("availabilityStart");
		String availabilityEndString = request.getParameter("availabilityEnd");
		Date availabilityStart = formatter.parse(availabilityStartString);
		Date availabilityEnd = formatter.parse(availabilityEndString);
		
		if(availabilityStartString==null || !availabilityStartString.matches("((19|20)\\d\\d)/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01]) ([01]?[0-9]|2[0-3]):[0-5][0-9]")){
			request.setAttribute("availabilityStart", "format should be '2017/06/01 20:00'");
			proceed=false;
		}
		if(availabilityEndString==null || !availabilityEndString.matches("((19|20)\\d\\d)/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01]) ([01]?[0-9]|2[0-3]):[0-5][0-9]")){
			request.setAttribute("availabilityEnd", "format should be '2017/06/01 20:00'");
			proceed=false;
		}
		if(!availabilityEnd.after(availabilityStart)){
			request.setAttribute("availabilityEnd", "set date end after start");
			proceed=false;
		}
		

		
		
		Integer numSchedules = Integer.parseInt(request.getParameter("numSchedules"));
		Integer numOffers = Integer.parseInt(request.getParameter("numOffers"));
		System.out.println("number of schedule " + numSchedules);
		
		
		ArrayList<String> starttimeL = new ArrayList<>();
		ArrayList<String> endtimeL = new ArrayList<>();
	
		ArrayList<String> startdayL = new ArrayList<>();
		ArrayList<String> enddayL = new ArrayList<>();
	
		ArrayList<String> startmonthL = new ArrayList<>();
		ArrayList<String> endmonthL = new ArrayList<>();
		for (Integer i = 1; i <= numSchedules; i++) {
			String alltimeCheck = request.getParameter("alltimeCheck" + i);
			String starttime = request.getParameter("starttime" + i);
			String endtime = request.getParameter("endtime" + i);
			System.out.println("--------------------"+"starttime = "+starttime);
			System.out.println("--------------------"+"endtime = "+endtime);
			if (alltimeCheck == null || !alltimeCheck.equals("true")) {
				if(starttime==null || starttime.isEmpty()){
					request.setAttribute("starttime" + i, "empty field");
					proceed=false;
				}
				else if(!starttime.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")){
					request.setAttribute("starttime" + i, "wrong format");
					proceed=false;
				}
				if(endtime==null || endtime.isEmpty()){
					request.setAttribute("endtime" + i, "empty field");
					proceed=false;
				}
				else if(!endtime.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")){
					request.setAttribute("endtime" + i, "wrong format");
					proceed=false;
				}
			}
			else {
				starttime="00:00";
				endtime="00:00";
			}
			starttimeL.add(starttime);
			endtimeL.add(endtime);
			
			String alldayCheck = request.getParameter("alldayCheck" + i);
			String startday = request.getParameter("startday" + i);
			String endday = request.getParameter("endday" + i);
			if (alldayCheck == null || !alldayCheck.equals("true")) {
				if(startday==null || startday.isEmpty() || !startday.matches("[1-7]")){
					request.setAttribute("startday" + i, "pick a day");
					proceed=false;
				}
				if(endday==null || endday.isEmpty() || !endday.matches("[1-7]") ){
					request.setAttribute("endday" + i, "pick a day");
					proceed=false;
				}
			}
			else{
				startday="0";
				endday="0";
			}
			startdayL.add(startday);
			enddayL.add(endday);
			
			String allmonthCheck = request.getParameter("allmonthCheck" + i);
			String startmonth = request.getParameter("startmonth" + i);
			String endmonth = request.getParameter("endmonth" + i);
			if (allmonthCheck == null || !allmonthCheck.equals("true")) {
				if(startmonth==null || startmonth.isEmpty() || !startmonth.matches("([1-9])|(1[0-2])")){
					request.setAttribute("startmonth" + i, "pick a day");
					proceed=false;
				}
				if(endmonth==null || endmonth.isEmpty() || !endmonth.matches("([1-9])|(1[0-2])")){
					request.setAttribute("endmonth" + i, "pick a day");
					proceed=false;
				}
			}
			else{
				startmonth="0";
				endmonth="0";
			}
			startmonthL.add(startmonth);
			endmonthL.add(endmonth);
		}

		
		ArrayList<String> offerlistL = new ArrayList<>();
		ArrayList<String> offervalueL = new ArrayList<>();
		for (Integer i = 1; i <= numOffers; i++) {
			String offerlist = request.getParameter("offerlist" + i);
			if(offerlist==null || offerlist.isEmpty()){
				request.setAttribute("offerlist" + i, "pick a type");
				proceed=false;
			}
			String offervalue = request.getParameter("offervalue" + i);
			if(offervalue==null || offervalue.isEmpty() || !offervalue.matches("\\d+\\.?\\d*")){
				request.setAttribute("offervalue" + i, "insert a valid value");
				proceed=false;
			}
			offerlistL.add(offerlist);
			offervalueL.add(offervalue);
		}
		AllDAO dao = new AllDAO();
		
		RequestDispatcher rd = null;

			if (proceed == true) {
				Parking parking = new Parking(0, owner.getUserID(), street + " " + number + ", " + city + ", " + zipcode,
						Double.parseDouble(lat), Double.parseDouble(lng), null);
				
				
				Schedule availability = new Schedule(0, true, availabilityStart, availabilityEnd);
				
				ArrayList<Advancedschedule> advancedschedules = new ArrayList<>();
				formatter = new SimpleDateFormat("HH:mm");
				for(int i=0;i<startdayL.size();i++){
					System.out.println("--------------------"+"formatterTime.parse(starttimeL.get(i)) = "+formatterTime.parse(starttimeL.get(i)));
					System.out.println("--------------------"+"formatterTime.parse(endtimeL.get(i)) = "+formatterTime.parse(endtimeL.get(i)));
					advancedschedules.add(new Advancedschedule(0, 0, formatterTime.parse(starttimeL.get(i)),
							 										formatterTime.parse(endtimeL.get(i)), 
							 										Integer.parseInt(startdayL.get(i)),
							 										Integer.parseInt(enddayL.get(i)),
							 										Integer.parseInt(startmonthL.get(i)),
							 										Integer.parseInt(endmonthL.get(i))));
				}
				if(!advancedschedules.isEmpty()) availability.setSimple(false);
				
				ArrayList<Offer> offers = new ArrayList<>();
				for(int i=0;i<offerlistL.size();i++){
					offers.add(new Offer(0, offerlistL.get(i),Double.parseDouble( offervalueL.get(i)), 0));
				}
				String parkingID=(String)request.getParameter("parkingID");
				if(parkingID!=null && !parkingID.isEmpty() ){
					parking.setParkingID(Integer.parseInt(parkingID));
					dao.updateParking(parking, availability, advancedschedules, offers);
				}
				else{
					dao.registerParking(parking, availability, advancedschedules, offers);
				}
				

				rd = request.getRequestDispatcher("/jsp/registerPark_success.jsp");
			} else {
				request.setAttribute("Controller","controller");
				rd = request.getRequestDispatcher("/jsp/RegisterParking.jsp");
			}
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();

		}

		
	}

}
