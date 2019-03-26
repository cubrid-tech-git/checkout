package com.cubrid.util.paging;

public class Paging {
	private int totalCount;	// ��ü ���ڵ� �� : DB���� ������
	private int recordSize;	// �� �������� �ѷ��� ���ڵ��� �� : �ʱ�ȭ �ʿ�
	private int blockCount;	// �� ��ϴ� �ѷ��� ��������ȣ�� ���� : �ʱ�ȭ �ʿ�
	private int pageCount;	// �� ������ ��
	private int startRecord;// ��������ȣ�� �ش��ϴ� ���ڵ��� ������
	private int endRecord;	// ��������ȣ�� �ش��ϴ� ���ڵ��� ����
	private int curPageNum = 1;	// ���� ������ ��ȣ
	private int temp;		// ���� �������� ���ϱ� ���� �ӽ� ����
	private int startPage;	// ���� ������
	
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
	 * �� ������ �� ��ȯ�ϴ� �޼ҵ�
	 */
	public int getPageCount() {
		pageCount = (int) Math.ceil((double)this.totalCount / this.recordSize);
		return pageCount;
	}
	
	/**
	 * @param
	 * @return startRecord
	 * 
	 * ���� ���������� ���� ���ڵ��� ��ȣ�� ��ȯ�ϴ� �޼ҵ�
	 */
	public int getStartRecord() {
		startRecord = (curPageNum - 1) * this.getRecordSize() + 1;
		return startRecord;
	}
	
	/**
	 * @param
	 * @return endRecord
	 * 
	 * ���� ���������� ������ ���ڵ��� ��ȣ�� ��ȯ�ϴ� �޼ҵ�
	 */
	public int getEndRecord() {
		endRecord = this.getStartRecord() - 1 + this.getRecordSize();
		return endRecord;
	}
	
	/**
	 * @param
	 * @return
	 * 
	 * ���� �������� ��ȣ�� ��ȯ�ϴ� �޼ҵ�
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
	 *  ȭ���� ������������ ���ϴ� �޼ҵ�(������� ��������� ����)
	 */
	public int getStartPage() {
		temp = (curPageNum - 1) % blockCount;
		startPage = curPageNum - temp;
		return startPage;
	}
	
	
}
