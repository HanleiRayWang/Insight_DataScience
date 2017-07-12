# Insight_DataScience
Anomaly Detection

There are four JAVA classes in the program: User, UserMap, Network, and Detector.
I'll first explain the data structure used in each of them, and tell the relationship between these four classes.

1. User.java
This class represents a single customer. Each customer(user) has a user ID(String), a total purchase amount(Double), and a Queue recording the T most recent purchases. Some basic methods are provided to access the basic information about the user, like getID, getTotal, getMean, and so on. 
          methods:  
          public User(String str_id)
          public void purchase(double amount, int T, Timestamp time)
          public String getID()
          public double getTotal()
          public double getMean()
          public double getSD()
          public int getQueueSize()

2. UserMap.java
This is a simple class that has a Map can link the user ID with the actual user. It also record all users appeared.
          methods:
          public UserMap()
          public void addUser(String id){
		      public int Count()
	        public void removeUser(String id)
          public boolean contains(String id)
          public User get(String id)

3. Network.java
In order to save space and have a clear look at the whole picture, I created only one network representing all relationships betwenn all users. Basically, it's an undirected graph. Each user was represented as a node. If two users are friends, there is a line drawn between these two users in the graph(Actually two lines, from A to B, and from B to A). So the "friend" and "unfriend" action can be easilly acchieved by drawing and deleting lines between nodes. There is a Map representing the whole network, and a UserMap representing all the users. Other methods provided by Network can be found in the java file and more explaination about specific methods are written in the file.
          methods:
          public Network()
          public getUser(String id)
          public boolean addUser(String id)
          public void addEdge(String id1, String id2)
          public void removeEdge(String id1, String id2)
          public boolean userExists(String id)
          public boolean edgeExists(String id1, String id2)
          public Set<String> edgesFrom(String id)
          public int size()
          public boolean isEmpty()

4. Detector.java
This is the main class. All the file processing(reading batch and stream, writing anomoly purchases) are happened here. The core method is 'process(FileWriter, Network, JSONObject)' which deal with all data provided by batch_log.json and stream_log.json. Besides, I wrote a 'getNet(String, Network)' method here to provide a better view of the social network of a specific user. You can provide the user ID to see actual social network of that user.(add parameter in auto.sh)
          methods:
          public static Map<Integer, List<String>> getNet(String id, Network network)
          public static void process(FileWriter output, Network network, JSONObject obj)
          public static Network batchProcess(String location)
          public static void streamProcess(FileWriter output, Network network, String location)
          public static void writeFlagged(FileWriter output, String id, String time, double amount, double mean, double sd)
          public static void main(String[] args)


Relation:
Detector uses Network and UserMap to creating the whole social network, Network and UserMap use User class to represnt actual user information.

Extra Library:
I used JSON to parse the input files. Please download and include the java-json.jar file when you run the program.
If you put files in a specific location when you compile and run, please change the corresponding parameters in auto.sh.

Run the program:
I created a shell script file auto.sh. Simply run the file can get the output file flagged_purchases.json.
If you want to see the Map version of a specific user's social network, please add user ID at the end of 'java' line in auto.sh and run the program again. Like this:
java -cp java-json.jar:. Detector "batch_log.json" "stream_log.json" "flagged_purchases.json" 3714
Here, 3714 is the user ID. The printout will show a Map, whose keys are the dimensions(1 to D), values are all friends that has 'key' dimenstion from user 3714.
Please also change parameters in the 'java' line to change test data files.
BATCH file locates first after 'Detector';
STREAM file locates second after 'Detector';
FLAGGED file(output) locates last.


Runtime and space:
Since all users and their information are recorded in the same network, it is really space saving and time efficient. Adding a new purchase/friend/unfriend takes O(1) time. Finding a specific user's social network within dimension D can be made by calling getNet(). I tried to avoid using DFS to find user's friends, instead, by making a intersection between users' social network, we can get the common friends or possible consumer easily.


Problems:
I have to admit that I did not fully understand how to work with the timestamp. 
I tried to use a PriorityQueue recording the recent timestamps, but I failed since it's hard to link each timestamp with its corresponding purchase amount(which was stored in the Queue), not mention about there are so many timestamps that are exactly the same. I did thought about sorting the purchases history file so that all transactions are listed according to their timestamps. However, I didn't do that. First, sorting is a easy algorithm, but will cost a lot of time; second, sorting will make the transaction file lost its meaning, especially when same user made several purchases in a same timestamp; last, when used in a real-life system, the data will always come as the timestamp grows. So I put the timestamp away and focus on other part of the program.


For more information, please contact me at 323-868-7832 or hanleiwa@usc.edu

Hanlei Wang
Jul/11/2017

(T_T)
(T_T)
(T_T)
OH SHIT! I MISUNDERSTOOD THE SYSTEM REQUIREMENT.IT REQUIRES TO GET ANOMALY PURCHASES WHEN THE AMOUNT IS BIGGER THAN MEAN+3*SD OF THE LAST T PURCHASES IN THE USER'S DTH DEGREE NETWORK. No time left for me to modify my code. Really sorry, my apology.
(T_T)
(T_T)
(T_T)
Anyway, if you still want to talk to me and know more about my passion about data science, please contact me.







