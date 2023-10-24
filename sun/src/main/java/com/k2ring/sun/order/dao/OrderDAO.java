package com.k2ring.sun.order.dao;


import java.util.List;

import org.springframework.dao.DataAccessException;

import com.k2ring.sun.order.vo.OrderVO;



public interface OrderDAO {
	
	//�ֹ��ϱ�
	public void insertNewOrder(List<OrderVO> myOrderList) throws DataAccessException;

	//�ֹ��Ϸ�� ��ٱ��Ͽ��� ��ǰ ����
	public void removeGoodsFromCart(List<OrderVO> myOrderList)throws DataAccessException;
	
}
