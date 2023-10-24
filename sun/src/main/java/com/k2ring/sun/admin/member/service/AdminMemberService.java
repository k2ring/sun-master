package com.k2ring.sun.admin.member.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.k2ring.sun.member.vo.MemberVO;

public interface AdminMemberService {
	// 회원관리
	public ArrayList<MemberVO> listMember(HashMap condMap) throws Exception;
}
