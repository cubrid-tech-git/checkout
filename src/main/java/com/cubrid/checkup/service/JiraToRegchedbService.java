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
	 * jira db에서 고객사 정보를 검색하여 
	 * regchedb의 main_con 테이블로 값들을 insert 하는 메소드
	 */
	Map<String, Integer> getCustomFromJira(List<MainConSubVo> list);
	
	/**
	 * 
	 * @return Map<String, Object>
	 * 
	 * JIRA DB 에서 updated 컬럼을 기준으로 새로 수정된 놈들을 뽑아오는 메소드
	 */
	List<MainConSubVo> getUpdatedData();

	/**
	 * 
	 * @param String con_id
	 * @param String con_year
	 * @return int
	 * @throws SQLException 
	 * @see 새로운 항목이 있을 경우 추가하는 메소드
	 */
	int insertData(MainConSubVo vo) throws SQLException;
	
	/**
	 * 
	 * @param String con_id
	 * @param String con_year
	 * @return int
	 * @throws SQLException 
	 * @see 수정된 항목이 있을 경우 update 하는 메소드
	 */
	int updateData(MainConSubVo vo) throws SQLException;
	
	/**
	 * jira에서 사용자 정보 가져오기 
	 * @param
	 * 
	 */
	void insertOrUpdateFromJiraMember();
}
