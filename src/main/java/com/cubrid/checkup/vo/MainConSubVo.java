package com.cubrid.checkup.vo;

/**
 * 
 * @author HUN
 * 
 * main_con_sub 테이블에 대한 vo
 *
 */
public class MainConSubVo {
	private String con_id;	// con_id(varchar(15))
	private int con_seq;	// con_seq(short)
	private String con_year;	// con_year(char(4))
	private String con_from_date;	// con_from_date(date)
	private String con_to_date;	// con_to_date(date)
	private String cust_nm;	// cust_nm(varchar(60))
	private String proc_nm;	// proc_nm(varchar(60))
	private String check_nm;	// check_nm(varchar(20))
	private String main_oper_nm;	// main_oper_nm(varchar(20))
	private String sub_oper_nm;	// sub_oper_nm(varchar(20))
	private String con_desc;	// con_desc(varchar(200))
	private String close_yn;	// close_yn(varchar(1))
	private String reg_date;	// reg_date(timestamp)
	private String upd_date;	// upd_date(timestamp)
	private int visitPlanCount;	// 방문에 대한 갯수 저장할 변수
	private int remotePlanCount;	// 원격에 대한 갯수 저장할 변수
	private int monthResultCount;	// 월별 실시한 결과의 갯수
	private int monthTotalResultCount;	// 월별 예정된 결과의 갯수
	
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
	public String getCon_from_date() {
		return con_from_date;
	}
	public void setCon_from_date(String con_from_date) {
		this.con_from_date = con_from_date;
	}
	public String getCon_to_date() {
		return con_to_date;
	}
	public void setCon_to_date(String con_to_date) {
		this.con_to_date = con_to_date;
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
	public String getCheck_nm() {
		return check_nm;
	}
	public void setCheck_nm(String check_nm) {
		this.check_nm = check_nm;
	}
	public String getMain_oper_nm() {
		return main_oper_nm;
	}
	public void setMain_oper_nm(String main_oper_nm) {
		this.main_oper_nm = main_oper_nm;
	}
	public String getSub_oper_nm() {
		return sub_oper_nm;
	}
	public void setSub_oper_nm(String sub_oper_nm) {
		this.sub_oper_nm = sub_oper_nm;
	}
	public String getCon_desc() {
		return con_desc;
	}
	public void setCon_desc(String con_desc) {
		this.con_desc = con_desc;
	}
	public String getClose_yn() {
		return close_yn;
	}
	public void setClose_yn(String close_yn) {
		this.close_yn = close_yn;
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
	public int getVisitPlanCount() {
		return visitPlanCount;
	}
	public void setVisitPlanCount(int input) {
		this.visitPlanCount = input; 
	}
	public int getRemotePlanCount() {
		return remotePlanCount;
	}
	public void setRemotePlanCount(int input) {
		this.remotePlanCount = input;
	}
	public int getMonthResultCount() {
		return monthResultCount;
	}
	public void setMonthResultCount(int monthResultCount) {
		this.monthResultCount = monthResultCount;
	}
	public int getMonthTotalResultCount() {
		return monthTotalResultCount;
	}
	public void setMonthTotalResultCount() {
		this.monthTotalResultCount = this.getVisitPlanCount() + this.getRemotePlanCount();
	}
	public String getCon_year() {
		return con_year;
	}
	public void setCon_year(String con_year) {
		this.con_year = con_year;
	}
}
