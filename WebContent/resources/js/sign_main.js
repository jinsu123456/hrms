function selectFn(obj) {
	let selected = obj.id;
	if (selected == "docu") {
		obj.setAttribute("class", "font-weight-bold text-primary py-1 mr-4 border-bottom-primary d-inline");
		document.querySelector("#vaca").setAttribute("class", "font-weight-bold text-secondary py-1 mr-4 d-inline");
		document.querySelector("#over").setAttribute("class", "font-weight-bold text-secondary py-1 d-inline");

		document.querySelector(".docuTable").setAttribute("class", "table table-bordered text-center docuTable");
		document.querySelector(".vacaTable").setAttribute("class", "table table-bordered text-center vacaTable d-none");
		document.querySelector(".overTable").setAttribute("class", "table table-bordered text-center overTable d-none");
	} else if (selected == "vaca") {
		obj.setAttribute("class", "font-weight-bold text-primary py-1 mr-4 border-bottom-primary d-inline");
		document.querySelector("#docu").setAttribute("class", "font-weight-bold text-secondary py-1 mr-4 d-inline");
		document.querySelector("#over").setAttribute("class", "font-weight-bold text-secondary py-1 d-inline");

		document.querySelector(".docuTable").setAttribute("class", "table table-bordered text-center docuTable d-none");
		document.querySelector(".vacaTable").setAttribute("class", "table table-bordered text-center vacaTable");
		document.querySelector(".overTable").setAttribute("class", "table table-bordered text-center overTable d-none");
	} else if (selected == "over") {
		obj.setAttribute("class", "font-weight-bold text-primary py-1 border-bottom-primary d-inline");
		document.querySelector("#docu").setAttribute("class", "font-weight-bold text-secondary py-1 mr-4 d-inline");
		document.querySelector("#vaca").setAttribute("class", "font-weight-bold text-secondary py-1 mr-4 d-inline");

		document.querySelector(".docuTable").setAttribute("class", "table table-bordered text-center docuTable d-none");
		document.querySelector(".vacaTable").setAttribute("class", "table table-bordered text-center vacaTable d-none");
		document.querySelector(".overTable").setAttribute("class", "table table-bordered text-center overTable");
	}
}

function iClickFn(clicked) {
	clicked.focus();
}

$.datepicker.setDefaults({
	closeText: "닫기",
	prevText: "이전달",
	nextText: "다음달",
	currentText: "오늘",
	monthNames: ["1월", "2월", "3월", "4월", "5월", "6월",
		"7월", "8월", "9월", "10월", "11월", "12월"
	],
	monthNamesShort: ["1월", "2월", "3월", "4월", "5월", "6월",
		"7월", "8월", "9월", "10월", "11월", "12월"
	],
	dayNames: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"],
	dayNamesShort: ["일", "월", "화", "수", "목", "금", "토"],
	dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
	weekHeader: "주",
	dateFormat: "yy-mm-dd", // 날짜형태 예)yy년 m월 d일
	firstDay: 0,
	isRTL: false,
	showMonthAfterYear: true,
	yearSuffix: "년"
})

function iClickFn(clicked) {
	clicked.focus();
}

$("#startDate").datepicker({
	maxDate: 0,
	onSelect: function(){
		var startDate = $("#startDate").datepicker('getDate');
		var endDate = $("#endDate").datepicker('getDate');
			if (endDate != null) {
				if (startDate > endDate) {
					alert("조회 종료일이 시작일보다 전입니다. \n종료일을 다시 설정해주세요.");
					var endDateMax = new Date(startDate);
					endDateMax.setDate(startDate.getDate() +10);
					$("#endDate").val("");
				}  
			}
			searchFn();
		}
});

$("#endDate").datepicker({
	maxDate: 0,
	beforeShow: function() {
		var startDate = $("#startDate").datepicker('getDate');
		if (startDate != null) {
			$(this).datepicker('option', 'minDate', startDate);
		}
	},
	onSelect: function(){
		searchFn();
	}
});

function searchFn(){
	let startDate = $("#startDate").val();
	let endDate = $("#endDate").val();
	let name = $('#search').val();
	let mySignState = $('#selectMySignState').val();
	$.ajax({
		url:"search.do",
		type:"get",
		data:{startDate : startDate, endDate : endDate, name : name, mySignState : mySignState},
		success:function(data){
			var docResult = '';
			var vacaResult = '';
			var overResult = '';
				for(i in data.list.docVO){
					docResult += '	<tr>';
					docResult += '		<td>';
					docResult += '			<div class="d-inline card text-primary text-center px-1 mr-1 border-primary font-weight-bold">'+data.list.docVO[i].dept+'</div>';
					docResult += '			<span class="text-dark font-weight-bold">'+data.list.docVO[i].name+'</span> ';
					docResult += '			<span class="text-xs font-weight-bold">'+data.list.docVO[i].position+'</span>';
					docResult += '		</td>';
					docResult += '		<td>';
											if(data.list.docVO[i].state == '0'){
					docResult += '					<div class="d-inline card text-secondary text-center px-2 mr-1 border-secondary font-weight-bold">대기</div>';
											}else if(data.list.docVO[i].state == '1'){
					docResult += '					<div class="d-inline card text-danger text-center px-2 mr-1 border-danger font-weight-bold">진행</div>';
											}else if(data.list.docVO[i].state == '2'){
					docResult += '					<div class="d-inline card text-white text-center px-2 mr-1 bg-info">승인</div>';
											}else if(data.list.docVO[i].state == '3'){
					docResult += '					<div class="d-inline card text-white text-center px-2 mr-1 bg-dark">반려</div>';
											}else{
					docResult += '					<div class="d-inline card text-white text-center px-2 mr-1 bg-danger">오류</div>';
											}

					docResult += '			<a href="docView.do?docNo='+data.list.docVO[i].docNo+'">'+data.list.docVO[i].title+'</a>';
					docResult += '		</td>';
					docResult += '		<td>';
					docResult += '			<div class="text-dark">'+data.list.docVO[i].date+'</div>';
					docResult += '		</td>';
					docResult += '		<td>';
											if(data.list.docVO[i].mySignState == '0'){
					docResult += '					<div class="d-inline card bg-danger text-white text-center px-3 py-1">';
					docResult += '						<i class="fas fa-fw fa-file-signature"></i>결재 대기';
					docResult += '					</div>';
											}else if(data.list.docVO[i].mySignState == '2'){
					docResult += '					<div class="d-inline card bg-info text-white text-center px-3 py-1">승인 완료</div>';
											}else if(data.list.docVO[i].mySignState == '3'){
					docResult += '					<div class="d-inline card bg-dark text-white text-center px-4 py-1">반려</div>';
											}else{
					docResult += '					<div class="d-inline card bg-danger text-white text-center px-4 py-1">오류</div>';
											}
					docResult += '		</td>';
					docResult += '	</tr>';	
				}
				for(i in data.list.vacaVO){
					vacaResult += '	<tr>';
					vacaResult += '		<td>';
					vacaResult += '			<div class="d-inline card text-primary text-center px-1 mr-1 border-primary font-weight-bold">'+data.list.vacaVO[i].dept+'</div>';
					vacaResult += '			<span class="text-dark font-weight-bold">'+data.list.vacaVO[i].name+'</span> ';
					vacaResult += '			<span class="text-xs font-weight-bold">'+data.list.vacaVO[i].position+'</span>';
					vacaResult += '		</td>';
					vacaResult += '		<td>';
											if(data.list.vacaVO[i].state == '0'){
					vacaResult += '					<div class="d-inline card text-secondary text-center px-2 mr-1 border-secondary font-weight-bold">대기</div>';
											}else if(data.list.vacaVO[i].state == '1'){
					vacaResult += '					<div class="d-inline card text-danger text-center px-2 mr-1 border-danger font-weight-bold">진행</div>';
											}else if(data.list.vacaVO[i].state == '2' || data.list.vacaVO[i].state == '7'){
					vacaResult += '					<div class="d-inline card text-white text-center px-2 mr-1 bg-info">승인</div>';
											}else if(data.list.vacaVO[i].state == '3'){
					vacaResult += '					<div class="d-inline card text-white text-center px-2 mr-1 bg-dark">반려</div>';
											}else{
					vacaResult += '					<div class="d-inline card text-white text-center px-2 mr-1 bg-danger">오류</div>';
											}

					vacaResult += '			<a href="vacaView.do?vacaNo='+data.list.vacaVO[i].vacaNo+'">'+data.list.vacaVO[i].reason+'</a>';
					vacaResult += '		</td>';
					vacaResult += '		<td>';
					vacaResult += '			<div class="text-dark">'+data.list.vacaVO[i].rdate+'</div>';
					vacaResult += '		</td>';
					vacaResult += '		<td>';
											if(data.list.vacaVO[i].mySignState == '0'){
					vacaResult += '					<div class="d-inline card bg-danger text-white text-center px-3 py-1">';
					vacaResult += '						<i class="fas fa-fw fa-file-signature"></i>결재 대기';
					vacaResult += '					</div>';
											}else if(data.list.vacaVO[i].mySignState == '2'){
					vacaResult += '					<div class="d-inline card bg-info text-white text-center px-3 py-1">승인 완료</div>';
											}else if(data.list.vacaVO[i].mySignState == '3'){
					vacaResult += '					<div class="d-inline card bg-dark text-white text-center px-4 py-1">반려</div>';
											}else{
					vacaResult += '					<div class="d-inline card bg-danger text-white text-center px-4 py-1">오류</div>';
											}
					vacaResult += '		</td>';
					vacaResult += '	</tr>';	
				}
				for(i in data.list.overVO){
					overResult += '	<tr>';
					overResult += '		<td>';
					overResult += '			<div class="d-inline card text-primary text-center px-1 mr-1 border-primary font-weight-bold">'+data.list.overVO[i].dept+'</div>';
					overResult += '			<span class="text-dark font-weight-bold">'+data.list.overVO[i].name+'</span> ';
					overResult += '			<span class="text-xs font-weight-bold">'+data.list.overVO[i].position+'</span>';
					overResult += '		</td>';
					overResult += '		<td>';
											if(data.list.overVO[i].state == '0'){
					overResult += '					<div class="d-inline card text-secondary text-center px-2 mr-1 border-secondary font-weight-bold">대기</div>';
											}else if(data.list.overVO[i].state == '1'){
					overResult += '					<div class="d-inline card text-danger text-center px-2 mr-1 border-danger font-weight-bold">진행</div>';
											}else if(data.list.overVO[i].state == '2'){
					overResult += '					<div class="d-inline card text-white text-center px-2 mr-1 bg-info">승인</div>';
											}else if(data.list.overVO[i].state == '3'){
					overResult += '					<div class="d-inline card text-white text-center px-2 mr-1 bg-dark">반려</div>';
											}else{
					overResult += '					<div class="d-inline card text-white text-center px-2 mr-1 bg-danger">오류</div>';
											}

					overResult += '			<a href="overView.do?overTimeNo='+data.list.overVO[i].overTimeNo+'">'+data.list.overVO[i].content+'</a>';
					overResult += '		</td>';
					overResult += '		<td>';
					overResult += '			<div class="text-dark">'+data.list.overVO[i].rdate+'</div>';
					overResult += '		</td>';
					overResult += '		<td>';
											if(data.list.overVO[i].mySignState == '0'){
					overResult += '					<div class="d-inline card bg-danger text-white text-center px-3 py-1">';
					overResult += '						<i class="fas fa-fw fa-file-signature"></i>결재 대기';
					overResult += '					</div>';
											}else if(data.list.overVO[i].mySignState == '2'){
					overResult += '					<div class="d-inline card bg-info text-white text-center px-3 py-1">승인 완료</div>';
											}else if(data.list.overVO[i].mySignState == '3'){
					overResult += '					<div class="d-inline card bg-dark text-white text-center px-4 py-1">반려</div>';
											}else{
					overResult += '					<div class="d-inline card bg-danger text-white text-center px-4 py-1">오류</div>';
											}
					overResult += '		</td>';
					overResult += '	</tr>';	
				}
				$('#docTbody').html(docResult);
				$('#vacaTbody').html(vacaResult);
				$('#overTbody').html(overResult);
		}
	});
}