
import java.util.LinkedList;
import java.util.Queue;

public class User {
	
	final private String id;
	private double total;//sum in queue
	private Queue<Double> transaction;
	
	public User(String str_id){
		id=str_id;
		total=0;
		transaction = new LinkedList<Double>();
	}
	
	public void purchase(double amount, int T){

		double old = 0.0;
		if(transaction.size()==T)
			old = transaction.poll();
		transaction.add(amount);
		total = total-old+amount;
	
	}
	
	public String getID(){
		return id;
	}
	
	public double getTotal(){
		return total;
	}
	
	public double getMean(){
		return total/transaction.size();
	}
	
	public double getSD(){
		double sum = 0;
		double mean = getMean();
		for(Double value : transaction)
			sum += Math.pow(value-mean, 2);
		return Math.sqrt(sum/transaction.size());
	}
	
	public int getQueueSize(){
		return transaction.size();
	}
    
    
}
