function submitFn(){
	let startDate = $("#startDate");
	let endDate = $("#endDate");
	let startTime = $("#startTime option:selected");
	let endTime = $("#endTime option:selected");
	if(startDate.val()===""){
		alert("연차 시작일을 선택해주세요.");
		startDate.focus();
		return false;
	}
	if(startTime.val()==="NONE"){
		alert("시작 시간을 선택해주세요.");
		return false;
	}
	if(endDate.val()===""){
		alert("연차 종료일을 선택해주세요.");
		endDate.focus();
		return false;
	}
	if(endTime.val()==="NONE"){
		alert("종료 시간을 선택해주세요.");
		return false;
	}
	let msg = confirm("연차를 신청하시겠습니까?");
	if(msg===true){
		return true;
	}
	return false;
}

function itemChange() {
	let startDate = $("#startDate").val();
	let endDate = $("#endDate").val();
	
	let endTimeArr = ["10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"];
	let changeItem;
	if(startDate==endDate){
		var selectItem = $("#startTime").val();
		if(selectItem === "09:00"){
			changeItem = endTimeArr.slice(0,9);
		}else if(selectItem === "10:00" ) {
			changeItem = endTimeArr.slice(1,9);
		}else if (selectItem === "11:00") {
			changeItem = endTimeArr.slice(2,9);
		}else if (selectItem === "12:00") {
			changeItem = endTimeArr.slice(3,9);
		}else if (selectItem === "13:00") {
			changeItem = endTimeArr.slice(4,9);
		}else if (selectItem === "14:00") {
			changeItem = endTimeArr.slice(5,9);
		}else if (selectItem === "15:00") {
			changeItem = endTimeArr.slice(6,9);
		}else if (selectItem === "16:00") {
			changeItem = endTimeArr.slice(7,9);
		}else if (selectItem === "17:00") {
			changeItem = endTimeArr.slice(8,9);
		}
	
	}else{
		changeItem = endTimeArr;
	}
	$('#endTime').empty();
	let firstOption = $("<option value='NONE'>종료 시간</option>");
	$('#endTime').append(firstOption);
	for(let i=0; i < changeItem.length; i++) {
		let option = $("<option>" + changeItem[i] + "</option>");
		$('#endTime').append(option);
	}
	
}

function noWeekends (date){
	return [(date.getDay() != 0 && date.getDay() != 6)];
}

function addBusinessDays(startDate, numDays) {
	var currentDate = new Date(startDate.getTime());
	var businessDaysAdded = 0;
	while (businessDaysAdded < numDays) {
	  currentDate.setDate(currentDate.getDate() + 1);
	
	  if (currentDate.getDay() !== 0 && currentDate.getDay() !== 6) {
	    businessDaysAdded++;
	  }
	}
	
	return currentDate;
}	

$("#startDate").datepicker({
	beforeShowDay: noWeekends,
	dateFormat: 'yy-mm-dd',
	minDate: 0,
	maxDate: "+14d",
	onClose: function(selectedDate) {
		let paramDate = new Date(selectedDate);
		let maxD = addBusinessDays(paramDate, 6);
		
		$("#endDate").val("");
		$('#endDate').datepicker('destroy');
		
		$("#endDate").datepicker({
			beforeShowDay: noWeekends,
			dateFormat: 'yy-mm-dd',
			minDate: selectedDate,
			maxDate: maxD
		})
	}
});
