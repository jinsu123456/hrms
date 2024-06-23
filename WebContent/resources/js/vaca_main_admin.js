function reloadListFn(pNum){
	let category_dept = $("select[name=category_dept]").val();
	let category_position = $("select[name=category_position]").val();
	let searchVal = $("#searchVal").val();
	
	$.ajax({
		url:"reloadList_admin.do",
		data: {searchVal : searchVal, category_dept : category_dept, category_position : category_position, pNum : pNum},
		success:function(data){
			let html = "";
			let outputBody = $(".outputBody");
			for(let i=0; i<data.list.length; i++){
				html += "";
				html += `<tr>`;
				html += `<td>${data.list[i].dept }</td>`;
				html += `<td>${data.list[i].position }</td>`;
				html += `<td>${data.list[i].name }</td>`;
				html += `<td>`;
				html += `<span class="text-bold">`;
				html += Math.round((data.list[i].keepVaca + data.list[i].useVaca) / 8) + '일';
				html += `</span>`;
				html += '<span class="text-sm text-blue"> ('+(data.list[i].keepVaca + data.list[i].useVaca)+'시간)</span>';
				html += `</td>`;
				html += `<td>`;
				html += `<span class="text-bold">${data.list[i].useVaca_day }일\ </span>`;
				html += `<span class="text-sm text-red">(${data.list[i].useVaca }시간)</span>`;
				html += `</td>`;
				html += `<td>`;
				html += `<span class="text-bold">${data.list[i].keepVaca_day }일\ </span>`;
				html += `<span class="text-sm text-green">(${data.list[i].keepVaca }시간)</span>`;
				html += `</td>`;
				html += `<td><a class="modal-open" href="#" onclick="viewFn('${data.list[i].userid}', '${data.list[i].name }')"><i class="far fa-id-card fa-lg"></i></a></td>`;
				html += `<td><a href="#" onclick="giveVacaFn('${data.list[i].userid}', '${data.list[i].dept }', '${data.list[i].position }', '${data.list[i].name }')"><i class="fas fa-fw fa-plane fa-lg"></i></a></td>`;
				html += `</tr>`;
			}
			outputBody.html(html);
			
			// 페이징
			html = "";
			outputBody = $(".page_nation");
			
			if(data.pagingVO.startPage > data.pagingVO.cntPage){
				html += `<a class="arrow prev" onclick="reloadListFn(${data.pagingVO.startPage-1})"></a>`
			}
			for(let i=data.pagingVO.startPage; i<=data.pagingVO.endPage; i++){
				if(i==data.pagingVO.nowPage){
					html += `<a class="active" onclick="reloadListFn(${i}); pagingFn(this)">${i}</a>`;
				}else{
					html += `<a onclick="reloadListFn(${i}); pagingFn(this)">${i}</a>`;
				}
			}
			if(data.pagingVO.endPage < data.pagingVO.lastPage){
				html += `<a onclick="reloadListFn(${data.pagingVO.endPage+1})"></a>`;
			}
			
			outputBody.html(html);
		}
		
	});
}

function viewFn(userid, name){
	$.ajax({
		url: "view_admin.do",
		type: "POST",
		data: {userid : userid},
		success: function(data){
			let headTitle = $(".head-title");
			headTitle.html(name+"님의 연차 내역");
			let bodyContentbox = $(".body-contentbox");
			let html = "";
			
			if(data.length === 0){
				html = "연차 내역이 없습니다.";
			}else{
				for(let i=0; i<data.length; i++){
					let state;
					let className;
					switch(data[i].state){
						case "2" : 
							state = "(승인)"; 
							className = "text-content-approved"; break;
						case "7" : 
							state = "(사용완료)"; 
							className = "text-content-use"; break;
					}
					html += `<div class="popup-content-box">`;
					html += `<span class="text-head3">`;
					html += `${data[i].startDate}\ <span class="text-md2">(${data[i].startTime})</span>\ ~\ `;
					html += `${data[i].endDate}\ <span class="text-md2">(${data[i].endTime})</span>`;
					html += `</span>`;
					html += `<span class="text-content2">&nbsp;&nbsp;${data[i].reason}</span>`;
					html += `<span class="${className}">&nbsp;${state}</span>`;
					html += `</div>`;
				}
			}
			bodyContentbox.html(html);
		}
	})
}

function pagingFn(obj){
	let otherA = $(".page_nation").children(".active");
	otherA.attr("class","");
	let thisA = $(obj);
	thisA.attr("class","active");
}

function giveVacaFn(userid, dept, position, name){
	let hour = prompt("연차 부여 시간 입력", "hhh or -hhh");
	if(hour==null) return false;
	
	let regex = /^-?[0-9]{1,3}$/;
	let test = regex.test(hour);
	if(test){
		let conf = confirm("사원번호: " + userid + ": " + dept + " " + position + " "  + name + "님에게 연차 " + hour + "시간을 부여하시겠습니까?");
		if(conf){
			$.ajax({
				url: "giveVaca_admin.do",
				type: "POST",
				data: {userid : userid, hour : hour},
				success: function(data){
					if(data>0){
						alert("처리 완료되었습니다.");
						location.reload(true);
					}else{
						alert("오류가 발생하였습니다.");
						location.reload(true);
					}
				},
				error: function(){
					alert("통신 오류");
				}
			})
		}
	}else{
		alert("시간 형식이 올바르지 않습니다.");
	}
}