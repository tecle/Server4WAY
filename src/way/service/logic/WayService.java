package way.service.logic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import netty.service.message.BinaryResponseMessage;

import way.service.bean.User;
import way.service.channel.ClientChannels;
import way.service.dao.WayDao;
import way.service.util.MsgCode;

public class WayService {// 逻辑服务

	private WayDao dao;// 数据传输对象

	static private WayService service = new WayService();// 单例模式

	public WayService() {
		setDao(new WayDao());
	}

	public static WayService getService() {// 获取service
		return service;
	}

	public boolean login(String name, String pwd) {// 用户登陆
		return dao.userLogin(name, pwd);
	}

	public boolean addUser(User user) {// 添加用户
		return dao.addUser(user);
	}

	public boolean userExists(String name) {// 用户名查重
		return dao.userExist(name);
	}

	public void setDao(WayDao dao) {
		this.dao = dao;
	}

	public WayDao getDao() {
		return dao;
	}

	public Map getUserByName(String name) {// 通过用户名获取用户
		return dao.getUserByName(name);
	}

	public String getIdByName(String name) {// 通过id获取用户名
		return dao.getIdByName(name);
	}

	public Map getUserById(int id) {// 通过id获取用户
		return dao.getUserById(id);
	}

	public String searchUsersByNick(String nick, int start, int limit) {// 通过昵称模糊查找用户
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

	public boolean addFriendREQ(String id, String fid) {// 添加好友请求
		return dao.addFriendREQ(id, fid);
	}

	public boolean isFriend(String id, String fid) {// 判断是否是好友
		return dao.isFriend(id, fid);
	}

	public String getFriendREQS(String id, int start, int limit) {// 获取好友请求的列表
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
	
	public boolean updatePosition(String id, String longi, String lati) {//更新自身位置信息
		return dao.updatePosition(id, longi, lati);
	}

	public String getFriends(String id, Integer start, Integer limit) {// 获取好友列表
		List<Map<String, String>> friends = dao.getFriends(id, start, limit);
		if (friends.size() == 0) {
			return null;
		} else {
			StringBuilder s = new StringBuilder("[");
			for (Iterator<Map<String, String>> it = friends.iterator(); it
					.hasNext();) {
				Map<String, String> friend = it.next();
				s.append(String.format("{id: '%s', nick: '%s'},", friend
						.get("id"), friend.get("nick")));
			}
			s.replace(s.length() - 1, s.length(), "]");
			return s.toString();
		}
	}

	public void pushToId(String id, BinaryResponseMessage message) {//服务器推送消息到用户
		ClientChannels.write(id, message);
	}
	
	public void pushToGroup(String id, BinaryResponseMessage message){//服务器推送消息到群组
		
	}

	public Map getPosition(String id) {//获取好友的位置
		return dao.getPosition(id);
	}

	
}