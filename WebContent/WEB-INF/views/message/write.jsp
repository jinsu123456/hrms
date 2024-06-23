<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/navigator.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link href="${pageContext.request.contextPath}/resources/css/message_write.css?after" rel="stylesheet" type="text/css">
</head>
<body id="page-top">
<form action="write.do" method="post" name="frm">
	<!-- Begin Page Content -->
	<div class="container-fluid">
		<!-- Page Heading -->
		<div class="d-sm-flex align-items-center justify-content-between mb-4">
			<h1 class="h3 mb-0 text-gray-800">쪽지</h1>
		</div>

		<div class="card o-hidden border-0 shadow-lg">
			<div class="card-body p-0">
				<!-- Nested Row within Card Body -->
				<div class="row">
					<div class="col p-5 mx-5">
						<div class="my-4">
							<h1 class="d-inline h4 text-gray-900 font-weight-bold">쪽지 쓰기</h1>
						</div>
						<hr>
						<div class="row text-center pt-5">
							<div
								class="d-inline col-3 text-dark font-weight-bold text-right align-self-center">
								받는 사람<br></div>
							<div
								class="d-inline col-6 text-dark font-weight-bold text-left">
								<div class="row p-0 m-0">
									<div class="p-2 mx-1 mb-1 d-inline" id="plusUser">
										<a href="#" onclick="receiverPlusFn()" data-toggle="modal" data-target="#receiverModal"><i
										class="fas fa-user-plus"></i></a>
									</div>
								</div>
							</div>
							<div class="col-auto align-self-center">
								
							</div>
						</div>
						<div class="row text-center pt-5">
							<div class="d-inline col-3 text-dark font-weight-bold text-right">
								쪽지 내용</div>
							<div class="d-inline col-6 text-dark font-weight-bold text-left">
								<textarea name="content" class="msgContent" placeholder="내용을 입력해 주세요."></textarea>
							</div>
						</div>

					</div>
				</div>
				<div class="row">
					<div class="col p-5 mt-5 mx-5">
						<div class="mb-4 text-center">
							<hr>
							<a href="main.do" class="btn btn-secondary btn-user"> 돌아가기 </a> 
							<button type="button" onclick="sumitFn()" class="btn btn-primary btn-user"> 작성완료 </button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="receiverModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white">
                    
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true" class="text-white">x</span>
                    </button>
                </div>
                <div class="modal-body">
                	<div class="row m-2">
                		<h5 class="modal-title text-gray-900 font-weight-bold" id="exampleModalLabel">사원 목록</h5>
                	</div>
                	<div class="row m-2">
                		<div class="col-12 card border-secondary p-3 text-dark font-weight-bold">
                			<div class="row">
                				<div class="col-6 border-right border-secondary" id="receiverDept">
                				</div>
                				<div class="col-6 deptEmpl" id="checkBoxList">
                					<div class="font-weight-bold px-2 py-1 m-1" id="checkAll">
                						<input type="checkbox"> <span class="text-gray-900">전체 선택</span>
                					</div>
                					<hr class="m-2">
                				</div>
                			</div>
                		</div>
                	</div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">닫기</button>
                    <a class="btn btn-primary" href="#" onclick="recPlusFn()" data-dismiss="modal">추가</a>
                </div>
            </div>
        </div>
    </div>
</form>
<script>
var flag = false;
var dept = "All";
function receiverPlusFn(){
	if(flag == false){
		$.ajax({
			url:"receiverPlus.do",
			success:function(data){
				flag = true;
				let deptList = '';
				let deptAllCount = 0;
				let deptAllList = '';
				let labelCount = 0;
				for(let i=0; i<data.deptCount.length; i++){
					deptAllCount += data.deptCount[i].count;
					deptList += '<div class="row m-1 dept" onclick="deptClickFn(this)" id="dept'+data.deptCount[i].dept+'">';
					deptList += '	<div class="font-weight-bold p-2 m-1 col">';
					deptList += '		'+data.deptCount[i].deptCase;
					deptList += '	</div>';
					deptList += '	<span class="text-primary font-weight-bold d-inline p-2 m-1 col-auto">'+data.deptCount[i].count+'</span>';
					deptList += '</div>';
					deptList += '<hr class="m-2">';
					
					let receiverList = '';
					receiverList += '<div class="font-weight-bold px-2 py-1 m-1 d-none" id="receiverCheckBox'+data.deptCount[i].dept+'">';
					for(let j=0; j<data.receiverList.length; j++){
						if(data.receiverList[j].userid == ${userId}){
							continue;
						}
						if(data.deptCount[i].dept == data.receiverList[j].dept){
							receiverList += '<input type="checkbox" name="receiver" id="checkBox'+labelCount+'" value="'+data.receiverList[j].userid+'"> <label for="checkBox'+labelCount+'">';
							receiverList += '<span class="text-gray-900">'+data.receiverList[j].name+'</span> <span class="text-xs font-weight-bold">'+data.receiverList[j].position+'</span></label><br>';
							labelCount++;
						}
					}
					receiverList += '</div>';
					$("#checkBoxList").html($("#checkBoxList").html()+receiverList);
				}
				deptAllList += '<div class="row m-1 dept deptSelected" onclick="deptClickFn(this)" id="deptAll">';
				deptAllList += '	<div class="font-weight-bold p-2 m-1 col">';
				deptAllList += '		전체';
				deptAllList += '	</div>';
				deptAllList += '	<span class="text-primary font-weight-bold d-inline p-2 m-1 col-auto">'+deptAllCount+'</span>';
				deptAllList += '</div>';
				deptAllList += '<hr class="m-2">';
				$("#receiverDept").html(deptAllList+deptList);
				
				let receiverList = '';
				receiverList += '<div class="font-weight-bold px-2 py-1 m-1" id="receiverCheckBoxAll">';
				for(let j=0; j<data.receiverList.length; j++){
						if(data.receiverList[j].userid == ${userId}){
							$('#dept'+'${dept}').children('span').html($('#dept'+'${dept}').children('span').html()-1);
							$('#deptAll').children('span').html($('#deptAll').children('span').html()-1);
							continue;
						}
						receiverList += '<input type="checkbox" id="checkBox'+labelCount+'" value="'+data.receiverList[j].userid+'"> <label for="checkBox'+labelCount+'">';
						receiverList += '<span class="text-gray-900">'+data.receiverList[j].name+'</span> '
						receiverList += '<span class="text-xs font-weight-bold">'+data.receiverList[j].position+'</span> <div class="d-inline card text-primary text-center px-1 border-primary font-weight-bold text-xs">'+data.receiverList[j].deptCase+'</div></label><br>';
						labelCount++;
				}
				receiverList += '</div>';
				$("#checkBoxList").html($("#checkBoxList").html()+receiverList);
				
				checkAllFn();
			}
		});
	};
}

function deptClickFn(obj){
	let deptTemp = obj.id;
	dept = deptTemp.replace('dept', '');
	$('[id^=receiverCheckBox]').attr('class', 'font-weight-bold px-2 py-1 m-1 d-none');
	$('#receiverCheckBox'+dept).attr('class', 'font-weight-bold px-2 py-1 m-1');
	$('[id^=dept]').attr('class', 'row m-1 dept');
	$('#dept'+dept).attr('class', 'row m-1 dept deptSelected');
	checkAllFn();
}

function checkAllFn(){
	var all = $("#checkAll");
	$(":checkbox", all).prop('checked', false);
	$('[id^=checkBox]').not(':disabled').prop('checked', false);

	var rec = $('#receiverCheckBox'+dept);
	var disabled = $(":checkbox", rec).not(':disabled').length;

	if(disabled == 0){
		$(":checkbox", all).prop('checked', true);
		$(":checkbox", all).prop('disabled', true);
	}else{
		$(":checkbox", all).prop('disabled', false);
	}
	
	$(":checkbox", all).off('click').click(function() {
		if($(":checkbox", all).is(":checked")){
			$(":checkbox", rec).prop("checked", true);
		}else{
			$(":checkbox", rec).not(':disabled').prop("checked", false);
		}
	});
	
	$(":checkbox", rec).off('click').click(function() {
		var total = $(":checkbox", rec).length;
		var checked = $(":checkbox:checked", rec).length;
		if(total != checked){
			$(":checkbox", all).prop("checked", false);
		}else{
			$(":checkbox", all).prop("checked", true);
		}
	});
}

function recPlusFn(){
	$.ajax({
		url:"receiverPlus.do",
		success:function(data){
			let checked = $('input[type=checkbox][id^=checkBox]:checked').not(':disabled');
			let plusUser = '';
			for(let i=0; i<checked.length; i++){
				for(let j=0; j<data.receiverList.length; j++){
					if(checked[i].value == data.receiverList[j].userid){
						plusUser += '<div class="receiver p-2 mx-1 mb-1 d-inline">';
						plusUser += '<div class="d-inline card text-primary text-center px-1 border-primary font-weight-bold">'+data.receiverList[j].deptCase+'</div> ';
						plusUser += '<span class="text-gray-900">'+data.receiverList[j].name+'</span> ';
						plusUser += '<span class="text-xs font-weight-bold">'+data.receiverList[j].position+'</span> ';
						plusUser += '<a href="#" onclick="receiverDelFn(this, '+data.receiverList[j].userid+')"><i class="fas fa-times"></i></a>';
						plusUser += '</div>';
					}
				}
				
				let check = $('input[type=checkbox][id^=checkBox][value='+checked[i].value+']');
				check.prop('disabled', true);
				check.prop('checked', true);
			}
			$('#plusUser').before(plusUser);
			
			var all = $("#checkAll");
			if($(":checkbox", all).is(':checked')){
				$(":checkbox", all).prop('disabled', true);
			}
		}
	});
}

function receiverDelFn(obj, userid){
	$(obj).parent().remove();
	
	let checked = $('input[type=checkbox][id^=checkBox][value='+userid+']');
	checked.prop('disabled', false);
	checked.prop('checked', false);
	
	var all = $("#checkAll");
	var rec = $('#receiverCheckBox'+dept);
	var disabled = $(":checkbox", rec).not(':disabled').length;

	if(disabled == 0){
		$(":checkbox", all).prop('checked', true);
		$(":checkbox", all).prop('disabled', true);
	}else{
		$(":checkbox", all).prop('checked', false);
		$(":checkbox", all).prop('disabled', false);
	}
}

function sumitFn(){
	let rec = $('#receiverCheckBox'+dept);
	let checkCount = $(":checkbox:checked", rec).length;
	let content = $('textarea[name=content]').val();
	if(checkCount == 0){
		alert("쪽지를 수신할 사원을 선택해 주세요.");
	}else if(content == ""){
		alert("쪽지 내용을 입력해 주세요.");
	}else{
		let submitChecked = $('input[type=checkbox][id^=checkBox]:disabled');
		submitChecked.prop('disabled', false);
		
		let socketChecked = $('input[type=checkbox][id^=checkBox][name=receiver]:checked');
		let socketMsg = "sendMessage,";
		for(let i=0; i<socketChecked.length; i++){
			socketMsg += socketChecked[i].value+",";
		}
		socketMsg += "${login.userid}";
		if(socket){
			socket.send(socketMsg);
		}
		$('[name=frm]').submit();
	}
}

$(document).ready(function(){
	let sendUserId = `${sendUserId}`;
	if(!sendUserId == ""){
		receiverPlusFn();
		setTimeout(() => {
			let rec = $('#receiverCheckBoxAll');
			let reply = $('input[type=checkbox][id^=checkBox][value='+sendUserId+']', rec);
			reply.prop('checked', true);
			recPlusFn();
		}, 500);
	}
})
</script>

<%@ include file="../include/footer.jsp"%>