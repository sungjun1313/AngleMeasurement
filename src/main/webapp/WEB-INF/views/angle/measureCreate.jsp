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
				<div id="result"></div>
				<svg id="canvas">
					<polyline class="poly poly1" style="file:none;stroke:black;stoke-width:3"></polyline>
      				<polyline class="poly poly2" style="file:none;stroke:black;stoke-width:3"></polyline>
				</svg>
				<div class="cir cir1"></div>
    			<div class="cir cir2"></div>
    			<div class="cir cir3"></div>
			</div>
			
			<form id="createForm" action="/angle/measure/create/<c:out value='${ empty prevValue.bno ? bno : prevValue.bno }'/> " method="post" enctype="multipart/form-data" class="col-12 col-lg-8 my-4 mx-auto">
				<input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" />
				<input type="hidden" name="pageNum" value="<c:out value='${ cri.pageNum }' />" />
				<input type="hidden" name="amount" value="<c:out value='${ cri.amount }' />" />
				<input type="hidden" name="type" value="<c:out value='${ cri.type }' />" />
				<input type="hidden" name="keyword" value="<c:out value='${ cri.keyword }' />" />
				<div class="form-group">
					<label for="id_angleFile" class="text-primary">파일*</label>
					<!-- input file multiple 삭제 -->
					<input type="file" name="angleFile" id="id_angleFile" accept="image/*" required class="form-control" />
				</div>
				<div class="form-group">
					<label for="id_angle" class="text-primary">
						각도*
					</label>
					<input type="number" name="angle" id="id_angle" class="form-control" readOnly />
					<!--<input type="hidden" id="submitAngle" name="angleList[0].angle" />-->
				</div>
				<div class="form-grouo text-center">
					<input type="submit" class="btn btn-info" value="저장" />
				</div>
			</form>
		</div>
	</layout:put>
	<layout:put block="js">
		<script src="/resources/js/angle.js"></script>
		<script src="/resources/js/utils.js"></script>
		<script>
			$(document).ready(function(){
				
				$("#createForm").submit(function(e){
					var angleFileVal = $("#id_angleFile").val();
					var angleVal = $("#id_angle").val();
					
					if(!utils.isEmptyCheck(angleFileVal.trim(), "파일")){
						return false;
					}
					
					if(!utils.isEmptyCheck(angleVal.trim(), "각도")){
						return false;
					}
				});
				
				utils.reSubmitPrevent();
				
			});
		</script>
	</layout:put>
</layout:extends>