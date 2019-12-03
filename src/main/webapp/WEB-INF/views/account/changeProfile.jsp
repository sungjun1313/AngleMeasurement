<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authentication property="principal" var="pinfo" />
<layout:extends name="base">
	<layout:put block="title">회원정보변경</layout:put>
	<layout:put block="content">
		<div class="container">
			<h3 class="mt-5 mb-3 text-center text-info">프로필 변경</h3>
			<div class="mx-auto col-12 col-lg-8">
				<form id="changeProfileForm" action='/changeProfile' method='post'>
					<input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" />
					<div class="form-group">
						<label for="id_userName" class="text-primary">
							이름*
						</label>
						<input type="text" name="userName" id="id_userName" class="form-control" required value="<c:out value='${ empty prevValue.userName ? pinfo.member.userName : prevValue.userName }'/>" />
						<c:forEach var="error" items="${ validation.userNameResult }">
							<p class="text-danger warning"><c:out value="${ error }" /></p>
						</c:forEach>
					</div>
					<div class="form-group">
						<label for="id_email">
							이메일
						</label>
						<input type="email" name="email" id="id_email" class="form-control" value="<c:out value='${ empty prevValue.email ? pinfo.member.email : prevValue.email }'/>" />
						<c:forEach var="error" items="${ validation.emailResult }">
							<p class="text-danger warning"><c:out value="${ error }" /></p>
						</c:forEach>
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
				$("#changeProfileForm").submit(function(){
					var userNameVal = $("#id_userName").val();
					var emailVal = $("#id_email").val();
					
					if(!utils.isSpaceCheck(userNameVal, userNameVal.trim(), "이름")){
						return false;
					}
					
					if(!utils.isSpaceCheck(emailVal, emailVal.trim(), "이메일")){
						return false;
					}
					
					if(!utils.isEmptyCheck(userNameVal.trim(), "이름")){
						return false;
					}
					
					if(emailVal.trim() && !utils.validateEmail(emailVal)){
						return false;
					}
				});
				
				utils.reSubmitPrevent();
			});
		</script>
	</layout:put>
</layout:extends>