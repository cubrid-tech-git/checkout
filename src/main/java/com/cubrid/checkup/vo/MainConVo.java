package com.cubrid.checkup.vo;

/**
 * 
 * @author HUN
 * 
 * main_con table의 값들을 저장할 vo
 *
 */
public class MainConVo {
	private String con_id;	// con_id(varchar15)
	private String con_nm;	// con_nm(varchar30)
	
	public String getCon_id() {
		return con_id;
	}
	public void setCon_id(String con_id) {
		this.con_id = con_id;
	}
	public String getCon_nm() {
		return con_nm;
	}
	public void setCon_nm(String con_nm) {
		this.con_nm = con_nm;
	}
}
