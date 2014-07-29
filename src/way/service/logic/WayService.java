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

	public String searchUsersByNick(String nick, int start, int limit, String id) {// 通过昵称模糊查找用户，除去自身
		List<User> users = dao.searchUsersByNick(nick, start, limit, id);
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

	public boolean updatePosition(String id, String longi, String lati) {// 更新自身位置信息
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

	public void pushToId(String id, BinaryResponseMessage message) {// 服务器推送消息到用户
		ClientChannels.write(id, message);
	}

	public void pushToGroup(String id, BinaryResponseMessage message) {// 服务器推送消息到群组

	}

	public Map getPosition(String id) {// 获取好友的位置
		return dao.getPosition(id);
	}

	public Map addActivity(String creator, String name, String note) {// 创建activity
		if (null == dao.getActivityByName(name)) {
			if (dao.addActivity(creator, name, note)) {
				return dao.getActivityByName(name);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public Map getActivityById(String id) {// 通过id获取activity
		return dao.getActivityById(id);
	}

	public Map getActivityByName(String name) {// 通过name获取activity
		return dao.getActivityByName(name);
	}

	public String activity2Json(Map<String, String> activity) {// 将activity
		// Map转化成为json字符串
		if (null == activity) {
			return "{}";
		}
		StringBuilder s = new StringBuilder("");
		s.append(String.format(
				"{'id':'%s','name':'%s','time':'%s','note':'%s','creator':",
				activity.get("id"), activity.get("name"), activity.get("time"),
				activity.get("note")));
		String creator = activity.get("creator");
		Map<String, String> cuser = this.getUserById(Integer.parseInt(creator));
		s.append(map2Json(cuser));
		s.append(",'members':[");
		List<String> members = getActivityMembers(activity.get("id"));
		for (String member : members) {
			s
					.append(map2Json(this.getUserById(Integer.parseInt(member)))
							+ ",");
		}
		s.deleteCharAt(s.length() - 1);
		s.append("]}");
		return s.toString();
	}

	private String activity2JsonNoMembers(Map<String, String> activity) {//将activity
		// Map转化成为json字符串，不带用户成员
		if (null == activity) {
			return "{}";
		}
		StringBuilder s = new StringBuilder("");
		s.append(String.format(
				"{'id':'%s','name':'%s','time':'%s','note':'%s','creator':",
				activity.get("id"), activity.get("name"), activity.get("time"),
				activity.get("note")));
		String creator = activity.get("creator");
		Map<String, String> cuser = this.getUserById(Integer.parseInt(creator));
		s.append(map2Json(cuser));
		s.append("}");
		return s.toString();
	}
	public List<String> getActivityMembers(String id) {
		return dao.getActivityMembers(id);
	}

	public String map2Json(Map<String, String> m) {// 将map转化为json
		if (null == m) {
			return "{}";
		}
		StringBuilder s = new StringBuilder("{");
		for (Iterator<String> it = m.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			s.append(String.format("'%s':'%s',", key, m.get(key)));
		}
		s.setCharAt(s.length() - 1, '}');
		return s.toString();
	}

	public boolean invite2Activity(String id, String fid, String aid) {// 添加活动申请
		return dao.invite2Activity(id, fid, aid);
	}

	public List<String> getUserActivitiesById(String id) {// 获取用户活动
		return dao.getUserActivitiesById(id);
	}

	public boolean join2Activity(String aid, String uid) {// 用户加入到活动
		if (inActivity(aid, uid)) {
			return true;
		}
		return dao.join2Activity(aid, uid);
	}

	public boolean inActivity(String aid, String uid) {// 判断用户是否在活动中
		return dao.inActivity(aid, uid);
	}

	public boolean removeActivityMember(String aid, String uid) {// 将用户移出活动
		if (inActivity(aid, uid)) {
			return dao.removeActivityMember(aid, uid);
		} else {
			return true;
		}
	}

	public List<Map<String, String>> getActivityInvites(String id) {// 获取活动邀请
		return dao.getActivityInvites(id);
	}

	public String invites2Json(List<Map<String, String>> invites) {// 将邀请list转换为json字符串
		if (invites.size() == 0) {
			return "[]";
		}
		StringBuilder s = new StringBuilder("[");
		for (Map<String, String> invite : invites) {
			s.append(invite2Json(invite) + ",");
		}
		s.setCharAt(s.length() - 1, ']');
		return s.toString();
	}

	private String invite2Json(Map<String, String> invite) {// 将邀请转换为json
		if (invite.isEmpty()) {
			return "{}";
		}
		StringBuilder s = new StringBuilder(String.format(
				"{'time':'%s', invitor:", invite.get("time")));
		s.append(map2Json(getUserById(Integer.parseInt(invite.get("reqid"))))
				+ ",'activity':");
		s.append(activity2JsonNoMembers(getActivityById(invite.get("aid"))) + "}");
		return s.toString();
	}

	public boolean isCreator(String uid, String aid) {//判断是否为活动创建者
		return dao.isCreator(uid, aid);
	}

	public void deleteActivityInvite(String aid, String uid) {//删除活动邀请
		dao.deleteActivityInvite(aid, uid);		
	}
}