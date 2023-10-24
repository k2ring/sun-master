package com.k2ring.sun.mypage.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.k2ring.sun.member.vo.MemberVO;
import com.k2ring.sun.order.vo.OrderVO;

public interface MyPageDAO {

	//주문목록
	public List<OrderVO> selectMyOrderHistoryList(Map dateMap) throws DataAccessException;

	//주문취소
	public void updateMyOrderCancel(String order_id) throws DataAccessException;
	
	//반품
	public void updateMyOrderReturn(String order_id) throws DataAccessException;
	
	//교환
	public void updateMyOrderExchange(String order_id) throws DataAccessException;
	
	//내정보
	public MemberVO selectMyDetailInfo(String member_id) throws DataAccessException;
	
	//내 정보 수정
	public void updateMyInfo(Map memberMap) throws DataAccessException;
	
	//회원탈퇴
	public void deleteMember(String member_id) throws DataAccessException;
	
}
