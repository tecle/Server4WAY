package way.service.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import way.service.bean.User;
import way.service.mysql.conn.MySqlConnection;

public class WayDao {
	static Logger logger = Logger.getLogger(WayDao.class.getName());

	private MySqlConnection conn;// mysql连接，提供基本的sql语句查询

	private String user_table = "users";

	private String request_table = "requests";

	private String friend_table = "friends";

	public String getUser_table() {
		return user_table;
	}

	public void setUser_table(String userTable) {
		user_table = userTable;
	}

	public String getRequest_table() {
		return request_table;
	}

	public void setRequest_table(String requestTable) {
		request_table = requestTable;
	}

	public String getFriend_table() {
		return friend_table;
	}

	public void setFriend_table(String friendTable) {
		friend_table = friendTable;
	}

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
		String sql = "select count(*) from " + user_table + " where name='"
				+ name + "' and pw='" + pwd + "'";
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
		String sql = "select count(*) from " + user_table + " where name='"
				+ name + "'";
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
		String sql = "insert into " + user_table
				+ " (name, pw, nick, sex) values ('" + user.getName() + "','"
				+ user.getPw() + "','" + user.getNick() + "','" + user.getSex()
				+ "')";
		logger.info(sql);
		return conn.executeUpdate(sql) == 1;
	}

	public Map getUserByName(String name) {// 通过用户名获取用户详细信息
		try {
			String sql = "select * from " + user_table + " where name='" + name
					+ "'";
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

	public String getIdByName(String name) {// 通过用户名获取用户id
		try {
			String sql = "select id from " + user_table + " where name='"
					+ name + "'";
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

	public Map getUserById(int id) {// 通过用户id获取用户详细信息
		try {
			String sql = "select * from " + user_table + " where id='" + id
					+ "'";
			ResultSet rs = conn.execute(sql);
			rs.next();
			if (rs == null) {
				return null;
			}
			Map<String, String> rtn = new HashMap<String, String>();
			rtn.put("name", rs.getString("name"));
			rtn.put("id", rs.getString("id"));
			rtn.put("nick", rs.getString("nick"));
			rtn.put("sex", rs.getString("sex"));
			return rtn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<User> searchUsersByNick(String nick, int start, int limit) {// 通过昵称搜索用户列表
		List<User> userList = new ArrayList<User>();
		try {
			String sql = "select * from " + user_table + " where nick like '%"
					+ nick + "%' limit " + limit + " offset " + start;
			ResultSet rs = conn.execute(sql);
			while (rs.next()) {
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

	public boolean addFriendREQ(String id, String fid) {// 新增好友请求
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		String check = String.format(
				"select count(*) from %s where reqid='%s' and destid='%s'",
				request_table, id, fid);
		try {
			ResultSet rs = conn.execute(check);
			rs.next();
			int count = rs.getInt(1);
			String sql;
			if (count == 0) {
				sql = String.format(
						"insert into %s values ('%s','%s','%s','')",
						request_table, id, fid, str);
			} else {// 如果已经添加过请求，则更新时间
				sql = String
						.format(
								"update %s set reqtime='%s' where reqid='%s' and destid='%s'",
								request_table, str, id, fid);
			}
			System.out.println(sql);
			int rtn = conn.executeUpdate(sql);
			return rtn == 1 ? true : false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public boolean isFriend(String id, String fid) {// 判断两人是否为好友
		int fid1 = Integer.valueOf(id);
		int fid2 = Integer.valueOf(fid);
		if (fid1 > fid2) {
			fid1 ^= fid2;
			fid2 ^= fid1;
			fid1 ^= fid2;
		}
		String sql = "select count(*) from " + friend_table + " where fid1='"
				+ fid1 + "' and fid2='" + fid2 + "'";
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

	public List<User> getFriendREQS(String id, int start, int limit) {// 获取请求列表
		List<User> userList = new ArrayList<User>();
		try {
			String sql = String
					.format(
							"select a.reqid as id,a.reqtime as datetime,b.nick as nick from %s as a left join %s as b on a.reqid=b.id where a.destid=%s limit %s offset %s",
							request_table, user_table, id, limit, start);
			ResultSet rs = conn.execute(sql);
			while (rs.next()) {
				User user = new User();
				user.setId(Integer.parseInt(rs.getString("id")));
				user.setNick(rs.getString("nick"));
				user.setDatetime(rs.getString("datetime"));
				userList.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}

	public boolean addFriend(String id, String fid) {// 添加好友
		int fid1 = Integer.valueOf(id);
		int fid2 = Integer.valueOf(fid);
		if (fid1 > fid2) {
			fid1 ^= fid2;
			fid2 ^= fid1;
			fid1 ^= fid2;
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		String sql = String.format("insert into %s values ('%s','%s','%s')",
				friend_table, str, fid1, fid2);
		int rtn = conn.executeUpdate(sql);
		return rtn == 1;
	}

	public boolean deleteFriendREQ(String reqid, String destid) {// 删除好友请求
		String sql = String.format(
				"delete from %s where reqid='%s' and destid='%s'",
				request_table, reqid, destid);
		int rtn = conn.executeUpdate(sql);
		return rtn == 1;
	}

	public boolean deleteFriend(String id, String fid) {// 删除好友
		int fid1 = Integer.valueOf(id);
		int fid2 = Integer.valueOf(fid);
		if (fid1 > fid2) {
			fid1 ^= fid2;
			fid2 ^= fid1;
			fid1 ^= fid2;
		}
		String sql = String.format(
				"delete from %s where fid1='%s' and fid2='%s'", friend_table,
				fid1, fid2);
		int rtn = conn.executeUpdate(sql);
		return rtn == 1;
	}

	public List<Map<String, String>> getFriends(String id, Integer start,
			Integer limit) {//获取好友列表
		String sql = String
				.format(
						"select a.fid1 as fid1,b.nick as nick1,a.fid2 as fid2, c.nick as nick2 from %s a left join %s b on a.fid1=b.id  left join %s c on a.fid2=c.id where a.fid1='%s' or a.fid2='%s' limit %s offset %s",
						friend_table, user_table, user_table, id, id, limit,
						start);
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		try {
			ResultSet rs = conn.execute(sql);
			while (rs.next()) {
				Map<String, String> m = new HashMap<String, String>();
				if (rs.getString("fid1").equals(id)) {
					m.put("id", rs.getString("fid2"));
					m.put("nick", rs.getString("nick2"));
				} else {
					m.put("id", rs.getString("fid1"));
					m.put("nick", rs.getString("nick1"));
				}
				l.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}

	public boolean updatePosition(String id, String longi, String lati) {// 更新位置信息
		String sql = String.format(
				"update %s set longi='%s', lati='%s' where id='%s'",
				user_table, longi, lati, id);
		int rtn = conn.executeUpdate(sql);
		return rtn == 1;
	}

	public Map getPosition(String id) {//获取位置信息
		String sql = String.format("select * from %s where id='%s'", user_table, id);
		logger.info(sql);
		try {
			ResultSet rs = conn.execute(sql);
			rs.next();
			if (rs == null) {
				return null;
			}
			Map<String, String> rtn = new HashMap<String, String>();
			rtn.put("id", rs.getString("id"));
			rtn.put("longi", rs.getString("longi"));
			rtn.put("lati", rs.getString("lati"));
			return rtn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}