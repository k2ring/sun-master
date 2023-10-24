package com.k2ring.sun.order.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.k2ring.sun.order.vo.OrderVO;


@Repository("orderDAO")
public class OrderDAOImpl implements OrderDAO {
	@Autowired
	private SqlSession sqlSession;
	
	//�ֹ��ϱ�
	public void insertNewOrder(List<OrderVO> myOrderList) throws DataAccessException{
		//���ϵ� �ֹ���ȣ�� �Բ� �ֹ� table�� �ֹ������� insert�Ѵ�.
		int order_id=selectOrderID();
		for(int i=0; i<myOrderList.size();i++){
			OrderVO orderVO =(OrderVO)myOrderList.get(i);
			//�ֹ���ȣ ��ü�� set
			orderVO.setOrder_id(order_id);
			sqlSession.insert("mapper.order.insertNewOrder",orderVO);
		}
	}	
	
	private int selectOrderID() throws DataAccessException{
		//�ֹ���ȣ �������� �����Ͽ� ������� ��ȯ�Ѵ�.
		int result = sqlSession.selectOne("mapper.order.selectOrderID");
		return result;
	}
	
	//�ֹ��Ϸ�� ��ٱ��Ͽ��� ��ǰ ����
	public void removeGoodsFromCart(List<OrderVO> myOrderList)throws DataAccessException{
		for(int i=0; i<myOrderList.size();i++){
			//�ֹ���ǰ����Ʈ�� ������ ������ delete���� myOrderList��ŭ, for���� ���� �����Ѵ�.
			OrderVO orderVO =(OrderVO)myOrderList.get(i);
			sqlSession.delete("mapper.order.deleteGoodsFromCart",orderVO);	
		}
	}	
}


