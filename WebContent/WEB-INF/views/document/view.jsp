<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../include/navigator.jsp"%>
<% pageContext.setAttribute("nl", "\n"); %>
<!DOCTYPE html>
<head>
	<meta name="content-disposition" content="inline; filename=filename.pdf">
	<link href="${pageContext.request.contextPath}/resources/css/sign_view.css" rel="stylesheet">
</head>

<!-- Begin Page Content -->
<div class="container-fluid">
	<!-- Page Heading -->
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">기안</h1>
	</div>

	<div class="card o-hidden border-0 shadow-lg">
		<div class="card-body p-0">
			<!-- Nested Row within Card Body -->
			<div class="row">
				<div class="col-9">
					<div class="py-5 pl-5">
						<div class="my-4">
							<span class="d-inline bg-primary card text-white py-1 px-3">기안</span>
							<h1 class="d-inline h4 text-gray-900 font-weight-bold"></h1>
						</div>
						<hr>
						<div class="py-2">
							<span class="content-head">${vo.title }</span>
						</div>
						<hr>
						<div class="py-2">
							<span class="d-inline font-weight-bold text-gray-800">작성자</span>
							|&nbsp; 
							<span class="d-inline card bg-info text-white text-center px-2">${vo.dept }</span>
							<span>${vo.name }</span>
							<span class="text-xs font-weight-bold text-primary text-uppercase mb-1">${vo.position }</span>
							<span class="d-inline font-weight-bold text-gray-800 ml-5">등록일</span>
							|&nbsp; ${vo.date}
						</div>
						<hr>
						<div class="py-2 doc-content">
							<c:out value="${fn:replace(vo.content, nl, '<br/>')}" escapeXml="false"/>
						</div>
						<hr>
						<div class="py-2">
							<span class="d-inline font-weight-bold text-gray-800">첨부파일</span><br>
							<c:forEach var="i" items="${dfList }">
								<a href="javascript:downloadFn('${i.fileNo}', '${i.docNo}', '${i.realNm}', '${i.originNm}')"> 
									${i.originNm}<br>
								</a>
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="col-3">
					<div class="py-5 pr-5">
						<div class="row my-4">
							<div class="col">
								<h1 class="d-inline h4 text-gray-900 mb-4 font-weight-bold">
									결재 현황 
									<c:if test="${nowState ne '철회' }">
										<span class="d-inline text-primary font-weight-bold">${count}/${list.size()}</span>
									</c:if>
								</h1>
							</div>
							<div class="col-auto">
								<c:choose>
									<c:when test="${nowState eq '대기' }">
										<div class="d-inline card text-secondary text-center px-3 py-1 border-secondary font-weight-bold">대기</div>
									</c:when>
									<c:when test="${nowState eq '진행' }">
										<div class="d-inline card text-danger text-center px-3 py-1 border-danger font-weight-bold">진행</div>
									</c:when>
									<c:when test="${nowState eq '승인' }">
										<div class="d-inline card bg-info text-white text-center px-3 py-1 border-info font-weight-bold">승인</div>
									</c:when>
									<c:when test="${nowState eq '반려' }">
										<div class="d-inline card bg-dark text-white text-center px-3 py-1 border-dark font-weight-bold">반려</div>
									</c:when>
									<c:when test="${nowState eq '철회' }">
										<span class="d-inline card bg-dark text-white text-center px-3 py-1 border-dark font-weight-bold">철회</span>
									</c:when>
								</c:choose>
							</div>
						</div>
						<c:forEach var="item" items="${list }">
							<c:choose>
								<c:when test="${item.state eq 0}">
									<div class="card text-secondary px-3 py-2 my-2 border-secondary font-weight-bold">
										<div class="row">
											<span class="d-inline col">${item.name } ${item.position }</span> 
											<span class="d-inline col-auto">대기</span>
										</div>
									</div>
								</c:when>
								<c:when test="${item.state eq 1}">
									<div class="card text-danger px-3 py-2 my-2 border-danger font-weight-bold">
										<div class="row">
											<span class="d-inline col">${item.name } ${item.position }</span> 
											<span class="d-inline col-auto">결재 대기</span>
										</div>
									</div>
								</c:when>
								<c:when test="${item.state eq 2}">
									<div class="card px-3 py-2 my-2 bg-info font-weight-bold text-white">
										<div class="row">
											<span class="d-inline col">${item.name } ${item.position }</span> 
											<span class="d-inline col-auto">승인</span>
										</div>
									</div>
								</c:when>
								<c:when test="${item.state eq 3}">
									<div class="card bg-dark px-3 py-2 my-2 font-weight-bold text-white">
										<div class="row">
											<span class="d-inline col">${item.name } ${item.position }</span> 
											<span class="d-inline col-auto">반려</span>
										</div>
									</div>
								</c:when>
								<c:when test="${item.state eq 9}">
									<div class="card bg-white px-3 py-2 my-2 font-weight-bold text-gray">
										<div class="row">
											<span class="d-inline col">${item.name } ${item.position }</span> 
											<span class="d-inline col-auto">-</span>
										</div>
									</div>
								</c:when>
							</c:choose>
						</c:forEach>
						<hr>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col p-5 mt-5">
					<div class="mb-4 text-center">
						<hr>
						<a href="main.do" class="btn btn-light border-dark btn-user"> 목록으로 </a> 
						<sec:authorize access="principal.userid.equals('${vo.userid }')">
							<c:if test="${vo.state eq '3'}">
								<a href="modify.do?docNo=${vo.docNo }" class="btn btn-primary btn-user"> 수정하기 </a>
							</c:if>
							<c:if test="${vo.state ne '9' && vo.state ne '3' && vo.state ne '2'}">
								<a onclick="withdrawalFn()" class="btn btn-dark btn-user"> 철회하기 </a>
							</c:if>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>
<script>
	let value = '${vo.docNo}';
</script>
<script src="${pageContext.request.contextPath }/resources/js/doc_view.js"></script>
<!-- /.container-fluid -->
<%@ include file="../include/footer.jsp"%>