<%@ page import="java.util.ArrayList, airparking.model.Report"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	if (session.getAttribute("user") == null) {
		request.setAttribute("message", "You are not authorized to access this resource. Please login.");
%>
<jsp:forward page="intro.jsp" />
<%
	}
%>
<%@ include file="../html/header.html"%>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCRrKdCnf50oQiDlEL4_Xrr5kpXdcHaGA0&libraries=places&language=el">
	</script>

<%@ include file='navbar.jsp'%>
<%-- User object imported from navbar --%>
<%
	if (!(user instanceof Owner)) {
		request.setAttribute("message", "You are not authorized to access this resource. Upgrade account!");
%>
<jsp:forward page="intro.jsp" />
<%
	}

ArrayList<Report> parklist = null;

if(request.getAttribute("parklist") != null) {
 	parklist = (ArrayList<Report>) request.getAttribute("parklist");
%>

<div class="container-fluid">
	<div class="well"
		style="margin-top: auto; background: rgba(255, 255, 255, 0.75);">
		<div class="row">
			<div class="col-md-12">
				<h3>Συγκεντρωτική κατάσταση καταχωριμένων parking</h3>
			</div>
		</div>
		
		<hr>
		<div class="table-responsive">	 
  <div class="container">
  <form class="form-horizontal" role="form">
  <div class="row"> <div class="col col-xs-8" style="text-align:right;">
  </div>
    				<div class="col col-xs-4 form-inline">
    				<label for="searchInput" class="mr-sm-2" style="v-align:center;">Φίλτρο επιλογής: &nbsp;</label>
      <input type="text" id="searchInput" class="form-control" placeholder="Αναζήτηση" >
    </div>
  </div></form>
</div>
 
<table class="table table-hover myTable" id="parking _report">
				<thead>
					<tr>
						<th style="text-align: center;">#</th>
						<th style="text-align: center;">ID</th>
						<th>Address</th>
						<th style="text-align: center;display:none">lat</th>
						<th style="text-align: center;display:none">lng</th>
						<%--<th style="text-align: center;">Photo</th>
						 ><th style="text-align: center;">User</th>
						<th style="text-align: center;">Comments</th>
						<th style="text-align: center;">Rating</th>--%>
						<th style="text-align: center;">Last booking&nbsp;date</th>
						<th style="text-align: center;">Duration</th>
						<th style="text-align: center;">Offer type</th>
						<th style="text-align: center;">Value per&nbsp;offer</th>
						<th style="text-align: center;">Total(€)</th>
					</tr>
				</thead>
				<tbody id="myTable">
					<%--   Retrieves records from the ResultSet --%>
					<% int i=0; 
					
			for(Report s: parklist) {
				 i++;  
			; %>
					<tr>
						<td style="text-align: center;"><%= i %></td>
						<td style="text-align: center;"><%= s.getParkingID() %></td>
						<td id = "address" ><%= s.getAddress() %></td>
						<td id ="lat" style="display:none"><%= s.getLat() %></td>
						<td id = "lng" style="display:none"><%= s.getLng() %></td>
						<td style="text-align: center;"><%= ((s.getLastBooking() == null) ? "-" : s.getLastBooking())%></td>
						<td style="text-align: center;"><%= ((s.getduration() == null) ? "-" : s.getduration()) %></td>
						<td style="text-align: center;"><%= ((s.getOfferType() == null) ? "-" : s.getOfferType()) %></td>
						<td style="text-align: center;"><%= s.getvalue() %></td>
						<td style="text-align: center;"><%= s.getTotalCost() %></td>
					</tr>
					<% } 
} %>

				</tbody>
			</table>
	<script>
	
	$("#searchInput").on('keyup', function () {
		filterTable(this);
		tableReader();	
	}).focus(function () {
	    this.value = "";
	    $(this).css({
	        "color": "black"
	    });
	    $(this).unbind('focus');
	}).css({
	    "color": "#C0C0C0"
	});
	
	function filterTable(select){	
    //split the current value of searchInput
    var data = select.value.toUpperCase().split(" ");
    //create a jquery object of the rows
    var jObj = $("#myTable").find("tr");
    if (this.value == "") {
        jObj.show();
        return;
    }
    //hide all the rows
    jObj.hide();
   
    //Recusively filter the jquery object to get results.
    jObj.filter(function (i, v) {
        var $t = $(this);
        for (var d = 0; d < data.length; ++d) {
            if ($t.text().toUpperCase().indexOf(data[d]) > -1) {
                return true;
            }
        }
        return false;
    })
    //show the rows that match.
   .show(); 
}
	</script>
 <div id="map" style="height: 300px; width: auto;">
</div>
<script>
function map(markObj){
	//Google Maps Script
    var map = new google.maps.Map(document.getElementById('map'), {
      maxZoom: 16,
      center: new google.maps.LatLng(39.01, 23.73),
      mapTypeId: google.maps.MapTypeId.ROADMAP
    });
	//Add the markers
	var marker, i;
	var bounds = new google.maps.LatLngBounds();
	var infowindow = new google.maps.InfoWindow();
	
    for (i = 0; i < markObj.myRows.length; i++) { 
      marker = new google.maps.Marker({
      position: new google.maps.LatLng(markObj.myRows[i].lat, markObj.myRows[i].lng),  
      map: map,
      title: markObj.myRows[i].Address    
      
      });
    //Add popup on click  
      google.maps.event.addListener(marker, 'click', function() {
          infowindow.setContent(this.title);	  
          infowindow.open(map, this);
      });
      //Autofit map with bounds 
      bounds.extend(marker.position);      
     }
    
  //Scale to new zoom level
    map.fitBounds(bounds);       
    map.panToBounds(bounds);
    
    }
    
    function tableReader(){
	var myRows = { myRows :[] };
	var $th = $('table th');
	//Select only from visible ROWS
	$('table tbody tr:visible').each(function(i, tr){
	    var obj = {};
	    $tds = $(tr).find('td');
	    $th.each(function(index, th){
	        obj[$(th).text()] = $tds.eq(index).text();
	    });
	    myRows.myRows.push(obj);
	})
	 
	map(myRows);
}
    $(tableReader());
</script>
		</div>
	</div>
</div>
</body>
</html>
