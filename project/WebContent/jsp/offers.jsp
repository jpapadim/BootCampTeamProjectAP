<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="airparking.model.ParkingReport"%>
<script src="/airparking/js/offer.js"></script>
<%
String numOffers = request.getParameter("numOffers");
if(request.getParameter("parkingID")!=null && request.getAttribute("Controller")==null) {
	ParkingReport pr=(ParkingReport)session.getAttribute("pr");
	numOffers=String.valueOf(pr.getOffer().size());
}
%>
<div class="row">
	<div class="col-sm-8 form-group" style="margin-top:15px">
		<label style="font-size:24px"> Καταχώριση προσφοράς </label>
	</div>
	<div class="col-sm-4 form-group">
		<input type='text' name="numOffers" id="numOffers"  value="<%=numOffers==null?"1":numOffers%>" style="visibility:hidden">
	</div>
</div>

<%
	for (int i = 1; i < 5; i++) {
		String offerlist = request.getParameter("offerlist" + i);
		String offervalue = request.getParameter("offervalue" + i);
		if(request.getParameter("parkingID")!=null && request.getAttribute("Controller")==null) {
			ParkingReport pr=(ParkingReport)session.getAttribute("pr");
			if(pr.getOffer().size()>(i-1)){
				offerlist=pr.getOffer().get(i-1).getType();
				offervalue=String.format("%.2f",pr.getOffer().get(i-1).getValue());
			}
			
		}
%>
<div class="row" id="offer<%=i%>" style="<%=i<=1?"display: block":"display: none"%>">
	<div class="col-sm-6 form-group" style="margin: 0px">
		<div class="form-group">
			<label>Τύπος προσφοράς</label> <select class="form-control" name="offerlist<%=i%>" readonly id="offerlist<%=i%>">
				<option value="hour" <%=(offerlist!=null && offerlist.equals("hour"))?"selected":""%>>ανα ώρα</option>
				<option value="day" <%=(offerlist!=null && offerlist.equals("day"))?"selected":""%>>ανα ημέρα</option>
				<option value="month" <%=(offerlist!=null && offerlist.equals("month"))?"selected":""%>>ανα μήνα</option>
				<option value="year" <%=(offerlist!=null && offerlist.equals("year"))?"selected":""%>>ανα έτος</option>
			</select>
			<span style="color: red;font-size:0.9em;margin: 0px 0 0px 0;"> <%
									 	if ((String) request.getAttribute("offerlist"+i) != null) 
									 		out.println((String) request.getAttribute("offerlist"+i));%>
								</span>
		</div>
	</div>
	<div class="col-sm-6 form-group">
		<label>Αξία προσφοράς</label>
		<div class="input-group">
			<input type="text" class="form-control" name="offervalue<%=i%>" id="offervalue<%=i%>" value="<%=offervalue==null?"1.00":offervalue%>" placeholder='1.00'><span
				class="input-group-addon"> <span class="glyphicon glyphicon-eur"></span></span>
				
		</div>
		<span style="color: red;font-size:0.9em;margin: 0px 0 0px 0;"> <%
									 	if ((String) request.getAttribute("offervalue"+i) != null) 
									 		out.println((String) request.getAttribute("offervalue"+i));%>
								</span>
	</div>
</div>
<%}%>
<div style="display:none">
<div class="row">
	<div class="col-sm-2 form-group" id="addOfferButton">
		<button type="button" class="btn btn-sm btn-info glyphicon glyphicon-plus" onclick="addOffer()">
			<b> Προσθήκη προσφοράς</b>
		</button>
	</div>
	<div class="col-sm-2 form-group" id="removeOfferButton" style="display: none">
		<button type="button" class="btn btn-sm btn-danger glyphicon glyphicon-minus" onclick="removeOffer()">
			<b> Αφαίρεση προσφοράς</b>
		</button>
	</div>
</div>
</div>
<script>

window.onload=function(){
disableOptions();
setOffer();
}

</script>