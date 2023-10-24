package com.k2ring.sun.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.k2ring.sun.common.base.BaseController;
import com.k2ring.sun.goods.service.GoodsService;
import com.k2ring.sun.goods.vo.GoodsVO;


@Controller("mainController")
@EnableAspectJAutoProxy
public class MainController extends BaseController{
	
	@Autowired
	private GoodsService goodsService;
	
	///main/main.do로 요청시 listGoods, 상품목록을 불러와 goodsMap에 저장 후 viewName과 함께 리턴.
	@RequestMapping(value= "/main/main.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session;
		ModelAndView mav=new ModelAndView();
		String viewName=(String)request.getAttribute("viewName");
		mav.setViewName(viewName);
		
		session=request.getSession();
		session.setAttribute("side_menu", "user");
		Map<String, List<GoodsVO>> goodsMap=goodsService.listGoods();
		mav.addObject("goodsMap", goodsMap);
		System.out.println(goodsMap);
		return mav;
	}
	
}
