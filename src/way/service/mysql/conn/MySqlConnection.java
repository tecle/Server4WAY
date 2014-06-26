package way.service.mysql.conn;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

public class MySqlConnection {
	private final String filename = "mysql.properties";

	private String driver = "com.mysql.jdbc.Driver";

	private String url = "jdbc:mysql://localhost:3306/extjs?useUnicode=true&characterEncoding=UTF-8";

	// MySQL配置时的用户名
	private String user = "root";

	private String password = "123456";

	private Connection conn;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void closeConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MySqlConnection() {
		InputStream inputStream;
		Properties p = new Properties();
		try {
			File directory = new File("bin\\" + filename);//参数为空 
			String courseFile = directory.getCanonicalPath() ; 
			System.out.println(courseFile); 
			inputStream = new BufferedInputStream(new FileInputStream(directory));
			p.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		url = p.getProperty("url");
		user = p.getProperty("user");
		password = p.getProperty("password");
	}

	public ResultSet execute(String sql) {
		System.out.println(sql);
		ResultSet rs = null;
		try {
			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, password);

			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");

			Statement statement = conn.createStatement();

			rs = statement.executeQuery(sql);

		} catch (ClassNotFoundException e) {

			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return rs;
	}

	public int executeUpdate(String sql) {
		System.out.println(sql);
		int rs = 0;
		try {
			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, password);

			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");

			Statement statement = conn.createStatement();

			rs = statement.executeUpdate(sql);

		} catch (ClassNotFoundException e) {

			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return rs;
	}

	public static void main(String[] args) {
		MySqlConnection conn = new MySqlConnection();
	}
}