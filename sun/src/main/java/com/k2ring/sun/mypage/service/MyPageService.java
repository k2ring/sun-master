package com.k2ring.sun.mypage.service;

import java.util.List;
import java.util.Map;

import com.k2ring.sun.member.vo.MemberVO;
import com.k2ring.sun.order.vo.OrderVO;

public interface MyPageService{

	//�ֹ����
	public List<OrderVO> listMyOrderHistory(Map dateMap) throws Exception;

	//�ֹ����
	public void cancelOrder(String order_id) throws Exception;

	//��ǰ
	public void returnOrder(String order_id) throws Exception;
	
	//��ȯ
	public void exchangeOrder(String order_id) throws Exception;

	//������
	public MemberVO myDetailInfo(String member_id) throws Exception;
	
	//�� ���� ����
	public MemberVO  modifyMyInfo(Map memberMap) throws Exception;
	
	//ȸ��Ż��
	public void  deleteMember(String member_id) throws Exception;
	
	
	

}
