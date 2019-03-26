package com.cubrid.logging;

import java.util.List;
import java.util.Map;

import com.cubrid.checkup.vo.CubMemberVo;
import com.cubrid.checkup.vo.LoggingVo;
import com.cubrid.checkup.vo.MainConSubVo;
/**
 * 
 * @author HUN
 * @since 2014-12-24
 * @ �������˰� ���õ� log�� DB�� ����� ���� ����� ������ interface
 */
public interface CheckupLogging {

	/**
	 * @param 
	 * 
	 * @ �������� ��ü ���� ��� �α� �޼ҵ�
	 */
	void entirePlanInsert(MainConSubVo vo, List<String> dateList);
	
	/**
	 * @param 
	 * 
	 * @ �������� ��ü ���� ���� �α� �޼ҵ�
	 */
	void entirePlanUpdate(Map<String, MainConSubVo> voMap, List<String> completeDateList, Map<String, List<String>> listMap);
	
	/**
	 * @param 
	 * 
	 * @ �������� ���� ��¥ ����
	 */
	void selectedPlanUpdate(Map<String, MainConSubVo> map, String originDate, String updateDate);
	
	/**
	 * @param 
	 * 
	 * @ �������� �Ϸ� ��� �α� �޼ҵ�
	 */
	void registerRegularCheckup(MainConSubVo vo, String planDate, String completeDate);
	
	/**
	 * @param 
	 * 
	 * @ �������� �Ϸ� ��ϵ� ���� ���� �α� �޼ҵ�
	 */
	void updateCompletedPlan(Map<String, MainConSubVo> map, String originDate, String updateDate); 
	
	/**
	 * @param 
	 * 
	 * @ �������� �Ϸ� ��� ���� �ʱ�ȭ �α� �޼ҵ�
	 */
	void rollbackCompletedPlan(MainConSubVo vo, String originDate);
	
	/**
	 * @param 
	 * 
	 * @ �Ϸ���� ���� ���� ���� �ϳ��� ���� ��  �α� �޼ҵ�
	 */
	void deleteAPlan(MainConSubVo vo, String originDate);
	
	/**
	 * @param 
	 * 
	 * @ ���õ� ����Ʈ �ϳ��� �����ϴ� �޼ҵ�
	 */
	void deleteSelectedSite(MainConSubVo vo);
	
	/**
	 * @param 
	 * 
	 * @ ���� �� ���ó⵵�� ���Ӱ� ��ϵ� ��� �α� �޼ҵ�
	 */
	void insertNewYear(String year);
	
	/**
	 * @param 
	 * 
	 * @ JIRA�� ��ϵǾ� �ִ� ������Ʈ ������ ó�� DB�� insrt �� �� �α� �޼ҵ�
	 */
	void insertFromJira(List<MainConSubVo> insertList);
	
	/**
	 * @param 
	 * 
	 * @ JIRA�� ��ϵǾ� �ִ� ������Ʈ ������ ���ŵǾ� DB�� update �� �� �α� �޼ҵ�
	 */
	void updateFromJira(Map<String, List<MainConSubVo>> map);
	
	/**
	 * JIRA���� 
	 * @param
	 */
	void addCubMember(List<CubMemberVo> memberList);
	
	/**
	 * @ DB ���� ���� �޼ҵ�
	 */
	void close();
	
	/**
	 * 
	 * @param
	 * @return 
	 * @ UPDATE �Ǳ� �� ������ �������� �޼ҵ�
	 */
	MainConSubVo beforeUpdatedVo(String con_id, String con_year);
	
	/**
	 * logging ������ �� �������� ����ϱ� ���� select �ϴ� �޼ҵ�
	 * @param 
	 * @return
	 */
	List<LoggingVo> selectAll(String year, String condition, String arg);
	
	/**
	 * CUB_MEMBER ���̺��� �ο����� ������ �ο��� ��� logging �ϴ� �޼ҵ�
	 * @param
	 */
	void updateCubMember(List<CubMemberVo> vo);
}
