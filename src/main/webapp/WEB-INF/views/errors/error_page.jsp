<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<layout:extends name="base">
	<layout:put block="title">에러페이지</layout:put>
	<layout:put block="content">
		<div class="container">
			<div class="alert alert-danger text-center font-weight-bold my-4">
				Error Page
			</div>
			<div>
				<c:out value="${ exception }"></c:out>
			</div>
		</div>
	</layout:put>
</layout:extends>