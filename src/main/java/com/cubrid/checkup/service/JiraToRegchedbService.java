package com.cubrid.checkup.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.cubrid.checkup.vo.MainConSubVo;

public interface JiraToRegchedbService {
	/**
	 * 
	 * @return boolean
	 * 
	 * jira db���� ���� ������ �˻��Ͽ� 
	 * regchedb�� main_con ���̺�� ������ insert �ϴ� �޼ҵ�
	 */
	Map<String, Integer> getCustomFromJira(List<MainConSubVo> list);
	
	/**
	 * 
	 * @return Map<String, Object>
	 * 
	 * JIRA DB ���� updated �÷��� �������� ���� ������ ����� �̾ƿ��� �޼ҵ�
	 */
	List<MainConSubVo> getUpdatedData();

	/**
	 * 
	 * @param String con_id
	 * @param String con_year
	 * @return int
	 * @throws SQLException 
	 * @see ���ο� �׸��� ���� ��� �߰��ϴ� �޼ҵ�
	 */
	int insertData(MainConSubVo vo) throws SQLException;
	
	/**
	 * 
	 * @param String con_id
	 * @param String con_year
	 * @return int
	 * @throws SQLException 
	 * @see ������ �׸��� ���� ��� update �ϴ� �޼ҵ�
	 */
	int updateData(MainConSubVo vo) throws SQLException;
	
	/**
	 * jira���� ����� ���� �������� 
	 * @param
	 * 
	 */
	void insertOrUpdateFromJiraMember();
}
