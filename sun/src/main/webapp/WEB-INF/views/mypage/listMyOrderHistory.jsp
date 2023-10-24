<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="container">
	<div class="row ms-5 ps-5">
		<div class="mt-5 p-0 ps-5 align-items-center">
			<div class="ps-4">

				<p class="fs-5 fw-bold mb-2">주문목록</p>
				<form method="post">

					<!-- 검색 - 오늘 -->
					<a href="javascript:search_order_history('today')"
						class="badge rounded-pill btn mb-2 rounded-0 border-main samll ">오늘</a>
					<!-- 검색 - 1개월 -->
					<a href="javascript:search_order_history('one_month')"
						name="one_month"
						class="badge rounded-pill btn mb-2 rounded-0 border-main samll active">최근
						1개월</a>
					<!-- 검색 - 2개월 -->
					<a href="javascript:search_order_history('two_month')"
						class="badge rounded-pill btn mb-2 rounded-0 border-main samll two_month">최근
						2개월</a>
					<!-- 검색 - 3개월 -->
					<a href="javascript:search_order_history('three_month')"
						class="badge rounded-pill btn mb-2 rounded-0 border-main samll three_month">최근
						3개월 </a>
					<!-- 검색 - 6개월 -->
					<a href="javascript:search_order_history('six_month')"
						class="badge rounded-pill btn mb-2 rounded-0 border-main samll six_month">최근
						6개월</a>

					<!-- set된 조회할 기간 -->
					<div class="d-none">
						조회한 기간:<input type="text" size="4" value="${beginYear}" />년 <input
							type="text" size="4" value="${beginMonth}" />월 <input
							type="text" size="4" value="${beginDay}" />일 &nbsp; ~ <input
							type="text" size="4" value="${endYear}" />년 <input type="text"
							size="4" value="${endMonth}" />월 <input type="text" size="4"
							value="${endDay}" />일
					</div>
					<!-- set된 조회할 기간 -->

				</form>

				<!-- 주문정보 표시 영역 -->
				<div class="border-top border-main border-2 mt-2">

					<c:choose>
					
						<c:when test="${empty myOrderHistList }">
							<!-- 주문상품이 없을때 -->
							<div class="shadow-sm p-4 mt-3 rounded border border-light">
								<p class="my-5 text-center">주문한 상품이 없습니다.</p>
							</div>
							<!-- 주문상품이 없을때 -->
						</c:when>

						<c:otherwise>
						<!-- 주문상품이 있을때 for문 -->
							<c:forEach var="item" items="${myOrderHistList }" varStatus="i">
							
								<!-- 주문상품 -->
								<c:choose>
									<c:when test="${item.order_id != pre_order_id }">
										<c:set var="pre_order_id" value="${item.order_id }" />
										<div class="shadow-sm p-4 mt-3 rounded border border-light">

											<!-- 주문일자/시간 정보  -->
											<p class="fw-bold mb-0">${item.pay_order_time }주문</p>
											<!-- 주문일자/시간 정보  -->

											<div
												class="shadow-sm p-4 mt-3 rounded border border-light d-flex justify-content-between">
												<div class="d-flex flex-column gap-4">
													<p class="text-secondary fw-bold mb-0">
														<!-- 배송정보에 따른 표시 -->
														<c:choose>
															<c:when
																test="${item.delivery_state=='delivery_prepared' }">배송준비중</c:when>
															<c:when test="${item.delivery_state=='delivering' }">배송중</c:when>
															<c:when
																test="${item.delivery_state=='finished_delivering' }">배송완료</c:when>
															<c:when test="${item.delivery_state=='cancel_order' }">주문취소</c:when>
															<c:when test="${item.delivery_state=='returning_goods' }">반품중</c:when>
															<c:when test="${item.delivery_state=='exchange_goods' }">교환중</c:when>
														</c:choose>
														<!-- 배송정보에 따른 표시 -->
													</p>

													<c:forEach var="item2" items="${myOrderHistList}"
														varStatus="j">
														<c:if test="${item.order_id ==item2.order_id}">
															<div class="d-flex">
																<!-- 상품이미지 -->
																<img
																	src="${contextPath}/thumbnails.do?goods_id=${item2.goods_id}&fileName=${item2.goods_fileName}"
																	style="width: 64px; height: 64px">
																<!-- 상품이미지 -->

																<div class="ms-3">
																	<p class="mb-1 mt-1 small">

																		<!-- 상품명 -->
																		<a class="text-decoration-none"
																			href="${contextPath}/goods/goodsDetail.do?goods_id=${item2.goods_id }">${item2.goods_title}</a>
																		<!-- 상품명 -->

																	</p>
																	<p class="mb-0 text-secondary">

																		<!-- 주문갯수 -->
																		<span>${item2.order_goods_qty}</span>개
																		<span> · </span> <span> 
																		
																		<!-- 상품가격 * 갯수 : 총 구매가격 -->
																		<fmt:formatNumber value="${item2.goods_price*item2.order_goods_qty}" pattern="#,###" />
																			</span> 원
																	</p>
																</div>
															</div>
														</c:if>
													</c:forEach>
												</div>
												
												
												<!-- 주문취소, 교환, 반품, 배송중에는 사용자가  배송관련 정보를 수정할 수없다. -->
												<c:choose>
												
													
													<c:when test="${item.delivery_state=='cancel_order'}">
													</c:when>
													<c:when test="${item.delivery_state=='returning_goods'}">
													</c:when>
													<c:when test="${item.delivery_state=='exchange_goods'}">
													</c:when>
													<c:when test="${item.delivery_state=='delivering'}">
													</c:when>
													
													<c:otherwise>
														<div
															class="border-start ps-4 align-self-center align-self-stretch d-flex align-items-center">
															<div>
																<c:choose>

																	<c:when
																		test="${item.delivery_state=='delivery_prepared'}">
																		<!-- 배송준비완료일때 -->
																		<button
																			class="btn btn-sm border-main rounded-0 small d-block my-2"
																			onClick="fn_edit_order('${item.order_id}','cancel')"
																			style="width: 150px;">주문취소</button>
																		<!-- 배송준비완료일때 -->
																	</c:when>

																	<c:when
																		test="${item.delivery_state=='finished_delivering' }">
																		<!-- 배송완료 일때 -->
																		<button
																			class="btn btn-sm border-main rounded-0 small d-block my-2"
																			onClick="fn_edit_order('${item.order_id}', 'exchange')"
																			style="width: 150px;">교환신청</button>
																		<button
																			class="btn btn-sm border-main rounded-0 small d-block my-2"
																			onClick="fn_edit_order('${item.order_id}','return')"
																			style="width: 150px;">반품신청</button>
																			<!-- 배송완료 일때 -->
																	</c:when>

																</c:choose>
															</div>
														</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</c:when>
								</c:choose>
								<!-- 주문상품 -->
								
							</c:forEach>
						<!-- 주문상품이 있을때 for문 -->
						</c:otherwise>
						
					</c:choose>

				</div>
				<!-- 주문정보 표시 영역 -->

			</div>
		</div>
	</div>
</div>


<script>
			
			//기간별 조회, fixedSearchPeriod값을 가지고 submit
			function search_order_history(fixedSearchPeriod) {
				var formObj = document.createElement("form");
				var i_fixedSearch_period = document.createElement("input");
				i_fixedSearch_period.name = "fixedSearchPeriod";
				i_fixedSearch_period.value = fixedSearchPeriod;
				formObj.appendChild(i_fixedSearch_period);
				document.body.appendChild(formObj);
				formObj.method = "get";
				formObj.action = "${contextPath}/mypage/listMyOrderHistory.do";
				formObj.submit();
			}
			
			//주문수정, option값에 따라 if문을 통해 submit한다.
			function fn_edit_order(order_id, option){
				var formObj=document.createElement("form");
				var i_order_id = document.createElement("input"); 
				 	i_order_id.name="order_id";
				    i_order_id.value=order_id;
					
				    formObj.appendChild(i_order_id);
				    document.body.appendChild(formObj); 
				    formObj.method="post";
				   
				    //취소
				    if(option == "cancel"){
				    	var answer=confirm("주문을 취소하시겠습니까?");
						if(answer==true){
							formObj.action="${contextPath}/mypage/cancelMyOrder.do";
							formObj.submit();
						}
				    }else if(option == "return"){
				    //반품
				    	var answer=confirm("반품신청 하시겠습니까?");
						if(answer==true){
				    	formObj.action="${contextPath}/mypage/returnMyOrder.do";
				    	formObj.submit();
						}
				    }else if(option == "exchange"){
				    //교환
				    	var answer=confirm("교환신청 하시겠습니까?");
						if(answer==true){
				    	formObj.action="${contextPath}/mypage/exchangeMyOrder.do";
				    	formObj.submit();
						}
				    }
				    	
				
			}
			
			//url에 따라서 버튼의 style을 변경
			if (window.location.href.includes("fixedSearchPeriod")) {
				//모든 뱃지 style 초기화
				const badges = document.querySelectorAll(".badge");
				for (b of badges){ b.classList.remove("active");}
				
				//url에 표시된 txt에 따른 style 변경
				if (window.location.href.includes("today")) {
					badges[0].classList.add("active");
				} else if (window.location.href.includes("one_month")) {
					badges[1].classList.add("active");
				}else if (window.location.href.includes("two_month")) {
					badges[2].classList.add("active");
				}else if (window.location.href.includes("three_month")) {
					badges[3].classList.add("active");
				}else if (window.location.href.includes("six_month")) {
					badges[4].classList.add("active");
				}
			
			}
			
		</script>