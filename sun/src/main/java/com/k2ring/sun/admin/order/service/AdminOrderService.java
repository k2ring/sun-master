package com.k2ring.sun.admin.order.service;

import java.util.List;
import java.util.Map;

import com.k2ring.sun.order.vo.OrderVO;

public interface AdminOrderService {
	
	//�ֹ����
	public List<OrderVO> listNewOrder(Map condMap) throws Exception;

	//�ֹ����� - ��ۼ���
	public void modifyDeliveryState(Map deliveryMap) throws Exception;
}
