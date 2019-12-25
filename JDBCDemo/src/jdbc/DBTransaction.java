package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 测试数据库事务
 * 
 * @author beeworkshop
 *
 */
public class DBTransaction {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		try {
			// 加载驱动类
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_student", "root", "123456");

			conn.setAutoCommit(false); // JDBC中默认是true，自动提交事务

			ps1 = conn.prepareStatement("insert into t_users (username,pwd) values (?,?)");
			ps1.setObject(1, "武松");
			ps1.setObject(2, "123456");
			ps1.execute();
			System.out.println("插入一个用户,武松");

			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			ps2 = conn.prepareStatement("insert into t_users (username,pwd) values (?,?,?)");
			ps2.setObject(1, "卢俊义");
			ps2.setObject(2, "123456");
			ps2.execute();
			System.out.println("插入一个用户,卢俊义");

			conn.commit();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			try {
				conn.rollback(); // 回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps1 != null) {
					ps1.close();
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