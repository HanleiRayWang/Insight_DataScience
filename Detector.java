import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONObject;


public class Detector {
	
	private static int D=1;
	private static int T=2;
	
	
	public static Map<Integer, List<String>> getNet(String id, Network network){
		Map<Integer,List<String>> net = new HashMap<Integer,List<String>>();
		Set<String> ID = new HashSet<String>();
		ID.add(id);
		Set<String> dic = new HashSet<String>();
		dic.add(id);
		for(int i=1;i<=D;i++){
			List<String> children = new ArrayList<String>();
			for(String id_temp : ID){
				Set<String> set = network.edgesFrom(id_temp);
				for(String str : set)
					if(!dic.contains(str)){
						children.add(str);
						dic.add(str);
					}
			}
			ID.clear();
			ID.addAll(children);
			net.put(i, children);
		}
		
		return net;
	}
	

	public static void process(FileWriter output, Network network, JSONObject obj) throws Exception{
		String str;
		str = obj.getString("event_type");
		switch(str){
			case "purchase":
				String id = obj.getString("id");
				if(!network.userExists(id))
					network.addUser(id);
				User user = network.getUser(id);
				double amount = obj.getDouble("amount");
				if(output!=null && user.getQueueSize()>=2){
					double mean = user.getMean();
					double sd = user.getSD();
					if(amount>mean+3*sd)
						writeFlagged(output,id,obj.getString("timestamp"),amount,mean,sd);
				}
				user.purchase(amount,T);
				break;
			case "befriend":
				String id1 = obj.getString("id1");
				String id2 = obj.getString("id2");
				if(!network.userExists(id1))
					network.addUser(id1);
				if(!network.userExists(id2))
					network.addUser(id2);
				network.addEdge(id1,id2);
				break;
			case "unfriend":
				String un_id1 = obj.getString("id1");
				String un_id2 = obj.getString("id2");
				network.removeEdge(un_id1,un_id2);
				break;
			default:
				break;
		}
	}
	
	public static Network batchProcess(String location) throws Exception{
		File file = new File(location);
		Scanner scanner = new Scanner(file);
		Network network = new Network();
		JSONObject obj = new JSONObject(scanner.nextLine().trim());
		D = obj.getInt("D");
		T = obj.getInt("T");
		obj=new JSONObject(scanner.nextLine().trim());
		while(scanner.hasNextLine()){
			process(null, network, obj);
			obj = new JSONObject(scanner.nextLine().trim());
		}
		process(null, network, obj);
		scanner.close();
		return network;
	}
	
	
	public static void streamProcess(FileWriter output, Network network, String location) throws Exception{
		File file = new File(location);
		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine()){
			String line = scanner.nextLine().trim();
			int len = line.length();
			if(line.length()>2 && line.charAt(0)=='{' && line.charAt(len-1)=='}'){
				JSONObject obj = new JSONObject(line);
				process(output, network,obj);
			}
		}
		scanner.close();
	}
	
	public static void writeFlagged(FileWriter output, String id, String time, double amount, double mean, double sd) throws Exception{
		JSONObject obj = new JSONObject();
		obj.put("event_type", "purchase");
		obj.put("id", id);
		obj.put("timestamp", time);
		obj.put("amount", amount);
		DecimalFormat df = new DecimalFormat("#.00");
		obj.put("mean", df.format(mean));
		obj.put("sd",df.format(sd));
		System.out.println(obj.toString());
		try{
			output.write(obj.toString());
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) throws Exception{
        
		FileWriter output = new FileWriter(args[2]);
		
		Network network = null;

		network = batchProcess(args[0]);
		
		streamProcess(output, network, args[1]);
		output.flush();
		output.close();
		
        if(args.length>3){
			for(int i=3;i<args.length;i++){
				Map<Integer, List<String>> map = getNet(args[i],network);
				for(Map.Entry<Integer, List<String>> entry : map.entrySet()){
					System.out.println("Dimension: "+entry.getKey()+", Friends: "+entry.getValue());
				}
			}
		}
	}
    
}