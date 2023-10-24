package com.k2ring.sun.mypage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.k2ring.sun.common.base.BaseController;
import com.k2ring.sun.member.vo.MemberVO;
import com.k2ring.sun.mypage.service.MyPageService;
import com.k2ring.sun.order.vo.OrderVO;

@Controller("myPageController")
@RequestMapping(value = "/mypage")
public class MyPageControllerImpl extends BaseController implements MyPageController {
	@Autowired
	private MyPageService myPageService;

	@Autowired
	private MemberVO memberVO;

	// �ֹ����
	@Override
	@RequestMapping(value = "/listMyOrderHistory.do", method = RequestMethod.GET)
	public ModelAndView listMyOrderHistory(@RequestParam Map<String, String> dateMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();

		MemberVO logincheck = (MemberVO) session.getAttribute("memberInfo");

		if (logincheck == null) {
	        mav.setViewName("redirect:/main/main.do");
	    
		} else {

			String member_id = logincheck.getMember_id();

			// ��ȸ�Ⱓ fixedSearchPeriod get
			String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
			// ��ȸ �Ⱓ �ʱ�ȭ
			String beginDate = null, endDate = null;
			String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
			beginDate = tempDate[0];
			endDate = tempDate[1];

			// ��ȸ�Ⱓ�� member_id�� dateMap�� put�� ��ȸ
			dateMap.put("beginDate", beginDate);
			dateMap.put("endDate", endDate);
			dateMap.put("member_id", member_id);
			List<OrderVO> myOrderHistList = myPageService.listMyOrderHistory(dateMap);

			// �˻����ڸ� ��,��,�Ϸ� �и��ؼ� ȭ�鿡 ����
			String[] beginDate1 = beginDate.split("-");
			String[] endDate1 = endDate.split("-");
			mav.addObject("beginYear", beginDate1[0]);
			mav.addObject("beginMonth", beginDate1[1]);
			mav.addObject("beginDay", beginDate1[2]);
			mav.addObject("endYear", endDate1[0]);
			mav.addObject("endMonth", endDate1[1]);
			mav.addObject("endDay", endDate1[2]);
			mav.addObject("myOrderHistList", myOrderHistList);
		}

		return mav;
	}

	// �ֹ����
	@Override
	@RequestMapping(value = "/cancelMyOrder.do", method = RequestMethod.POST)
	public ModelAndView cancelMyOrder(@RequestParam("order_id") String order_id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		// �ֹ� id order_id�� db���� �� cancel_order message ����
		myPageService.cancelOrder(order_id);
		mav.addObject("message", "cancel_order");
		mav.setViewName("redirect:/mypage/listMyOrderHistory.do");
		return mav;
	}

	// ��ǰ
	@Override
	@RequestMapping(value = "/returnMyOrder.do", method = RequestMethod.POST)
	public ModelAndView returnMyOrder(@RequestParam("order_id") String order_id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		// �ֹ� id order_id�� db���� �� returning_goods message ����
		myPageService.returnOrder(order_id);
		mav.addObject("message", "returning_goods");
		mav.setViewName("redirect:/mypage/listMyOrderHistory.do");
		return mav;
	}

	// ��ȯ
	@Override
	@RequestMapping(value = "/exchangeMyOrder.do", method = RequestMethod.POST)
	public ModelAndView exchangeMyOrder(@RequestParam("order_id") String order_id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		// �ֹ� id order_id�� db���� �� exchange_goods message ����
		myPageService.exchangeOrder(order_id);
		mav.addObject("message", "exchange_goods");
		mav.setViewName("redirect:/mypage/listMyOrderHistory.do");
		return mav;
	}

	// ������
	@Override
	@RequestMapping(value = "/myDetailInfo.do", method = RequestMethod.GET)
	public ModelAndView myDetailInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// �ܼ��� �������� �̵�, myDetailInfo
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		return mav;
	}

	// �� ���� ����
	@Override
	@RequestMapping(value = "/modifyMyInfo.do", method = RequestMethod.POST)
	public ResponseEntity modifyMyInfo(@RequestParam("member_pw") String member_pw, @RequestParam("hp1") String hp1,
			@RequestParam("zipcode") String zipcode, @RequestParam("member_address") String member_address,
			@RequestParam("subaddress") String subaddress, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, String> memberMap = new HashMap<String, String>();

		HttpSession session = request.getSession();
		memberVO = (MemberVO) session.getAttribute("memberInfo");
		String member_id = memberVO.getMember_id();

		// �޾ƿ� ���� memberMap�� put
		memberMap.put("member_pw", member_pw);
		memberMap.put("hp1", hp1);
		memberMap.put("zipcode", zipcode);
		memberMap.put("member_address", member_address);
		memberMap.put("subaddress", subaddress);
		memberMap.put("member_id", member_id);

		// memberMap�� ������ db����
		memberVO = (MemberVO) myPageService.modifyMyInfo(memberMap);

		// ������ ȸ�� ������ �ٽ� ���ǿ� �����Ѵ�.
		session.removeAttribute("memberInfo");
		session.setAttribute("memberInfo", memberVO);

		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();

		// �Ϸ��� message mod_success ����
		message = "mod_success";
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	// ȸ��Ż��
	@Override
	@RequestMapping(value = "/deleteMember.do", method = RequestMethod.POST)
	public ResponseEntity deleteMember(@RequestParam("member_id") String member_id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// @RequestParam���� member_id�� db���� ����
		myPageService.deleteMember(member_id);

		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();

		// �Ϸ� �� message delete_success ����
		message = "delete_success";
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

}
