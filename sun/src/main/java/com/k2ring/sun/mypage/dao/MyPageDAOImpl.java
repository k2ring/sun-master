package com.k2ring.sun.mypage.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.k2ring.sun.member.vo.MemberVO;
import com.k2ring.sun.order.vo.OrderVO;

@Repository("myPageDAO")
public class MyPageDAOImpl implements MyPageDAO{
	@Autowired
	private SqlSession sqlSession;

	//주문목록
	public List<OrderVO> selectMyOrderHistoryList(Map dateMap) throws DataAccessException{
		List<OrderVO> myOrderHistList=(List)sqlSession.selectList("mapper.mypage.selectMyOrderHistoryList",dateMap);
		return myOrderHistList;
	}
	
	//주문취소
	public void updateMyOrderCancel(String order_id) throws DataAccessException{
		sqlSession.update("mapper.mypage.updateMyOrderCancel",order_id);
	}
	
	//반품
	public void updateMyOrderReturn(String order_id) throws DataAccessException{
		sqlSession.update("mapper.mypage.updateMyOrderReturn",order_id);
	}
	
	//교환
	public void updateMyOrderExchange(String order_id) throws DataAccessException{
		sqlSession.update("mapper.mypage.updateMyOrderExchange",order_id);
	}
	
	//내정보
	public MemberVO selectMyDetailInfo(String member_id) throws DataAccessException{
		MemberVO memberVO=(MemberVO)sqlSession.selectOne("mapper.mypage.selectMyDetailInfo",member_id);
		return memberVO;
		
	}
	
	//내 정보 수정
	public void updateMyInfo(Map memberMap) throws DataAccessException{
		sqlSession.update("mapper.mypage.updateMyInfo",memberMap);
	}
	
	//회원탈퇴
	public void deleteMember(String member_id) throws DataAccessException{
		sqlSession.update("mapper.mypage.deleteMember",member_id);
	}
	
	
	
	
}
