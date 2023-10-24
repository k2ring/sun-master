<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="goods" value="${goodsMap.goodsVO}" />
<c:set var="imageList" value="${goodsMap.imageList }" />


<div class="container">
	<div class="row">
		<div class="p-0 align-items-center gap-3 mt-5">
			<!-- <p class="fs-6 mb-1">HOT! TREND</p>
                <p class="fs-3 fw-bold">카테고리</p> -->
		</div>
	</div>

	<div class="row">

		<div class="p-0 d-flex">

			<!-- 상품이미지 -->
			<div class="d-flex">
				<!-- tab caller -->
				<div class="list-group me-3" id="list-tab" role="tablist">
					<a class="active mb-3 back_eee" id="detailThumb1"
						data-bs-toggle="list" href="#detailThumb01" role="tab"
						aria-controls="detailThumb01"> <img
						src="${contextPath}/thumbnails.do?goods_id=${goods.goods_id}&fileName=${goods.goods_fileName}"
						style="width: 50px"></a> <a class="mb-3 back_eee"
						id="detailThumb2" data-bs-toggle="list" href="#detailThumb02"
						role="tab" aria-controls="detailThumb02"> <img
						src="${contextPath}/resources/img/logo_square.png"
						style="width: 50px"></a>
				</div>
				<!-- tab caller -->

				<!-- tab 본문 -->
				<!-- 배경설정 back_eee -->
				<div class="tab-content back_eee" id="nav-tabContent">
					<div class="tab-pane show active" id="detailThumb01"
						role="tabpanel" aria-labelledby="detailThumb1">
						<img
							src="${contextPath}/download.do?goods_id=${goods.goods_id}&fileName=${goods.goods_fileName}"
							style="width: 410px">
					</div>
					<div class="tab-pane back_eee" id="detailThumb02" role="tabpanel"
						aria-labelledby="detailThumb2">
						<img src="${contextPath}/resources/img/logo_square.png">
					</div>
				</div>
				<!-- tab 본문 -->

			</div>
			<!-- 상품이미지 -->



			<!-- 상품정보 -->
			<div class="ps-4 w-100">
				<!-- 카테고리 -->
				<p class="fs-6 mb-1">${goods.goods_sort }</p>
				<!-- 상품명 -->
				<p class="fs-3 fw-bold">${goods.goods_title }</p>
				<hr>
				<!-- 가격 및 수량, 수량은 고정 -->
				<p class="fs-6 mb-1">
					<span class="fs-4 text-danger fw-bold">
					<fmt:formatNumber value="${goods.goods_price }" pattern="#,###" />
					</span>원
					· 1개
				</p>

				<div class="d-flex gap-2 mt-4">
					<input type="hidden" id="goods_qty" name="order_goods_qty"
						value="1">

					<!-- 장바구니 담기, goods_id값과 함께 add_cart 실행 -->
					<a href="javascript:add_cart('${goods.goods_id}')"
						class="btn btn-lg fw-bold border-main rounded-0 d-block flex-fill">장바구니담기</a>
					<!-- 주문하기, 상품정보와 함께 fn_order_each_goods실행  -->
					<a
						href="javascript:fn_order_each_goods('${goods.goods_id }','${goods.goods_title }','${goods.goods_price}','${goods.goods_fileName}');"
						class="btn btn-lg fw-bold btn-main rounded-0 d-block flex-fill">바로구매</a>
				</div>
			</div>
			<!-- 상품정보 -->


		</div>


		<!-- 하단 상품상세정보 -->
		<div class="mt-5 border-top border-secondary border-3 p-0">
			<!-- tab Caller -->
			<ul class="nav nav-tabs">
				<li class="nav-item"><a
					class="nav-link rounded-0 text-center py-3 fw-bold active"
					id="detailInfo1" data-bs-toggle="list" href="#detailInfo01"
					role="tab" aria-controls="detailInfo01" style="width: 250px">
						상품상세 </a></li>
				<li class="nav-item"><a
					class="nav-link rounded-0 py-3 text-center fw-bold"
					id="detailInfo2" data-bs-toggle="list" href="#detailInfo02"
					role="tab" aria-controls="detailInfo02" style="width: 250px">
						배송/교환/반품 안내</a></li>
			</ul>
			<!-- tab Caller -->


			<!-- tab 본문 -->
			<div class="tab-content mt-5" id="nav-tabContent">
				<div class="tab-pane show active" id="detailInfo01" role="tabpanel"
					aria-labelledby="detailInfo1">
					<!-- 상세이미지 리스트 foreach로 뿌림 -->
					<c:forEach var="image" items="${imageList }">
						<div class="mb-5"
							style="background:url(${contextPath}/resources/img/back1.jpg);background-size: cover;">
							<img class=""
								src="${contextPath}/download.do?goods_id=${goods.goods_id}&fileName=${image.fileName}"
								style="width: 1200px;">
						</div>
					</c:forEach>
					<!-- 상세이미지 리스트 foreach로 뿌림 -->
				</div>

				<!-- 상품/배송정보등의 외 정보 -->
				<div class="tab-pane" id="detailInfo02" role="tabpanel"
					aria-labelledby="detailInfo2">
					<img src="${contextPath}/resources/img/goods/detailInfo.jpg">
				</div>
				<!-- 상품/배송정보등의 외 정보 -->
			</div>
			<!-- tab 본문 -->

		</div>
		<!-- 하단 상품상세정보 -->


	</div>
</div>





<script type="text/javascript">

	//장바구니 추가, goods_id정보를 넘겨줌.
	function add_cart(goods_id) {
		$.ajax({
			type : "post",
			async : false,
			url : "${contextPath}/cart/addGoodsInCart.do",
			data : {goods_id:goods_id},
			success : function(data, textStatus) {
				if(data.trim()=='add_success'){alert("장바구니에 추가되엇습니다.");
				}else if(data.trim()=='already_existed'){alert("이미 카트에 등록된 상품입니다.");	}
			},
			error : function(data, textStatus) {alert("로그인 후 추가하실 수 있습니다!");},
			complete : function(data, textStatus) {}
		});
	}

	
//바로 주문하기 
function fn_order_each_goods(goods_id,goods_title,goods_price,fileName){
	 	var total_price,final_total_price;
		var order_goods_qty=document.getElementById("order_goods_qty");
		var formObj=document.createElement("form");
		var i_goods_id = document.createElement("input"); 
	    var i_goods_title = document.createElement("input");
	    var i_goods_price=document.createElement("input");
	    var i_fileName=document.createElement("input");
	    var i_order_goods_qty=document.createElement("input");
	    
		let memberName = "${memberInfo.member_name }";
		/* console.log(memberName); */
		 
		if(memberName == ""){alert("로그인 후 구매하실 수 있습니다!");}/* 로그인상태가 아닌경우 안내함. */
		else{
		    i_goods_id.name="goods_id";
		    i_goods_title.name="goods_title";
		    i_goods_price.name="goods_price";
		    i_fileName.name="goods_fileName";
		    i_order_goods_qty.name="order_goods_qty";
		    i_goods_id.value=goods_id;
		    i_order_goods_qty.value=1;//i_order_goods_qty 1로 고정
		    i_goods_title.value=goods_title;
		    i_goods_price.value=goods_price;
		    i_fileName.value=fileName;
		    
		    //formObj에 해당 상품 정보를 할당해 orderEachGoods로 action
		    formObj.appendChild(i_goods_id);
		    formObj.appendChild(i_goods_title);
		    formObj.appendChild(i_goods_price);
		    formObj.appendChild(i_fileName);
		    formObj.appendChild(i_order_goods_qty);
		    document.body.appendChild(formObj); 
		    
		    formObj.method="post";
		    formObj.action="${contextPath}/order/orderEachGoods.do";
		    formObj.submit();
			}	
		
	}
	
</script>
