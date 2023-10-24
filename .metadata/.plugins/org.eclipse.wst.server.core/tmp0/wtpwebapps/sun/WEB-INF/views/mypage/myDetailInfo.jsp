<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<div class="container">
	<div class="row ms-5 ps-5">
		<div class="mt-5 p-0 ps-5 align-items-center">
			<div class="ps-4">
				<p class="fs-5 fw-bold mb-3">내정보</p>

				<div class="border-top border-main border-2 mt-2"></div>
				<div class="table-responsive mt-4">
					<form name="frm_mod_member">
						<div id="detail_table">
							<table class="table border-top mb-0 small fw-light">
							
								<!-- 이름 -->
								<tr>
									<td class="table-light ps-4 align-middle" style="width: 200px;">이름</td>
									<td class="px-4"><input style="width: 200px;"
										class="form-control rounded-0" name="member_name" type="text"
										size="20" value="${memberInfo.member_name }" disabled /></td>
								</tr>
								
								<!-- 아이디 -->
								<tr>
									<td class="table-light ps-4 align-middle" style="width: 200px;">아이디</td>
									<td class="px-4"><input style="width: 200px;"
										name="member_id" class="form-control rounded-0" type="text"
										size="20" value="${memberInfo.member_id }" disabled /></td>
								</tr>
								
								<!-- 비밀번호 -->
								<tr>
									<td class="table-light ps-4 align-middle" style="width: 200px;">비밀번호</td>
									<td class="px-4"><input style="width: 200px;"
										name="member_pw" class="form-control rounded-0"
										type="password" size="20" value="${memberInfo.member_pw }" />
									</td>
								</tr>
								
								<!-- 휴대폰번호 -->
								<tr>
									<td class="table-light ps-4 align-middle" style="width: 200px;">휴대폰번호</td>
									<td class="px-4"><input style="width: 200px;" type="text"
										class="form-control rounded-0" name="hp1" size=4
										value="${memberInfo.hp1 }"></td>
								</tr>
								<tr>
									<td class="table-light ps-4 align-middle" style="width: 200px;">배송지</td>
									<td class="px-4">
										<div class="input-group mb-2" style="width: 395px;">
											
											<!-- 우편번호 -->
											<input type="text" class="form-control rounded-0"
												placeholder="우편번호" id="zipcode" name="zipcode" size=5
												value="${memberInfo.zipcode }"> 
												
											<!-- 다음 우편번호 검색 -->
											<a class="btn border-main small rounded-0 samll"
												href="javascript:execDaumPostcode()">우편번호검색</a>
											<!-- 다음 우편번호 검색 -->
											
										</div> 
										
										<!-- 주소 -->
										<input type="text" id="member_address"
										class="form-control rounded-0 mb-2" placeholder="주소"
										name="member_address" size="50"
										value="${memberInfo.member_address }"> 
										
										<!-- 상세주소 -->
										<input
										type="text" id="subaddress"
										class="form-control rounded-0 mb-2" name="subaddress"
										size="50" value="${memberInfo.subaddress }">
									</td>
								</tr>
							</table>
						</div>

						<!-- 수정 -->
						<a href="javascript:fn_modify_member_info()"
							class="btn btn-lg btn-main rounded-0 w-100 d-block fw-bold p-2 lh-lg mt-4 mb-2">수정하기</a>

						<!-- 취소, 클릭시  reload-->
						<button type="button" onClick="location.reload()"
							class="btn btn-lg border-main rounded-0 w-100 d-block fw-bold p-2 lh-lg mt-0 mb-2 fs-6">취소하기</button>

						<!-- 탈퇴 -->
						<button type="button"
							onClick="fn_delete_member('${member_info.member_id }','Y')"
							class="btn btn-lg border-main rounded-0 w-100 d-block fw-bold p-2 lh-lg mt-0 mb-3 fs-6">탈퇴하기</button>

					</form>
				</div>

			</div>
		</div>
	</div>
	<input type="hidden" name="h_hp1" value="${memberInfo.hp1}" />
</div>


<script>
	//다음 주소 찾기
	function execDaumPostcode() {
		new daum.Postcode({
			oncomplete : function(data) {
				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				document.getElementById('zipcode').value = data.zonecode; //5자리 새우편번호 사용
				document.getElementById('member_address').value = data.address;
			}
		}).open();
	}

	//회원정보 수정
	function fn_modify_member_info() {
		var frm_mod_member = document.frm_mod_member.value;
		var member_pw = document.frm_mod_member.member_pw.value;
		var hp1 = document.frm_mod_member.hp1.value;
		var zipcode = document.frm_mod_member.zipcode.value;
		var member_address = document.frm_mod_member.member_address.value;
		var subaddress = document.frm_mod_member.subaddress.value;
		$.ajax({
			type : "post",
			async : false, //false인 경우 동기식으로 처리한다.
			url : "${contextPath}/mypage/modifyMyInfo.do",
			data : {
				member_pw : member_pw,
				hp1 : hp1,
				zipcode : zipcode,
				member_address : member_address,
				subaddress : subaddress
			},
			success : function(data, textStatus) {
				if (data.trim() == 'mod_success') {
					//성공시 안내
					alert("회원 정보를 수정했습니다.");
				} else if (data.trim() == 'failed') {
					alert("다시 시도해 주세요.");
				}
			},
			error : function(data, textStatus) {
				alert("에러가 발생했습니다." + data);
			},
			complete : function(data, textStatus) {
			}
		});

	}

	//회원탈퇴
	function fn_delete_member(member_id, del_yn) {
		var frm_mod_member = document.frm_mod_member.value;
		var member_id = document.frm_mod_member.member_id.value;

		var answer = confirm("탈퇴하시겠습니까?");
		//confirm answer = true일때 submit
		if (answer == true) {
			$.ajax({
				type : "post",
				async : false,
				url : "${contextPath}/mypage/deleteMember.do",
				data : {
					member_id : member_id,
					del_yn : del_yn
				},
				success : function(data, textStatus) {
					if (data.trim() == 'delete_success') {
						//성공시 안내와 함께 logout.do
						alert("탈퇴되었습니다.");
						location.href = "${contextPath}/member/logout.do";
					} else if (data.trim() == 'failed') {
						alert("다시 시도해 주세요.");
					}
				},
				error : function(data, textStatus) {
					alert("에러가 발생했습니다." + data);
				},
				complete : function(data, textStatus) {
				}
			});
		}
	}
</script>