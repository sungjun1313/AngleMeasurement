<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<layout:extends name="base">
	<layout:put block="title">로그아웃페이지</layout:put>
	<layout:put block="content">
		<div class="container">
			<h3 class="mt-5 mb-3 text-center">Logout</h3>
			<div class="row justify-content-center">
				<div class="col-12 col-md-6">
					<form action='/customLogout' method='post'>
						<input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" />
						<div class="alert alert-danger text-center">
							<div>
								로그아웃하시겠습니까?
							</div>
							<div>
								<button type="submit" class="btn btn-danger">로그아웃</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</layout:put>
</layout:extends>