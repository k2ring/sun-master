<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	var loopSearch = true;
	function keywordSearch() {
		var searchWordValue = document.getElementById('searchWord').value;
		var value = document.frmSearch.searchWord.value;
		
		if (loopSearch == false) return;
		if(searchWordValue.length > 0){
			$.ajax({
				type : "get",
				async : true, //false인 경우 동기식으로 처리한다.
				url : "${contextPath}/goods/keywordSearch.do",
				data : {
					keyword : value
				},
				success : function(data, textStatus) {
					var jsonInfo = JSON.parse(data);
					displayResult(jsonInfo);
				},
				error : function(data, textStatus) {
					alert("에러가 발생했습니다." + data);
				},
				complete : function(data, textStatus) {
					//alert("작업을완료 했습니다");
				}
			}); //end ajax	
		}else{
			hide('suggest');
		}
	}

	function displayResult(jsonInfo) {
		var count = jsonInfo.keyword.length;
		var listView = document.getElementById("innerDivForSuggestList");
		if (count > 0) {
			var html = '';
			for ( var i in jsonInfo.keyword) {
				html += "<li><a class='dropdown-item small' href=\"javascript:select('"
						+ jsonInfo.keyword[i]
						+ "')\">"
						+ jsonInfo.keyword[i]
						+ "</a></li>";
			}
			listView.innerHTML = html;
			show('suggest');
		} else {
			hide('suggest');
		}
	}

	function select(selectedKeyword) {
		document.frmSearch.searchWord.value = selectedKeyword;
		loopSearch = false;
		hide('suggest');
	}

	function show(elementId) {
		var element = document.getElementById(elementId);
		if (element) {
			element.classList.remove('d-none');
			element.classList.add('d-block');
		}
	}

	function hide(elementId) {
		var element = document.getElementById(elementId);
		if (element) {
			element.classList.remove('d-block');
			element.classList.add('d-none');
		}
	}
</script>

<body class="position-relative">
	<div class="sticky-sm-top header">
		<div class="bg-light d-flex p-0 justify-content-end border-bottom">
			<div class="container d-flex align-items-center justify-content-end p-1 pe-0">
			
				<c:choose>
					<c:when test="${isLogOn==true and not empty memberInfo}">
					
					<c:choose>
					
					<c:when test="${isLogOn==true and memberInfo.member_id =='admin'}">
					<div class="d-flex align-items-center gap-2">
					<a href="${contextPath}/cart/myCartList.do"
								class="text-decoration-none"><span class="samll fw-bold">${memberInfo.member_name}
							</span> </a> 
							<a href="${contextPath}/admin/goods/adminGoodsMain.do"
								class="text-decoration-none samll btn btn-outline-secondary btn-sm p-0 px-2 rounded-0 bg-white">햇빛농장 관리
						</a> <a href="${contextPath}/member/logout.do"
								class="text-decoration-none samll btn btn-outline-secondary btn-sm p-0 px-2 rounded-0 bg-white">로그아웃</a>
						</div>
					</c:when>
					<c:otherwise>
					<div class="d-flex align-items-center gap-2">
							<a href="${contextPath}/cart/myCartList.do"
								class="text-decoration-none"><span class="samll fw-bold">${memberInfo.member_name}
							</span> </a> <a href="${contextPath}/member/logout.do"
								class="text-decoration-none samll btn btn-outline-secondary btn-sm p-0 px-2 rounded-0 bg-white">로그아웃</a>
						</div>
					</c:otherwise>
					
					</c:choose>
					
					</c:when>
					
					<c:otherwise>
						<div class="d-flex gap-2">
							<a href="${contextPath}/member/login.do"
								class="text-decoration-none samll btn btn-outline-secondary btn-sm p-0 px-2 rounded-0 bg-white">로그인</a> 
								<a
								href="${contextPath}/member/join.do"
								class="text-decoration-none samll btn btn-outline-secondary btn-sm p-0 px-2 rounded-0 bg-white">회원가입</a>
						</div>
					</c:otherwise>
				</c:choose>

			</div>
		</div>

		<div class="container">
			<div class="row">
				<div
					class="bg-white d-flex p-0 d-flex align-items-center justify-content-between">
					<div class="btn-group m-0" role="group">
						<button type="button" class="btn btn-main rounded-0 menu_btn"
							data-bs-toggle="dropdown" aria-expanded="false" width="110px"
							height="115px">
							<i class="fa-solid fa-bars d-block mt-1 mb-1"></i> <span
								class="small">카테고리</span>
						</button>
						<ul class="dropdown-menu rounded-0 shadow border-0 px-2 py-3"
							id="suggestList">
							<li><a class="dropdown-item small"
								href="${contextPath}/goods/menuGoods.do?menuGoods=과일">과일</a></li>
							<li><a class="dropdown-item small"
								href="${contextPath}/goods/menuGoods.do?menuGoods=곡류">곡류</a></li>
							<li><a class="dropdown-item small"
								href="${contextPath}/goods/menuGoods.do?menuGoods=채소">채소</a></li>
							<li><a class="dropdown-item small"
								href="${contextPath}/goods/menuGoods.do?menuGoods=버섯">버섯</a></li>
						</ul>
					</div>

					<div class="logo d-flex align-items-center px-4">
						<a href="${contextPath}/main/main.do"> <img
							src="${contextPath}/resources/img/logo.png" width="150"
							class="p-2">
						</a>
					</div>

					<div class="search d-flex align-items-center flex-grow-1">
						<div
							class="input-group border border-main border-2 position-relative">
							<select name="" id=""
								class="form-select rounded-0 flex-inherit text-start small border border-end">
								<option value="">전체</option>
								<option value="">과일</option>
								<option value="">곡류</option>
								<option value="">채소</option>
								<option value="">버섯</option>
							</select>
							<form name="frmSearch"
								action="${contextPath}/goods/searchGoods.do"
								class="form-control mb-0 border-0 d-flex p-0">

								<input type="text small" name="searchWord" id="searchWord"
									class="form-control border-0" placeholder="찾고 싶은 상품을 검색해보세요!"
									onKeyUp="keywordSearch()"> <input name="search"
									type="submit" id="searchInputWithLabel" class="d-none">
								<label for="searchInputWithLabel"
									class="samll d-flex align-items-center px-2 pe-2" style="cursor: pointer;"><i
									class="fa-solid fa-magnifying-glass d-block color-main"></i></label> </a>
							</form>
							<!-- 추천키워드 -->
							<div id="suggest" class="d-none">
								<ul id="suggestList"
									class="keywordSearchList dropdown-menu rounded-0 shadow border-0 px-2 pb-3 show position-absolute top-100"
									style="left: 120px; width: calc(100% - 120px);">
									<div id="innerDivForSuggestList"></div>
								</ul>
							</div>
						</div>
					</div>

					<a href="${contextPath}/mypage/listMyOrderHistory.do"
						class="mySun text-decoration-none samll d-flex flex-column px-2 text-center ps-3 mt-2">
						<i class="fa-solid fa-user d-block mb-2 color-main "></i> <span
						class="my-coupang-title">마이햇빛농장</span>
					</a> <a href="${contextPath}/cart/myCartList.do"
						class="cart text-decoration-none samll d-flex flex-column px-2 text-center ps-3 mt-2">
						<i class="fa-solid fa-cart-arrow-down d-block mb-2 color-main"></i>
						<span class="my-coupang-title">장바구니</span>
					</a>
				</div>
			</div>
		</div>
	</div>

</body>
</html>