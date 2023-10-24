package com.k2ring.sun.admin.goods.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.k2ring.sun.goods.vo.GoodsVO;

public interface AdminGoodsDAO {
	//상품관리
	public List<GoodsVO>selectNewGoodsList(Map condMap) throws DataAccessException;

	//상품추가
	public int insertNewGoods(Map newGoodsMap) throws DataAccessException;
	public void insertGoodsImageFile(List fileList)  throws DataAccessException;
	
	//상품삭제
	public void deleteGoods(String goods_id) throws Exception;
	
	//상품수정
	public void  modifyGoods(String goods_id, Map newGoodsMap) throws Exception;
	public void  modifyImages(List imageFileList) throws Exception;
	
}
