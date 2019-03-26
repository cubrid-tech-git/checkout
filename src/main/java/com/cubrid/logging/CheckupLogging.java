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
 * @ 정기점검과 관련된 log를 DB에 남기기 위한 기능을 정리한 interface
 */
public interface CheckupLogging {

	/**
	 * @param 
	 * 
	 * @ 정기점검 전체 일정 등록 로그 메소드
	 */
	void entirePlanInsert(MainConSubVo vo, List<String> dateList);
	
	/**
	 * @param 
	 * 
	 * @ 정기점검 전체 일정 수정 로그 메소드
	 */
	void entirePlanUpdate(Map<String, MainConSubVo> voMap, List<String> completeDateList, Map<String, List<String>> listMap);
	
	/**
	 * @param 
	 * 
	 * @ 정기점검 선택 날짜 수정
	 */
	void selectedPlanUpdate(Map<String, MainConSubVo> map, String originDate, String updateDate);
	
	/**
	 * @param 
	 * 
	 * @ 정기점검 완료 등록 로그 메소드
	 */
	void registerRegularCheckup(MainConSubVo vo, String planDate, String completeDate);
	
	/**
	 * @param 
	 * 
	 * @ 정기점검 완료 등록된 내용 수정 로그 메소드
	 */
	void updateCompletedPlan(Map<String, MainConSubVo> map, String originDate, String updateDate); 
	
	/**
	 * @param 
	 * 
	 * @ 정기점검 완료 등록 내용 초기화 로그 메소드
	 */
	void rollbackCompletedPlan(MainConSubVo vo, String originDate);
	
	/**
	 * @param 
	 * 
	 * @ 완료되지 않은 점검 일정 하나를 지울 때  로그 메소드
	 */
	void deleteAPlan(MainConSubVo vo, String originDate);
	
	/**
	 * @param 
	 * 
	 * @ 선택된 사이트 하나를 삭제하는 메소드
	 */
	void deleteSelectedSite(MainConSubVo vo);
	
	/**
	 * @param 
	 * 
	 * @ 매해 초 선택년도를 새롭게 등록될 경우 로그 메소드
	 */
	void insertNewYear(String year);
	
	/**
	 * @param 
	 * 
	 * @ JIRA에 등록되어 있는 프로젝트 정보를 처음 DB에 insrt 할 때 로그 메소드
	 */
	void insertFromJira(List<MainConSubVo> insertList);
	
	/**
	 * @param 
	 * 
	 * @ JIRA에 등록되어 있는 프로젝트 정보가 갱신되어 DB에 update 할 때 로그 메소드
	 */
	void updateFromJira(Map<String, List<MainConSubVo>> map);
	
	/**
	 * JIRA에서 
	 * @param
	 */
	void addCubMember(List<CubMemberVo> memberList);
	
	/**
	 * @ DB 연결 끊는 메소드
	 */
	void close();
	
	/**
	 * 
	 * @param
	 * @return 
	 * @ UPDATE 되기 전 값들을 가져오는 메소드
	 */
	MainConSubVo beforeUpdatedVo(String con_id, String con_year);
	
	/**
	 * logging 정보를 웹 페이지에 출력하기 위해 select 하는 메소드
	 * @param 
	 * @return
	 */
	List<LoggingVo> selectAll(String year, String condition, String arg);
	
	/**
	 * CUB_MEMBER 테이블의 인원에게 권한을 부여할 경우 logging 하는 메소드
	 * @param
	 */
	void updateCubMember(List<CubMemberVo> vo);
}
