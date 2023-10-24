package com.k2ring.sun.admin.order.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.k2ring.sun.admin.order.dao.AdminOrderDAO;
import com.k2ring.sun.order.vo.OrderVO;


@Service("adminOrderService")
@Transactional(propagation=Propagation.REQUIRED)
public class AdminOrderServiceImpl implements AdminOrderService {
	@Autowired
	private AdminOrderDAO adminOrderDAO;
	
	
	//주문목록
	public List<OrderVO>listNewOrder(Map condMap) throws Exception{
		return adminOrderDAO.selectNewOrderList(condMap);
	}
	

	//주문수정 - 배송수정
	@Override
	public void  modifyDeliveryState(Map deliveryMap) throws Exception{
		adminOrderDAO.updateDeliveryState(deliveryMap);
	}
	

}
