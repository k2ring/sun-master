package com.k2ring.sun.goods.controller;

import java.util.ArrayList;
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

import com.k2ring.sun.common.base.BaseController;
import com.k2ring.sun.goods.service.GoodsService;
import com.k2ring.sun.goods.vo.GoodsVO;

import net.sf.json.JSONObject;
@Controller("goodsController")
@RequestMapping(value="/goods")
public class GoodsControllerImpl extends BaseController   implements GoodsController {
	@Autowired
	private GoodsService goodsService;
	
// �긽�뭹 硫붾돱
	@Override
	@RequestMapping(value="menuGoods.do" ,method = RequestMethod.GET)
	public ModelAndView menuGoods(String menuGoods, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		List<GoodsVO> goodsList=goodsService.menuGoods(menuGoods);
		ModelAndView mav = new ModelAndView(viewName);
		//goodsList 異붽�
		//menuGoods 異붽�
		mav.addObject("goodsList", goodsList);
		mav.addObject("menuGoods", menuGoods);
		return mav;
	}
	

	//�궎�썙�뱶 �꽑�깮.
	@RequestMapping(value="/keywordSearch.do",method = RequestMethod.GET,produces = "application/text; charset=utf8")
	public @ResponseBody String  keywordSearch(@RequestParam("keyword") String keyword,  // 由ы�섏뒪�듃 keyword 諛쏆븘�샂.
			                                  HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		
		//keywordr媛� null �씠嫄곕굹 怨듬갚�씪�뻹 null �쓣 諛섑솚�븳�떎.
		if(keyword == null || keyword.equals(""))
		   return null ;
	
		//��臾몄옄濡� 蹂��솚
		keyword = keyword.toUpperCase();
	    List<String> keywordList =goodsService.keywordSearch(keyword);
	    //臾몄옄�뿴 由ъ뒪�듃 keywordList 濡� �궎�썙�뱶 �꽑�깮
		JSONObject jsonObject = new JSONObject(); //json媛앹껜 �깮�꽦�븯怨�
		jsonObject.put("keyword", keywordList); //key :keyword value:keywordList 濡� �궡蹂대궦�떎.
	    String jsonInfo = jsonObject.toString();
	    
	    //return jsonInfo 
	    return jsonInfo ;
	}
	
	
	//占싯삼옙
	@RequestMapping(value="/searchGoods.do" ,method = RequestMethod.GET)
	public ModelAndView searchGoods(@RequestParam("searchWord") String searchWord,
			                       HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName=(String)request.getAttribute("viewName");
		List<GoodsVO> goodsList=goodsService.searchGoods(searchWord);
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("goodsList", goodsList);
		return mav;
	}
	
	
	//�긽�뭹 �긽�꽭
	@RequestMapping(value="/goodsDetail.do" ,method = RequestMethod.GET)
	public ModelAndView goodsDetail(@RequestParam("goods_id") String goods_id,
			                       HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session=request.getSession();
		
		//�긽�뭹 goodsDetail 硫붿냼�뱶瑜� �샇異쒗븯�뿬 goods id瑜� �넗��濡� �긽�뭹�젙蹂대�� goodsMap�뿉 ���옣�빀�땲�떎.
		Map goodsMap=goodsService.goodsDetail(goods_id);
		mav.addObject("goodsMap", goodsMap);
		
		
		//goodsvo�씪怨� ���옣�맂 媛앹콈瑜� 媛�吏�怨좎샂.
		
		GoodsVO goodsVO=(GoodsVO)goodsMap.get("goodsVO");
		
		//鍮좊Ⅸ 二쇰Ц 由ъ뒪�듃�뿉 異붽��빀�땲�떎.
		addGoodsInQuick(goods_id,goodsVO,session);
		
		return mav;
	}
	
	
	
// 鍮좊Ⅸ援щℓ
	private void addGoodsInQuick(String goods_id,GoodsVO goodsVO,HttpSession session){
		// 以묐났 �뿬遺�寃��궗 
		boolean already_existed=false;
		
		   // 鍮좊Ⅸ �긽�뭹 由ъ뒪�듃 quickGoodsList瑜� 媛��졇�샃�땲�떎.
		List<GoodsVO> quickGoodsList;
		quickGoodsList=(ArrayList<GoodsVO>)session.getAttribute("quickGoodsList");
		
		//�씠誘� 議댁옱�븷寃쎌슦 
		if(quickGoodsList!=null){
			
			//鍮좊Ⅸ �긽�뭹�씠 3�씠�긽�쑝濡� 議댁옱�븳�떎硫�
			if(quickGoodsList.size() < 3){
				for(int i=0; i<quickGoodsList.size();i++){
					String _goodsBean=String.valueOf(quickGoodsList.get(i).getGoods_id());
					   // quickGoodsList�뿉�꽌 i踰덉㎏ �긽�뭹�쓽 goods_id瑜� 臾몄옄�뿴濡� 蹂��솚�븯�뿬 _goodsBean 蹂��닔�뿉 ���옣�빀�땲�떎.
					
					
					// goods_id �� _goodsBean 怨� 媛숈쑝硫� �씠誘� �엳�떎�뒗 �쑜�씠誘�濡� already_existed=true; 諛섎났臾몄쓣 �깉異쒗븳�떎.
					if(goods_id.equals(_goodsBean)){
						already_existed=true;
						break;
					}
				}
				  // already_existed媛� false�씤 寃쎌슦, 以묐났�씠 �븘�땶 �긽�뭹�쓣 鍮좊Ⅸ �긽�뭹 由ъ뒪�듃�뿉 異붽��빀�땲�떎.
				if(already_existed==false){
					quickGoodsList.add(goodsVO);
				}

			//3媛� �씠�긽�씤寃쎌슦
			}else {
				//泥� 踰덉�� �긽�뭹 �젣嫄�
				quickGoodsList.remove(0);
				quickGoodsList.add(goodsVO);
			}
		
		
		//鍮좊Ⅸ �긽�뭹 由ъ뒪�듃媛� 議댁옱�븯吏� �븡�뒗寃쎌슦  �깉濡쒖슫 ArrayList瑜� �깮�꽦�븳�떎.
		}else{
			quickGoodsList =new ArrayList<GoodsVO>();
			quickGoodsList.add(goodsVO);
		}
		
		//鍮좊Ⅸ �긽�뭹 由ъ뒪�듃�� 由ъ뒪�듃 �겕湲곕�� �꽭�뀡�뿉 �쟻�슜�빀�땲�떎.
		session.setAttribute("quickGoodsList",quickGoodsList);
		session.setAttribute("quickGoodsListNum", quickGoodsList.size());
	}
	
}