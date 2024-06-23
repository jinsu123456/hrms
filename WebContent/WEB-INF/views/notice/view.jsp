<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/navigator.jsp"%>
<% pageContext.setAttribute("nl", "\n"); %>
<!DOCTYPE html>
<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Page Heading -->
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">공지사항</h1>
	</div>

	<div class="card o-hidden border-0 shadow-lg">
		<div class="card-body p-0">
			<!-- Nested Row within Card Body -->
			<div class="row">
				<div class="col">
					<div class="p-4">
						<div class="my-4">
							<span class="d-inline bg-primary card text-white py-1 px-3">공지사항</span>
							<h1 class="d-inline h4 text-gray-900 font-weight-bold">
							${vo.title}
							</h1>
						</div>
						<hr>
						<div class="py-2">
							<span class="d-inline font-weight-bold text-gray-800">작성자</span>
							|&nbsp; 
							<c:choose>
								<c:when test="${vo.userId eq '99000'}">
									대표<br>
								</c:when>
								<c:when test="${vo.userId eq '99001'}">
									관리자<br>
								</c:when>
								<c:otherwise>
									오류<br>
								</c:otherwise>
							</c:choose>
							<span class="d-inline font-weight-bold text-gray-800">등록일</span>
							|&nbsp; ${vo.rdate}
						</div>
						<hr>
						<div class="py-2">
							<span class="d-block font-weight-bold text-gray-800 mb-3">공지사항 내용</span>
							<c:out value="${fn:replace(vo.content, nl, '<br/>')}" escapeXml="false"/>
						</div>
						<div class="col p-5 mt-5 text-right py-2">
							<span class="d-inline font-weight-bold text-gray-800">첨부파일</span><br>
							<c:forEach var="i" items="${list }">
								<a href="javascript:downloadFn('${i.noticeFileNo}', '${i.noticeNo}', '${i.realNm}', '${i.originNm}')"> 
									${i.originNm}<br>
								</a>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col p-1">
					<div class="mb-4 text-center">
						<hr>
						<a href="main.do" class="btn btn-secondary btn-user">목록으로</a> 
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<a href="modify.do?noticeNo=${vo.noticeNo}" class="btn btn-primary btn-user">수정하기</a>
							<c:if test="${vo.delyn ne 'y' }">
								<a onclick="delynFn()" class="btn btn-dark btn-user">삭제하기</a>							
							</c:if> 
						</sec:authorize> 
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	function delynFn(){
		let select = confirm("공지사항을 삭제 하시겠습니까?");
		if(select===true){
			let f = document.createElement('form');
	    
			let noticeNoInput;
			noticeNoInput = document.createElement('input');
			noticeNoInput.setAttribute('type', 'hidden');
			noticeNoInput.setAttribute('name', 'noticeNo');
			noticeNoInput.setAttribute('value', '${vo.noticeNo}');
			f.appendChild(noticeNoInput);
			
			f.setAttribute('method', 'post');
			f.setAttribute('action', 'delyn.do');
			document.body.appendChild(f);
			f.submit();
		}
	}
	
	function downloadFn(noticeFileNo, noticeNo, realNm, originNm){
		let f = document.createElement('form');
		let nameArr = ["noticeFileNo", "noticeNo", "realNm", "originNm"];
		let valueArr = [noticeFileNo, noticeNo, realNm, originNm];
		let inputArr = [];
		for(let i=0; i<valueArr.length; i++){
			let input = document.createElement('input');
			input.setAttribute('type', 'hidden');
			input.setAttribute('name', nameArr[i]);
			input.setAttribute('value', valueArr[i]);
			inputArr[i] = input;
			f.appendChild(inputArr[i]);
		}
		f.setAttribute('method', 'post');
		f.setAttribute('action', 'download.do');
		document.body.appendChild(f);
		f.submit();
	}
</script>
<!-- /.container-fluid -->
<%@ include file="../include/footer.jsp"%>