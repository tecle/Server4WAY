package way.service.logic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import way.service.bean.User;
import way.service.dao.WayDao;

public class WayService{//逻辑服务
	
	private WayDao dao;//数据传输对象
	
	static private WayService service = new WayService();//单例模式
	
	public WayService(){
		setDao(new WayDao());
	}
	
	public static WayService getService(){//获取service
		return service;
	}
	
	public boolean login(String name, String pwd){
		return dao.userLogin(name, pwd);
	}
	
	public boolean addUser(User user){
		return dao.addUser(user);
	}
	
	public boolean userExists(String name){
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
		List<User> users = dao.searchUsersByNick(nick, start,limit);
		if(users.size() == 0){
		return null;
		} else{
			StringBuilder s = new StringBuilder("[");
			for(Iterator<User> it = users.iterator(); it.hasNext();){
				User user = it.next();
				s.append("{'nick':'" + user.getNick() + "','id':'" + user.getId() + "'},");
			}
			s.deleteCharAt(s.length() - 1);
			s.append("]");
			return s.toString();
		}
	}
}