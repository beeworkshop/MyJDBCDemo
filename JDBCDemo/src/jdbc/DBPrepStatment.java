package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 测试PreparedStatement的基本用法
 * 
 * @author beeworkshop
 *
 */
public class DBPrepStatment {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// 加载驱动类
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_student", "root", "123456");

			String sql = "insert into t_users (username,pwd,regtime) values (?,?,?)"; // ?占位符
			ps = conn.prepareStatement(sql);
//			ps.setString(1, "林冲"); // 参数索引是从1开始计算， 而不是0
//			ps.setString(2, "88736");
//			ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
//			ps.execute();

			// 可以使用setObject方法处理参数——通吃
			ps.setObject(1, "鲁智深");
			ps.setObject(2, "headhead");
			ps.setObject(3, new java.sql.Date(System.currentTimeMillis()));

//			ps.execute();
			System.out.println("插入1行记录");

			int count = ps.executeUpdate();
			System.out.println(count);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
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
