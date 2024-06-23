const goToWorkClock = document.querySelector("#goToWorkClock");
const leaveWorkClock = document.querySelector("#leaveWorkClock");

function getClock() {
   const date = new Date();
   const hours = String(date.getHours()).padStart(2, "0");
   const min = String(date.getMinutes()).padStart(2, "0");
   const sec = String(date.getSeconds()).padStart(2, "0");
   if(start==""){
   	goToWorkClock.innerText = hours+":"+min+":"+sec;
   }else{
   	goToWorkClock.innerText = start;
	   if(end==""){
	   	leaveWorkClock.innerText = hours+":"+min+":"+sec;
	   }else{
		   leaveWorkClock.innerText = end;
	   }
   }
}

getClock();
let clockVar = setInterval(getClock, 1000);

function workFn(obj){
	const date = new Date();
	const year = date.getFullYear();
	const month = ('0' + (date.getMonth() + 1)).slice(-2);
	const day = ('0' + date.getDate()).slice(-2);
   const hours = String(date.getHours()).padStart(2, "0");
   const min = String(date.getMinutes()).padStart(2, "0");
   const sec = String(date.getSeconds()).padStart(2, "0");
   
	const dateStr = year+"-"+month+"-"+day;
	const timeStr = hours+":"+min+":"+sec;
	
	if(obj=="GO"){
		if(start==""){
			clearInterval(clockVar);
			let goOrLeave = "GO";
		   $.ajax({
				url: path+"/work/workInsert.do",
				type:"POST",
				data: {date : dateStr, time : timeStr, goOrLeave : goOrLeave},
				success:function(data){
					alert("출근 처리 되었습니다.");
					location.reload(true);
				}
			});
		   
		}else{
			alert("이미 출근 처리되었습니다.");
		}
		
	}else if(obj=="LEAVE"){
		if(start==""){
			alert("출근 처리가 완료되지 않았습니다.");
		}else{
			let goOrLeave = "LEAVE";
		   $.ajax({
				url: path+"/work/workInsert.do",
				type:"POST",
				data: {date : dateStr, time : timeStr, goOrLeave : goOrLeave},
				success:function(data){
					if(data=="SUCCESS"){
						alert("퇴근 처리 되었습니다.");
						location.reload(true);
					}else{
						let message = "금일 \n";
						for(let i=0; i<data.length; i++){
							if(data[i]!==null){
								message += data[i].start+"시 ~ "+data[i].end+"시 \n";
							}							
						}
						message += "까지 초과근무를 신청하셨습니다. \n지금 퇴근처리 하시겠습니까?";
						let choice = confirm(message);
						if(choice){
							$.ajax({
								url: path+"/work/updateOvertime.do",
								type:"POST",
								data: {date : dateStr, time : timeStr},
								success:function(data){
									alert("퇴근 처리 되었습니다.");
									location.reload(true);
								}
							})
						}
					}
				}
			});
		}
	}
}

function overtime_aplicationFn(){
	if(ovoAppArr !== ""){
		alert("오늘 이미 처리 진행중인 초과근무 건이 있습니다.");
	}else{
		location.href="overtime_application.do";
	}
}