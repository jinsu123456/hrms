<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../include/navigator.jsp"%>
<!DOCTYPE html>
<head>
<link href="${pageContext.request.contextPath}/resources/css/user.css" rel="stylesheet" type="text/css">
<style>
.divTitle{
	justify-content: space-between !important;
    align-items: center;
}
</style>
</head>
<body>
<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Page Heading -->
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">사원</h1>
	</div>

	<div class="card o-hidden border-0 shadow-lg">
		<div class="card-body p-0">
			<!-- Nested Row within Card Body -->
			<div class="row">
				<div class="col-12">
					<div class="px-5 pt-5">
						<div class="my-4">
							<h1 class="d-inline h4 text-gray-900 font-weight-bold">
							내 정보 수정
							</h1>
						</div>
						<hr>
					</div>
					<div class="row">
						<div class="col-lg-3 d-none d-lg-block"></div>
						<div class="col-lg-6">
							<div class="p-1 text-left">
								<form action="modify.do" name="frm" method="post" onsubmit="return validation()">
									<div class="row m-3">
										<div class="col-3 d-flex divTitle">
										<span class="text-dark font-weight-bold">사원번호</span>
										</div>
										<input type="text" class="inp2" value="${loginUser.userid}" disabled>
									</div>
									<div class="row m-3">
										<div class="col-3 d-flex divTitle">
										<span class="text-dark font-weight-bold">현재 비밀번호</span>
										</div>
										<input type="password" class="inp2" name="password" oninput="checkPass(this)" onfocus="checkPass(this)">
									</div>
									<div class="row m-3">
										<div class="col-3 d-flex divTitle">
										<span class="text-dark font-weight-bold">새 비밀번호</span>
										</div>
										<input type="password" class="inp2" name="newPassword" oninput="checkNewPass(this)" onfocus="checkNewPass(this)">
									</div>
									<div class="row m-3">
										<div class="col-3 d-flex divTitle">
										<span class="text-dark font-weight-bold">새 비밀번호 확인</span>
										</div>
										<input type="password" class="inp2" name="newPasswordChk" oninput="checkPassRe(this)" onfocus="checkPassRe(this)">
									</div>
									<div class="row m-3">
										<div class="col-3 d-flex divTitle">
										<span class="text-dark font-weight-bold">연락처</span>
										</div>
										<input type="text" class="inp2" value="${loginUser.phoneNumber}" name="phone" oninput="checkPhone(this)" onfocus="checkPhone(this)">
									</div>
									<div class="row m-3">
										<div class="col-3 d-flex divTitle">
										<span class="text-dark font-weight-bold">이메일</span>
										</div>
										<input type="text" class="inp2" value="${loginUser.email}" name="email" oninput="checkEmail(this)" onfocus="checkEmail(this)">
									</div>
									<div class="row m-3">
										<div class="col-3 d-flex divTitle">
										<span class="text-dark font-weight-bold">주소</span>
										</div>
										<input type="text" class="inp2" value="${loginUser.addr}" name="addr">
									</div>
									<div class="row m-3" id="chkDiv">
									</div>
									<div class="row m-5">
										<div class="col-12 text-center">
											<a href="main.do" class="btn btn-secondary btn-user">뒤로가기</a> 
											<button type="submit" class="btn btn-primary btn-user">수정완료</button> 
										</div>
									</div>
								</form>
							</div>
						</div>
						<div class="col-lg-3 d-none d-lg-block"></div>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	function checkPass(obj){
		let chkDiv = $('#chkDiv');
		if(obj.value == ""){
			let html = '<div class="col-12 text-center text-danger border-danger font-weight-bold inp2">필수입력입니다.</div>';
			$(chkDiv).html(html);
			let div = $(obj).siblings('div');
			$(div).children('i').remove();
			$(div).html($(div).html()+'<i class="fas fa-exclamation fa-fw" style="color:red"></i>');
			return false;
		}else{
			let html = '<div class="col-12 text-center text-warning border-warning font-weight-bold inp2">수정완료 버튼을 통해서 확인할 수 있습니다.</div>';
			$(chkDiv).html(html);
			let div = $(obj).siblings('div');
			$(div).children('i').remove();
			$(div).html($(div).html()+'<i class="fas fa-check fa-fw" style="color:#f6c23e"></i>');
			return true;
		}
	}
	
	function checkNewPass(obj){
		let regId = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/;
		let regRs = regId.test(obj.value);
		let chkDiv = $('#chkDiv');
		if(obj.value == ""){
			let html = '<div class="col-12 text-center text-danger border-danger font-weight-bold inp2">필수입력입니다.</div>';
			$(chkDiv).html(html);
			let div = $(obj).siblings('div');
			$(div).children('i').remove();
			$(div).html($(div).html()+'<i class="fas fa-exclamation fa-fw" style="color:red"></i>');
			return false;
		}else if(!regRs){
			let html = '<div class="col-12 text-center text-danger border-danger font-weight-bold inp2">비밀번호는 8~20자리 영문, 숫자, 특수문자 조합으로 입력해야 합니다.</div>';
			$(chkDiv).html(html);
			let div = $(obj).siblings('div');
			$(div).children('i').remove();
			$(div).html($(div).html()+'<i class="fas fa-exclamation fa-fw" style="color:red"></i>');
			return false;
		}else{
			let html = '<div class="col-12 text-center text-success border-success font-weight-bold inp2">사용가능합니다.</div>';
			$(chkDiv).html(html);
			let div = $(obj).siblings('div');
			$(div).children('i').remove();
			$(div).html($(div).html()+'<i class="fas fa-check fa-fw" style="color:#1cc88a"></i>');
			return true;
		}
	}
	
	function checkPassRe(obj){
		let confirmPass = $('input[name=newPassword]').val() == obj.value;
		let chkDiv = $('#chkDiv');
		if(obj.value == ""){
			let html = '<div class="col-12 text-center text-danger border-danger font-weight-bold inp2">필수입력입니다.</div>';
			$(chkDiv).html(html);
			let div = $(obj).siblings('div');
			$(div).children('i').remove();
			$(div).html($(div).html()+'<i class="fas fa-exclamation fa-fw" style="color:red"></i>');
			return false;
		}else if(!confirmPass){
			let html = '<div class="col-12 text-center text-danger border-danger font-weight-bold inp2">비밀번호가 일치하지 않습니다.</div>';
			$(chkDiv).html(html);
			let div = $(obj).siblings('div');
			$(div).children('i').remove();
			$(div).html($(div).html()+'<i class="fas fa-exclamation fa-fw" style="color:red"></i>');
			return false;
		}else{
			let html = '<div class="col-12 text-center text-success border-success font-weight-bold inp2">비밀번호가 일치합니다.</div>';
			$(chkDiv).html(html);
			let div = $(obj).siblings('div');
			$(div).children('i').remove();
			$(div).html($(div).html()+'<i class="fas fa-check fa-fw" style="color:#1cc88a"></i>');
			return true;
		}
	}
	
	function checkEmail(obj){
		let regId = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		let regRs = regId.test(obj.value);
		let chkDiv = $('#chkDiv');
		if(obj.value == ""){
			let html = '<div class="col-12 text-center text-danger border-danger font-weight-bold inp2">필수입력입니다.</div>';
			$(chkDiv).html(html);
			let div = $(obj).siblings('div');
			$(div).children('i').remove();
			$(div).html($(div).html()+'<i class="fas fa-exclamation fa-fw" style="color:red"></i>');
			return false;
		}else if(!regRs){
			let html = '<div class="col-12 text-center text-danger border-danger font-weight-bold inp2">올바른 형식의 이메일을 입력해주세요.</div>';
			$(chkDiv).html(html);
			let div = $(obj).siblings('div');
			$(div).children('i').remove();
			$(div).html($(div).html()+'<i class="fas fa-exclamation fa-fw" style="color:red"></i>');
			return false;
		}else{
			let html = '<div class="col-12 text-center text-success border-success font-weight-bold inp2">사용 가능합니다.</div>';
			$(chkDiv).html(html);
			let div = $(obj).siblings('div');
			$(div).children('i').remove();
			$(div).html($(div).html()+'<i class="fas fa-check fa-fw" style="color:#1cc88a"></i>');
			return true;
		}
	}
	
	function checkPhone(obj){
		let regId = /^01([0-9])-([0-9]{3,4})-([0-9]{4})$/;
		let regRs = regId.test(obj.value);
		let chkDiv = $('#chkDiv');
		if(obj.value == ""){
			let html = '<div class="col-12 text-center text-danger border-danger font-weight-bold inp2">필수입력입니다.</div>';
			$(chkDiv).html(html);
			let div = $(obj).siblings('div');
			$(div).children('i').remove();
			$(div).html($(div).html()+'<i class="fas fa-exclamation fa-fw" style="color:red"></i>');
			return false;
		}else if(!regRs){
			let html = '<div class="col-12 text-center text-danger border-danger font-weight-bold inp2">연락처는 01x-xxx(x)-xxxx 형식으로 01로 시작하는 하이픈(-)포함 12~13자리 숫자만 입력 가능합니다.</div>';
			$(chkDiv).html(html);
			let div = $(obj).siblings('div');
			$(div).children('i').remove();
			$(div).html($(div).html()+'<i class="fas fa-exclamation fa-fw" style="color:red"></i>');
			return false;
		}else{
			let html = '<div class="col-12 text-center text-success border-success font-weight-bold inp2">사용 가능합니다.</div>';
			$(chkDiv).html(html);
			let div = $(obj).siblings('div');
			$(div).children('i').remove();
			$(div).html($(div).html()+'<i class="fas fa-check fa-fw" style="color:#1cc88a"></i>');
			return true;
		}
	}
	
	function validation(){
		if(checkPass(document.frm.password) & checkNewPass(document.frm.newPassword) 
			& checkPassRe(document.frm.newPasswordChk) & checkEmail(document.frm.email) & checkPhone(document.frm.phone)){
			return true;
		}else{
			alert("입력 정보를 확인해주세요.");
			let html = '<div class="col-12 text-center text-danger border-danger font-weight-bold inp2">입력 정보를 확인해주세요.</div>';
			let chkDiv = $('#chkDiv');
			chkDiv.html(html);
			return false;
		}
	}
</script>
<%@ include file="../include/footer.jsp"%>