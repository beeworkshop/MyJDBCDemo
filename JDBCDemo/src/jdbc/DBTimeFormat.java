package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 测试时间处理(java.sql.Date,Time,Timestamp),取出指定时间段的数据
 * 
 * @author beeworkshop
 *
 */
public class DBTimeFormat {

	/**
	 * 将字符串代表的日期转为long数字(格式：yyyy-MM-dd hh:mm:ss)
	 * 
	 * @param dateStr
	 * @return
	 */
	public static long str2Date(String dateStr) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			return format.parse(dateStr).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 加载驱动类
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_student", "root", "123456");

			// MySQL数据库设置了大小写不敏感配置，所以字段名大小写就不敏感了。
//			ps = conn.prepareStatement("select * from t_users where regTime>? and regTime<?");
//			java.sql.Date start = new java.sql.Date(str2Date("2019-12-13 9:00:00"));
//			java.sql.Date end = new java.sql.Date(str2Date("2019-12-23 23:59:59"));
//			ps.setObject(1, start);
//			ps.setObject(2, end);

			ps = conn.prepareStatement(
					"select * from t_users where lastLoginTime>? and lastLoginTime<?  order by lastLoginTime ");
			Timestamp start = new Timestamp(str2Date("2019-12-13 9:00:00"));
			Timestamp end = new Timestamp(str2Date("2019-12-23 23:59:59"));
			ps.setObject(1, start);
			ps.setObject(2, end);

			rs = ps.executeQuery();
			while (rs.next()) { // cursor游标的起始位置在0(没有记录的位置)，一调用next()方法就指向1（第一条记录的位置）。
				System.out.println(
						rs.getInt("id") + "--" + rs.getString("username") + "--" + rs.getTimestamp("lastLoginTime"));
			}

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
