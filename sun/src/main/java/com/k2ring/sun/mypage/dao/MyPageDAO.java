package com.k2ring.sun.mypage.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.k2ring.sun.member.vo.MemberVO;
import com.k2ring.sun.order.vo.OrderVO;

public interface MyPageDAO {

	//�ֹ����
	public List<OrderVO> selectMyOrderHistoryList(Map dateMap) throws DataAccessException;

	//�ֹ����
	public void updateMyOrderCancel(String order_id) throws DataAccessException;
	
	//��ǰ
	public void updateMyOrderReturn(String order_id) throws DataAccessException;
	
	//��ȯ
	public void updateMyOrderExchange(String order_id) throws DataAccessException;
	
	//������
	public MemberVO selectMyDetailInfo(String member_id) throws DataAccessException;
	
	//�� ���� ����
	public void updateMyInfo(Map memberMap) throws DataAccessException;
	
	//ȸ��Ż��
	public void deleteMember(String member_id) throws DataAccessException;
	
}
