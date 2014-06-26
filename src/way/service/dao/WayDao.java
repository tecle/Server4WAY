package way.service.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import way.service.bean.User;
import way.service.mysql.conn.MySqlConnection;

public class WayDao {
	static Logger logger = Logger.getLogger(WayDao.class.getName());

	private MySqlConnection conn;// mysql连接，提供基本的sql语句查询

	public WayDao() {
		conn = new MySqlConnection();
	}

	public void setConn(MySqlConnection conn) {
		this.conn = conn;
	}

	public MySqlConnection getConn() {
		return conn;
	}

	public boolean userLogin(String name, String pwd) {// 登陆
		String sql = "select count(*) from users where name='" + name
				+ "' and pw='" + pwd + "'";
		logger.info(sql);
		try {
			ResultSet rs = conn.execute(sql);
			rs.next();
			int rtn = rs.getInt(1);
			return rtn == 0 ? false : true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean userExist(String name) {// 是否存在该user
		String sql = "select count(*) from users where name='" + name + "'";
		logger.info(sql);
		try {
			ResultSet rs = conn.execute(sql);
			rs.next();
			int rtn = rs.getInt(1);
			return rtn == 0 ? false : true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean addUser(User user) {// 新增user
		String sql = "insert into users (name, pw, nick, sex) values ('"
				+ user.getName() + "','" + user.getPw() + "','"
				+ user.getNick() + "','" + user.getSex() + "')";
		logger.info(sql);
		return conn.executeUpdate(sql) == 1 ? true : false;
	}

	public Map getUserByName(String name) {
		try {
			String sql = "select * from users where name='" + name + "'";
			ResultSet rs = conn.execute(sql);
			rs.next();
			Map<String, String> rtn = new HashMap<String, String>();
			rtn.put("name", rs.getString("name"));
			rtn.put("id", rs.getString("id"));
			rtn.put("sex", rs.getString("sex"));
			rtn.put("nick", rs.getString("nick"));
			return rtn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public String getIdByName(String name) {
		try {
			String sql = "select id from users where name='" + name + "'";
			ResultSet rs = conn.execute(sql);
			rs.next();
			String rtn = rs.getString("id");
			return rtn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Map getUserById(int id) {
		try {
			String sql = "select * from users where id='" + id + "'";
			ResultSet rs = conn.execute(sql);
			rs.next();
			if (rs == null) {
				return null;
			}
			Map<String, String> rtn = new HashMap<String, String>();
			rtn.put("name", rs.getString("name"));
			rtn.put("id", rs.getString("id"));
			rtn.put("nick", rs.getString("nick"));
			return rtn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<User> searchUsersByNick(String nick, int start, int limit) {
		List<User> userList = new ArrayList<User>();
		try {
			String sql = "select * from users where nick like '%" + nick
					+ "%' limit " + limit + " offset " + start;
			ResultSet rs = conn.execute(sql);
			while(rs.next()){
				User user = new User();
				user.setId(Integer.parseInt(rs.getString("id")));
				user.setNick(rs.getString("nick"));
				userList.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}
}