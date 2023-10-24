<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- intercepter에서 set한 회원정보에 따른 조회값  get -->
<c:set var="cartCount" value="${sessionScope.cartCount}" />
<c:set var="deliveringCount" value="${sessionScope.deliveringCount}" />
<c:set var="sun_money" value="${sessionScope.sun_money}" />
<!-- intercepter에서 set한 회원정보에 따른 조회값  get -->

<div class="container">
	<div class="row">

<!-- count 값 표시 -->
		<div
			class="d-flex mypageLayout align-content-stretch p-0 position-relative">
			<div
				class="bg-main mypageBox d-flex fw-light align-items-center text-center box-sixing-content border-top border-end border-primary-subtle box-sixing-border"
				style="width: 150px">
				<p class="w-100 mb-0 fs-3">MY농장</p>
			</div>
			<div
				class="bg-main mypageBox fw-light d-flex flex-column align-items-center ps-4 pt-4 border-end border-primary-subtle"
				style="width: 150px">
				<p class="w-100 mb-2 small">배송중</p>
				<p class="w-100 mb-0">

				<!-- 배송중 count -->
					<span class="fw-light me-2 lh-1" style="font-size: 2.2rem;">${deliveringCount }</span>개
				<!-- 배송중 count -->
				
				</p>
			</div>
			<div
				class="bg-mainColorLight mypageBox fw-light d-flex flex-column align-items-center ps-4 pt-4 border-end border-primary-subtle"
				style="width: 150px">
				<p class="w-100 mb-2 small">장바구니</p>
				<p class="w-100 mb-0">
				
				<!-- 장바구니 count -->
					<span class="fw-light me-2 lh-1" style="font-size: 2.2rem;">${cartCount }</span>개
				<!-- 장바구니 count -->
				
				</p>
			</div>

			<div
				class="bg-mainColorLight mypageBox fw-light d-flex flex-fill flex-column align-items-center pe-4 pt-4 border-end border-primary-subtle text-end"
				style="width: 150px">
				<p class="w-100 mb-2 small">썬머니</p>
				<p class="w-100 mb-0">
				
				<!-- 썬머니 count -->
					<span class="fw-light me-2 lh-1" style="font-size: 2.2rem;">
					<fmt:formatNumber value="${sun_money}" pattern="#,###" /></span>원
				<!-- 썬머니 count -->
					
				</p>
			</div>


			<!-- left 메뉴 -->
			<div
				class="position-absolute top-100 start-0 bg-light px-3 py-4 d-flex flex-column border secondary-subtle"
				style="width: 150px;">
				<p class="fw-bold text-black mb-2">MY농장</p>
				<a href="${contextPath}/mypage/listMyOrderHistory.do"
					class="text-decoration-none mb-2 small sideMenu listMyOrderHistory">주문목록</a>
				<a href="${contextPath}/cart/myCartList.do"
					class="text-decoration-none mb-2 small sideMenu myCartList">장바구니</a>
				<a href="${contextPath}/mypage/myDetailInfo.do"
					class="text-decoration-none mb-0 small sideMenu myDetailInfo">내정보</a>
			</div>
			<!-- left 메뉴 -->
			
		</div>
<!-- count 값 표시 -->

	</div>
</div>