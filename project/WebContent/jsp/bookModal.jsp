	<div id="<%="book_"+request.getAttribute("index") %>" class="modal fade in" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Επιβεβαίωση κράτησης</h4>
				</div>
				<div class="modal-body">
				<p> <b>Parking address:</b> <%=request.getAttribute("address") %> </p>
				<p> <b>From:</b> <%= request.getAttribute("from")%> </p>
				<p> <b>Until:</b> <%= request.getAttribute("until") %> </p>
				<p> <b>Duration:</b> <%= request.getAttribute("Duration") %> </p>
				<p> <b>Total Cost:</b> <%= request.getAttribute("cost") %>&euro; </p>
					<form class="form-horizontal" action="/airparking/bookingController" method="post">
						<div class="form-group">
							
								<button type="submit" class="btn btn-success">Κράτηση</button>
								<button class="btn btn-danger" data-dismiss="modal">Άκυρο</button>
								<input type="hidden" name="parkingIndex" value="<%=request.getAttribute("index")%>"/>
							
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
