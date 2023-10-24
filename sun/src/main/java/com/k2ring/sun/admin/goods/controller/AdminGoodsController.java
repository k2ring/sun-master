package com.k2ring.sun.admin.goods.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface AdminGoodsController {
	//상품관리
	public ModelAndView adminGoodsMain(@RequestParam Map<String, String> dateMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	//상품추가
	public ResponseEntity addNewGoods(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception;

	//상품삭제
	public ModelAndView deleteGoods(@RequestParam("goods_id") String goods_id, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	//상품수정
	public ResponseEntity modifyGoods(@RequestParam("goods_id") String goods_id,
			MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;
}
