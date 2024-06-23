function submitFn(){
	let date = $("#startDate");
	let startTime = $("#startTime option:selected");
	let endTime = $("#endTime option:selected");
	if(date.val()===""){
		alert("날짜를 선택해주세요.");
		date.focus();
		return false;
	}
	if(startTime.val()==="NONE"){
		alert("시작 시간을 선택해주세요.");
		return false;
	}
	if(endTime.val()==="NONE"){
		alert("종료 시간을 선택해주세요.");
		return false;
	}
	let msg = confirm("초과근무를 신청하시겠습니까?");
	if(msg===true){
		return true;
	}
	return false;
}

function itemChange() {
	let endTimeArr = ["13:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"];
	
	var selectItem = $("#startTime").val();
	var changeItem;
	if(selectItem === "12:00"){
		changeItem = endTimeArr.slice(0,1);
	}else if(selectItem === "18:00" ) {
		changeItem = endTimeArr.slice(1,7);
	}else if (selectItem === "19:00") {
		changeItem = endTimeArr.slice(2,7);
	}else if (selectItem === "20:00") {
		changeItem = endTimeArr.slice(3,7);
	}else if (selectItem === "21:00") {
		changeItem = endTimeArr.slice(4,7);
	}else if (selectItem === "22:00") {
		changeItem = endTimeArr.slice(5,7);
	}else if (selectItem === "23:00") {
		changeItem = endTimeArr.slice(6);
	}
	
	$('#endTime').empty();
	let firstOption = $("<option value='NONE'>종료 시간</option>");
	$('#endTime').append(firstOption);
	for(let i=0; i < changeItem.length; i++) {
		let option = $("<option>" + changeItem[i] + "</option>");
		$('#endTime').append(option);
	}
}

$(".datepicker").datepicker({
	minDate: 0
})