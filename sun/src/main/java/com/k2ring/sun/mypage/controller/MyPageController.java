package com.k2ring.sun.mypage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface MyPageController {

	//주문목록
	public ModelAndView listMyOrderHistory(@RequestParam Map<String, String> dateMap,HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	//주문취소
	public ModelAndView cancelMyOrder(@RequestParam("order_id")  String order_id,HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	//반품
	public ModelAndView returnMyOrder(@RequestParam("order_id")  String order_id,HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	//교환
	public ModelAndView exchangeMyOrder(@RequestParam("order_id")  String order_id,HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	//내정보
	public ModelAndView myDetailInfo(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	//내 정보 수정
	public ResponseEntity modifyMyInfo(@RequestParam("member_pw")  String member_pw,
			@RequestParam("hp1")  String hp1,
			@RequestParam("zipcode")  String zipcode,
			@RequestParam("member_address")  String member_address,
			@RequestParam("subaddress")  String subaddress,
					            HttpServletRequest request, HttpServletResponse response)  throws Exception;

	//회원탈퇴
	public ResponseEntity deleteMember(@RequestParam("member_id")  String member_id,  HttpServletRequest request, HttpServletResponse response)  throws Exception;
}
