<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../html/header.html"%>
	<%@ include file="navbar.jsp"%>
	<div class="container">
		<h1 class="well"><%= (user == null) ? ("Φόρμα εγγραφής") : ("Αναβάθμιση λογαριασμού") %></h1>
		<div class="col-lg-12 well">
			<div class="row">
				<form action="/airparking/signupcontroller" method="post">
					<div class="col-sm-12">
					    <%if(user == null) {%>
						<div class="row">
							<div class="col-sm-6 form-group">
								<label>Όνομα</label> <input type="text" id="firstname" name="firstname"
									placeholder="Εισάγετε το όνομά σας.." class="form-control" value="<%=user == null ? "" : user.getfName()%>">
								<span style="color: red;font-size:0.9em;margin: 0px 0 0px 0;"> <%-- <%
									 	if ((String) request.getAttribute("fname") != null) 
									 		out.println((String) request.getAttribute("fname"));%> --%>
								</span>
							</div>
							<div class="col-sm-6 form-group">
								<label>Επώνυμο</label> <input type="text" id="lastname" name="lastname" placeholder="Εισάγετε το επώνυμό σας.."
									class="form-control" value="<%=user == null ? "" : user.getlName()%>"> <span style="color: red;font-size:0.9em;margin: 0px 0 0px 0;"> <%-- <%
									 	if ((String) request.getAttribute("lname") != null) 
									 		out.println((String) request.getAttribute("lname"));%> --%>
								</span>
							</div>
						</div>
						<div class="form-group">
							<label> Email </label> <input type="text" id="email" name="email" placeholder="Εισάγετε το email σας.."
								class="form-control" value="<%=user == null ? "" : user.getEmail()%>"> <span style="color: red;font-size:0.9em;margin: 0px 0 0px 0;"> <%-- <%
									 	if ((String) request.getAttribute("email") != null)
									 		out.println((String) request.getAttribute("email"));%> --%>
							</span>
						</div>
						<div class="form-group">
							<label>Password</label> <input type="password" id="password" name="password"
								placeholder="Εισάγετε password.. (ελάχιστο μήκος 8 χαρακτήρες)" class="form-control"> <span style="color: red;font-size:0.9em;margin: 0px 0 0px 0;"><%--  <%
								if ((String) request.getAttribute("password") != null) 
			 		               out.println((String) request.getAttribute("password"));%> --%>
							</span>
						</div>
						<%if (user == null) {%>
						<div class="form-group">
							<label>Password (Confirm)</label> <input type="password" id="confirmpassword" name="confirmpassword"
								placeholder="Επιβεβαίωση password.." class="form-control"> <span style="color: red;font-size:0.9em;margin: 0px 0 0px 0;"> <%-- <%
								if ((String) request.getAttribute("confirmpassword") != null) 
							 		out.println((String) request.getAttribute("confirmpassword"));%> --%>
							</span>
						</div>
						<%}%>
						<div class="form-group">
							<div class="checkbox">
								<label> <input type="checkbox" id="ownerCheck" name="ownerCheck" onclick="isOwner()" value="true" checked="false"> <b>Επιλογή λογαριασμού με δικαιώματα καταχώρισης parking</b>
								</label>
							</div>
						</div>
						<%} %>
						<div id="bankRegister" style="display:block"> 
						<div class="row" >
							<div class="col-sm-6 form-group">
								<label>Διαθέτω λογαρισμώ σε τράπεζα</label> <select id="bankname" name="bankname" class="form-control">
									<option value="">Επιλέξτε τράπεζα..</option>
									<option value="nbg">NBG</option>
									<option value="eurobank">EUROBANK</option>
									<option value="alpha">ALPHA BANK</option>
									<option value="pireus">PIRAEUS BANK</option>
									<option value="attica">ATTICA BANK</option>
								</select> <span style="color: red;font-size:0.9em;margin: 0px 0 0px 0;">  <%-- <%
								 	if ((String) request.getAttribute("bankName") != null) 
								 		out.println((String) request.getAttribute("bankName"));%> --%>
								</span>
							</div>
							<script type="text/javascript">document.getElementById("bankname").value = '<%=(user != null && (user instanceof Owner)) ? ((Owner)user).getBank() : ""%>';</script>
							<div class="col-sm-6 form-group">
								<label>IBAN</label> <input type="text" id="bankaccount" name="bankaccount" maxlength="27"
									placeholder="Εισάγετε λογαριασμό ΙΒΑΝ.." class="form-control"
									value='<%=(user != null && (user instanceof Owner)) ? ((Owner)user).getAccount() : ""%>'> <span style="color: red;font-size:0.9em"> <%--  <%
									 	if ((String) request.getAttribute("bankAccount") != null) 
									 		out.println((String) request.getAttribute("bankAccount"));%> --%>
								</span>
							</div>
						</div>
						<div class="row" >
							<div class="col-sm-6 form-group">
								<label>Αριθμός Φορολογικού Μητρώου</label> <input type="text" id="taxregistrationnumber" name="taxregistrationnumber" maxlength="9"
									placeholder="Εισάγετε ΑΦΜ.." class="form-control"
									value='<%=(user != null && (user instanceof Owner)) ? ((Owner)user).getAccount() : ""%>'> <span style="color: red;font-size:0.9em"> <%--  <%
									 	if ((String) request.getAttribute("taxregistrationnumber") != null) 
									 		out.println((String) request.getAttribute("taxregistrationnumber"));%> --%>
								</span>
							</div>
							<div class="col-sm-6 form-group">
								<label>Εφορία</label> <input type="text" id="taxoffice" name="taxoffice"
									placeholder="Εισάγεται αρμόδια ΔΟΥ.." class="form-control"
									value='<%=(user != null && (user instanceof Owner)) ? ((Owner)user).getAccount() : "" %>'> <span style="color: red;font-size:0.9em"> 
									 	<%-- <%if ((String) request.getAttribute("taxoffice") != null) 
									 		out.println((String) request.getAttribute("taxoffice"));%> --%></span>
								
							</div>
						</div>
						</div>
						<button type="submit" class="btn btn-lg btn-info">Καταχώριση</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
	<%if(user == null) {%>
		function isOwner() {
			//window.alert(document.getElementById("bankRegister").style.display);
			if (document.getElementById("ownerCheck").checked == true) {
				document.getElementById("bankRegister").style.display = "block";
			} else
				document.getElementById("bankRegister").style.display = "none";
		}
		<% if ((String)request.getParameter("ownerCheck") != null) {%>
		document.getElementById('ownerCheck').checked=true; <%}else{%> document.getElementById('ownerCheck').checked=false;<%}%>
		isOwner();
		<% if ((String)request.getParameter("firstname")!=null) {%>
				document.getElementById('firstname').value="<%=(String)request.getParameter("firstname")%>"; <%}%>
		<% if ((String)request.getParameter("lastname")!=null) {%>
				document.getElementById('lastname').value="<%=(String)request.getParameter("lastname")%>"; <%}%>
		<% if ((String)request.getParameter("email")!=null) {%>
				document.getElementById('email').value="<%=(String)request.getParameter("email")%>"; <%}%>	
				<%}%>
		<% if ((String)request.getParameter("bankname")!=null) {%>
		document.getElementById('bankname').value="<%=(String)request.getParameter("bankname")%>"; <%}%>
<% if ((String)request.getParameter("bankaccount")!=null) {%>
		document.getElementById('bankaccount').value="<%=((String)request.getParameter("bankaccount").replace('-',' ')).replaceAll("\\s+","")%>"; <%}%>
		<% if ((String)request.getParameter("taxregistrationnumber")!=null) {%>
		document.getElementById('taxregistrationnumber').value="<%=((String)request.getParameter("taxregistrationnumber").replace('-',' ')).replaceAll("\\s+","")%>"; <%}%>
		<% if ((String)request.getParameter("taxoffice")!=null) {%>
		document.getElementById('taxoffice').value="<%=(String)request.getParameter("taxoffice")%>"; <%}%>
		
		</script>
	<%@ include file="../html/footer.html"%>