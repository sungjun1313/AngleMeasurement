<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authentication property="principal" var="pinfo" />
<layout:extends name="base">
	<layout:put block="title">각도 측정</layout:put>
	<layout:put block="css">
		<link rel="stylesheet" href="/resources/css/angle.css" />
	</layout:put>
	<layout:put block="content">
		<div class="container">
			<h3 class="my-4 text-center text-info">각도 측정</h3>
			
			<div id="angleBox" class="border">
				<div id="result">
					<div>
						<img class="img customPointer" src="/resources/images/no_image.png"
							data-uploadpath="<c:out value='${ angle.uploadPath }' />" data-uuid="<c:out value='${ angle.uuid }' />"
							data-filename="<c:out value='${ angle.fileName }' />"
							alt="각도 측정 이미지"
						/>
					</div>
				</div>
				<svg id="canvas">
					<polyline class="poly poly1" style="file:none;stroke:black;stoke-width:3"></polyline>
      				<polyline class="poly poly2" style="file:none;stroke:black;stoke-width:3"></polyline>
				</svg>
				<div class="cir cir1"></div>
    			<div class="cir cir2"></div>
    			<div class="cir cir3"></div>
			</div>
			
			<form id="modifyForm" action="/angle/angleDetail/<c:out value='${ empty prevValue.uuid ? angle.uuid : prevValue.uuid }'/> " method="post" enctype="multipart/form-data" class="col-12 col-lg-8 my-4 mx-auto">
				<input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" />
				<input type="hidden" name="bno" value="<c:out value='${ empty prevValue.bno ? board.bno : prevValue.bno }' />" />
				<input type="hidden" name="uploadPath" value="<c:out value='${ empty prevValue.uploadPath ? angle.uploadPath : prevValue.uploadPath }' />" />
				<input type="hidden" name="fileName" value="<c:out value='${ empty prevValue.fileName ? angle.fileName : prevValue.fileName }' />" />
				<input type="hidden" name="writer" value="<c:out value='${ board.writer }' />" />
				<input type="hidden" name="pageNum" value="<c:out value='${ cri.pageNum }' />" />
				<input type="hidden" name="amount" value="<c:out value='${ cri.amount }' />" />
				<input type="hidden" name="type" value="<c:out value='${ cri.type }' />" />
				<input type="hidden" name="keyword" value="<c:out value='${ cri.keyword }' />" />
				<div class="form-group">
					<label for="id_angleFile" class="text-primary">파일*</label>
					<!-- input file multiple 삭제 -->
					<input type="file" name="angleFile" id="id_angleFile" accept="image/*" class="form-control" />
					<c:forEach var="error" items="${ validation.uuidResult }">
						<p class="text-danger warning"><c:out value="${ error }" /></p>
					</c:forEach>
					<c:forEach var="error" items="${ validation.bnoResult }">
						<p class="text-danger warning"><c:out value="${ error }" /></p>
					</c:forEach>
					<c:forEach var="error" items="${ validation.uuidResult }">
						<p class="text-danger warning"><c:out value="${ error }" /></p>
					</c:forEach>
				</div>
				<div class="form-group">
					<label for="id_originAngle">
						기존 각도
					</label>
					<input type="number" name="originAngle" id="id_originAngle" class="form-control" readOnly value="<c:out value='${ angle.angle }'/>" />
					<!--<input type="hidden" id="submitAngle" name="angleList[0].angle" />-->
				</div>
				<div class="form-group">
					<label for="id_angle" class="text-primary">
						각도*
					</label>
					<input type="number" name="angle" id="id_angle" class="form-control" readOnly />
					<!--<input type="hidden" id="submitAngle" name="angleList[0].angle" />-->
				</div>
				<div class="form-group text-center">
					<input type="submit" class="btn btn-info" value="저장" />
					<button id="deleteBtn" class="btn btn-danger ml-3">삭제</button>
				</div>
			</form>
		</div>
	</layout:put>
	<layout:put block="js">
		<script src="/resources/js/angle.js"></script>
		<script src="/resources/js/utils.js"></script>
		<script>
			$(document).ready(function(){
				var modifyForm = $("#modifyForm");
				
				$(".img").each(function(index, item){
					//console.log($(item).data("filename"));
					var fileCallPath = encodeURIComponent($(item).data("uploadpath") + "/" + $(item).data("uuid") + "_" + $(item).data("filename"));
					$(item).attr("src", "/angle/display?fileName=" + fileCallPath);
				});
				
				modifyForm.submit(function(e){
					var angleFileVal = $("#id_angleFile").val();
					var angleVal = $("#id_angle").val();
					
					if(!utils.isEmptyCheck(angleVal.trim(), "각도")){
						return false;
					}
				});
				
				$("#deleteBtn").on("click", function(e){
					e.preventDefault();
					if(confirm("정말로 삭제하시겠습니까?")){
						var deleteUrl = modifyForm.attr("action").replace("angleDetail", "angleDelete");
						modifyForm.attr("action", deleteUrl);
						modifyForm.submit();
					}
					
				});
				
				utils.reSubmitPrevent();
				
			});
		</script>
	</layout:put>
</layout:extends>