package com.cubrid.checkup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cubrid.checkup.vo.CubMemberVo;
import com.cubrid.checkup.vo.CustRegSvcVo;
import com.cubrid.checkup.vo.JobOpVo;
import com.cubrid.checkup.vo.MainConSubVo;
import com.cubrid.checkup.vo.MainConVo;
import com.cubrid.checkup.vo.MonthInfoVo;
import com.cubrid.logging.CheckupLogging;
import com.cubrid.logging.CheckuploggingImpl;
import com.cubrid.util.count.CheckupCount;
import com.cubrid.util.replace.MakeModifyReason;
import com.cubrid.util.replace.ReplaceData;

/**
 * 
 * @author HUN
 * 
 * CheckupDao interface �� ������ Ŭ����
 *
 */
public class CheckupDaoImpl implements CheckupDao {
//	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private List<CustRegSvcVo> custRegSvcList;
	private List<JobOpVo> jobOpList;
	private List<MainConVo> mainConList;
	private List<MainConSubVo> mainConSubList;

	@Override
	// @ TODO recordCount
	public int recordCount(int year, String name) {
		int result = 0;
		ResultSet countRs = null;
		Connection selectConn = new RegchedbConnectionManager().getConnection();;
		String sql = "SELECT COUNT(*) FROM main_con a LEFT OUTER JOIN cust_reg_svc b "
				+ "ON a.con_id = b.con_id AND a.con_year = b.con_year "
				+ "WHERE a.con_year='" + year + "' AND a.close_yn = 'N'";
		if(name != null) sql += " and a.main_oper_nm='" + idFromJira(name) + "'";
		try {
			System.out.println("** record Count : year : " + year + " **");
			pstmt = selectConn.prepareStatement(sql);
			countRs = pstmt.executeQuery(sql);

			if(countRs.next()) result = countRs.getInt(1);
			
			selectConn.commit();
			
			System.out.println("recordCount conn : " + selectConn);
			System.out.println("recordCount rs : " + countRs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(countRs != null) countRs.close();
				if(stmt != null) stmt.close();
				if(selectConn != null) selectConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// TODO selectResult
	@Override
	public Map<String, Object> selectResult(int year, String name) {
		System.out.println(" ** CheckupDaoImpl.selectResult **");
		System.out.println("name : " + name);
		ResultSet rs = null;
		Connection conn = new RegchedbConnectionManager().getConnection();;
		Map<String, Object> map = new HashMap<String, Object>();
		mainConSubList = new ArrayList<MainConSubVo>();
		custRegSvcList = new ArrayList<CustRegSvcVo>();
		CheckupCount count = new CheckupCount();
		int projectJobCountY = 0;	// �ش� ������Ʈ���� �Ϸ�� �������� ī��Ʈ
		int jobCountY_1 = 0;	// 1���� �Ϸ�� �������� ���� ī��Ʈ
		int jobCountY_2 = 0;
		int jobCountY_3 = 0;
		int jobCountY_4 = 0;
		int jobCountY_5 = 0;
		int jobCountY_6 = 0;
		int jobCountY_7 = 0;
		int jobCountY_8 = 0;
		int jobCountY_9 = 0;
		int jobCountY_10 = 0;
		int jobCountY_11 = 0;
		int jobCountY_12 = 0;
		int[] visit = new int[12];
		int[] remote = new int[12];
		boolean visitCheck = false;
		boolean remoteCheck = false;

		// visit �� remote ���� 0���� �ʱ�ȭ
		for(int i = 0; i < visit.length; i++) visit[i] = 0;
		for(int i = 0; i < remote.length; i++) remote[i] = 0;

		String sql = "SELECT a.con_id AS con_id, "
				+ "a.con_year AS con_year, "
				+ "CAST(a.con_from_date AS varchar) AS con_from_date, "
				+ "CAST(a.con_to_date AS varchar) AS con_to_date, "
				+ "a.cust_nm, a.proc_nm, a.check_nm, a.main_oper_nm, "
				+ "CAST(a.upd_date AS varchar) AS upd_date, "
				+ "NVL(b.visit_cnt_1, 0) AS visit_cnt_1, "
				+ "NVL(b.remote_cnt_1, 0) AS remote_cnt_1, "
				+ "NVL(b.job_nm_1, '') AS job_nm_1, "
				+ "b.job_yn_1 AS job_yn_1, "
				+ "TO_CHAR(b.job_date_1, 'YYYYMMDD') AS job_date_1, "
				+ "NVL(b.visit_cnt_2, 0) AS visit_cnt_2, "
				+ "NVL(b.remote_cnt_2, 0) AS remote_cnt_2, "
				+ "NVL(b.job_nm_2, '') AS job_nm_2, "
				+ "b.job_yn_2 AS job_yn_2, "
				+ "TO_CHAR(b.job_date_2, 'YYYYMMDD') AS job_date_2, "
				+ "NVL(b.visit_cnt_3, 0) AS visit_cnt_3, "
				+ "NVL(b.remote_cnt_3, 0) AS remote_cnt_3, "
				+ "NVL(b.job_nm_3, '') AS job_nm_3, "
				+ "b.job_yn_3 AS job_yn_3, "
				+ "TO_CHAR(b.job_date_3, 'YYYYMMDD') job_date_3, "
				+ "NVL(b.visit_cnt_4, 0) AS visit_cnt_4, "
				+ "NVL(b.remote_cnt_4, 0) AS remote_cnt_4, "
				+ "NVL(b.job_nm_4, '') AS job_nm_4, "
				+ "b.job_yn_4 AS job_yn_4, "
				+ "TO_CHAR(b.job_date_4, 'YYYYMMDD') job_date_4, "
				+ "NVL(b.visit_cnt_5, 0) AS visit_cnt_5, "
				+ "NVL(b.remote_cnt_5, 0) AS remote_cnt_5, "
				+ "NVL(b.job_nm_5, '') AS job_nm_5, "
				+ "b.job_yn_5 AS job_yn_5, "
				+ "TO_CHAR(b.job_date_5, 'YYYYMMDD') AS job_date_5, "
				+ "NVL(b.visit_cnt_6, 0) AS visit_cnt_6, "
				+ "NVL(b.remote_cnt_6, 0) AS remote_cnt_6, "
				+ "NVL(b.job_nm_6, '') AS job_nm_6, "
				+ "b.job_yn_6 AS job_yn_6, "
				+ "TO_CHAR(b.job_date_6, 'YYYYMMDD') AS job_date_6,"
				+ "NVL(b.visit_cnt_7, 0) AS visit_cnt_7, "
				+ "NVL(b.remote_cnt_7, 0) AS remote_cnt_7, "
				+ "NVL(b.job_nm_7, '') AS job_nm_7, "
				+ "b.job_yn_7 AS job_yn_7, "
				+ "TO_CHAR(b.job_date_7, 'YYYYMMDD') AS job_date_7, "
				+ "NVL(b.visit_cnt_8, 0) AS visit_cnt_8, "
				+ "NVL(b.remote_cnt_8, 0) AS remote_cnt_8, "
				+ "NVL(b.job_nm_8, '') AS job_nm_8, "
				+ "b.job_yn_8 AS job_yn_8, "
				+ "TO_CHAR(b.job_date_8, 'YYYYMMDD') AS job_date_8, "
				+ "NVL(b.visit_cnt_9, 0) AS visit_cnt_9, "
				+ "NVL(b.remote_cnt_9, 0) AS remote_cnt_9, "
				+ "NVL(b.job_nm_9, '') AS job_nm_9, "
				+ "b.job_yn_9 AS job_yn_9, "
				+ "TO_CHAR(b.job_date_9, 'YYYYMMDD') AS job_date_9, "
				+ "NVL(b.visit_cnt_10, 0) AS visit_cnt_10, "
				+ "NVL(b.remote_cnt_10, 0) AS remote_cnt_10, "
				+ "NVL(b.job_nm_10, '') AS job_nm_10, "
				+ "b.job_yn_10 AS job_yn_10, "
				+ "TO_CHAR(b.job_date_10, 'YYYYMMDD') AS job_date_10, "
				+ "NVL(b.visit_cnt_11, 0) AS visit_cnt_11, "
				+ "NVL(b.remote_cnt_11, 0) AS remote_cnt_11, "
				+ "NVL(b.job_nm_11, '') AS job_nm_11, "
				+ "b.job_yn_11 AS job_yn_11, "
				+ "TO_CHAR(b.job_date_11, 'YYYYMMDD') AS job_date_11, "
				+ "NVL(b.visit_cnt_12, 0) AS visit_cnt_12, "
				+ "NVL(b.remote_cnt_12, 0) AS remote_cnt_12, "
				+ "NVL(b.job_nm_12, '') AS job_nm_12, "
				+ "b.job_yn_12 AS job_yn_12 , "
				+ "TO_CHAR(b.job_date_12, 'YYYYMMDD') AS job_date_12 "
				+ "FROM main_con a LEFT OUTER JOIN cust_reg_svc b "
				+ "ON a.con_id = b.con_id "
				+ "AND a.con_year = b.con_year "
				// + "WHERE a.con_year = ? ";
				+ "WHERE a.con_year = ? AND a.close_yn = 'N'";
		if(name != null && !"default".equals(name)) {
			if ("������� 1��".equals(idFromJira(name))) {
				sql += " and a.main_oper_nm IN ('����ȣ', '��â��', '������', '�輺��', '������', 'Ȳ����')";
				sql += " ORDER BY a.main_oper_nm;";
			} else if ("������� 2��".equals(idFromJira(name))) {
				sql += " and a.main_oper_nm IN ('��ȣ��', '������', '�ڵ���', '�ֿ���', '������', '������')";
				sql += " ORDER BY a.main_oper_nm;";
			} else if ("�����繫��".equals(idFromJira(name))) {
				sql += " and a.main_oper_nm IN ('�����', '���ֿ�', '���ؼ�', '�㼭��')";
				sql += " ORDER BY a.main_oper_nm;";
			} else {
				sql += " and a.main_oper_nm='" + idFromJira(name) + "'";
				sql += " ORDER BY a.cust_nm;";
			}
		} else {
			sql += " ORDER BY a.cust_nm;";
		}
		

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(year));
			rs = pstmt.executeQuery();
			System.out.println("selectResult conn : " + conn);
			System.out.println("selectResult rs : " + rs);
			while(rs.next()) {
				MainConSubVo mainConSubVo = new MainConSubVo();
				CustRegSvcVo custRegSvcVo = new CustRegSvcVo();
				int index = 1;
				projectJobCountY= 0;
				int month = 0;
				int projectVisit = 0;
				int projectRemote = 0;

				custRegSvcVo.setCon_id(rs.getString(index));
				mainConSubVo.setCon_id(rs.getString(index++));
				custRegSvcVo.setCon_year(rs.getString(index));
				mainConSubVo.setCon_year(rs.getString(index++));
				mainConSubVo.setCon_from_date(ReplaceData.replaceTimeFormat(rs.getString(index++)));
				mainConSubVo.setCon_to_date(ReplaceData.replaceTimeFormat(rs.getString(index++)));
				mainConSubVo.setCust_nm(rs.getString(index++));
				if(rs.getString(index).length() > 20)
					mainConSubVo.setProc_nm(rs.getString(index++).substring(0, 20) + "...");
				else mainConSubVo.setProc_nm(rs.getString(index++));
				mainConSubVo.setCheck_nm(rs.getString(index++));
				mainConSubVo.setMain_oper_nm(rs.getString(index++));
				mainConSubVo.setUpd_date(rs.getString(index++));

				// 1��
				custRegSvcVo.setVisit_cnt_1(rs.getInt(index));
				if(rs.getInt(index++) == 1) { // �湮�� 1�̸�, �湮���� 1 ���� �� true ��
					visit[month]++;	
					projectVisit++;
					visitCheck = true;
				}
				custRegSvcVo.setRemote_cnt_1(rs.getInt(index));
				if(rs.getInt(index++) == 1) {	// ������ 1�̸�, ���ݺ��� 1 ���� �� true ��
					remote[month]++;
					projectRemote++;
					remoteCheck = true;
				}
				custRegSvcVo.setJob_nm_1(rs.getString(index++));
				custRegSvcVo.setJob_yn_1(rs.getString(index++));
				if(custRegSvcVo.getJob_yn_1() != null && custRegSvcVo.getJob_yn_1().equals("Y")) {
					projectJobCountY++;	// �ش� ������Ʈ���� �Ϸ�� ���������� ������ 1 ����
					jobCountY_1++;	// 1���� �������� �Ϸ� ������ 1 ����
				} else if(custRegSvcVo.getJob_yn_1() != null && custRegSvcVo.getJob_yn_1().equals("C")) {	// ����, ��ҵ� �۾��� �߰ߵ� ���
					if(visitCheck) {	// visitCheck�� true�� ������ visit++ ���.
						visit[month]--;
						projectVisit--;
					}
					if(remoteCheck){	// remoteCheck�� true�� ������ remote++ ���
						remote[month]--;
						projectRemote--;
					}
				}
				custRegSvcVo.setJob_date_1(rs.getString(index++));
				visitCheck = false; remoteCheck = false; month++;

				// 2��
				custRegSvcVo.setVisit_cnt_2(rs.getInt(index));
				if(rs.getInt(index++) == 1) { // �湮�� 1�̸�, �湮���� 1 ���� �� true ��
					visit[month]++;	
					projectVisit++;
					visitCheck = true;
				}
				custRegSvcVo.setRemote_cnt_2(rs.getInt(index));
				if(rs.getInt(index++) == 1) {	// ������ 1�̸�, ���ݺ��� 1 ���� �� true ��
					remote[month]++;
					projectRemote++;
					remoteCheck = true;
				}
				custRegSvcVo.setJob_nm_2(rs.getString(index++));
				custRegSvcVo.setJob_yn_2(rs.getString(index++));
				if(custRegSvcVo.getJob_yn_2() != null && custRegSvcVo.getJob_yn_2().equals("Y")) {
					projectJobCountY++;	// �ش� ������Ʈ���� �Ϸ�� ���������� ������ 1 ����
					jobCountY_2++;	// 2���� �������� �Ϸ� ������ 1 ����
				} else if(custRegSvcVo.getJob_yn_2() != null && custRegSvcVo.getJob_yn_2().equals("C")) {	// ����, ��ҵ� �۾��� �߰ߵ� ���
					if(visitCheck) {	// visitCheck�� true�� ������ visit++ ���.
						visit[month]--;
						projectVisit--;
					}
					if(remoteCheck){	// remoteCheck�� true�� ������ remote++ ���
						remote[month]--;
						projectRemote--;
					}
				}
				custRegSvcVo.setJob_date_2(rs.getString(index++));
				visitCheck = false; remoteCheck = false; month++;

				// 3��
				custRegSvcVo.setVisit_cnt_3(rs.getInt(index));
				if(rs.getInt(index++) == 1) { // �湮�� 1�̸�, �湮���� 1 ���� �� true ��
					visit[month]++;	
					projectVisit++;
					visitCheck = true;
				}
				custRegSvcVo.setRemote_cnt_3(rs.getInt(index));
				if(rs.getInt(index++) == 1) {	// ������ 1�̸�, ���ݺ��� 1 ���� �� true ��
					remote[month]++;
					projectRemote++;
					remoteCheck = true;
				}
				custRegSvcVo.setJob_nm_3(rs.getString(index++));
				custRegSvcVo.setJob_yn_3(rs.getString(index++));
				if(custRegSvcVo.getJob_yn_3() != null && custRegSvcVo.getJob_yn_3().equals("Y")) {
					projectJobCountY++;	// �ش� ������Ʈ���� �Ϸ�� ���������� ������ 1 ����
					jobCountY_3++;	// 3���� �������� �Ϸ� ������ 1 ����
				} else if(custRegSvcVo.getJob_yn_3() != null && custRegSvcVo.getJob_yn_3().equals("C")) {	// ����, ��ҵ� �۾��� �߰ߵ� ���
					if(visitCheck) {	// visitCheck�� true�� ������ visit++ ���.
						visit[month]--;
						projectVisit--;
					}
					if(remoteCheck){	// remoteCheck�� true�� ������ remote++ ���
						remote[month]--;
						projectRemote--;
					}
				}
				custRegSvcVo.setJob_date_3(rs.getString(index++));
				visitCheck = false; remoteCheck = false; month++;

				// 4��
				custRegSvcVo.setVisit_cnt_4(rs.getInt(index));
				if(rs.getInt(index++) == 1) { // �湮�� 1�̸�, �湮���� 1 ���� �� true ��
					visit[month]++;	
					projectVisit++;
					visitCheck = true;
				}
				custRegSvcVo.setRemote_cnt_4(rs.getInt(index));
				if(rs.getInt(index++) == 1) {	// ������ 1�̸�, ���ݺ��� 1 ���� �� true ��
					remote[month]++;
					projectRemote++;
					remoteCheck = true;
				}
				custRegSvcVo.setJob_nm_4(rs.getString(index++));
				custRegSvcVo.setJob_yn_4(rs.getString(index++));
				if(custRegSvcVo.getJob_yn_4() != null && custRegSvcVo.getJob_yn_4().equals("Y")) {
					projectJobCountY++;	// �ش� ������Ʈ���� �Ϸ�� ���������� ������ 1 ����
					jobCountY_4++;	// 4���� �������� �Ϸ� ������ 1 ����
				} else if(custRegSvcVo.getJob_yn_4() != null && custRegSvcVo.getJob_yn_4().equals("C")) {	// ����, ��ҵ� �۾��� �߰ߵ� ���
					if(visitCheck) {	// visitCheck�� true�� ������ visit++ ���.
						visit[month]--;
						projectVisit--;
					}
					if(remoteCheck){	// remoteCheck�� true�� ������ remote++ ���
						remote[month]--;
						projectRemote--;
					}
				}
				custRegSvcVo.setJob_date_4(rs.getString(index++));
				visitCheck = false; remoteCheck = false; month++;

				// 5��
				custRegSvcVo.setVisit_cnt_5(rs.getInt(index));
				if(rs.getInt(index++) == 1) { // �湮�� 1�̸�, �湮���� 1 ���� �� true ��
					visit[month]++;	
					projectVisit++;
					visitCheck = true;
				}
				custRegSvcVo.setRemote_cnt_5(rs.getInt(index));
				if(rs.getInt(index++) == 1) {	// ������ 1�̸�, ���ݺ��� 1 ���� �� true ��
					remote[month]++;
					projectRemote++;
					remoteCheck = true;
				}
				custRegSvcVo.setJob_nm_5(rs.getString(index++));
				custRegSvcVo.setJob_yn_5(rs.getString(index++));
				if(custRegSvcVo.getJob_yn_5() != null && custRegSvcVo.getJob_yn_5().equals("Y")) {
					projectJobCountY++;	// �ش� ������Ʈ���� �Ϸ�� ���������� ������ 1 ����
					jobCountY_5++;	// 5���� �������� �Ϸ� ������ 1 ����
				} else if(custRegSvcVo.getJob_yn_5() != null && custRegSvcVo.getJob_yn_5().equals("C")) {	// ����, ��ҵ� �۾��� �߰ߵ� ���
					if(visitCheck) {	// visitCheck�� true�� ������ visit++ ���.
						visit[month]--;
						projectVisit--;
					}
					if(remoteCheck){	// remoteCheck�� true�� ������ remote++ ���
						remote[month]--;
						projectRemote--;
					}
				}
				custRegSvcVo.setJob_date_5(rs.getString(index++));
				visitCheck = false; remoteCheck = false; month++;

				// 6��
				custRegSvcVo.setVisit_cnt_6(rs.getInt(index));
				if(rs.getInt(index++) == 1) { // �湮�� 1�̸�, �湮���� 1 ���� �� true ��
					visit[month]++;	
					projectVisit++;
					visitCheck = true;
				}
				custRegSvcVo.setRemote_cnt_6(rs.getInt(index));
				if(rs.getInt(index++) == 1) {	// ������ 1�̸�, ���ݺ��� 1 ���� �� true ��
					remote[month]++;
					projectRemote++;
					remoteCheck = true;
				}
				custRegSvcVo.setJob_nm_6(rs.getString(index++));
				custRegSvcVo.setJob_yn_6(rs.getString(index++));
				if(custRegSvcVo.getJob_yn_6() != null && custRegSvcVo.getJob_yn_6().equals("Y")) {
					projectJobCountY++;	// �ش� ������Ʈ���� �Ϸ�� ���������� ������ 1 ����
					jobCountY_6++;	// 6���� �������� �Ϸ� ������ 1 ����
				} else if(custRegSvcVo.getJob_yn_6() != null && custRegSvcVo.getJob_yn_6().equals("C")) {	// ����, ��ҵ� �۾��� �߰ߵ� ���
					if(visitCheck) {	// visitCheck�� true�� ������ visit++ ���.
						visit[month]--;
						projectVisit--;
					}
					if(remoteCheck){	// remoteCheck�� true�� ������ remote++ ���
						remote[month]--;
						projectRemote--;
					}
				}
				custRegSvcVo.setJob_date_6(rs.getString(index++));
				visitCheck = false; remoteCheck = false; month++;

				// 7��
				custRegSvcVo.setVisit_cnt_7(rs.getInt(index));
				if(rs.getInt(index++) == 1) { // �湮�� 1�̸�, �湮���� 1 ���� �� true ��
					visit[month]++;	
					projectVisit++;
					visitCheck = true;
				}
				custRegSvcVo.setRemote_cnt_7(rs.getInt(index));
				if(rs.getInt(index++) == 1) {	// ������ 1�̸�, ���ݺ��� 1 ���� �� true ��
					remote[month]++;
					projectRemote++;
					remoteCheck = true;
				}
				custRegSvcVo.setJob_nm_7(rs.getString(index++));
				custRegSvcVo.setJob_yn_7(rs.getString(index++));
				if(custRegSvcVo.getJob_yn_7() != null && custRegSvcVo.getJob_yn_7().equals("Y")) {
					projectJobCountY++;	// �ش� ������Ʈ���� �Ϸ�� ���������� ������ 1 ����
					jobCountY_7++;	// 7���� �������� �Ϸ� ������ 1 ����
				} else if(custRegSvcVo.getJob_yn_7() != null && custRegSvcVo.getJob_yn_7().equals("C")) {	// ����, ��ҵ� �۾��� �߰ߵ� ���
					if(visitCheck) {	// visitCheck�� true�� ������ visit++ ���.
						visit[month]--;
						projectVisit--;
					}
					if(remoteCheck){	// remoteCheck�� true�� ������ remote++ ���
						remote[month]--;
						projectRemote--;
					}
				}
				custRegSvcVo.setJob_date_7(rs.getString(index++));
				visitCheck = false; remoteCheck = false; month++;

				// 8��
				custRegSvcVo.setVisit_cnt_8(rs.getInt(index));
				if(rs.getInt(index++) == 1) { // �湮�� 1�̸�, �湮���� 1 ���� �� true ��
					visit[month]++;	
					projectVisit++;
					visitCheck = true;
				}
				custRegSvcVo.setRemote_cnt_8(rs.getInt(index));
				if(rs.getInt(index++) == 1) {	// ������ 1�̸�, ���ݺ��� 1 ���� �� true ��
					remote[month]++;
					projectRemote++;
					remoteCheck = true;
				}
				custRegSvcVo.setJob_nm_8(rs.getString(index++));
				custRegSvcVo.setJob_yn_8(rs.getString(index++));
				if(custRegSvcVo.getJob_yn_8() != null && custRegSvcVo.getJob_yn_8().equals("Y")) {
					projectJobCountY++;	// �ش� ������Ʈ���� �Ϸ�� ���������� ������ 1 ����
					jobCountY_8++;	// 8���� �������� �Ϸ� ������ 1 ����
				} else if(custRegSvcVo.getJob_yn_8() != null && custRegSvcVo.getJob_yn_8().equals("C")) {	// ����, ��ҵ� �۾��� �߰ߵ� ���
					if(visitCheck) {	// visitCheck�� true�� ������ visit++ ���.
						visit[month]--;
						projectVisit--;
					}
					if(remoteCheck){	// remoteCheck�� true�� ������ remote++ ���
						remote[month]--;
						projectRemote--;
					}
				}
				custRegSvcVo.setJob_date_8(rs.getString(index++));
				visitCheck = false; remoteCheck = false; month++;

				// 9��
				custRegSvcVo.setVisit_cnt_9(rs.getInt(index));
				if(rs.getInt(index++) == 1) { // �湮�� 1�̸�, �湮���� 1 ���� �� true ��
					visit[month]++;	
					projectVisit++;
					visitCheck = true;
				}
				custRegSvcVo.setRemote_cnt_9(rs.getInt(index));
				if(rs.getInt(index++) == 1) {	// ������ 1�̸�, ���ݺ��� 1 ���� �� true ��
					remote[month]++;
					projectRemote++;
					remoteCheck = true;
				}
				custRegSvcVo.setJob_nm_9(rs.getString(index++));
				custRegSvcVo.setJob_yn_9(rs.getString(index++));
				if(custRegSvcVo.getJob_yn_9() != null && custRegSvcVo.getJob_yn_9().equals("Y")) {
					projectJobCountY++;	// �ش� ������Ʈ���� �Ϸ�� ���������� ������ 1 ����
					jobCountY_9++;	// 9���� �������� �Ϸ� ������ 1 ����
				} else if(custRegSvcVo.getJob_yn_9() != null && custRegSvcVo.getJob_yn_9().equals("C")) {	// ����, ��ҵ� �۾��� �߰ߵ� ���
					if(visitCheck) {	// visitCheck�� true�� ������ visit++ ���.
						visit[month]--;
						projectVisit--;
					}
					if(remoteCheck){	// remoteCheck�� true�� ������ remote++ ���
						remote[month]--;
						projectRemote--;
					}
				}
				custRegSvcVo.setJob_date_9(rs.getString(index++));
				visitCheck = false; remoteCheck = false; month++;

				// 10��
				custRegSvcVo.setVisit_cnt_10(rs.getInt(index));
				if(rs.getInt(index++) == 1) { // �湮�� 1�̸�, �湮���� 1 ���� �� true ��
					visit[month]++;	
					projectVisit++;
					visitCheck = true;
				}
				custRegSvcVo.setRemote_cnt_10(rs.getInt(index));
				if(rs.getInt(index++) == 1) {	// ������ 1�̸�, ���ݺ��� 1 ���� �� true ��
					remote[month]++;
					projectRemote++;
					remoteCheck = true;
				}
				custRegSvcVo.setJob_nm_10(rs.getString(index++));
				custRegSvcVo.setJob_yn_10(rs.getString(index++));
				if(custRegSvcVo.getJob_yn_10() != null && custRegSvcVo.getJob_yn_10().equals("Y")) {
					projectJobCountY++;	// �ش� ������Ʈ���� �Ϸ�� ���������� ������ 1 ����
					jobCountY_10++;	// 1���� �������� �Ϸ� ������ 1 ����
				} else if(custRegSvcVo.getJob_yn_10() != null && custRegSvcVo.getJob_yn_10().equals("C")) {	// ����, ��ҵ� �۾��� �߰ߵ� ���
					if(visitCheck) {	// visitCheck�� true�� ������ visit++ ���.
						visit[month]--;
						projectVisit--;
					}
					if(remoteCheck){	// remoteCheck�� true�� ������ remote++ ���
						remote[month]--;
						projectRemote--;
					}
				}
				custRegSvcVo.setJob_date_10(rs.getString(index++));
				visitCheck = false; remoteCheck = false; month++;

				// 11��
				custRegSvcVo.setVisit_cnt_11(rs.getInt(index));
				if(rs.getInt(index++) == 1) { // �湮�� 1�̸�, �湮���� 1 ���� �� true ��
					visit[month]++;	
					projectVisit++;
					visitCheck = true;
				}
				custRegSvcVo.setRemote_cnt_11(rs.getInt(index));
				if(rs.getInt(index++) == 1) {	// ������ 1�̸�, ���ݺ��� 1 ���� �� true ��
					remote[month]++;
					projectRemote++;
					remoteCheck = true;
				}
				custRegSvcVo.setJob_nm_11(rs.getString(index++));
				custRegSvcVo.setJob_yn_11(rs.getString(index++));
				if(custRegSvcVo.getJob_yn_11() != null && custRegSvcVo.getJob_yn_11().equals("Y")) {
					projectJobCountY++;	// �ش� ������Ʈ���� �Ϸ�� ���������� ������ 1 ����
					jobCountY_11++;	// 1���� �������� �Ϸ� ������ 1 ����
				} else if(custRegSvcVo.getJob_yn_11() != null && custRegSvcVo.getJob_yn_11().equals("C")) {	// ����, ��ҵ� �۾��� �߰ߵ� ���
					if(visitCheck) {	// visitCheck�� true�� ������ visit++ ���.
						visit[month]--;
						projectVisit--;
					}
					if(remoteCheck){	// remoteCheck�� true�� ������ remote++ ���
						remote[month]--;
						projectRemote--;
					}
				}
				custRegSvcVo.setJob_date_11(rs.getString(index++));
				visitCheck = false; remoteCheck = false;  month++;

				// 12��
				custRegSvcVo.setVisit_cnt_12(rs.getInt(index));
				if(rs.getInt(index++) == 1) { // �湮�� 1�̸�, �湮���� 1 ���� �� true ��
					visit[month]++;	
					projectVisit++;
					visitCheck = true;
				}

				custRegSvcVo.setRemote_cnt_12(rs.getInt(index));
				if(rs.getInt(index++) == 1) {	// ������ 1�̸�, ���ݺ��� 1 ���� �� true ��
					remote[month]++;
					projectRemote++;
					remoteCheck = true;
				}
				custRegSvcVo.setJob_nm_12(rs.getString(index++));
				custRegSvcVo.setJob_yn_12(rs.getString(index++));
				if(custRegSvcVo.getJob_yn_12() != null && custRegSvcVo.getJob_yn_12().equals("Y")) {
					projectJobCountY++;	// �ش� ������Ʈ���� �Ϸ�� ���������� ������ 1 ����
					jobCountY_12++;	// 1���� �������� �Ϸ� ������ 1 ����
				} else if(custRegSvcVo.getJob_yn_12() != null && custRegSvcVo.getJob_yn_12().equals("C")) {	// ����, ��ҵ� �۾��� �߰ߵ� ���
					if(visitCheck) {	// visitCheck�� true�� ������ visit++ ���.
						visit[month]--;
						projectVisit--;
					}
					if(remoteCheck){	// remoteCheck�� true�� ������ remote++ ���
						remote[month]--;
						projectRemote--;
					}
				}
				custRegSvcVo.setJob_date_12(rs.getString(index++));
				visitCheck = false; remoteCheck = false;  month++;

				/* ���⼭ ������Ʈ ī��Ʈ ���� */
				mainConSubVo.setVisitPlanCount(projectVisit); mainConSubVo.setRemotePlanCount(projectRemote);
				mainConSubVo.setMonthResultCount(projectJobCountY); mainConSubVo.setMonthTotalResultCount();


				mainConSubList.add(mainConSubVo);
				custRegSvcList.add(custRegSvcVo);
			}

			/* ���⼭ �� ī��Ʈ, ��ü ī��Ʈ ���� */
			count.setScheduledVisit_1(visit[0]); count.setScheduledRemote_1(remote[0]); count.setMonthResult_1(); count.setMonthRecent_1(jobCountY_1);
			count.setScheduledVisit_2(visit[1]); count.setScheduledRemote_2(remote[1]); count.setMonthResult_2(); count.setMonthRecent_2(jobCountY_2);
			count.setScheduledVisit_3(visit[2]); count.setScheduledRemote_3(remote[2]); count.setMonthResult_3(); count.setMonthRecent_3(jobCountY_3);
			count.setScheduledVisit_4(visit[3]); count.setScheduledRemote_4(remote[3]); count.setMonthResult_4(); count.setMonthRecent_4(jobCountY_4);
			count.setScheduledVisit_5(visit[4]); count.setScheduledRemote_5(remote[4]); count.setMonthResult_5(); count.setMonthRecent_5(jobCountY_5);
			count.setScheduledVisit_6(visit[5]); count.setScheduledRemote_6(remote[5]); count.setMonthResult_6(); count.setMonthRecent_6(jobCountY_6);
			count.setScheduledVisit_7(visit[6]); count.setScheduledRemote_7(remote[6]); count.setMonthResult_7(); count.setMonthRecent_7(jobCountY_7);
			count.setScheduledVisit_8(visit[7]); count.setScheduledRemote_8(remote[7]); count.setMonthResult_8(); count.setMonthRecent_8(jobCountY_8);
			count.setScheduledVisit_9(visit[8]); count.setScheduledRemote_9(remote[8]); count.setMonthResult_9(); count.setMonthRecent_9(jobCountY_9);
			count.setScheduledVisit_10(visit[9]); count.setScheduledRemote_10(remote[9]); count.setMonthResult_10(); count.setMonthRecent_10(jobCountY_10);
			count.setScheduledVisit_11(visit[10]); count.setScheduledRemote_11(remote[10]); count.setMonthResult_11(); count.setMonthRecent_11(jobCountY_11);
			count.setScheduledVisit_12(visit[11]); count.setScheduledRemote_12(remote[11]); count.setMonthResult_12(); count.setMonthRecent_12(jobCountY_12);
			count.setTotalVisit(); count.setTotalRemote(); count.setTotalResultSum(); count.setTotalRecentSum();

			map.put("mainConSubList", mainConSubList);
			map.put("custRegSvcList", custRegSvcList);
			map.put("count", count);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return map;
	}

	// TODO insertCheckAll
	@Override
	public int insertCheckAll(MainConSubVo vo, String date) {
		ResultSet rs = null;
		System.out.println("\n** insertCheckAll() **");
		int result = 0;
		Connection conn = new RegchedbConnectionManager().getConnection();;
		StringBuffer sql = new StringBuffer();
		int curYear = Calendar.getInstance().get(Calendar.YEAR);

		CheckuploggingImpl logging = new CheckuploggingImpl();
		// logging vo ��ü
		MainConSubVo loggingVo = new MainConSubVo();
		// logging �Ķ������ dateList ����
		List<String> dateList = new ArrayList<String>();

		String stsql = "select con_id, con_year, to_char(con_from_date, 'YYYY-MM-DD'), to_char(con_to_date,'YYYY-MM-DD'), check_nm, main_oper_nm, cust_nm, proc_nm "
				+ "from main_con where con_id=? and con_year=?;";

		try {
			pstmt = conn.prepareStatement(stsql);
			pstmt.setString(1, vo.getCon_id());
			pstmt.setString(2, vo.getCon_year());

			rs = pstmt.executeQuery();

			if(rs.next()) {
				String insertCRSSql = "insert into cust_reg_svc(con_id, con_year) values(?, ?)";
				pstmt = conn.prepareStatement(insertCRSSql);
				pstmt.setString(1, rs.getString(1));
				pstmt.setString(2, rs.getString(2));

				System.out.println("log : insert cust_reg_svc ����");

				result = pstmt.executeUpdate();

				if(result != 0) {
					String sConId = rs.getString(1);
					String sConYear = rs.getString(2);
					String sConFromDate = rs.getString(3);
					String sConToDate = rs.getString(4);
					String sCheckNm = vo.getCheck_nm();
					String sMainOperNm = vo.getMain_oper_nm();

					loggingVo.setCon_id(sConId);
					loggingVo.setCon_year(sConYear);
					loggingVo.setCon_from_date(sConFromDate);
					loggingVo.setCon_to_date(sConToDate);
					loggingVo.setCheck_nm(sCheckNm);
					loggingVo.setMain_oper_nm(sMainOperNm);
					loggingVo.setSub_oper_nm(vo.getSub_oper_nm());
					loggingVo.setCust_nm(rs.getString(7));
					loggingVo.setProc_nm(rs.getString(8));

					String inputDay = date.substring(8, 10);
					int startYear = Integer.parseInt(sConFromDate.substring(0, 4));
					int endYear = Integer.parseInt(sConToDate.substring(0, 4));
					int startMonth = Integer.parseInt(sConFromDate.substring(5, 7));
					int endMonth = Integer.parseInt(sConToDate.substring(5, 7));
					int monthCount = 0;

					System.out.println("log : insert cust_reg_svc ����");
					System.out.println("\n** input data **");
					System.out.println("id : " + rs.getString(1) + "\tseq : " + rs.getString(2) + "\tfrom_date : " + rs.getString(3) + " ~ " + rs.getString(4) + "\tcheck_nm : " + sCheckNm + "\tmain : " + sMainOperNm + "\n");


					/* ��� �Ⱓ�� 1�� �̳��� ��� */
					if(startYear == endYear) monthCount = endMonth - startMonth + 1;
					/* ��� �Ⱓ�� 2�� �̻� ���� ���� ���  ex) 2014.01.31 ~ 2015.01.31, 2013.03.16 ~ 2015.03.16 */
					else if(endYear > startYear) {
						/* ���� �⵵�� ������ ��� */
						if(startYear == curYear) {	
							monthCount = 13 - startMonth;
							/* ������ �⵵�� ������ ��� */
						} else if(endYear == curYear) {
							monthCount = endMonth;
							/* ���� �⵵�� ���Ե��� ���� ���  ex) ���Ⱓ : 2013 ~ 2015, ���� �⵵ : 2014 */
						} else if(startYear < curYear && curYear < endYear) {
							monthCount = 12;
						}
					}
					System.out.println("log : month Count : " + monthCount);

					// ���������� �޺��� ������ �迭
					String[] aCheckMonth = new String[monthCount];

					// ���Ⱓ�� �� �� �� ���
					int index = 0;
					if(startYear == endYear && startYear == curYear) {
						index = 0;
						for(int i = startMonth; i <= endMonth; i++) {
							if(i < 10) {
								// 2������ 28�ϱ����� �ִ�
								if(i == 2 && Integer.parseInt(inputDay) > 28) aCheckMonth[index++] = curYear + "-0" + i + "-28";
								else aCheckMonth[index++] = curYear + "-0" + i + "-" + inputDay;
							} else aCheckMonth[index++] = curYear + "-" + i + "-" + inputDay;
						}
						// ���Ⱓ�� Ƚ���� 2���ε� ���� ���ڰ� �۳��� ���
					} else if(endYear > startYear && endYear == curYear) {
						index = 0;
						// �տ� �⵵�� ���� �����
						for(int i = 1; i <= endMonth; i++) {
							if(i < 10) {
								// 2������ 28�ϱ���
								if(i == 2 && Integer.parseInt(inputDay) > 28) aCheckMonth[index++] = curYear + "-0" + i + "-28";
								else aCheckMonth[index++] = curYear + "-0" + i + "-" + inputDay;
							} else aCheckMonth[index++] = curYear + "-" + i + "-" + inputDay;
						}
						// ���Ⱓ�� Ƚ���� 2���ε� ���� ���ڰ� ������ ���
					} else if(endYear > startYear && startYear == curYear) {
						index = 0;
						// �տ� �⵵�� ���� �����
						for(int i = startMonth; i <= 12; i++) {
							if(i < 10) {
								// 2������ 28�ϱ���
								if(i == 2 && Integer.parseInt(inputDay) > 28) aCheckMonth[index++] = curYear + "-0" + i + "-28";
								else aCheckMonth[index++] = curYear + "-0" + i + "-" + inputDay;
							} else aCheckMonth[index++] = curYear + "-" + i + "-" + inputDay;
						}
						// ��� �Ⱓ�� Ƚ���� 3�� �̻��� ���, ���̿� �� ��� ex) ���Ⱓ : 2013 ~ 2015, curYear : 2014
					} else if(startYear < curYear && curYear < endYear) {
						index = 0;
						for(int i = 1; i <= 12; i++) {
							if(i < 10) {
								// 2������ 28�ϱ���
								if(i == 2 && Integer.parseInt(inputDay) > 28) aCheckMonth[index++] = curYear + "-0" + i + "-28";
								else aCheckMonth[index++] = curYear + "-0" + i + "-" + inputDay;
							} else aCheckMonth[index++] = curYear + "-" + i + "-" + inputDay;
						}
					}


					/* ������ */
					//					if(endYear == startYear) {	// ���Ⱓ�� 1�� �̳��� ��� - ���� ���������� �Ǿ��ִ�.
					//						index = 0;
					//						for(int i = startMonth; i <= endMonth; i++) {
					//							if(i < 10) {
					//								// 2������ 28�ϱ����� �ִ�
					//								if(i == 2 && Integer.parseInt(inputDay) > 28) aCheckMonth[index++] = startYear + "-0" + i + "-28";
					//								else aCheckMonth[index++] = startYear + "-0" + i + "-" + inputDay;
					//							} else aCheckMonth[index++] = startYear + "-" + i + "-" + inputDay;
					//						}
					//					} else if(endYear > startYear) {	// ���Ⱓ�� 1�� �̻��� ��� - 12�� �Է��սô�
					//						index = 0;
					//						// �տ� �⵵�� ���� �����
					//						for(int i = 1; i <= endMonth; i++) {
					//							if(i < 10) {
					//								// 2������ 28�ϱ���
					//								if(i == 2 && Integer.parseInt(inputDay) > 28) aCheckMonth[index++] = startYear + "-0" + i + "-28";
					//								else aCheckMonth[index++] = startYear + "-0" + i + "-" + inputDay;
					//							}
					//							else aCheckMonth[index++] = startYear + "-" + i + "-" + inputDay;
					//						}
					//
					//						
					//					}
					System.out.println("log : result of aCheckMonth[]");
					for(String s : aCheckMonth) System.out.print(s + "\t");
					System.out.println("\nlog : aCheckMonth[] ����� �Ϸ�\n");
					// ��������� �������� ��¥ ����� ��

					// cust_reg_svc ������Ʈ�� ���� month[] �� �����
					int[] month = new int[monthCount];
					index = 0;
					for(String s : aCheckMonth) {
						month[index] = Integer.parseInt(s.substring(5, 7));
						index++;
					}
					System.out.println("log : month[] ����� �Ϸ�");

					// check_nm�� ���� �ٸ��� cust_reg_svc ������Ʈ �ϱ�
					if(sCheckNm.equals("�湮(�ſ�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i++) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i++) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							for(int i = 0; i < aCheckMonth.length; i++) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								pstmt.setString(6, "V");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							if(result != 0) {
								System.out.println("log : insert job_op ����");
								conn.commit();
							}
						}

					} else if(sCheckNm.equals("����(�ſ�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i++) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i++) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							// job_op insert �ϱ�
							for(int i = 0; i < aCheckMonth.length; i++) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								pstmt.setString(6, "R");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							if(result != 0) {
								System.out.println("log : insert job_op ����");
								conn.commit();
							}
						}

					} else if(sCheckNm.equals("�湮(�ݿ�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i += 2) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i += 2) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							// job_op insert �ϱ�
							for(int i = 0; i < aCheckMonth.length; i += 2) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								pstmt.setString(6, "V");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							System.out.println("log : insert job_op ����");
						}

					} else if(sCheckNm.equals("����(�ݿ�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i += 2) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i += 2) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							// job_op insert �ϱ�
							for(int i = 0; i < aCheckMonth.length; i += 2) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								pstmt.setString(6, "R");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							System.out.println("log : insert job_op ����");
						}

					} else if(sCheckNm.equals("�湮(�б�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i += 3) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i += 3) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							// job_op insert �ϱ�
							for(int i = 0; i < aCheckMonth.length; i += 3) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								pstmt.setString(6, "V");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							System.out.println("log : insert job_op ����");
						}

					} else if(sCheckNm.equals("����(�б�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i += 3) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i += 3) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							// job_op insert �ϱ�
							for(int i = 0; i < aCheckMonth.length; i += 3) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								pstmt.setString(6, "R");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							System.out.println("log : insert job_op ����");
						}

					} else if(sCheckNm.equals("�湮(�ݱ�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i += 6) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i += 6) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							// job_op insert �ϱ�
							for(int i = 0; i < aCheckMonth.length; i += 6) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								pstmt.setString(6, "V");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							System.out.println("log : insert job_op ����");
						}

					} else if(sCheckNm.equals("����(�ݱ�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i += 6) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i += 6) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							// job_op insert �ϱ�
							for(int i = 0; i < aCheckMonth.length; i += 6) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								pstmt.setString(6, "R");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							System.out.println("log : insert job_op ����");
						}

					} else if(sCheckNm.equals("�湮(�ݿ�),����(�ݿ�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i++) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else if(i % 2 == 1) {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else if(i % 2 == 0) {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i++) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							for(int i = 0; i < aCheckMonth.length; i++) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								if(i % 2 == 0) pstmt.setString(6, "V");
								else pstmt.setString(6, "R");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							if(result != 0) {
								System.out.println("log : insert job_op ����");
								conn.commit();
							}
						}

					} else if(sCheckNm.equals("�湮(�ſ�),����(�б�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i++) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else if(i == 2 || i == 5 || i == 8 || i == 11) {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i++) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							for(int i = 0; i < aCheckMonth.length; i++) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								if(i == 2 || i == 5 || i == 8 || i == 11) pstmt.setString(6, "R");
								else pstmt.setString(6, "V");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							if(result != 0) {
								System.out.println("log : insert job_op ����");
								conn.commit();
							}
						}

					} else if(sCheckNm.equals("�湮(�ſ�),����(�ݱ�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i++) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else if(i == 5 || i == 11) {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i++) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							for(int i = 0; i < aCheckMonth.length; i++) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								if(i == 5 || i == 11) pstmt.setString(6, "R");
								else pstmt.setString(6, "V");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							if(result != 0) {
								System.out.println("log : insert job_op ����");
								conn.commit();
							}
						}

					} else if(sCheckNm.equals("�湮(�б�),����(�ſ�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i++) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else if(i == 2 || i == 5 || i == 8 || i == 11) {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i++) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							for(int i = 0; i < aCheckMonth.length; i++) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								if(i == 2 || i == 5 || i == 8 || i == 11) pstmt.setString(6, "V");
								else pstmt.setString(6, "R");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							if(result != 0) {
								System.out.println("log : insert job_op ����");
								conn.commit();
							}
						}

					} else if(sCheckNm.equals("�湮(�ݱ�),����(�ſ�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i++) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else if(i == 5 || i == 11) {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i++) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							for(int i = 0; i < aCheckMonth.length; i++) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								if(i == 5 || i == 11) pstmt.setString(6, "V");
								else pstmt.setString(6, "R");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							if(result != 0) {
								System.out.println("log : insert job_op ����");
								conn.commit();
							}
						}

					} else if(sCheckNm.equals("�湮(�б�),����(�ݱ�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i += 3) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else if(i % 2 == 1){
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i += 3) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							// job_op insert �ϱ�
							for(int i = 0; i < aCheckMonth.length; i += 3) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								if(i % 2 == 1) pstmt.setString(6, "R");
								else pstmt.setString(6, "V");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							System.out.println("log : insert job_op ����");
						}

					} else if(sCheckNm.equals("�湮(�ݱ�),����(�б�)")) {

						System.out.println("log : update cust_reg_svc ����");

						// query �����
						sql.setLength(0);
						sql.append(" UPDATE cust_reg_svc");
						for(int i = 0; i < month.length; i += 3) {
							if(i == 0) {
								sql.append(" set job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else if(i % 2 == 0){
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , remote_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							} else {
								sql.append(" , job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
								sql.append(" , job_nm_" + month[i] + " = ?");
								sql.append(" , visit_cnt_" + month[i] + " = 1");
								sql.append(" , result_cnt_" + month[i] + " = 1");
								sql.append(" , job_yn_" + month[i] + " = 'N'");
							}
						}
						sql.append(" WHERE con_id = ? AND con_year = ?");
						pstmt = conn.prepareStatement(sql.toString());

						// �� ���ε� �ϱ�
						int bindingIndex = 1;
						for(int i = 0; i < aCheckMonth.length; i += 3) {
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, aCheckMonth[i]);
							pstmt.setString(bindingIndex++, sMainOperNm);
							dateList.add(aCheckMonth[i]);
						}
						pstmt.setString(bindingIndex++, sConId);
						pstmt.setString(bindingIndex++, sConYear);

						result = pstmt.executeUpdate();

						if(result != 0) {

							System.out.println("log : update cust_reg_svc ����");
							System.out.println("log : insert job_op ����");

							// job_op insert �ϱ�
							for(int i = 0; i < aCheckMonth.length; i += 3) {
								result = 0;
								sql.setLength(0);
								sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, sConId);
								pstmt.setString(2, sConYear);
								pstmt.setString(3, aCheckMonth[i]);
								pstmt.setString(4, aCheckMonth[i]);
								pstmt.setString(5, sMainOperNm);
								if(i % 2 == 1) pstmt.setString(6, "V");
								else pstmt.setString(6, "R");
								result = pstmt.executeUpdate();
								if(result == 0) break;
							}
							System.out.println("log : insert job_op ����");
						}

					}

					// main_con ���̺��� main_oper_nm�� check_nm, upd_date �� ����
					if(result != 0) {

						System.out.println("log : main_con update ����");

						String updateSql = "update main_con set main_oper_nm=?, check_nm=?, upd_date=sysdate where con_id=? and con_year=?";
						pstmt = conn.prepareStatement(updateSql);
						pstmt.setString(1, sMainOperNm);
						pstmt.setString(2, sCheckNm);
						pstmt.setString(3, sConId);
						pstmt.setString(4, sConYear);
						result = pstmt.executeUpdate();

						// �Է� �Ϸ� - Ŀ��
						if(result != 0) {
							System.out.println("log : main_con update ���� and commit;");
							conn.commit();
							// �������� ��ü ��� logging
							logging.entirePlanInsert(loggingVo, dateList);
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// @ TODO updateCheckAll
	@Override
	public int updateCheckAll(MainConSubVo vo) {
		ResultSet rs = null;
		System.out.println("\n** updateCheckAll() **");
		int result = 0;
		Connection conn = new RegchedbConnectionManager().getConnection();;
		StringBuffer sql = new StringBuffer();
		int jobYLength = 0;
		int index = 0;

		//logging ���� ��ü ����
		CheckuploggingImpl logging = new CheckuploggingImpl();
		// beforeUpdateVo, updateVo�� ������ map
		Map<String, MainConSubVo> voMap = new HashMap<String, MainConSubVo>();
		MainConSubVo updateVo = new MainConSubVo();
		MainConSubVo beforeUpdateVo = new MainConSubVo();
		// �Ϸ�� ��¥�� ������ list
		List<String> completeDateList = new ArrayList<String>();
		// beforeUpdateDateList, updateDateList �� ������ map
		Map<String, List<String>> listMap = new HashMap<String, List<String>>();
		List<String> updateDateList = new ArrayList<String>();
		List<String> beforeUpdateDateList = new ArrayList<String>();

		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		/*
		 * input 
		 *  - vo.getCon_id()		: con_id
		 *  - vo.getCon_year()		: con_year
		 *  - vo.getMain_oper_nm()	: main_oper_nm
		 *  - vo.getSub_oper_nm()	: sub_oper_nm
		 *  - vo.check_nm()			: check_nm
		 *  - vo.getUpd_date()		: job_date
		 *  - vo.getCon_desc()		: job_reason
		 */


		try {
			//0. logging�� �ʿ��� before ������ ���ϱ�
			String beforeSql = "SELECT check_nm, main_oper_nm, sub_oper_nm FROM main_con WHERE con_id = ? AND con_year = ?;";
			pstmt = conn.prepareStatement(beforeSql);
			pstmt.setString(1, vo.getCon_id());
			pstmt.setString(2, vo.getCon_year());
			rs = pstmt.executeQuery();

			if(rs.next()) {
				beforeUpdateVo.setCon_id(vo.getCon_id());
				beforeUpdateVo.setCon_year(vo.getCon_year());
				beforeUpdateVo.setCheck_nm(rs.getString(1));
				beforeUpdateVo.setMain_oper_nm(rs.getString(2));
				beforeUpdateVo.setSub_oper_nm(rs.getString(3));
				voMap.put("beforeUpdateVo", beforeUpdateVo);
			}
			if(rs != null) rs.close();

			String beforeDateSql = "SELECT job_date FROM job_op WHERE  con_id = ? AND con_year = ? AND job_yn = 'N'";
			pstmt = conn.prepareStatement(beforeDateSql);
			pstmt.setString(1, vo.getCon_id());
			pstmt.setString(2, vo.getCon_year());
			rs = pstmt.executeQuery();

			while(rs.next()) {
				beforeUpdateDateList.add(rs.getString(1));
			}
			// 1. job_op ���� job_yn='Y'�� �� ã�Ƽ� [�Ϸ�] list �� �ֱ�.
			String checkYSql = "SELECT * FROM job_op WHERE con_id=? AND con_year=? AND job_yn='Y'";
			pstmt = conn.prepareStatement(checkYSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, vo.getCon_id());
			pstmt.setString(2, vo.getCon_year());
			rs = pstmt.executeQuery();

			System.out.println("log : select ���� (job_yn='Y')");
			if(rs.last()) {
				// job_yn='Y'�� ���ڵ��� ������ ����
				jobYLength = rs.getRow();
				rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
			}

			System.out.println("log : job_yn='Y' �� ���ڵ� length : " + jobYLength);

			List<JobOpVo> jobYList = new ArrayList<JobOpVo>();

			// job_yn='Y' �� ���ڵ���� jobYList�� �ֱ�
			while(rs.next()) {
				JobOpVo jobYVo = new JobOpVo();
				jobYVo.setJob_seq(rs.getInt(1));
				jobYVo.setCon_id(rs.getString(2));
				jobYVo.setCon_year(rs.getString(3));
				jobYVo.setJob_date(rs.getString(4));
				jobYVo.setJob_nm(rs.getString(5));
				jobYVo.setJob_visit_remote(rs.getString(6));
				jobYVo.setJob_yn(rs.getString(7));
				jobYVo.setModify_yn(rs.getString(8));
				jobYVo.setJob_reason(rs.getString(9));
				jobYVo.setReg_date(rs.getString(10));
				jobYVo.setUpd_date(rs.getString(11));
				jobYVo.setParent_seq(rs.getString(12));
				jobYList.add(jobYVo);
				// logging�� ����� list ����
				completeDateList.add(rs.getString(4));
			}

			System.out.println("log : ������ job_vo ��ü Ȯ��");
			for(JobOpVo jobYVo : jobYList) {
				System.out.println(jobYVo.getJob_seq() + "\t" + jobYVo.getCon_id() + "\t" + jobYVo.getCon_year() + "\t" + jobYVo.getJob_date() + "\t" + jobYVo.getJob_nm() + "\t" + jobYVo.getJob_visit_remote() + "\t" + jobYVo.getJob_yn() + "\t" + jobYVo.getModify_yn() + "\t" + jobYVo.getJob_reason() + "\t" + jobYVo.getReg_date() + "\t" + jobYVo.getUpd_date() + "\t" + jobYVo.getParent_seq());
			}
			System.out.println();


			// 2. from ~ to �� �°� aCheckMonth[] ����
			System.out.println("log : main_con ���� ����� ��������");
			String stsql = "select con_id, con_year, to_char(con_from_date, 'YYYY-MM-DD'), to_char(con_to_date,'YYYY-MM-DD'), check_nm, main_oper_nm "
					+ "from main_con where con_id=? and con_year=?;";

			pstmt = conn.prepareStatement(stsql);
			pstmt.setString(1, vo.getCon_id());
			pstmt.setString(2, vo.getCon_year());

			rs = pstmt.executeQuery();

			if(rs.next()) {
				String sConId = rs.getString(1);
				String sConYear = rs.getString(2);
				String sConFromDate = rs.getString(3);
				String sConToDate = rs.getString(4);
				String sCheckNm = vo.getCheck_nm();
				String sMainOperNm = vo.getMain_oper_nm();

				// logging �Ķ���� ����
				updateVo.setCon_id(vo.getCon_id());
				updateVo.setCon_year(vo.getCon_year());
				updateVo.setCheck_nm(sCheckNm);
				updateVo.setMain_oper_nm(sMainOperNm);
				updateVo.setSub_oper_nm(vo.getSub_oper_nm());
				updateVo.setCon_desc(vo.getCon_desc());
				voMap.put("updateVo", updateVo);

				System.out.println("\ncon_from_date : " + sConFromDate + "\tscon_to_date : " + sConToDate);

				String inputDay = vo.getUpd_date().substring(8, 10);
				int startYear = Integer.parseInt(sConFromDate.substring(0, 4));
				int endYear = Integer.parseInt(sConToDate.substring(0, 4));
				int startMonth = Integer.parseInt(sConFromDate.substring(5, 7));
				int endMonth = Integer.parseInt(sConToDate.substring(5, 7));
				int monthCount = 0;

				/* ��� �Ⱓ�� 1�� �̳��� ��� */
				if(startYear == endYear) monthCount = endMonth - startMonth + 1;
				/* ��� �Ⱓ�� 2�� �̻� ���� ���� ���  ex) 2014.01.31 ~ 2015.01.31, 2013.03.16 ~ 2015.03.16 */
				else if(endYear > startYear) {
					/* ���� �⵵�� ������ ��� */
					if(startYear == curYear) {	
						monthCount = 13 - startMonth;
						/* ������ �⵵�� ������ ��� */
					} else if(endYear == curYear) {
						monthCount = endMonth;
						/* ���� �⵵�� ���Ե��� ���� ���  ex) ���Ⱓ : 2013 ~ 2015, ���� �⵵ : 2014 */
					} else if(startYear < curYear && curYear < endYear) {
						monthCount = 12;
					}
				}
				System.out.println("log : month Count : " + monthCount);

				// ���������� �޺��� ������ �迭
				String[] aCheckMonth = new String[monthCount];

				// ���Ⱓ�� �� �� �� ���
				if(startYear == endYear && startYear == curYear) {
					index = 0;
					for(int i = startMonth; i <= endMonth; i++) {
						if(i < 10) {
							// 2������ 28�ϱ����� �ִ�
							if(i == 2 && Integer.parseInt(inputDay) > 28) aCheckMonth[index++] = curYear + "-0" + i + "-28";
							else aCheckMonth[index++] = curYear + "-0" + i + "-" + inputDay;
						} else aCheckMonth[index++] = curYear + "-" + i + "-" + inputDay;
					}
					// ���Ⱓ�� Ƚ���� 2���ε� ���� ���ڰ� �۳��� ���
				} else if(endYear > startYear && endYear == curYear) {
					index = 0;
					// �տ� �⵵�� ���� �����
					for(int i = 1; i <= endMonth; i++) {
						if(i < 10) {
							// 2������ 28�ϱ���
							if(i == 2 && Integer.parseInt(inputDay) > 28) aCheckMonth[index++] = curYear + "-0" + i + "-28";
							else aCheckMonth[index++] = curYear + "-0" + i + "-" + inputDay;
						} else aCheckMonth[index++] = curYear + "-" + i + "-" + inputDay;
					}
					// ���Ⱓ�� Ƚ���� 2���ε� ���� ���ڰ� ������ ���
				} else if(endYear > startYear && startYear == curYear) {
					index = 0;
					// �տ� �⵵�� ���� �����
					for(int i = startMonth; i <= 12; i++) {
						if(i < 10) {
							// 2������ 28�ϱ���
							if(i == 2 && Integer.parseInt(inputDay) > 28) aCheckMonth[index++] = curYear + "-0" + i + "-28";
							else aCheckMonth[index++] = curYear + "-0" + i + "-" + inputDay;
						} else aCheckMonth[index++] = curYear + "-" + i + "-" + inputDay;
					}
				}


				/* ������ */
				//				if(endYear == startYear) {	// ���Ⱓ�� 1�� �̳��� ��� - ���� ���������� �Ǿ��ִ�.
				//					index = 0;
				//					for(int i = startMonth; i <= endMonth; i++) {
				//						if(i < 10) {
				//							// 2������ 28�ϱ����� �ִ�
				//							if(i == 2 && Integer.parseInt(inputDay) > 28) aCheckMonth[index++] = startYear + "-0" + i + "-28";
				//							else aCheckMonth[index++] = startYear + "-0" + i + "-" + inputDay;
				//						} else aCheckMonth[index++] = startYear + "-" + i + "-" + inputDay;
				//					}
				//				} else if(endYear > startYear) {	// ���Ⱓ�� 1�� �̻��� ��� - 12�� �Է��սô�
				//					index = 0;
				//					// �տ� �⵵�� ���� �����
				//					for(int i = 1; i <= endMonth; i++) {
				//						if(i < 10) {
				//							// 2������ 28�ϱ���
				//							if(i == 2 && Integer.parseInt(inputDay) > 28) aCheckMonth[index++] = startYear + "-0" + i + "-28";
				//							else aCheckMonth[index++] = startYear + "-0" + i + "-" + inputDay;
				//						}
				//						else aCheckMonth[index++] = startYear + "-" + i + "-" + inputDay;
				//					}
				//
				//					
				//				}
				System.out.println("log : result of aCheckMonth[]");
				for(String s : aCheckMonth) System.out.print(s + "\t");
				System.out.println("\nlog : aCheckMonth[] ����� �Ϸ�\n");
				// ��������� �������� ��¥ ����� ��

				// 3. check_nm �� ���� aCheckMonth[] �� [�Ϸ�] list�� �ش��ϴ� ������ �˸´� ��ġ�� ����

				// aCheckMonth[] ���� �ٲ�� �� ����, �� �Ϸ�� �۾��� ���� ��¥�� ����
				String[] jobYDate = new String[jobYLength];
				String[] jobYName = new String[jobYLength];

				for(int i = 0; i < jobYLength; i++) {
					JobOpVo tmpVo = jobYList.get(i);
					String[] tmp = MakeModifyReason.getDateAndName(tmpVo.getJob_reason()).split(":");
					jobYDate[i] = tmp[0];
					jobYName[i] = tmp[1];
					System.out.println("log : jobYDate[" + i + "] : " + jobYDate[i]);
					System.out.println("log : jobYName[" + i + "] : " + jobYName[i]);
				}

				// �� �ٲٱ�
				System.out.println("log : �Ϸ�� �۾� ������ ���� �ۼ��� aCheckMonth[]�� ��ü");
				for(int i = 0; i < aCheckMonth.length; i++) {
					for(int j = 0; j < jobYDate.length; j++) {
						if(aCheckMonth[i].substring(0,7).equals(jobYDate[j].substring(0,7))) aCheckMonth[i] = jobYDate[j];
					}
				}

				System.out.println("log : ��ü ��� Ȯ��");
				for(String s : aCheckMonth) System.out.print(s + "\t");
				System.out.println();

				// 4. main_con �� ������ ���� �־�����
				System.out.println("log : main_con update ���� [�ٲ� ����� �ٲ��ֱ�]");
				sql.setLength(0);
				sql.append("update main_con set check_nm=?, main_oper_nm=?, upd_date=sysdate where con_id=? and con_year=?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, sCheckNm);
				pstmt.setString(2, sMainOperNm);
				pstmt.setString(3, sConId);
				pstmt.setString(4, sConYear);
				result = pstmt.executeUpdate();

				if(result == 1) {
					System.out.println("log : main_con update ����");
					// 5. cust_reg_svc �ʱ�ȭ ����
					int[] jobYMonth = new int[jobYLength];

					// cust_reg_svc���� �ʱ�ȭ ���� ���ƾ� �� ���� ã�� ���� �غ�, jobYMonth �� ����
					for(int i = 0; i < jobYLength; i++)
						jobYMonth[i] = Integer.parseInt(jobYDate[i].substring(5, 7));

					System.out.print("log : jobYMonth :");
					for(int i : jobYMonth) System.out.print("\t" + i);
					System.out.println();

					System.out.println("log : make init query about cust_reg_svc");
					sql.setLength(0);

					int initCustRegSvcStartMonth = Integer.parseInt(aCheckMonth[0].substring(5, 7));
					int initCustRegSvcEndMonth = Integer.parseInt(aCheckMonth[(aCheckMonth.length - 1)].substring(5, 7));

					System.out.println("initCustRegSvcStartMonth : " + initCustRegSvcStartMonth + "\ninitCustRegSvcEndMonth : " + initCustRegSvcEndMonth);

					sql.append("update cust_reg_svc set ");

					boolean isFirst = true;

					for(int i = initCustRegSvcStartMonth; i <= initCustRegSvcEndMonth; i++) {
						boolean check = true;	// jobYMonth�� �������� ���� ��� false�� ����

						for(int j = 0; j < jobYMonth.length; j++)
							if(i == jobYMonth[j]) check = false;

						// check �� true�̸� jobYMonth �� �������� �ƴϹǷ� �ʱ�ȭ ����
						if(check) {
							if(!isFirst) sql.append(", ");
							else isFirst = false;
							sql.append(" job_date_" + i + "=null, job_nm_" + i + "=null, visit_cnt_" + i + "=0, remote_cnt_" + i + "=0, result_cnt_" + i + "=0, job_yn_" + i + "=null");
						}
					}
					sql.append(" where con_id=? and con_year=?");

					System.out.println("log : query result ");
					System.out.println(sql.toString());

					// ���⿡�� ���� ����
					System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� ���鿡 ���� �ʱ�ȭ]");
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, sConId);
					pstmt.setString(2, sConYear);
					result = pstmt.executeUpdate();

					if(result == 1) {
						System.out.println("log : cust_reg_svc update �Ϸ�");
						System.out.println("log : job_op update ���� [job_yn='C']");
						// 6. job_op ���� job_yn='N'�� ������ ã�Ƽ� job_yn='C', modify_yn='Y', upd_date=sysdate �� update ����
						sql.setLength(0);
						sql.append("update job_op set ");
						sql.append("job_yn='C', modify_yn='Y', upd_date=sysdate ");
						sql.append("where con_id=? and con_year=? and job_yn='N'");

						// ���⿡�� ���� ����
						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, sConId);
						pstmt.setString(2, sConYear);
						result = pstmt.executeUpdate();

						System.out.println("log : job_op update �Ϸ�");
						// 7. check_nm�� �°� insert ����

						// cust_reg_svc ������Ʈ�� ���� month[] �� �����
						int[] month = new int[monthCount];
						index = 0;
						for(String s : aCheckMonth) {
							month[index] = Integer.parseInt(s.substring(5, 7));
							index++;
						}
						System.out.println("log : month[] ����� �Ϸ�");
						System.out.println("log : month[] result");
						for(int i : month) System.out.print("\t" + i);
						System.out.println();

						if(sCheckNm.equals("�湮(�ſ�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i++) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
									sql.append(" , job_nm_" + month[i] + " = ?");
									sql.append(" , visit_cnt_" + month[i] + " = 1");
									sql.append(" , result_cnt_" + month[i] + " = 1");
									sql.append(" , job_yn_" + month[i] + " = 'N'");
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i++) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i++) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										pstmt.setString(6, "V");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}

						} else if(sCheckNm.equals("����(�ſ�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i++) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
									sql.append(" , job_nm_" + month[i] + " = ?");
									sql.append(" , remote_cnt_" + month[i] + " = 1");
									sql.append(" , result_cnt_" + month[i] + " = 1");
									sql.append(" , job_yn_" + month[i] + " = 'N'");
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i++) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i++) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										pstmt.setString(6, "R");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}

						} else if(sCheckNm.equals("�湮(�ݿ�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i += 2) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
									sql.append(" , job_nm_" + month[i] + " = ?");
									sql.append(" , visit_cnt_" + month[i] + " = 1");
									sql.append(" , result_cnt_" + month[i] + " = 1");
									sql.append(" , job_yn_" + month[i] + " = 'N'");
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i += 2) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i += 2) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										pstmt.setString(6, "V");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}

						} else if(sCheckNm.equals("����(�ݿ�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i += 2) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
									sql.append(" , job_nm_" + month[i] + " = ?");
									sql.append(" , remote_cnt_" + month[i] + " = 1");
									sql.append(" , result_cnt_" + month[i] + " = 1");
									sql.append(" , job_yn_" + month[i] + " = 'N'");
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i += 2) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i += 2) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										pstmt.setString(6, "R");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}

						} else if(sCheckNm.equals("�湮(�б�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i += 3) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
									sql.append(" , job_nm_" + month[i] + " = ?");
									sql.append(" , visit_cnt_" + month[i] + " = 1");
									sql.append(" , result_cnt_" + month[i] + " = 1");
									sql.append(" , job_yn_" + month[i] + " = 'N'");
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i += 3) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i += 3) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										pstmt.setString(6, "V");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}

						} else if(sCheckNm.equals("����(�б�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i += 3) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
									sql.append(" , job_nm_" + month[i] + " = ?");
									sql.append(" , remote_cnt_" + month[i] + " = 1");
									sql.append(" , result_cnt_" + month[i] + " = 1");
									sql.append(" , job_yn_" + month[i] + " = 'N'");
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i += 3) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i += 3) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										pstmt.setString(6, "R");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}

						} else if(sCheckNm.equals("�湮(�ݱ�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i += 6) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
									sql.append(" , job_nm_" + month[i] + " = ?");
									sql.append(" , visit_cnt_" + month[i] + " = 1");
									sql.append(" , result_cnt_" + month[i] + " = 1");
									sql.append(" , job_yn_" + month[i] + " = 'N'");
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i += 6) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i += 6) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										pstmt.setString(6, "V");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}

						} else if(sCheckNm.equals("����(�ݱ�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i += 6) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
									sql.append(" , job_nm_" + month[i] + " = ?");
									sql.append(" , remote_cnt_" + month[i] + " = 1");
									sql.append(" , result_cnt_" + month[i] + " = 1");
									sql.append(" , job_yn_" + month[i] + " = 'N'");
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i += 6) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i += 6) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										pstmt.setString(6, "R");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}

						} else if(sCheckNm.equals("�湮(�ݿ�),����(�ݿ�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i++) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									if(i % 2 == 0) {
										sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
										sql.append(" , job_nm_" + month[i] + " = ?");
										sql.append(" , visit_cnt_" + month[i] + " = 1");
										sql.append(" , result_cnt_" + month[i] + " = 1");
										sql.append(" , job_yn_" + month[i] + " = 'N'");
									} else {
										sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
										sql.append(" , job_nm_" + month[i] + " = ?");
										sql.append(" , remote_cnt_" + month[i] + " = 1");
										sql.append(" , result_cnt_" + month[i] + " = 1");
										sql.append(" , job_yn_" + month[i] + " = 'N'");
									}
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i++) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i++) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										if(i % 2 == 0) pstmt.setString(6, "V");
										else pstmt.setString(6, "R");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}

						} else if(sCheckNm.equals("�湮(�ſ�),����(�б�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i++) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									if(i == 2 || i == 5 || i == 8 || i == 11) {
										sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
										sql.append(" , job_nm_" + month[i] + " = ?");
										sql.append(" , remote_cnt_" + month[i] + " = 1");
										sql.append(" , result_cnt_" + month[i] + " = 1");
										sql.append(" , job_yn_" + month[i] + " = 'N'");
									} else {
										sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
										sql.append(" , job_nm_" + month[i] + " = ?");
										sql.append(" , visit_cnt_" + month[i] + " = 1");
										sql.append(" , result_cnt_" + month[i] + " = 1");
										sql.append(" , job_yn_" + month[i] + " = 'N'");
									}
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i++) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i++) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										if(i == 2 || i == 5 || i == 8 || i == 11) pstmt.setString(6, "R");
										else pstmt.setString(6, "V");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}

						} else if(sCheckNm.equals("�湮(�ſ�),����(�ݱ�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i++) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									if(i == 5 || i == 11) {
										sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
										sql.append(" , job_nm_" + month[i] + " = ?");
										sql.append(" , remote_cnt_" + month[i] + " = 1");
										sql.append(" , result_cnt_" + month[i] + " = 1");
										sql.append(" , job_yn_" + month[i] + " = 'N'");
									} else {
										sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
										sql.append(" , job_nm_" + month[i] + " = ?");
										sql.append(" , visit_cnt_" + month[i] + " = 1");
										sql.append(" , result_cnt_" + month[i] + " = 1");
										sql.append(" , job_yn_" + month[i] + " = 'N'");
									}
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i++) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i++) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										if(i == 5 || i == 11) pstmt.setString(6, "R");
										else pstmt.setString(6, "V");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}

						} else if(sCheckNm.equals("�湮(�б�),����(�ſ�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i++) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									if(i == 2 || i == 5 || i == 8 || i == 11) {
										sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
										sql.append(" , job_nm_" + month[i] + " = ?");
										sql.append(" , visit_cnt_" + month[i] + " = 1");
										sql.append(" , result_cnt_" + month[i] + " = 1");
										sql.append(" , job_yn_" + month[i] + " = 'N'");
									} else {
										sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
										sql.append(" , job_nm_" + month[i] + " = ?");
										sql.append(" , remote_cnt_" + month[i] + " = 1");
										sql.append(" , result_cnt_" + month[i] + " = 1");
										sql.append(" , job_yn_" + month[i] + " = 'N'");
									}
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i++) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i++) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										if(i == 2 || i == 5 || i == 8 || i == 11) pstmt.setString(6, "V");
										else pstmt.setString(6, "R");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}

						} else if(sCheckNm.equals("�湮(�ݱ�),����(�ſ�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i++) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									if(i == 5 || i == 11) {
										sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
										sql.append(" , job_nm_" + month[i] + " = ?");
										sql.append(" , visit_cnt_" + month[i] + " = 1");
										sql.append(" , result_cnt_" + month[i] + " = 1");
										sql.append(" , job_yn_" + month[i] + " = 'N'");
									} else {
										sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
										sql.append(" , job_nm_" + month[i] + " = ?");
										sql.append(" , remote_cnt_" + month[i] + " = 1");
										sql.append(" , result_cnt_" + month[i] + " = 1");
										sql.append(" , job_yn_" + month[i] + " = 'N'");
									}
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i++) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i++) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										if(i == 5 || i == 11) pstmt.setString(6, "V");
										else pstmt.setString(6, "R");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}

						} else if(sCheckNm.equals("�湮(�б�),����(�ݱ�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i += 3) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									if(i % 2 == 1){
										sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
										sql.append(" , job_nm_" + month[i] + " = ?");
										sql.append(" , remote_cnt_" + month[i] + " = 1");
										sql.append(" , result_cnt_" + month[i] + " = 1");
										sql.append(" , job_yn_" + month[i] + " = 'N'");
									} else {
										sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
										sql.append(" , job_nm_" + month[i] + " = ?");
										sql.append(" , visit_cnt_" + month[i] + " = 1");
										sql.append(" , result_cnt_" + month[i] + " = 1");
										sql.append(" , job_yn_" + month[i] + " = 'N'");
									}
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i += 3) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i += 3) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										if(i % 2 == 1) pstmt.setString(6, "R");
										else pstmt.setString(6, "V");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}

						} else if(sCheckNm.equals("�湮(�ݱ�),����(�б�)")) {
							System.out.println("log : cust_reg_svc update ���� [job_yn!='Y' �� �ֵ鸸 ����]");
							// query �����
							sql.setLength(0);
							sql.append("UPDATE cust_reg_svc SET");

							isFirst = true;

							for(int i = 0; i < month.length; i += 3) {
								boolean check = true;

								for(int j = 0; j < jobYMonth.length; j++)
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									if(!isFirst) sql.append(", ");
									else isFirst = false;
									if(i % 2 == 1){
										sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
										sql.append(" , job_nm_" + month[i] + " = ?");
										sql.append(" , visit_cnt_" + month[i] + " = 1");
										sql.append(" , result_cnt_" + month[i] + " = 1");
										sql.append(" , job_yn_" + month[i] + " = 'N'");
									} else {
										sql.append(" job_date_" + month[i] + " = NVL2(?, cast(? as date), NULL)");
										sql.append(" , job_nm_" + month[i] + " = ?");
										sql.append(" , remote_cnt_" + month[i] + " = 1");
										sql.append(" , result_cnt_" + month[i] + " = 1");
										sql.append(" , job_yn_" + month[i] + " = 'N'");
									}
								}
							}
							sql.append(" WHERE con_id = ? AND con_year = ?");
							System.out.println("log : query result");
							System.out.println(sql.toString());
							pstmt = conn.prepareStatement(sql.toString());

							// �� ���ε� �ϱ�
							int bindingIndex = 1;
							for(int i = 0; i < aCheckMonth.length; i += 3) {
								boolean check = true;
								for(int j = 0; j < jobYMonth.length; j++) 
									if(month[i] == jobYMonth[j]) check = false;

								if(check) {
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, aCheckMonth[i]);
									pstmt.setString(bindingIndex++, sMainOperNm);
									updateDateList.add(aCheckMonth[i]);
								}
							}
							pstmt.setString(bindingIndex++, sConId);
							pstmt.setString(bindingIndex++, sConYear);

							result = pstmt.executeUpdate();

							if(result != 0) {

								System.out.println("log : update cust_reg_svc ����");
								System.out.println("log : insert job_op ����");

								for(int i = 0; i < aCheckMonth.length; i += 3) {
									result = 0;
									sql.setLength(0);

									boolean check = true;

									for(int j = 0; j < jobYMonth.length; j++) 
										if(month[i] == jobYMonth[j]) check = false; 

									if(check) {
										sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate);");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, sConId);
										pstmt.setString(2, sConYear);
										pstmt.setString(3, aCheckMonth[i]);
										pstmt.setString(4, aCheckMonth[i]);
										pstmt.setString(5, sMainOperNm);
										if(i % 2 == 1) pstmt.setString(6, "V");
										else pstmt.setString(6, "R");
										result = pstmt.executeUpdate();

										if(result == 0) {
											System.out.println("log : " + i + " ��°���� insert ����");
											break;
										}
									}
								}

								if(result != 0) {
									System.out.println("log : insert job_op ����");
									conn.commit();
								}
							}
						}
						System.out.println("\n*** updateDateList list ***");
						for(String s : updateDateList) System.out.println(s);

						//logging ó��
						listMap.put("updateDateList", updateDateList);
						listMap.put("beforeUpdateDateList", beforeUpdateDateList);
						logging.entirePlanUpdate(voMap, completeDateList, listMap);
						System.out.println("entirePlanUpdate executed");
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// @ TODO updateCheckMonth
	@Override
	public int updateCheckMonth(JobOpVo vo) {
		ResultSet rs = null; 
		int result = 0;
		Connection conn = new RegchedbConnectionManager().getConnection();;
		StringBuffer sql = new StringBuffer();
		String con_id = vo.getCon_id();
		String con_year = vo.getCon_year();
		String originDate = vo.getJob_date();
		String newDate = vo.getUpd_date();
		String job_nm = vo.getJob_nm();
		String job_visit_remote = vo.getJob_visit_remote();
		String job_resason = vo.getJob_reason();

		//logging ���� ��ü ����
		CheckuploggingImpl logging = new CheckuploggingImpl();
		Map<String, MainConSubVo> voMap = new HashMap<String, MainConSubVo>();
		MainConSubVo updateVo = new MainConSubVo();
		MainConSubVo beforeUpdateVo = new MainConSubVo();
		updateVo.setCon_id(con_id);
		updateVo.setCon_year(con_year);
		updateVo.setCheck_nm(job_visit_remote);
		updateVo.setMain_oper_nm(job_nm);
		updateVo.setCon_desc(job_resason);
		voMap.put("updateVo", updateVo);

		int visit_cnt = 0;
		int remote_cnt = 0;
		int result_cnt = 0;

		int originMonth = Integer.parseInt(originDate.substring(5, 7));
		int newMonth = Integer.parseInt(newDate.substring(5, 7));

		// ����, �湮�� ���� �� ����
		if(job_visit_remote.equals("V")) visit_cnt++;
		else if(job_visit_remote.equals("R")) remote_cnt++;
		result_cnt = visit_cnt + remote_cnt;

		try {
			// origin data�� ������� ���� ���� �ѹ� ������
			String originSql = "SELECT job_nm, job_visit_remote FROM job_op  WHERE con_id=? AND con_year = ? AND job_date = ? AND job_yn = 'N'";
			pstmt = conn.prepareStatement(originSql);
			pstmt.setString(1, con_id);
			pstmt.setString(2, con_year);
			pstmt.setString(3, originDate);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				beforeUpdateVo.setMain_oper_nm(rs.getString(1));
				beforeUpdateVo.setCheck_nm(rs.getString(2));
				voMap.put("beforeUpdateVo", beforeUpdateVo);
			}
			if(rs != null) rs.close();
			conn.commit();

			// ���� ���� �����͸� �����ϴ��� Ȯ������
			// �� ���� �������� ���ϴ°�? -> ������ con_id�� con_year�� �ش��ϸ鼭 job_yn='N'�� ����� 12�� ���ϴ�. ��, ���� ���� �� �ٸ���. (��, �⵵�� �簢��)
			if(originDate.substring(5, 7).equals(newDate.substring(5, 7))) {	// ���� ����! �׳� �ٲ���!
				// cust_reg_svc ���̺� ������Ʈ 
				sql.setLength(0);
				sql.append("update cust_reg_svc set job_date_" + originMonth + "=?, job_nm_" + originMonth + "=?, ");
				sql.append("visit_cnt_" + originMonth + "=?, remote_cnt_" + originMonth + "=? ");
				sql.append("where con_id=? and con_year=?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, newDate);
				pstmt.setString(2, job_nm);
				pstmt.setInt(3, visit_cnt);
				pstmt.setInt(4, remote_cnt);
				pstmt.setString(5, con_id);
				pstmt.setString(6, con_year);
				result = pstmt.executeUpdate();

				if(result == 0) {	// cust_reg_svc ������Ʈ ���� �ѹ�
					conn.rollback();
					return result;
				} else {	// cust_reg_svc ������Ʈ ����
					// job_op �� ������Ʈ ����
					sql.setLength(0);
					sql.append("update job_op set job_date=?, job_nm=?, job_visit_remote=?, job_reason=?, modify_yn='Y', upd_date=sysdate ");
					sql.append("where con_id=? and con_year=? and to_char(job_date, 'YYYY-MM-DD') like ?");
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, newDate);
					pstmt.setString(2, job_nm);
					pstmt.setString(3, job_visit_remote);
					pstmt.setString(4, job_resason);
					pstmt.setString(5, con_id);
					pstmt.setString(6, con_year);
					pstmt.setString(7, originDate);
					result = pstmt.executeUpdate();

					if(result == 0) {	// job_op ������Ʈ�� ���н� �ѹ�
						conn.rollback();
						return result;
					}
				}

			} else {	// �ٸ� �޷��� ������ ������ �õ�!
				/*
				 * 1. cust_reg_svc���� newMonth �� �ش��ϴ� �÷��� ����ִ��� Ȯ������. �ƴ϶�� -1�� ����
				 * 2. 1�� ������ cust_reg_svc ���� originMonth�� �ش��ϴ� �÷��� job_yn='C'�� ����. -> <del> �±׷� �̻ڰ� ����ϱ� ����
				 * 3. 2�� ������ cust_reg_svc���� newMonth�� �ش��ϴ� �÷��� job_date�� job_nm, cnt ����, job_yn='N'���� ������Ʈ
				 * 4. 3�� ������ job_op ���̺��� con_id, con_year, originDate�� �ش��ϴ� job_yn�� 'C'�� ����
				 * 5. 4�� ������ job_op ���̺� con_id, con_year, job_date, job_nm, job_visit_remote, job_yn='N', job_reason, reg_date�� �ش��ϴ� ������ ���Ӱ� insert
				 * 	  query : insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, job_reason, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', ?, sysdate);
				 * 6. ��� ������ 1 ����, �ƴϸ� conn.rollback(); ���� 
				 */

				sql.setLength(0);
				//				sql.append("select job_date_" + newMonth + " from cust_reg_svc where con_id=? and con_year=?");
				sql.append("select job_yn_" + newMonth + " from cust_reg_svc where con_id=? and con_year=?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, con_id);
				pstmt.setString(2, con_year);
				rs = pstmt.executeQuery();

				if(rs.next()) {	
					if("N".equals(rs.getString(1))) {	// N�� �ƴϸ� �־ �ȴٴ� ������! 
						return -1;
					} else {	// newMonth �� �ش��ϴ� ���� ����ֳ� ���� �׷� ��������.
						sql.setLength(0);
						sql.append("update cust_reg_svc set job_yn_" + originMonth + "='C' where con_id=? and con_year=?");
						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, con_id);
						pstmt.setString(2, con_year);
						result = pstmt.executeUpdate();

						if(result == 0) {	// update �����ϸ� �ѹ�
							conn.rollback();
							return result;
						} else {	// update ����! �� ������Ʈ �õ�!
							sql.setLength(0);
							sql.append("update cust_reg_svc set job_date_" + newMonth + "=?, job_nm_" + newMonth + "=?, ");
							sql.append("visit_cnt_" + newMonth + "=?, remote_cnt_" + newMonth + "=?, result_cnt_" + newMonth + "=?, ");
							sql.append("job_yn_" + newMonth + "='N' where con_id=? and con_year=?");
							pstmt = conn.prepareStatement(sql.toString());
							pstmt.setString(1, newDate);
							pstmt.setString(2, job_nm);
							pstmt.setInt(3, visit_cnt);
							pstmt.setInt(4, remote_cnt);
							pstmt.setInt(5, result_cnt);
							pstmt.setString(6, con_id);
							pstmt.setString(7, con_year);
							result = pstmt.executeUpdate();

							if(result == 0) {	// update ���н� �ѹ�!
								conn.rollback();
								return result;
							} else {	// update �� ����! ���� job_op ������Ʈ!!
								sql.setLength(0);
								sql.append("update job_op set job_yn='C' where con_id=? and con_year=? and to_char(job_date, 'YYYY-MM-DD') like ?");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, con_id);
								pstmt.setString(2, con_year);
								pstmt.setString(3, originDate);
								result = pstmt.executeUpdate();

								if(result == 0) {	// job_op update ���н�...
									conn.rollback();
									return result;
								} else {	// job_op update ������! ������ insert!
									sql.setLength(0);
									sql.append("insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'N', sysdate)");
									pstmt = conn.prepareStatement(sql.toString());
									pstmt.setString(1, con_id);
									pstmt.setString(2, con_year);
									pstmt.setString(3, newDate);
									pstmt.setString(4, newDate);
									pstmt.setString(5, job_nm);
									pstmt.setString(6, job_visit_remote);

									result = pstmt.executeUpdate();

									if(result == 0) {	// ������ insert ���н�... �ѹ�!
										conn.rollback();
										return result;
									}
									conn.commit();
								}
							}
						}
					}
				}
			}

			conn.commit();
			logging.selectedPlanUpdate(voMap, originDate, newDate);
		} catch (SQLException e) {
			if(result == 0)
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// @ TODO insertCompleteCheck
	@Override
	public int insertCompleteCheck(JobOpVo vo) {
		System.out.println("\n** CheckupDaoImpl.insertCompleteCheck **");
		/*
		 * vo.con_id	: �۾��� ���� id
		 * vo.con_year	: �۾��� �⵵
		 * vo.job_nm	: ������
		 * vo.job_date	: ������ �Ϸ�� ��¥
		 * vo.upd_date  : ������ ��¥�� ����ִ� �κ� (�̷� �򰥸��µ�... ��¿�� ���� ��)
		 * vo.job_visit_remote : �湮���� �������� �κ�
		 * 
		 * ������ ��¥�� ������ ��¥�� �޶� ������ �Ϸ�ǵ��� ����.
		 * ��, ���� con_id, con_year, upd_date�� �ش��ϴ� record�� �ִ��� Ȯ���� �ϰ�.
		 * �ش� ���ڵ尡 �ִٸ�, job_yn='C'�� ����.
		 * job_op.job_seq ���� ������ ��
		 * job_op ���̺� ���Ӱ� con_id, con_year, job_nm, job_date �� insert
		 * job_op.parent_seq �� ������ job_op.job_seq ���� �ֱ�
		 */
		ResultSet rs = null;
		int result = 0;
		int job_seq = 0;
		Connection conn = new RegchedbConnectionManager().getConnection();;
		System.out.println(vo.getCon_id() + ", " + vo.getCon_year() + ", " + vo.getJob_nm() + ", " + vo.getJob_date() + ", " + vo.getUpd_date() + ", " + vo.getJob_reason());
		String originDate = vo.getUpd_date();
		String job_date = vo.getJob_date();
		String con_id = vo.getCon_id();
		String con_year = vo.getCon_year();
		String job_nm = vo.getJob_nm();
		String job_visit_remote = vo.getJob_visit_remote();
		String tmp_job_reason = vo.getJob_reason();
		String origin_job_reason = null;
		String job_reason = vo.getJob_reason();
		String rollbackData = null;
		String rollbackJob_nm = null;
		String rollbackJob_date = originDate;
		int month = 0;

		// logging ��ü ����
		CheckupLogging logging = new CheckuploggingImpl();
		MainConSubVo loggingVo = new MainConSubVo();
		loggingVo.setCon_id(con_id);
		loggingVo.setCon_year(con_year);
		loggingVo.setMain_oper_nm(job_nm);
		loggingVo.setCheck_nm(job_visit_remote);

		try {
			// 1. select job_nm, job_date, job_reason from job_op where con_id=? and con_year=? and to_char(job_date, 'YYYY-MM-DD') like ? and job_yn!='C'
			// rollback �� ������ ����
			System.out.println("select job_nm, job_date, job_reason from job_op ����\t" + con_id + "\t" + con_year + "\t" + originDate);
			String originSql = "select job_seq, job_nm, job_date, job_reason from job_op where con_id=? and con_year=? and to_char(job_date, 'YYYY-MM-DD') like ? and job_yn!='C'";
			pstmt = conn.prepareStatement(originSql);
			pstmt.setString(1, con_id);
			pstmt.setString(2, con_year);
			pstmt.setString(3, originDate);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				job_seq = rs.getInt(1);
				rollbackJob_nm = rs.getString(2);
				rollbackJob_date = rs.getString(3);
				origin_job_reason = rs.getString(4);
			}

			if(rs != null) rs.close();

			if(origin_job_reason == null) origin_job_reason = job_reason;

			System.out.println("origin_job_reason : " + origin_job_reason);
			String[] rollbackTmp = null;
			rollbackData = MakeModifyReason.getDateAndName(origin_job_reason);
			if(rollbackData != null && rollbackData.indexOf(":") != -1) {
				rollbackTmp = rollbackData.split(":");
				month = Integer.parseInt(rollbackTmp[0].substring(5, 7));	// cust_reg_svc ���� Ÿ�� �÷��� ã�� ���� month
				System.out.println("rollback date : " + rollbackTmp[0]);
			} else {
				month = Integer.parseInt(rollbackJob_date.substring(5, 7));	// cust_reg_svc ���� Ÿ�� �÷��� ã�� ���� month
				System.out.println("rollback date : " + rollbackJob_date);
			}

			if(originDate.equals(job_date)) {	// ���� �����ϰ� ���� �������� ���� ���
				System.out.println("���� �����ϰ� ���� �������� ����! \tid : " + con_id + "\tseq : " + con_year + "\tjob_date : " + job_date + "\tjob_nm : " + job_nm);
				// cust_reg_svc�� �۾� �Ϸ�� ��¥�� �����ڷ� ������ ����.
				String custUpdateSql = "update cust_reg_svc set job_date_" + month + "=?, job_nm_" + month + "=?, job_yn_" + month + "='Y' where con_id=? and con_year=?";
				pstmt = conn.prepareStatement(custUpdateSql);
				pstmt.setString(1, job_date);
				pstmt.setString(2, job_nm);
				pstmt.setString(3, con_id);
				pstmt.setString(4, con_year);
				result = pstmt.executeUpdate();

				if(result <= 0) {	// cust_reg_svc �� upadate ���н�
					conn.rollback();
					System.out.println("cust_reg_svc update ����");
					return result;
				} else {	// ������
					System.out.println("update cust_reg_svc ����!");
					String jobSql = "update job_op set job_nm=?, job_date=?, job_visit_remote=?, job_yn='Y', upd_date=sysdate, job_reason=?";
					if(MakeModifyReason.getDateAndName(tmp_job_reason) == null) { // �ʱ⿡ �۾� �Ϸ��, �� ó�� ��ϵ� �ð��� �̸��� �����ϱ� ���Ͽ� job_reason�� ����� ������ ���� (**YYYY-MM-DD:000**)
						job_reason = MakeModifyReason.make("", "**" + rollbackJob_date + ":" + rollbackJob_nm + "**");
						if(origin_job_reason != null) job_reason += MakeModifyReason.getWithoutRollbackData(origin_job_reason);
						System.out.println("job_reason : " + job_reason);
						if(tmp_job_reason != null) {
							job_reason += tmp_job_reason;
							System.out.println("job_reason : " + job_reason);
						}
					}else {	// ��������Ͱ� ���� ��� ������ ����̴�.
						jobSql += ", modify_yn='Y'";
					}
					System.out.println(con_id + "\t" + con_year + "\t" + originDate + "\t" + job_nm + "\t" + job_date + "\t" + job_visit_remote + "\t" + job_reason + "\t" + tmp_job_reason + "\t" + origin_job_reason);
					//					if(rollbackData == null) {
					//						 jobSql += " where con_id=? and con_year=? and to_char(job_date, 'YYYY-MM-DD') like ? and job_yn!='C'";
					//						 if(origin_job_reason == null) {
					//							 jobSql += " and job_reason is null";
					//							 pstmt = conn.prepareStatement(jobSql);
					//						 } else {
					//							 jobSql += " and job_reason=?";
					//							 System.out.println(jobSql);
					//							 pstmt = conn.prepareStatement(jobSql);
					//							 pstmt.setString(8, origin_job_reason);
					//						 }
					//					} else {
					//						jobSql += " where con_id=? and con_year=? and to_char(job_date, 'YYYY-MM-DD') like ? and job_yn!='C'and job_reason like ?";
					//						pstmt.setString(8, "%" + rollbackData + "%");
					//						pstmt = conn.prepareStatement(jobSql);
					//					}
					jobSql += " where job_seq=?";
					System.out.println(jobSql);

					pstmt = conn.prepareStatement(jobSql);
					pstmt.setString(1, job_nm);
					pstmt.setString(2, job_date);
					pstmt.setString(3, job_visit_remote);
					pstmt.setString(4, job_reason);
					pstmt.setInt(5, job_seq);
					//					pstmt.setString(5, con_id);
					//					pstmt.setString(6, con_year);
					//					pstmt.setString(7, originDate);
					result = pstmt.executeUpdate();
					System.out.println(con_id + "\t" + con_year + "\t" + originDate + "\t" + job_nm + "\t" + job_date + "\t" + job_visit_remote + "\t" + job_reason + "\t" + tmp_job_reason + "\t" + origin_job_reason);
					System.out.println("result : " + result + "\t����� ���?");
					if(result <= 0) conn.rollback();
					else conn.commit();
				}

			} else {	// ���� �����ϰ� ���� �������� �ٸ� ���
				System.out.println("���� �����ϰ� ���� �������� �ٸ���!");
				// cust_reg_svc�� �۾� �Ϸ�� ��¥�� �����ڷ� ������ ����.
				String custUpdateSql = "update cust_reg_svc set job_date_" + month + "=?, job_nm_" + month + "=?, job_yn_" + month + "='Y' where con_id=? and con_year=?";
				pstmt = conn.prepareStatement(custUpdateSql);
				pstmt.setString(1, job_date);
				pstmt.setString(2, job_nm);
				pstmt.setString(3, con_id);
				pstmt.setString(4, con_year);
				result = pstmt.executeUpdate();
				if(result <= 0) {
					conn.rollback();
					System.out.println("update cust_reg_svc ����");
					return result;
				}
				// job_seq�� ������
				System.out.println("job_seq�� ������!");
				String selectSql = "select job_seq from job_op where con_id=? and con_year=? and to_char(job_date, 'YYYY-MM-DD') like ? and job_yn!='C'";
				pstmt = conn.prepareStatement(selectSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				pstmt.setString(1, con_id);
				pstmt.setString(2, con_year);
				pstmt.setString(3, originDate);
				rs = pstmt.executeQuery();
				int parent_seq = 0;

				if(rs.last()) {
					if(rs.getRow() > 1) {
						conn.rollback();
						System.out.println("job_seq ���ϴµ� row ������ �� ����");
						return -3;
					}
					rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
				}

				// ������� ���Դ� => ������ ��¥�� �ش��ϴ� record�� job_yn='C'�� �ٲٰ� ���� insert(insert�� parent_seq �� �����ϴ� job_seq�� insert) 
				if(rs.next()) {
					parent_seq = rs.getInt(1);

					String updateSql = null;
					System.out.println("rollbackData : " + rollbackData + "\tjob_reason : " + job_reason);
					//					if(rollbackData == null) {
					//						updateSql = "update job_op set job_yn='C', upd_date=sysdate where con_id=? and con_year=? and to_char(job_date, 'YYYY-MM-DD') like ? and job_yn='Y' and job_reason ";
					//						
					//						if(origin_job_reason == null) {
					//							updateSql += "is null";
					//							pstmt = conn.prepareStatement(updateSql);
					//						} else {
					//							updateSql += "= ?";
					//							pstmt = conn.prepareStatement(updateSql);
					//							pstmt.setString(4, origin_job_reason);
					//						}
					//						
					//					} else {
					//						System.out.println("rollbackData : " + rollbackData);
					//						updateSql = "update job_op set job_yn='C', upd_date=sysdate where con_id=? and con_year=? and to_char(job_date, 'YYYY-MM-DD') like ? and job_yn='Y' and job_reason LIKE ?";
					//						pstmt = conn.prepareStatement(updateSql);
					//						pstmt.setString(4, "%" + rollbackData + "%");
					//					}
					updateSql = "update job_op set job_yn='C', upd_date=sysdate where job_seq=?";
					pstmt = conn.prepareStatement(updateSql);
					pstmt.setInt(1, job_seq);
					//					pstmt.setString(1, con_id);
					//					pstmt.setString(2, con_year);
					//					pstmt.setString(3, originDate);
					result = pstmt.executeUpdate();
					System.out.println("parent_seq : " + parent_seq + "\tcon_id : " + con_id + "\tcon_year : " + con_year + "\toriginDate : " + originDate + "\torigin_job_reason : " + origin_job_reason);
					if(result <= 0) {	// ���� ���н�
						conn.rollback();
						System.out.println("update job_seq ����");
						System.out.println("sql : " + updateSql);
						return result;
					} else {	// ���� ������ ���Ӱ� insert ����
						String insertSql = "insert into job_op(con_id, con_year, job_date, job_nm, job_visit_remote, job_yn, reg_date, modify_yn, job_reason, parent_seq, upd_date) values(?, ?, NVL2(?, cast(? as date), NULL), ?, ?, 'Y', sysdate,?, ?, ?, sysdate)";
						pstmt = conn.prepareStatement(insertSql);
						pstmt.setString(1, con_id);
						pstmt.setString(2, con_year);
						pstmt.setString(3, job_date);
						pstmt.setString(4, job_date);
						pstmt.setString(5, job_nm);
						pstmt.setString(6, job_visit_remote);
						if(MakeModifyReason.getDateAndName(job_reason) == null) { // �ʱ⿡ �۾� �Ϸ��, �� ó�� ��ϵ� �ð��� �̸��� �����ϱ� ���Ͽ� job_reason�� ����� ������ ���� (**YYYY-MM-DD:000**)
							String tmp = origin_job_reason;
							job_reason = MakeModifyReason.make("", "**" + rollbackJob_date + ":" + rollbackJob_nm + "**");
							System.out.println("rollback + origin_job_reason : " + job_reason);
							if(tmp != null) job_reason += tmp;
							pstmt.setString(7, null);
						} else {	// ������ ���
							pstmt.setString(7, "Y");
						}
						pstmt.setString(8, job_reason);
						pstmt.setInt(9, parent_seq);
						result = pstmt.executeUpdate();

						if(result <= 0) {	// insert ������ ���
							conn.rollback();
							System.out.println("insert job_op ����");
							return result;
						} else conn.commit();
					}
				}
			}
			// logging ����
			loggingVo.setCon_desc(job_reason);
			logging.registerRegularCheckup(loggingVo, originDate, job_date);
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if(result <= 0) conn.rollback();
				else conn.commit();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public MainConSubVo selectInfoByCodeSeq(String con_id, String con_year) {
		ResultSet rs = null;
		Connection conn = new RegchedbConnectionManager().getConnection();;
		MainConSubVo vo = new MainConSubVo();

		String sql = "select cust_nm, proc_nm, con_from_date, con_to_date, con_year, check_nm, main_oper_nm, sub_oper_nm, upd_date "
				+ "from main_con where con_id='" + con_id + "' and con_year='" + con_year + "' order by con_year desc;";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if(rs.next()) {
				vo.setCust_nm(rs.getString(1));
				vo.setProc_nm(rs.getString(2));
				vo.setCon_from_date(rs.getString(3));
				vo.setCon_to_date(rs.getString(4));
				vo.setCon_year(rs.getString(5));
				vo.setCheck_nm(rs.getString(6));
				vo.setMain_oper_nm(rs.getString(7));
				vo.setSub_oper_nm(rs.getString(8));
				vo.setUpd_date(rs.getString(9));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vo;
	}

	@Override
	public Map<String, Object> selectCompleteShow(JobOpVo vo) {
		ResultSet rs = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Connection conn = new RegchedbConnectionManager().getConnection();;
		String con_id = vo.getCon_id();
		String con_year = vo.getCon_year();
		int month = Integer.parseInt(vo.getJob_date().substring(4, 6));
		String job_date = null;
		String compareDate = vo.getJob_date().substring(0, 6) + "%";
		System.out.println("\n ** selectCompleteShow() ** ");
		System.out.println("id : " + con_id + "\tseq : " + con_year);

		String sql = "select a.con_id, a.con_year, a.cust_nm, a.proc_nm, a.main_oper_nm, b.job_nm, b.job_date, b.job_visit_remote, b.job_reason, to_char(b.upd_date, 'YYYY-MM-DD') "
				+ "from main_con a, job_op b where a.con_id=b.con_id AND a.con_year=b.con_year and b.con_id=? and b.con_year=? and to_char(b.job_date, 'YYYYMMDD') like ? and b.job_yn!='C'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, con_id);
			pstmt.setString(2, con_year);
			pstmt.setString(3, compareDate);
			rs = pstmt.executeQuery();
			System.out.println(con_id +"\t" + con_year + "\t" + compareDate);

			if(rs.next()) {

				//				if(MakeModifyReason.getDateAndName(rs.getString(9)) != null)
				//					job_date = MakeModifyReason.getDateAndName(rs.getString(9)).split(":")[0];
				//				else
				job_date = rs.getString(7);

				MainConSubVo mainVo = new MainConSubVo();
				JobOpVo jobVo = new JobOpVo();
				System.out.println(rs.getString(1) + "\t" + rs.getInt(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\t" + rs.getString(8) + "\t" + rs.getString(9) + "\t" + rs.getString(10));
				mainVo.setCon_id(rs.getString(1));
				jobVo.setCon_id(rs.getString(1));
				mainVo.setCon_year(rs.getString(2));
				jobVo.setCon_year(rs.getString(2));
				mainVo.setCust_nm(rs.getString(3));
				mainVo.setProc_nm(rs.getString(4));
				mainVo.setMain_oper_nm(rs.getString(5));
				jobVo.setJob_nm(rs.getString(6));
				jobVo.setJob_date(job_date);
				jobVo.setJob_visit_remote(rs.getString(8));
				jobVo.setJob_reason(rs.getString(9));
				jobVo.setUpd_date(rs.getString(10));
				map.put("mainConSubVo", mainVo);
				map.put("jobOpVo", jobVo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	@Override
	public int completeRollback(JobOpVo vo) {
		ResultSet rs = null;
		int result = 0;
		System.out.println("first result : " + result);
		Connection conn = new RegchedbConnectionManager().getConnection();;
		String con_id = vo.getCon_id();
		String con_year = vo.getCon_year();
		System.out.println(vo.getJob_reason());
		String[] rollbackData = MakeModifyReason.getDateAndName(vo.getJob_reason()).split(":");
		String job_date = rollbackData[0];
		String job_nm = rollbackData[1];
		System.out.println("\n** completeRollback() **");
		System.out.println(con_id + "\t" + con_year + "\t" + job_date + "\t" + job_nm);

		// logging ��ü ����
		CheckupLogging logging = new CheckuploggingImpl();
		MainConSubVo updateVo = new MainConSubVo();
		MainConSubVo beforeUpdateVo = logging.beforeUpdatedVo(con_id, con_year);
		updateVo.setCon_id(con_id);
		updateVo.setCon_year(con_year);
		updateVo.setCust_nm(beforeUpdateVo.getCust_nm());
		updateVo.setProc_nm(beforeUpdateVo.getProc_nm());
		// newReason�� ������ ���� ��� modify_yn�� �ؼ� ����� ��
		updateVo.setCon_desc(vo.getModify_yn());

		try {
			// 1. compareDate �� ����� ���� job_op���� job_date�� �˻�
			String sql = "select job_seq, job_date from job_op where con_id=? and con_year=? and job_reason=? and job_yn!='C'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, con_id);
			pstmt.setString(2, con_year);
			pstmt.setString(3, vo.getJob_reason());
			rs = pstmt.executeQuery();

			if(rs.next()) {
				System.out.println("1. job_op.job_date �˻� �Ϸ�");
				int deleteJobSeq = rs.getInt(1);
				int month = Integer.parseInt(job_date.substring(5, 7));	// cust_reg_svc ���� �ش� �޿� ���� �÷��� �����ϱ� ���� ����
				String compareDate = rs.getString(2).substring(0, 7) + "%";	// �ѹ��ϰ��� �ϴ� ���ڵ带 ã�� ���� �ʿ��� ����

				// 2. ���� �޿� �ش��ϴ� �÷��� �����Ѵ�.
				sql = "update cust_reg_svc set job_date_" + month + "=?, job_nm_" + month + "=?, job_yn_" + month + "='N' "
						+ "where con_id=? and con_year=?;";
				System.out.println("2. ���� �޿� �ش��ϴ� �÷� ���� SQL : " + sql);
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, job_date);
				pstmt.setString(2, job_nm);
				pstmt.setString(3, con_id);
				pstmt.setString(4, con_year);

				result = pstmt.executeUpdate();
				System.out.println("cust_reg_svc result : " + result);

				if(result == 1) {
					// 3. rollback �� �� ����
					System.out.println("3. rollback �� �� ����");
					sql = "delete from job_op where job_seq = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, deleteJobSeq);
					result = pstmt.executeUpdate();

					if(result == 1) {	// cust_reg_svc ������ ������
						System.out.println("cust_reg_svc ���� �Ϸ�");
						result = 0;
						sql = "update job_op set job_date=?, job_nm=?, job_yn='N', modify_yn='N', job_reason=null, upd_date=sysdate "
								+ "where con_id=? and con_year=? and to_char(job_date, 'YYYY-MM-DD') like ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, job_date);
						pstmt.setString(2, job_nm);
						pstmt.setString(3, con_id);
						pstmt.setString(4, con_year);
						pstmt.setString(5, compareDate);
						System.out.println(sql);
						System.out.println(job_date + "\t" + job_nm + "\t" + con_id + "\t" + con_year + "\t" + compareDate);
						result = pstmt.executeUpdate();
						System.out.println("job_op result : " + result);
						conn.commit();

					} else {
						conn.rollback();
					}
				}
			}
			logging.rollbackCompletedPlan(updateVo, job_date);
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			try {
				if(result == 0) conn.rollback();
				else conn.commit();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public List<JobOpVo> selectMonth(JobOpVo vo) {
		ResultSet rs = null;
		List<JobOpVo> list = new ArrayList<JobOpVo>();
		Connection conn = new RegchedbConnectionManager().getConnection();;
		String con_id = vo.getCon_id();
		String con_year = vo.getCon_year();

		try {
			String sql = "SELECT job_date FROM job_op WHERE con_id=? AND con_year=? AND job_yn='N'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, con_id);
			pstmt.setString(2, con_year);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				JobOpVo jobVo = new JobOpVo();
				jobVo.setJob_date(rs.getString(1));
				list.add(jobVo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	@Override
	public Map<String, Object> selectHistory(int year, Map<String, Object> map) {
		ResultSet rs = null;
		Map<String, Object> outputMap = new HashMap<String, Object>();
		List<JobOpVo> jobList = new ArrayList<JobOpVo>();
		StringBuffer sql = new StringBuffer();
		sql.setLength(0);

		// query�� �غ�
		sql.append("SELECT a.cust_nm, a.proc_nm, b.job_date, b.job_nm, b.job_reason, b.job_visit_remote, b.job_yn, ");

		String cust_nm = null;
		String proc_nm = null;
		String job_nm = null;
		String job_date = null;
		String check_nm = null;	// ���⼭ check_nm �� �湮���� ���������� ����.

		Connection conn = new RegchedbConnectionManager().getConnection();;

		// �˻��� ���� ���� �ִ��� ������ Ȯ���� �غ���.
		JobOpVo inputJobVo =  (JobOpVo)map.get("jobVo");
		MainConSubVo inputMainVo = (MainConSubVo)map.get("mainVo");

		System.out.println("\n ** selectHistory() ** ");

		if(inputMainVo.getCust_nm() != null) {
			System.out.println("inputMainVo.getCust_nm() != null");
			cust_nm = inputMainVo.getCust_nm();
			System.out.println("cust_nm : " + cust_nm);

			sql.append("(SELECT COUNT(c.con_id) FROM job_op c WHERE c.job_yn!='C' and to_char(c.job_date, 'YYYY-MM-DD') like '" + year + "%' GROUP BY c.con_id HAVING c.con_id=b.con_id) AS cnt ");
			sql.append("FROM main_con a, job_op b ");
			sql.append("WHERE a.con_id=b.con_id AND a.con_year=b.con_year AND b.job_yn!='C' and to_char(b.job_date, 'YYYY-MM-DD') like '" + year + "%' ");
			sql.append("AND a.cust_nm like '%" + cust_nm + "%' ");


		} else if(inputMainVo.getProc_nm() != null) {
			System.out.println("inputMainVo.getProc_nm() != null");
			proc_nm = inputMainVo.getProc_nm();
			System.out.println("proc_nm : " + proc_nm);

			sql.append("(SELECT COUNT(c.con_id) FROM job_op c WHERE c.job_yn!='C' and to_char(c.job_date, 'YYYY-MM-DD') like '" + year + "%' GROUP BY c.con_id HAVING c.con_id=b.con_id) AS cnt ");
			sql.append("FROM main_con a, job_op b ");
			sql.append("WHERE a.con_id=b.con_id AND a.con_year=b.con_year AND b.job_yn!='C' and to_char(b.job_date, 'YYYY-MM-DD') like '" + year + "%' ");
			sql.append("AND a.proc_nm like '%" + proc_nm + "%' ");
			sql.append("ORDER BY a.con_id DESC, job_date");

			// �湮����, �������� �˾ƺ��� ��.
		} else if(inputMainVo.getCheck_nm() != null) {
			System.out.println("inputMainVo.getCheck_nm() != null");
			check_nm = inputMainVo.getCheck_nm();
			System.out.println("check_nm : " + check_nm);

			if(check_nm.equals("�湮")) check_nm = "V";
			else if(check_nm.equals("����")) check_nm = "R";

			sql.append("(SELECT COUNT(c.con_id) FROM job_op c WHERE c.job_yn!='C' and to_char(c.job_date, 'YYYY-MM-DD') like '" + year + "%' AND c.job_visit_remote='" + check_nm + "' GROUP BY c.con_id HAVING c.con_id=b.con_id) AS cnt ");
			sql.append("FROM main_con a, job_op b ");
			sql.append("WHERE a.con_id=b.con_id AND a.con_year=b.con_year AND b.job_yn!='C' and to_char(b.job_date, 'YYYY-MM-DD') like '" + year + "%' ");
			sql.append("AND b.job_visit_remote='" + check_nm + "' ");
			sql.append("ORDER BY a.con_id DESC, job_date");

			// ���� �ʿ�
			// ���� ��¥�� ���� -> job_reason ���� �̾ƾ� ��.
		} else if(inputJobVo.getJob_date() != null) {
			System.out.println("inputJobVo.getJob_date() != null");
			job_date = inputJobVo.getJob_date();
			System.out.println("job_date : " + job_date);

			sql.setLength(0);
			sql.append("SELECT A.cust_nm, A.proc_nm, A.job_date, A.job_nm, A.job_reason, A.job_visit_remote, A.job_yn, ");
			sql.append("(SELECT COUNT(B.cust_nm) FROM (SELECT a.cust_nm, a.proc_nm, b.job_date, b.job_nm, b.job_reason, b.job_visit_remote, b.job_yn, (SELECT COUNT(c.con_id) FROM job_op c WHERE c.job_yn='N' AND TO_CHAR(c.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND to_char(c.job_date, 'YYYY-MM-DD') like '%" + job_date + "%' GROUP BY c.con_id HAVING c.con_id=b.con_id) AS cnt FROM main_con a, job_op b WHERE a.con_id=b.con_id AND a.con_year=b.con_year AND TO_CHAR(b.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND b.job_yn='N' AND TO_CHAR(b.job_date, 'YYYY-MM-DD') LIKE '" + job_date + "%' ");
			sql.append("UNION SELECT a.cust_nm, a.proc_nm, b.job_date, b.job_nm, b.job_reason, b.job_visit_remote, b.job_yn, (SELECT COUNT(c.con_id) FROM job_op c WHERE c.job_yn='Y' AND TO_CHAR(c.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND c.job_reason LIKE '**%" + job_date + "%:%' GROUP BY c.con_id HAVING c.con_id=b.con_id) AS cnt FROM main_con a, job_op b WHERE a.con_id=b.con_id AND a.con_year=b.con_year AND TO_CHAR(b.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND b.job_yn='Y' AND b.job_reason LIKE '**%" + job_date + "%:%') B WHERE B.cust_nm=A.cust_nm) AS cnt ");
			sql.append("FROM (SELECT a.cust_nm, a.proc_nm, b.job_date, b.job_nm, b.job_reason, b.job_visit_remote, b.job_yn, (SELECT COUNT(c.con_id) FROM job_op c WHERE c.job_yn='N' AND TO_CHAR(c.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND to_char(c.job_date, 'YYYY-MM-DD') like '%" + job_date + "%' GROUP BY c.con_id HAVING c.con_id=b.con_id) AS cnt ");
			sql.append("FROM main_con a, job_op b ");
			sql.append("WHERE a.con_id=b.con_id AND a.con_year=b.con_year AND TO_CHAR(b.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND b.job_yn='N' AND TO_CHAR(b.job_date, 'YYYY-MM-DD') LIKE '" + job_date + "%' ");
			sql.append("UNION ");
			sql.append("SELECT a.cust_nm, a.proc_nm, b.job_date, b.job_nm, b.job_reason, b.job_visit_remote, b.job_yn, (SELECT COUNT(c.con_id) FROM job_op c WHERE c.job_yn='Y' AND TO_CHAR(c.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND c.job_reason LIKE '**%" + job_date + "%:%' GROUP BY c.con_id HAVING c.con_id=b.con_id) AS cnt ");
			sql.append("FROM main_con a, job_op b ");
			sql.append("WHERE a.con_id=b.con_id AND a.con_year=b.con_year AND TO_CHAR(b.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND b.job_yn='Y' AND b.job_reason LIKE '**%" + job_date + "%:%') A ");
			sql.append("ORDER BY A.cust_nm, A.job_date");

			// ���� �ʿ�
			// ���� �����ڿ� ���� -> job_reason ���� �̾ƾ� ��.
		} else if(inputJobVo.getJob_nm() != null) {
			System.out.println("inputJobVo.getJob_nm() != null");
			job_nm = inputJobVo.getJob_nm();
			System.out.println("job_nm : " + job_nm);

			sql.setLength(0);
			sql.append("SELECT A.cust_nm, A.proc_nm, A.job_date, A.job_nm, A.job_reason, A.job_visit_remote, A.job_yn, ");
			sql.append("(SELECT COUNT(B.cust_nm) FROM  (SELECT a.cust_nm, a.proc_nm, b.job_date, b.job_nm, b.job_reason, b.job_visit_remote, b.job_yn, (SELECT COUNT(c.con_id) FROM job_op c WHERE c.job_yn='N' AND TO_CHAR(c.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND c.job_nm LIKE '%" + job_nm + "%' GROUP BY c.con_id HAVING c.con_id=b.con_id) AS cnt FROM main_con a, job_op b WHERE a.con_id=b.con_id AND a.con_year=b.con_year AND TO_CHAR(b.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND b.job_yn='N' AND b.job_nm LIKE '%" + job_nm + "%' ");
			sql.append("UNION SELECT a.cust_nm, a.proc_nm, b.job_date, b.job_nm, b.job_reason, b.job_visit_remote, b.job_yn, (SELECT COUNT(c.con_id) FROM job_op c WHERE c.job_yn='Y' AND TO_CHAR(c.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND c.job_reason LIKE '%:%" + job_nm + "%**%' GROUP BY c.con_id HAVING c.con_id=b.con_id) AS cnt FROM main_con a, job_op b WHERE a.con_id=b.con_id AND a.con_year=b.con_year AND TO_CHAR(b.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND b.job_yn='Y' AND b.job_reason LIKE '%:%" + job_nm + "%**%') AS B WHERE B.cust_nm=A.cust_nm) AS cnt ");
			sql.append("FROM (SELECT a.cust_nm, a.proc_nm, b.job_date, b.job_nm, b.job_reason, b.job_visit_remote, b.job_yn, (SELECT COUNT(c.con_id) FROM job_op c WHERE c.job_yn='N' AND TO_CHAR(c.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND c.job_nm LIKE '%" + job_nm + "%' GROUP BY c.con_id HAVING c.con_id=b.con_id) AS cnt ");
			sql.append("FROM main_con a, job_op b ");
			sql.append("WHERE a.con_id=b.con_id AND a.con_year=b.con_year AND TO_CHAR(b.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND b.job_yn='N' AND b.job_nm LIKE '%" + job_nm + "%' ");
			sql.append("UNION ");
			sql.append("SELECT a.cust_nm, a.proc_nm, b.job_date, b.job_nm, b.job_reason, b.job_visit_remote, b.job_yn, (SELECT COUNT(c.con_id) FROM job_op c WHERE c.job_yn='Y' AND TO_CHAR(c.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND c.job_reason LIKE '%:%" + job_nm + "%**%' GROUP BY c.con_id HAVING c.con_id=b.con_id) AS cnt ");
			sql.append("FROM main_con a, job_op b ");
			sql.append("WHERE a.con_id=b.con_id AND a.con_year=b.con_year AND TO_CHAR(b.job_date, 'YYYY-MM-DD') LIKE '" + year + "%' AND b.job_yn='Y' AND b.job_reason LIKE '%:%" + job_nm + "%**%') A ");
			sql.append("ORDER BY A.cust_nm, A.job_date");

			// ���� ���� �Ϸ��ڿ� ����
		} else if(inputJobVo.getReg_date() != null) {
			System.out.println("inputJobVo.getReg_date() != null");
			job_nm = inputJobVo.getReg_date();
			System.out.println("reg_date : " + job_nm);

			sql.append("(SELECT COUNT(c.con_id) FROM job_op c WHERE c.job_yn='Y' and to_char(c.job_date, 'YYYY-MM-DD') like '" + year + "%' AND c.job_nm like '%" + job_nm + "%' GROUP BY c.con_id HAVING c.con_id=b.con_id) AS cnt ");
			sql.append("FROM main_con a, job_op b ");
			sql.append("WHERE a.con_id=b.con_id AND a.con_year=b.con_year AND b.job_yn='Y' and to_char(b.job_date, 'YYYY-MM-DD') like '" + year + "%' ");
			sql.append("AND b.job_nm like '%" + job_nm + "%' ");
			sql.append("ORDER BY a.con_id DESC, job_date");

			// ���� ���� �Ϸ� ��¥�� ����
		} else if(inputJobVo.getUpd_date() != null) {
			System.out.println("inputJobVo.getUpd_date() != null");
			job_date = inputJobVo.getUpd_date();
			System.out.println("upd_date : " + job_date);

			sql.append("(SELECT COUNT(c.con_id) FROM job_op c WHERE c.job_yn='Y' and to_char(c.job_date, 'YYYY-MM-DD') like '" + year + "%' AND to_char(c.job_date, 'YYYY-MM-DD') like '%" + job_date + "%' GROUP BY c.con_id HAVING c.con_id=b.con_id) AS cnt ");
			sql.append("FROM main_con a, job_op b ");
			sql.append("WHERE a.con_id=b.con_id AND a.con_year=b.con_year AND b.job_yn='Y' and to_char(b.job_date, 'YYYY-MM-DD') like '" + year + "%' ");
			sql.append("AND to_char(b.job_date, 'YYYY-MM-DD') like '%" + job_date + "%' ");
			sql.append("ORDER BY a.con_id DESC, job_date");
		} else {
			System.out.println("else");
			sql.append("(SELECT COUNT(c.con_id) FROM job_op c WHERE c.job_yn!='C' and to_char(c.job_date, 'YYYY-MM-DD') like '" + year + "%' GROUP BY c.con_id HAVING c.con_id=b.con_id) AS cnt ");
			sql.append("FROM main_con a, job_op b ");
			sql.append("WHERE a.con_id=b.con_id AND a.con_year=b.con_year AND b.job_yn!='C' and to_char(b.job_date, 'YYYY-MM-DD') like '" + year + "%' ");
			sql.append("ORDER BY a.con_id DESC, job_date");
		}

		try {
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while(rs.next()) {
				String[] temp = null;
				String originDate = new String();
				String originNm = new String();
				if(rs.getString(7).equals("Y")) {
					System.out.println("cust_nm : " + rs.getString(1) + ", job_reason : " + rs.getString(5));
					temp = MakeModifyReason.getDateAndName(rs.getString(5)).split(":");
					originDate = temp[0];
					originNm = temp[1];
				}
				JobOpVo jobVo = new JobOpVo();
				jobVo.setCon_id(rs.getString(1));	// con_id �� cust_nm�� ����
				jobVo.setModify_yn(rs.getString(2));	// modify_yn �� proc_nm�� ����
				jobVo.setJob_date(rs.getString(3));	// ���� ������ �Ϸ�� ��¥
				jobVo.setJob_nm(rs.getString(4));	// ���� ������
				jobVo.setJob_reason(MakeModifyReason.getWithoutRollbackData(rs.getString(5)));
				jobVo.setJob_visit_remote(ReplaceData.replaceVandR(rs.getString(6)));	// �湮���� ��������
				jobVo.setJob_yn(rs.getString(7));	// �Ϸ����� �ƴ���
				jobVo.setCon_year(rs.getString(8));	// �ش� �۾��� ���ڵ� ��
				jobVo.setUpd_date(originDate);	// ������ ���� ��¥
				jobVo.setReg_date(originNm);	// ������ ������

				jobList.add(jobVo);
			}
			outputMap.put("jobLIst", jobList);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return outputMap;
	}

	@Override
	public int deletePlan(JobOpVo vo) {
		System.out.println("\n** deletePlan() **");
		int result = 0;
		Connection conn = new RegchedbConnectionManager().getConnection();;
		String con_id = vo.getCon_id();
		String con_year = vo.getCon_year();
		String job_date = vo.getJob_date();
		int month = Integer.parseInt(job_date.substring(5, 7));
		System.out.println("con_id : " + con_id + "\tcon_year" + con_year + "\tjob_date : " + job_date);

		// logging ��ü ����
		CheckupLogging logging = new CheckuploggingImpl();
		MainConSubVo updateVo = new MainConSubVo();
		updateVo.setCon_id(con_id);
		updateVo.setCon_year(con_year);
		MainConSubVo beforeUpdateVo = logging.beforeUpdatedVo(con_id, con_year);
		updateVo.setCust_nm(beforeUpdateVo.getCust_nm());
		updateVo.setProc_nm(beforeUpdateVo.getProc_nm());


		try {
			// 1. job_op ���̺��� ���� �ش� ���ڵ带 job_yn='C'�� ����
			System.out.println("log : update job_op start");
			String jobSql = "update job_op set job_yn='C', modify_yn='Y', upd_date=sysdate "
					+ "where con_id=? and con_year=? and to_char(job_date, 'YYYY-MM-DD') like ? and job_yn='N'";
			pstmt = conn.prepareStatement(jobSql);
			pstmt.setString(1, con_id);
			pstmt.setString(2, con_year);
			pstmt.setString(3, job_date);
			result = pstmt.executeUpdate();

			if(result == 1) {
				System.out.println("log : update job_op success");
				System.out.println("log : update cust_reg_svc start");
				// 2. cust_reg_svc ���̺��� month�� �ش��ϴ� �÷����� ���� �ʱ�ȭ
				String custSql = "update cust_reg_svc set job_date_" + month + "=null, job_nm_" + month + "=null, "
						+ "visit_cnt_" + month + "=0, remote_cnt_" + month + "=0, result_cnt_" + month + "=0, "
						+ "job_yn_" + month + "=null where con_id=? and con_year=?";
				pstmt = conn.prepareStatement(custSql);
				pstmt.setString(1, con_id);
				pstmt.setString(2, con_year);
				result = pstmt.executeUpdate();

				if(result == 1) {
					System.out.println("log : update cust_reg_svc success");
					System.out.println("log : delete plane success");
					conn.commit();
				}
			} else {
				System.out.println("update failed....");
			}
			logging.deleteAPlan(updateVo, job_date);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public List<MonthInfoVo> getCheckInfo(String year, String month) {
		ResultSet rs = null;
		List<MonthInfoVo> list = new ArrayList<MonthInfoVo>();
		System.out.println("** Month info **");
		Connection conn = new RegchedbConnectionManager().getConnection();;

		String sql = "SELECT '��ü' AS [name], (SUM(visit_cnt_" + month + ") + SUM(remote_cnt_" + month + ") - SUM( IF(job_yn_" + month + " = 'C', 1, 0) )) AS [sum], SUM( IF( job_yn_" + month + " = 'Y', 1, 0 ) ) AS [avg] "
				+ "FROM cust_reg_svc "
				+ "WHERE TO_CHAR(job_date_" + month + ", 'YYYY') = ? "
				+ "UNION ALL "
				+ "SELECT job_nm_" + month + " AS [name], (SUM(visit_cnt_" + month + ") + SUM(remote_cnt_" + month + ") - SUM( IF(job_yn_" + month + " = 'C', 1, 0) )) AS [sum], SUM( IF( job_yn_" + month + " = 'Y', 1, 0 ) ) AS [avg] "
				+ "FROM cust_reg_svc " 
				+ "WHERE TO_CHAR(job_date_" + month + ", 'YYYY') = ? "
				+ "GROUP BY job_nm_" + month + "";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, year);
			pstmt.setString(2, year);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				MonthInfoVo vo = new MonthInfoVo();
				vo.setName(rs.getString(1));
				vo.setPlan(rs.getInt(2));
				vo.setResult(rs.getInt(3));

				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	@Override
	public int deleteSite(String con_id, String con_year) {
		int result = 0;
		System.out.println("** delete site Dao info **");
		Connection conn = new RegchedbConnectionManager().getConnection();;

		// logging ��ü ����
		CheckupLogging logging = new CheckuploggingImpl();
		MainConSubVo updateVo = new MainConSubVo();
		updateVo.setCon_id(con_id);
		updateVo.setCon_year(con_year);
		MainConSubVo beforeUpdateVo = logging.beforeUpdatedVo(con_id, con_year);
		updateVo.setCust_nm(beforeUpdateVo.getCust_nm());
		updateVo.setProc_nm(beforeUpdateVo.getProc_nm());

		// 1. job_op ����
		String sql = "delete from job_op where con_id=? and con_year=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, con_id);
			pstmt.setString(2, con_year);

			System.out.println("delete job_op : " + pstmt.executeUpdate());

			// 2. cust_reg_svc ����
			sql = "delete from cust_reg_svc where con_id=? and con_year=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, con_id);
			pstmt.setString(2, con_year);

			System.out.println("delete from cust_reg_svc : " + pstmt.executeUpdate());

			// 3. main_con ����
			sql = "delete from main_con where con_id=? and con_year=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, con_id);
			pstmt.setString(2, con_year);

			result = pstmt.executeUpdate();

			if(result == 1) {
				System.out.println("delete success");
				conn.commit();
				logging.deleteSelectedSite(updateVo);
			} else {
				System.out.println("delete main_con fail : " + result);
			}


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public int insertYear() {
		ResultSet rs = null;
		int result = 0;
		System.out.println("** insertYear Dao info ** ");
		Connection conn = new RegchedbConnectionManager().getConnection();;
		String getMaxYear = "SELECT MAX(con_year) FROM main_con";
		String sql = "INSERT INTO year_tbl (reg_year) VALUES(?)";
		String year = null;
		try {
			pstmt = conn.prepareStatement(getMaxYear);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				year = rs.getString(1);
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, year);
				result = pstmt.executeUpdate();
				if(result > 0) {
					conn.commit();
					CheckupLogging logging = new CheckuploggingImpl();
					logging.insertNewYear(year);
				}
				System.out.println("curYear : " + year);
			}
		} catch (SQLException e) {
			if(e.getErrorCode() == -670) System.out.println(year + "�⵵�� �̹� ����Ǿ� �ֽ��ϴ�."); 
			else e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public ArrayList<String> selectYear() {
		ResultSet yearRs = null;
		ArrayList<String> yearArray = new ArrayList<String>();
		Connection conn = new RegchedbConnectionManager().getConnection();;
		String sql = "SELECT reg_year FROM year_tbl";

		try {
			pstmt = conn.prepareStatement(sql);
			yearRs = pstmt.executeQuery();

			while(yearRs.next()) {
				yearArray.add(yearRs.getString(1));
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(yearRs != null) yearRs.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return yearArray;
	}

	@Override
	public String idFromJira(String name) {
		ResultSet idRs = null;
		Connection idConn = new RegchedbConnectionManager().getConnection();;
		String sql = "select cub_name from cub_member where jira_id=?";
		String returnName = "";
		try {
			pstmt = idConn.prepareStatement(sql);
			pstmt.setString(1, name);
			idRs = pstmt.executeQuery();
			if(idRs.next()) returnName = idRs.getString(1);
			idConn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(idRs != null) idRs.close();
				if(idConn != null) idConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("** jira name : " + returnName + " **");
		return returnName;
	}

	@Override
	public List<CubMemberVo> selectMember(String type) {
		List<CubMemberVo> memberList = new ArrayList<CubMemberVo>();
		Connection memberConn = new RegchedbConnectionManager().getConnection();;
		ResultSet rs = null;
		String sql = "SELECT jira_id, cub_name, email_addr, reg_date, show_yn FROM cub_member";
		
		if("Y".equals(type))
			sql += " WHERE show_yn = 'Y' ORDER BY cub_name";
		else if("A".equals(type))
			sql += " WHERE show_yn != 'D' ORDER BY cub_name";
		
		try {
			pstmt = memberConn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				if(rs.getString(1).equals("cubridall") || rs.getString(1).equals("admin") || rs.getString(1).equals("�������")) continue;
				CubMemberVo vo = new CubMemberVo();
				vo.setJira_id(rs.getString(1));
				vo.setCub_name(rs.getString(2));
				vo.setEmail_addr(rs.getString(3));
				vo.setReg_date(rs.getString(4));
				vo.setShow_yn(rs.getString(5));
				memberList.add(vo);
			}
			memberConn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(memberConn != null) memberConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return memberList;
	}

	@Override
	public int updateCubMember(List<CubMemberVo> list) {
		int result = 0;
		
		String sql = "UPDATE cub_member SET show_yn = ? WHERE jira_id = ?";
		Connection memberConn = new RegchedbConnectionManager().getConnection();
		
		try {
			pstmt = memberConn.prepareStatement(sql);
			for(CubMemberVo vo : list) {
				System.out.println("id : " + vo.getJira_id() + "\tshow : " + vo.getShow_yn());
				pstmt.setString(1, vo.getShow_yn());
				pstmt.setString(2, vo.getJira_id());
				result += pstmt.executeUpdate();
			}
			if(result > 0) memberConn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(memberConn != null) memberConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

}
