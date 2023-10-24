package com.k2ring.sun.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.k2ring.sun.common.base.BaseController;
import com.k2ring.sun.member.service.MemberService;
import com.k2ring.sun.member.vo.MemberVO;

@Controller("memberController")
@RequestMapping(value = "/member")
public class MemberControllerImpl extends BaseController implements MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberVO memberVO;

	//로그인
	@Override
	@RequestMapping(value="/login.do" ,method = RequestMethod.POST)
	public ModelAndView login(@RequestParam Map<String, String> loginMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		memberVO = memberService.login(loginMap);
		
		//memberVO가 존재할 경우
		if (memberVO != null && memberVO.getMember_id() != null) {
			HttpSession session = request.getSession();
			session = request.getSession();
			
			//로그인 여부 isLogOn와 회원정보 memberInfo를 세션에 저장한다.
			session.setAttribute("isLogOn", true);
			session.setAttribute("memberInfo", memberVO);
			
			//메인페이지로 이동.
			mav.setViewName("redirect:/main/main.do");
			
		} else { //memberVO가 존재하지않을 경우 message를 담아 return + login페이지로 이동
			String message = "아이디나  비밀번호가 틀립니다. 다시 로그인해주세요";
			mav.addObject("message", message);
			mav.setViewName("/member/login");
		}
		return mav;
	}
	
	
	

	//회원가입
	@Override
	@RequestMapping(value="/join.do" ,method = RequestMethod.POST)
	public ResponseEntity addMember(@ModelAttribute("memberVO") MemberVO _memberVO,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		try {
			//회원가입을 try, addMember 성공시 안내문구와 함께  login페이지로 이동한다.
		    memberService.addMember(_memberVO);
		    message  = "<script>";
		    message +=" alert('햇빛농장에 오신걸 환영합니다!');";
		    message += " location.href='"+request.getContextPath()+"/member/login.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			//오류발생시, 회원가입페이지로 재이동
			message  = "<script>";
		    message += " location.href='"+request.getContextPath()+"/member/join.do';";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		
		//각 케이스에 따른 위 설정값 return
		return resEntity;
	}

	
	
	
	//아이디 중복확인
	@Override
	@RequestMapping(value="/overlapped.do" ,method = RequestMethod.POST)
	public ResponseEntity overlapped(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseEntity resEntity = null;
		
		//overlapped의 경과를 매핑해 return 한다.
		String result = memberService.overlapped(id);
		resEntity = new ResponseEntity(result, HttpStatus.OK);
		return resEntity;
	}
	
	
	
	//로그아웃
	@Override
	@RequestMapping(value="/logout.do" ,method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session=request.getSession();
		//세션 설정 초기화 및 메인페이지 이동.
		session.setAttribute("isLogOn", false);
		session.removeAttribute("memberInfo");
		mav.setViewName("redirect:/main/main.do");
		return mav;
	}

	
}
