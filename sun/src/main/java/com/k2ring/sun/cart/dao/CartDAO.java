package com.k2ring.sun.cart.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.k2ring.sun.cart.vo.CartVO;
import com.k2ring.sun.goods.vo.GoodsVO;

public interface CartDAO {
	//장바구니
	public List<CartVO> selectCartList(CartVO cartVO) throws DataAccessException;
	public List<GoodsVO> selectGoodsList(List<CartVO> cartList) throws DataAccessException;
	
	//장바구니 추가
	public boolean selectCountInCart(CartVO cartVO) throws DataAccessException;
	public void insertGoodsInCart(CartVO cartVO) throws DataAccessException;
	
	//장바구니 삭제
	public void deleteCartGoods(int cart_id) throws DataAccessException;
	
	//장바구니 수정
	public void updateCartGoodsQty(CartVO cartVO) throws DataAccessException;

}
