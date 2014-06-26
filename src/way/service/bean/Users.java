package way.service.bean;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by LiCheng on 2014/6/23.
 */
public class Users {
    private ArrayList<User> users = new ArrayList<User>();
    
    public void addUser(User user){
        users.add(user);
    }
    public void clear(){
        users.clear();
    }
    
    public ArrayList<User> getList(){
    	return users;
    }
    
    public int size(){
        return users.size();
    }
    
    @Override
    public String toString(){
    	StringBuilder s = new StringBuilder("");
    	for(Iterator<User> it = users.iterator(); it.hasNext();){
    		s.append(it.next().toString() + "\n");
    	}
    	return s.toString();
    }
}
