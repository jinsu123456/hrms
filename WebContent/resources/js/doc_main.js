$('#startDate1').datepicker();
$('#startDate1').datepicker("option", "maxDate", $("#endDate1").val());
$('#startDate1').datepicker("option", "onClose", function ( selectedDate ) {
	$("#endDate1").datepicker("option", "minDate", selectedDate );
});
$('#endDate1').datepicker();
$('#endDate1').datepicker("option", "minDate", $("#startDate1").val());
$('#endDate1').datepicker("option", "onClose", function ( selectedDate ) {
	$("#startDate1").datepicker("option", "maxDate", selectedDate );
});

$('#startDate2').datepicker();
$('#startDate2').datepicker("option", "maxDate", $("#endDate2").val());
$('#startDate2').datepicker("option", "onClose", function ( selectedDate ) {
	$("#endDate2").datepicker("option", "minDate", selectedDate );
});
$('#endDate2').datepicker();
$('#endDate2').datepicker("option", "minDate", $("#startDate2").val());
$('#endDate2').datepicker("option", "onClose", function ( selectedDate ) {
	$("#startDate2").datepicker("option", "maxDate", selectedDate );
});

function reloadListFn(obj){
	let startDate;
	let endDate;
	let listType;
	let outputBody;
	let category_position = $("select[name=category] option:selected").val();
	let searchVal = $("input[name=searchVal]").val();
	if(obj==1){
		startDate = $("#startDate1").val();
		endDate = $("#endDate1").val();
		listType = "my";
		outputBody = $("#outputBody1");
	}else if(obj==2){
		startDate = $("#startDate2").val();
		endDate = $("#endDate2").val();
		listType = "dept";
		outputBody = $("#outputBody2");
	}
	
	$.ajax({
		url:"reloadList.do",
		data: {startDate : startDate, endDate : endDate, listType : listType, category_position : category_position, searchVal : searchVal},
		success:function(data){
			let html = "";
			if(obj==1){
				for(let i=0; i<data.length; i++){
					html += "<tr>";
					html += `<td>${data[i].rownum}</td>`;
					html += `<td><a href="javascript:viewFn(${data[i].docNo})">${data[i].title}</a></td>`;
					html += `<td>${data[i].date}</td>`;
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
					html += "</tr>";
				}
			}else if(obj==2){
				for(let i=0; i<data.length; i++){
					html += "<tr>";
					html += `<td>${data[i].rownum }</td>`;
					html += `<td><a href="javascript:viewFn(${data[i].docNo})">${data[i].title}</a></td>`;
					html += "<td>";
					html += `<span class="d-inline card bg-info text-white text-center px-2">${data[i].dept }</span>`
					html += `<span>\ ${data[i].name }\ </span>`;
					html += `<span class="text-xs font-weight-bold text-primary text-uppercase mb-1">${data[i].position }</span>`
					html += "</td>";
					html += `<td>${data[i].date }</td>`;
					html += "</tr>";
				}
			}
			
			outputBody.html(html);
		}
	})
}

function viewFn(obj){
	let f = document.createElement('form');
	let i = document.createElement('input');
	i.setAttribute('type', 'hidden');
	i.setAttribute('name', 'docNo');
	i.setAttribute('value', obj);
	f.appendChild(i);
	f.setAttribute('method', 'get');
	f.setAttribute('action', 'view.do');
	document.body.appendChild(f);
	f.submit();
}