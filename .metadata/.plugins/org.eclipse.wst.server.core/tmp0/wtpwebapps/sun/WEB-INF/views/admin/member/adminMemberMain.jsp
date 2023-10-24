<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="container">
	<div class="row ms-5 ps-5">
		<div class="mt-5 p-0 ps-5 align-items-center">
			<div class="ps-4">

				<p class="fs-5 fw-bold mb-2">회원관리</p>
				<form name="frm_mod_member">

					<div
						class="d-flex align-items-center gap-1 justify-content-between">
						<div>
						
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

						</div>
					</div>


<!-- 회원정보 표시 영역 -->
					<div class="border-top border-main border-2 mt-2"></div>

					<div class="table-responsive mt-4">
						<table
							class="table border-top mb-0 small fw-light border-bottom member_table">
							<tbody>
							
							<!-- thead -->
								<tr>
									<td
										class="table-light p-2 align-middle fw-bold border-end text-center"
										style="width: 100px;">회원ID</td>
									<td
										class="table-light ps-4 align-middle fw-bold text-center border-end">회원정보</td>
									<td
										class="table-light ps-4 align-middle fw-bold text-center border-end">배송정보</td>
									<td
										class="table-light align-middle fw-bold text-center border-end">가입일</td>
									<td class="table-light text-center px-4 align-middle"
										style="width: 114px;">-</td>
								</tr>
								<!-- thead -->
								
								<c:choose>
									<c:when test="${empty member_list}">

									<!-- 조회된 회원이 없을경우 -->
										<tr>
											<td colspan="5">
												<div class="p-4">
													<p class="my-5 text-center">조회된 회원이 없습니다.</p>
												</div>
											</td>
										</tr>
										<!-- 조회된 회원이 없을경우 -->

									</c:when>


									<c:otherwise>
									
									<!-- 회원리스트, newGoodsList for문 -->
										<c:forEach var="item" items="${member_list}"
											varStatus="item_num">

											<tr>
											
											<!-- 회원 id 1 -->
												<td class="align-middle border-end text-center">
													${item.member_id}</td>
													<!-- 회원 id 1 -->
													
												<td class="border-end align-middle">
												
												<!-- 회원이름 -->
													<div class="d-flex mb-1 align-items-center">
														<span style="width: 100px;" class="">이름</span> <input
															class="form-control rounded-0 member_name" type="text"
															placeholder="이름" value="${item.member_name}" readonly>
													</div>
													<!-- 회원이름 -->
													
													<!-- 회원 id 2 -->
													<div class="d-flex mb-1 align-items-center">
														<span style="width: 100px;" class="">아이디</span> <input
															class="form-control rounded-0 member_id" type="text"
															name="member_id" placeholder="아이디"
															value="${item.member_id}" readonly>
													</div>
													<!-- 회원 id 2 -->
													
													<!-- 비밀번호 -->
													<div class="d-flex mb-1 align-items-center">
														<span style="width: 100px;" class="">비밀번호</span> <input
															class="form-control rounded-0 member_pw" type="text"
															name="member_pw" placeholder="비밀번호"
															value="${item.member_pw}" readonly>
													</div>
													<!-- 비밀번호 -->
													
													<!-- 휴대전화 -->
													<div class="d-flex mb-0 align-items-center">
														<span style="width: 100px;" class="">휴대전화</span> <input
															class="form-control rounded-0 hp1" type="text" name="hp1"
															placeholder="010-0000-0000" value="${item.hp1}" readonly>
													</div>
													<!-- 휴대전화 -->
													
												</td>
												<td class="border-end align-middle">
													<div class="d-flex mb-1 align-items-center">
														<span style="width: 97.5px;" class="">우편번호</span>
														<div class="input-group mb-0">
														
														<!-- 우편번호 -->
															<input type="text" class="form-control rounded-0 zipcode"
																placeholder="우편번호" id="zipcode" name="zipcode" size=5
																value="${item.zipcode}" aria-describedby="button-addon2"
																readonly>
																<!-- 우편번호 -->

														</div>


													</div>
													
													<!-- 주소 -->
													<div class="d-flex mb-1 align-items-center">
														<span style="width: 100px;" class="">주소</span> <input
															class="form-control rounded-0 member_address" type="text"
															placeholder="주소" name="member_address"
															value="${item.member_address}" readonly>
													</div>
													<!-- 주소 -->
													
													<!-- 상세주소 -->
													<div class="d-flex mb-1 align-items-center">
														<span style="width: 100px;" class="">상세주소</span> <input
															class="form-control rounded-0 subaddress" type="text"
															placeholder="상세주소" name="subaddress"
															value="${item.subaddress}" readonly>
													</div>
													<!-- 상세주소 -->
													
												</td>
												
												<!-- 가입일 -->
												<td class="border-end align-middle text-center">
													${item.joinDate}
													<!-- 가입일 -->
													 
													<c:out value="${arr[0]}" /><br> 
													
													<!-- 탈퇴여부 del_yn가 N일경우, 활동중 표시 -->
													<c:choose>
														<c:when test="${item.del_yn=='N' }">
															<strong> 활동중</strong>
														</c:when>
														<c:otherwise>
														</c:otherwise>
													</c:choose>
													<!-- 탈퇴여부 del_yn가 N일경우, 활동중 표시 -->

												</td>

												<td class="align-middle">
												<c:set var="index" value="${status.index}" /> 
												
												<!-- 강제탈퇴 -->
													<button
														class="w-100 btn border-main small rounded-0 samll mb-0"
														type="button"
														onClick="fn_delete_member('${item.member_id }','Y')">강제탈퇴</button>
												<!-- 강제탈퇴 -->
														
												</td>

											</tr>
										</c:forEach>
										<!-- 회원리스트, newGoodsList for문 -->
										
										
										
									</c:otherwise>
								</c:choose>

							</tbody>
						</table>
					</div>


<!-- 회원정보 표시 영역 -->

				</form>

			</div>
		</div>
	</div>
</div>


<script>

//버튼식 상품조회
//onclick할때 같이 가져오는 fixedSearchPeriod값과함께 adminMemberMain 재요청
//해당 값은 자바소스를 거쳐 지정된 기간에 맞는 정보만 select되어 뿌려진다.
function search_order_history(fixedSearchPeriod) {
	var formObj = document.createElement("form");
	var i_fixedSearch_period = document.createElement("input");
	i_fixedSearch_period.name = "fixedSearchPeriod";
	i_fixedSearch_period.value = fixedSearchPeriod;
	formObj.appendChild(i_fixedSearch_period);
	document.body.appendChild(formObj);
	formObj.method = "get";
	formObj.action = "${contextPath}/admin/member/adminMemberMain.do";
	formObj.submit();
}

//버튼식 상품조회 badge url에 따른 style변경
if (window.location.href.includes("fixedSearchPeriod")) {
	const badges = document.querySelectorAll(".badge");
	for (b of badges){ b.classList.remove("active");}
	//각 값이 url에 들어있을 경우 active된다.
	if (window.location.href.includes("today")) {badges[0].classList.add("active");
	} else if (window.location.href.includes("one_month")) {badges[1].classList.add("active");
	}else if (window.location.href.includes("two_month")) {badges[2].classList.add("active");
	}else if (window.location.href.includes("three_month")) {badges[3].classList.add("active");
	}else if (window.location.href.includes("six_month")) {badges[4].classList.add("active");
	}
}				
					

//강제탈퇴
function fn_delete_member(member_id ,del_yn){
	var member_id=member_id;
	var del_yn=del_yn;
	
	//confirm단계를 거치고 사용자가 취소를 누를경우 삭제를 취소한다.
	var answer=confirm("해당회원을 삭제하시겠습니까?");
	if(answer==true){
		$.ajax({
			type : "post",
			async : false,
			url : "${contextPath}/mypage/deleteMember.do",
			data : {
				member_id:member_id,
				del_yn:del_yn
			},
			success : function(data, textStatus) {
				if(data.trim()=='delete_success'){
					alert("삭제되었습니다.");
					location.href="${contextPath}/admin/member/adminMemberMain.do";
				}else if(data.trim()=='failed'){
					alert("다시 시도해 주세요.");	
				}
			},
			error : function(data, textStatus) {
				alert("에러가 발생했습니다."+data);
			},
			complete : function(data, textStatus) {}
		});
	}
}
					
</script>