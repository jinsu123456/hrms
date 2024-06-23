window.addEventListener('load', function() {
	let goToWorkButton = $("#goToWorkButton");
	let leaveWorkButton = $("#leaveWorkButton");
	if(start != null && start != ''){
		if(cls==="btn btn-success btn-icon-split"){
			goToWorkButton.parent().attr("class", cls);
		}else{
			goToWorkButton.attr("class", cls);
		}
	}
	if(end != null && end != ''){
		if(cls==="btn btn-success btn-icon-split"){
			leaveWorkButton.parent().attr("class", cls);
		}else{
			leaveWorkButton.attr("class", cls);
		}
	}
});