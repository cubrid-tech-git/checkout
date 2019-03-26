package com.cubrid.util.count;

public class CheckupCount {
	private int totalVisit;	// 전체 방문에 대한 카운트
	private int totalRemote;	// 전체 원격에 대한 카운트
	private int totalResultSum;	// 전체 방문+원격 에 대한 카운트
	private int totalRecentSum;	// 전체 중에서 진행된 방문+원격 에 대한 카운트
	private int projectVisit;	// 프로젝트별 방문에 대한 카운트
	private int projectRemote;	// 프로젝트별 원격에 대한 카운트
	private int projectResultSum;	// 프로젝트별 전체 에 대한 카운트
	private int projectRecentSum;	// 프로젝트별 방문+원격이 진행된 수에 대한 카운트
	private int scheduledVisit_1;	// 1월에 대한 방문 카운트
	private int scheduledRemote_1;	// 1월에 대한 원격 카운트
	private int monthResult_1;	// 1월에 대한 방문+원격에 대한 카운트
	private int monthRecent_1;	// 1월에서 진행된 방문+원격에 대한 카운트
	private int scheduledVisit_2;
	private int scheduledRemote_2;
	private int monthResult_2; 
	private int monthRecent_2;
	private int scheduledVisit_3;
	private int scheduledRemote_3;
	private int monthResult_3; 
	private int monthRecent_3;
	private int scheduledVisit_4;
	private int scheduledRemote_4;
	private int monthResult_4; 
	private int monthRecent_4;
	private int scheduledVisit_5;
	private int scheduledRemote_5;
	private int monthResult_5; 
	private int monthRecent_5;
	private int scheduledVisit_6;
	private int scheduledRemote_6;
	private int monthResult_6; 
	private int monthRecent_6;
	private int scheduledVisit_7;
	private int scheduledRemote_7;
	private int monthResult_7; 
	private int monthRecent_7;
	private int scheduledVisit_8;
	private int scheduledRemote_8;
	private int monthResult_8; 
	private int monthRecent_8;
	private int scheduledVisit_9;
	private int scheduledRemote_9;
	private int monthResult_9; 
	private int monthRecent_9;
	private int scheduledVisit_10;
	private int scheduledRemote_10;
	private int monthResult_10; 
	private int monthRecent_10;
	private int scheduledVisit_11;
	private int scheduledRemote_11;
	private int monthResult_11; 
	private int monthRecent_11;
	private int scheduledVisit_12;
	private int scheduledRemote_12;
	private int monthResult_12; 
	private int monthRecent_12;
	
	// 전체 카운트 getter / setter 
	public int getTotalVisit() {
		return totalVisit;
	}
	public void setTotalVisit() {
		this.totalVisit = this.scheduledVisit_1 + this.scheduledVisit_2 + this.scheduledVisit_3 + this.scheduledVisit_4 + this.scheduledVisit_5 + this.scheduledVisit_6 + this.scheduledVisit_7 + this.scheduledVisit_8 + this.scheduledVisit_9 + this.scheduledVisit_10 + this.scheduledVisit_11 + this.scheduledVisit_12;
	}
	public int getTotalRemote() {
		return totalRemote;
	}
	public void setTotalRemote() {
		this.totalRemote = this.scheduledRemote_1 + this.scheduledRemote_2 + this.scheduledRemote_3 + this.scheduledRemote_4 + this.scheduledRemote_5 + this.scheduledRemote_6 + this.scheduledRemote_7 + this.scheduledRemote_8 + this.scheduledRemote_9 + this.scheduledRemote_10 + this.scheduledRemote_11 + this.scheduledRemote_12;
	}
	public int getTotalResultSum() {
		return totalResultSum;
	}
	public void setTotalResultSum() {
		this.totalResultSum = this.totalVisit + this.totalRemote;
	}
	public int getTotalRecentSum() {
		return totalRecentSum;
	}
	public void setTotalRecentSum() {
		this.totalRecentSum = this.monthRecent_1 + this.monthRecent_2 + this.monthRecent_3 + this.monthRecent_4 + this.monthRecent_5 + this.monthRecent_6 + this.monthRecent_7 + this.monthRecent_8 + this.monthRecent_9 + this.monthRecent_10 + this.monthRecent_11 + this.monthRecent_12;
	}
	
	// project 별 getter / setter
	public int getProjectVisit() {
		return projectVisit;
	}
	public void setProjectVisit(int projectVisit) {
		this.projectVisit = projectVisit;
	}
	public int getProjectRemote() {
		return projectRemote;
	}
	public void setProjectRemote(int projectRemote) {
		this.projectRemote = projectRemote;
	}
	public int getProjectResultSum() {
		return projectResultSum;
	}
	public void setProjectResultSum() {
		this.projectResultSum = this.projectVisit + this.projectRemote;
	}
	public int getProjectRecentSum() {
		return projectRecentSum;
	}
	public void setProjectRecentSum(int projectRecentSum) {
		this.projectRecentSum = projectRecentSum;
	}
	
	// 월별 getter / setter
	public int getScheduledVisit_1() {
		return scheduledVisit_1;
	}
	public void setScheduledVisit_1(int scheduledVisit_1) {
		this.scheduledVisit_1 = scheduledVisit_1;
	}
	public int getScheduledRemote_1() {
		return scheduledRemote_1;
	}
	public void setScheduledRemote_1(int scheduledRemote_1) {
		this.scheduledRemote_1 = scheduledRemote_1;
	}
	public int getMonthResult_1() {
		return monthResult_1;
	}
	public void setMonthResult_1() {
		this.monthResult_1 = this.scheduledVisit_1 + this.scheduledRemote_1;
	}
	public int getMonthRecent_1() {
		return monthRecent_1;
	}
	public void setMonthRecent_1(int monthRecent_1) {
		this.monthRecent_1 = monthRecent_1;
	}
	public int getScheduledVisit_2() {
		return scheduledVisit_2;
	}
	public void setScheduledVisit_2(int scheduledVisit_2) {
		this.scheduledVisit_2 = scheduledVisit_2;
	}
	public int getScheduledRemote_2() {
		return scheduledRemote_2;
	}
	public void setScheduledRemote_2(int scheduledRemote_2) {
		this.scheduledRemote_2 = scheduledRemote_2;
	}
	public int getMonthResult_2() {
		return monthResult_2;
	}
	public void setMonthResult_2() {
		this.monthResult_2 = this.scheduledVisit_2 + this.scheduledRemote_2;
	}
	public int getMonthRecent_2() {
		return monthRecent_2;
	}
	public void setMonthRecent_2(int monthRecent_2) {
		this.monthRecent_2 = monthRecent_2;
	}
	public int getScheduledVisit_3() {
		return scheduledVisit_3;
	}
	public void setScheduledVisit_3(int scheduledVisit_3) {
		this.scheduledVisit_3 = scheduledVisit_3;
	}
	public int getScheduledRemote_3() {
		return scheduledRemote_3;
	}
	public void setScheduledRemote_3(int scheduledRemote_3) {
		this.scheduledRemote_3 = scheduledRemote_3;
	}
	public int getMonthResult_3() {
		return monthResult_3;
	}
	public void setMonthResult_3() {
		this.monthResult_3 = this.scheduledVisit_3 + this.scheduledRemote_3;
	}
	public int getMonthRecent_3() {
		return monthRecent_3;
	}
	public void setMonthRecent_3(int monthRecent_3) {
		this.monthRecent_3 = monthRecent_3;
	}
	public int getScheduledVisit_4() {
		return scheduledVisit_4;
	}
	public void setScheduledVisit_4(int scheduledVisit_4) {
		this.scheduledVisit_4 = scheduledVisit_4;
	}
	public int getScheduledRemote_4() {
		return scheduledRemote_4;
	}
	public void setScheduledRemote_4(int scheduledRemote_4) {
		this.scheduledRemote_4 = scheduledRemote_4;
	}
	public int getMonthResult_4() {
		return monthResult_4;
	}
	public void setMonthResult_4() {
		this.monthResult_4 = this.scheduledVisit_4 + this.scheduledRemote_4;
	}
	public int getMonthRecent_4() {
		return monthRecent_4;
	}
	public void setMonthRecent_4(int monthRecent_4) {
		this.monthRecent_4 = monthRecent_4;
	}
	public int getScheduledVisit_5() {
		return scheduledVisit_5;
	}
	public void setScheduledVisit_5(int scheduledVisit_5) {
		this.scheduledVisit_5 = scheduledVisit_5;
	}
	public int getScheduledRemote_5() {
		return scheduledRemote_5;
	}
	public void setScheduledRemote_5(int scheduledRemote_5) {
		this.scheduledRemote_5 = scheduledRemote_5;
	}
	public int getMonthResult_5() {
		return monthResult_5;
	}
	public void setMonthResult_5() {
		this.monthResult_5 = this.scheduledVisit_5 + this.scheduledRemote_5;
	}
	public int getMonthRecent_5() {
		return monthRecent_5;
	}
	public void setMonthRecent_5(int monthRecent_5) {
		this.monthRecent_5 = monthRecent_5;
	}
	public int getScheduledVisit_6() {
		return scheduledVisit_6;
	}
	public void setScheduledVisit_6(int scheduledVisit_6) {
		this.scheduledVisit_6 = scheduledVisit_6;
	}
	public int getScheduledRemote_6() {
		return scheduledRemote_6;
	}
	public void setScheduledRemote_6(int scheduledRemote_6) {
		this.scheduledRemote_6 = scheduledRemote_6;
	}
	public int getMonthResult_6() {
		return monthResult_6;
	}
	public void setMonthResult_6() {
		this.monthResult_6 = this.scheduledVisit_6 + this.scheduledRemote_6;
	}
	public int getMonthRecent_6() {
		return monthRecent_6;
	}
	public void setMonthRecent_6(int monthRecent_6) {
		this.monthRecent_6 = monthRecent_6;
	}
	public int getScheduledVisit_7() {
		return scheduledVisit_7;
	}
	public void setScheduledVisit_7(int scheduledVisit_7) {
		this.scheduledVisit_7 = scheduledVisit_7;
	}
	public int getScheduledRemote_7() {
		return scheduledRemote_7;
	}
	public void setScheduledRemote_7(int scheduledRemote_7) {
		this.scheduledRemote_7 = scheduledRemote_7;
	}
	public int getMonthResult_7() {
		return monthResult_7;
	}
	public void setMonthResult_7() {
		this.monthResult_7 = this.scheduledVisit_7 + this.scheduledRemote_7;
	}
	public int getMonthRecent_7() {
		return monthRecent_7;
	}
	public void setMonthRecent_7(int monthRecent_7) {
		this.monthRecent_7 = monthRecent_7;
	}
	public int getScheduledVisit_8() {
		return scheduledVisit_8;
	}
	public void setScheduledVisit_8(int scheduledVisit_8) {
		this.scheduledVisit_8 = scheduledVisit_8;
	}
	public int getScheduledRemote_8() {
		return scheduledRemote_8;
	}
	public void setScheduledRemote_8(int scheduledRemote_8) {
		this.scheduledRemote_8 = scheduledRemote_8;
	}
	public int getMonthResult_8() {
		return monthResult_8;
	}
	public void setMonthResult_8() {
		this.monthResult_8 = this.scheduledVisit_8 + this.scheduledRemote_8;
	}
	public int getMonthRecent_8() {
		return monthRecent_8;
	}
	public void setMonthRecent_8(int monthRecent_8) {
		this.monthRecent_8 = monthRecent_8;
	}
	public int getScheduledVisit_9() {
		return scheduledVisit_9;
	}
	public void setScheduledVisit_9(int scheduledVisit_9) {
		this.scheduledVisit_9 = scheduledVisit_9;
	}
	public int getScheduledRemote_9() {
		return scheduledRemote_9;
	}
	public void setScheduledRemote_9(int scheduledRemote_9) {
		this.scheduledRemote_9 = scheduledRemote_9;
	}
	public int getMonthResult_9() {
		return monthResult_9;
	}
	public void setMonthResult_9() {
		this.monthResult_9 = this.scheduledVisit_9 + this.scheduledRemote_9;
	}
	public int getMonthRecent_9() {
		return monthRecent_9;
	}
	public void setMonthRecent_9(int monthRecent_9) {
		this.monthRecent_9 = monthRecent_9;
	}
	public int getScheduledVisit_10() {
		return scheduledVisit_10;
	}
	public void setScheduledVisit_10(int scheduledVisit_10) {
		this.scheduledVisit_10 = scheduledVisit_10;
	}
	public int getScheduledRemote_10() {
		return scheduledRemote_10;
	}
	public void setScheduledRemote_10(int scheduledRemote_10) {
		this.scheduledRemote_10 = scheduledRemote_10;
	}
	public int getMonthResult_10() {
		return monthResult_10;
	}
	public void setMonthResult_10() {
		this.monthResult_10 = this.scheduledVisit_10 + this.scheduledRemote_10;
	}
	public int getMonthRecent_10() {
		return monthRecent_10;
	}
	public void setMonthRecent_10(int monthRecent_10) {
		this.monthRecent_10 = monthRecent_10;
	}
	public int getScheduledVisit_11() {
		return scheduledVisit_11;
	}
	public void setScheduledVisit_11(int scheduledVisit_11) {
		this.scheduledVisit_11 = scheduledVisit_11;
	}
	public int getScheduledRemote_11() {
		return scheduledRemote_11;
	}
	public void setScheduledRemote_11(int scheduledRemote_11) {
		this.scheduledRemote_11 = scheduledRemote_11;
	}
	public int getMonthResult_11() {
		return monthResult_11;
	}
	public void setMonthResult_11() {
		this.monthResult_11 = this.scheduledVisit_11 + this.scheduledRemote_11;
	}
	public int getMonthRecent_11() {
		return monthRecent_11;
	}
	public void setMonthRecent_11(int monthRecent_11) {
		this.monthRecent_11 = monthRecent_11;
	}
	public int getScheduledVisit_12() {
		return scheduledVisit_12;
	}
	public void setScheduledVisit_12(int scheduledVisit_12) {
		this.scheduledVisit_12 = scheduledVisit_12;
	}
	public int getScheduledRemote_12() {
		return scheduledRemote_12;
	}
	public void setScheduledRemote_12(int scheduledRemote_12) {
		this.scheduledRemote_12 = scheduledRemote_12;
	}
	public int getMonthResult_12() {
		return monthResult_12;
	}
	public void setMonthResult_12() {
		this.monthResult_12 = this.scheduledVisit_12 + this.scheduledRemote_12;
	}
	public int getMonthRecent_12() {
		return monthRecent_12;
	}
	public void setMonthRecent_12(int monthRecent_12) {
		this.monthRecent_12 = monthRecent_12;
	}
}
