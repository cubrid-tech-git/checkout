package com.cubrid.checkup.vo;

/**
 * 
 * @author HUN
 *
 *	job_op 테이블에 대한 vo
 */
public class JobOpVo {
	private int job_seq;	// job_seq(short)
	private String con_id;	// con_id(varchar(15))
	private int con_seq;	// con_seq(short)
	private String con_year; // con_year(char(4))
	private String job_date;	// job_date(date)
	private String job_nm;	// job_nm(varchar(20))
	private String job_visit_remote;	// job_visit_remote(varchar(1))
	private String job_yn;	// job_yn(varchar(1))
	private String modify_yn;	// modify_yn(varchar(1))
	private String job_reason;	// job_reason(varchar(200))
	private String reg_date;	// reg_date(timestamp)
	private String upd_date;	// upd_date(timestamp)
	private String parent_seq;	// parent_seq(short)
	
	public int getJob_seq() {
		return job_seq;
	}
	public void setJob_seq(int job_seq) {
		this.job_seq = job_seq;
	}
	public String getCon_id() {
		return con_id;
	}
	public void setCon_id(String con_id) {
		this.con_id = con_id;
	}
	public int getCon_seq() {
		return con_seq;
	}
	public void setCon_seq(int con_seq) {
		this.con_seq = con_seq;
	}
	public String getJob_date() {
		return job_date;
	}
	public void setJob_date(String job_date) {
		this.job_date = job_date;
	}
	public String getJob_nm() {
		return job_nm;
	}
	public void setJob_nm(String job_nm) {
		this.job_nm = job_nm;
	}
	public String getJob_visit_remote() {
		return job_visit_remote;
	}
	public void setJob_visit_remote(String job_visit_remote) {
		this.job_visit_remote = job_visit_remote;
	}
	public String getJob_yn() {
		return job_yn;
	}
	public void setJob_yn(String job_yn) {
		this.job_yn = job_yn;
	}
	public String getModify_yn() {
		return modify_yn;
	}
	public void setModify_yn(String modify_yn) {
		this.modify_yn = modify_yn;
	}
	public String getJob_reason() {
		return job_reason;
	}
	public void setJob_reason(String job_reason) {
		this.job_reason = job_reason;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getUpd_date() {
		return upd_date;
	}
	public void setUpd_date(String upd_date) {
		this.upd_date = upd_date;
	}
	public String getParent_seq() {
		return parent_seq;
	}
	public void setParent_seq(String parent_seq) {
		this.parent_seq = parent_seq;
	}
	public String getCon_year() {
		return con_year;
	}
	public void setCon_year(String con_year) {
		this.con_year = con_year;
	}
}
