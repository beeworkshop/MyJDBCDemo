package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 测试Statement接口的用法，执行SQL语句，以及SQL注入问题
 * 
 * @author beeworkshop
 *
 */
public class DBStatment {
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// 加载驱动类
			Class.forName("com.mysql.jdbc.Driver");
			// 建立连接(连接对象内部其实包含了Socket对象，是一个远程的连接。比较耗时！这是Connection对象管理的一个要点！)
			// 真正开发中，为了提高效率，都会使用连接池来管理连接对象！
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_student", "root", "123456");

			stmt = conn.createStatement();

//			String name = "赵六";
//			String sql = "insert into t_student (name,age,grade) values ('" + name + "',28,'高级班')";
//			stmt.execute(sql);

			// 测试SQL注入攻击
			String id = "4 or 1=1 ";
			String sql = "delete from t_student where id=" + id; // 这里删除了数据库表中的所有记录，而不只是id=4的记录。
			stmt.execute(sql);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
