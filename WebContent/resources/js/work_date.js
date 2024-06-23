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
			maxDate: 0,
			onClose: function(selectedDate){
				selDate_end1 = selectedDate;
			}
		});
	}
});
$("#endDate1").datepicker({
	dateFormat: 'yy-mm-dd',
	minDate: selDate_start1,
	maxDate: 0,
	onClose: function(selectedDate){
		selDate_end1 = selectedDate;
	}
});


let selDate_start2;
let selDate_end2 = $("#endDate2").val();
$("#startDate2").datepicker({
	dateFormat: 'yy-mm-dd',
	maxDate: new Date(selDate_end2),
	onClose: function(selectedDate) {
		let minDDate = new Date(selectedDate);
		selDate_start2 = minDDate;
		let endDateDate = new Date($("#endDate2").val());
		let year = minDDate.getFullYear().toString();
		let month = ('0' + (minDDate.getMonth() + 1)).slice(-2).toString();
		let day = ('0' + minDDate.getDate()).slice(-2).toString();
		let minDString = year + "-" + month + "-" + day;
		$('#endDate2').datepicker('destroy'); 
		if(minDDate>endDateDate){
			$("#endDate2").val(minDString);
		}
		$("#endDate2").datepicker({
			dateFormat: 'yy-mm-dd',
			minDate: minDDate,
			maxDate: 0,
			onClose: function(selectedDate){
				selDate_end2 = selectedDate;
			}
		});
	}
});
$("#endDate2").datepicker({
	dateFormat: 'yy-mm-dd',
	minDate: selDate_start2,
	maxDate: 0,
	onClose: function(selectedDate){
		selDate_end2 = selectedDate;
	}
});


let selDate_start3;
let selDate_end3 = $("#endDate3").val();
$("#startDate3").datepicker({
	dateFormat: 'yy-mm-dd',
	maxDate: new Date(selDate_end3),
	onClose: function(selectedDate) {
		let minDDate = new Date(selectedDate);
		selDate_start3 = minDDate;
		let endDateDate = new Date($("#endDate3").val());
		let year = minDDate.getFullYear().toString();
		let month = ('0' + (minDDate.getMonth() + 1)).slice(-2).toString();
		let day = ('0' + minDDate.getDate()).slice(-2).toString();
		let minDString = year + "-" + month + "-" + day;
		$('#endDate3').datepicker('destroy'); 
		if(minDDate>endDateDate){
			$("#endDate3").val(minDString);
		}
		$("#endDate3").datepicker({
			dateFormat: 'yy-mm-dd',
			minDate: minDDate,
			maxDate: 0,
			onClose: function(selectedDate){
				selDate_end1 = selectedDate;
			}
		});
	}
});
$("#endDate3").datepicker({
	dateFormat: 'yy-mm-dd',
	minDate: selDate_start3,
	maxDate: 0,
	onClose: function(selectedDate){
		selDate_end3 = selectedDate;
	}
});