<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.io.IOException"%>
<%@ page import="airparking.model.*"%>
<%@ page import="airparking.dao.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat,java.text.SimpleDateFormat"%>
<%
	AllDAO dao = new AllDAO();
DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
DateFormat formatterTime = new SimpleDateFormat("HH:mm");
	ArrayList<User> users = dao.getUsers();
	ArrayList<Parking> parkings = dao.getParkings();
	ArrayList<Offer> offers = dao.getOffers();
	ArrayList<Booking> bookings = dao.getBookings();
	ArrayList<Review> reviews = dao.getReviews();
	
	
	ArrayList<Schedule> availabilities = dao.getAvailabilities();
	ArrayList<Schedule> bookingPeriods = dao.getBookingPeriods();
	ArrayList<Advancedschedule> advancedschedules = dao.getAdvancedschedules();

	ArrayList<Schedule> parkingsOfAvailabilities = dao.getParkingsOfAvailability();
	ArrayList<Schedule> parkingsOfBookingPeriods = dao.getParkingsOfBookingPeriods();
%>
<%@ include file="../html/header.html"%>

<%@ include file="navbar.jsp"%>

	
		<div class="container-fluid">
		<div class="well">
			<table class="table  table-sm ">
				<tr class="bg-success">
					<th colspan="6">user</th>
				</tr>
				<tr class="bg-danger">
					<th>userID</th>
					<th>fName</th>
					<th>lName</th>
					<th>password</th>
					<th>email</th>
					<th></th>
				</tr>
				<%
					for (User i : users) if(i instanceof User) {
				%>
				<tr>
					<td><%=i.getUserID()%></td>
					<td><%=i.getfName()%></td>
					<td><%=i.getlName()%></td>
					<td><%=i.getPassword()%></td>
					<td><%=i.getEmail()%></td>
					<td><a href="/airparking/deleteRecord?table=user&id=<%=String.valueOf(i.getUserID())%>" ><button >Delete</button></a></td>
				</tr>
				<%
					}
				%>
			</table>
			<hr>
				<table class="table  table-sm ">
				<tr class="bg-success">
					<th colspan="6">owner</th>
				</tr>
				<tr class="bg-danger">
					<th>userID (FK)</th>
					<th>account</th>
					<th>bank</th>
					<th>taxregistrationnumber</th>
					<th>taxoffice</th>
					<th></th>
				</tr>
				<%
					for (User i : users) if(i instanceof Owner) {
				%>
				<tr>
					<td><%=((Owner)i).getUserID()%></td>
					<td><%=((Owner)i).getAccount()%></td>
					<td><%=((Owner)i).getBank()%></td>
					<td><%=((Owner)i).getTaxregistrationnumber()%></td>
					<td><%=((Owner)i).getTaxoffice()%></td>
					<td><a href="/airparking/deleteRecord?table=owner&id=<%=String.valueOf(i.getUserID())%>" ><button >Delete</button></a></td>
				</tr>
				<%
					}
				%>
			</table>
			<hr>
			<table class="table  table-sm">
				<tr class="bg-success">
					<th colspan="6">parking</th>
				</tr>
				<tr class="bg-danger">
					<th>parkingID</th>
					<th>ownerID</th>
					<th>address</th>
					<th>lat</th>
					<th>lng</th>
					<th>photo</th>
				</tr>
				<%
					for (Parking i : parkings) {
				%>
				<tr>
					<td><%=i.getParkingID()%></td>
					<td><%=i.getOwnerID()%></td>
					<td><%=i.getAddress()%></td>
					<td><%=i.getLat()%></td>
					<td><%=i.getLng()%></td>
					<td><%=i.getPhoto()%></td>
				</tr>
				<%
					}
				%>
			</table>
			<hr>
			<table class="table  table-sm">
				<tr class="bg-success">
					<th colspan="4">availability</th>
				</tr>
				<tr class="bg-danger">
					<th>id</th>
					<th>isSimple</th>
					<th>startDateTime</th>
					<th>endDateTime</th>
				</tr>
				<%
					for (Schedule i : availabilities) {
				%>
				<tr>
					<td><%=i.getId()%></td>
					<td><%=i.isSimple()%></td>
					<td><%=formatter.format(i.getStartDateTime())%></td>
					<td><%=formatter.format(i.getEndDateTime())%></td>
					
				</tr>
				<%
					}
				%>
			</table>
			<hr>
			<table class="table  table-sm">
				<tr class="bg-success">
					<th colspan="4">bookingperiod</th>
				</tr>
				<tr class="bg-danger">
					<th>id</th>
					<th>isSimple</th>
					<th>startDateTime</th>
					<th>endDateTime</th>
				</tr>
				<%
					for (Schedule i : bookingPeriods) {
				%>
				<tr>
					<td><%=i.getId()%></td>
					<td><%=i.isSimple()%></td>
					<td><%=formatter.format(i.getStartDateTime())%></td>
					<td><%=formatter.format(i.getEndDateTime())%></td>
					
				</tr>
				<%
					}
				%>
			</table>
			<hr>
			<table class="table  table-sm">
				<tr class="bg-success">
					<th colspan="5">booking</th>
				</tr>
				<tr class="bg-danger">
					<th>bookingID</th>
					<th>timestamp</th>
					<th>parkingID</th>
					<th>userID</th>
					<th>offerID</th>
					
				</tr>
				<%
					for (Booking i : bookings) {
				%>
				<tr>
					<td><%=i.getBookingID()%></td>
					<td><%=formatter.format(i.getTimestamp())%></td>
					<td><%=i.getParkingID()%></td>
					<td><%=i.getUserID()%></td>
					<td><%=i.getOfferID()%></td>
					</tr>
				<%
					}
				%>
			</table>
			<hr>
			<table class="table  table-sm">
				<tr class="bg-success">
					<th colspan="5">review</th>
				</tr>
				<tr class="bg-danger">
					<th>reviewID</th>
					<th>comment</th>
					<th>rating</th>
					<th>parkingID</th>
					<th>userID</th>
					
				</tr>
				<%
					for (Review i : reviews) {
				%>
				<tr>
					<td><%=i.getReviewID()%></td>
					<td><%=i.getComment()%></td>
					<td><%=i.getRating()%></td>
					<td><%=i.getParkingID()%></td>
					<td><%=i.getUserID()%></td>
					</tr>
				<%
					}
				%>
			</table>
			<hr>
			<table class="table  table-sm">
				<tr class="bg-success">
					<th colspan="4">offer</th>
				</tr>
				<tr class="bg-danger">
					<th>offerID</th>
					<th>type</th>
					<th>value</th>
					<th>parkingID</th>
				</tr>
				<%
					for (Offer i : offers) {
				%>
				<tr>
					<td><%=i.getOfferID()%></td>
					<td><%=i.getType()%></td>
					<td><%=i.getValue()%></td>
					<td><%=i.getParkingID()%></td>
					</tr>
				<% }%>
			</table>
			<hr>
			<table class="table  table-sm">
				<tr class="bg-success">
					<th colspan="8">advancedschedule</th>
				</tr>
				<tr class="bg-danger">
				    <th>advschID</th>
					<th>parkingID</th>
					<th>sTimeOfDay</th>
					<th>eTimeOfDay</th>
					<th>sDayOfWeek</th>
					<th>eDayOfWeek</th>
					<th>sMonthOfYear</th>
					<th>eMonthOfYear</th>
				</tr>
				<% for(Advancedschedule i:advancedschedules){ %>
				<tr>
				    <td><%=i.getAdvschID()%></td>
					<td><%=i.getParkingID()%></td>
					<td><%=formatterTime.format(i.getsTimeOfDay())%></td>
					<td><%=formatterTime.format(i.geteTimeOfDay())%></td>
					<td><%=i.getsDayOfWeek()%></td>
					<td><%=i.geteDayOfWeek()%></td>
					<td><%=i.getsMonthOfYear()%></td>
					<td><%=i.geteMonthOfYear()%></td>
					</tr>
				<% }%>
			</table>
			<hr>
			<table class="table  table-sm text-justified">
				<tr class="bg-success">
					<th colspan="4">parkings with availability</th>
				</tr>
				<tr class="bg-danger">
					<th>id</th>
					<th>isSimple</th>
					<th>startDateTime</th>
					<th>endDateTime</th>
				</tr>
				<%
					for (Schedule i : parkingsOfAvailabilities) {
				%>
				<tr>
					<td><%=i.getId()%></td>
					<td><%=i.isSimple()%></td>
					<td><%=i.getStartDateTime()%></td>
					<td><%=i.getEndDateTime()%></td>
				</tr>
				<%
					}
				%>
			</table>
			<hr>
			<table class="table  table-sm text-justified">
				<tr class="bg-success">
					<th colspan="4">parkings with bookingperiod</th>
				</tr>
				<tr class="bg-danger">
					<th>id</th>
					<th>isSimple</th>
					<th>startDateTime</th>
					<th>endDateTime</th>
				</tr>
				<%
					for (Schedule i : parkingsOfBookingPeriods) {
				%>
				<tr>
					<td><%=i.getId()%></td>
					<td><%=i.isSimple()%></td>
					<td><%=i.getStartDateTime()%></td>
					<td><%=i.getEndDateTime()%></td>
				</tr>
				<%
					}
				%>
			</table>
		</div>
	</div>
	<hr>

	<%@ include file="../html/footer.html"%>

