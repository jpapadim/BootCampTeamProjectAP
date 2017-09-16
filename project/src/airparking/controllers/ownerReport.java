package airparking.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import airparking.connection.DB;
import airparking.model.Owner;
import airparking.model.Report;
import airparking.model.User;

/**
 * Servlet implementation class ownerReport
 */
@WebServlet("/oreport")
public class ownerReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		
		DB db = new DB();
		Connection con = null;
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (session.getAttribute("user") != null) {
		
		//if (user != null) {
			if (user instanceof Owner) {
				String ownerId = Integer.toString(user.getUserID());

				try {
					db.open();
					con = db.getConnection();
					try {
						PreparedStatement stmt = null;
						ArrayList<Report> parkList = new ArrayList<Report>();

						String extQuery = "SELECT parking.parkingID, parking.address, parking.lat, parking.lng, parking.photo, user.fname, user.lName, review.comment, review.rating, booking.bookingID,\r\n" + 
								"TIMESTAMPDIFF(HOUR,bookingperiod.startdatetime,bookingperiod.enddatetime) AS Duration, bookingperiod.startdatetime, offer.type, offer.value,\r\n" + 
								"CASE WHEN offer.type='hour' then (TIMESTAMPDIFF(HOUR,bookingperiod.startdatetime,bookingperiod.enddatetime))*offer.value\r\n" + 
								"WHEN offer.type ='day' then (TIMESTAMPDIFF(HOUR,bookingperiod.startdatetime,bookingperiod.enddatetime)/24)*offer.value\r\n" + 
								"WHEN offer.type = 'month' then (TIMESTAMPDIFF(HOUR,bookingperiod.startdatetime,bookingperiod.enddatetime)/730)*offer.value\r\n" + 
								"WHEN offer.type = 'year' then (TIMEDIFF(bookingperiod.enddatetime, bookingperiod.startdatetime)/8760)*offer.value\r\n" + 
								"END AS TotalCost FROM  air_parking.parking\r\n" + 
								"LEFT JOIN air_parking.review on parking.parkingID = review.parkingID\r\n" + 
								"LEFT JOIN air_parking.booking on booking.parkingID = parking.parkingID\r\n" + 
								"LEFT JOIN air_parking.bookingperiod on bookingperiod.bookingID = booking.bookingID\r\n" + 
								"LEFT JOIN air_parking.user on user.userID = booking.userID\r\n" + 
								"LEFT JOIN air_parking.offer on offer.parkingID = parking.parkingID\r\n" + 
								"WHERE parking.ownerID = ? ORDER BY TotalCost DESC, address;";

						stmt = con.prepareStatement(extQuery);
						stmt.setString(1, ownerId);
						ResultSet rs = stmt.executeQuery();
						if (rs.next()) {
							do {
																					
								parkList.add(new Report (rs.getInt("parkingID"),
										rs.getString("address"),rs.getDouble("lat"),
										rs.getDouble("lng"),rs.getString("photo"),
										rs.getString("fname"),rs.getString("lName"),
										rs.getString("comment"), rs.getInt("rating"),
										rs.getString("Duration"), rs.getString("startdatetime"),
										rs.getString("type"), rs.getDouble("value"), rs.getDouble("TotalCost")));
							} while (rs.next());
						}
						request.setAttribute("parklist", parkList);
						rs.close();

					} catch (Exception e) {
						out.println(e.getMessage());
					} finally {
						db.close();	
					}
				} catch (Exception ex) {
					out.println(ex.getMessage());
				}
				RequestDispatcher rd = request.getRequestDispatcher("/jsp/ownerReport.jsp");
				rd.forward(request, response);
			} 
		} else {
			out.println(user.getUserID());
			out.println("Not a registered user");
		}
	}
}
