function selectFn(obj){
	let selected = obj.id;
	if(selected=="userInfo"){
		obj.setAttribute("class", "font-weight-bold text-primary py-1 mr-4 border-bottom-primary d-inline");
		document.querySelector("#myInfo").setAttribute("class", "font-weight-bold text-secondary py-1 d-inline");
		
		document.querySelector(".userInfo").setAttribute("class", "table table-bordered text-center userInfo");
		document.querySelector(".myInfo").setAttribute("class", "myInfo d-none");
		document.querySelector(".userInfoHeader").setAttribute("class", "row userInfoHeader d-flex");
	}else if(selected=="myInfo"){
		obj.setAttribute("class", "font-weight-bold text-primary py-1 mr-4 border-bottom-primary d-inline");
		document.querySelector("#userInfo").setAttribute("class", "font-weight-bold text-secondary py-1 mr-4 d-inline");
		
		document.querySelector(".userInfo").setAttribute("class", "table table-bordered text-center userInfo d-none");
		document.querySelector(".myInfo").setAttribute("class", "myInfo d-block");
		document.querySelector(".userInfoHeader").setAttribute("class", "row userInfoHeader d-none");
	}
}