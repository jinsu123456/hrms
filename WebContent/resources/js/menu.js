const menu1 = document.querySelector("#menu1");
const menu2 = document.querySelector("#menu2");
const menubar1 = document.querySelector("#menubar1");
const menubar2 = document.querySelector("#menubar2");

window.onload = function(){
	document.querySelector("#menubar1").setAttribute("class","menubar text-decoration");
}

function displayFn(obj){
	if(obj==1){
		menu1.style.display = "block";
		menu2.style.display = "none";
		menubar1.setAttribute("class","menubar text-decoration");
		if(menubar2!=null){
			menubar2.setAttribute("class","menubar");
		}
	}else if(obj==2){
		menu1.style.display = "none";
		menu2.style.display = "block";
		menubar1.setAttribute("class","menubar");
		if(menubar2!=null){
			menubar2.setAttribute("class","menubar text-decoration");
		}
	}
}