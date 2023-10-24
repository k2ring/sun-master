package com.k2ring.sun.mypage.service;

import java.util.List;
import java.util.Map;

import com.k2ring.sun.member.vo.MemberVO;
import com.k2ring.sun.order.vo.OrderVO;

public interface MyPageService{

	//주문목록
	public List<OrderVO> listMyOrderHistory(Map dateMap) throws Exception;

	//주문취소
	public void cancelOrder(String order_id) throws Exception;

	//반품
	public void returnOrder(String order_id) throws Exception;
	
	//교환
	public void exchangeOrder(String order_id) throws Exception;

	//내정보
	public MemberVO myDetailInfo(String member_id) throws Exception;
	
	//내 정보 수정
	public MemberVO  modifyMyInfo(Map memberMap) throws Exception;
	
	//회원탈퇴
	public void  deleteMember(String member_id) throws Exception;
	
	
	

}
