package com.k2ring.sun.admin.goods.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.k2ring.sun.goods.vo.GoodsVO;

public interface AdminGoodsDAO {
	//��ǰ����
	public List<GoodsVO>selectNewGoodsList(Map condMap) throws DataAccessException;

	//��ǰ�߰�
	public int insertNewGoods(Map newGoodsMap) throws DataAccessException;
	public void insertGoodsImageFile(List fileList)  throws DataAccessException;
	
	//��ǰ����
	public void deleteGoods(String goods_id) throws Exception;
	
	//��ǰ����
	public void  modifyGoods(String goods_id, Map newGoodsMap) throws Exception;
	public void  modifyImages(List imageFileList) throws Exception;
	
}
