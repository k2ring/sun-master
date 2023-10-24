package com.k2ring.sun.cart.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.k2ring.sun.cart.dao.CartDAO;
import com.k2ring.sun.cart.vo.CartVO;
import com.k2ring.sun.goods.vo.GoodsVO;

@Service("cartService")
@Transactional(propagation = Propagation.REQUIRED)
public class CartServiceImpl implements CartService {
	@Autowired
	private CartDAO cartDAO;

	// ��ٱ���
	public Map<String, List> myCartList(CartVO cartVO) throws Exception {
		Map<String, List> cartMap = new HashMap<String, List>();

		//��ٱ��� ���� ������ list�� ����
		List<CartVO> myCartList = cartDAO.selectCartList(cartVO);
		
		//����Ʈ�� ���� ��� return null
		if (myCartList.size() == 0) {return null;}
		
		//��ٱ��� ����Ʈ�� �´� goodList�� cartMap�� put �� ����. 
		List<GoodsVO> myGoodsList = cartDAO.selectGoodsList(myCartList);
		try {
			cartMap.put("myCartList", myCartList);
			cartMap.put("myGoodsList", myGoodsList);
		} catch (Exception e) {e.printStackTrace();}

		return cartMap;
	}

	// ��ٱ��� �߰�, �ߺ����� Ȯ�� �� �߰��Ѵ�.
	public boolean findCartGoods(CartVO cartVO) throws Exception {return cartDAO.selectCountInCart(cartVO);	}
	public void addGoodsInCart(CartVO cartVO) throws Exception {cartDAO.insertGoodsInCart(cartVO);}

	// ��ٱ��� ����
	public void removeCartGoods(int cart_id) throws Exception {cartDAO.deleteCartGoods(cart_id);}

	// ��ٱ��� ����
	public boolean modifyCartQty(CartVO cartVO) throws Exception {
		boolean result = true;
		cartDAO.updateCartGoodsQty(cartVO);
		return result;
	}

}
