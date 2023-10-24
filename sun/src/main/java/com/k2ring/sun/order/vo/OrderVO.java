package com.k2ring.sun.order.vo;

import org.springframework.stereotype.Component;

@Component("orderVO")
public class OrderVO {
	private int order_seq_num;
	private int order_id;
	private int goods_id;
	private String goods_title;
	private int goods_price;
	private String goods_fileName;
	private int order_goods_qty;
	private String delivery_state; 
	
	private String member_id;
	private String orderer_hp;
	private String receiver_name;
	private String receiver_hp1;
	private String delivery_address;
	private String pay_method;
	private String card_com_name;
	private String  pay_orderer_hp_num;

	public String getPay_orderer_hp_num() {
		return pay_orderer_hp_num;
	}

	public void setPay_orderer_hp_num(String pay_orderer_hp_num) {
		this.pay_orderer_hp_num = pay_orderer_hp_num;
	}

	private String card_pay_month;
	
	private String pay_order_time;

	public int getOrder_seq_num() {
		return order_seq_num;
	}

	public void setOrder_seq_num(int order_seq_num) {
		this.order_seq_num = order_seq_num;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_title() {
		return goods_title;
	}

	public void setGoods_title(String goods_title) {
		this.goods_title = goods_title;
	}

	public int getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(int goods_price) {
		this.goods_price = goods_price;
	}

	public String getGoods_fileName() {
		return goods_fileName;
	}

	public void setGoods_fileName(String goods_fileName) {
		this.goods_fileName = goods_fileName;
	}

	public int getOrder_goods_qty() {
		return order_goods_qty;
	}

	public void setOrder_goods_qty(int order_goods_qty) {
		this.order_goods_qty = order_goods_qty;
	}

	public String getDelivery_state() {
		return delivery_state;
	}

	public void setDelivery_state(String delivery_state) {
		this.delivery_state = delivery_state;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getOrderer_hp() {
		return orderer_hp;
	}

	public void setOrderer_hp(String orderer_hp) {
		this.orderer_hp = orderer_hp;
	}

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getReceiver_hp1() {
		return receiver_hp1;
	}

	public void setReceiver_hp1(String receiver_hp1) {
		this.receiver_hp1 = receiver_hp1;
	}

	public String getDelivery_address() {
		return delivery_address;
	}

	public void setDelivery_address(String delivery_address) {
		this.delivery_address = delivery_address;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getCard_com_name() {
		return card_com_name;
	}

	public void setCard_com_name(String card_com_name) {
		this.card_com_name = card_com_name;
	}


	public String getCard_pay_month() {
		return card_pay_month;
	}

	public void setCard_pay_month(String card_pay_month) {
		this.card_pay_month = card_pay_month;
	}

	public String getPay_order_time() {
		return pay_order_time;
	}

	public void setPay_order_time(String pay_order_time) {
		this.pay_order_time = pay_order_time;
	}
	
	
	
	
	
	
}