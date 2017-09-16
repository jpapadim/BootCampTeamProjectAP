<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../html/header.html"%>
<div class="container-fluid">
	<%@ include file="navbar.jsp"%>
	
<script>
				var map;
				function initS() {
					var autocomplete = new google.maps.places.Autocomplete(
					/** @type {!HTMLInputElement} */
					(document.getElementById('search')), {
						types : [ 'geocode' ],componentRestrictions: {country: "gr"}
					});
					autocomplete.addListener('place_changed',function(){
						var place=autocomplete.getPlace();
						var geo=place.geometry;
						var location=geo.location;
						document.getElementById("lat").value=location.lat();
						document.getElementById("lng").value=location.lng();
						
					});
					
				}
			</script>
			<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCRrKdCnf50oQiDlEL4_Xrr5kpXdcHaGA0&sensor=false&libraries=places&language=el&callback=initS"" async
				defer></script>

<%@ include file="../html/footer.html"%>