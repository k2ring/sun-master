<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />



<!-- order_goods_list에 데이터가 있을때 -->
<c:choose>
	<c:when test='${not empty order_goods_list}'>
		<script type="text/javascript">
window.onload=function(){init();}

//각각의 주문건에 대한 배송 상태를 표시한다.
function init(){
	var frm_delivery_list=document.frm_delivery_list;
	var h_delivery_state=frm_delivery_list.h_delivery_state;
	var s_delivery_state=frm_delivery_list.s_delivery_state;
	
	//조회된 주문 정보가 1건인 경우
	if(h_delivery_state.length==undefined){
		s_delivery_state.value=h_delivery_state.value;
	}else{
		//조회된 주문 정보가 여러건인 경우
		for(var i=0; s_delivery_state.length;i++){
			s_delivery_state[i].value=h_delivery_state[i].value;
		}
	}
}
</script>
	</c:when>
</c:choose>
<!-- order_goods_list에 데이터가 있을때 -->

<div class="container">
	<div class="row ms-5 ps-5">
		<div class="mt-5 p-0 ps-5 align-items-center">
			<div class="ps-4">
				<p class="fs-5 fw-bold mb-2">주문관리</p>
				<div class="d-flex align-items-center gap-1 justify-content-between">
					<div>

						<!-- 검색 - 오늘 -->
						<a href="javascript:search_order_history('today')"
							class="badge rounded-pill btn mb-2 rounded-0 border-main samll ">오늘</a>

						<!-- 검색 - 1개월 -->
						<a href="javascript:search_order_history('one_month')"
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

					</div>
				</div>


				<!-- 주문정보 표시 영역 -->
				<div class="border-top border-main border-2 mt-2"></div>

				<div class="table-responsive mt-4">
					<table
						class="table border-top table-striped mb-0 small fw-light border-bottom">
						<tbody>

							<!-- thead -->
							<tr>
								<td
									class="table-light p-2 align-middle fw-bold border-end text-center samll"
									style="width: 45px;">주문번호</td>
								<td
									class="table-light ps-4 align-middle fw-bold text-center border-end">주문정보</td>
								<td
									class="table-light ps-4 align-middle fw-bold text-center border-end">배송정보</td>
								<td
									class="table-light ps-4 align-middle fw-bold text-center border-end">주문날짜</td>
								<td class="table-light text-center px-4 align-middle"
									style="width: 114px;">-</td>
							</tr>
							<!-- thead -->


							<c:choose>
								<c:when test="${empty newOrderList}">
									<!-- 조회된 주문이 없을경우 -->
									<tr>
										<td colspan="5">
											<div class="p-4">
												<p class="my-5 text-center">조회된 주문이 없습니다.</p>
											</div>
										</td>
									</tr>
									<!-- 조회된 주문이 없을경우 -->
								</c:when>

								<c:otherwise>

									<!-- 주문리스트, newOrderList for문 -->
									<c:forEach var="item" items="${newOrderList}" varStatus="i">
										<c:choose>

											<c:when test="${item.order_id != pre_order_id }">
												<form name="frm_delivery_list">
													<tr>

														<!-- 주문 id -->
														<td
															class="table-light align-middle border-end text-center">${item.order_id}</td>
														<!-- 주문 id -->

														<td class="border-end align-middle">
															<!-- 회원 id -->
															<div class="d-flex mb-1 align-items-center">
																<span style="width: 110px;" class="">주문회원</span> <input
																	class="form-control rounded-0" type="text"
																	placeholder="회원이름" value="${item.member_id}" readonly>
															</div> <!-- 회원 id --> <!-- 수령자 이름 -->
															<div class="d-flex mb-1 align-items-center">
																<span style="width: 110px;" class="">수령자</span> <input
																	class="form-control rounded-0" type="text"
																	placeholder="수령자" value="${item.receiver_name}"
																	readonly>
															</div> <!-- 수령자 이름 --> <!-- 배송 연락처 -->
															<div class="d-flex mb-1 align-items-center">
																<span style="width: 110px;" class="">배송연락처</span> <input
																	class="form-control rounded-0" type="text"
																	placeholder="010-0000-0000"
																	value="${item.receiver_hp1}" readonly>
															</div> <!-- 배송 연락처 -->

														</td>

														<td class="border-end align-middle">
															<!-- 상품이름 -->
															<div class="d-flex mb-1 align-items-center">
																<span style="width: 100px;" class="">주문상품</span> <input
																	class="form-control rounded-0" type="text"
																	placeholder="주문상품" value="${item.goods_title}" readonly>
															</div> <!-- 상품이름 --> <!-- 주문수량 -->
															<div class="d-flex mb-1 align-items-center">
																<span style="width: 100px;" class="">주문수량</span> <input
																	class="form-control rounded-0" type="text"
																	placeholder="주문상품" value="${item.order_goods_qty}"
																	readonly>
															</div> <!-- 주문수량 --> <!-- 다중 주문일 경우 --> <c:forEach var="item2"
																items="${newOrderList}" varStatus="j">
																<!-- i.index보다 j.index의 수가 크다면  -->
																<c:if test="${j.index > i.index }">
																	<!-- 그리고 주문id가 동일하다면 -->
																	<c:if test="${item.order_id == item2.order_id}">

																		<!-- 구분선 -->
																		<hr class="border-secondary">
																		<!-- 구분선 -->

																		<div class="d-flex mb-1 align-items-center">
																			<span style="width: 103px;" class="">주문상품</span>

																			<!-- 상품이름 -->
																			<input class="form-control rounded-0" type="text"
																				placeholder="주문상품" value="${item2.goods_title}">
																			<!-- 상품이름 -->

																		</div>
																		<div class="d-flex mb-1 align-items-center">
																			<span style="width: 100px;" class="">주문수량</span>

																			<!-- 주문수량 -->
																			<input class="form-control rounded-0" type="text"
																				placeholder="주문상품" value="${item2.order_goods_qty}">
																			<!-- 주문수량 -->

																		</div>
																	</c:if>
																</c:if>
															</c:forEach> <!-- 다중 주문일 경우 -->

															<div class="d-flex mb-0 align-items-center">

																<!-- 주문상태 표시 -->
																<span style="width: 100px;" class="">주문상태</span>

																<!-- select부분과 option부분으로 나누어 표현해보자. -->
																<c:choose>
																	<c:when test="${item.order_id != pre_order_id }">
																		<c:choose>
																			<c:when
																				test="${item.delivery_state=='delivery_prepared' }">
																				<!-- 준비중일때 -->
																				<select name="s_delivery_state${i.index }"
																					id="s_delivery_state${i.index }"
																					onchange="selectValue(this, this.value)"
																					class="text-warning-emphasis bg-warning-subtle form-select rounded-0 flex-inherit text-start small border border-end text-center">
																			</c:when>

																			<c:when
																				test="${item.delivery_state=='finished_delivering' }">
																				<!-- 배송중일때 -->
																				<select name="s_delivery_state${i.index }"
																					id="s_delivery_state${i.index }"
																					onchange="selectValue(this, this.value)"
																					class="text-success bg-success-subtle form-select rounded-0 flex-inherit text-start small border border-end text-center">
																			</c:when>

																			<c:otherwise>
																				<!-- 외 -->
																				<select name="s_delivery_state${i.index }"
																					id="s_delivery_state${i.index }"
																					onchange="selectValue(this, this.value)"
																					class="text-danger bg-danger-subtle form-select rounded-0 flex-inherit text-start small border border-end text-center">
																			</c:otherwise>

																		</c:choose>

																		<c:choose>
																			<c:when
																				test="${item.delivery_state=='delivery_prepared' }">
																				<option value="delivery_prepared"
																					class="text-secondary" selected>배송준비중</option>
																				<option value="delivering">배송중</option>
																				<option value="finished_delivering">배송완료</option>
																				<option value="cancel_order">주문취소</option>
																				<option value="returning_goods">반품</option>
																			</c:when>

																			<c:when test="${item.delivery_state=='delivering' }">
																				<!-- 배송중 option 선택 -->
																				<option value="delivery_prepared">배송준비중</option>
																				<option value="delivering" selected>배송중</option>
																				<option value="finished_delivering">배송완료</option>
																				<option value="cancel_order">주문취소</option>
																				<option value="returning_goods">반품</option>
																			</c:when>
																			<c:when
																				test="${item.delivery_state=='finished_delivering' }">
																				<!-- 배송완료 option 선택 -->
																				<option value="delivery_prepared">배송준비중</option>
																				<option value="delivering">배송중</option>
																				<option value="finished_delivering" selected>배송완료</option>
																				<option value="cancel_order">주문취소</option>
																				<option value="returning_goods">반품</option>
																			</c:when>
																			<c:when
																				test="${item.delivery_state=='cancel_order' }">
																				<!-- 주문취소 option 선택 -->
																				<option value="delivery_prepared">배송준비중</option>
																				<option value="delivering">배송중</option>
																				<option value="finished_delivering">배송완료</option>
																				<option value="cancel_order" selected>주문취소</option>
																				<option value="returning_goods">반품</option>
																			</c:when>
																			<c:when
																				test="${item.delivery_state=='returning_goods' }">
																				<!-- 반품 option 선택 -->
																				<option value="delivery_prepared">배송준비중</option>
																				<option value="delivering">배송중</option>
																				<option value="finished_delivering">배송완료</option>
																				<option value="cancel_order">주문취소</option>
																				<option value="returning_goods" selected>반품</option>
																			</c:when>
																			<c:otherwise>
																				<!-- 주문오류 option 선택 -->
																				<option value="delivery_prepared">배송준비중</option>
																				<option value="주문오류" selected>주문오류</option>
																				<option value="delivering">배송중</option>
																				<option value="finished_delivering">배송완료</option>
																				<option value="cancel_order">주문취소</option>
																				<option value="returning_goods">반품</option>
																			</c:otherwise>
																		</c:choose>

																	</c:when>
																</c:choose>
																</select>


																<!-- 배송상태 hidden input -->
																<input type="hidden" id="delivery_state"
																	name="delivery_state" value="${item.delivery_state}">
																<!-- 배송상태 hidden input -->

																<!-- 주문상태 표시 -->

															</div>

														</td>

														<!-- 결제시간 -->
														<td class="border-end align-middle text-center">${item.pay_order_time }</td>
														<!-- 결제시간 -->

														<td class="text-center align-middle">
															<!-- 수정하기 --> <input
															class="w-100 btn border-main small rounded-0 samll mb-2"
															type="button" value="수정"
															onClick="fn_modify_order_state('${i.index }','${item.order_id}')" />
															<!-- 수정하기 -->

														</td>
													</tr>

												</form>
											</c:when>

										</c:choose>
										<c:set var="pre_order_id" value="${item.order_id }" />
									</c:forEach>
									<!-- 주문리스트, newOrderList for문 -->

								</c:otherwise>

							</c:choose>

						</tbody>
					</table>
				</div>
				<!-- 주문정보 표시 영역 -->



			</div>
		</div>
	</div>
</div>
<script>



//selectBox가 selected되면 그다음 요소 input에게 그 value가 반영된다.
function selectValue(selectBox, value){
	var input = selectBox.nextElementSibling
	input.setAttribute("value", value);
}


//주문수정 - 배송수정
//수정할 상품 index와 order_id를 가져온다.
function fn_modify_order_state(index, order_id){
	//해당 getElementsByName이 delivery_state인 input을 가져와
	var deliveryInputs=document.getElementsByName("delivery_state");
	//그 값 value을 저장해 수정 수행
	var delivery_state = deliveryInputs[index].value;

	$.ajax({
		type : "post",
		async : false, 
		url : "${contextPath}/admin/order/modifyDeliveryState.do",
		data : {
			order_id:order_id,
			delivery_state:delivery_state
		},
		success : function(data, textStatus) {
			if(data.trim()=='mod_success'){
				//수정완료
				alert("주문 정보를 수정했습니다.");
				location.href="${contextPath}/admin/order/adminOrderMain.do";
			}else if(data.trim()=='failed'){
				alert("다시 시도해 주세요.");	
			}
			
		},
		error : function(data, textStatus) {alert("에러가 발생했습니다."+data);},
		complete : function(data, textStatus) {}
	}); 	
}


//버튼식 상품조회
//onclick할때 같이 가져오는 fixedSearchPeriod값과함께 adminOrderMain 재요청
//해당 값은 자바소스를 거쳐 지정된 기간에 맞는 정보만 select되어 뿌려진다.
function search_order_history(fixedSearchPeriod) {
	var formObj = document.createElement("form");
	var i_fixedSearch_period = document.createElement("input");
	i_fixedSearch_period.name = "fixedSearchPeriod";
	i_fixedSearch_period.value = fixedSearchPeriod;
	formObj.appendChild(i_fixedSearch_period);
	document.body.appendChild(formObj);
	formObj.method = "get";
	formObj.action = "${contextPath}/admin/order/adminOrderMain.do";
	formObj.submit();
}

//버튼식 상품조회 badge url에 따른 style변경
if (window.location.href.includes("fixedSearchPeriod")) {
	const badges = document.querySelectorAll(".badge");
	for (b of badges){ 
		b.classList.remove("active");
		}
	//각 값이 url에 들어있을 경우 active된다.
	if (window.location.href.includes("today")) {badges[0].classList.add("active");
	} else if (window.location.href.includes("one_month")) {badges[1].classList.add("active");
	}else if (window.location.href.includes("two_month")) {badges[2].classList.add("active");
	}else if (window.location.href.includes("three_month")) {badges[3].classList.add("active");
	}else if (window.location.href.includes("six_month")) {badges[4].classList.add("active");
	}
}


</script>

