package com.k2ring.sun.order.service;

import java.util.List;

import com.k2ring.sun.order.vo.OrderVO;


public interface OrderService {

	//�ֹ��ϱ� - �����Ϸ��� �ֹ� table�� insert �ȴ�
	public void addNewOrder(List<OrderVO> myOrderList) throws Exception;
}
