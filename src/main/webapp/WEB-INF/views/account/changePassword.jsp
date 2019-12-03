<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<layout:extends name="base">
	<layout:put block="title">로그아웃페이지</layout:put>
	<layout:put block="content">
		<div class="container">
			<h3 class="mt-5 mb-3 text-center text-info">비밀번호 변경</h3>
			<div class="mx-auto col-12 col-lg-8">
				<form id="changePasswordForm" action='/changePassword' method='post'>
					<input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" />
					<div class="form-group">
						<label for="id_oldPassword" class="text-primary">
							기존 비밀번호*
						</label>
						<input type="password" name="oldPassword" id="id_oldPassword" class="form-control" required />
						<c:forEach var="error" items="${ validation.oldUserPwResult }">
							<p class="text-danger warning"><c:out value="${ error }" /></p>
						</c:forEach>
					</div>
					<div class="form-group">
						<label for="id_newPassword1" class="text-primary">
							새 비밀번호*
						</label>
						<input type="password" name="newPassword1" id="id_newPassword1" class="form-control" required />
						<c:forEach var="error" items="${ validation.newUserPwResult }">
							<p class="text-danger warning"><c:out value="${ error }" /></p>
						</c:forEach>
					</div>
					<div class="form-group">
						<label for="id_newPassword2" class="text-primary">
							새 비밀번호 확인*
						</label>
						<input type="password" name="newPassword2" id="id_newPassword2" class="form-control" required />
					</div>
					<div class="form-group text-center">
						<input type="submit" class="btn btn-info" value="변경" />
					</div>
				</form>
			</div>
		</div>
	</layout:put>
	<layout:put block="js">
		<script src="/resources/js/utils.js"></script>
		<script>
			$(document).ready(function(){
				$("#changePasswordForm").submit(function(){
					var oldPasswordVal = $("#id_oldPassword").val();
					var newPassword1Val = $("#id_newPassword1").val();
					var newPassword2Val = $("#id_newPassword2").val();
					
					if(!utils.isSpaceCheck(oldPasswordVal, oldPasswordVal.trim(), "기존 비밀번호")){
						return false;
					}
					
					if(!utils.isSpaceCheck(newPassword1Val, newPassword1Val.trim(), "새 비밀번호")){
						return false;
					}
					
					if(!utils.isSpaceCheck(newPassword2Val, newPassword2Val.trim(), "새 비밀번호 확인")){
						return false;
					}
					
					if(!utils.isEmptyCheck(oldPasswordVal.trim(), "기존 비밀번호")){
						return false;
					}
					
					if(!utils.isEmptyCheck(newPassword1Val.trim(), "새 비밀번호")){
						return false;
					}
					
					if(!utils.isEmptyCheck(newPassword2Val.trim(), "새 비밀번호 확인")){
						return false;
					}
					
					if(newPassword1Val != newPassword2Val){
						alert("비밀번호가 일치하지 않습니다.");
						return false;
					}
					
				});
				
				utils.reSubmitPrevent();
			});
		</script>
	</layout:put>
</layout:extends>