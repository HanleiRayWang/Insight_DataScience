import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;


public class Network implements Iterable<String> {
	
	/* A map containing all users and edges between users 
	 * representing the relationship (friend or not)*/
	private final Map<String, Set<String>> graph;
	
	/* A map containing all users.
	 * Key is ID, Value is the User
	 * */
	private UserMap userMap;
	
	public Network(){
		graph = new HashMap<String, Set<String>>();
		userMap = new UserMap();
	}

	
	public User getUser(String id){
		if(!userMap.contains(id))
			throw new NoSuchElementException("User not in the network!");
		return userMap.get(id);
	}
	
	/**
     * Adds a new user to the network.  
     * If the node already exists, return false and do nothing.
     *
     * @param id The user to add.
     * @return Whether or not the user was added.
     */
	public boolean addUser(String id){
		if(userMap.contains(id))
			return false;
		userMap.addUser(id);
		graph.put(id, new HashSet<String>());
		return true;
	}
	
	
	/**
     * Adds a new friendship to the network.  
     *
     * @param user1 User1.
     * @param user2 User2.
     * @throws NoSuchElementException if either user1 or user2 are not in the network
     */
	public void addEdge(String id1, String id2){
		graph.get(id1).add(id2);
		graph.get(id2).add(id1);
	}
	
	
	/**
     * Removes a friendship between two users from the network.  
     *
     * @param user1 User1.
     * @param user2 User2.
     * @throws NoSuchElementException if either user1 or user2 are not in the network
     */
	public void removeEdge(String id1, String id2){
		if(!userMap.contains(id1) || !userMap.contains(id2))
			throw new NoSuchElementException("Both Users have to be in the network!");
		graph.get(id1).remove(id2);
		graph.get(id2).remove(id1);
	}
	
	
	/**
	 * Given the id of a users, return whether that user is in the network
	 * 
	 * @param user The user.
	 * @return In the network or not.
	*/
	public boolean userExists(String id){
		return userMap.contains(id);
	}
	
	
	/**
	 * Given two user, determine whether they are friend or not.
	 * 
	 * @param user1 User1.
	 * @param user2 User2.
	 * @return User1 and user2 are friend or not.
	*/
	public boolean edgeExists(String id1, String id2){
		if(!userMap.contains(id1) || !userMap.contains(id2))
			throw new NoSuchElementException("Both Users have to be in the network!");
		return graph.get(id1).contains(id2);
	}
	
	/**
	 * Find user's all friend.
	 * @param user User
	 * @return All the users
	 */
	public Set<String> edgesFrom(String id){
		if(userMap.contains(id)==false)
			throw new NoSuchElementException("User does not exist!");
		Set<String> friends = graph.get(id);
		return Collections.unmodifiableSet(friends);
	}
	
	
	
	/**
     * Returns an iterator that can traverse the nodes in the graph.
     */
	public Iterator<String> iterator(){
		return graph.keySet().iterator();
	}
	
	
	 /**
     * Returns the number of users in the network.
     */
	public int size(){
		return graph.size();
	}
	
	
	/**
     * Returns whether the network is empty.
     */
    public boolean isEmpty() {
        return graph.isEmpty();
    }
	
    
    public String toString(){
    	return graph.toString();
    }
    

}
