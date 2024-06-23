function reloadListFn(obj, pNum){
	let startDate = $("#startDate1").val();
	let endDate = $("#endDate1").val();
	let category_dept = $("select[name=category_dept]").val();
	let category_position = $("select[name=category_position]").val();
	let searchVal = $("#searchVal").val();
	
	$.ajax({
		url: path+"/work/reloadList.do",
		data: {startDate : startDate, endDate : endDate, obj : obj, searchVal : searchVal, category_dept : category_dept, category_position : category_position, pNum : pNum},
		success:function(data){
			let html = "";
			let outputBody = $(".outputBody");
			for(let i=0; i<data.list.length; i++){
				html += "<tr>";
				html += `<td>${data.list[i].date} ${data.list[i].dayOfWeek}</td>`;
				html += "<td>";
				html += "<span class='d-inline card bg-info text-white text-center px-2'>"+data.list[i].dept+"</span>&nbsp;";
				html += "<span>"+data.list[i].name+"</span>&nbsp;";
				html += "<span class='text-xs font-weight-bold text-primary text-uppercase mb-1'>"+data.list[i].position+"</span>";
				html += "</td>";
				html += `<td>${data.list[i].start}</td>`;
				if(data.list[i].end == "-"){
					html += `<td><a href="#"><i class="fas fa-clock fa-lg" onclick="leaveWork_admin('${data.list[i].wNo}')"></i></a></td>`;
				}else{
					html += `<td>${data.list[i].end}</td>`
				}
				html += "<td>";
				if(data.list[i].overtime.includes(',')){
					for(let j=0; j<2; j++){
						let ot = data.list[i].overtime.split(',')[j];
						let ots = data.list[i].overtime_state.split(',')[j];
						let className = "";
						switch(ots){
							case '(결재 대기)' : className = "text-gray"; break;
							case '(결재 진행중)' : className = "text-red"; break;
							case '(승인)' : className = "text-green"; break;
							case null : className = ""; break;
						}
						if(j==1){html += "<br>";}
						html += "<span class='"+className+"'>"+ot+" "+ots+"</span>";
					}
				}else{
					switch(data.list[i].overtime_state){
						case '(결재 대기)' : className = "text-gray"; break;
						case '(결재 진행중)' : className = "text-red"; break;
						case '(승인)' : className = "text-green"; break;
						case null : className = ""; break;
					}
					html += `<span class='${className}'>${data.list[i].overtime} `
					if(data.list[i].overtime_state!=null){
						html += `${data.list[i].overtime_state}`;
					}
					html += "</span>";
				}
				html += "</td>"
				html += "<td>"+data.list[i].total+"</td>";
			}
			outputBody.html(html);
			
			// 페이징
			html = "";
			outputBody = $(".page_nation");
			
			if(data.pagingVO.startPage > data.pagingVO.cntPage){
				html += `<a class="arrow prev" onclick="reloadListFn(3,${data.pagingVO.startPage-1})"></a>`
			}
			for(let i=data.pagingVO.startPage; i<=data.pagingVO.endPage; i++){
				if(i==data.pagingVO.nowPage){
					html += `<a class="active" onclick="reloadListFn(3,${i}); pagingFn(this)">${i}</a>`;
				}else{
					html += `<a onclick="reloadListFn(3,${i}); pagingFn(this)">${i}</a>`;
				}
			}
			if(data.pagingVO.endPage < data.pagingVO.lastPage){
				html += `<a onclick="reloadListFn(${data.pagingVO.endPage+1})"></a>`;
			}
			
			outputBody.html(html);
			
		}
		
	});
}

function pagingFn(obj){
	let otherA = $(".page_nation").children(".active");
	otherA.attr("class","");
	let thisA = $(obj);
	thisA.attr("class","active");
}

<!-- 관리자용 퇴근시키기 버튼 -->
function leaveWork_admin(wNo){
	let end = prompt("퇴근시간 입력","18:00:00");
	if(end==null) return false;
	
	let regex = /^([1-9]|[01][0-9]|2[0-3])\:[0-5]{1}[0-9]{1}\:[0-5]{1}[0-9]{1}$/;
	let test = regex.test(end);
	if(test){
		let conf = confirm("근무번호 "+wNo+"의 퇴근시간을 "+end+"로 입력하시겠습니까?");
		if(conf){
			$.ajax({
				url: "workInsert_admin.do",
				type: "POST",
				data: {wNo : wNo, end : end},
				success: function(data){
					console.log(typeof data);
					console.log(data);
					if(data>0){
						alert("퇴근 처리 되었습니다.");
						location.reload(true);
					}else{
						alert("오류가 발생하였습니다.");
						location.reload(true);
					}
				}
				
			})
		}
	}else{
		alert("시간 형식이 올바르지 않습니다.");
	}
}