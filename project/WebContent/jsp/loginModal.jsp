<div id="login" class="modal fade in" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Σύνδεση</h4>
				</div>
				<div class="modal-body">
				<% if(request.getAttribute("errormessage")!= null){ %>
				<div class="alert alert-danger text-center" role="alert"><%=request.getAttribute("errormessage") %></div>
				<%} %>
					<form class="form-horizontal" action="/airparking/login" method="post">
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
							<div class="col-sm-10">
								<input type="text" name="email" class="form-control"
									id="inputEmail3" placeholder="Email" value="<%=(request.getAttribute("mail")==null)?"":request.getAttribute("mail")%>">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">Password</label>
							<div class="col-sm-10">
								<input type="password" name="password" class="form-control"
									id="inputPassword3" placeholder="Password">
							</div>
						</div>
						<div style="display:none;"class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<div class="checkbox">
									<label><input type="checkbox"> Remember me</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-success">ΟΚ</button>
								<button type="reset" class="btn btn-danger">Άκυρο</button>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>
	