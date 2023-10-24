package com.k2ring.sun.admin.goods.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.k2ring.sun.admin.goods.dao.AdminGoodsDAO;
import com.k2ring.sun.goods.vo.GoodsVO;
import com.k2ring.sun.goods.vo.ImageFileVO;

@Service("adminGoodsService")
@Transactional(propagation = Propagation.REQUIRED)
public class AdminGoodsServiceImpl implements AdminGoodsService {
	@Autowired
	private AdminGoodsDAO adminGoodsDAO;

	
	
	//상품관리 - 상품리스트
	@Override
	public List<GoodsVO> listNewGoods(Map condMap) throws Exception {
		return adminGoodsDAO.selectNewGoodsList(condMap);
	}

	
	
	//상품추가 - 상품정보
	@Override
	public int addNewGoods(Map newGoodsMap) throws Exception {
		
		//상품정보추가, 시퀀스가 실행되며 상품 id가 발급되며 그 리턴값을 goods_id에 저장한다.
		int goods_id = adminGoodsDAO.insertNewGoods(newGoodsMap);
		
		//해당 goods_id값을 img에 대입해 insert한다.
		ArrayList<ImageFileVO> imageFileList = (ArrayList) newGoodsMap.get("imageFileList");
		for (ImageFileVO imageFileVO : imageFileList) {imageFileVO.setGoods_id(goods_id);}
		adminGoodsDAO.insertGoodsImageFile(imageFileList);
		
		return goods_id;
	}

	
	//상품추가 - 이미지
	@Override
	public void addNewGoodsImage(List imageFileList) throws Exception {
		adminGoodsDAO.insertGoodsImageFile(imageFileList);
	}

	
	
	//상품삭제
	@Override
	public void deleteGoods(String goods_id) throws Exception {
		adminGoodsDAO.deleteGoods(goods_id);

	}

	
	
	//상품수정
	@Override
	public void modifyGoods(String goods_id, Map newGoodsMap) throws Exception {
		//전달받은 goods_id를 정수화, goods_id_toInt
		int goods_id_toInt = Integer.parseInt(goods_id);

		//goods_id값과 함께 상품정보 수정
		adminGoodsDAO.modifyGoods(goods_id, newGoodsMap);
		
		//정수화한 상품id goods_id_toInt를 img에 대입한다.
		ArrayList<ImageFileVO> imageFileList = (ArrayList) newGoodsMap.get("imageFileList");
		for (ImageFileVO imageFileVO : imageFileList) {
			imageFileVO.setGoods_id(goods_id_toInt);
		}
		
		//이미지 수정
		//이미지가 없을경우의 submit에 null예외를 예방하고, 메소드를 분리하지않기위해 if문을 사용.
		// 파일은 수정하지않을 경우엔 modifyImages를 사용하지않는다.
		for (ImageFileVO imageFileVO : imageFileList) {
			if (imageFileVO.getFileName() == "" || imageFileVO.getFileName() == null) {} 
			else {adminGoodsDAO.modifyImages(imageFileList);}
		}

	}

}
