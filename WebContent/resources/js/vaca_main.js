function reloadListFn(obj){
	let startDate = $("#startDate1").val() === "" ? null : $("#startDate1").val();
	let endDate = $("#endDate1").val() === "" ? null : $("#endDate1").val();
	$.ajax({
		url:"reloadList.do",
		data: {startDate : startDate, endDate : endDate},
		success:function(data){
			let html = "";
			let outputBody = $(".outputBody");
			for(let i=0; i<data.length; i++){
				html += "<tr>";
				html += `<td>${data[i].startDate} ${data[i].startDate_dayOfWeek} ~ ${data[i].endDate} ${data[i].endDate_dayOfWeek}</td>`;
				html += `<td>${data[i].duration}일</td>`;
				html += `<td>${data[i].startDate} ${data[i].startDate_dayOfWeek}<br>${data[i].startTime }</td>`;
				html += `<td>${data[i].endDate} ${data[i].endDate_dayOfWeek}<br>${data[i].endTime }</td>`;
				html += `<td>${data[i].useTime }</td>`;
				html += `<td>${data[i].reason }</td>`;
				html += `<td>${data[i].rdate}</td>`
				html += "<td>";
				switch(data[i].state){
					case '대기':
						html += `<span class="btn btn-light btn-gradient2 mini">대기</span>`;
						break;
					case '진행중':
						html += `<span class="btn-gradient red mini">진행중</span>`;
						break;
					case '승인':
						html += `<span class="btn-gradient green mini">승인</span>`;
						break;
					case '반려':
						html += `<span class="btn-gradient mini btn-secondary">반려</span>`;
						break;
					case '사용 완료':
						html += `<span class="btn-gradient green mini">사용 완료</span>`;
						break;
					case '철회':
						html += `<span class="btn-gradient mini btn-secondary">철회</span>`;
						break;
				}
				html += "</td>";
				html += `<td>`;
				html += `<a href="view.do?no=${data[i].vacaNo }"><i class="far fa-calendar-alt fa-lg"></i></a>`
				html += `</td>`;
				html += "</tr>";
			}
				
			outputBody.html(html);
		}
	});
}

let selDate_start1;
let selDate_end1 = $("#endDate1").val();
$("#startDate1").datepicker({
	dateFormat: 'yy-mm-dd',
	maxDate: new Date(selDate_end1),
	onClose: function(selectedDate) {
		let minDDate = new Date(selectedDate);
		selDate_start1 = minDDate;
		let endDateDate = new Date($("#endDate1").val());
		let year = minDDate.getFullYear().toString();
		let month = ('0' + (minDDate.getMonth() + 1)).slice(-2).toString();
		let day = ('0' + minDDate.getDate()).slice(-2).toString();
		let minDString = year + "-" + month + "-" + day;
		$('#endDate1').datepicker('destroy'); 
		if(minDDate>endDateDate){
			$("#endDate1").val(minDString);
		}
		$("#endDate1").datepicker({
			dateFormat: 'yy-mm-dd',
			minDate: minDDate,
			onClose: function(selectedDate){
				selDate_end1 = selectedDate;
			}
		});
	}
});
$("#endDate1").datepicker({
	dateFormat: 'yy-mm-dd',
	minDate: selDate_start1,
	onClose: function(selectedDate){
		selDate_end1 = selectedDate;
	}
});
