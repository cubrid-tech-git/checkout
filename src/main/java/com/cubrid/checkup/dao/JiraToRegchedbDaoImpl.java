package com.cubrid.checkup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cubrid.checkup.service.CheckUpServiceImpl;
import com.cubrid.checkup.vo.CubMemberVo;
import com.cubrid.checkup.vo.MainConSubVo;
import com.cubrid.logging.CheckupLogging;
import com.cubrid.logging.CheckuploggingImpl;
import com.cubrid.util.date.MyDate;
import com.cubrid.util.replace.ReplaceData;

public class JiraToRegchedbDaoImpl implements JiraToRegchedbDao {
	private Connection jiraConn;
	private Connection regcheConn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	@Override
	public Map<String, Integer> getCustomFromJira(List<MainConSubVo> list) {
		System.out.println("[DEBUG] getCustomFromJira Call - list size : " + list.size() );
		for (MainConSubVo vo : list) {
			System.out.println(vo.getCust_nm() + " " + vo.getClose_yn());
		}
		
		// 만약, from-to 가 null 일 경우 default로 넣을 값 생성
		regcheConn = new RegchedbConnectionManager().getConnection();
		List<MainConSubVo> insertList = new ArrayList<MainConSubVo>();
		List<MainConSubVo> updateList = new ArrayList<MainConSubVo>();
		List<MainConSubVo> beforeUpdateList = new ArrayList<MainConSubVo>();
		CheckupLogging logging = new CheckuploggingImpl();
		Map<String, List<MainConSubVo>> updatedMap = new HashMap<String, List<MainConSubVo>>();

		System.out.println("getCustomFromJira page ");
		Map<String, Integer> map = new HashMap<String, Integer>();
		int successCount = 0;	// 성공한 갯수 카운트 변수
		int failCount = 0;	// 실패한 갯수 카운트 변수

		for(MainConSubVo vo : list) {
			System.out.println(vo.getCon_id());
			try {
				successCount += this.insertData(vo);
				System.out.println(vo.getCon_id() + " inserted.");
				insertList.add(vo);
			} catch (SQLException e) {
				// e.getErrorCode() 가 primary key 중복일 경우
				// update 수행
				if(e.getErrorCode() == -670) {
					try {
						MainConSubVo beforeUpdateVo = logging.beforeUpdatedVo(vo.getCon_id(), vo.getCon_year());
						successCount += this.updateData(vo);
						System.out.println(vo.getCon_id() + " updated.");
						updateList.add(vo);
						beforeUpdateList.add(beforeUpdateVo);
					} catch (SQLException e1) {
						failCount++;
						continue;
					}
				} else {
					e.printStackTrace();
				}
			} 
		}
		// jsp 페이지로 가져갈 성공 / 실패 카운트 갯수 저장
		map.put("success", successCount);
		map.put("fail", failCount);
		
		CheckUpServiceImpl service = new CheckUpServiceImpl();
		System.out.println(service.insertYear());
		updatedMap.put("updateList", updateList);
		updatedMap.put("beforeUpdateList", beforeUpdateList);
		// logging to DB
		if(insertList.size() > 0) {
			logging.insertFromJira(insertList);
		}
		
		if(updateList.size() > 0) {
			logging.updateFromJira(updatedMap);
		}

		try {
			if(pstmt != null) pstmt.close();
			if(regcheConn != null) regcheConn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return map;
	}

	@Override
	public int insertData(MainConSubVo vo) throws SQLException {
		String fromDate = MyDate.currentDate();
		String toDate = MyDate.nextYearDate();
		int result = 0;

		String curYear = Calendar.getInstance().get(Calendar.YEAR) + "";

		if(vo.getCon_from_date() == null) vo.setCon_from_date(fromDate);
		if(vo.getCon_to_date() == null) vo.setCon_to_date(toDate);

		// String sql = "insert into main_con(con_id, con_year, con_from_date, con_to_date, cust_nm, proc_nm, main_oper_nm, sub_oper_nm, reg_date, check_nm) values(?, ?, str_to_date(?, '%Y-%m-%d'), str_to_date(?, '%Y-%m-%d'), ?, ?, ?, ?, sysdate, ?)";
		String sql = "insert into main_con(con_id, con_year, con_from_date, con_to_date, cust_nm, proc_nm, main_oper_nm, sub_oper_nm, reg_date, check_nm, close_yn) values(?, ?, str_to_date(?, '%Y-%m-%d'), str_to_date(?, '%Y-%m-%d'), ?, ?, ?, ?, sysdate, ?, ?)";
		
		// regchedb에 넣기
		pstmt = regcheConn.prepareStatement(sql);

		pstmt.setString(1, vo.getCon_id());
		pstmt.setString(2, curYear);
		pstmt.setString(3, vo.getCon_from_date());
		pstmt.setString(4, vo.getCon_to_date());
		pstmt.setString(5, vo.getCust_nm());
		pstmt.setString(6, vo.getProc_nm());
		pstmt.setString(7, vo.getMain_oper_nm());
		pstmt.setString(8, vo.getSub_oper_nm());
		pstmt.setString(9, vo.getCheck_nm());
		pstmt.setString(10, vo.getClose_yn()); /* ADD */

		System.out.println("[DEBUG] INSERT about close_yn");
		result = pstmt.executeUpdate();

		if(result == 1) regcheConn.commit();

		return result;
	}

	@Override
	public int updateData(MainConSubVo vo) throws SQLException {
		// TODO Auto-generated method stub
		String fromDate = MyDate.currentDate();
		String toDate = MyDate.nextYearDate();
		int result = 0;

		String curYear = Calendar.getInstance().get(Calendar.YEAR) + "";

		if(vo.getCon_from_date() == null) vo.setCon_from_date(fromDate);
		if(vo.getCon_to_date() == null) vo.setCon_to_date(toDate);

		// String sql = "update main_con set con_from_date=str_to_date(?, '%Y-%m-%d'), con_to_date=str_to_date(?, '%Y-%m-%d'), cust_nm=?, proc_nm=?, main_oper_nm=?, sub_oper_nm=?, reg_date=systimestamp, check_nm=? where con_id=? and con_year=?";
		String sql = "update main_con set con_from_date=str_to_date(?, '%Y-%m-%d'), con_to_date=str_to_date(?, '%Y-%m-%d'), cust_nm=?, proc_nm=?, main_oper_nm=?, sub_oper_nm=?, reg_date=systimestamp, check_nm=?, close_yn=? where con_id=? and con_year=?";
		// String sql = "update main_con set con_from_date=str_to_date(?, '%Y-%m-%d'), con_to_date=str_to_date(?, '%Y-%m-%d'), cust_nm=?, proc_nm=?, main_oper_nm=?, sub_oper_nm=?, reg_date=systimestamp, check_nm=?, close_yn=? where con_id=?";
		
		// regchedb에 넣기
		pstmt = regcheConn.prepareStatement(sql);

		pstmt.setString(1, vo.getCon_from_date());
		pstmt.setString(2, vo.getCon_to_date());
		pstmt.setString(3, vo.getCust_nm());
		pstmt.setString(4, vo.getProc_nm());
		pstmt.setString(5, vo.getMain_oper_nm());
		pstmt.setString(6, vo.getSub_oper_nm());
		pstmt.setString(7, vo.getCheck_nm());
		pstmt.setString(8, vo.getClose_yn()); /* ADD */
		pstmt.setString(9, vo.getCon_id());
		pstmt.setString(10, curYear);

		System.out.println("[DEBUG] UPDATE about close_yn");
		result = pstmt.executeUpdate();
		
		// if(result == 1) regcheConn.commit();
		regcheConn.commit();

		return result;
	}

	@Override
	public List<MainConSubVo> getUpdatedData() {
		// TODO Auto-generated method stub
		List<MainConSubVo> list = new ArrayList<MainConSubVo>();
		jiraConn = new JiraConnectionManager().getConnection();
		regcheConn = new RegchedbConnectionManager().getConnection();;
		String lastTime = null;
		
		String curYear = Calendar.getInstance().get(Calendar.YEAR) + "";

		// 가장 마지막에 저장된 access time을 DB에서 가져옴
		String selectMainConSql = "select last_time from get_update_tbl where from_name='J' and con_year=?";

		try {
			pstmt = regcheConn.prepareStatement(selectMainConSql);
			pstmt.setString(1, curYear);
			rs = pstmt.executeQuery();
			if(rs.next()) lastTime = rs.getString(1);
			else {
				String insertSql = "insert into get_update_tbl values(?, ?, sysdatetime)";
				pstmt = regcheConn.prepareStatement(insertSql);
				pstmt.setString(1, "J");
				pstmt.setString(2, curYear);
				int result = pstmt.executeUpdate();

				if(result == 1) {
					regcheConn.commit();
					pstmt.close();
				} else {
					regcheConn.rollback();
					pstmt.close();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		System.out.println("last access time : " + lastTime);

		try {
			// JIRA 에서 프로젝트 정보 가져오기
			// String sql = "select A.id, A.pkey, A.sequence, A.cust, A.proc, B.from_date, B.to_date, C.main_oper, C.sub_oper, Z.check_nm, A.updated from (select i.updated, i.id, i.pkey, i.issuestatus, v.parentkey, v.stringvalue, o.customvalue cust, u.customvalue proc, u.sequence from jiraissue i join customfieldvalue v on i.id=v.issue join customfieldoption o on v.parentkey=o.id join customfieldoption u on v.stringvalue=u.id where v.customfield=10015 and i.issuetype=6 and pkey like 'CUBRID%') A join (select a.id, a.issue, a.datevalue from_date, b.datevalue to_date from customfieldvalue a join customfieldvalue b on a.issue=b.issue where a.customfield=10106 and b.customfield=10107) B on A.id=B.issue left outer join (select F.issue, F.main_oper, Q.sub_oper from (select v.issue, o.customvalue main_oper from customfieldvalue v join customfieldoption o on v.stringvalue=o.id where v.customfield=10003) F left outer join (select v.issue, o.customvalue sub_oper from customfieldvalue v join customfieldoption o on v.stringvalue=o.id where v.customfield=10004) Q on F.issue=Q.issue) C on A.id=C.issue join (select i.pkey, v.issue, o.customvalue as check_nm from jiraissue i join customfieldvalue v on i.id=v.issue left outer join customfieldoption o on v.stringvalue=o.id where v.customfield=10109) Z on A.id=Z.issue join (select i.id, i.pkey, o.customvalue from jiraissue i join customfieldvalue v on i.id = v.issue join customfieldoption o on v.stringvalue = o.id where o.id = 10803) K on A.id = K.id";
			String sql = "select A.id, A.pkey, A.sequence, A.cust, A.proc, B.from_date, B.to_date, C.main_oper, C.sub_oper, Z.check_nm, A.updated, A.issuestatus from (select i.updated, i.id, i.pkey, i.issuestatus, v.parentkey, v.stringvalue, o.customvalue cust, u.customvalue proc, u.sequence from jiraissue i join customfieldvalue v on i.id=v.issue join customfieldoption o on v.parentkey=o.id join customfieldoption u on v.stringvalue=u.id where v.customfield=10015 and i.issuetype=6 and pkey like 'CUBRID%') A join (select a.id, a.issue, a.datevalue from_date, b.datevalue to_date from customfieldvalue a join customfieldvalue b on a.issue=b.issue where a.customfield=10106 and b.customfield=10107) B on A.id=B.issue left outer join (select F.issue, F.main_oper, Q.sub_oper from (select v.issue, o.customvalue main_oper from customfieldvalue v join customfieldoption o on v.stringvalue=o.id where v.customfield=10003) F left outer join (select v.issue, o.customvalue sub_oper from customfieldvalue v join customfieldoption o on v.stringvalue=o.id where v.customfield=10004) Q on F.issue=Q.issue) C on A.id=C.issue join (select i.pkey, v.issue, o.customvalue as check_nm from jiraissue i join customfieldvalue v on i.id=v.issue left outer join customfieldoption o on v.stringvalue=o.id where v.customfield=10109) Z on A.id=Z.issue join (select i.id, i.pkey, o.customvalue from jiraissue i join customfieldvalue v on i.id = v.issue join customfieldoption o on v.stringvalue = o.id where o.id = 10803) K on A.id = K.id";
			if(lastTime != null) {
				// 수정 날짜와 last access time을 비교하여 최근 수정된 애들을 가져옴
				sql += " where date_format(A.updated, '%y-%m-%d %H:%i:%s') > date_format(?, '%y-%m-%d %H:%i:%s') order by A.updated";
				pstmt = jiraConn.prepareStatement(sql);
				pstmt.setString(1, lastTime);
				System.out.println("[DEBUG] SELECT about close_yn");
				rs = pstmt.executeQuery();

				while(rs.next()) {
					MainConSubVo vo = new MainConSubVo();
					vo.setCon_id(rs.getString(2));
					vo.setCon_seq(rs.getInt(3));
					vo.setCon_year(curYear);
					vo.setCust_nm(rs.getString(4));
					vo.setProc_nm(rs.getString(5));
					vo.setCon_from_date(rs.getString(6));
					vo.setCon_to_date(rs.getString(7));
					vo.setMain_oper_nm(rs.getString(8));
					vo.setSub_oper_nm(rs.getString(9));
					vo.setCheck_nm(ReplaceData.replaceCondition(rs.getString(10)));
					vo.setUpd_date(rs.getString(11));
					
					
					String close_yn = rs.getString(12);
					if ("6".equals(close_yn)) { 
						vo.setClose_yn("Y");
					} else {
						vo.setClose_yn("N");
					}
					

					list.add(vo);
				}

				// 그게 아니면 전부 다 가져오기
			} else {
				sql += " order by A.updated desc";
				pstmt = jiraConn.prepareStatement(sql);
				System.out.println("[DEBUG] SELECT-ELSE about close_yn");
				rs = pstmt.executeQuery();

				while(rs.next()) {
					MainConSubVo vo = new MainConSubVo();
					vo.setCon_id(rs.getString(2));
					vo.setCon_seq(rs.getInt(3));
					vo.setCon_year(curYear);
					vo.setCust_nm(rs.getString(4));
					vo.setProc_nm(rs.getString(5));
					vo.setCon_from_date(rs.getString(6));
					vo.setCon_to_date(rs.getString(7));
					vo.setMain_oper_nm(rs.getString(8));
					vo.setSub_oper_nm(rs.getString(9));
					vo.setCheck_nm(ReplaceData.replaceCondition(rs.getString(10)));
					vo.setUpd_date(rs.getString(11));
					
					String close_yn = rs.getString(12);
					if ("6".equals(close_yn)) { 
						vo.setClose_yn("Y");
					} else {
						vo.setClose_yn("N");
					}

					list.add(vo);
				}
			}

			// 제대로 들어가는지 확인하는 loop
			//			for(MainConSubVo a : list) {
			//				System.out.println(a.getCon_id() + "\t" + a.getCon_seq() + "\t" + a.getCust_nm() + "\t" + a.getProc_nm() + "\t" + a.getCon_from_date() + "\t");
			//				System.out.println(a.getCon_to_date() + "\t" + a.getMain_oper_nm() + "\t" + a.getSub_oper_nm() + "\t" + a.getCheck_nm() + "\t" + a.getUpd_date());
			//			}

			String curDateUpdateSql = "update get_update_tbl set last_time=sysdatetime where from_name=? and con_year=?";
			pstmt = regcheConn.prepareStatement(curDateUpdateSql);
			pstmt.setString(1, "J");
			pstmt.setString(2, curYear);
			int result = pstmt.executeUpdate();

			if(result == 1) {
				regcheConn.commit();
				regcheConn.close();
			} else {
				regcheConn.rollback();
				regcheConn.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(jiraConn != null) jiraConn.close();
				if(regcheConn != null) regcheConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	@Override
	public void insertOrUpdateFromJiraMember() {
		jiraConn = new JiraConnectionManager().getConnection();
		regcheConn = new RegchedbConnectionManager().getConnection();;
		String jiraSql = "SELECT user_name, display_name, email_address FROM cwd_user";
		String insertSql = "INSERT INTO cub_member(jira_id,cub_name,email_addr,reg_date,show_yn) VALUES(?, ?, ?, sysdatetime,?)";
		
		// logging 객체
		List<CubMemberVo> memberList = new ArrayList<CubMemberVo>();
		
		System.out.println("** INSERT OR UPDATE CUB_MEMBER ** ");
		
		try {
			pstmt = jiraConn.prepareStatement(jiraSql);
			rs = pstmt.executeQuery();
			pstmt = regcheConn.prepareStatement(insertSql);
			while(rs.next()) {
				try {
					pstmt.setString(1, rs.getString(1));
					pstmt.setString(2, rs.getString(2));
					pstmt.setString(3, rs.getString(3));
					pstmt.setString(4, "Y");
					pstmt.execute();
					CubMemberVo vo = new CubMemberVo();
					vo.setJira_id(rs.getString(1));
					vo.setCub_name(rs.getString(2));
					vo.setEmail_addr(rs.getString(3));
					memberList.add(vo);
					System.out.println(rs.getString(2) + " 님이 저장되었습니다.");
				} catch(SQLException ee) {
					if(ee.getErrorCode() == -670) {
						System.out.println(rs.getString(2) + "님은 저장되어 있습니다.");
						continue;
					}
				}
			}
			regcheConn.commit();
			// logging 수행
			if(memberList.size() != 0) {
				CheckupLogging logging = new CheckuploggingImpl();
				logging.addCubMember(memberList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
				if(jiraConn != null) jiraConn.close();
				if(regcheConn != null) regcheConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
