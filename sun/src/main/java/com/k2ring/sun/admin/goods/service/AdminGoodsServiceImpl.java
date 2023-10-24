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

	
	
	//��ǰ���� - ��ǰ����Ʈ
	@Override
	public List<GoodsVO> listNewGoods(Map condMap) throws Exception {
		return adminGoodsDAO.selectNewGoodsList(condMap);
	}

	
	
	//��ǰ�߰� - ��ǰ����
	@Override
	public int addNewGoods(Map newGoodsMap) throws Exception {
		
		//��ǰ�����߰�, �������� ����Ǹ� ��ǰ id�� �߱޵Ǹ� �� ���ϰ��� goods_id�� �����Ѵ�.
		int goods_id = adminGoodsDAO.insertNewGoods(newGoodsMap);
		
		//�ش� goods_id���� img�� ������ insert�Ѵ�.
		ArrayList<ImageFileVO> imageFileList = (ArrayList) newGoodsMap.get("imageFileList");
		for (ImageFileVO imageFileVO : imageFileList) {imageFileVO.setGoods_id(goods_id);}
		adminGoodsDAO.insertGoodsImageFile(imageFileList);
		
		return goods_id;
	}

	
	//��ǰ�߰� - �̹���
	@Override
	public void addNewGoodsImage(List imageFileList) throws Exception {
		adminGoodsDAO.insertGoodsImageFile(imageFileList);
	}

	
	
	//��ǰ����
	@Override
	public void deleteGoods(String goods_id) throws Exception {
		adminGoodsDAO.deleteGoods(goods_id);

	}

	
	
	//��ǰ����
	@Override
	public void modifyGoods(String goods_id, Map newGoodsMap) throws Exception {
		//���޹��� goods_id�� ����ȭ, goods_id_toInt
		int goods_id_toInt = Integer.parseInt(goods_id);

		//goods_id���� �Բ� ��ǰ���� ����
		adminGoodsDAO.modifyGoods(goods_id, newGoodsMap);
		
		//����ȭ�� ��ǰid goods_id_toInt�� img�� �����Ѵ�.
		ArrayList<ImageFileVO> imageFileList = (ArrayList) newGoodsMap.get("imageFileList");
		for (ImageFileVO imageFileVO : imageFileList) {
			imageFileVO.setGoods_id(goods_id_toInt);
		}
		
		//�̹��� ����
		//�̹����� ��������� submit�� null���ܸ� �����ϰ�, �޼ҵ带 �и������ʱ����� if���� ���.
		// ������ ������������ ��쿣 modifyImages�� ��������ʴ´�.
		for (ImageFileVO imageFileVO : imageFileList) {
			if (imageFileVO.getFileName() == "" || imageFileVO.getFileName() == null) {} 
			else {adminGoodsDAO.modifyImages(imageFileList);}
		}

	}

}
