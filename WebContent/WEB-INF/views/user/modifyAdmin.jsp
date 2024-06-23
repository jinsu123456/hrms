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

	<div class="card o-hidden border-0 shadow-lg mb-5">
		<div class="card-body p-0">
			<!-- Nested Row within Card Body -->
			<div class="row">
				<div class="col-12">
					<div class="px-5 pt-5">
						<div class="my-4">
							<h1 class="d-inline h4 text-gray-900 font-weight-bold">
							사원 정보 수정
							</h1>
						</div>
						<hr>
					</div>
					<div class="row">
						<div class="col-lg-3 d-none d-lg-block"></div>
						<div class="col-lg-6">
							<div class="p-1 text-left">
								<form action="modifyAdminOk.do" method="post">
									<div class="row m-3">
										<div class="col-3 align-self-center">
										<span class="text-dark font-weight-bold">사원번호</span>
										</div>
										<input type="text" class="inp2" value="${user.userid}" disabled>
										<input type="hidden" name="userid" value="${user.userid}">
									</div>
									<div class="row m-3">
										<div class="col-3 align-self-center">
										<span class="text-dark font-weight-bold">새 비밀번호</span>
										</div>
										<input type="password" class="inp2" name="password">
									</div>
									<div class="row m-3">
										<div class="col-3 align-self-center">
										<span class="text-dark font-weight-bold">새 비밀번호 확인</span>
										</div>
										<input type="password" class="inp2">
									</div>
									<div class="row m-3">
										<div class="col-3 align-self-center">
										<span class="text-dark font-weight-bold">이름</span>
										<span class="text-danger font-weight-bold">*</span>
										</div>
										<input type="text" class="inp2" name="name" value="${user.name}">
									</div>
									<div class="row m-3">
										<div class="col-6">
											<div class="row">
												<div class="col-6 align-self-center">
												<span class="text-dark font-weight-bold">부서</span>
												<span class="text-danger font-weight-bold">*</span>
												</div>
												<select class="inp" name="dept">
													<option value="P" <c:if test="${user.dept eq 'P'}">selected</c:if>>기획부</option>
													<option value="H" <c:if test="${user.dept eq 'H'}">selected</c:if>>인사부</option>
													<option value="S" <c:if test="${user.dept eq 'S'}">selected</c:if>>영업부</option>
													<option value="D" <c:if test="${user.dept eq 'D'}">selected</c:if>>개발부</option>
													<option value="M" <c:if test="${user.dept eq 'M'}">selected</c:if>>관리부</option>
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
													<option value="E" <c:if test="${user.position eq 'E'}">selected</c:if>>사원</option>
													<option value="L" <c:if test="${user.position eq 'L'}">selected</c:if>>팀장</option>
													<option value="D" <c:if test="${user.position eq 'D'}">selected</c:if>>부장</option>
													<option value="A" <c:if test="${user.position eq 'A'}">selected</c:if>>관리자</option>
													<option value="C" <c:if test="${user.position eq 'C'}">selected</c:if>>대표</option>
												</select>
											</div>
										</div>
									</div>
									<div class="row m-3">
										<div class="col-6">
											<div class="row">
												<div class="col-6 align-self-center">
												<span class="text-dark font-weight-bold">상태</span>
												<span class="text-danger font-weight-bold">*</span>
												</div>
												<select class="inp" name="state">
													<option value="1" <c:if test="${user.state eq 1}">selected</c:if>>재직</option>
													<option value="2" <c:if test="${user.state eq 2}">selected</c:if>>휴직</option>
													<option value="9" <c:if test="${user.state eq 9}">selected</c:if>>퇴직</option>
												</select>
											</div>
										</div>
									</div>
									<div class="row m-3">
										<div class="col-3 align-self-center">
										<span class="text-dark font-weight-bold">연락처</span>
										</div>
										<input type="text" class="inp2" name="phone" value="${user.phoneNumber}">
									</div>
									<div class="row m-3">
										<div class="col-3 align-self-center">
										<span class="text-dark font-weight-bold">이메일</span>
										</div>
										<input type="text" class="inp2" name="email" value="${user.email}">
									</div>
									<div class="row m-3">
										<div class="col-3 align-self-center">
										<span class="text-dark font-weight-bold">주소</span>
										</div>
										<input type="text" class="inp2" name="addr" value="${user.addr}">
									</div>
									<div class="row m-5">
										<div class="col-12 text-center">
											<a href="main.do" class="btn btn-secondary btn-user">뒤로가기</a> 
											<button type="submit" class="btn btn-primary btn-user">수정하기</button> 
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