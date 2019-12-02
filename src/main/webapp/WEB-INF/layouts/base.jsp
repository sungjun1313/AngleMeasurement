<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic:400,700" rel="stylesheet">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="/resources/css/base.css">
<layout:block name="css"></layout:block>
<title>
	<layout:block name="title"></layout:block>
</title>
</head>
<body>
	<!-- Header -->
	<nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">
		<div class="container">
  			<a class="navbar-brand" href="/angle/list">Angle Measurement</a>
  			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    			<span class="navbar-toggler-icon"></span>
  			</button>
  		
  			<div class="collapse navbar-collapse" id="navbarSupportedContent">
   				<ul class="navbar-nav ml-auto">
      			 	<sec:authorize access="isAnonymous()">
      			 		<li class="nav-item">
      			 			<a class="nav-link" href="/register">Register</a>
      			 		</li>
      			 		<li class="nav-item">
      			 			<a class="nav-link" href="/customLogin">Login</a>
      			 		</li>
      			 	</sec:authorize>
      		 	
      			 	<sec:authorize access="isAuthenticated()">
      			 		<li class="nav-item">
      			 			<a class="nav-link" href="/customProfile">Profile</a>
      			 		</li>
      			 		<li class="nav-item">
      			 			<a class="nav-link" href="/customLogout">Logout</a>
      			 		</li>
      			 	</sec:authorize>
    			</ul>
  			</div>
  		</div>
	</nav>
	<!-- 공통 메시지 -->
	<c:if test="${ not empty message }">
		<div class="container">
			<div class="alert alert-danger my-4">
				<c:out value="${ message }" />
			</div>
		</div>
	</c:if>
	
	<div><layout:block name="content"></layout:block></div>
	<!-- footer -->
	<footer>
		<div class="container text-center">
			SungJun's Portfolio
		</div>
	</footer>
</body>

<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<layout:block name="js"></layout:block>

</html>