<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<layout:extends name="base">
	<layout:put block="title">회원가입</layout:put>
	<layout:put block="content">
		<div class="container">
			<h3 class="text-center my-5 text-info font-weight-bold">
				회원가입
			</h3>
			<form id="registerForm" action="/register" method="post" class="mx-auto col-12 col-lg-8">
				<input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" />
				<input type="hidden" name="authList[0].auth" value="ROLE_MEMBER" />
				<div class="form-group">
					<label for="id_userId" class="text-primary">
						아이디*
					</label>
					<input type="text" name="userId" id="id_userId" class="form-control" required value="<c:out value='${ prevValue.userId }'/>" />
					<c:forEach var="error" items="${ validation.userIdResult }">
						<p class="text-danger"><c:out value="${ error }" /></p>
					</c:forEach>
				</div>
				<div class="form-group">
					<label for="id_userPw" class="text-primary">
						비밀번호*
					</label>
					<input type="password" name="userPw" id="id_userPw" class="form-control" required />
					<c:forEach var="error" items="${ validation.userPwResult }">
						<p class="text-danger"><c:out value="${ error }" /></p>
					</c:forEach>
				</div>
				<div class="form-group">
					<label for="id_userPw2" class="text-primary">
						비밀번호 확인*
					</label>
					<input type="password" name="userPw2" id="id_userPw2" class="form-control" required />
				</div>
				<div class="form-group">
					<label for="id_userName" class="text-primary">
						이름*
					</label>
					<input type="text" name="userName" id="id_userName" class="form-control" required value="<c:out value='${ prevValue.userName }'/>" />
					<c:forEach var="error" items="${ validation.userNameResult }">
						<p class="text-danger"><c:out value="${ error }" /></p>
					</c:forEach>
				</div>
				<div class="form-group">
					<label for="id_email">
						이메일
					</label>
					<input type="email" name="email" id="id_email" class="form-control" value="<c:out value='${ prevValue.email }'/>" />
					<c:forEach var="error" items="${ validation.emailResult }">
						<p class="text-danger"><c:out value="${ error }" /></p>
					</c:forEach>
				</div>
				<div class="form-group text-center">
					<input id="formBtn" type="submit" class="btn btn-info" value="회원가입" />
				</div>
			</form>
		</div>
	</layout:put>
	<layout:put block="js">
		<script>
			$(document).ready(function(){
				//빈값인 지 확인
				function isEmptyCheck(val, type){
					if(!val || val == null){
						alert(type + "을(를) 적어주세요.");
						return false;
					}
					return true;
				}
				
				//공백 여부 체크
				function isSpaceCheck(val1, val2, type){
					if(val1 != val2){
						alert(type + "의 공백을 제거해주세요.");
						return false;
					}
					return true;
				}
				
				//이메일 체크
				function validateEmail(email) {
				  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
				  return re.test(email);
				}
				
				$("#registerForm").submit(function(e){
					
					var userIdVal = $("#id_userId").val();
					var userPwVal = $("#id_userPw").val();
					var userPw2Val = $("#id_userPw2").val();
					var userNameVal = $("#id_userName").val();
					var emailVal = $("#id_email").val();
					
					if(!isSpaceCheck(userIdVal, userIdVal.trim(), "아이디")){
						return false;
					}
					
					if(!isSpaceCheck(userPwVal, userPwVal.trim(), "비밀번호")){
						return false;
					}
					
					if(!isSpaceCheck(userPw2Val, userPw2Val.trim(), "비밀번호 확인")){
						return false;
					}
					
					if(!isSpaceCheck(userNameVal, userNameVal.trim(), "이름")){
						return false;
					}
					
					if(!isSpaceCheck(emailVal, emailVal.trim(), "이메일")){
						return false;
					}
					
					if(!isEmptyCheck(userIdVal.trim(), "아이디")){
						return false;
					}
					if(!isEmptyCheck(userPwVal.trim(), "비밀번호")){
						return false;
					}
					if(!isEmptyCheck(userPw2Val.trim(), "비밀번호 확인")){
						return false;
					}
					if(!isEmptyCheck(userNameVal.trim(), "이름")){
						return false;
					}
					
					if(userPwVal != userPw2Val){
						alert("비밀번호가 일치하지 않습니다.");
						return false;
					}
					
					if(!validateEmail(emailVal)){
						alert("올바른 이메일을 적어주세요.");
						return false;
					}
					
				});
			});
		</script>
	</layout:put>
</layout:extends>