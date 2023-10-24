package com.k2ring.sun.admin.order.dao;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.k2ring.sun.order.vo.OrderVO;

public interface AdminOrderDAO {

	//주문목록
	public ArrayList<OrderVO> selectNewOrderList(Map condMap) throws DataAccessException;

	//주문수정 - 배송수정
	public void updateDeliveryState(Map deliveryMap) throws DataAccessException;
}
