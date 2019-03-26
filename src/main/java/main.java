import java.sql.Connection;

import com.cubrid.checkup.dao.JiraConnectionManager;
import com.cubrid.checkup.dao.RegchedbConnectionManager;

public class main {
	public static void main(String[] args) {
//		// connection test
//		Connection conn = new JiraConnectionManager().getConnection();
//		if(conn != null) System.out.println(conn);
//		else System.out.println("connection fail");
		
//		Connection conn1 = new RegchedbConnectionManager().getConnection();
//		if(conn1 != null) System.out.println(conn1);
//		else System.out.println("connection fail");
//		// end connection test
	}
	
//	public static void main(String[] args) {
//		StringBuffer sql = new StringBuffer();
//		
//		sql.setLength(0);
//		sql.append("update cust_reg_svc set ");
//		for(int i = 1; i <= 12; i++) {
//			if(i > 1) sql.append(", ");
//			sql.append("job_date_" + i + "=null, job_nm_" + i + "=null, visit_cnt_" + i + "=0, remote_cnt_" + i + "=0, result_cnt_" + i + "=0, job_yn_" + i + "=null");
//		}
//		sql.append(" where con_id=? and con_seq=?");
//		
//		System.out.println(sql);
//	}
	
//	public static void main(String[] args) {
//		JiraToRegchedbServiceImpl service = new JiraToRegchedbServiceImpl();
//		Map<String, Integer> map = service.getCustomFromJira();
//		int success = map.get("success");
//		int fail = map.get("fail");
//		System.out.println("success : " + success + "\tfail : " + fail);
//	}
	
//	public static void main(String[] args) {
//		Connection conn = RegchedbConnectionManager.getConnection();
//		if(conn != null) System.out.println(conn);
//	}
}
