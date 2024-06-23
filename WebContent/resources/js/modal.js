$(function(){
	$("#confirm").click(function(){
		modalClose();
	});
	$(document).on("click",".modal-open",function(key){
		if(key.clientX!==0 && key.clientY!==0){
			$("#popup").css('display','flex').hide().fadeIn();
		}
	});
	$(document).on("keyup",".modal-open",function(key){
		if(key.keyCode==13) {
			modalClose();
		}
	});
	$("#close").click(function(){
		modalClose();
	});
	function modalClose(){
		$("#popup").fadeOut();
	}
});