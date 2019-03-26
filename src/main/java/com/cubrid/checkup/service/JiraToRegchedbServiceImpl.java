package com.cubrid.checkup.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.cubrid.checkup.dao.JiraToRegchedbDaoImpl;
import com.cubrid.checkup.vo.MainConSubVo;

public class JiraToRegchedbServiceImpl implements JiraToRegchedbService {
	private JiraToRegchedbDaoImpl dao = new JiraToRegchedbDaoImpl();

	@Override
	public Map<String, Integer> getCustomFromJira(List<MainConSubVo> list) {
		// TODO Auto-generated method stub
		return dao.getCustomFromJira(list);
	}

	@Override
	public List<MainConSubVo> getUpdatedData() {
		// TODO Auto-generated method stub
		return dao.getUpdatedData();
	}

	@Override
	public int insertData(MainConSubVo vo) throws SQLException {
		// TODO Auto-generated method stub
		return dao.insertData(vo);
	}

	@Override
	public int updateData(MainConSubVo vo) throws SQLException {
		// TODO Auto-generated method stub
		return dao.updateData(vo);
	}

	@Override
	public void insertOrUpdateFromJiraMember() {
		// TODO Auto-generated method stub
		dao.insertOrUpdateFromJiraMember();
	}

}
