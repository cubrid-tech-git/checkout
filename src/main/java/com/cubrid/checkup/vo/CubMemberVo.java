package com.cubrid.checkup.vo;

public class CubMemberVo {
	private String jira_id;
	private String cub_name;
	private String show_yn;
	private String email_addr;
	private String reg_date;
	
	public String getJira_id() {
		return jira_id;
	}
	public void setJira_id(String jira_id) {
		this.jira_id = jira_id;
	}
	public String getCub_name() {
		return cub_name;
	}
	public void setCub_name(String cub_name) {
		this.cub_name = cub_name;
	}
	public String getEmail_addr() {
		return email_addr;
	}
	public void setEmail_addr(String email_addr) {
		this.email_addr = email_addr;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getShow_yn() {
		return show_yn;
	}
	public void setShow_yn(String show_yn) {
		this.show_yn = show_yn;
	}
}
