package com.k2ring.sun.order.dao;


import java.util.List;

import org.springframework.dao.DataAccessException;

import com.k2ring.sun.order.vo.OrderVO;



public interface OrderDAO {
	
	//주문하기
	public void insertNewOrder(List<OrderVO> myOrderList) throws DataAccessException;

	//주문완료시 장바구니에서 상품 제거
	public void removeGoodsFromCart(List<OrderVO> myOrderList)throws DataAccessException;
	
}
