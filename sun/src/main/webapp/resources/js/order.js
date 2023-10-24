window.onload = function() {init();}
	function init() {var form_order = document.form_order;}

	
	//select박스가 체크되었을때 input에 반영함.
	function selectValue(selectBox, value){
		var input = selectBox.nextElementSibling
		input.setAttribute("value", value);
	} 
	
	
	//숫자여부체크
	var cardFormError = document.querySelector(".cardFormError");
	function checkLength(input, num){
		console.log(input.length+": "+num);
		if (input.value.length != num || isNaN(input.value)) {
			cardFormError.classList.remove("d-none");
			input.classList.add("is-invalid");
		} else{
			input.classList.remove("is-invalid");
			cardFormError.classList.add("d-none");
		}
		
	}
	
	
	//휴대폰결제 radio가 checked 되었을때 혹은 else일때 카드결제정보 레이아웃의 변화.
	var radioBtns = document.querySelectorAll('input[name="pay_method"]');
	radioBtns.forEach(function(radioBtn) {
	  radioBtn.addEventListener('change', function() {
	    var whenSelected_Phone = document.getElementsByClassName("whenSelected_Phone");
	    var whenSelected_Card = document.getElementsByClassName("whenSelected_Card");
	    
	    if (this.value === "휴대폰결제") {
	      whenSelected_Phone[0].classList.remove("d-none");
	      whenSelected_Card[0].classList.add("d-none");
	      
	    } else if(this.value === "신용카드"){
	      whenSelected_Phone[0].classList.add("d-none");
	      whenSelected_Card[0].classList.remove("d-none");
	    } else{
	      whenSelected_Phone[0].classList.add("d-none");
	      whenSelected_Card[0].classList.add("d-none");
	    }
	    
	  });
	});

	
		
	
	
	//다음 주소찾기
	function execDaumPostcode() {
		new daum.Postcode({
			oncomplete : function(data) {
				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				document.getElementById('zipcode').value = data.zonecode; //5자리 새우편번호 사용
				document.getElementById('member_address').value = data.address;
			}
		}).open();
	}
	//다음 주소찾기

	
	
	//분리되어잇는 배송지 정보들 get
	var delivery_address;
	var i_zipcode = document.getElementById("zipcode");
	var i_member_address = document.getElementById("member_address");
	var i_subaddress = document.getElementById("subaddress");
	const inputs = document.querySelectorAll("input[required]");
	
	var cardDate;
	var cardDate_month  = document.getElementById("cardDate_month");
	var cardDate_date  = document.getElementById("cardDate_date");
	
	
	//pay_method
	// 라디오 버튼 요소 선택
	function pay_method(){
		var radios = document.getElementsByName('pay_method');
		// 선택된 라디오 버튼의 값을 가져오기
		for (var i = 0; i < radios.length; i++) {
		  if (radios[i].checked) {
		    var selectedValue = radios[i].value;
		    return selectedValue;
		    break;
		  }
		}	
	}

	

	function getContextPath() {
		 var hostIndex = location.href.indexOf( location.host ) + location.host.length;
		 return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
		};
		

	//form 생성
	var formObj = document.createElement("form");
	formObj.setAttribute("id", "form_basic");
		
	//결제하기
	function fn_process_pay_order() {
		var payType = $('input[name="pay_method"]:checked').val();
	
		//required값인 input이 입력되지않았을 경우 submit을 하지 않도록 한다.
		let isValid = true;
//		inputs.forEach(input => {
//			  if (!input.value || input.classList.contains("is-invalid")) {
//			    isValid = false;
//			  }
//			});
		
		 if (isValid) {
			 
			 if (!confirm("결제하시겠습니까?")) { alert("결제가 취소되었습니다.");} 
			 else {
				//배송지 통합하여 저장
					delivery_address = "우편번호:" + i_zipcode.value + "<br>" + "주소:"
							+ i_member_address.value + "<br>" + "상세주소:"
							+ i_subaddress.value;
				
					/* cardDate = cardDate_month.value + "" + cardDate_date.value; */


				//수령자이름
					var i_receiver_name = document.createElement("input");
					i_receiver_name.name = "receiver_name";
					i_receiver_name.value = document.getElementById("receiver_name").value;
					formObj.appendChild(i_receiver_name);
				//수령자 핸드폰
					var i_receiver_hp1 = document.createElement("input");
					i_receiver_hp1.name = "receiver_hp1";
					i_receiver_hp1.value = document.getElementById("h_hp1").value;
					formObj.appendChild(i_receiver_hp1);
				
				//배송정보
					var i_delivery_address = document.createElement("input");
					i_delivery_address.name = "delivery_address";
					i_delivery_address.value = delivery_address;
					formObj.appendChild(i_delivery_address);

					//결제방법
					var i_pay_method = document.createElement("input");
					i_pay_method.name = "pay_method";
					i_pay_method.value= pay_method();
					formObj.appendChild(i_pay_method);
					
					//카드사선택
					var i_card_com_name = document.createElement("input");
					i_card_com_name.name="card_com_name";
					i_card_com_name.value=document.getElementById("card_com_name").value;
					formObj.appendChild(i_card_com_name);
					
					//할부기간
					var i_card_pay_month = document.createElement("input");
					i_card_pay_month.name="card_pay_month";
					i_card_pay_month.value=document.getElementById("card_pay_month").value;
					formObj.appendChild(i_card_pay_month);
					
					//핸드폰결제
				 	var i_pay_orderer_hp_num = document.createElement("input");
					i_pay_orderer_hp_num.name="pay_orderer_hp_num"; 
				    i_pay_orderer_hp_num.value=document.getElementById("pay_orderer_hp_num").value;
				    formObj.appendChild(i_pay_orderer_hp_num); 
				    
				    //카드번호 
				 	var i_cardNum = document.createElement("input");
				 	i_cardNum.name="cardNo"; 
				 	i_cardNum.value=document.getElementById("cardNum").value;
				    formObj.appendChild(i_cardNum); 
				    
				    //유효기간  - 년
				 	var i_cardDate_year = document.createElement("input");
				 	i_cardDate_year.name="expireYear"; 
				 	i_cardDate_year.value=document.getElementById("cardDate_year").value;
				    formObj.appendChild(i_cardDate_year); 
				    
				    //유효기간  - 월
				 	var i_cardDate_month = document.createElement("input");
				 	i_cardDate_month.name="expireMonth"; 
				 	i_cardDate_month.value=document.getElementById("cardDate_month").value;
				    formObj.appendChild(i_cardDate_month); 
				    
				    //생년월일  
				 	var i_birth  = document.createElement("input");
				 	i_birth.name="birthday"; 
				 	i_birth.value=document.getElementById("birth").value;
				    formObj.appendChild(i_birth); 
				    
				    //비밀번호  
				 	var i_cardPassword  = document.createElement("input");
				 	i_cardPassword.name="cardPw"; 
				 	i_cardPassword.value=document.getElementById("cardPassword").value;
				    formObj.appendChild(i_cardPassword ); 
				    
					
					//카카오 페이로 왔을 때
					if(payType == '카카오페이(간편결제)'){
				
						//화면에서 가져갈 데이터
						var amount = $("#h_final_total_Price").val(); //결제금액
						var itemName = $("#h_goods_title").val(); //상품명
						var userName = $("#h_orderer_name").val(); //구매자
				
						
						//카카오페이 선택
						//여기안에서
						//자바에서 주문api요청을 한 후 데이터 받아오기
						//비동기 ajax 사용 예정
//						alert("카카오페이 선택");
						$.ajax({
							type : "post",
							
							// async : false, //false인 경우 동기식으로 처리한다.
							
							url : getContextPath() + "/test/kakaoOrder.do",
							// url : "https://api.testpayup.co.kr/ep/api/kakao/himedia/order", (안됨)
							
							data : {
								"amount":amount
								,"itemName":itemName
								,"userName":userName
							},
							success : function(data, textStatus) {
								console.log(data);
								//컨트롤러에서 받은 데이터를 화면에 넣기
						
								if(data.responseCode == '0000'){ //주문을 성공했을 때
								// 주문요청 후 응답 받은 데이터를 form에 넣음
								$('.easyPayment_method').val(""); 
								$('input[name="kakaopay_direct"]').val("Y");
								
								$('input[name="ordr_idxx"]').val(data.ordr_idxx);
								$('input[name="good_name"]').val(data.good_name);
								$('input[name="good_mny"]').val(data.good_mny);
								$('input[name="buyr_name"]').val(data.buyr_name);
								$('input[name="site_cd"]').val(data.site_cd);
						
								// jsf__pay() 함수를 호출
								jsf__pay();
						
							}else{
								//주문데이터 받아오기 실패
								alert("오류");
							}
							},
							error : function(data, textStatus) {
								alert("에러가 발생했습니다."+data);
							},
							complete : function(data, textStatus) {
								//alert("작업을완료 했습니다");
							}
						}); //end ajax
				
						return false;

					}else if(payType =='네이버페이(카드)'){
						
						//화면에서 가져갈 데이터
						var amount = $("#h_final_total_Price").val(); //결제금액
						var itemName = $("#h_goods_title").val(); //상품명
						var userName = $("#h_orderer_name").val(); //구매자
				
						$.ajax({
							type : "post",
							url : getContextPath() + "/test/naverOrder.do",
							data : {
								"amount":amount
								,"itemName":itemName
								,"userName":userName
								,"payMethod":"네이버페이(카드)"
							},
							success : function(data, textStatus) {
								console.log(data);
								if(data.responseCode == '0000'){
								$('.easyPayment_method').val("");
								$('input[name="naverpay_direct"]').val("Y");
								
								$('input[name="ordr_idxx"]').val(data.ordr_idxx);
								$('input[name="good_name"]').val(data.good_name);
								$('input[name="good_mny"]').val(data.good_mny);
								$('input[name="buyr_name"]').val(data.buyr_name);
								$('input[name="site_cd"]').val(data.site_cd);
								jsf__pay();
						
							}else{alert("오류");}
							},
							error : function(data, textStatus) {
								alert("에러가 발생했습니다."+data);
							},
							complete : function(data, textStatus) {
							}
						});
				
						return false;
						
					}else if(payType =='네이버페이(포인트)'){
						console.log(payType);
						//화면에서 가져갈 데이터
						var amount = $("#h_final_total_Price").val(); //결제금액
						var itemName = $("#h_goods_title").val(); //상품명
						var userName = $("#h_orderer_name").val(); //구매자
				
						$.ajax({
							type : "post",
							url : getContextPath() + "/test/naverOrder.do",
							data : {
								"amount":amount
								,"itemName":itemName
								,"userName":userName
								,"payMethod":"네이버페이(포인트)"
							},
							success : function(data, textStatus) {
								console.log(data);
								if(data.responseCode == '0000'){
								$('.easyPayment_method').val("");
								$('input[name="naverpay_direct"]').val("Y");
								$('input[name="naverpay_point_direct"]').val("Y");
								
								$('input[name="ordr_idxx"]').val(data.ordr_idxx);
								$('input[name="good_name"]').val(data.good_name);
								$('input[name="good_mny"]').val(data.good_mny);
								$('input[name="buyr_name"]').val(data.buyr_name);
								$('input[name="site_cd"]').val(data.site_cd);
								jsf__pay();
						
							}else{alert("오류");}
							},
							error : function(data, textStatus) {
								alert("에러가 발생했습니다."+data);
							},
							complete : function(data, textStatus) {
							}
						});
				
						return false;
						
					}else if(payType =='네이버페이(포인트)'){
						console.log(payType);
						return false;
					}


					//return ; //아래 실행 안되게 하는중
					

				    
					//form body에 append
					document.body.appendChild(formObj);
					
					
					//form에 생성한 정보들로 payToOrderGoods submit
				 	formObj.method = "post";
					formObj.action = getContextPath() + "/order/payToOrderGoods.do";
					formObj.submit(); 
					}
				} 
		 else {alert("입력하신 정보가 없거나 올바르지않습니다!");}
	}
	
	
	
	
	/****************************************************************/
	/* m_Completepayment 설명 */
	/****************************************************************/
	/* 인증완료시 재귀 함수 */
	/* 해당 함수명은 절대 변경하면 안됩니다. */
	/* 해당 함수의 위치는 payplus.js 보다먼저 선언되어야 합니다. */
	/* Web 방식의 경우 리턴 값이 form 으로 넘어옴 */
	/****************************************************************/
	function m_Completepayment(FormOrJson, closeEvent) {
	
	var frm = document.order_info;
	/********************************************************************/
	/* FormOrJson은 가맹점 임의 활용 금지 */
	/* frm 값에 FormOrJson 값이 설정 됨 frm 값으로 활용 하셔야 됩니다. */
	/********************************************************************/
	GetField(frm, FormOrJson);
	if (frm.res_cd.value == "0000") {
	/*
	[가맹점 리턴값 처리 영역]
	인증이 완료되면 frm에 인증값이 들어갑니다. 해당 데이터를 가지고
	승인요청을 진행 해주시면 됩니다.
	*/
	console.log(frm);
//	frm.method="post";
//	frm.action = getContextPath() + "/test/kakaoPay.do";
//	frm.submit();
	
	

	
	//res_cd  
 	var i_res_cd  = document.createElement("input");
 	i_res_cd.name="res_cd"; 
 	i_res_cd.value=document.getElementsByName("res_cd")[0].value;
    formObj.appendChild(i_res_cd); 

	//res_msg  
	var i_res_msg  = document.createElement("input");
	i_res_msg.name="res_msg"; 
	i_res_msg.value=document.getElementsByName("res_msg")[0].value;
   formObj.appendChild(i_res_msg); 

   //res_msg  
	var i_res_msg  = document.createElement("input");
	i_res_msg.name="res_msg"; 
	i_res_msg.value=document.getElementsByName("res_msg")[0].value;
   formObj.appendChild(i_res_msg); 

   //enc_info  
	var i_enc_info  = document.createElement("input");
	i_enc_info.name="enc_info"; 
	i_enc_info.value=document.getElementsByName("enc_info")[0].value;
   formObj.appendChild(i_enc_info); 

   //enc_data  
	var i_enc_data  = document.createElement("input");
	i_enc_data.name="enc_data"; 
	i_enc_data.value=document.getElementsByName("enc_data")[0].value;
   formObj.appendChild(i_enc_data); 

    //ret_pay_method  
	var i_ret_pay_method  = document.createElement("input");
	i_ret_pay_method.name="ret_pay_method"; 
	i_ret_pay_method.value=document.getElementsByName("ret_pay_method")[0].value;
   formObj.appendChild(i_ret_pay_method); 

    //tran_cd  
	var i_tran_cd  = document.createElement("input");
	i_tran_cd.name="tran_cd"; 
	i_tran_cd.value=document.getElementsByName("tran_cd")[0].value;
   formObj.appendChild(i_tran_cd); 

   
    //use_pay_method  
	var i_use_pay_method  = document.createElement("input");
	i_use_pay_method.name="use_pay_method"; 
	i_use_pay_method.value=document.getElementsByName("use_pay_method")[0].value;
   formObj.appendChild(i_use_pay_method); 

   
    //card_pay_method  
	var i_card_pay_method  = document.createElement("input");
	i_card_pay_method.name="card_pay_method"; 
	i_card_pay_method.value=document.getElementsByName("card_pay_method")[0].value;
   formObj.appendChild(i_card_pay_method); 
   
   
   //order_idxx  
	var ordr_idxx  = document.createElement("input");
	ordr_idxx.name="ordr_idxx"; 
	ordr_idxx.value=document.getElementsByName("ordr_idxx")[0].value;
  formObj.appendChild(ordr_idxx); 

    
	//form body에 append
	document.body.appendChild(formObj);
	
	
	formObj.method="post";
	
	
	if(document.querySelector("input[name='kakaopay_direct']").value == "Y"){
		//카카오일때
		formObj.action = getContextPath() + "/test/kakaoPay.do";
	}else if(document.querySelector("input[name='naverpay_direct']").value == "Y"){
		//네이버일때
		formObj.action = getContextPath() + "/test/naverPay.do";
	}
	
	
	formObj.submit();
	
	
	} else {
//	alert("[" + frm.res_cd.value + "] " + frm.res_msg.value);
		console.log(frm);
		console.log(frm.res_cd.value);
		console.log(frm.res_msg.value);
	closeEvent();
	}
}
	/* 이 함수를 실행하여 카카오결제창을 호출 합니다*/
	function jsf__pay() {
	    try {
	        var form = document.order_info;
	        KCP_Pay_Execute(form);
	    } catch (e) {
	        console.error('An error occurred during payment:', e);
	        // Additional error handling code can be added here.
	    }
	}