package com.k2ring.sun.admin.order.service;

import java.util.List;
import java.util.Map;

import com.k2ring.sun.order.vo.OrderVO;

public interface AdminOrderService {
	
	//주문목록
	public List<OrderVO> listNewOrder(Map condMap) throws Exception;

	//주문수정 - 배송수정
	public void modifyDeliveryState(Map deliveryMap) throws Exception;
}
