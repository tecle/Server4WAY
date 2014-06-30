package way.service.logic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import way.service.bean.User;
import way.service.dao.WayDao;

public class WayService {// 逻辑服务

	private WayDao dao;// 数据传输对象

	static private WayService service = new WayService();// 单例模式

	public WayService() {
		setDao(new WayDao());
	}

	public static WayService getService() {// 获取service
		return service;
	}

	public boolean login(String name, String pwd) {
		return dao.userLogin(name, pwd);
	}

	public boolean addUser(User user) {
		return dao.addUser(user);
	}

	public boolean userExists(String name) {
		return dao.userExist(name);
	}

	public void setDao(WayDao dao) {
		this.dao = dao;
	}

	public WayDao getDao() {
		return dao;
	}

	public Map getUserByName(String name) {
		return dao.getUserByName(name);
	}

	public String getIdByName(String name) {
		return dao.getIdByName(name);
	}

	public Map getUserById(int id) {
		return dao.getUserById(id);
	}

	public String searchUsersByNick(String nick, int start, int limit) {
		List<User> users = dao.searchUsersByNick(nick, start, limit);
		if (users.size() == 0) {
			return null;
		} else {
			StringBuilder s = new StringBuilder("[");
			for (Iterator<User> it = users.iterator(); it.hasNext();) {
				User user = it.next();
				s.append("{'nick':'" + user.getNick() + "','id':'"
						+ user.getId() + "'},");
			}
			s.deleteCharAt(s.length() - 1);
			s.append("]");
			return s.toString();
		}
	}

	public boolean addFriendREQ(String id, String fid) {
		return dao.addFriendREQ(id, fid);
	}

	public boolean isFriend(String id, String fid) {
		return dao.isFriend(id, fid);
	}

	public String getFriendREQS(String id, int start, int limit) {
		List<User> users = dao.getFriendREQS(id, start, limit);
		if (users.size() == 0) {
			return null;
		} else {
			StringBuilder s = new StringBuilder("[");
			for (Iterator<User> it = users.iterator(); it.hasNext();) {
				User user = it.next();
				s.append(String.format("{nick:'%s',id:'%s',datetime:'%s'},",
						user.getNick(), user.getId(), user.getDatetime()));
			}
			s.deleteCharAt(s.length() - 1);
			s.append("]");
			return s.toString();
		}
	}

	public boolean addFriend(String id, String fid) {// 同意添加好友
		if (isFriend(id, fid)) {
			return true;
		}
		return dao.addFriend(id, fid);
	}

	public void deleteFriendREQ(String destid, String reqid) {// 删除好友请求
		dao.deleteFriendREQ(reqid, destid);
	}

	public boolean deleteFriend(String id, String fid) {// 删除好友
		return dao.deleteFriend(id, fid);
	}

	public String getFriends(String id, Integer start, Integer limit) {
		List<Map<String, String>> friends = dao.getFriends(id, start, limit);
		if (friends.size() == 0) {
			return null;
		} else {
			StringBuilder s = new StringBuilder("[");
			for (Iterator<Map<String, String>> it = friends.iterator(); it
					.hasNext();) {
				Map<String, String> friend = it.next();
				s.append(String.format("{id: '%s', nick: '%s'},", friend.get("id"), friend.get("nick")));
			}
			s.replace(s.length()-1,s.length(), "]");
			return s.toString();
		}
	}
}