package com.k2ring.sun.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.k2ring.sun.goods.dao.GoodsDAO;
import com.k2ring.sun.goods.vo.GoodsVO;
import com.k2ring.sun.goods.vo.ImageFileVO;
@Service("goodsService")
@Transactional(propagation = Propagation.REQUIRED)
public class GoodsServiceImpl implements GoodsService {
	@Autowired
	private GoodsDAO goodsDAO;

	public Map<String, List<GoodsVO>> listGoods() throws Exception {
		Map<String, List<GoodsVO>> goodsMap = new HashMap<String, List<GoodsVO>>();

		//���̺��� bestseller ��ǰ
		List<GoodsVO> goodsList = goodsDAO.selectGoodsList("bestseller");
		goodsMap.put("bestseller", goodsList);

		//����
		goodsList = goodsDAO.selectMenusList("����");
		goodsMap.put("cate_fruit", goodsList);

		//���
		goodsList = goodsDAO.selectMenusList("���");
		goodsMap.put("cate_grain", goodsList);

		//ä��
		goodsList = goodsDAO.selectMenusList("ä��");
		goodsMap.put("cate_vege", goodsList);

		//����
		goodsList = goodsDAO.selectMenusList("����");
		goodsMap.put("cate_mush", goodsList);

		//ī�װ� ��� Map return
		return goodsMap;
	}

	
	
	//header �޴�
	@Override
	public List<GoodsVO> menuGoods(String menuGoods) throws Exception {
		List goodsList = goodsDAO.selectGoodsByMenuGoods(menuGoods);
		return goodsList;
	}
	
	

	//Ű����˻�
	@Override
	public List<String> keywordSearch(String keyword) throws Exception {
		List<String> list = goodsDAO.selectKeywordSearch(keyword);
		return list;
	}

	
	//��ǰ�˻�
	@Override
	public List<GoodsVO> searchGoods(String searchWord) throws Exception {
		List goodsList = goodsDAO.selectGoodsBySearchWord(searchWord);
		return goodsList;
	}
	

	//�� ���� ������
	public Map goodsDetail(String _goods_id) throws Exception {
		Map goodsMap = new HashMap();
		//��� ���� goodsMap ����
		
		
		//goodsmap ����Ʈ�� goodsvo��� �̸��� �߰��մϴ�.
		GoodsVO goodsVO = goodsDAO.selectGoodsDetail(_goods_id);
		goodsMap.put("goodsVO", goodsVO);
		
	    // ��ǰ�� ���� �̹��� ���� ������ ������ goodsMap�� "imageList"��� �̸����� �߰��մϴ�.
		List<ImageFileVO> imageList = goodsDAO.selectGoodsDetailImage(_goods_id);
		goodsMap.put("imageList", imageList);
		
		//���� ������ ����� goodsMap�� ��ȯ�մϴ�.
		return goodsMap;
	}

}
