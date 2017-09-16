<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
%>
<!DOCTYPE html>
<html lang="el">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="favicon-car.ico?" type="image/x-icon" />
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>AirParking</title>
<!-- Bootstrap -->
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet"
>
<link rel="stylesheet" href="../css/airparking.css">
<link
	href="https://fonts.googleapis.com/css?family=Baloo+Bhai|Baloo+Bhaina|Baloo+Tamma|Baumans|Candal|Fredoka+One|Gruppo|Krona+One|Londrina+Outline|Prosto+One|Rammetto+One|Shrikhand|Titan+One"
	rel="stylesheet"
>
</head>
<body>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"
	></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	></script>
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
			<div class="row">
				<div class="span12 page-header text-center">
					<h3>Air Parking - Our awesome team!</h3>
				</div>
			</div>
			<div>
				<p>To project μας κινείται στα πρότυπα συνεργατικής οικονομίας και
				    δημιουργήθηκε στα πλαίσια του 2ου Coding Bootcamp ως πρακτική εφαρμογή.
				    Case Study:</br>
				    Ο ιδιοκτήτης μιας θέσης parking, σε κάποια piloti ή σε στεγαζόμενο
					χώρο μπορεί να ενοικιάσει την θέση του για όσο χρόνο αυτός επιθυμεί
					και να κερδίσει ένα επιπλέον εισόδημα για τις μέρες και ώρες που αυτός
					δεν την χρειάζεται. Αντίστοιχα ο πελάτης μπορεί να βρεί έναν
					ασφαλή χώρο για την φιλοξενία του οχήματός του με μικρότερο
					συνήθως κόστος απο τα επαγγελματικά parking αλλά και σε χώρους όπου
					αυτά δεν υπάρχουν.</br></p>
			</div>
			<div class="row text-center">
				<div class="span4 col-md-4">
					<a href="#"><img src="../images/kelly.jpg" width="160"
						height="160" class="img-circle"
					></a>
					<h3>Καίλη Μπάρμπα</h3>
					<p class="text-center social-contacts">
						 <a href="https://github.com/kelly-b"><i
							class="fa fa-github fa-2x" aria-hidden="true"
						></i></a> <a href="https://www.linkedin.com/in/kelly-barba-a72aa3129/"><i
							class="fa fa-linkedin-square fa-2x" aria-hidden="true"
						></i></a>
					</p>
				</div>
				<div class="span4 col-md-4">
					<a href="#"><img src="../images/nikos.jpg" width="160"
						height="160" class="img-circle"
					></a>
					<h3>Νίκος Μαραγκός</h3>
					<p class="text-center social-contacts">
						 <a href="https://github.com/maragnik"><i
							class="fa fa-github fa-2x" aria-hidden="true"
						></i></a> <a href="https://www.linkedin.com/in/nikos-maragos-10a20a128/"><i
							class="fa fa-linkedin-square fa-2x" aria-hidden="true"
						></i></a>
					</p>
				</div>
				<div class="span4 col-md-4">
					<a href="#"><img src="../images/john.jpg" width="160"
						height="160" class="img-circle"
					></a>
					<h3>Γιάννης Παπαδημητρίου</h3>
					<p class="text-center social-contacts">
						 <a href="https://github.com/jpapadim"><i
							class="fa fa-github fa-2x" aria-hidden="true"
						></i></a> <a
							href="https://www.linkedin.com/in/ioannis-papadimitriou-95142915/"
						><i class="fa fa-linkedin-square fa-2x" aria-hidden="true"></i></a>
					</p>
				</div>
			</div>
			<hr>
			<div class="col">
				<div class="span7 col-md-7">
					<h3 class="main-color">OUR PROJECT</h3>
					<div class="row-fluid about-box">
						<div class="span3">
							<div class="circ-wrap">
								<i class="fa fa-television fa-4x" aria-hidden="true"></i>
							</div>
						</div>
						<div class="span8">
							<h3>
								<span>WEB &amp; UI DESIGN</span>
							</h3>
							<p class="">Bootstrap framework with a touch of custom CSS
								and a pinch of Javascript & JQuery to make it look and feel better.</p>
						</div>
					</div>
					<div class="row-fluid about-box">
						<div class="span3">
							<div class="circ-wrap">
								<i class="fa fa-cogs fa-4x" aria-hidden="true"></i>
							</div>
						</div>
						<div class="span8">
							<h3>
								<span>APPLICATION &amp; DEVELOPMENT</span>
							</h3>
							<p class="">Pure java for the logic, some SQL for the
								database and a few third party plug-ins for enhanced
								functionality like Joda time.</p>
						</div>
					</div>
					<div class="row-fluid about-box">
						<div class="span3">
							<div class="circ-wrap">
								<i class="fa fa-server fa-4x" aria-hidden="true"></i>
							</div>
						</div>
						<div class="span8">
							<h3>
								<span>3 TIER ARCHITECTURE </span>
							</h3>
							<p class="">Client - server based architecture. Techs used:
								Apache Tomcat and MySQL</p>
						</div>
					</div>
				</div>
			</div>
			<div class="col col-sm-4 col-md-offset-1">
				<div class="span5">
					<h3 class="main-color">SKILLS
						ACQUIRED</h3>
					<h4 style="padding-top: 30px;">CODING IN JAVA</h4>
					<div class="progress">
						<div class="progress-bar progress-bar-success" role="progressbar"
							aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"
							style="width: 55%"
						></div>
					</div>
					<h4>SQL</h4>
					<div class="progress">
						<div class="progress-bar progress-bar-info" role="progressbar"
							aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"
							style="width: 15%"
						></div>
					</div>
					<h4>DESIGN</h4>
					<div class="progress">
						<div class="progress-bar progress-bar-warning" role="progressbar"
							aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"
							style="width: 15%"
						></div>
					</div>
					<h4>JAVASCRIPT</h4>
					<div class="progress">
						<div class="progress-bar progress-bar-danger" role="progressbar"
							aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"
							style="width: 10%"
						></div>
					</div>
					<h4>JQUERY</h4>
					<div class="progress">
						<div class="progress-bar " role="progressbar" aria-valuenow="40"
							aria-valuemin="0" aria-valuemax="100"
							style="width: 5%; color: #000021;"
						></div>
					</div>
				</div>
			</div>
			<div class="row">&nbsp;</div>
		</div>
		<%@ include file="../html/footer.html"%>