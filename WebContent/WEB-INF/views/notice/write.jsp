<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/navigator.jsp"%>
<!DOCTYPE html>
<head>
	<link href="${pageContext.request.contextPath}/resources/css/fileUpload.css" rel="stylesheet">
</head>
<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Content Row -->
	<div class="row">

		<!-- Border Bottom Utilities -->
		<div class="col-lg-12">

			<div class="card mb-4 py-3 border-bottom-primary">
				<div class="card-body">
					<b>공지 작성</b>
				</div>
			</div>
			<div class="card o-hidden border-0 shadow-lg my-5">
				<div class="card-body p-0">
					<!-- Nested Row within Card Body -->
					<div class="row">
						<div class="col-lg-3 d-none d-lg-block"></div>
						<div class="col-lg-6">
							<div class="p-5">
								<form class="user" id="form" method="post" onsubmit="return false;" enctype="multipart/form-data">
									<div class="form-group">
										<div class="row">
											<div class="form-group">
												제목 : <input style="width:35rem" name="title" type="text" class="resize-none form-control" placeholder="제목" required>
											</div>
										</div>
									</div>
									<div class="form-group">
										내용 : <textarea name="content" style="height:20rem" class="resize-none form-control" required></textarea>
									</div>
									<div class="form-group insert">
										첨부파일 : 
										<input type="file" onchange="addFile(this);" multiple />										
										<div class="file-list"></div>
									</div>
									<hr>
									<div class="row">
										<div class="col-lg-6">
											<a href="main.do" class="btn btn-secondary btn-user btn-block">목록으로</a> 
										</div>
										<div class="col-lg-6">
											<button type="button" onclick="submitForm()"class="btn btn-primary btn-user btn-block">작성완료</button> 
										</div>
									</div>
								</form>
								<hr>
							</div>
						</div>
						<div class="col-lg-3 d-none d-lg-block"></div>
					</div>
				</div>
			</div>

		</div>

	</div>
</div>
<!-- /.container-fluid -->
<script>
	var fileNo = 0;
	var filesArr = new Array();
	
	/* 첨부파일 추가 */
	function addFile(obj) {
		var maxFileCnt = 5; // 첨부파일 최대 개수
		var attFileCnt = document.querySelectorAll('.filebox').length; // 기존 추가된 첨부파일 개수
		var remainFileCnt = maxFileCnt - attFileCnt; // 추가로 첨부가능한 개수
		var curFileCnt = obj.files.length; // 현재 선택된 첨부파일 개수
		
		console.log(curFileCnt);
	
		// 첨부파일 개수 확인
		if (curFileCnt > remainFileCnt) {
			alert("첨부파일은 최대 " + maxFileCnt + "개 까지 첨부 가능합니다.");
		}
	
		for (var i = 0; i < Math.min(curFileCnt, remainFileCnt); i++) {
	
			const file = obj.files[i];
	
			// 첨부파일 검증
			if (validation(file)) {
				// 파일 배열에 담기
				var reader = new FileReader();
				reader.onload = function() {
					filesArr.push(file);
				};
				reader.readAsDataURL(file)
	
				// 목록 추가
				let htmlData = '';
				htmlData += '<div id="file' + fileNo + '" class="filebox">';
				htmlData += '   <p class="name">' + file.name + '</p>';
				htmlData += '   <a class="delete" onclick="deleteFile('
						+ fileNo
						+ ');"><i class="far fa-minus-square"></i></a>';
				htmlData += '</div>';
				$('.file-list').append(htmlData);
				fileNo++;
			} else {
				continue;
			}
		}
		// 초기화
		document.querySelector("input[type=file]").value = "";
	}
	
	/* 첨부파일 검증 */
	function validation(obj) {
		const fileTypes = [ 'application/pdf', 'image/gif', 'image/jpeg',
				'image/png', 'image/bmp', 'image/tif', 'text/plain', 'application/msword',
				'application/haansofthwp', 'application/x-hwp'
				, 'application/vnd.ms-excel'
				, 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' ];
		if (obj.name.length > 100) {
			alert("파일명이 100자 이상인 파일은 제외되었습니다.");
			return false;
		} else if (obj.size > (100 * 1024 * 1024)) {
			alert("최대 파일 용량인 100MB를 초과한 파일은 제외되었습니다.");
			return false;
		} else if (obj.name.lastIndexOf('.') == -1) {
			alert("확장자가 없는 파일은 제외되었습니다.");
			return false;
		} else if (!fileTypes.includes(obj.type)) {
			alert("첨부가 불가능한 파일은 제외되었습니다.");
			return false;
		} else {
			return true;
		}
	}
	
	/* 첨부파일 삭제 */
	function deleteFile(num) {
		document.querySelector("#file" + num).remove();
		filesArr[num].is_delete = true;
	}
	
	/* 폼 전송 */
	function submitForm() {
		let form = document.getElementById("form");
				
		if(!form.checkValidity()){
			form.reportValidity();
			return false;
		}
		
		let choice = confirm("공지 작성을 완료하시겠습니까?");
		
		if(choice){
			// 폼데이터 담기
			/* var form = document.querySelector("form"); */
			var formData = new FormData(form);
			for (var i = 0; i < filesArr.length; i++) {
				// 삭제되지 않은 파일만 폼데이터에 담기
				if (!filesArr[i].is_delete) {
					formData.append("files", filesArr[i]);
				}
			}
	
		$.ajax({
			type : 'post',
			/* enctype='multipart/form-data', */
			url : 'write.do',
			dataType : 'json',
			data : formData,
//			async : true,
//			timeout : 30000,
			cache : false,
			headers : {
				'cache-control' : 'no-cache',
				'pragma' : 'no-cache'
			},
			contentType : false,
			processData : false,
			success : function() {
				alert("공지 작성이 완료되었습니다.");
				location.href="main.do"
			}/* ,
			error : function(xhr, desc, err) {
				alert('에러가 발생 하였습니다.');
				return;
			} */
			})
		}
	}
	
</script>
<%@ include file="../include/footer.jsp"%>