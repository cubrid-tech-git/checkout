package com.cubrid.checkup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cubrid.checkup.vo.CubMemberVo;
import com.cubrid.checkup.vo.JobOpVo;
import com.cubrid.checkup.vo.MainConSubVo;
import com.cubrid.checkup.vo.MonthInfoVo;

public interface CheckUpService {
	/**
	 * 
	 * @param int year
	 * @return
	 * 
	 * record�� ��ü ������ �����ϴ� �޼ҵ�
	 */
	int recordCount(int year, String name);
	/**
	 * 
	 * @param int year
	 * @return Map<String, Object>
	 * 
	 * ��Ȳ�� ����ȭ�鿡 �ѷ��� �����͵��� ��� ������ ���� �޼ҵ�
	 */
	Map<String, Object> selectResult(int year, String name);

	/**
	 * 
	 * @param MainConSubVo vo
	 * @return int
	 * 
	 * �������� ��ü ���� ��� �޼ҵ�
	 */
	int insertCheckAll(MainConSubVo vo, String date);

	/**
	 * 
	 * @param MainConSubVo vo
	 * @return int
	 * 
	 * �������� ��ü ���� �����ϴ� �޼ҵ�
	 */
	int updateCheckAll(MainConSubVo vo);

	/**
	 * 
	 * @param JobOpVo vo
	 * @return int
	 * 
	 * �������� ���� ��¥ ���� �޼ҵ�
	 */
	int updateCheckMonth(JobOpVo vo);

	/**
	 * 
	 * @param JobOpVo vo
	 * @return int
	 * 
	 * �������� �Ϸ� ��� �޼ҵ�
	 */
	int insertCompleteCheck(JobOpVo vo);

	/**
	 * 
	 * @param String con_id
	 * @param int con_year
	 * @return MainConSubVo
	 * 
	 * main_con_sub ���̺��� id �� seq ���� �ش��ϴ� �����͸� �������� �޼ҵ� 
	 */
	MainConSubVo selectInfoByCodeSeq(String con_id, String con_year);
	
	/**
	 * 
	 * @param JobOpVo vo
	 * @return Map<String, Object>
	 * 
	 * �Ϸ� ȭ���� �����ֱ� ���� ������ �˻��ϴ� �޼ҵ�
	 */
	Map<String, Object> selectCompleteShow(JobOpVo vo);
	
	/**
	 * 
	 * @param JobOpVo vo
	 * @return int
	 * 
	 * ������ �Ϸ�� �ϳ��� �������� �ʱ�ȭ ��Ű�� �޼ҵ�
	 */
	int completeRollback(JobOpVo vo);
	
	/**
	 * 
	 * @param JobOpVo vo
	 * @return List<JobOpVo>
	 * 
	 * Ư�� �޿� ���� ������, ������ ��¥�� ���ϴ� �޼ҵ�
	 */
	List<JobOpVo> selectMonth(JobOpVo vo);
	
	/**
	 * 
	 * @return Map<String, Object>
	 * 
	 * �̷º��⿡�� ���ڵ���� �������� �޼ҵ�
	 */
	Map<String, Object> selectHistory(int year, Map<String, Object> map);
	
	/**
	 * 
	 * @param JobOpVo vo
	 * @return int;
	 * 
	 * �ϳ��� ��ȹ�� ����� �޼ҵ�
	 */
	int deletePlan(JobOpVo vo);
	
	/**
	 * 
	 * @param String year
	 * @param String month
	 * @return MonthInfoVo
	 * 
	 * �����ڿ� ���� ���� ��Ȳ ����
	 */
	List<MonthInfoVo> getCheckInfo(String year, String month);
	
	/**
	 * 
	 * @param con_id
	 * @param con_year
	 * @return int
	 * 
	 * ���� ����Ʈ�� �����ϴ� �޼ҵ�
	 */
	int deleteSite(String con_id, String con_year);
	
	/**
	 * 
	 * @return int
	 * 
	 * @see ���ó⵵�� YEAR �κ��� �ڵ����� ������ִ� �޼ҵ�
	 */
	int insertYear();
	
	/**
	 * 
	 * @return ArrayList<String>
	 * 
	 * @see ���ó⵵�� YEAR �κ��� �������� �޼ҵ�
	 */
	ArrayList<String> selectYear();
	
	/**
	 * JIRA ���̵� �ش��ϴ� �ѱ� �̸��� �����ϴ� �޼ҵ�
	 * @param 
	 * @return
	 */
	String idFromJira(String name);
	
	/**
	 * CUBRID member ��ü�� ��ȸ�ϴ� �޼ҵ�
	 * @param
	 * @return
	 */
	List<CubMemberVo> selectMember(String type);

	/**
	 * CUBRID member�� ������ UPDATE �ϴ� �޼ҵ�
	 * @param 
	 * @return
	 */
	int updateCubMember(List<CubMemberVo> list);
}
