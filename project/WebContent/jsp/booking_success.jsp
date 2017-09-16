<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ include file="../html/header.html"%>



<%@ include file="navbar.jsp"%>
<%
String message="Επιτυχείς καταχώριση!";
%>
<div class="container">
      <div class="alert alert-success text-center" role="alert">
        <span style="color: green;  font-size: 1.5em;"> <b><%= message%></b></span><br>
        <strong><a href="/airparking/jsp/userReport.jsp" class="btn btn-info" role="button">Παρακαλώ ελέγξτε την καταχώρισή σας</a>
      </strong>
      </div>
    </div>
<%@ include file="../html/footer.html"%>