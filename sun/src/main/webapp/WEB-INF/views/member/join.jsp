<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8">
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>

//id중복체크
function fn_overlapped(){
	//member_id 값을 가져와 overlapped를 실행함.
    var _id=$("#member_id").val();
    if(_id==''){return;}
    
    $.ajax({
       type:"post",
       async:false,  
       url:"${contextPath}/member/overlapped.do",
       dataType:"text",
       data: {id:_id},
       success:function (data,textStatus){
    	  //가능한 id일 경우 input값은 _id로 유지되며 외의경우 아무런이벤트 없음 
    	  //각 상황에 따른 피드백 및 스타일을 add or remove
          if(data=='false'){
       	    $('#member_id').addClass("is-valid");
       		$('#member_id').removeClass("is-invalid");
       	 	$('.member_id-feedback.valid-feedback').removeClass("d-none");
       	    $('#member_id').val(_id);
          }else{
        	  $('#member_id').removeClass("is-valid");
        	  $('#member_id').addClass("is-invalid");
        	  $('.member_id-feedback.invalid-feedback').removeClass("d-none");
          }
       },
       error:function(data,textStatus){
          alert("에러가 발생했습니다.");
       },
       complete:function(data,textStatus){}
    });
 }	
//id중복체크

</script>
</head>
<body>

	<form action="${contextPath}/member/join.do" method="post"
		id="joinForm">

		<!-- id -->
		<input class="form-control form-control-lg rounded-0 mb-0"
			name="member_id" id="member_id" type="text" placeholder="아이디"
			onblur="fn_overlapped()" required>
		<div class="member_id-feedback valid-feedback text-start fs-07 d-none">
			사용가능한 아이디 입니다.</div>
		<div
			class="member_id-feedback invalid-feedback text-start fs-07 d-none">
			사용중인 아이디 입니다.</div>
		<div class="mb-3"></div>
		<!-- id -->


		<!-- password -->
		<input
			class="form-control form-control-lg rounded-0 mb-3 member_pwWrite"
			name="member_pw" type="text" placeholder="비밀번호" required
			onblur="member_pwChecking()"> <input
			class="form-control form-control-lg rounded-0 mb-0 member_pwCheck"
			type="text" placeholder="비밀번호 확인" required
			onblur="member_pwChecking()">

		<div class="member_pw-feedback valid-feedback text-start fs-07 d-none">
			비밀번호가 일치합니다.</div>

		<div
			class="member_pw-feedback invalid-feedback text-start fs-07 d-none">
			비밀번호가 일치하지않습니다.</div>
		<!-- password -->


		<div class="mb-3"></div>


		<!-- 이름 -->
		<input class="form-control form-control-lg rounded-0 mb-3 nameAndH1"
			name="member_name" type="text" placeholder="이름" required
			oninput="etcInptut()">

		<!-- 휴대폰번호 -->
		<input class="form-control form-control-lg rounded-0 mb-3 nameAndH1" name="hp1"
			type="text" placeholder="휴대폰번호" required oninput="etcInptut()">

		<div
			class="allRequiredInputCheck invalid-feedback text-start fs-07 mb-3 d-none">
			모든 정보를 입력해주세요.</div>

		<button type="button" onClick="joinSun()"
			class="btn btn-main rounded-0 w-100 d-block fw-bold p-2 lh-lg mb-3">회원가입</button>
		<a
			class="btn border-main rounded-0 w-100 d-block fw-bold p-2 lh-lg mb-3"
			href="${contextPath}/member/login.do">로그인</a>
	</form>



	<script>
	
	//비밀번호 확인 값체크
	//password input에서 onblur시 member_pwChecking가 실행됨.
    function member_pwChecking(){
   	 	let member_pwWrite = document.querySelector('.member_pwWrite');
        let member_pwCheck = document.querySelector('.member_pwCheck');
        let member_pw_feedback = document.querySelector('.member_pw-feedback.invalid-feedback');
        let member_pw_feedback_valid = document.querySelector('.member_pw-feedback.valid-feedback');
        
        //member_pwWrite와 member_pwCheck가 입력되엇을때만 안내문구를 출력할 것임.
		if(member_pwWrite.value=="" || member_pwCheck.value==""){
			console.log("아무것도입력되지않음");
		}else{
			//같지않을때
			if(member_pwWrite.value != member_pwCheck.value){
            	 member_pwWrite.classList.remove("is-valid");
            	 member_pwCheck.classList.remove("is-valid");
            	 
            	 member_pwWrite.classList.add("is-invalid");
            	 member_pwCheck.classList.add("is-invalid");
            	 member_pw_feedback.classList.remove("d-none");
            	 member_pw_feedback.classList.add("d-block");
            	 
            	 member_pw_feedback_valid.classList.remove("d-block");
            	 member_pw_feedback_valid.classList.add("d-none");
             }else{
            	//같을때
            	 member_pwWrite.classList.remove("is-invalid");
            	 member_pwCheck.classList.remove("is-invalid");
            	 member_pw_feedback.classList.add("d-none");
            	 member_pw_feedback.classList.remove("d-block");
            	 
            	 member_pwWrite.classList.add("is-valid");
            	 member_pwCheck.classList.add("is-valid");
            	 
            	 member_pw_feedback_valid.classList.add("d-block");
            	 member_pw_feedback_valid.classList.remove("d-none");
             }
		}
    }
  	//비밀번호 확인 값체크

  
        const form = document.getElementById("joinForm");
        const inputs = form.querySelectorAll("input[required]");
        const nameAndH1 = form.querySelectorAll(".nameAndH1");
        var allRequiredInputCheck = document.querySelector('.allRequiredInputCheck');
        
        
        //이름, 휴대폰 input 에서 this input이 공란일때 style로 경고를 표시함.
        function etcInptut(){
        	if(this.value != ""){
        		nameAndH1.forEach(input => {input.classList.remove("is-invalid");});
        		allRequiredInputCheck.classList.add('d-none');
        	}
        }
        
        
        //회원가입
        function joinSun(){
            let isValid = true;
            //input들의 값을 확인하여 값이 없을경우 style을 변경한다.
            inputs.forEach(input => {
                if (!input.value) {
                  isValid = false;
                  input.classList.add("is-invalid");
                } else {
                  input.classList.remove("is-invalid");
                  allRequiredInputCheck.classList.add('d-none');
                }
              });
            
         // 모든 입력이 유효하면 폼을 제출
            if (isValid) {form.submit();} 
            else {
            	allRequiredInputCheck.classList.remove('d-none');}
        }
        
        </script>

</body>
</html>