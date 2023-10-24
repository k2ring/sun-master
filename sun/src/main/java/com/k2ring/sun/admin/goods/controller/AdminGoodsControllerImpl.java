package com.k2ring.sun.admin.goods.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.k2ring.sun.admin.goods.service.AdminGoodsService;
import com.k2ring.sun.common.base.BaseController;
import com.k2ring.sun.goods.vo.GoodsVO;
import com.k2ring.sun.goods.vo.ImageFileVO;
import com.k2ring.sun.member.vo.MemberVO;

@Controller("adminGoodsController")
@RequestMapping(value = "/admin/goods")
public class AdminGoodsControllerImpl extends BaseController implements AdminGoodsController {
	private static final String CURR_IMAGE_REPO_PATH = "C:\\sun\\file_repo";
	@Autowired
	private AdminGoodsService adminGoodsService;

	// 상품관리 - 상품리스트
	@RequestMapping(value = "/adminGoodsMain.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView adminGoodsMain(@RequestParam Map<String, String> dateMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		session = request.getSession();
		session.setAttribute("side_menu", "admin_mode");
		
		//fixedSearchPeriod값을 받아 저장
		String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");

		//기간 초기화
		String beginDate = null, endDate = null;
		
		//fixedSearchPeriod값 가공해 dateMap에 put
		String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
		beginDate = tempDate[0];
		endDate = tempDate[1];
		dateMap.put("beginDate", beginDate);
		dateMap.put("endDate", endDate);

		//condMap에 put 후 listNewGoods수행.
		Map<String, Object> condMap = new HashMap<String, Object>();
		condMap.put("beginDate", beginDate);
		condMap.put("endDate", endDate);
		List<GoodsVO> newGoodsList = adminGoodsService.listNewGoods(condMap);
		
		//리턴된 상품리스트 newGoodsList를  mav의 newGoodsList에 부여
		mav.addObject("newGoodsList", newGoodsList);

		//날짜형식지정
		String beginDate1[] = beginDate.split("-");
		String endDate2[] = endDate.split("-");
		mav.addObject("beginYear", beginDate1[0]);
		mav.addObject("beginMonth", beginDate1[1]);
		mav.addObject("beginDay", beginDate1[2]);
		mav.addObject("endYear", endDate2[0]);
		mav.addObject("endMonth", endDate2[1]);
		mav.addObject("endDay", endDate2[2]);
		
		return mav;
	}

	// 상품추가
	@RequestMapping(value = "/addNewGoods.do", method = { RequestMethod.POST })
	public ResponseEntity addNewGoods(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String imageFileName = null;
		
		//form 값을 받아 newGoodsMap에 put
		Map newGoodsMap = new HashMap();
		Enumeration enu = multipartRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			newGoodsMap.put(name, value);
		}

		//세션에서 get한 memberInfo가 reg_id, 수정한이가 됨.
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
		String reg_id = memberVO.getMember_id();

		//baseController upload
		List<ImageFileVO> imageFileList = upload(multipartRequest);
		
		//imageFileList를 받아 setReg_id해 newGoodsMap에 put
		if (imageFileList != null && imageFileList.size() != 0) {
			for (ImageFileVO imageFileVO : imageFileList) {
				imageFileVO.setReg_id(reg_id);
			}
			newGoodsMap.put("imageFileList", imageFileList);
		}

		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		try {
			//상품, 파일정보가 들어있는 newGoodsMap으로 addNewGoods 수행
			int goods_id = adminGoodsService.addNewGoods(newGoodsMap);
			
			//imageFileList가 있을 경우 
			if (imageFileList != null && imageFileList.size() != 0) {
				for (ImageFileVO imageFileVO : imageFileList) {
					
					//temp안에 imageFileName 이름으로 파일 생성,
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					
					//이후 goods_id가 폴더명인 폴더를 하나 만들어 
					File destDir = new File(CURR_IMAGE_REPO_PATH + "\\" + goods_id);
					
					//이동한다.
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
				}
			}
			
			//위의 절차가 완료되면 안내하며, adminGoodsMain 상품목록 페이지를 reload한다.
			message = "<script>";
			message += " alert('새상품을 추가했습니다.');";
			message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain.do';";
			message += ("</script>");
		} catch (Exception e) {
			//예외발생의 경우 
			//이미 파일이 생성되어 있는 이후 에러가 발생시 대비한다.
			if (imageFileList != null && imageFileList.size() != 0) {
				for (ImageFileVO imageFileVO : imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					//만들어진 temp경로안의 파일들을 삭제한다.
					srcFile.delete();
				}
			}
			//삭제를 완료한 이후 안내하며, adminGoodsMain 상품목록 페이지를 reload한다.
			message = "<script>";
			message += " alert('오류가 발생했습니다. 다시 시도해 주세요');";
			message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain.do';";
			message += ("</script>");
			e.printStackTrace();
		}
		
		
		//각 경우에 따른 message를 가지고 resEntity 리턴한다.
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	
	
	
	

	// 상품삭제
	@RequestMapping(value = "/deleteGoods.do", method = RequestMethod.GET)
	public ModelAndView deleteGoods(@RequestParam("goods_id") String goods_id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//goods_id를 가지고 삭제 진행.
		adminGoodsService.deleteGoods(goods_id);

		//상품정보가 잘 삭제되었다면
		//해당 goods_id 경로의 이미지 파일도 삭제해 용량부족과 중복을 방지한다.
		File folder = new File(CURR_IMAGE_REPO_PATH + "\\" + goods_id);
		try {
			//folder가 존재하는 한 아래의 절차가 반복된다.
			//파일이 아무것도 없는 폴더만을 삭제할 수 있음으로 파일을 먼저 삭제하고 폴더를 삭제하는 과정을 거친다.
			while (folder.exists()) {
				File[] folder_list = folder.listFiles();
				for (int j = 0; j < folder_list.length; j++) {
					//파일 삭제
					folder_list[j].delete();
				}
				if (folder_list.length == 0 && folder.isDirectory()) {
					//모든 파일이 삭제되었다면, 폴더를 삭제한다.
					folder.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//완료후 adminGoodsMain로 reload
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/goods/adminGoodsMain.do");
		
		return mav;
	}

	
	
	
	
	// 상품수정
	@Override
	@RequestMapping(value = "/modifyGoods.do", method = { RequestMethod.POST })
	public ResponseEntity modifyGoods(@RequestParam("goods_id") String goods_id,
			MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {

		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		String imageFileName = null;

		//form 값을 받아 newGoodsMap에 put
		Map newGoodsMap = new HashMap();
		Enumeration enu = multipartRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			newGoodsMap.put(name, value);
		}

		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");

		
		//수령받은 imageFileList의 정보에서 getFileName이 비어잇진 않는지 확인하고, 카운트한다.
		int check = 0;
		List<ImageFileVO> imageFileList = upload(multipartRequest);
		if (imageFileList != null && imageFileList.size() != 0) {
			for (ImageFileVO imageFileVO : imageFileList) {
				if (imageFileVO.getFileName() == "" || imageFileVO.getFileName() == null) {
					check += 1;
				}
			}
			newGoodsMap.put("imageFileList", imageFileList);
		}

		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			//modifyGoods 상품정보 ㅅ정
			adminGoodsService.modifyGoods(goods_id, newGoodsMap);
			for (ImageFileVO imageFileVO : imageFileList) {
				
				//수령받은 정보에서 getFileName을 찾을 수 없다면 폴더/이미지를 생성/업로드하지않는다.
				if (imageFileVO.getFileName() == "" || imageFileVO.getFileName() == null) {
				} else {
					//리스트와 파일정보를 잘 받았다면
					imageFileName = imageFileVO.getFileName();
					//temp 임시폴더 안에 파일생성 imageFileName
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					//이름이 goods_id인 폴더로 덮어쓰기 copyFileToDirectory
					File destDir = new File(CURR_IMAGE_REPO_PATH + "\\" + goods_id);
					FileUtils.copyFileToDirectory(srcFile, destDir);
				}
				
			}
			
			//위 절차를 완료한 이후 안내하며 adminGoodsMain로 reload
			message = "<script>";
			message += " alert('수정되었습니다!');";
			message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain.do';";
			message += ("</script>");
			
		} catch (Exception e) {
			//수정중 예외가 낫을때
			
			if (imageFileList != null && imageFileList.size() != 0) {
				for (ImageFileVO imageFileVO : imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					//temp임시 폴더에 생성된 파일들을 삭제한다.
					srcFile.delete();
				}
			}

			//위 절차를 완료한 이후 안내하며 adminGoodsMain로 reload
			message = "<script>";
			message += " alert('오류가 발생했습니다. 다시 시도해 주세요');";
			message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain.do';";
			message += ("</script>");
			e.printStackTrace();
		}
		
		//각 경우에 따른 message를 가지고 resEntity 리턴한다.
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

}
