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

	// ��ǰ���� - ��ǰ����Ʈ
	@RequestMapping(value = "/adminGoodsMain.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView adminGoodsMain(@RequestParam Map<String, String> dateMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		session = request.getSession();
		session.setAttribute("side_menu", "admin_mode");
		
		//fixedSearchPeriod���� �޾� ����
		String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");

		//�Ⱓ �ʱ�ȭ
		String beginDate = null, endDate = null;
		
		//fixedSearchPeriod�� ������ dateMap�� put
		String[] tempDate = calcSearchPeriod(fixedSearchPeriod).split(",");
		beginDate = tempDate[0];
		endDate = tempDate[1];
		dateMap.put("beginDate", beginDate);
		dateMap.put("endDate", endDate);

		//condMap�� put �� listNewGoods����.
		Map<String, Object> condMap = new HashMap<String, Object>();
		condMap.put("beginDate", beginDate);
		condMap.put("endDate", endDate);
		List<GoodsVO> newGoodsList = adminGoodsService.listNewGoods(condMap);
		
		//���ϵ� ��ǰ����Ʈ newGoodsList��  mav�� newGoodsList�� �ο�
		mav.addObject("newGoodsList", newGoodsList);

		//��¥��������
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

	// ��ǰ�߰�
	@RequestMapping(value = "/addNewGoods.do", method = { RequestMethod.POST })
	public ResponseEntity addNewGoods(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String imageFileName = null;
		
		//form ���� �޾� newGoodsMap�� put
		Map newGoodsMap = new HashMap();
		Enumeration enu = multipartRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			newGoodsMap.put(name, value);
		}

		//���ǿ��� get�� memberInfo�� reg_id, �������̰� ��.
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
		String reg_id = memberVO.getMember_id();

		//baseController upload
		List<ImageFileVO> imageFileList = upload(multipartRequest);
		
		//imageFileList�� �޾� setReg_id�� newGoodsMap�� put
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
			//��ǰ, ���������� ����ִ� newGoodsMap���� addNewGoods ����
			int goods_id = adminGoodsService.addNewGoods(newGoodsMap);
			
			//imageFileList�� ���� ��� 
			if (imageFileList != null && imageFileList.size() != 0) {
				for (ImageFileVO imageFileVO : imageFileList) {
					
					//temp�ȿ� imageFileName �̸����� ���� ����,
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					
					//���� goods_id�� �������� ������ �ϳ� ����� 
					File destDir = new File(CURR_IMAGE_REPO_PATH + "\\" + goods_id);
					
					//�̵��Ѵ�.
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
				}
			}
			
			//���� ������ �Ϸ�Ǹ� �ȳ��ϸ�, adminGoodsMain ��ǰ��� �������� reload�Ѵ�.
			message = "<script>";
			message += " alert('����ǰ�� �߰��߽��ϴ�.');";
			message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain.do';";
			message += ("</script>");
		} catch (Exception e) {
			//���ܹ߻��� ��� 
			//�̹� ������ �����Ǿ� �ִ� ���� ������ �߻��� ����Ѵ�.
			if (imageFileList != null && imageFileList.size() != 0) {
				for (ImageFileVO imageFileVO : imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					//������� temp��ξ��� ���ϵ��� �����Ѵ�.
					srcFile.delete();
				}
			}
			//������ �Ϸ��� ���� �ȳ��ϸ�, adminGoodsMain ��ǰ��� �������� reload�Ѵ�.
			message = "<script>";
			message += " alert('������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');";
			message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain.do';";
			message += ("</script>");
			e.printStackTrace();
		}
		
		
		//�� ��쿡 ���� message�� ������ resEntity �����Ѵ�.
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	
	
	
	

	// ��ǰ����
	@RequestMapping(value = "/deleteGoods.do", method = RequestMethod.GET)
	public ModelAndView deleteGoods(@RequestParam("goods_id") String goods_id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//goods_id�� ������ ���� ����.
		adminGoodsService.deleteGoods(goods_id);

		//��ǰ������ �� �����Ǿ��ٸ�
		//�ش� goods_id ����� �̹��� ���ϵ� ������ �뷮������ �ߺ��� �����Ѵ�.
		File folder = new File(CURR_IMAGE_REPO_PATH + "\\" + goods_id);
		try {
			//folder�� �����ϴ� �� �Ʒ��� ������ �ݺ��ȴ�.
			//������ �ƹ��͵� ���� �������� ������ �� �������� ������ ���� �����ϰ� ������ �����ϴ� ������ ��ģ��.
			while (folder.exists()) {
				File[] folder_list = folder.listFiles();
				for (int j = 0; j < folder_list.length; j++) {
					//���� ����
					folder_list[j].delete();
				}
				if (folder_list.length == 0 && folder.isDirectory()) {
					//��� ������ �����Ǿ��ٸ�, ������ �����Ѵ�.
					folder.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//�Ϸ��� adminGoodsMain�� reload
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/goods/adminGoodsMain.do");
		
		return mav;
	}

	
	
	
	
	// ��ǰ����
	@Override
	@RequestMapping(value = "/modifyGoods.do", method = { RequestMethod.POST })
	public ResponseEntity modifyGoods(@RequestParam("goods_id") String goods_id,
			MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {

		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		String imageFileName = null;

		//form ���� �޾� newGoodsMap�� put
		Map newGoodsMap = new HashMap();
		Enumeration enu = multipartRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			newGoodsMap.put(name, value);
		}

		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");

		
		//���ɹ��� imageFileList�� �������� getFileName�� ������� �ʴ��� Ȯ���ϰ�, ī��Ʈ�Ѵ�.
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
			//modifyGoods ��ǰ���� ����
			adminGoodsService.modifyGoods(goods_id, newGoodsMap);
			for (ImageFileVO imageFileVO : imageFileList) {
				
				//���ɹ��� �������� getFileName�� ã�� �� ���ٸ� ����/�̹����� ����/���ε������ʴ´�.
				if (imageFileVO.getFileName() == "" || imageFileVO.getFileName() == null) {
				} else {
					//����Ʈ�� ���������� �� �޾Ҵٸ�
					imageFileName = imageFileVO.getFileName();
					//temp �ӽ����� �ȿ� ���ϻ��� imageFileName
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					//�̸��� goods_id�� ������ ����� copyFileToDirectory
					File destDir = new File(CURR_IMAGE_REPO_PATH + "\\" + goods_id);
					FileUtils.copyFileToDirectory(srcFile, destDir);
				}
				
			}
			
			//�� ������ �Ϸ��� ���� �ȳ��ϸ� adminGoodsMain�� reload
			message = "<script>";
			message += " alert('�����Ǿ����ϴ�!');";
			message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain.do';";
			message += ("</script>");
			
		} catch (Exception e) {
			//������ ���ܰ� ������
			
			if (imageFileList != null && imageFileList.size() != 0) {
				for (ImageFileVO imageFileVO : imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					//temp�ӽ� ������ ������ ���ϵ��� �����Ѵ�.
					srcFile.delete();
				}
			}

			//�� ������ �Ϸ��� ���� �ȳ��ϸ� adminGoodsMain�� reload
			message = "<script>";
			message += " alert('������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');";
			message += " location.href='" + multipartRequest.getContextPath() + "/admin/goods/adminGoodsMain.do';";
			message += ("</script>");
			e.printStackTrace();
		}
		
		//�� ��쿡 ���� message�� ������ resEntity �����Ѵ�.
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

}
