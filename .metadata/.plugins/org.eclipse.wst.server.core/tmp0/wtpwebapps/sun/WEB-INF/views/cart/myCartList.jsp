<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="myCartList" value="${cartMap.myCartList}" />
<c:set var="myGoodsList" value="${cartMap.myGoodsList}" />

<c:set var="totalGoodsNum" value="0" />
<!--주문 개수 -->
<c:set var="totalDeliveryPrice" value="0" />
<!-- 총 배송비 -->
<c:set var="totalDiscountedPrice" value="0" />
<!-- 총 할인금액 -->
<script>

//select박스가 체크되었을때 input에 반영함.
function selectValue(selectBox, value, goods_id, index){
	var input = selectBox.nextElementSibling
	input.setAttribute("value", value);
	modify_cart_qty(index, goods_id, value);
}


//장바구니 수정하기
function modify_cart_qty(index, goods_id, value){
	//수정할 cart_goods_qty의 값을 저장.
	var cart_goods_qty = Number(value);
	$.ajax({
		type : "post",
		async : false,
		url : "${contextPath}/cart/modifyCartQty.do",
		data : {
			goods_id:goods_id,
			cart_goods_qty:cart_goods_qty
		},
		success : function(data, textStatus) {
			if(data.trim()=='modify_success'){alert("수량을 변경했습니다!!");	
			}else{alert("다시 시도해 주세요!!");	}			
		},
		error : function(data, textStatus) {alert("에러가 발생했습니다."+data);},
		complete : function(data, textStatus) {			}
	});
}


//장바구니 삭제하기
function delete_cart_goods(cart_id){
	// 삭제할 cart_id값을 저장
	var cart_id=Number(cart_id);
	//formObj을 만들어 submit
	var formObj=document.createElement("form");
	var i_cart = document.createElement("input");
	i_cart.name="cart_id";
	i_cart.value=cart_id;
	formObj.appendChild(i_cart);
    document.body.appendChild(formObj); 
    formObj.method="post";
    formObj.action="${contextPath}/cart/removeCartGoods.do";
    formObj.submit();
}


//선택(전체)상품 주문하기
function fn_order_all_cart_goods(){
	var order_goods_qty;
	var order_goods_id;
	var objForm=document.frm_order_all_cart;
	var cart_goods_qty=objForm.cart_goods_qty; 
	var h_order_each_goods_qty=objForm.h_order_each_goods_qty;
	var checked_goods=objForm.checked_goods;
	
	var cartGood=document.getElementsByClassName("cartGood");
	var length=document.getElementsByClassName("cartGood").length;
	let checkLen = 0;
	if(length>1){
		//전체 체크박스를 돌려 체크됬을때만 걸러낸다.
		for(var i=0; i<length;i++){
			if(checked_goods[i].checked==true){
				checkLen++;
				console.log(checkLen);
				order_goods_id=checked_goods[i].value;
				order_goods_qty=cart_goods_qty[i].value;
				//console.log(order_goods_id+":"+order_goods_qty);
				//각 상품의 정보를 해당 숨겨진 hidden input에 넣어 submit
				cart_goods_qty[i].value=order_goods_id+":"+order_goods_qty;
			}
		}	
	}else if(length=1){//선택된 상품이 하나일때는 분기처리한다.
		if(cartGood[0].checked){
			checkLen++;
			order_goods_id=checked_goods.value;
			order_goods_qty=cart_goods_qty.value;
			//각 상품의 정보를 해당 숨겨진 hidden input에 넣어 submit
			cart_goods_qty.value=order_goods_id+":"+order_goods_qty;
		}
	}
	
	//체크된 상품이 있을 경우 위에 세팅한 값으로 주문.
	if(checkLen > 0){
		objForm.method="post";
	 	objForm.action="${contextPath}/order/orderAllCartGoods.do";
		objForm.submit();
		//사용자가 뒤로가기 한뒤 다시 주문할때를 대비해 input 값을 되돌려놓음.
	 	cart_goods_qty[i].value=cart_goods_qty[i].previousElementSibling.value;
		
	}else {alert("원하시는 상품을 선택해주세요!");}//체크된 상품이 없을 경우 submit하지않고 alert만 표시.
	
}


//개별 주문하기
function fn_order_each_goods(goods_id,goods_title,goods_price,fileName){
	var total_price,final_total_price,_goods_qty;
	var cart_goods_qty=document.getElementById("cart_goods_qty");
	
	_order_goods_qty=cart_goods_qty.value;
	var formObj=document.createElement("form");
	var i_goods_id = document.createElement("input"); 
    var i_goods_title = document.createElement("input");
    var goods_price=document.createElement("input");
    var i_fileName=document.createElement("input");
    var i_order_goods_qty=document.createElement("input");
    
    i_goods_id.name="goods_id";
    i_goods_title.name="goods_title";
    i_goods_price.name="goods_price";
    i_fileName.name="goods_fileName";
    i_order_goods_qty.name="order_goods_qty";
    
    i_goods_id.value=goods_id;
    i_order_goods_qty.value=_order_goods_qty;
    i_goods_title.value=goods_title;
    i_goods_price.value=goods_price;
    i_fileName.value=fileName;
    
    //받아온 정보를 formObj에 저장
    formObj.appendChild(i_goods_id);
    formObj.appendChild(i_goods_title);
    formObj.appendChild(i_goods_price);
    formObj.appendChild(i_fileName);
    formObj.appendChild(i_order_goods_qty);
    document.body.appendChild(formObj);
    
    //submit
    formObj.method="post";
    formObj.action="${contextPath}/order/orderEachGoods.do";
    formObj.submit();
}

</script>

<div class="container">
	<div class="row ms-5 ps-5">
		<div class="mt-5 p-0 ps-5 align-items-center">
			<div class="ps-4">
				<p class="fs-5 fw-bold mb-3">장바구니</p>
				<div class="border-top border-main border-2 mt-2">

					<div class="">

						<c:choose>

							<c:when test="${ empty myCartList }">
								<!-- 장바구니가 비어있을 경우 -->
								<p class="my-5 text-center">장바구니에 상품이 없습니다 !</p>
							</c:when>

							<c:otherwise>
								<!-- 장바구니  리스트 myGoodsList를 foreach 문으로 돌려 출력한다.-->
								<form name="frm_order_all_cart">
									<c:forEach var="item" items="${myGoodsList}" varStatus="cnt">

										<!-- 인덱스 초기화 -->
										<c:set var="cart_goods_qty"
											value="${myCartList[cnt.count-1].cart_goods_qty}" />
										<c:set var="cart_id"
											value="${myCartList[cnt.count-1].cart_id}" />
										<!-- 인덱스 초기화 -->

										<div
											class="shadow-sm p-0 mt-3 rounded border border-light d-flex justify-content-between">

											<!-- 상품정보 및 선택영역 -->
											<div class="d-flex">

												<!-- 체크박스 영역 -->
												<div class="d-flex align-items-center p-3 bg-light">
													<input type="checkbox" name="checked_goods"
														class="cartGood"
														price="${item.goods_price*cart_goods_qty}"
														value="${item.goods_id}">
												</div>
												<!-- 체크박스 영역 -->

												<div class="d-flex align-items-center ps-4">

													<!-- 상품상세페이지로 이동 -->
													<a
														href="${contextPath}/goods/goodsDetail.do?goods_id=${item.goods_id }"
														class="text-decoration-none d-block"> <!-- 상품정보 -->
														<div class="d-flex justify-content-between">
															<div class="d-flex">
																<img
																	src="${contextPath}/thumbnails.do?goods_id=${item.goods_id}&fileName=${item.goods_fileName}"
																	style="width: 64px; height: 64px">
																<div class="ms-3">
																	<p class="mb-1 mt-1 small">${item.goods_title}</p>
																	<p class="mb-0 text-secondary">
																		<span class="cart_goods_qty">${cart_goods_qty}</span>개
																		<span> · </span><span>
																		<fmt:formatNumber value="${item.goods_price*cart_goods_qty}" pattern="#,###" />
																		</span>
																		<span class="goods_price">${item.goods_price*cart_goods_qty}</span>
																		원
																		<%-- 카트번호: ${cart_id} --%>
																	</p>
																</div>
															</div>
														</div> <!-- 상품정보 -->

													</a>
													<!-- 상품상세페이지로 이동 -->
												</div>

											</div>
											<!-- 상품정보 및 선택영역 -->


											<!-- 상품정보 수정영역 -->
											<div class="p-4">
												<div class="border-start ps-4">

													<select id="" selectedValue="1"
														class="form-select rounded-0 text-center"
														onchange="selectValue(this, this.value,${item.goods_id },${cnt.count-1 })">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
														<option value="6">6</option>
														<option value="7">7</option>
													</select> <input type="hidden" id="cart_goods_qty"
														name="cart_goods_qty" value="${cart_goods_qty}">

													<!-- 주문하기 -->
													<a
														href="javascript:fn_order_each_goods('${item.goods_id }','${item.goods_title }','${item.goods_price}','${item.goods_fileName}');"
														class="btn btn-sm border-main rounded-0 small d-block mt-2"
														style="width: 150px;">주문하기</a>

													<!-- 삭제하기 -->
													<a href="javascript:delete_cart_goods('${cart_id}');"
														class="btn btn-sm border-main rounded-0 small d-block mt-2"
														style="width: 150px;">삭제</a>

												</div>
											</div>
											<!-- 상품정보 수정영역 -->


										</div>

									</c:forEach>
								</form>
								<!-- 장바구니  리스트 myGoodsList를 foreach 문으로 돌려 출력한다.-->
							</c:otherwise>
						</c:choose>

					</div>
					
				</div>

				<!-- 전체선택 -->
				<div class="text-end mt-4 mb-2">
					<label> <input title="모든 상품을 결제상품으로 설정" type="checkbox"
						name="checked_goods" class="all-deal-select"
						onclick='selectAll(this)'> <span
						class="small selectAllSpan">전체선택</span>
					</label>
				</div>
				<!-- 전체선택 -->


				<!-- 선택상품 가격표시 영역 -->
				<p
					class="bg-light border text-end p-4 text-secondary d-flex justify-content-end align-items-center">
					<!-- 변수세팅 및 형 변환 -->
					<!-- 상품가격 * 갯수 및 형변환 -->
					<c:set var="totalGoodsPrice"
						value="${totalGoodsPrice+item.goods_price*cart_goods_qty }" />
					
					<c:set var="total_price"
						value="${totalGoodsPrice+totalDeliveryPrice-totalDiscountedPrice}" />
					<!-- 변수세팅 및 형 변환 -->

					<!-- 가격정보 hidden input -->
					<input id="h_totalGoodsPrice" type="hidden"
						value="${totalGoodsPrice}" /> <input id="h_totalDeliveryPrice"
						type="hidden" value="${totalDeliveryPrice}" /> <input
						id="h_totalSalesPrice" type="hidden" value="${totalSalesPrice}" />
					<input id="h_final_totalPrice" type="hidden"
						value="${totalGoodsPrice+totalDeliveryPrice}" />
					<!-- 가격정보 hidden input -->

					<span> <!-- 총 상품가격 --> <span>총 상품가격 <span
							id="goodsPrice">${total_goods_price}</span>원
					</span> <span>+</span> <!-- 총 배송비 --> <span>총 배송비
							${totalDeliveryPrice }원</span> <span>=</span> <!-- 총 주문금액 --> 총 주문금액
					</span> <span class="text-black fw-bold fs-5 ms-3"><span
						id="totalPrice">${total_price}</span> 원</span>
				</p>
				<!-- 선택상품 가격표시 영역 -->


				<!-- 선택상품 주문하기 -->
				<a href="javascript:fn_order_all_cart_goods()"
					class="btn btn-lg btn-main rounded-0 w-100 d-block fw-bold p-2 lh-lg mb-3">주문하기</a>
				<!-- 선택상품 주문하기 -->


			</div>
		</div>
	</div>
</div>

<script>

	
/* input의 value를 select에 select해 사용자에게 보여주는 역할 */
var cart_goods_qty_inputs = document.getElementsByName("cart_goods_qty");
cart_goods_qty_inputs.forEach((cart_goods_qty_inputs) => {
let inputValue = cart_goods_qty_inputs.value;
let selectBox = cart_goods_qty_inputs.previousElementSibling;
let selectBox_len = selectBox.options.length;
for (let i=0; i<selectBox_len; i++){  
	if(selectBox.options[i].value == inputValue){
		selectBox.options[i].selected = true;
	}
}
});



//전체 선택을 눌렀을때 금액 계산
var total = 0;
const checkboxes = document.getElementsByName('checked_goods');
var totalPrice=document.getElementById("totalPrice");
var goodsPrice=document.getElementById("goodsPrice");
function selectAll(selectAll){
	const goods_price = document.querySelectorAll(".goods_price");
	//checked_goods name을 가진 checkbox가 체크되엇는지 확인
	checkboxes.forEach((checkbox) => {checkbox.checked = selectAll.checked;}); 
	//체크되었을경우 금액추가
	if (selectAll.checked == true) {
		total=0;
		for (const i of goods_price) {total += i.innerHTML*1;};
		totalPrice.innerHTML=total;
		goodsPrice.innerHTML=total;
	}
	//체크되지않았을 경우 금액빼기
	else if(selectAll.checked == false){ 
		for (const i of goods_price) {total -= i.innerHTML*1;};
		totalPrice.innerHTML=total;
		goodsPrice.innerHTML=total;
	}
}


//체크박스를 누를때 금액 계산
let all_select = document.querySelector(".all-deal-select");
checkboxes.forEach((i) => i.addEventListener("click", function () {
	//체크되었을경우 금액추가
	if (this.checked == true) {total += i.getAttribute("price")*1;}
	//체크되지않았을 경우 금액빼기
	else if(this.checked == false){total -= i.getAttribute("price")*1;all_select.checked = false;}
	//계산된 금액 반영 innerHTML
	totalPrice.innerHTML=total;
	goodsPrice.innerHTML=total; 
}));

</script>