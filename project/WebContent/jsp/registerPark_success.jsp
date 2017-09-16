<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ include file="../html/header.html"%>



<%@ include file="navbar.jsp"%>
<%
String message="Επιτυχής καταχώριση!";
String parkingID=(String)request.getParameter("parkingID");
if(parkingID!=null && !parkingID.isEmpty() ){
	message="Η καταχώριση parking ενημερώθηκε επιτυχώς!";
}%>
<div class="container">
      <div class="alert alert-success text-center" role="alert">
        <span style="color: green;  font-size: 1.5em;"> <b><%= message%></b></span><br>
        <a href="/airparking/oreport" class="btn btn-info" role="button">Παρακαλώ ελέγξτε τις καταχωρίσεις σας!</a>
      </div>
    </div>
<%@ include file="../html/footer.html"%>