package com.k2ring.sun.member.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.k2ring.sun.member.vo.MemberVO;

public interface MemberDAO {
	//�α���
	public MemberVO login(Map loginMap) throws DataAccessException;

	//ȸ������
	public void insertNewMember(MemberVO memberVO) throws DataAccessException;
	
	//���̵� �ߺ�Ȯ��
	public String selectOverlappedID(String id) throws DataAccessException;
}
