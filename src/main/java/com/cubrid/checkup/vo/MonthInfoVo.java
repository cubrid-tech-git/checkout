package com.cubrid.checkup.vo;

public class MonthInfoVo {
	private String name;
	private int plan;
	private int result;
	private double average;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPlan() {
		return plan;
	}
	public void setPlan(int plan) {
		this.plan = plan;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public double getAverage() {
		return Math.ceil((double)result / (double)plan * 100);
	}
}
