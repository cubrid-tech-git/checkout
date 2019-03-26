package com.cubrid.checkup.vo;

public class LoggingVo {
	private int log_seq;
	private String job_id;
	private String con_id;
	private String con_year;
	private String log_date;
	private String log_content;
	private String cust_nm;
	private String proc_nm;
	
	public int getLog_seq() {
		return log_seq;
	}
	public void setLog_seq(int log_seq) {
		this.log_seq = log_seq;
	}
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	public String getCon_id() {
		return con_id;
	}
	public void setCon_id(String con_id) {
		this.con_id = con_id;
	}
	public String getCon_year() {
		return con_year;
	}
	public void setCon_year(String con_year) {
		this.con_year = con_year;
	}
	public String getLog_date() {
		return log_date;
	}
	public void setLog_date(String log_date) {
		this.log_date = log_date;
	}
	public String getLog_content() {
		return log_content;
	}
	public void setLog_content(String log_content) {
		this.log_content = log_content;
	}
	public String getCust_nm() {
		return cust_nm;
	}
	public void setCust_nm(String cust_nm) {
		this.cust_nm = cust_nm;
	}
	public String getProc_nm() {
		return proc_nm;
	}
	public void setProc_nm(String proc_nm) {
		this.proc_nm = proc_nm;
	}
}
