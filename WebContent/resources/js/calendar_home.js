<!-- 날짜 계산 -->
function isDateBetweenStartToEnd(startDate, endDate, paramDate) {
	var regex = RegExp(/^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/);
	if(!(regex.test(startDate) && regex.test(endDate))) return "Not Date Format";
	
	var curDate = new Date(paramDate);
	var text;
	if(curDate>=new Date(startDate)){
		if(curDate<=new Date(endDate)){
			return true;
		}
	}
	return false;
}

function getDayOfWeek(yyyyMMdd){
    const dayOfWeek = new Date(yyyyMMdd).getDay(); 
    
    //0:일, 1:월, 2:화, 3:수, 4:목, 5:금, 6:토
    return dayOfWeek;
}

<!-- 달력 스크립트 -->
let date = new Date();

const renderCalender = () => {
	const viewYear = date.getFullYear();
	const viewMonth = date.getMonth();
	
	document.querySelector('.year-month').textContent = viewYear + "년 " + (viewMonth + 1) + "월";

	const prevLast = new Date(viewYear, viewMonth, 0);
	const thisLast = new Date(viewYear, viewMonth + 1, 0);

	const PLDate = prevLast.getDate();
	const PLDay = prevLast.getDay();

	const TLDate = thisLast.getDate();
	const TLDay = thisLast.getDay();

	const prevDates = [];
	const thisDates = [...Array(TLDate + 1).keys()].slice(1);
	const nextDates = [];

	if (PLDay !== 6) {
		for (let i = 0; i < PLDay + 1; i++) {
			prevDates.unshift(PLDate - i);
		}
	}

	for (let i = 1; i < 7 - TLDay; i++) {
		nextDates.push(i);
	}

	const dates = prevDates.concat(thisDates, nextDates);
	const firstDateIndex = dates.indexOf(1);
	const lastDateIndex = dates.lastIndexOf(TLDate);

	var listArr = list.split("VacaVO");
	listArr.splice(0,1);
	
	dates.forEach((date, i) => {
		
		const condition = i >= firstDateIndex && i < lastDateIndex + 1 ? 'this' : 'other';
		
		var formatDate = viewYear+"-";
		if(condition==='this'){
			formatDate += (("00"+(viewMonth+1).toString()).slice(-2))+"-";
		}else{
			if(i < lastDateIndex + 1){
				formatDate += (("00"+(viewMonth).toString()).slice(-2))+"-";
			}else{
				formatDate += (("00"+(viewMonth+2).toString()).slice(-2))+"-";
			}
		}
		formatDate += (("00"+date.toString()).slice(-2));
		
		// 출력할 연차자 담을 배열
		var view = [];
		// formatDate 가 listArr 돌면서 포함되는 날 있는지 찾고 배열에 담는다
		for(let j=0; j<listArr.length; j++){
			var startDate = listArr[j].match(/[0-9]{4}-[0-9]{2}-[0-9]{2}/g)[1];
			var endDate = listArr[j].match(/[0-9]{4}-[0-9]{2}-[0-9]{2}/g)[2];
			
			var dept = listArr[j].match(/dept=.*/)[0].split(',')[0].substring(5);
			var name = listArr[j].match(/name=.*/)[0].split(',')[0].substring(5);
			var position = listArr[j].match(/position=.*/)[0].split(',')[0].substring(9);
			
			var obj = {dept, name, position, formatDate};
			
			var flag = isDateBetweenStartToEnd(startDate, endDate, formatDate);
			if(flag==true){
				view.push(obj);
			}
		}
		
		let viewJSON = JSON.stringify(view);
		
		let dayOfWeek = getDayOfWeek(formatDate);

		dates[i] = '<div class="date">'
					+ '<span class="' + condition + '">' + date + '</span>';
		if(view.length>0 && (dayOfWeek!=0 && dayOfWeek !=6)) {
			dates[i] += "<div onclick='clickFn("+viewJSON+", this)' class='card bg-info text-white text-center shadow cursor-pointer'>"
						+ '연차 ' + view.length + '건' + '</div>';
		}
		dates[i]	+= '</div>';

	});

	document.querySelector('.dates').innerHTML = dates.join('');

	const today = new Date();
	if (viewMonth === today.getMonth() && viewYear === today.getFullYear()) {
		for (let date of document.querySelectorAll('.this')) {
			if (+date.innerText === today.getDate()) {
				date.classList.add('today');
				let sibling = date.nextElementSibling;
				if(sibling==null){
					date.setAttribute("style","cursor:pointer");
					date.setAttribute("onclick","todayVacaFn()");
				}
				break;
			}
		}
	}
};

renderCalender();

const prevMonth = () => {
	date.setMonth(date.getMonth() - 1);
	renderCalender();
};

const nextMonth = () => {
	date.setMonth(date.getMonth() + 1);
	renderCalender();
};

const goToday = () => {
	date = new Date();
	renderCalender();
};


<!-- 달력 클릭 함수 (연차자 표시) -->
function clickFn(obj, t){
	const vacaDiv = $("#vaca-list");
	let html = "";
	html += `<div id="vaca-date" class="h4 mb-3 ml-2 font-weight-bold text-gray-800 today-date">${obj[0].formatDate}</div>`;
	for(let i=0; i<obj.length; i++){
		html += `<div class="card mt-3 bg-light text-black">`;
		html += `<div class="card-body">`;
		html += `<div id="vaca-dept" class="d-inline card bg-info text-white text-center px-2">${obj[i].dept}</div>`;
		html += `<span id="vaca-name">\ ${obj[i].name}\ </span>`;
		html += `<span class="text-xs font-weight-bold text-primary text-uppercase mb-1" id="vaca-position">${obj[i].position}</span>`;
		//html += `<div id="vaca-state" class="d-inline ml-5">${obj[i].state}</div>`;
		html += `</div>`;
		html += `</div>`;
	}
	vacaDiv.html(html);
	
	const sAll = $(".calendar.col").find("span");
	sAll.removeClass("selectDate");
	const s = $(t).prev();
	s.addClass("selectDate");
}

function todayVacaFn(){
	const vacaDiv = $("#vaca-list");
	const list = `${todayList}`;
	if(list=="[]"){
		let html = `<div id="vaca-date" class="h4 mb-3 ml-2 font-weight-bold text-gray-800 today-date">${today}</div>`;
		html += `<div class="h4 mb-3 ml-2 font-weight-bold text-lightgray">연차자 없음 <i class="fas fa-user-alt-slash"></i></div>`;
		vacaDiv.html(html);
	}
}