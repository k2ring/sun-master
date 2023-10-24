package com.k2ring.sun.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.k2ring.sun.order.dao.OrderDAO;
import com.k2ring.sun.order.vo.OrderVO;


@Service("orderService")
@Transactional(propagation=Propagation.REQUIRED)
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDAO orderDAO;

	//�ֹ��ϱ�
	public void addNewOrder(List<OrderVO> myOrderList) throws Exception{
		//�ֹ��ϱ�
		orderDAO.insertNewOrder(myOrderList);
		
		//īƮ���� �ֹ� ��ǰ �����Ѵ�.
		orderDAO.removeGoodsFromCart(myOrderList);
		System.out.println("��ٱ��Ͽ��� �ش� ��ǰ�� �����߽��ϴ�.");
	}	
	
}

