package jdbc;

import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 测试CLOB 文本大对象的使用
 * 
 * 包含：将字符串、文件内容插入数据库中的CLOB字段、将CLOB字段值取出来的操作。
 * 
 * @author beeworkshop
 *
 */
public class DBCLOB {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Reader r = null;
		try {
			// 加载驱动类
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_student", "root", "123456");

//			ps = conn.prepareStatement("insert into t_users (username,info) values (?,?) ");
//			ps.setString(1, "王英");
			// 将文本文件内容直接输入到数据库中
//			ps.setClob(2, new FileReader(new File("E:/tmp/log/log_warn.log")));
			// 将程序中的字符串输入到数据库的CLOB字段中
//			ps.setClob(2,
//					new BufferedReader(new InputStreamReader(new ByteArrayInputStream("一场风花雪月之事abc".getBytes()))));

//			ps.executeUpdate();
//			System.out.println("插入一条CLOB");

			ps = conn.prepareStatement("select * from t_users where id=?");
			ps.setObject(1, 2);

			rs = ps.executeQuery();
			while (rs.next()) {
				Clob c = rs.getClob("info");
				r = c.getCharacterStream();
				int temp = 0;
				while ((temp = r.read()) != -1) {
					System.out.print((char) temp);
				}
				System.out.println();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (r != null) {
					r.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

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