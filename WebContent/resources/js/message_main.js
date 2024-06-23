function selectFn(obj){
	let selected = obj.id;
	if(selected=="received"){
		obj.setAttribute("class", "font-weight-bold text-primary py-1 mr-4 border-bottom-primary d-inline");
		document.querySelector("#sent").setAttribute("class", "font-weight-bold text-secondary py-1 d-inline");
		
		document.querySelector(".received").setAttribute("class", "table table-bordered text-center received");
		document.querySelector(".sent").setAttribute("class", "table table-bordered text-center sent d-none");
		
		document.querySelector("#recPaging").setAttribute("class", "pagination justify-content-center d-none");
		document.querySelector("#paging").setAttribute("class", "pagination justify-content-center");
		
		document.querySelector("#menu").setAttribute("value","received");
	}else if(selected=="sent"){
		obj.setAttribute("class", "font-weight-bold text-primary py-1 mr-4 border-bottom-primary d-inline");
		document.querySelector("#received").setAttribute("class", "font-weight-bold text-secondary py-1 mr-4 d-inline");
		
		document.querySelector(".received").setAttribute("class", "table table-bordered text-center received d-none");
		document.querySelector(".sent").setAttribute("class", "table table-bordered text-center sent");
		
		document.querySelector("#paging").setAttribute("class", "pagination justify-content-center d-none");
		document.querySelector("#recPaging").setAttribute("class", "pagination justify-content-center");
		
		document.querySelector("#menu").setAttribute("value","sent");
	}
}