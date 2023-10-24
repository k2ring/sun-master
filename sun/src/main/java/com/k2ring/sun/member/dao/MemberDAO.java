package com.k2ring.sun.member.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.k2ring.sun.member.vo.MemberVO;

public interface MemberDAO {
	//로그인
	public MemberVO login(Map loginMap) throws DataAccessException;

	//회원가입
	public void insertNewMember(MemberVO memberVO) throws DataAccessException;
	
	//아이디 중복확인
	public String selectOverlappedID(String id) throws DataAccessException;
}
