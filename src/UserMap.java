import java.util.HashMap;
import java.util.Map;

public class UserMap {
	private Map<String, User> map;
	
	public UserMap(){
		map = new HashMap<String, User>();
	}
	
	public void addUser(String id){
		User user = new User(id);
		map.put(id, user);
	}
	
	public int Count(){
		return map.size();
	}
	
	public void removeUser(String id){
		map.remove(id);
	}
	
	public boolean contains(String id){
		return map.containsKey(id);
	}
	
	public User get(String id){
		return map.get(id);
	}
	
	
	
}
