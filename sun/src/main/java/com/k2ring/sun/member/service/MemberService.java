package com.k2ring.sun.member.service;

import java.util.Map;

import com.k2ring.sun.member.vo.MemberVO;

public interface MemberService {
	//�α���
	public MemberVO login(Map  loginMap) throws Exception;
	
	//ȸ������
	public void addMember(MemberVO memberVO) throws Exception;
	
	//���̵� �ߺ�Ȯ��
	public String overlapped(String id) throws Exception;
}
