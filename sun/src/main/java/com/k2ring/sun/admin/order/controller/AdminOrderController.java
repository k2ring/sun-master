package com.k2ring.sun.admin.order.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface AdminOrderController {

	//주문목록
	public ModelAndView adminOrderMain(@RequestParam Map<String, String> dateMap,
            HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	//주문수정 - 배송수정
	public ResponseEntity modifyDeliveryState(@RequestParam Map<String, String> deliveryMap, 
            HttpServletRequest request, HttpServletResponse response)  throws Exception;
}
