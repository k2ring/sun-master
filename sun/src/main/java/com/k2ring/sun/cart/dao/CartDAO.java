package com.k2ring.sun.cart.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.k2ring.sun.cart.vo.CartVO;
import com.k2ring.sun.goods.vo.GoodsVO;

public interface CartDAO {
	//��ٱ���
	public List<CartVO> selectCartList(CartVO cartVO) throws DataAccessException;
	public List<GoodsVO> selectGoodsList(List<CartVO> cartList) throws DataAccessException;
	
	//��ٱ��� �߰�
	public boolean selectCountInCart(CartVO cartVO) throws DataAccessException;
	public void insertGoodsInCart(CartVO cartVO) throws DataAccessException;
	
	//��ٱ��� ����
	public void deleteCartGoods(int cart_id) throws DataAccessException;
	
	//��ٱ��� ����
	public void updateCartGoodsQty(CartVO cartVO) throws DataAccessException;

}
