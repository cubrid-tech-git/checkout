package com.cubrid.logging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cubrid.checkup.dao.RegchedbConnectionManager;
import com.cubrid.checkup.vo.CubMemberVo;
import com.cubrid.checkup.vo.LoggingVo;
import com.cubrid.checkup.vo.MainConSubVo;
import com.cubrid.util.replace.MakeModifyReason;

public class CheckuploggingImpl implements CheckupLogging {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	@Override
	public void entirePlanInsert(MainConSubVo vo, List<String> dateList) {
		String job_id = "EPI";
		StringBuffer log_content = new StringBuffer();
		String sql = "INSERT INTO logging_tbl (job_id, con_id, con_year, log_date, log_content) "
				+ "values (?, ?, ?, sysdatetime, ?)";
		
		// log_content �ۼ�
		log_content.append("���� : " + vo.getCust_nm() + "<br>");
		log_content.append("������Ʈ : " + vo.getProc_nm() + "<br>");
		log_content.append(vo.getCon_year() + "�⵵ ��ϵ� ���� ����<br>");
		log_content.append("��� �Ⱓ : " + vo.getCon_from_date() + " ~ " + vo.getCon_to_date() + "<br>");
		log_content.append("���� ���� : " + vo.getCheck_nm() + "<br>");
		log_content.append("�����(��) : " + vo.getMain_oper_nm() + "<br>");
		log_content.append("�����(��) : " + vo.getSub_oper_nm() + "<br>");
		log_content.append("���� ���� ���� : ");
		
		for(String s : dateList) {
			log_content.append("[" + s + "] ");
		}
		System.out.println(log_content.toString());
		// log_content �ۼ� ��
		
		conn = new RegchedbConnectionManager().getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, job_id);
			pstmt.setString(2, vo.getCon_id());
			pstmt.setString(3, vo.getCon_year());
			pstmt.setString(4, log_content.toString());
			
			pstmt.execute();
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	@Override
	public void entirePlanUpdate(Map<String, MainConSubVo> voMap,
								List<String> completeDateList, 
								Map<String, List<String>> listMap) {
		String job_id = "EPU";
		StringBuffer log_content = new StringBuffer();
		String sql = "INSERT INTO logging_tbl (job_id, con_id, con_year, log_date, log_content) values (?, ?, ?, sysdatetime, ?)";
		MainConSubVo beforeUpdateVo = voMap.get("beforeUpdateVo");
		MainConSubVo updateVo = voMap.get("updateVo");
		List<String> updateDateList = listMap.get("updateDateList");
		List<String> beforeUpdateDateList = listMap.get("beforeUpdateDateList");
		
		log_content.append("INFO OF ENTIRE PLAN UPDATE<br>");
		log_content.append("con_id : " + updateVo.getCon_id() + "<br>");
		log_content.append("con_year : " + updateVo.getCon_year() + "<br>");
		log_content.append("���� ���� : " + beforeUpdateVo.getCheck_nm() + " -> " + updateVo.getCheck_nm() + "<br>");
		log_content.append("�����(��) : " + beforeUpdateVo.getMain_oper_nm() + " -> " + updateVo.getMain_oper_nm() + "<br>");
		log_content.append("�����(��) : " + beforeUpdateVo.getSub_oper_nm() + " -> " + updateVo.getSub_oper_nm() + "<br>");
		log_content.append("�Ϸ�� ���� ���� : ");
		for(String s : completeDateList) log_content.append(s + " ");
		log_content.append("<br>");
		log_content.append("������ ���� ���� :");
		log_content.append(" ���� ���� ���� : [");
		for(int i = 0; i < beforeUpdateDateList.size(); i++) {
			log_content.append(beforeUpdateDateList.get(i) + " ");
		}
		log_content.append("]<br>");
		log_content.append(" ������ ���� ���� : [");
		for(int i = 0; i < updateDateList.size(); i++) {
			log_content.append(updateDateList.get(i) + " ");
		}
		log_content.append("]<br>");
		log_content.deleteCharAt(log_content.length() - 1);
		log_content.append("<br>");
		if(updateVo.getCon_desc() != null) log_content.append("���� : " + MakeModifyReason.getWithoutRollbackData(updateVo.getCon_desc()) + "<br>");
		
		conn = new RegchedbConnectionManager().getConnection();;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, job_id);
			pstmt.setString(2, updateVo.getCon_id());
			pstmt.setString(3, updateVo.getCon_year());
			pstmt.setString(4, log_content.toString());
			
			System.out.println("entirePlanUpdate : " + pstmt.execute());
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	@Override
	public void selectedPlanUpdate(Map<String, MainConSubVo> voMap, String originDate, String updateDate) {
		String job_id = "SPU";
		StringBuffer log_content = new StringBuffer();
		String sql = "INSERT INTO logging_tbl (job_id, con_id, con_year, log_date, log_content) values (?, ?, ?, sysdatetime, ?)";
		MainConSubVo beforeUpdateVo = voMap.get("beforeUpdateVo");
		MainConSubVo updateVo = voMap.get("updateVo");

		log_content.append("INFO OF SELECTED PLAN UPDATE<br>");
		log_content.append("con_id : " + updateVo.getCon_id() + "<br>");
		log_content.append("con_year : " + updateVo.getCon_year() + "<br>");
		log_content.append("���� ���� : " + beforeUpdateVo.getCheck_nm() + " -> " + updateVo.getCheck_nm() + "<br>");
		log_content.append("�����(��) : " + beforeUpdateVo.getMain_oper_nm() + " -> " + updateVo.getMain_oper_nm() + "<br>");
		log_content.append("������ ���� ���� : " + originDate + " -> " + updateDate + "<br>");
		if(updateVo.getCon_desc() != null) log_content.append("���� : " + MakeModifyReason.getWithoutRollbackData(updateVo.getCon_desc()) + "<br>");
		
		conn = new RegchedbConnectionManager().getConnection();;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, job_id);
			pstmt.setString(2, updateVo.getCon_id());
			pstmt.setString(3, updateVo.getCon_year());
			pstmt.setString(4, log_content.toString());
			
			System.out.println("selectedPlanUpdate : " + pstmt.execute());
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	@Override
	public void registerRegularCheckup(MainConSubVo vo, String planDate,
			String completeDate) {
		String job_id = "RRC";
		String originDate = "";
		StringBuffer log_content = new StringBuffer();
		String sql = "INSERT INTO logging_tbl (job_id, con_id, con_year, log_date, log_content) values (?, ?, ?, sysdatetime, ?)";
		
		if(vo.getCon_desc() != null) originDate = MakeModifyReason.getDateAndName(vo.getCon_desc()).split(":")[0];
		else originDate = planDate;
		
		log_content.append("INFO OF REGISTER REGULAR CHECKUP<br>");
		log_content.append("con_id : " + vo.getCon_id() + "<br>");
		log_content.append("con_year : " + vo.getCon_year() + "<br>");
		log_content.append("���� ��� : " + vo.getCheck_nm() + "<br>");
		log_content.append("������ : " + vo.getMain_oper_nm() + "<br>");
		log_content.append("���� ������ : " + originDate + "<br>");
		log_content.append("���� �Ϸ��� : " + completeDate + "<br>");
		if(vo.getCon_desc() != null) log_content.append("���� : " + MakeModifyReason.getWithoutRollbackData(vo.getCon_desc()) + "<br>");
		
		conn = new RegchedbConnectionManager().getConnection();;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, job_id);
			pstmt.setString(2, vo.getCon_id());
			pstmt.setString(3, vo.getCon_year());
			pstmt.setString(4, log_content.toString());
			
			pstmt.execute();
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	@Override
	public void updateCompletedPlan(Map<String, MainConSubVo> voMap, String planDate,
			String updateDate) {
		String job_id = "UCP";
		String originDate = "";
		StringBuffer log_content = new StringBuffer();
		String sql = "INSERT INTO logging_tbl (job_id, con_id, con_year, log_date, log_content) values (?, ?, ?, sysdatetime, ?)";
		MainConSubVo beforeUpdateVo = voMap.get("beforeUpdateVo");
		MainConSubVo updateVo = voMap.get("updateVo");
		
		if(updateVo.getCon_desc() != null) originDate = MakeModifyReason.getDateAndName(updateVo.getCon_desc()).split(":")[0];
		else originDate = planDate;
		
		log_content.append("INFO OF UPDATE COMPLETED PLAN<br>");
		log_content.append("con_id : " + updateVo.getCon_id() + "<br>");
		log_content.append("con_year : " + updateVo.getCon_year() + "<br>");
		log_content.append("���� ��� : " + beforeUpdateVo.getCheck_nm() + " -> " + updateVo.getCheck_nm() + "<br>");
		log_content.append("������ : " + beforeUpdateVo.getMain_oper_nm() + " -> " + updateVo.getMain_oper_nm() + "<br>");
		log_content.append("���� ���� �Ϸ��� : " + originDate + "<br>");
		log_content.append("������ ���� �Ϸ��� : " + updateDate + "<br>");
		if(updateVo.getCon_desc() != null) log_content.append("���� : " + MakeModifyReason.getWithoutRollbackData(updateVo.getCon_desc()) + "<br>");
		
		conn = new RegchedbConnectionManager().getConnection();;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, job_id);
			pstmt.setString(2, updateVo.getCon_id());
			pstmt.setString(3, updateVo.getCon_year());
			pstmt.setString(4, log_content.toString());
			
			pstmt.execute();
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	@Override
	public void rollbackCompletedPlan(MainConSubVo vo, String originDate) {
		String job_id = "RCP";
		StringBuffer log_content = new StringBuffer();
		String sql = "INSERT INTO logging_tbl (job_id, con_id, con_year, log_date, log_content) values (?, ?, ?, sysdatetime, ?)";
		
		log_content.append("INFO OF ROLLBACK COMPLETED PLAN<br>");
		log_content.append("con_id : " + vo.getCon_id() + "<br>");
		log_content.append("con_year : " + vo.getCon_year() + "<br>");
		log_content.append("���� : " + vo.getCust_nm() + "<br>");
		log_content.append("������Ʈ : " + vo.getProc_nm() + "<br>");
		log_content.append("����� ���� ������ : " + originDate + "<br>");
		if(vo.getCon_desc() != null) log_content.append("���� : " + vo.getCon_desc() + "<br>");
		
		conn = new RegchedbConnectionManager().getConnection();;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, job_id);
			pstmt.setString(2, vo.getCon_id());
			pstmt.setString(3, vo.getCon_year());
			pstmt.setString(4, log_content.toString());
			
			pstmt.execute();
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}

	@Override
	public void deleteAPlan(MainConSubVo vo, String originDate) {
		String job_id = "DAP";
		StringBuffer log_content = new StringBuffer();
		String sql = "INSERT INTO logging_tbl (job_id, con_id, con_year, log_date, log_content) values (?, ?, ?, sysdatetime, ?)";
		
		log_content.append("INFO OF DELETE A PLAN<br>");
		log_content.append("con_id : " + vo.getCon_id() + "<br>");
		log_content.append("con_year : " + vo.getCon_year() + "<br>");
		log_content.append("���� : " + vo.getCust_nm() + "<br>");
		log_content.append("������Ʈ : " + vo.getProc_nm() + "<br>");
		log_content.append("���� ���� ��¥ : " + originDate + "<br>PLAN DELETED<br>");
		
		conn = new RegchedbConnectionManager().getConnection();;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, job_id);
			pstmt.setString(2, vo.getCon_id());
			pstmt.setString(3, vo.getCon_year());
			pstmt.setString(4, log_content.toString());
			
			pstmt.execute();
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}

	@Override
	public void deleteSelectedSite(MainConSubVo vo) {
		String job_id = "DSS";
		StringBuffer log_content = new StringBuffer();
		String sql = "INSERT INTO logging_tbl (job_id, con_id, con_year, log_date, log_content) values (?, ?, ?, sysdatetime, ?)";
		
		log_content.append("INFO OF DELETE SELECTED SITE<br>");
		log_content.append("con_id : " + vo.getCon_id() + "<br>");
		log_content.append("con_year : " + vo.getCon_year() + "<br>");
		log_content.append("���� : " + vo.getCust_nm() + "<br>");
		log_content.append("������Ʈ : " + vo.getProc_nm() + "<br>DELETED SITE<br>");
		
		conn = new RegchedbConnectionManager().getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, job_id);
			pstmt.setString(2, vo.getCon_id());
			pstmt.setString(3, vo.getCon_year());
			pstmt.setString(4, log_content.toString());
			
			pstmt.execute();
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	@Override
	public void insertNewYear(String year) {
		String job_id = "INY";
		StringBuffer log_content = new StringBuffer();
		String sql = "INSERT INTO logging_tbl (job_id, con_id, con_year, log_date, log_content) values (?, 'CUBRID-YEAR', ?, sysdatetime, ?)";
		
		log_content.append("INSERT NEW YEAR VALUE : " + year + "<br>");
		
		conn = new RegchedbConnectionManager().getConnection();;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, job_id);
			pstmt.setString(2, year);
			pstmt.setString(3, log_content.toString());
			
			if(pstmt.executeUpdate() > 0) conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	@Override
	public void insertFromJira(List<MainConSubVo> insertList) {
		String job_id = "IFJ";
		StringBuffer log_content = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO logging_tbl (job_id, con_id, con_year, log_date, log_content) values ");
		String appendSql = "(?, ?, ?, sysdatetime, ?)";
		
		// insert ���� ����� (MySQL ��Ÿ�� : insert into table values (...), (...), ..., (...) )
		for(int i = 0; i < insertList.size(); i++) {
			sql.append(appendSql + ",");
		}
		sql.deleteCharAt(sql.length() - 1);
		
		conn = new RegchedbConnectionManager().getConnection();;
		
		try {
			pstmt = conn.prepareStatement(sql.toString());
			int index = 1;
			// query�� �� ���������� ���ε�
			for(MainConSubVo vo : insertList) {
				// log_content �����
				log_content.setLength(0);
				log_content.append("INFO OF INSERTED SITE FROM JIRA<br>");
				log_content.append("con_id : " + vo.getCon_id() + "<br>");
				log_content.append("con_year : " + vo.getCon_year() + "<br>");
				log_content.append("���Ⱓ : " + vo.getCon_from_date() + " ~ " + vo.getCon_to_date() + "<br>");
				log_content.append("���� : " + vo.getCust_nm() + "<br>");
				log_content.append("����� : " + vo.getProc_nm() + "<br>");
				log_content.append("���� ��� : " + vo.getCheck_nm() + "<br>");
				log_content.append("�����(��) : " + vo.getMain_oper_nm() + " �����(��) : " + vo.getSub_oper_nm() + "<br>");
				
				// ���ε�
				pstmt.setString(index++, job_id);
				pstmt.setString(index++, vo.getCon_id());
				pstmt.setString(index++, vo.getCon_year());
				pstmt.setString(index++, log_content.toString());
			}
			pstmt.execute();
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}

	@Override
	public void updateFromJira(Map<String, List<MainConSubVo>> map) {
		String job_id = "UFJ";
		StringBuffer log_content = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO logging_tbl (job_id, con_id, con_year, log_date, log_content) values ");
		String appendSql = "(?, ?, ?, sysdatetime, ?)";
		List<MainConSubVo> updateList = map.get("updateList");
		List<MainConSubVo> beforeUpdateList = map.get("beforeUpdateList");
		
		// insert ���� ����� (MySQL ��Ÿ�� : insert into table values (...), (...), ..., (...) )
		for(int i = 0; i < updateList.size(); i++) {
			sql.append(appendSql + ",");
		}
		sql.deleteCharAt(sql.length() - 1);
		
		conn = new RegchedbConnectionManager().getConnection();;
		
		try {
			pstmt = conn.prepareStatement(sql.toString());
			int index = 1;
			// query�� �� ���������� ���ε�
			for(int i = 0; i < updateList.size(); i++) {
				MainConSubVo updateVo = updateList.get(i);
				MainConSubVo beforeUpdateVo = beforeUpdateList.get(i);
				
				log_content.setLength(0);
				log_content.append("INFO OF UPDATED SITE FROM JIRA<br>");
				log_content.append("con_id : " + updateVo.getCon_id() + "<br>");
				log_content.append("con_year : " + updateVo.getCon_year() + "<br>");
				log_content.append("���Ⱓ : " + beforeUpdateVo.getCon_from_date() + " ~ " + beforeUpdateVo.getCon_to_date() + " -> " + updateVo.getCon_from_date() + " ~ " + updateVo.getCon_to_date() + "<br>");
				log_content.append("���� : " + beforeUpdateVo.getCust_nm() + " -> " + updateVo.getCust_nm() + "<br>");
				log_content.append("����� : " + beforeUpdateVo.getProc_nm() + " -> " + updateVo.getProc_nm() + "<br>");
				log_content.append("���� ��� : " + beforeUpdateVo.getCheck_nm() + " -> " + updateVo.getCheck_nm() + "<br>");
				log_content.append("�����(��) : " + beforeUpdateVo.getMain_oper_nm() + " -> " + updateVo.getMain_oper_nm() + "<br>");
				log_content.append("�����(��) : " + beforeUpdateVo.getSub_oper_nm() + " -> " + updateVo.getSub_oper_nm() + "<br>");
				
				// ���ε�
				pstmt.setString(index++, job_id);
				pstmt.setString(index++, updateVo.getCon_id());
				pstmt.setString(index++, updateVo.getCon_year());
				pstmt.setString(index++, log_content.toString());
				
			}
			pstmt.execute();
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}

	@Override
	public void close() {
		try {
			if(pstmt != null) pstmt.close();
			if(rs != null) pstmt.close();
			if(conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public MainConSubVo beforeUpdatedVo(String con_id, String con_year) {
		MainConSubVo vo = new MainConSubVo();
		String sql = "SELECT con_id, con_year, con_from_date, con_to_date, cust_nm, proc_nm, check_nm, main_oper_nm, sub_oper_nm FROM main_con WHERE con_id=? AND con_year=?";
		
		conn = new RegchedbConnectionManager().getConnection();;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, con_id);
			pstmt.setString(2, con_year);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setCon_id(rs.getString(1));
				vo.setCon_year(rs.getString(2));
				vo.setCon_from_date(rs.getString(3));
				vo.setCon_to_date(rs.getString(4));
				vo.setCust_nm(rs.getString(5));
				vo.setProc_nm(rs.getString(6));
				vo.setCheck_nm(rs.getString(7));
				vo.setMain_oper_nm(rs.getString(8));
				vo.setSub_oper_nm(rs.getString(9));
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return vo;
	}

	@Override
	public void addCubMember(List<CubMemberVo> memberList) {
		String job_id = "ACM";
		int recordCount = memberList.size();
		StringBuffer log_content = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO logging_tbl (job_id, con_id, con_year, log_date, log_content) values (?, ?, year(sysdate), sysdatetime, ?)");
		log_content.append("INFO OF ADD CUBRID MEMBER<br>");
		log_content.append(recordCount + "�� ����<br>");
		log_content.append("�߰��� �ο� ���<br>");
		for(CubMemberVo vo : memberList) {
			log_content.append(vo.getCub_name() + "<br>");
		}
		
		conn = new RegchedbConnectionManager().getConnection();;
		
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, job_id);
			pstmt.setString(2, "CUBRID-MEMBER");
			pstmt.setString(3, log_content.toString());
			
			pstmt.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}

	@Override
	public List<LoggingVo> selectAll(String year, String condition, String arg) {
		List<LoggingVo> loggingList = new ArrayList<LoggingVo>();
		conn = new RegchedbConnectionManager().getConnection();;
		
		String sql = "SELECT A.log_seq, A.job_id, A.con_id, A.con_year, A.log_date, A.log_content, NVL(B.cust_nm, ''), NVL(B.proc_nm, '') "
				+ "FROM logging_tbl A LEFT OUTER JOIN main_con B ON A.con_id = B.con_id AND A.con_year = B.con_year "
				+ "WHERE A.con_year=? ";
		System.out.println("condition : " + condition + "\targ : " + arg);
		try {
			if(condition != null) {
				if(condition.toLowerCase().equals("conid")) {
					sql += "AND A.con_id = ? ORDER BY A.log_date";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, year);
					pstmt.setString(2, arg);
				} else if(condition.toLowerCase().equals("cust")) {
					sql += "AND B.cust_nm = ? ORDER BY A.log_date";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, year);
					pstmt.setString(2, arg);
				} else if(condition.toLowerCase().equals("proc")) {
					sql += "AND B.proc_nm = ? ORDER BY A.log_date";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, year);
					pstmt.setString(2, arg);
				}
			} else {
				sql += "ORDER BY A.log_date";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, year);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				LoggingVo vo = new LoggingVo();
				vo.setLog_seq(rs.getInt(1));
				vo.setJob_id(rs.getString(2));
				vo.setCon_id(rs.getString(3));
				vo.setCon_year(rs.getString(4));
				vo.setLog_date(rs.getString(5));
				vo.setLog_content(rs.getString(6));
				vo.setCust_nm(rs.getString(7));
				vo.setProc_nm(rs.getString(8));
				loggingList.add(vo);
			}
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return loggingList;
	}

	@Override
	public void updateCubMember(List<CubMemberVo> vo) {
		// TODO Auto-generated method stub
		
	}

}
