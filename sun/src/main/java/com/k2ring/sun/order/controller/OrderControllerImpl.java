package com.k2ring.sun.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.k2ring.sun.common.base.BaseController;
import com.k2ring.sun.goods.vo.GoodsVO;
import com.k2ring.sun.member.vo.MemberVO;
import com.k2ring.sun.order.service.OrderService;
import com.k2ring.sun.order.vo.OrderVO;
@Controller("orderController")
@RequestMapping(value = "/order")
public class OrderControllerImpl extends BaseController implements OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderVO orderVO;

	@RequestMapping(value = "/orderEachGoods.do", method = RequestMethod.POST)
	public ModelAndView orderEachGoods(@ModelAttribute("orderVO") OrderVO _orderVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		session = request.getSession();
          // �α��� ���°�����
		Boolean isLogOn = (Boolean) session.getAttribute("isLogOn");
		String action = (String) session.getAttribute("action");
		if (isLogOn == null || isLogOn == false) { //null�� ���ų� false �Ͻ�
			session.setAttribute("orderInfo", _orderVO); 
			session.setAttribute("action", "/order/orderEachGoods.do");
			return new ModelAndView("redirect:/member/loginForm.do");  // �α׿� ���°� null or false �Ͻ� �α��� �������� �Ѿ.
		} else {
			if (action != null && action.equals("/order/orderEachGoods.do")) {
				orderVO = (OrderVO) session.getAttribute("orderInfo");      //���� �ƴҰ�� ��ǰ�ֹ� �������� �Ѿ.
				session.removeAttribute("action");
			} else {
				orderVO = _orderVO;
			}
		}

		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);

		List myOrderList = new ArrayList<OrderVO>();
		myOrderList.add(orderVO);

		MemberVO memberInfo = (MemberVO) session.getAttribute("memberInfo");

		session.setAttribute("myOrderList", myOrderList);
		session.setAttribute("orderer", memberInfo);
		return mav;
	}
//īƮ ��ü ����Ʈ ��ȸ�ϱ�.
	@RequestMapping(value = "/orderAllCartGoods.do", method = RequestMethod.POST)
	public ModelAndView orderAllCartGoods(@RequestParam("cart_goods_qty") String[] cart_goods_qty,    //īƮ id
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		Map cartMap = (Map) session.getAttribute("cartMap");
		List myOrderList = new ArrayList<OrderVO>();
		

		List<GoodsVO> myGoodsList = (List<GoodsVO>) cartMap.get("myGoodsList"); //��ǰ����Ʈ ������.
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");

		for (int i = 0; i < cart_goods_qty.length; i++) { 
			if(cart_goods_qty[i].contains(":")) {
				String[] cart_goods = cart_goods_qty[i].split(":");  //: ���Կ��� Ȯ��.
				for (int j = 0; j < myGoodsList.size(); j++) { 
					GoodsVO goodsVO = myGoodsList.get(j);
					int goods_id = goodsVO.getGoods_id();
					if (goods_id == Integer.parseInt(cart_goods[0])) {
						OrderVO _orderVO = new OrderVO();
						String goods_title = goodsVO.getGoods_title();
						int goods_price = goodsVO.getGoods_price();
						String goods_fileName = goodsVO.getGoods_fileName();
						_orderVO.setGoods_id(goods_id);
						_orderVO.setGoods_title(goods_title);
						_orderVO.setGoods_price(goods_price);
						_orderVO.setGoods_fileName(goods_fileName);
						_orderVO.setOrder_goods_qty(Integer.parseInt(cart_goods[1]));
						myOrderList.add(_orderVO);
						break;
					}
				}
				
			}
		}
		session.setAttribute("myOrderList", myOrderList);
		session.setAttribute("orderer", memberVO);
		return mav;
	}
	//���� �Ϸ�.
	@RequestMapping(value = "/payToOrderGoods.do", method = RequestMethod.POST)
	public ModelAndView payToOrderGoods(@RequestParam Map<String, String> receiverMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		System.out.println("���� �Ϸ���� �����Խ��ϴ�.");
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("orderer"); //orderer ������.
		String member_id = memberVO.getMember_id();
		String order_name = memberVO.getMember_name();
		String orderer_hp = memberVO.getHp1();
		List<OrderVO> myOrderList = (List<OrderVO>) session.getAttribute("myOrderList");

		for (int i = 0; i < myOrderList.size(); i++) { 
			OrderVO orderVO = (OrderVO) myOrderList.get(i);
			orderVO.setMember_id(member_id);
			orderVO.setReceiver_name(receiverMap.get("receiver_name"));
			orderVO.setReceiver_hp1(receiverMap.get("receiver_hp1"));
			orderVO.setDelivery_address(receiverMap.get("delivery_address"));
			orderVO.setDelivery_state("delivery_prepared");
			orderVO.setPay_method(receiverMap.get("pay_method"));
			orderVO.setCard_com_name(receiverMap.get("card_com_name"));
			orderVO.setCard_pay_month(receiverMap.get("card_pay_month"));
			orderVO.setPay_orderer_hp_num(receiverMap.get("pay_orderer_hp_num"));
			orderVO.setOrderer_hp(orderer_hp);
			myOrderList.set(i, orderVO);
		} // end for

		orderService.addNewOrder(myOrderList);
		mav.addObject("myOrderInfo", receiverMap);
		mav.addObject("myOrderList", myOrderList);
		return mav;
	}

}