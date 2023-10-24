package com.k2ring.sun.admin.goods.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.k2ring.sun.goods.vo.GoodsVO;
import com.k2ring.sun.goods.vo.ImageFileVO;

@Repository("adminGoodsDAO")
public class AdminGoodsDAOImpl  implements AdminGoodsDAO{
	@Autowired
	private SqlSession sqlSession;

	
	//��ǰ���� - ��ǰ����Ʈ
	@Override
	public List<GoodsVO>selectNewGoodsList(Map condMap) throws DataAccessException {
		ArrayList<GoodsVO>  goodsList=(ArrayList)sqlSession.selectList("mapper.admin.goods.selectNewGoodsList",condMap);
		return goodsList;
	}
	
	
	//��ǰ�߰� - ��ǰ����
	@Override
	public int insertNewGoods(Map newGoodsMap) throws DataAccessException {
		sqlSession.insert("mapper.admin.goods.insertNewGoods",newGoodsMap);
		return Integer.parseInt((String)newGoodsMap.get("goods_id"));
	}
	
	
	
	//��ǰ�߰� - �̹���
	@Override
	public void insertGoodsImageFile(List fileList)  throws DataAccessException {
		//fileList ����Ʈ�� ���� �ϳ��� sql�� ������ insert�Ѵ�.
		for(int i=0; i<fileList.size();i++){
			ImageFileVO imageFileVO=(ImageFileVO)fileList.get(i);
			sqlSession.insert("mapper.admin.goods.insertGoodsImageFile",imageFileVO);
		}
	}
		
	
	
	//��ǰ����
	@Override
	public void deleteGoods(String goods_id) throws Exception {
		//��ǰ���� ����
		sqlSession.insert("mapper.admin.goods.deleteGoods",goods_id);
		//��ǰ�̹��� ����
		sqlSession.insert("mapper.admin.goods.deleteimages",goods_id);
	}

	
	
	//��ǰ���� - ��ǰ����
	@Override
	public void modifyGoods(String goods_id, Map newGoodsMap) throws Exception {
		sqlSession.insert("mapper.admin.goods.modifyGoods",newGoodsMap);
	}

	
	//��ǰ���� - �̹���
	@Override
	public void modifyImages(List imageFileList) throws Exception {
		//fileList ����Ʈ�� ���� �ϳ��� sql�� ������ �����Ѵ�.
		for(int i=0; i<imageFileList.size();i++){
			ImageFileVO imageFileVO=(ImageFileVO)imageFileList.get(i);
			sqlSession.insert("mapper.admin.goods.modifyimages",imageFileVO);
		}
		
	}


}
