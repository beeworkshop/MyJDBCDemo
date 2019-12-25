package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 测试使用JDBCUtil工具类来简化JDBC开发
 * 
 * @author beeworkshop
 *
 */
public class JDBCTest {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getMysqlConn();

			ps = conn.prepareStatement("insert into t_users (username) values (?)");
			ps.setString(1, "姜子牙");
			ps.executeUpdate();
			System.out.println("插入了一条数据");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, ps, conn);
		}
	}
}