package com.cubrid.checkup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cubrid.checkup.dao.CheckupDaoImpl;
import com.cubrid.checkup.vo.CubMemberVo;
import com.cubrid.checkup.vo.JobOpVo;
import com.cubrid.checkup.vo.MainConSubVo;
import com.cubrid.checkup.vo.MonthInfoVo;

public class CheckUpServiceImpl implements CheckUpService {
	private CheckupDaoImpl dao = new CheckupDaoImpl();

	@Override
	public Map<String, Object> selectResult(int year, String name) {
		// TODO Auto-generated method stub
		return dao.selectResult(year, name);
	}

	@Override
	public int insertCheckAll(MainConSubVo vo, String date) {
		// TODO Auto-generated method stub
		return dao.insertCheckAll(vo, date);
	}

	@Override
	public int updateCheckAll(MainConSubVo vo) {
		// TODO Auto-generated method stub
		return dao.updateCheckAll(vo);
	}

	@Override
	public int updateCheckMonth(JobOpVo vo) {
		// TODO Auto-generated method stub
		return dao.updateCheckMonth(vo);
	}

	@Override
	public int insertCompleteCheck(JobOpVo vo) {
		// TODO Auto-generated method stub
		return dao.insertCompleteCheck(vo);
	}

	@Override
	public int recordCount(int year, String name) {
		// TODO Auto-generated method stub
		return dao.recordCount(year, name);
	}

	@Override
	public MainConSubVo selectInfoByCodeSeq(String con_id, String con_year) {
		// TODO Auto-generated method stub
		return dao.selectInfoByCodeSeq(con_id, con_year);
	}

	@Override
	public Map<String, Object> selectCompleteShow(JobOpVo vo) {
		// TODO Auto-generated method stub
		return dao.selectCompleteShow(vo);
	}

	@Override
	public int completeRollback(JobOpVo vo) {
		// TODO Auto-generated method stub
		return dao.completeRollback(vo);
	}

	@Override
	public List<JobOpVo> selectMonth(JobOpVo vo) {
		// TODO Auto-generated method stub
		return dao.selectMonth(vo);
	}

	@Override
	public Map<String, Object> selectHistory(int year, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.selectHistory(year, map);
	}

	@Override
	public int deletePlan(JobOpVo vo) {
		// TODO Auto-generated method stub
		return dao.deletePlan(vo);
	}

	@Override
	public List<MonthInfoVo> getCheckInfo(String year, String month) {
		// TODO Auto-generated method stub
		return dao.getCheckInfo(year, month);
	}

	@Override
	public int deleteSite(String con_id, String con_year) {
		// TODO Auto-generated method stub
		return dao.deleteSite(con_id, con_year);
	}

	@Override
	public int insertYear() {
		// TODO Auto-generated method stub
		return dao.insertYear();
	}

	@Override
	public ArrayList<String> selectYear() {
		// TODO Auto-generated method stub
		return dao.selectYear();
	}

	@Override
	public String idFromJira(String name) {
		// TODO Auto-generated method stub
		return dao.idFromJira(name);
	}

	@Override
	public List<CubMemberVo> selectMember(String type) {
		// TODO Auto-generated method stub
		return dao.selectMember(type);
	}

	@Override
	public int updateCubMember(List<CubMemberVo> list) {
		// TODO Auto-generated method stub
		return dao.updateCubMember(list);
	}

}
