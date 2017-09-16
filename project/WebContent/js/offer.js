function addOffer() {
	
	for (var i = 2; i < 5; i++) {
		if (document.getElementById('offer' + i).style.display == "none") {
			document.getElementById('offer' + i).style.display = "block";
			document.getElementById('numOffers').value=String(i);
			
			if (i == 4)
				document.getElementById('addOfferButton').style.display = "none";
			document.getElementById('removeOfferButton').style.display = "block";
			location.href = '#offer' + i;
			break;
		}
	}
	disableOptions();
}
function removeOffer() {
	for (var i = 4; i > 1; i--) {
		if (document.getElementById('offer' + i).style.display != "none") {
			document.getElementById('offer' + i).style.display = "none";
			document.getElementById('numOffers').value=String(i-1);
			if (i == 2)
				document.getElementById('removeOfferButton').style.display = "none";
			document.getElementById('addOfferButton').style.display = "block";
			if (i > 2)
				location.href = '#offer' + (i - 1);
			break;
		}
	}
	disableOptions();
}
function setOffer(){
	var numOfOfOpen=parseInt(document.getElementById('numOffers').value);
	document.getElementById('removeOfferButton').style.display="block";
	document.getElementById('addOfferButton').style.display="block";
	if(numOfOfOpen==1)document.getElementById('removeOfferButton').style.display="none";
	if(numOfOfOpen==4)document.getElementById('addOfferButton').style.display="none";
	for(var i=1;i<5;i++){
		if(i<=numOfOfOpen) {document.getElementById('offer'+i).style.display="block";}
		else {document.getElementById('offer'+i).style.display="none";}
	}
}

function disableOptions(){
	var num=parseInt(document.getElementById('numOffers').value);
	for (var i = 1; i < num; i++){
		document.getElementById('offerlist'+i).disabled=true;
	}
	document.getElementById('offerlist'+num).disabled=false;
	
	var numOfOfOpen=parseInt(document.getElementById('numOffers').value);
	if(numOfOfOpen>1){
		for(var i=1;i<=numOfOfOpen;i++){
			for(var j=0;j<4;j++){
				document.getElementById("offerlist"+i).options[j].disabled=false;
			}
		}
	for(var i=2;i<=numOfOfOpen;i++){
		for(var j=1;j<i;j++){
			var selInt=document.getElementById("offerlist"+(j)).selectedIndex;
			document.getElementById("offerlist"+i).options[selInt].disabled=true;
		}
		
	}
	}
	
	var index=document.getElementById("offerlist"+num).selectedIndex;
	if(document.getElementById("offerlist"+num).options[index].disabled){
	for (var i = 0; i < 4; i++){
		if(!document.getElementById("offerlist"+num).options[i].disabled){
			document.getElementById("offerlist"+num).options[i].selected=true;
			break;
		}
	}
	}
	
}