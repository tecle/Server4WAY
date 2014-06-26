package way.service.util;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import way.service.bean.User;
import way.service.bean.Users;

public class String2Model {
	public static Users json2Users(String usersS) {
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
			users.addUser(user);
		}
		return users;
	}
}