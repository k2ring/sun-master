package com.k2ring.sun.admin.goods.service;

import java.util.List;
import java.util.Map;

import com.k2ring.sun.goods.vo.GoodsVO;

public interface AdminGoodsService {
	// 상품관리
	public List<GoodsVO> listNewGoods(Map condMap) throws Exception;

	//상품추가
	public int addNewGoods(Map newGoodsMap) throws Exception;
	public void addNewGoodsImage(List imageFileList) throws Exception;

	//상품삭제
	public void deleteGoods(String goods_id) throws Exception;

	//상품수정
	public void modifyGoods(String goods_id, Map newGoodsMap) throws Exception;

}
