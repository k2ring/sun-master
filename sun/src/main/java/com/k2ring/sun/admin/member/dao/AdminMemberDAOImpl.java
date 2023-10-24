package com.k2ring.sun.admin.member.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.k2ring.sun.member.vo.MemberVO;

@Repository("adminMemberDao")
public class AdminMemberDAOImpl  implements AdminMemberDAO{
	@Autowired
	private SqlSession sqlSession;
	
	//회원관리
	public ArrayList<MemberVO> listMember(HashMap condMap) throws DataAccessException{
		ArrayList<MemberVO> memberList=(ArrayList)sqlSession.selectList("mapper.admin.member.listMember",condMap);
		return memberList;
	}

}
