<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authentication property="principal" var="pinfo" />
<layout:extends name="base">
	<layout:put block="title">로그아웃페이지</layout:put>
	<layout:put block="content">
		<div class="container">
			<div class="mx-auto my-5 col-12 col-lg-8 border border-success rounded p-4">
				<h3 class="text-center text-info">프로필</h3>
				<div class="row pt-2">
					<div class="col-4 text-info text-right font-weight-bold border-right">아이디</div>
					<div class="col-8 pl-2">
						<c:out value="${ pinfo.member.userId }" />
					</div>
				</div>
				<div class="row pt-2">
					<div class="col-4 text-info text-right font-weight-bold border-right">이름</div>
					<div class="col-8 pl-2">
						<c:out value="${ pinfo.member.userName }" />
					</div>
				</div>
				<div class="row pt-2">
					<div class="col-4 text-info text-right font-weight-bold border-right">이메일</div>
					<div class="col-8 pl-2">
						<c:out value="${ pinfo.member.email }" />
					</div>
				</div>
				<div class="row pt-2">
					<div class="col-4 text-info text-right font-weight-bold border-right">등록일</div>
					<div class="col-8 pl-2">
						<fmt:formatDate value="${ pinfo.member.regDate }" pattern="yyyy-MM-dd HH:mm:ss" />
					</div>
				</div>
				<div class="row pt-2">
					<div class="col-4 text-info text-right font-weight-bold border-right">수정일</div>
					<div class="col-8 pl-2">
						<fmt:formatDate value="${ pinfo.member.updateDate }" pattern="yyyy-MM-dd HH:mm:ss" />
					</div>
				</div>
				<div class="row pt-2">
					<div class="col-4 text-info text-right font-weight-bold border-right">마지막 접속일</div>
					<div class="col-8 pl-2">
						<fmt:formatDate value="${ pinfo.member.lastLogin }" pattern="yyyy-MM-dd HH:mm:ss" />
					</div>
				</div>
				<div class="pt-4 mt-4 text-center border-top">
					<a href="/changeProfile" class="btn btn-primary mr-2">프로필 변경</a>
					<a href="/changePassword" class="btn btn-info">비밀번호 변경</a>
				</div>
			</div>
		</div>
	</layout:put>
</layout:extends>