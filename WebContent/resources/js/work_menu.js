const menu1 = document.querySelector("#menu1");
const menu2 = document.querySelector("#menu2");
const menu3 = document.querySelector("#menu3");

const myWork = document.querySelector("#myWork");
const myOvertime = document.querySelector("#myOvertime");
const allWork = document.querySelector("#allWork");

window.addEventListener('load', function() {
	myWork.setAttribute("class","menubar text-decoration");
	const workBar = $("#workBar");
	let percent = myThisWeekTotalWorkTime.substring(0,2)/52*100+"%";
	workBar.attr("style",`width:${percent}`);
});

function displayFn(obj){
	if(obj==1){
		menu1.style.display = "block";
		menu2.style.display = "none";
		menu3.style.display = "none";
		myWork.setAttribute("class","menubar text-decoration");
		myOvertime.setAttribute("class","menubar");
		if(allWork!=null) allWork.setAttribute("class","menubar");
		
	}else if(obj==2){
		menu1.style.display = "none";
		menu2.style.display = "block";
		menu3.style.display = "none";
		myWork.setAttribute("class","menubar");
		myOvertime.setAttribute("class","menubar text-decoration");
		if(allWork!=null) allWork.setAttribute("class","menubar");
		
	}else if(obj==3){
		menu1.style.display = "none";
		menu2.style.display = "none";
		menu3.style.display = "block";
		myWork.setAttribute("class","menubar");
		myOvertime.setAttribute("class","menubar");
		if(allWork!=null) allWork.setAttribute("class","menubar text-decoration");
	}
}

function reloadListFn(obj, pNum){
	let startDate;
	let endDate;
	let searchVal;
	if(obj==1){
		startDate = $("#startDate1").val();
		endDate = $("#endDate1").val();
	}else if(obj==2){
		startDate = $("#startDate2").val();
		endDate = $("#endDate2").val();
	}else if(obj==3){
		startDate = $("#startDate3").val();
		endDate = $("#endDate3").val();
		searchVal = $("#searchVal").val();
	}
	
	$.ajax({
		url:"reloadList.do",
		data: {startDate : startDate, endDate : endDate, obj : obj, searchVal : searchVal, pNum : pNum},
		success:function(data){
			if(obj==1){
				let html = "";
				let outputBody = $(".outputBody1");
				for(let i=0; i<data.length; i++){
					html += "<tr>";
					html += "<td>"+data[i].date + " " + data[i].dayOfWeek+"</td>";
					html += "<td>"+data[i].start+"</td>";
					html += "<td>"+data[i].end+"</td>";
					html += "<td>";
					if(data[i].overtime.includes(',')){
						for(let j=0; j<2; j++){
							let ot = data[i].overtime.split(',')[j];
							let ots = data[i].overtime_state.split(',')[j];
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
						switch(data[i].overtime_state){
							case '(결재 대기)' : className = "text-gray"; break;
							case '(결재 진행중)' : className = "text-red"; break;
							case '(승인)' : className = "text-green"; break;
							case null : className = ""; break;
						}
						html += `<span class='${className}'>${data[i].overtime} `
						if(data[i].overtime_state!=null){
							html += `${data[i].overtime_state}`;
						}
						html += "</span>";
					}
					html += "</td>"
					
					html += "<td>"+data[i].total+"</td>";
					html += "</tr>";
				 
				}
				outputBody.html(html);
				
			}else if(obj==2){
				let html = "";
				let outputBody = $(".outputBody2");
				for(let i=0; i<data.length; i++){
					html += "<tr>";
					html += "<td>"+data[i].date + " " + data[i].dayOfWeek+"</td>";
					html += "<td>"+data[i].start+"</td>";
					html += "<td>"+data[i].end+"</td>";
					html += "<td>"+data[i].total+"</td>";
					html += "<td>"+data[i].content+"</td>";
					html += "<td>"
					if(data[i].state==0){
						html += '<span class="btn btn-light btn-gradient2 mini">대기</span>';
					}else if(data[i].state==1){
						html += '<span class="btn-gradient red mini">진행중</span>';
					}else if(data[i].state==2){
						html += '<span class="btn-gradient green mini">승인</span>';
					}else if(data[i].state==3){
						html += '<span class="btn-gradient mini btn-secondary">반려</span>';
					}else if(data[i].state==9){
						html += '<span class="btn-gradient mini btn-withdrawal">철회</span>';
					}
					html += "</td>"
					html += '<td><a href="overtime_view.do?no='+data[i].overtimeNo+'">';
					html += '<img class="icon-box" src="../resources/img/document_icon.png">';
					html += "</a></td>"
					html += "</tr>";
				}
				outputBody.html(html);
				
			}else if(obj==3){
				let html = "";
				let outputBody = $(".outputBody3");
				for(let i=0; i<data.list.length; i++){
					html += "<tr>";
					html += `<td>${data.list[i].date} ${data.list[i].dayOfWeek}</td>`;
					html += "<td>";
					html += "<span class='d-inline card bg-info text-white text-center px-2'>"+data.list[i].dept+"</span>&nbsp;";
					html += "<span>"+data.list[i].name+"</span>&nbsp;";
					html += "<span class='text-xs font-weight-bold text-primary text-uppercase mb-1'>"+data.list[i].position+"</span>";
					html += "</td>";
					html += `<td>${data.list[i].start}</td>`;
					html += "<td>"+data.list[i].end+"</td>";
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
		}
		
	});
}

function pagingFn(obj){
	let otherA = $(".page_nation").children(".active");
	otherA.attr("class","");
	let thisA = $(obj);
	thisA.attr("class","active");
}