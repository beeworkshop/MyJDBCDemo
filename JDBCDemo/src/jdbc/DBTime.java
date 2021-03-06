package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;

/**
 * 测试时间处理(java.sql.Date,Time,Timestamp)
 * 
 * @author beeworkshop
 *
 */
public class DBTime {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// 加载驱动类
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_student", "root", "123456");

			for (int i = 0; i < 1000; i++) {

				// regtime=2019-12-13 lastlogintime=2019-12-13 04:16:10
				ps = conn.prepareStatement("insert into t_users (username,pwd,regtime,lastlogintime) values (?,?,?,?)");
				ps.setObject(1, "孙二娘" + i);
				ps.setObject(2, "123456");

				int rand = 100000000 + new Random().nextInt(1000000000);

				java.sql.Date date = new java.sql.Date(System.currentTimeMillis() - rand);
				Timestamp stamp = new Timestamp(System.currentTimeMillis() - rand); // 如果需要插入指定日期，可以使用Calendar、DateFormat

				ps.setDate(3, date);
				ps.setTimestamp(4, stamp);
				ps.execute();

			}

			System.out.println("插入一个用户,孙二娘");

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
