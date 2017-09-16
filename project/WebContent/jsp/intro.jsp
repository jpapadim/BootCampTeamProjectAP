<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
%>
<%@ page import="org.joda.time.DateTime, java.text.SimpleDateFormat"%>
<%@ include file="../html/header.html"%>
<div class="container-fluid">
	<%@ include file="navbar.jsp"%>
	<%
		if (request.getAttribute("errormessage") != null) {
	%>
	<script type="text/javascript">
		$('#login').modal('show');
	</script>
	<%
		}
	%>
	<div class="jumbotron my_jumbotron">
		<h1 class="logo">Air Parking</h1>
		<form action="/airparking/searchcontroller" method="post">
		<p style="text-align:center;"> Ψάχνετε parking; Δείτε αμέσως που υπάρχουν διαθέσιμα parking προς ενοικίαση απλά</br>
		 συμπληρώνοντας την διεύθυνση που σας ενδιαφέρει.</p>
		
			<div class="form-group">
				<input type="text" class="form-control" id="search" name="search"
					placeholder="πχ. Κοραή 3, Αθήνα"
				> <span style="color: red; font-size: 0.9em; margin: 0px 0 0px 0;">
					<%
						if ((String) request.getAttribute("errorsearch") != null)
							out.println((String) request.getAttribute("errorsearch"));
					%>
				</span>
				<div>
					<input type="hidden" id="lat" name="lat"> <input
						type="hidden" id="lng" name="lng"
					>
				</div>
			</div>
			<script>
				function toggle() {
					if (document.getElementById('checkAdvancedSearch').checked == false) {
						document.getElementById('advancedSearchData').style.display = "none";
					} else {
						document.getElementById('advancedSearchData').style.display = "block";
					}
				}
				$( document ).ready(function() {
				    toggle();
				});
			</script>
			<div class="form-group">
				<div class="checkbox">
					<label> <input type="checkbox" id="checkAdvancedSearch"
						name="checkAdvancedSearch" onclick="toggle()" value="false"
					> <b>Προηγμένη αναζήτηση</b>
					</label>
				</div>
			</div>
			<%
				DateTime now = new DateTime();
				DateTime nowPlus4hours = now.plusHours(4);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				String startDate = formatter.format(now.toDate());
				String endDate = formatter.format(nowPlus4hours.toDate());
			%>
			<div id="advancedSearchData" style="display: block">
				<div class="row">
					<div class="col-sm-6 form-group">
						<label>Απο ημερ/νία - ώρα</label>
						<div class="input-group date">
							<input type='text' id='datetimepicker1' name="datetimepicker1"
								class="form-control" placeholder="Click to reveal calendar"
								value="<%=startDate%>"
							/> <span class="input-group-addon"> <span
								class="glyphicon glyphicon-calendar"
							></span>
							</span> <span style="color: red; font-size: 0.9em; margin: 0px 0 0px 0;">
								<%
									if ((String) request.getAttribute("errorstart") != null)
										out.println((String) request.getAttribute("errorstart"));
								%>
							</span>
						</div>
					</div>
					<div class="col-sm-6 form-group">
						<label>Έως ημερ/νία - ώρα</label>
						<div class="input-group date">
							<input type='text' id='datetimepicker2' name="datetimepicker2"
								class="form-control" placeholder="Click to reveal calendar."
								value="<%=endDate%>"
							/> <span class="input-group-addon"> <span
								class="glyphicon glyphicon-calendar"
							></span>
							</span> <span style="color: red; font-size: 0.9em; margin: 0px 0 0px 0;">
								<%
									if ((String) request.getAttribute("errorend") != null)
										out.println((String) request.getAttribute("errorend"));
								%>
							</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6 form-group">
						<label>Αναζήτηση σε απόσταση έως (m)</label> <input type="text"
							id="maxDist" name="maxDist"
							placeholder="Enter Maximum Distance in meters." value="1000"
							class="form-control"
						> <span
							style="color: red; font-size: 0.9em; margin: 0px 0 0px 0;"
						> <%
 	if ((String) request.getAttribute("errordist") != null)
 		out.println((String) request.getAttribute("errordist"));
 %>
						</span>
					</div>
					<div class="col-sm-6 form-group">
						<label>Μέγιστο κόστος ενοικίασης έως (&euro;/ώρα)</label> <input type="text"
							id="maxOffer" name="maxOffer"
							placeholder="Enter Maximum Offer per hour in euro." value="10"
							class="form-control"
						> <span
							style="color: red; font-size: 0.9em; margin: 0px 0 0px 0;"
						> <%
 	if ((String) request.getAttribute("erroroffer") != null)
 		out.println((String) request.getAttribute("erroroffer"));
 %>
						</span>
					</div>
				</div>
			</div>
			<div class="col-lg-12 text-center">
				<button type="submit" class="btn btn-default">
					<span class="glyphicon glyphicon-search"></span> Αναζήτηση
				</button>
			</div>
		</form>
	</div>
</div>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCRrKdCnf50oQiDlEL4_Xrr5kpXdcHaGA0&libraries=places&language=el&callback=initS"
	async defer
></script>
<script>
	var map;
	function initS() {

		var autocomplete = new google.maps.places.Autocomplete(
		/** @type {!HTMLInputElement} */
		(document.getElementById('search')), {
			types : [ 'geocode' ],
			componentRestrictions : {
				country : "gr"
			}
		});
		autocomplete.addListener('place_changed', function() {
			var place = autocomplete.getPlace();
			var geo = place.geometry;
			var location = geo.location;
			document.getElementById("lat").value = location.lat();
			document.getElementById("lng").value = location.lng(); 
		});
	}
</script>
<script>
	$(function() {
		$('#datetimepicker1').datetimepicker();
		$('#datetimepicker2').datetimepicker({
			useCurrent : false
		//Important! 
		});
		$("#datetimepicker1").on("dp.change", function(e) {
			$('#datetimepicker2').data("DateTimePicker").minDate(e.date);
		});
		$("#datetimepicker2").on("dp.change", function(e) {
			$('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
		});
	});
</script>

<%@ include file="../html/footer.html"%>