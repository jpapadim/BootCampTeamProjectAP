 <%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ include file="../html/header.html"%>



<%@ include file="navbar.jsp"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import="airparking.model.*" %>
<%@ page import="airparking.dao.AllDAO" %>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.joda.time.Interval"%>


	<%Object resultObject=request.getAttribute("resultset");
	Object latObject=request.getAttribute("lat");
	Object lngObject=request.getAttribute("lng");
	
	ArrayList<Parking> results=null;
	Double lat=(Double)latObject;
	Double lng=(Double)lngObject;
	AllDAO dao = new AllDAO();
	if(resultObject instanceof ArrayList ){
       results=(ArrayList)(resultObject);
	}
	Schedule requestPeriod=(Schedule)(request.getAttribute("request"));
	Interval interv1 = new Interval(requestPeriod.getStartDateTime().getTime(), requestPeriod.getEndDateTime().getTime());

	
	for(Parking p:results){
		p.toString();
	}
	
	if(results.isEmpty()){
		%>
		<div class="container">
	      <div class="well text-center" id="my_well2">
	        <span style=" font-size: 1.5em;">
	        <img src="/airparking/images/NonThinkingManSmartphone.png" width="304" height="236">
	        <br> <b>Δεν βρέθηκαν καταχωριμένα parking με αυτά τα κριτήρια αναζήτησης. Μεταβάλετε τα κριτήρια και δοκιμάστε ξανά!</b>
	        <br><a href="/airparking/jsp/intro.jsp" class="btn btn-info" role="button">Αναζήτηση ξανά</a></span><br>
	      </div>
	    </div>
	<% }
	else
	{
	
	session.setAttribute("resultset", request.getAttribute("resultset"));
	session.setAttribute("request", request.getAttribute("request"));%>
	<%if(user==null){%>
	
	<div class="alert alert-danger"  >
	 Απαιτείται σύνδεση ή εγγραφή για να κάνετε κράτηση! &nbsp;&nbsp;<a class="btn btn-default" href="/airparking/jsp/intro.jsp">
	  <i class="fa fa-search"></i>&nbsp; Νέα αναζήτηση!</a>
	</div>
	<%}%>
	<div class="well" id="my_well">
	<table class="table  table-sm table-hover searchTable" id="mytable1">
				
				<tr style="align-text:center;">
					<th class="active"> ParkingID</th>
					<th class="active"><i class="fa fa-sort" aria-hidden="true"></i>&nbsp;Διεύθυνση</th>
					<th class="active"><i class="fa fa-sort" aria-hidden="true"></i>&nbsp;Απόσταση(m)</th>
					<th class="active"><i class="fa fa-sort" aria-hidden="true"></i>&nbsp;Προσφορά(&euro;/ώρα)</th>	
					<% if(user!=null){%><th class="active">Link</th><%} %>
				</tr>
				<%
				int index=0;
					for (Parking i : results) {
						int minutes=interv1.toPeriod().toStandardMinutes().getMinutes();
						System.out.println("minutes= "+minutes);
						Double value=dao.getOffersByParkingID(i.getParkingID());
						String distance = String.format("%.2f",i.findDistance(lat,lng));
						request.setAttribute("cost", String.format("%.2f ", (value)*(minutes/60.0)));
						request.setAttribute("address", i.getAddress());
						request.setAttribute("from", requestPeriod.getStartDateTime().toLocaleString());
						request.setAttribute("until", requestPeriod.getEndDateTime().toLocaleString());
						request.setAttribute("index", String.valueOf(index));
						int hours = interv1.toPeriod().toStandardHours().getHours();
						int leftMinutes= minutes-hours*60;
						
						System.out.println("hours= "+hours);
						System.out.println("leftMinutes= "+leftMinutes);
						request.setAttribute("Duration", String.format("%d houres and %d minutes",hours,leftMinutes));
						
				%>
				<%@ include file="bookModal.jsp" %>
				<tr>
					<td><b><%=i.getParkingID()%></b></td>
					<td><b><%=i.getAddress()%></b></td>
					<td><b><%= distance%></b></td>
					<td><b><%=value%> </b></td>
					<% if(user!=null){%><td>
                   <a href='#' data-toggle='modal' data-target='<%="#book_"+index%>'><button class="btn btn-info" id="<%=index%>">Κράτηση!</button></a>
					</td><%} %>
				</tr>
				<%
				index++;}
				%>
			</table>
			
			</div>
			<%
			}
			%>
<script>
$(document).ready(function() {
	  $('th').each(function(col) {
	    $(this).hover(
	    function() { $(this).addClass('focus'); },
	    function() { $(this).removeClass('focus'); }
	  );
	    $(this).click(function() {
	      if ($(this).is('.asc')) {
	        $(this).removeClass('asc');
	        $(this).addClass('desc selected');
	        sortOrder = -1;
	      }
	      else {
	        $(this).addClass('asc selected');
	        $(this).removeClass('desc');
	        sortOrder = 1;
	      }
	      $(this).siblings().removeClass('asc selected');
	      $(this).siblings().removeClass('desc selected');
	      var arrData = $('table').find('tbody >tr:has(td)').get();
	      arrData.sort(function(a, b) {
	        var val1 = $(a).children('td').eq(col).text().toUpperCase();
	        var val2 = $(b).children('td').eq(col).text().toUpperCase();
	        if($.isNumeric(val1) && $.isNumeric(val2))
	        return sortOrder == 1 ? val1-val2 : val2-val1;
	        else
	           return (val1 < val2) ? -sortOrder : (val1 > val2) ? sortOrder : 0;
	      });
	      $.each(arrData, function(index, row) {
	        $('tbody').append(row);
	      });
	    });
	  });
	});
</script>
<%@ include file="../html/footer.html"%>