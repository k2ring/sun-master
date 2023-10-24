package com.k2ring.sun.admin.member.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;

import com.k2ring.sun.member.vo.MemberVO;

public interface AdminMemberDAO {
	// 회원관리
	public ArrayList<MemberVO> listMember(HashMap condMap) throws DataAccessException;
}
