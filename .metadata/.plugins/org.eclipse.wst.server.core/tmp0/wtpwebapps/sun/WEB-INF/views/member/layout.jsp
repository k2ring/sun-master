<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%request.setCharacterEncoding("utf-8");%>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width">

<!-- bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp"
	crossorigin="anonymous">

<!-- icon -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
	integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
	crossorigin="anonymous" referrerpolicy="no-referrer">

<!-- custom style -->
<link href="${contextPath}/resources/css/style.css" rel="stylesheet"
	type="text/css" media="screen">

<!-- favicon -->
<!-- 블로그 favicon과 통일함 -->
<link rel="shortcut icon"
	href="https://t0.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=http://standout.tistory.com&size=16" />

<!-- title, 
tiles:insertAttribute name="title"의 값에 따른 값이 배치될것임. -->
<title><tiles:insertAttribute name="title" /></title>

</head>

<body>

	<div class="m-auto mt-5 pt-5 loginBox text-center">
		<!-- logo -->
		<img src="${contextPath}/resources/img/logo.png" width="208px"
			class="mb-4 me-3">

		<!-- 로그인/회원가입 레이아웃 배치됨 -->
		<tiles:insertAttribute name="body" />
		<!-- 로그인/회원가입 레이아웃 배치됨 -->

		<!-- footer -->
		<footer class="footer small text-secondary mt-5">
			<tiles:insertAttribute name="footer" />
		</footer>
	</div>

	<!-- jquery, 회원가입페이지의 ajax의 사용에 의해 추가함. -->
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	
	<!-- bootstrap -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-qKXV1j0HvMUeCBQ+QVp7JcfGl760yU08IQ+GpUo5hlbpg51QRiuqHAJz8+BrxE/N"
		crossorigin="anonymous"></script>

	<!-- custom js -->
	<script src="${contextPath}/resources/js/script.js"></script>
	
</body>

