package com.k2ring.sun.cart.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.k2ring.sun.cart.service.CartService;
import com.k2ring.sun.cart.vo.CartVO;
import com.k2ring.sun.common.base.BaseController;
import com.k2ring.sun.member.vo.MemberVO;

@Controller("cartController")
@RequestMapping(value="/cart")
public class CartControllerImpl extends BaseController implements CartController{
	@Autowired
	private CartService cartService;
	@Autowired
	private CartVO cartVO;
	@Autowired
	private MemberVO memberVO;
	
	//��ٱ���
	@RequestMapping(value="/myCartList.do" ,method = RequestMethod.GET)
	public ModelAndView myCartMain(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		
		HttpSession session=request.getSession();
		MemberVO memberVO=(MemberVO)session.getAttribute("memberInfo");
		String member_id=memberVO.getMember_id();
		
		//ȸ�������� �´� ��ٱ��� ����Ʈ�� �ҷ��´�. 
		cartVO.setMember_id(member_id);
		Map<String ,List> cartMap=cartService.myCartList(cartVO);
		
		session.setAttribute("cartMap", cartMap);
		return mav;
	}
	
	
	//��ٱ��� �߰�
	@RequestMapping(value="/addGoodsInCart.do" ,method = RequestMethod.POST,produces = "application/text; charset=utf8")
	public  @ResponseBody String addGoodsInCart(@RequestParam("goods_id") int goods_id,
			                    HttpServletRequest request, HttpServletResponse response)  throws Exception{
		
		HttpSession session=request.getSession();
		memberVO=(MemberVO)session.getAttribute("memberInfo");
		String member_id=memberVO.getMember_id();
		
		//ȸ�������� �߰��ϰ����ϴ� ��ǰid�� ��ٱ��� �ߺ�üũ �� boolean�� isAreadyExisted�� ���ϰ� ����
		cartVO.setMember_id(member_id);
		cartVO.setGoods_id(goods_id);
		boolean isAreadyExisted=cartService.findCartGoods(cartVO);
		
		//�ߺ��ɰ�� already_existed return, ���� ��� add_success return
		if(isAreadyExisted==true){return "already_existed";}
		else{cartService.addGoodsInCart(cartVO);return "add_success";}
	}
	
	
	
	//��ٱ��� ����
	@RequestMapping(value="/removeCartGoods.do" ,method = RequestMethod.POST)
	public ModelAndView removeCartGoods(@RequestParam("cart_id") int cart_id,
			                          HttpServletRequest request, HttpServletResponse response)  throws Exception{
		ModelAndView mav=new ModelAndView();
		
		//@RequestParam���� cart_id ��ǰ�� ���� �� myCartList�� redirect
		cartService.removeCartGoods(cart_id);
		mav.setViewName("redirect:/cart/myCartList.do");
		return mav;
	}
	
	
	
	//��ٱ��� ����
	@RequestMapping(value="/modifyCartQty.do" ,method = RequestMethod.POST)
	public @ResponseBody String  modifyCartQty(@RequestParam("goods_id") int goods_id,
			                                   @RequestParam("cart_goods_qty") int cart_goods_qty,
			                                    HttpServletRequest request, HttpServletResponse response)  throws Exception{
		HttpSession session=request.getSession();
		memberVO=(MemberVO)session.getAttribute("memberInfo");
		String member_id=memberVO.getMember_id();
		
		//member_id�� @RequestParam���� goods_id�� cart_goods_qty�� ���������� �ݿ��ϰ� ������� ���Ϲ޾� result�� ����
		cartVO.setMember_id(member_id);
		cartVO.setGoods_id(goods_id);
		cartVO.setCart_goods_qty(cart_goods_qty);
		boolean result=cartService.modifyCartQty(cartVO);
		
		//�Ϸ�� modify_success, ���� ��� modify_failed�� ����.
		if(result==true){return "modify_success";}
		else{return "modify_failed";	}
		
	}
	
	
}
