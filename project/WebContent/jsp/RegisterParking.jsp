<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList, airparking.model.*, airparking.dao.*"%>
<%@ include file="../html/header.html"%>
<%@ include file="navbar.jsp"%>
<%
	if (!(user instanceof Owner)) {
		request.setAttribute("message", "Δεν έχετε πρόσβαση σε αυτόν τον πόρο. Παρακαλώ αναβαθμίστε τον λογαριασμό σας απο τις ρυθμίσεις λογαριασμού!");
%>
<jsp:forward page="intro.jsp" />
<%
	}
%>
<%
	String parkingIDs = request.getParameter("parkingID");
	String autoaddress = request.getParameter("autoaddress");
	String street = request.getParameter("street");
	String number = request.getParameter("number");
	String city = request.getParameter("city");
	String zipcode = request.getParameter("zipcode");
	String lat = request.getParameter("lat");
	String lng = request.getParameter("lng");
	String title = "Register Parking";
	if (request.getParameter("parkingID") != null && request.getAttribute("Controller") == null) {
		title = "Edit Parking";
		int parkingID = Integer.parseInt(request.getParameter("parkingID"));
		AllDAO dao = new AllDAO();
		ParkingReport pr = dao.getParkingReport(parkingID);
		session.setAttribute("pr", pr);
		autoaddress = pr.getParking().getAddress();
		String[] split1 = autoaddress.split(",");
		String[] split2 = split1[0].split(" ");
		street = "";
		for (int i = 0; i < split2.length - 1; i++) {
			street += split2[i] + " ";
		}
		street = street.trim();
		number = split2[split2.length - 1].trim();
		city = split1[1];
		zipcode = split1[2];
		lat = String.valueOf(pr.getParking().getLat());
		lng = String.valueOf(pr.getParking().getLng());
	}
%>
<div class="container">
	<h1 class="well">Καταχώριση parking</h1>
	<div class="col-lg-12 well">
		<div class="row">
			<div class="col-sm-12 form-group" style="margin-top:15px">
				<label style="font-size:24px"> Καταχώριση διεύθυνσης</label>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 form-group">
				<label>Διεύθυνση parking (Οδός αριθμός, πόλη, ταχ.κώδικας)</label>
				<input type="text" id="autoaddress" name="autoaddress"
					placeholder="Έύρεση πλήρους διεύθυνσης... (Οδός αριθμός, πόλη, ταχ.κώδικας)" class="form-control"
					value="<%=autoaddress == null ? "" : autoaddress%>">
				<span style="color: red; font-size: 0.9em; margin: 0px 0 0px 0;"> <%
 	if ((String) request.getAttribute("addressNotFound") != null)
 		out.println((String) request.getAttribute("addressNotFound"));
 %>
				</span>
			</div>
			</div>
			<form action="/airparking/parkregister" method="post" id="form1">
				<input type="hidden" name="parkingID"
					value="<%=(request.getParameter("parkingID") != null) ? request.getParameter("parkingID") : ""%>" />
				<input type="hidden" id="street" name="street" placeholder="Εισάγετε διεύθυνση parking εδώ.." readonly
					class="form-control" value="<%=street == null ? "" : street%>">
				<input type="hidden" id="number" name="number" value="<%=number == null ? "" : number%>" readonly
					placeholder="Εισάγετε αριθμό διεύθυνσης parking εδώ.." class="form-control">
				<input type="hidden" id="city" name="city" placeholder="Εισάγετε πόλη εδώ.." class="form-control"
					value="<%=city == null ? "" : city%>" readonly>
				<input type="hidden" id="zipcode" name="zipcode" placeholder="Εισάγετε τ.κ. .."
					value="<%=zipcode == null ? "" : zipcode%>" readonly class="form-control">
				<input type="hidden" id="lat" name="lat" value="<%=lat == null ? "" : lat%>" class="form-control">
				<input type="hidden" id="lng" name="lng" value="<%=lng == null ? "" : lng%>" class="form-control">
				<%@ include file="availability.jsp"%>
				<%@ include file="offers.jsp"%>
				<div class="row">
					<div class="col-sm-12 form-group">
						<button type="button" class="btn btn-lg btn-info" onclick="beforeSubmit()">Καταχώριση</button>
					</div>
				</div>
			</form>
		</div>
	</div>

<%
	session.removeAttribute("pr");
%>
<script>
	function beforeSubmit() {
		for (var i = 1; i < 5; i++) {
			document.getElementById('offerlist' + i).disabled = false;
		}
		document.getElementById('form1').submit();
	}
</script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCRrKdCnf50oQiDlEL4_Xrr5kpXdcHaGA0&sensor=false&libraries=places&language=el&callback=initS"
	async defer
></script>
<script>
	function initS() {
		var geocoder = new google.maps.Geocoder();
		var autocomplete = new google.maps.places.Autocomplete((document
				.getElementById('autoaddress')), {
			types : [ 'address' ],
			componentRestrictions : {
				country : "gr"
			}
		});
		autocomplete
				.addListener(
						'place_changed',
						function() {
							var location = autocomplete.getPlace().geometry.location;
							document.getElementById("lat").value = location
									.lat();
							document.getElementById("lng").value = location
									.lng();
							var address = autocomplete.getPlace().address_components;
							for (var i = 0; i < address.length; i++) {

								if (address[i].types[0] == 'street_number')
									document.getElementById("number").value = address[i].long_name;
								if (address[i].types[0] == 'route')
									document.getElementById("street").value = address[i].long_name;
								if (address[i].types[0] == 'locality')
									document.getElementById("city").value = address[i].long_name;
								if (address[i].types[0] == 'postal_code')
									document.getElementById("zipcode").value = address[i].long_name;

							}

						});

	}
	function findAddressFromFields() {
		//address string from text inputs
		var address = document.getElementById('street').value + " "
				+ document.getElementById('number').value + " "
				+ document.getElementById('city').value + " "
				+ document.getElementById('zipcode').value;
		var geocoder = new google.maps.Geocoder();
		geocoder
				.geocode(
						{
							'address' : address
						},
						function(results, status) {
							document.getElementById('autoaddress').value = results[0].formatted_address;
						});

	}
</script>
<%@ include file="../html/footer.html"%>
