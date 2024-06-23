<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../include/navigator.jsp"%>
<!DOCTYPE html>
<head>
	<link href="${pageContext.request.contextPath}/resources/css/user.css" rel="stylesheet" type="text/css">
	
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.1/jquery.min.js"></script>
	<link type="text/css" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet" />
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
	
	<link href="${pageContext.request.contextPath}/resources/css/sign.css" rel="stylesheet">
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
							사원 등록
							</h1>
						</div>
						<hr>
					</div>
					<div class="row">
						<div class="col-lg-3 d-none d-lg-block"></div>
						<div class="col-lg-6">
							<div class="p-1 text-left">
								<form method="post" action="regist.do">
									<div class="row m-3">
										<div class="col-3 align-self-center">
										<span class="text-dark font-weight-bold">비밀번호</span>
										<span class="text-danger font-weight-bold">*</span>
										</div>
										<input type="password" class="inp2" name="password">
									</div>
									<div class="row m-3">
										<div class="col-3 align-self-center">
										<span class="text-dark font-weight-bold">이름</span>
										<span class="text-danger font-weight-bold">*</span>
										</div>
										<input type="text" class="inp2" name="name">
									</div>
									<div class="row m-3">
										<div class="col-6">
											<div class="row">
												<div class="col-6 align-self-center">
												<span class="text-dark font-weight-bold">부서</span>
												<span class="text-danger font-weight-bold">*</span>
												</div>
												<select class="inp" name="dept">
													<option value="P">기획부</option>
													<option value="H">인사부</option>
													<option value="S">영업부</option>
													<option value="D">개발부</option>
													<option value="M">관리부</option>
												</select>
											</div>
										</div>
										<div class="col-6">
											<div class="row">
												<div class="col-6 align-self-center">
												<span class="text-dark font-weight-bold">직급</span>
												<span class="text-danger font-weight-bold">*</span>
												</div>
												<select class="inp" name="position">
													<option value="E">사원</option>
													<option value="L">팀장</option>
													<option value="D">부장</option>
													<option value="C">대표</option>
													<option value="A">관리자</option>
												</select>
											</div>
										</div>
									</div>
									<div class="row m-3">
										<div class="col-6">
											<div class="row">
												<div class="col-6 align-self-center">
												<span class="text-dark font-weight-bold">입사일</span>
												<span class="text-danger font-weight-bold">*</span>
												</div>
												<div class="col-6 align-self-center p-0">
													<input type="text" name="joinDate" value="" id="joinDate" class="datepicker joinDate" placeholder="입사일 선택" readonly="true">
													<i class="fas fa-lg fa-calendar" onclick="iClickFn(joinDate)" style="cursor: pointer;"></i>
												</div>
											</div>
										</div>
										<div class="col-6">
											<div class="row">
												<div class="col-6 align-self-center">
												<span class="text-dark font-weight-bold">상태</span>
												<span class="text-danger font-weight-bold">*</span>
												</div>
												<select class="inp" name="state">
													<option value="1">재직</option>
													<option value="2">휴직</option>
													<option value="9">퇴직</option>
												</select>
											</div>
										</div>
									</div>
									<div class="row m-3">
										<div class="col-3 align-self-center">
										<span class="text-dark font-weight-bold">연락처</span>
										</div>
										<input type="text" class="inp2" name="phone">
									</div>
									<div class="row m-3">
										<div class="col-3 align-self-center">
										<span class="text-dark font-weight-bold">이메일</span>
										</div>
										<input type="text" class="inp2" name="email">
									</div>
									<div class="row m-3">
										<div class="col-3 align-self-center">
										<span class="text-dark font-weight-bold">주소</span>
										</div>
										<input type="text" class="inp2" name="addr">
									</div>
									<div class="row m-5">
										<div class="col-12 text-center">
											<a href="main.do" class="btn btn-secondary btn-user">뒤로가기</a> 
											<button type="submit" class="btn btn-primary btn-user">사원등록</button> 
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

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/calendar_noMin_noMax.js"></script>

<%@ include file="../include/footer.jsp"%>