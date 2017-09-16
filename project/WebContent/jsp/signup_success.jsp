<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ include file="../html/header.html"%>



<%@ include file="navbar.jsp"%>
	<%Object logedinuser=session.getAttribute("user");%>
<div class="container">
      <div class="alert alert-success text-center" role="alert">
        <span style="color: green;  font-size: 1.5em;"> <b>Επιτυχείς καταχώριση!</b></span><br>
        
        <p style="color: black;  font-size: 1em;">(<%=(logedinuser!=null && (logedinuser instanceof Owner))?"Driver & Parking Owner":"Driver" %> privileges).</p><br>
         <a class="btn btn-default" href="/airparking/jsp/intro.jsp"> <i class="fa fa-search"></i>&nbsp; Αναζήτηση θέσης parking</a>
      </div>
      
        <%if(logedinuser!=null && !(logedinuser instanceof Owner)){%>
      <div class="alert alert-info text-center" role="alert">
        <p style="color: black;  font-size: 1em;">Αν ενδιαφέρεστε να καταχωρίσετε ιδιόκτητο χώρο parking προς ενοικίαση,<br> 
        πρέπει να αναβαθμίσετε τον λογαριασμό σας, απο τις επιλογές του μενού.</p>
        </div>
        <%} %>
    </div>
<%@ include file="../html/footer.html"%>