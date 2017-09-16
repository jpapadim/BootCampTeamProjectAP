package airparking.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import airparking.dao.AllDAO;
import airparking.model.Booking;
import airparking.model.Offer;
import airparking.model.Parking;
import airparking.model.Schedule;
/*import airparking.model.SearchResult;*/
import airparking.model.User;

/**
 * Servlet implementation class BookingController
 */
@WebServlet("/bookingController")
public class BookingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingController() {
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
		HttpSession session = request.getSession(true);
		User user=null;
		ArrayList<Parking> results=null;
		Integer index=Integer.parseInt(request.getParameter("parkingIndex"));
		Schedule requestPeriod=null;
				if((session.getAttribute("resultset") instanceof ArrayList) && (session.getAttribute("request") instanceof Schedule) && (session.getAttribute("user") instanceof User) ){
					user=(User)(session.getAttribute("user"));
					results=(ArrayList)(session.getAttribute("resultset"));
					requestPeriod=(Schedule)(session.getAttribute("request"));
				}
				AllDAO dao=new AllDAO();
				ArrayList<Offer> offers = new ArrayList<>();
				try {
					offers = dao.getOffers(results.get(index).getParkingID());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		Booking booking=new Booking(0,new Date(),results.get(index).getParkingID(),user.getUserID(),offers.get(0).getOfferID());

		try {
			dao.book(booking, requestPeriod);
			RequestDispatcher rd=request.getRequestDispatcher("/jsp/booking_success.jsp");
			session.removeAttribute("results");
			session.removeAttribute("request");
			rd.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
