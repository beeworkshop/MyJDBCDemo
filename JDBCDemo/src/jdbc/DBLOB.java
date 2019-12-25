package jdbc;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 测试BLOB 二进制大对象的使用
 * 
 * @author beeworkshop
 *
 */
public class DBLOB {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		InputStream is = null;
		OutputStream os = null;
		try {
			// 加载驱动类
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_student", "root", "123456");

//			ps = conn.prepareStatement("insert into t_users (username,img) values (?,?) ");
//			ps.setString(1, "孟姜女");
//			ps.setBlob(2, new FileInputStream("E:/tmp/pics/20191119101234.jpg"));
//			ps.executeUpdate();
//			System.out.println("插入一条BLOB记录");

			ps = conn.prepareStatement("select * from t_users where id=?");
			ps.setObject(1, 1);

			rs = ps.executeQuery();
			while (rs.next()) {
				Blob b = rs.getBlob("img");
				is = b.getBinaryStream();
				os = new FileOutputStream("E:/tmp/孟姜女.jpg");
				int temp = 0;
				while ((temp = is.read()) != -1) {
					os.write(temp);
				}
				os.flush();
				System.out.println("输出BLOB字段");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (os != null) {
					os.close();
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