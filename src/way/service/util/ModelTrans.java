package way.service.util;

import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import way.service.bean.User;
import way.service.bean.Users;

public class ModelTrans {

	public static Users json2Users(String usersS) {// 搜索获取用户列表时字符串转换为Users
		Users users = new Users();
		JSONArray a = JSONArray.fromObject("[]");
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
			if (jobj.containsKey("longi")) {
				user.setLongi(Double.parseDouble(jobj.getString("longi")));
			}
			if (jobj.containsKey("lati")) {
				user.setLati(Double.parseDouble(jobj.getString("lati")));
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
		if (values.containsKey("longi")) {
			user.setLongi(Double.parseDouble(values.get("longi")));
		}
		if (values.containsKey("lati")) {
			user.setLati(Double.parseDouble(values.get("datetime")));
		}
		return user;
	}
}