package com.k2ring.sun.admin.goods.service;

import java.util.List;
import java.util.Map;

import com.k2ring.sun.goods.vo.GoodsVO;

public interface AdminGoodsService {
	// ��ǰ����
	public List<GoodsVO> listNewGoods(Map condMap) throws Exception;

	//��ǰ�߰�
	public int addNewGoods(Map newGoodsMap) throws Exception;
	public void addNewGoodsImage(List imageFileList) throws Exception;

	//��ǰ����
	public void deleteGoods(String goods_id) throws Exception;

	//��ǰ����
	public void modifyGoods(String goods_id, Map newGoodsMap) throws Exception;

}
