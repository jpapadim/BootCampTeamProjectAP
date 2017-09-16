<%@ page import="java.util.ArrayList, airparking.model.Report"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%
	if (session.getAttribute("user") == null) {
		request.setAttribute("message", "You are not authorized to access this resource. Please login.");
%>
<jsp:forward page="intro.jsp" />
<%
	}
%>
<!DOCTYPE html>
<html lang="el">
<!-- CSS files -->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.2.4.min.js"></script>  
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs-3.3.7/jq-2.2.4/dt-1.10.15/r-2.1.1/rg-1.0.0/rr-1.2.0/se-1.2.2/datatables.min.css"/>

<!-- JS files -->     
              
<script type="text/javascript" src="https://cdn.datatables.net/v/bs-3.3.7/jq-2.2.4/dt-1.10.15/r-2.1.1/rg-1.0.0/rr-1.2.0/se-1.2.2/datatables.min.js"></script>         
<script type="text/javascript">
$(document).ready(function() {
	$('#parking _report').DataTable({
	    language: {
	        processing:     "Παρακαλώ περιμένετε...",
	        search:         "Αναζήτηση&nbsp;:",
	        lengthMenu:    "Στοιχεία _MENU_ &nbsp;προβολής",
	        info:           "Προβολή στοιχείων _START_ έως _END_ απο _TOTAL_",
	        infoEmpty:      "Προβολή στοιχείων 0 εως 0 απο 0 ",
	        infoFiltered:   "(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)",
	        infoPostFix:    "",
	        loadingRecords: "Παρακαλώ περιμένετε...",
	        zeroRecords:    "Δεν βρέθηκαν στοιχεία με βάση το φίλτρο αναζήτησης",
	        emptyTable:     "Δεν βρέθηκαν καταχωρήσεις",
	        paginate: {
	            first:      "Πρώτο",
	            previous:   "Προηγούμενο",
	            next:       "Επόμενο",
	            last:       "Τελευταίο"
	        },
	        aria: {
	            sortAscending:  ": Αύξουσα στοίχηση",
	            sortDescending: ": Φθήνουσα στοίχηση"
	        }
	    }
	});} 
);</script>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>AirParking</title>
 
	 
 
<link
	href="https://fonts.googleapis.com/css?family=Baloo+Bhai|Baloo+Bhaina|Baloo+Tamma|Baumans|Candal|Fredoka+One|Gruppo|Krona+One|Londrina+Outline|Prosto+One|Rammetto+One|Shrikhand|Titan+One"
	rel="stylesheet">

</head>
<body>
 

<div class="container-fluid">
		<div class="well"
			style="margin-top: auto; background: rgba(255, 255, 255, 0.75);">
			<div class="row">
				<div class="col-md-12">
					<h3>Owner's parking report</h3>
				</div>
			</div>
			<hr>
			<div class="table-responsive">
				<%-- 
<div class='danger'> <h2 class='text-center'>Δεν βρέθηκε κάποια καταχώρηση</h2></div>
 --%>
				<table class="table table-hover display" id="parking _report">
					<thead>
						<tr>
							 
							<th style="text-align: center;">ID</th>
							<th>Address</th>
							<%--<th style="text-align: center;">Photo</th>
						 ><th style="text-align: center;">User</th>
						<th style="text-align: center;">Comments</th>
						<th style="text-align: center;">Rating</th>--%>
							<th style="text-align: center;">Last booking&nbsp;date</th>
							<th style="text-align: center;">Duration</th>
							<th style="text-align: center;">Offer type</th>
							<th style="text-align: center;">Value per&nbsp;offer</th>
							<th style="text-align: center;">Total(€)</th>
						</tr>
					</thead>
					<tbody id="myTable">
						<%--   Retrieves records from the ResultSet --%>
						 <tbody>
        <tr>
            <td>Linus</td>
            <td>Torvalds</td>
            <td>Computer Science</td>
        </tr>
        <tr>
            <td>Brian</td>
            <td>Kernighan</td>
            <td>Computer Science</td>
        </tr>
        <tr>
            <td>Blaise</td>
            <td>Pascal</td>
            <td>Mathematics</td>
        </tr>
        <tr>
            <td>Larry</td>
            <td>Page</td>
            <td>Computer Science</td>
        </tr>
        <tr>
            <td>Richard</td>
            <td>Hamming</td>
            <td>Mathematics</td>
        </tr>
        <tr>
            <td>Grace</td>
            <td>Hopper</td>
            <td>Computer Science</td>
        </tr>
        <tr>
            <td>Pierre</td>
            <td>Bezier</td>
            <td>Mathematics</td>
        </tr>
        <tr>
            <td>Shigeru</td>
            <td>Miyamoto</td>
            <td>Computer Science</td>
        </tr>
        <tr>
            <td>Leslie</td>
            <td>Lamport</td>
            <td>Computer Science</td>
        </tr>
        <tr>
            <td>Rasmus</td>
            <td>Lerdorf</td>
            <td>Computer Science</td>
        </tr>
        <tr>
            <td>Xavier</td>
            <td>Leroy</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Albert</td>
            <td>Einstein</td>
            <td>Physics</td>
		</tr>
        <tr>
            <td>Bill</td>
            <td>Gates</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Leonard</td>
            <td>De Vinci</td>
            <td>Mathematics</td>
		</tr>
        <tr>
            <td>Pierre</td>
            <td>De Fermat</td>
            <td>Mathematics</td>
		</tr>
        <tr>
            <td>René</td>
            <td>Descartes</td>
            <td>Mathematics</td>
		</tr>
        <tr>
            <td>Alan</td>
            <td>Turing</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Ada</td>
            <td>Lovelace</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Isaac</td>
            <td>Newton</td>
            <td>Physics</td>
		</tr>
        <tr>
            <td>Carl Friedrich</td>
            <td>Gauss</td>
            <td>Mathematics</td>
		</tr>
        <tr>
            <td>John</td>
            <td>Von Neumann</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Claude</td>
            <td>Shannon</td>
            <td>Mathematics</td>
		</tr>
        <tr>
            <td>Tim</td>
            <td>Berners-Lee</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Richard</td>
            <td>Stallman</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Dennis</td>
            <td>Ritchie</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Bjarne</td>
            <td>Stroustrup</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Steve</td>
            <td>Jobs</td>
            <td>Computer Science</td>
		</tr>
    </tbody>
					</tbody>
				</table>
				<div id="paging-first-datatable"></div>
			</div>
		</div>
	</div>
<table id="table_owner" class="display">
    <thead>
        <tr>
            <th>Firstname</th>
            <th>Lastname</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Linus</td>
            <td>Torvalds</td>
            <td>Computer Science</td>
        </tr>
        <tr>
            <td>Brian</td>
            <td>Kernighan</td>
            <td>Computer Science</td>
        </tr>
        <tr>
            <td>Blaise</td>
            <td>Pascal</td>
            <td>Mathematics</td>
        </tr>
        <tr>
            <td>Larry</td>
            <td>Page</td>
            <td>Computer Science</td>
        </tr>
        <tr>
            <td>Richard</td>
            <td>Hamming</td>
            <td>Mathematics</td>
        </tr>
        <tr>
            <td>Grace</td>
            <td>Hopper</td>
            <td>Computer Science</td>
        </tr>
        <tr>
            <td>Pierre</td>
            <td>Bezier</td>
            <td>Mathematics</td>
        </tr>
        <tr>
            <td>Shigeru</td>
            <td>Miyamoto</td>
            <td>Computer Science</td>
        </tr>
        <tr>
            <td>Leslie</td>
            <td>Lamport</td>
            <td>Computer Science</td>
        </tr>
        <tr>
            <td>Rasmus</td>
            <td>Lerdorf</td>
            <td>Computer Science</td>
        </tr>
        <tr>
            <td>Xavier</td>
            <td>Leroy</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Albert</td>
            <td>Einstein</td>
            <td>Physics</td>
		</tr>
        <tr>
            <td>Bill</td>
            <td>Gates</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Leonard</td>
            <td>De Vinci</td>
            <td>Mathematics</td>
		</tr>
        <tr>
            <td>Pierre</td>
            <td>De Fermat</td>
            <td>Mathematics</td>
		</tr>
        <tr>
            <td>René</td>
            <td>Descartes</td>
            <td>Mathematics</td>
		</tr>
        <tr>
            <td>Alan</td>
            <td>Turing</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Ada</td>
            <td>Lovelace</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Isaac</td>
            <td>Newton</td>
            <td>Physics</td>
		</tr>
        <tr>
            <td>Carl Friedrich</td>
            <td>Gauss</td>
            <td>Mathematics</td>
		</tr>
        <tr>
            <td>John</td>
            <td>Von Neumann</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Claude</td>
            <td>Shannon</td>
            <td>Mathematics</td>
		</tr>
        <tr>
            <td>Tim</td>
            <td>Berners-Lee</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Richard</td>
            <td>Stallman</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Dennis</td>
            <td>Ritchie</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Bjarne</td>
            <td>Stroustrup</td>
            <td>Computer Science</td>
		</tr>
        <tr>
            <td>Steve</td>
            <td>Jobs</td>
            <td>Computer Science</td>
		</tr>
    </tbody>
</table>
<div id="paging-first-datatable"></div>
   
</body>
</html>