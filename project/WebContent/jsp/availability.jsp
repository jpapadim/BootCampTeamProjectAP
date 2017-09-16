<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="airparking.model.ParkingReport"%>
<script src="/airparking/js/availability.js"></script>
<%
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
	String availabilityStart = request.getParameter("availabilityStart");
	String availabilityEnd = request.getParameter("availabilityEnd");
	String numSchedules = request.getParameter("numSchedules");
	if (request.getParameter("parkingID") != null && request.getAttribute("Controller") == null) {
		ParkingReport pr = (ParkingReport) session.getAttribute("pr");
		availabilityStart = format1.format(pr.getAvailability().getStartDateTime());
		availabilityEnd = format1.format(pr.getAvailability().getEndDateTime());
		numSchedules = String.valueOf(pr.getAdvancedschedule().size());
	}

	Calendar cal = Calendar.getInstance();
	String startDate = format1.format(cal.getTime());
	cal.add(Calendar.YEAR, 1);
	String endDate = format1.format(cal.getTime());
%>
<div class="row">
	<div class="col-sm-12 form-group" style="margin-top:15px">
		<label style="font-size:24px"> Καταχώριση χρόνου διάθεσης </label>
		<input type='hidden' name="numSchedules" id="numSchedules" value="<%=numSchedules == null ? "0" : numSchedules%>"
			style="visibility: hidden">
	</div>
</div>
<div id="parkSchedule" class="row">
	<div class='col-sm-6'>
		<label>Από ημέρα - ώρα:&nbsp;</label>
		<div class="form-group">
			<div class='input-group date'>
				<input type='text' name="availabilityStart" class="form-control" id='datetimepicker1'
					value="<%=availabilityStart == null ? startDate : availabilityStart%>" placeholder='2017/06/01 03:00' />
				<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
				</span>
			</div>
			<span style="color: red; font-size: 0.9em; margin: 0px 0 0px 0; white-space: nowrap;"> <%
 	if ((String) request.getAttribute("availabilityStart") != null)
 		out.println((String) request.getAttribute("availabilityStart"));
 %>
			</span>
		</div>
	</div>
	<div class='col-sm-6'>
		<label>Έως ημέρα - ώρα</label>
		<div class="form-group">
			<div class='input-group date'>
				<input type='text' name="availabilityEnd" class="form-control" id='datetimepicker2'
					value="<%=availabilityEnd == null ? endDate : availabilityEnd%>" placeholder='2017/06/01 03:00' />
				<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
				</span>
			</div>
			<span style="color: red; font-size: 0.9em; margin: 0px 0 0px 0; white-space: nowrap;"> <%
 	if ((String) request.getAttribute("availabilityEnd") != null)
 		out.println((String) request.getAttribute("availabilityEnd"));
 %>
			</span>
		</div>
	</div>
</div>
<%
	for (int i = 1; i < 6; i++) {
		String alltimeCheck = request.getParameter("alltimeCheck" + i);
		String starttime = request.getParameter("starttime" + i);
		String endtime = request.getParameter("endtime" + i);
		String alldayCheck = request.getParameter("alldayCheck" + i);
		String startday = request.getParameter("startday" + i);
		String endday = request.getParameter("endday" + i);
		String allmonthCheck = request.getParameter("allmonthCheck" + i);
		String startmonth = request.getParameter("startmonth" + i);
		String endmonth = request.getParameter("endmonth" + i);
		if (numSchedules == null) {
			alltimeCheck = "true";
			alldayCheck = "true";
			allmonthCheck = "true";
		}
		if (request.getParameter("parkingID") != null && request.getAttribute("Controller") == null) {
			ParkingReport pr = (ParkingReport) session.getAttribute("pr");
			if (pr.getAdvancedschedule().size() > (i - 1)) {
				alltimeCheck = pr.getAdvancedschedule().get(i - 1).hasTimePeriot() ? null : "true";
				starttime = format2.format(pr.getAdvancedschedule().get(i - 1).getsTimeOfDay());
				endtime = format2.format(pr.getAdvancedschedule().get(i - 1).geteTimeOfDay());
				alldayCheck = pr.getAdvancedschedule().get(i - 1).hasDayPeriot() ? null : "true";
				startday = String.valueOf(pr.getAdvancedschedule().get(i - 1).getsDayOfWeek());
				endday = String.valueOf(pr.getAdvancedschedule().get(i - 1).geteDayOfWeek());
				allmonthCheck = pr.getAdvancedschedule().get(i - 1).hasMonthPeriot() ? null : "true";
				startmonth = String.valueOf(pr.getAdvancedschedule().get(i - 1).getsMonthOfYear());
				endmonth = String.valueOf(pr.getAdvancedschedule().get(i - 1).geteMonthOfYear());
			} else {
				alltimeCheck = "true";
				alldayCheck = "true";
				allmonthCheck = "true";
			}

		}
%>
<script src="/airparking/js/timepicker.js"></script>
<div style="display: none">
	<div class="col-lg-12 well my_well3" id="schedule<%=i%>" style="display: none; padding: 6px; margin-bottom: 2px">
		<div class="row">
			<div class="col-sm-4">
				<div class="row">
					<div class="col-sm-6 form-group" style="margin: 0px">
						<div class="form-group" style="margin-bottom: 0px;">
							<label style="white-space: nowrap; margin-bottom: 0px;" class="lb-sm">Απο ημέρα και ώρα</label>
							<input type="text" class="form-control input-sm" name="starttime<%=i%>" id="starttime<%=i%>" data-timepicker
								value="<%=starttime == null ? "" : starttime%>" placeholder="23:59">
							<span style="color: red; font-size: 0.9em; margin: 0px 0 0px 0; white-space: nowrap;"> <%
 	if ((String) request.getAttribute("starttime" + i) != null)
 			out.println((String) request.getAttribute("starttime" + i));
 %>
							</span>
						</div>
					</div>
					<div class="col-sm-6 form-group" style="margin: 0px">
						<div class="form-group" style="margin-bottom: 0px;">
							<label style="white-space: nowrap; margin-bottom: 0px;" class="lb-sm">Έως ημέρα και ώρα</label>
							<input type="text" class="form-control input-sm" name="endtime<%=i%>" id="endtime<%=i%>" data-timepicker
								value="<%=endtime == null ? "" : endtime%>" placeholder="23:59">
							<span style="color: red; font-size: 0.9em; margin: 0px 0 0px 0; white-space: nowrap;"> <%
 	if ((String) request.getAttribute("endtime" + i) != null)
 			out.println((String) request.getAttribute("endtime" + i));
 %>
							</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12 form-group" style="margin: 0px">
						<div class="form-group" style="white-space: nowrap; margin: 0px">
							<input type="checkbox" name="alltimeCheck<%=i%>" id="alltimeCheck<%=i%>" value="true"
								<%=(alltimeCheck == null || !alltimeCheck.equals("true")) ? "" : "checked"%> onclick="checkboxListenerTime(<%=i%>)">
							<label class="lb-sm">&nbsp Όλες τις ώρες της ημέρας</label>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="row">
					<div class="col-sm-6 form-group" style="margin: 0px">
						<div class="form-group" style="margin-bottom: 0px;">
							<label style="white-space: nowrap; margin-bottom: 0px;" class="lb-sm">Απο ημέρα:&nbsp;</label> <select
								class="form-control input-sm" name="startday<%=i%>" id="startday<%=i%>" disabled>
								<option value="0" <%=(startday != null && startday.equals("0")) ? "selected" : ""%>>Όλες τις ημέρες</option>
								<option value="1" <%=(startday != null && startday.equals("1")) ? "selected" : ""%>>Δευτέρα</option>
								<option value="2" <%=(startday != null && startday.equals("2")) ? "selected" : ""%>>Τρίτη</option>
								<option value="3" <%=(startday != null && startday.equals("3")) ? "selected" : ""%>>Τετάρτη</option>
								<option value="4" <%=(startday != null && startday.equals("4")) ? "selected" : ""%>>Πέμπτη</option>
								<option value="5" <%=(startday != null && startday.equals("5")) ? "selected" : ""%>>Παρασκευή</option>
								<option value="6" <%=(startday != null && startday.equals("6")) ? "selected" : ""%>>Σαββάτο</option>
								<option value="7" <%=(startday != null && startday.equals("7")) ? "selected" : ""%>>Κυριακή</option>
							</select> <span style="color: red; font-size: 0.9em; margin: 0px 0 0px 0; white-space: nowrap;"> <%
 	if ((String) request.getAttribute("startday" + i) != null)
 			out.println((String) request.getAttribute("startday" + i));
 %>
							</span>
						</div>
					</div>
					<div class="col-sm-6 form-group" style="margin: 0px">
						<div class="form-group" style="margin-bottom: 0px;">
							<label style="white-space: nowrap; margin-bottom: 0px;" class="lb-sm">Έως ημέρα:&nbsp;</label> <select
								class="form-control input-sm" name="endday<%=i%>" id="endday<%=i%>" disabled>
								<option value="0" <%=(endday != null && endday.equals("0")) ? "selected" : ""%>>Όλες τις ημέρες</option>
								<option value="1" <%=(endday != null && endday.equals("1")) ? "selected" : ""%>>Δευτέρα</option>
								<option value="2" <%=(endday != null && endday.equals("2")) ? "selected" : ""%>>Τρίτη</option>
								<option value="3" <%=(endday != null && endday.equals("3")) ? "selected" : ""%>>Τετάρτη</option>
								<option value="4" <%=(endday != null && endday.equals("4")) ? "selected" : ""%>>Πέμπτη</option>
								<option value="5" <%=(endday != null && endday.equals("5")) ? "selected" : ""%>>Παρασκευή</option>
								<option value="6" <%=(endday != null && endday.equals("6")) ? "selected" : ""%>>Σαββάτο</option>
								<option value="7" <%=(endday != null && endday.equals("7")) ? "selected" : ""%>>Κυριακή</option>
							</select> <span style="color: red; font-size: 0.9em; margin: 0px 0 0px 0; white-space: nowrap;"> <%
 	if ((String) request.getAttribute("endday" + i) != null)
 			out.println((String) request.getAttribute("endday" + i));
 %>
							</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12 form-group" style="margin: 0px">
						<div class="form-group" style="white-space: nowrap; margin: 0px">
							<input type="checkbox" name="alldayCheck<%=i%>" id="alldayCheck<%=i%>" value="true"
								<%=(alldayCheck == null || !alldayCheck.equals("true")) ? "" : "checked"%> onclick="checkboxListenerDay(<%=i%>)">
							<label class="lb-sm">&nbsp; Όλες τις ημέρες της εβδομάδας</label>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="row">
					<div class="col-sm-6 form-group" style="margin: 0px">
						<div class="form-group" style="margin-bottom: 0px;">
							<label style="white-space: nowrap; margin-bottom: 0px;" class="lb-sm">Απο μήνα:&nbsp;</label> <select
								class="form-control input-sm" name="startmonth<%=i%>" id="startmonth<%=i%>" disabled>
								<option value="0" <%=(startmonth != null && startmonth.equals("0")) ? "selected" : ""%>>Όλοι οι μήνες</option>
								<option value="1" <%=(startmonth != null && startmonth.equals("1")) ? "selected" : ""%>>01</option>
								<option value="2" <%=(startmonth != null && startmonth.equals("2")) ? "selected" : ""%>>02</option>
								<option value="3" <%=(startmonth != null && startmonth.equals("3")) ? "selected" : ""%>>03</option>
								<option value="4" <%=(startmonth != null && startmonth.equals("4")) ? "selected" : ""%>>04</option>
								<option value="5" <%=(startmonth != null && startmonth.equals("5")) ? "selected" : ""%>>05</option>
								<option value="6" <%=(startmonth != null && startmonth.equals("6")) ? "selected" : ""%>>06</option>
								<option value="7" <%=(startmonth != null && startmonth.equals("7")) ? "selected" : ""%>>07</option>
								<option value="8" <%=(startmonth != null && startmonth.equals("8")) ? "selected" : ""%>>08</option>
								<option value="9" <%=(startmonth != null && startmonth.equals("9")) ? "selected" : ""%>>09</option>
								<option value="10" <%=(startmonth != null && startmonth.equals("10")) ? "selected" : ""%>>10</option>
								<option value="11" <%=(startmonth != null && startmonth.equals("11")) ? "selected" : ""%>>11</option>
								<option value="12" <%=(startmonth != null && startmonth.equals("12")) ? "selected" : ""%>>12</option>
							</select> <span style="color: red; font-size: 0.9em; margin: 0px 0 0px 0; white-space: nowrap;"> <%
 	if ((String) request.getAttribute("startmonth" + i) != null)
 			out.println((String) request.getAttribute("startmonth" + i));
 %>
							</span>
						</div>
					</div>
					<div class="col-sm-6 form-group" style="margin: 0px">
						<div class="form-group" style="margin-bottom: 0px;">
							<label style="white-space: nowrap; margin-bottom: 0px;" class="lb-sm">Έως μήνα:&nbsp;</label> <select
								class="form-control input-sm" name="endmonth<%=i%>" id="endmonth<%=i%>" disabled>
								<option value="0" <%=(endmonth != null && endmonth.equals("0")) ? "selected" : ""%>>Όλοι οι μήνες</option>
								<option value="1" <%=(endmonth != null && endmonth.equals("1")) ? "selected" : ""%>>01</option>
								<option value="2" <%=(endmonth != null && endmonth.equals("2")) ? "selected" : ""%>>02</option>
								<option value="3" <%=(endmonth != null && endmonth.equals("3")) ? "selected" : ""%>>03</option>
								<option value="4" <%=(endmonth != null && endmonth.equals("4")) ? "selected" : ""%>>04</option>
								<option value="5" <%=(endmonth != null && endmonth.equals("5")) ? "selected" : ""%>>05</option>
								<option value="6" <%=(endmonth != null && endmonth.equals("6")) ? "selected" : ""%>>06</option>
								<option value="7" <%=(endmonth != null && endmonth.equals("7")) ? "selected" : ""%>>07</option>
								<option value="8" <%=(endmonth != null && endmonth.equals("8")) ? "selected" : ""%>>08</option>
								<option value="9" <%=(endmonth != null && endmonth.equals("9")) ? "selected" : ""%>>09</option>
								<option value="10" <%=(endmonth != null && endmonth.equals("10")) ? "selected" : ""%>>10</option>
								<option value="11" <%=(endmonth != null && endmonth.equals("11")) ? "selected" : ""%>>11</option>
								<option value="12" <%=(endmonth != null && endmonth.equals("12")) ? "selected" : ""%>>12</option>
							</select> <span style="color: red; font-size: 0.9em; margin: 0px 0 0px 0; white-space: nowrap;"> <%
 	if ((String) request.getAttribute("endmonth" + i) != null)
 			out.println((String) request.getAttribute("endmonth" + i));
 %>
							</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12 form-group" style="margin: 0px">
						<div class="form-group" style="white-space: nowrap; margin: 0px">
							<input type="checkbox" name="allmonthCheck<%=i%>" id="allmonthCheck<%=i%>" value="true"
								<%=(allmonthCheck == null || !allmonthCheck.equals("true")) ? "" : "checked"%> onclick="checkboxListenerMonth(<%=i%>)">
							<label class="lb-sm">&nbsp; Όλοι οι μήνες του έτους</label>
						</div>
					</div>
				</div>
			</div>
			<hr style="margin: 0px">
		</div>
	</div>
</div>
<script>checkboxListenerDay(<%=i%>);
checkboxListenerTime(<%=i%>);
checkboxListenerMonth(<%=i%>);
</script>
<%
	}
%>
<div style="display: none">
	<div class="row">
		<div class="col-sm-4 form-group" id="addAdvScheduleButton" style="margin-bottom: 16px">
			<button type="button" class="btn btn-sm btn-info glyphicon glyphicon-plus" onclick="addAdvancedSchedule()">
				<b> Προσθήκη διαθεσιμότητας</b>
			</button>
		</div>
		<div class="col-sm-2 form-group" id="removeAdvScheduleButton" style="display: none; margin-bottom: 16px">
			<button type="button" class="btn btn-sm btn-danger glyphicon glyphicon-minus" onclick="removeAdvancedSchedule()">
				<b> Αναίρεση διαθεσιμότητας</b>
			</button>
		</div>
	</div>
</div>
<script>setAdvancedSchedule();</script>