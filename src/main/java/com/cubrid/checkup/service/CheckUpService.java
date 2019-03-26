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
	 * record의 전체 갯수를 리턴하는 메소드
	 */
	int recordCount(int year, String name);
	/**
	 * 
	 * @param int year
	 * @return Map<String, Object>
	 * 
	 * 현황판 메인화면에 뿌려질 데이터들을 모두 가지고 오는 메소드
	 */
	Map<String, Object> selectResult(int year, String name);

	/**
	 * 
	 * @param MainConSubVo vo
	 * @return int
	 * 
	 * 정기점검 전체 일정 등록 메소드
	 */
	int insertCheckAll(MainConSubVo vo, String date);

	/**
	 * 
	 * @param MainConSubVo vo
	 * @return int
	 * 
	 * 정기점검 전체 일정 수정하는 메소드
	 */
	int updateCheckAll(MainConSubVo vo);

	/**
	 * 
	 * @param JobOpVo vo
	 * @return int
	 * 
	 * 정기점검 선택 날짜 수정 메소드
	 */
	int updateCheckMonth(JobOpVo vo);

	/**
	 * 
	 * @param JobOpVo vo
	 * @return int
	 * 
	 * 정기점검 완료 등록 메소드
	 */
	int insertCompleteCheck(JobOpVo vo);

	/**
	 * 
	 * @param String con_id
	 * @param int con_year
	 * @return MainConSubVo
	 * 
	 * main_con_sub 테이블에서 id 와 seq 값에 해당하는 데이터를 가져오는 메소드 
	 */
	MainConSubVo selectInfoByCodeSeq(String con_id, String con_year);
	
	/**
	 * 
	 * @param JobOpVo vo
	 * @return Map<String, Object>
	 * 
	 * 완료 화면을 보여주기 위한 값들을 검색하는 메소드
	 */
	Map<String, Object> selectCompleteShow(JobOpVo vo);
	
	/**
	 * 
	 * @param JobOpVo vo
	 * @return int
	 * 
	 * 점검이 완료된 하나의 점검일을 초기화 시키는 메소드
	 */
	int completeRollback(JobOpVo vo);
	
	/**
	 * 
	 * @param JobOpVo vo
	 * @return List<JobOpVo>
	 * 
	 * 특정 달에 대한 수정시, 수정될 날짜를 구하는 메소드
	 */
	List<JobOpVo> selectMonth(JobOpVo vo);
	
	/**
	 * 
	 * @return Map<String, Object>
	 * 
	 * 이력보기에서 레코드들을 가져오는 메소드
	 */
	Map<String, Object> selectHistory(int year, Map<String, Object> map);
	
	/**
	 * 
	 * @param JobOpVo vo
	 * @return int;
	 * 
	 * 하나의 계획을 지우는 메소드
	 */
	int deletePlan(JobOpVo vo);
	
	/**
	 * 
	 * @param String year
	 * @param String month
	 * @return MonthInfoVo
	 * 
	 * 점검자에 대한 월별 현황 보기
	 */
	List<MonthInfoVo> getCheckInfo(String year, String month);
	
	/**
	 * 
	 * @param con_id
	 * @param con_year
	 * @return int
	 * 
	 * 선택 사이트를 삭제하는 메소드
	 */
	int deleteSite(String con_id, String con_year);
	
	/**
	 * 
	 * @return int
	 * 
	 * @see 선택년도의 YEAR 부분을 자동으로 만들어주는 메소드
	 */
	int insertYear();
	
	/**
	 * 
	 * @return ArrayList<String>
	 * 
	 * @see 선택년도의 YEAR 부분을 가져오는 메소드
	 */
	ArrayList<String> selectYear();
	
	/**
	 * JIRA 아이디에 해당하는 한글 이름을 리턴하는 메소드
	 * @param 
	 * @return
	 */
	String idFromJira(String name);
	
	/**
	 * CUBRID member 전체를 조회하는 메소드
	 * @param
	 * @return
	 */
	List<CubMemberVo> selectMember(String type);

	/**
	 * CUBRID member의 값들을 UPDATE 하는 메소드
	 * @param 
	 * @return
	 */
	int updateCubMember(List<CubMemberVo> list);
}
