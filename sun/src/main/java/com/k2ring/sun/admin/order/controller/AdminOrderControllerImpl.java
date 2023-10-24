package com.k2ring.sun.admin.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.k2ring.sun.admin.order.service.AdminOrderService;
import com.k2ring.sun.common.base.BaseController;
import com.k2ring.sun.order.vo.OrderVO;

@Controller("adminOrderController")
@RequestMapping(value="/admin/order")
public class AdminOrderControllerImpl extends BaseController  implements AdminOrderController{
	@Autowired
	private AdminOrderService adminOrderService;
	
	//주문목록
	@Override
	@RequestMapping(value="/adminOrderMain.do" ,method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView adminOrderMain(@RequestParam Map<String, String> dateMap,
			                          HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);

		//fixedSearchPeriod값을 받아 저장
		String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
		
		//기간 초기화
		String beginDate=null,endDate=null;
		
		//fixedSearchPeriod값 가공해 dateMap에 put
		String [] tempDate=calcSearchPeriod(fixedSearchPeriod).split(",");
		beginDate=tempDate[0];
		endDate=tempDate[1];
		dateMap.put("beginDate", beginDate);
		dateMap.put("endDate", endDate);
		
		//condMap에 put 후 listNewOrder수행.
		HashMap<String,Object> condMap=new HashMap<String,Object>();
		condMap.put("beginDate",beginDate);
		condMap.put("endDate", endDate);
		List<OrderVO> newOrderList=adminOrderService.listNewOrder(condMap);
		
		//리턴된 배송리스트 member_list를  mav의 member_list에 부여		
		mav.addObject("newOrderList",newOrderList);
		
		//날짜형식지정
		String beginDate1[]=beginDate.split("-");
		String endDate2[]=endDate.split("-");
		mav.addObject("beginYear",beginDate1[0]);
		mav.addObject("beginMonth",beginDate1[1]);
		mav.addObject("beginDay",beginDate1[2]);
		mav.addObject("endYear",endDate2[0]);
		mav.addObject("endMonth",endDate2[1]);
		mav.addObject("endDay",endDate2[2]);
		
		return mav;
	}
	
	
	
	//주문수정 - 배송수정
	@Override
	@RequestMapping(value="/modifyDeliveryState.do" ,method={RequestMethod.POST})
	public ResponseEntity modifyDeliveryState(@RequestParam Map<String, String> deliveryMap, 
			                        HttpServletRequest request, HttpServletResponse response)  throws Exception {
		//modifyDeliveryState 수행
		adminOrderService.modifyDeliveryState(deliveryMap);
		
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		
		//완료시 message mod_success return
		message  = "mod_success";
		resEntity =new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	
}
