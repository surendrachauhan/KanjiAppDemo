import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class HashTestExample implements Serializable {

	public final static ReentrantLock l1 = new ReentrantLock();
	public static void main (String [] args) {
		HashSet <String> h = new HashSet<String>();
		h.add("a");
		h.add("ds");
		h.add("adsd");
		
		Object [] obj = h.toArray();
		for (int i = 0 ;i<h.size();i++) {
			System.out.println("Item "+i+" "+obj[i].toString());
		}
		
		
		HashMap <String,Integer> m = new HashMap<String,Integer>();
		m.put("a",1);
		m.put("ds",2);
		m.put("adsd",3);
		
		for(Map.Entry<String, Integer> e :  m.entrySet()) {
			System.out.println("Key : "+e.getKey()+" Value : "+e.getValue());
		}
		
		ExecutorService es = Executors.newFixedThreadPool(10);
		l1.lock();
		
		Set<String> s = Collections.synchronizedSet(h);
		Map<String,Integer> hmap = Collections.synchronizedMap(m);
	}
	
}
