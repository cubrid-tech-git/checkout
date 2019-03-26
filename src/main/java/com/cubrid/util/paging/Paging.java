package com.cubrid.util.paging;

public class Paging {
	private int totalCount;	// 전체 레코드 수 : DB에서 가져옴
	private int recordSize;	// 한 페이지에 뿌려질 레코드의 수 : 초기화 필요
	private int blockCount;	// 한 블록당 뿌려질 페이지번호의 갯수 : 초기화 필요
	private int pageCount;	// 총 페이지 수
	private int startRecord;// 페이지번호에 해당하는 레코드의 시작점
	private int endRecord;	// 페이지번호에 해당하는 레코드의 끝점
	private int curPageNum = 1;	// 현재 페이지 번호
	private int temp;		// 시작 페이지를 구하기 위한 임시 변수
	private int startPage;	// 시작 페이지
	
	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}
	public int getRecordSize() {
		return recordSize;
	}
	
	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}
	public int getBlockCount() {
		return blockCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	
	/**
	 * @param
	 * @return pageCount
	 * 
	 * 총 페이지 수 반환하는 메소드
	 */
	public int getPageCount() {
		pageCount = (int) Math.ceil((double)this.totalCount / this.recordSize);
		return pageCount;
	}
	
	/**
	 * @param
	 * @return startRecord
	 * 
	 * 현재 페이지에서 시작 레코드의 번호를 반환하는 메소드
	 */
	public int getStartRecord() {
		startRecord = (curPageNum - 1) * this.getRecordSize() + 1;
		return startRecord;
	}
	
	/**
	 * @param
	 * @return endRecord
	 * 
	 * 현재 페이지에서 마지막 레코드의 번호를 반환하는 메소드
	 */
	public int getEndRecord() {
		endRecord = this.getStartRecord() - 1 + this.getRecordSize();
		return endRecord;
	}
	
	/**
	 * @param
	 * @return
	 * 
	 * 현재 페이지의 번호를 반환하는 메소드
	 */
	public int getCurPageNum() {
		curPageNum = this.getEndRecord() / this.getRecordSize();
		return curPageNum;
	}
	public void setCurPageNum(int curPageNum) {
		this.curPageNum = curPageNum;
	}
	
	/**
	 * @param
	 * @return startPage
	 * 
	 *  화면의 시작페이지를 구하는 메소드(몇번부터 출력할지를 결정)
	 */
	public int getStartPage() {
		temp = (curPageNum - 1) % blockCount;
		startPage = curPageNum - temp;
		return startPage;
	}
	
	
}
