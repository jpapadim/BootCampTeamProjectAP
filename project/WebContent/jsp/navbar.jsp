<%@ page import="airparking.model.User"%>
<%@ page import="airparking.model.Owner"%>
<%
	User user = null;
	if (session.getAttribute("user") != null)
		user = (User) session.getAttribute("user");
%>
<nav class="navbar"></nav>
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar top-bar"></span> <span class="icon-bar middle-bar"></span>
				<span class="icon-bar bottom-bar"></span>
			</button>
			<a class="navbar-brand" href="/airparking/jsp/intro.jsp">AirParking</a>
		</div>

		<!-- Collect content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<%if (user == null) {%>
				<li><a href="/airparking/jsp/about.jsp"><i
						class="fa fa-user" aria-hidden="true"> Σχετικά με εμάς </i></a></li>
				<li><a href="/airparking/jsp/signup.jsp"><i
						class="fa fa-sign-in" aria-hidden="true"> Εγγραφή </i></a></li>
				<li><a href='#' data-toggle='modal' data-target='#login'><i
						class="fa fa-sign-in" aria-hidden="true"> Σύνδεση</i></a></li>
				<%} 
				  else { 
					if(!(user instanceof Owner)) {%>
				<li class="intro"><a href="/airparking/jsp/intro.jsp"><i
						class="fa fa-search" aria-hidden="true">&nbsp;Αναζήτηση </i></a></li>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">Επιλογές χρήστη <span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/airparking/jsp/signup.jsp"> Αναβάθμιση
								λογαριασμού</a></li>
						<li><a href="/airparking/jsp/userReport.jsp">Οι
								ενοικιάσεις μου</a></li>
					</ul></li>
				<%}else{%>
				<li class="intro"><a href="/airparking/jsp/intro.jsp"><i
						class="fa fa-search" aria-hidden="true">&nbsp;Αναζήτηση </i></a></li>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">Επιλογές Ιδιοκτήτη <span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/airparking/jsp/RegisterParking.jsp">Καταχώριση
								νέου parking</a></li>
						<li><a href="/airparking/oreport">Tα parking μου</a></li>
						<li><a href="/airparking/jsp/userReport.jsp">Οι
								ενοικιάσεις μου</a></li>
						<% if (user.getUserID() == 9 || user.getUserID() == 19 ||user.getUserID() == 11 ){ 
						       out.print("<li><a href='/airparking/jsp/viewDBcontent.jsp'>View SQL (Admin only)</a></li>");
						 } %>
					</ul></li>
				<%}%>
				<li><a href='/airparking/logout'><i class="fa fa-sign-out"
						aria-hidden="true">&nbsp;Αποσύνδεση&nbsp;</i></a></li>
				<%}%>
			</ul>
			<%if (user != null) {%>
			<p class='navbar-text navbar-center'>
				<b>Καλώς ήρθες <%=user.getfName()%>&nbsp;!
				</b>
			</p>
			<%}%>
		</div>
	</div>
</nav>
<script>
$(function(){
    if (window.location.href.indexOf("intro.jsp") > -1) {
        $(".intro").hide();
     }
});
 </script>
<%if (user == null) {%>
<%@ include file="loginModal.jsp"%>
<%}%>