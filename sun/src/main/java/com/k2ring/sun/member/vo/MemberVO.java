package com.k2ring.sun.member.vo;

import org.springframework.stereotype.Component;

@Component("memberVO")
public class MemberVO {
	
	private String member_name;
	private String member_id;
	private String member_pw;
	private String hp1;
	private String zipcode;
	private String member_address;
	private String subaddress;
	private String sun_money;
	private String joinDate;
	private String del_yn;
	
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getMember_pw() {
		return member_pw;
	}
	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}
	public String getHp1() {
		return hp1;
	}
	public void setHp1(String hp1) {
		this.hp1 = hp1;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getMember_address() {
		return member_address;
	}
	public void setMember_address(String member_address) {
		this.member_address = member_address;
	}
	public String getSubaddress() {
		return subaddress;
	}
	public void setSubaddress(String subaddress) {
		this.subaddress = subaddress;
	}
	public String getSun_money() {
		return sun_money;
	}
	public void setSun_money(String sun_money) {
		this.sun_money = sun_money;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	
	
}

