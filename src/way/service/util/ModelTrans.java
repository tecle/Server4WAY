package way.service.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import way.service.bean.*;

public class ModelTrans {

	public static Users json2Users(String usersS) {// 搜索获取用户列表时字符串转换为Users
		Users users = new Users();
		JSONArray array = JSONArray.fromObject(usersS);
		System.out.println(array.size());
		for (Iterator<JSONObject> it = array.iterator(); it.hasNext();) {
			JSONObject jobj = it.next();
			User user = new User();
			if (jobj.containsKey("id")) {
				user.setId(Integer.parseInt(jobj.getString("id")));
			}
			if (jobj.containsKey("name")) {
				user.setName(jobj.getString("name"));
			}
			if (jobj.containsKey("nick")) {
				user.setNick(jobj.getString("nick"));
			}
			if (jobj.containsKey("sex")) {
				user.setSex(jobj.getString("sex").charAt(0));
			}
			if (jobj.containsKey("datetime")) {
				user.setDatetime(jobj.getString("datetime"));
			}
			users.addUser(user);
		}
		return users;
	}

	public static User Map2User(Map<String, String> values) {// responseMsg
		// 的values转化为User
		User user = new User();
		if (values.containsKey("id")) {
			user.setId(Integer.parseInt(values.get("id")));
		}
		if (values.containsKey("name")) {
			user.setName(values.get("name"));
		}
		if (values.containsKey("nick")) {
			user.setNick(values.get("nick"));
		}
		if (values.containsKey("sex")) {
			user.setSex(values.get("sex").charAt(0));
		}
		if (values.containsKey("datetime")) {
			user.setDatetime(values.get("datetime"));
		}
		return user;
	}

	public static MActivity json2Activity(String activity) {// 搜索获取用户列表时字符串转换为Users
		MActivity act = new MActivity();
		JSONObject jobj = JSONObject.fromObject(activity);
		if (jobj.containsKey("id")) {
			act.setId(Integer.parseInt(jobj.getString("id")));
		}
		if (jobj.containsKey("name")) {
			act.setName(jobj.getString("name"));
		}
		if (jobj.containsKey("note")) {
			act.setNote(jobj.getString("note"));
		}
		if (jobj.containsKey("creator")) {
			act.setCreator(json2User(jobj.getString("creator")));
		}
		if (jobj.containsKey("members")) {
			act.setMembers(json2MapUsers(jobj.getString("members")));
		}

		return act;
	}

	public static ArrayList<MapUser> json2MapUsers(String str) {
		ArrayList<MapUser> users = new ArrayList<MapUser>();
		JSONArray array = JSONArray.fromObject(str);
		System.out.println(array.size());
		for (Iterator<JSONObject> it = array.iterator(); it.hasNext();) {
			JSONObject jobj = it.next();
			MapUser user = new MapUser();
			if (jobj.containsKey("id")) {
				user.setId(Integer.parseInt(jobj.getString("id")));
			}
			if (jobj.containsKey("name")) {
				user.setName(jobj.getString("name"));
			}
			if (jobj.containsKey("nick")) {
				user.setNick(jobj.getString("nick"));
			}
			if (jobj.containsKey("sex")) {
				user.setSex(jobj.getString("sex").charAt(0));
			}
			if (jobj.containsKey("datetime")) {
				user.setDatetime(jobj.getString("datetime"));
			}
			if (jobj.containsKey("longi") && null != jobj.getString("longi")
					&& !jobj.getString("longi").equals("null")) {
				user.setLongitude(Double.parseDouble(jobj.getString("longi")));
			}
			if (jobj.containsKey("lati") && null != jobj.getString("lati")
					&& !jobj.getString("lati").equals("null")) {
				user.setLatitude(Double.parseDouble(jobj.getString("lati")));
			}
			users.add(user);
		}
		return users;
	}

	public static User json2User(String suser) {
		User user = new User();
		JSONObject values = JSONObject.fromObject(suser);
		if (values.containsKey("id")) {
			user.setId(Integer.parseInt(values.getString("id")));
		}
		if (values.containsKey("name")) {
			user.setName(values.getString("name"));
		}
		if (values.containsKey("nick")) {
			user.setNick(values.getString("nick"));
		}
		if (values.containsKey("sex")) {
			user.setSex(values.getString("sex").charAt(0));
		}
		if (values.containsKey("datetime")) {
			user.setDatetime(values.getString("datetime"));
		}
		return user;
	}
	
	public static MActivities json2Activities(String activities){
		MActivities acts = new MActivities();
		JSONArray array = JSONArray.fromObject(activities);
		for (Iterator<JSONObject> it = array.iterator(); it.hasNext();) {
			JSONObject jobj = it.next();
			MActivity act = new MActivity();
			if (jobj.containsKey("id")) {
				act.setId(Integer.parseInt(jobj.getString("id")));
			}
			if (jobj.containsKey("name")) {
				act.setName(jobj.getString("name"));
			}
			if (jobj.containsKey("note")) {
				act.setNote(jobj.getString("note"));
			}
			if (jobj.containsKey("creator")) {
				act.setCreator(json2User(jobj.getString("creator")));
			}
			if (jobj.containsKey("members")) {
				act.setMembers(json2MapUsers(jobj.getString("members")));
			}
			acts.addActivity(act);
		}
		return acts;
	}

	public static AInvites json2Invites(String str) {
		AInvites invites = new AInvites();
		JSONArray array = JSONArray.fromObject(str);
		for (Iterator<JSONObject> it = array.iterator(); it.hasNext();) {
			JSONObject jobj = it.next();
			AInvite invite = new AInvite();
			if (jobj.containsKey("time")) {
				invite.setTime(jobj.getString("time"));
			}
			if (jobj.containsKey("invitor")) {
				invite.setInvitor(json2User(jobj.getString("invitor")));
			}
			if (jobj.containsKey("activity")) {
				invite.setActivity(json2Activity(jobj.getString("activity")));
			}
			invites.addInvite(invite);
		}
		return invites;
	}
}