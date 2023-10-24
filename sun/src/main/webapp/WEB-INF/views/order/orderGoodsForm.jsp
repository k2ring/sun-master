<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- 주문자 휴대폰 번호 -->
<c:set var="orderer_hp" value="" />
<!-- 최종 결제 금액 -->
<c:set var="final_total_order_price" value="0" />

<!-- 총주문 금액 -->
<c:set var="total_order_price" value="0" />
<!-- 총 상품수 -->
<c:set var="total_order_goods_qty" value="0" />
<!-- 총할인금액 -->
<c:set var="total_discount_price" value="0" />
<!-- 총 배송비 -->
<c:set var="total_delivery_price" value="0" />

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>


<div class="container">
	<div class="row ms-5 ps-5">
		<div class="mt-5 p-0 ps-5 align-items-center">
			<form name="form_order">
				<div class="ps-4">
					<p class="fs-5 fw-bold mb-3">주문하기</p>
					<div class="border-top border-main border-2 mt-2">
						<div class="shadow-sm p-4 pt-2 mt-3 rounded border border-light">

							<!-- myOrderList를 돌려 각 주문상품 정보를 표시한다. -->
							<c:forEach var="item" items="${myOrderList}">

								<a
									href="${contextPath}/goods/goodsDetail.do?goods_id=${item.goods_id }"
									class="shadow-sm p-0 mt-3 rounded border border-light d-flex justify-content-between text-decoration-none">
									<div class="d-flex">
										<div class="d-flex align-items-center p-4">
											<div class="d-flex justify-content-between">
												<div class="d-flex">

													<!-- 상품이미지 -->
													<img
														src="${contextPath}/thumbnails.do?goods_id=${item.goods_id}&fileName=${item.goods_fileName}"
														width="64px">
													<!-- 상품이미지 -->

													<!-- 상품id와 filename등 사용자에게 보여주지않아도 되는 정보들은 hidden type으로 넘긴다. -->
													<input type="hidden" id="h_goods_id" name="h_goods_id"
														value="${item.goods_id}" /> <input type="hidden"
														id="h_goods_fileName" name="h_goods_fileName"
														value="${item.goods_fileName}" />

													<div class="ms-3">

														<!-- 상품이름 -->
														<p class="mb-1 mt-1 small">${item.goods_title }</p>
														<input type="hidden" id="h_goods_title"
															name="h_goods_title" value="${item.goods_title }" />
														<!-- 상품이름 -->

														<!-- 상품가격 표시-->
														<p class="mb-0 text-secondary">
															<!-- 상품 낱개가격 -->
															<span>
															<fmt:formatNumber value="${item.goods_price}" pattern="#,###" />
															</span> 원 <span> ·
															</span> <input type="hidden" id="h_each_goods_price"
																name="h_each_goods_price"
																value="${item.goods_price * item.order_goods_qty}" />
															<!-- 상품 낱개가격 -->

															<!-- 상품수량 -->
															<span>${item.order_goods_qty }</span>개 <input
																type="hidden" id="h_order_goods_qty"
																name="h_order_goods_qty" value="${item.order_goods_qty}" />
															<!-- 상품수량 -->

															<!-- 상품별 총 가격: 낱개가격*수량, 필요없는 정보라고 생각하여 주석처리, 추후 필요할 경우 사용한다. -->
															<%-- = ${item.goods_price * item.order_goods_qty} 원 --%>
															<!-- 상품별 총 가격: 낱개가격*수량, 필요없는 정보라고 생각하여 주석처리, 추후 필요할 경우 사용한다. -->

														</p>
														<!-- 상품가격 표시 -->

													</div>
												</div>
											</div>
										</div>
									</div>
								</a>

								<!-- 상품 가격정보 -->
								<c:set var="final_total_order_price"
									value="${final_total_order_price+ item.goods_price* item.order_goods_qty}" />
								<c:set var="total_order_price"
									value="${total_order_price+ item.goods_price* item.order_goods_qty}" />
								<c:set var="total_order_goods_qty"
									value="${total_order_goods_qty+item.order_goods_qty }" />
								<!-- 상품 가격정보 -->

							</c:forEach>
							<!-- myOrderList를 돌려 각 주문상품 정보를 표시한다. -->


							<!-- 주문상품 총 금액 계산 -->
							<p
								class="bg-light border text-end p-3 text-secondary d-flex justify-content-end align-items-center mt-4">
								<span> 
								
								<!-- 총 상품가격 --> 
								<span>총 상품가격 <span id="p_totalPrice">
								<fmt:formatNumber value="${total_order_price}" pattern="#,###" />
								</span>원</span> 
								<input id="h_totalPrice" type="hidden" value="${total_order_price}" /> 
								<!-- 총 상품가격 --> 
								
								<!-- 총 배송비 -->
									<span>+</span> <span>총 배송비 0원</span> 
								<!-- 총 배송비 --> 
								
								<!-- 총 주문금액 -->
									<span>=</span> 총 주문금액</span> <span class="text-black fw-bold fs-5 ms-3">
									<span id="p_final_totalPrice">
									<fmt:formatNumber value="${final_total_order_price }" pattern="#,###" />
									
									</span>원</span> 
									<input id="h_final_total_Price" type="hidden" value="${final_total_order_price}" />
								<!-- 총 주문금액 -->

							</p>
							<!-- 주문상품 총 금액 계산 -->
							
							
						</div>
					</div>

					<p class="mt-5 mb-3 fw-bold">배송지</p>

					<div class="table-responsive">
					
					<!-- 기본 회원정보를 valuㄷ로 지정한다. -->
						<table class="table border-top mb-0 small fw-light">
							<tbody>

							<!-- 이름 -->
								<tr>
									<td class="table-light ps-4 align-middle" style="width: 200px;">이름</td>
									<td class="px-4"><input class="form-control rounded-0" required
										id="receiver_name" name="receiver_name" type="text" size="40"
										value="${orderer.member_name }" style="width: 300px;">
										<input type="hidden" id="h_orderer_name" name="h_orderer_name"
										value="${orderer.member_name }" /> <input type="hidden"
										id="h_receiver_name" name="h_receiver_name"
										value="${orderer.member_name }" /></td>
								</tr>
								<!-- 이름 -->


								<!-- 휴대폰 -->
								<tr>
									<td class="table-light ps-4 align-middle" style="width: 200px;">휴대폰
										번호</td>
									<td class="px-4"><input class="form-control rounded-0" required
										type="text" id="hp1" name="hp1" value="${orderer.hp1 }"
										style="width: 300px;"> <input type="hidden" id="h_hp1"
										name="h_hp1" value="${orderer.hp1 }" /> <c:set
											var="orderer_hp" value="${orderer.hp1}" /></td>
								</tr>
								<!-- 휴대폰 -->

								<!-- 배송지 -->
								<tr>
									<td class="table-light ps-4 align-middle" style="width: 200px;">배송지</td>
									<td class="px-4">
										<div class="input-group mb-2" style="width: 395px;">
											<input class="form-control rounded-0" type="text"
												id="zipcode" name="zipcode" size="5"
												value="${orderer.zipcode}" aria-describedby="button-addon2" required>
											<a class="btn border-main small rounded-0 samll"
												type="button" id="button-addon2"
												href="javascript:execDaumPostcode()">우편번호 검색</a>
										</div> <input class="form-control rounded-0 mb-2" type="text"
										id="member_address" name="member_address" size="50"
										value="${orderer.member_address }" required> <input
										class="form-control rounded-0" type="text" id="subaddress"
										name="subaddress" size="50" value="${orderer.subaddress }" required>

										<input type="hidden" id="h_zipcode" name="h_zipcode"
										value="${orderer.zipcode }"  /> <input type="hidden"
										id="h_member_address" name="h_zipcode"
										value="${orderer.member_address }" /> <input type="hidden"
										id="h_subaddress" name="h_zipcode"
										value="${orderer.subaddress }" />
									</td>
								</tr>
								<!-- 배송지 -->
								
							</tbody>
						</table>
						<!-- 기본 회원정보를 valuㄷ로 지정한다. -->

					</div>

					<!-- 결제방법 선택 -->
					<p class="mt-5 mb-3 fw-bold">결제정보</p>
					<table class="table border-top mb-0 small fw-light">
						<tbody>
							<tr>
								<td class="table-light ps-4 align-middle" style="width: 200px;">
									결제방법</td>
								<td class="">
								
								<div class="row px-4">
								
								<div class="form-check col-3">
								  <input class="form-check-input" type="radio" name="pay_method" id="pay1" value="신용카드" checked>
								  <label class="form-check-label" for="pay1">
								    신용카드
								  </label>
								</div>
								
								<!-- <div class="form-check col-3">
								  <input class="form-check-input" type="radio" name="pay_method" id="pay2" value="제휴 신용카드">
								  <label class="form-check-label" for="pay2">
								    제휴 신용카드
								  </label>
								</div> -->
								
								<!-- <div class="form-check col-3">
								  <input class="form-check-input" type="radio" name="pay_method" id="pay3" value="실시간 계좌이체">
								  <label class="form-check-label" for="pay3">
								    실시간 계좌이체
								  </label>
								</div> -->
								
								<!-- <div class="form-check col-3">
								  <input class="form-check-input" type="radio" name="pay_method" id="pay4" value="무통장 입금">
								  <label class="form-check-label" for="pay4">
								    무통장 입금
								  </label>
								</div> -->
								
								<div class="form-check col-3">
								  <input class="form-check-input" type="radio" name="pay_method" id="pay5" value="휴대폰결제">
								  <label class="form-check-label" for="pay5">
								    휴대폰결제
								  </label>
								</div>

								<div class="form-check col-3">
									<input class="form-check-input" type="radio" name="pay_method" id="pay6" value="카카오페이(간편결제)">
									<label class="form-check-label" for="pay6">
									  카카오페이(간편결제)
									</label>
								  </div>
								  
								  <div class="form-check col-3">
									<input class="form-check-input" type="radio" name="pay_method" id="pay7" value="네이버페이(카드)">
									<label class="form-check-label" for="pay7">
									  네이버페이(카드)
									</label>
								  </div>

								  <div class="form-check col-3">
									<input class="form-check-input" type="radio" name="pay_method" id="pay8" value="네이버페이(포인트)">
									<label class="form-check-label" for="pay8">
									  네이버페이(포인트)
									</label>
								  </div>


								 <!--  <div class="form-check col-3">
									<input class="form-check-input" type="radio" name="pay_method" id="pay9" value="직접입금">
									<label class="form-check-label" for="pay9">
									  직접입금
									</label>
								  </div> -->
								
								</div>
								</td>
							</tr>
							
							
							<!-- 카드결제 form -->
							<tr class="whenSelected_Card">
								<td class="table-light ps-4 align-middle" style="width: 200px;">
								<p class="mb-2">카드결제정보</p>
								<p class="text-danger small cardFormError mb-0 d-none"><span class="fw-bold">*입력정보를 확인해주세요</span><br>
								유효기간과(월/년), 비밀번호는 2자리 숫자로, <br>
								생년월일은 6자리 숫자로 입력하셔야합니다.
								</p>
								</td>
								<td class="px-4">
									<div class="d-flex align-items-center mb-2">
										<div style="width:100px"><p class="mb-0">카드사 선택</p></div>
										<div>
											<select id="" name="" class="form-select rounded-0" style="width: 300px;" onchange="selectValue(this, this.value)">
												<option value="삼성" selected>삼성</option>
												<option value="하나SK">하나SK</option>
												<option value="현대">현대</option>
												<option value="KB">KB</option>
												<option value="신한">신한</option>
												<option value="롯데">롯데</option>
												<option value="BC">BC</option>
												<option value="시티">시티</option>
												<option value="NH농협">NH농협</option>
										</select>
										<input type="hidden" id="card_com_name" name="card_com_name" value="삼성">
										</div>
									</div>

									<div class="d-flex align-items-center mb-2">
										<div style="width:100px"><p class="mb-0">할부기간</p>
										</div>
										<div>
											<select id="" name="" class="form-select rounded-0" style="width: 300px;" onchange="selectValue(this, this.value)">
												<option value="0" selected>일시불</option>
												<option value="2">2개월</option>
												<option value="3">3개월</option>
												<option value="4">4개월</option>
												<option value="5">5개월</option>
												<option value="6">6개월</option>
										</select>
										<input type="hidden" id="card_pay_month" name="card_pay_month" value="0">
										</div>
									</div>


									<div class="d-flex align-items-center mb-2">
										<div style="width: 100px">
											<p class="mb-0">카드번호</p>
										</div>
										<div>
											<input type="text" id="cardNum" name="cardNum"
												class="form-control rounded-0" style="width: 300px;"
												value="" placeholder="-를 제외하고 적어주세요" required/>
										</div>
									</div>
									
									<div class="d-flex align-items-center mb-2">
										<div style="width: 100px">
											<p class="mb-0">유효기간</p>
										</div>
										<div class="input-group"  style="width: 300px;">
										<div>
										
										</div>
											<input type="text" id="cardDate_month" name="cardDate_month"
												class="form-control rounded-0" style="width: 50px;"
												value="" placeholder="00(월)" onBlur="checkLength(this, 2);" maxlength="2"/>
												<span class="py-1 px-3"> / </span>
												<input type="text" id="cardDate_year" name="cardDate_year"
												class="form-control rounded-0" style="width: 50px;"
												value="" placeholder="00(년)" onBlur="checkLength(this, 2);" maxlength="2"/>
										</div>
									</div>
									
									<div class="d-flex align-items-center mb-2">
										<div style="width: 100px">
											<p class="mb-0">생년월일</p>
										</div>
										<div>
											<input type="text" id="birth" name="birth"
												class="form-control rounded-0" style="width: 300px;"
												value=""  placeholder="6자리로 작성해주세요 예) 980314"
												 onBlur="checkLength(this, 6)" maxlength="6"/>
										</div>
									</div>
									
									<div class="d-flex align-items-center mb-2">
										<div style="width: 100px">
											<p class="mb-0">비밀번호</p>
										</div>
										<div>
											<input type="text" id="cardPassword" name="cardPassword"
												class="form-control rounded-0" style="width: 300px;"
												value="" placeholder="비밀번호 앞 2자리"
												 onBlur="checkLength(this, 2)" maxlength="2" required/>
										</div>
									</div>
									
								</td>
							</tr>
							<!-- 카드결제 form -->
							
							
							<!-- 휴대폰결제 form -->
							<tr class="whenSelected_Phone d-none">
								<td class="table-light ps-4 align-middle" style="width: 200px;">휴대폰 번호</td>
								<td class="px-4">
								<input class="form-control rounded-0" type="text" id="pay_orderer_hp_num" name="pay_order_tel" value="" placeholder="-없이 작성해주세요: 01000000000" style="width: 300px;">
								</td>
							</tr>
							<!-- 휴대폰결제 form -->
							
						</tbody>
					</table>
					
					<!-- 결제방법 선택 -->
					
					
					<!-- 결제하기-->
					<a name="btn_process_pay_order" onClick="fn_process_pay_order()"
						class="btn btn-lg btn-main rounded-0 w-100 d-block fw-bold p-2 lh-lg mt-5 mb-2">결제하기</a>
						
					<!-- 결제하기-->

					<!-- 취소하기, 메인페이지로 돌아간다.-->
					<a href="${contextPath}/main/main.do"
						class="btn btn-lg border-main rounded-0 w-100 d-block fw-bold p-2 lh-lg mt-0 mb-3 fs-6">취소하기</a>
					<!-- 취소하기, 메인페이지로 돌아간다.-->
					
				</div>

			</form>
		</div>
	</div>
</div>




<form name="order_info" method="post" accept-charset="euc-kr">
<input type="hidden" name="ordr_idxx" value="${ordr_idxx }">
<input type="hidden" name="good_name" value="${ good_name }">
<input type="hidden" name="good_mny" value="${ good_mny }">
<input type="hidden" name="buyr_name" value="${ buyr_name }">
<input type="hidden" name="site_cd" value="${ site_cd }">
<!-- 고정값 --> 
<input type="hidden" name="req_tx" value="pay">
<input type="hidden" name="pay_method" value="100000000000"/>
<input type="hidden" name="currency" value="410">

<!-- 결제수단 값  -->
<input type="hidden" name="kakaopay_direct" value="" class="easyPayment_method">
<input type="hidden" name="naverpay_direct" value="" class="easyPayment_method">
<input type="hidden" name="naverpay_point_direct" value="" class="easyPayment_method">
<!-- 결제수단 값  -->

<input type="hidden" name="module_type" value="01"/>
<!-- 주문정보 검증 관련 정보 : 표준웹 에서 설정하는 정보입니다 -->
<input type="hidden" name="ordr_chk" value=""/>
<!-- 추가파라미터(가맹점에서 별도의 값 전달시 param_opt를 사용하여 값 전달 -->
<input type="hidden" name="param_opt_1" value="">
<input type="hidden" name="param_opt_2" value="">
<input type="hidden" name="param_opt_3" value="">

<!-- ※ 필 수
필수 항목 : 표준웹에서 값을 설정하는 부분으로 반드시 포함되어야 합니다
값을 설정하지 마십시오 -->

<input type="hidden" name="res_cd" value=""/>
<input type="hidden" name="res_msg" value=""/>
<input type="hidden" name="enc_info" value=""/>
<input type="hidden" name="enc_data" value=""/>
<input type="hidden" name="ret_pay_method" value=""/>
<input type="hidden" name="tran_cd" value=""/>
<input type="hidden" name="use_pay_method" value=""/>
<input type="hidden" name="card_pay_method" value=""/>
</form>
<script src="${contextPath}/resources/js/order.js"></script>	
<script type="text/javascript" src="https://pay.kcp.co.kr/plugin/payplus_web.jsp"></script>

