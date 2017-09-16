 <%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ include file="../html/header.html"%>

<%@ include file="navbar.jsp"%>
<%@ page import="java.util.ArrayList, java.text.SimpleDateFormat" %>
<%@ page import="org.joda.time.Duration" %>
<%@ page import="airparking.model.*" %>
<%@ page import="airparking.dao.AllDAO" %>
<%@ page import="java.util.Date" %>



	<%AllDAO dao = new AllDAO();
	ArrayList<Integer> bookingIds=dao.getBookingsIds(user.getUserID());%>

	<div class="well" id="my_well">
	<table class="table  table-sm table-hover text-justified">
				
				<tr>
					<th class="active">Διεύθυνση parking</th>
					<th class="active">Email ιδιοκτήτη</th>
					<th class="active">Συνολικό κόστος</th>
					<th class="active">Διάρκεια (ώρες)</th>
					<th class="active">Έναρξη μίσθωσης</th>	
					<th class="active">Λήξη μίσθωσης</th>				
				</tr>
				<%
				int index=0;
					for (Integer i : bookingIds) {
						BookingReport br=dao.getBookingReport(i);
						Duration duration =new Duration(br.getBookingPeriod().getStartDateTime().getTime(),br.getBookingPeriod().getEndDateTime().getTime());
						double value =br.getOffer().getValue();
						double totalCost=duration.getStandardMinutes()*value/60.0;
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
						String start=formatter.format(br.getBookingPeriod().getStartDateTime()); 
						String end=formatter.format(br.getBookingPeriod().getEndDateTime()); 
						String old="";
						if(br.getBookingPeriod().getEndDateTime().before(new Date())) old="bgcolor='#ffb3b3'";
						
				%>
				<tr <%= old%>>
					<td><b><%=br.getParking().getAddress()%></b></td>
					<td><b><%=br.getOwner().getEmail()%></b></td>
					<td><b><%=String.format("%.2f ",totalCost)%> </b></td>
					<td><b><%=String.format("%.2f ", duration.getStandardMinutes()/60.0) %></b>
					<td><b><%=start%></b></td>
					<td><b><%=end%></b></td>
				</tr>
				<%
			}
			%>
			</table>
			
			</div>
			

<%@ include file="../html/footer.html"%>