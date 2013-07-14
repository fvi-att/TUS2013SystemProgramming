package Bank;

import java.util.HashMap;

public class EventManager {
	
	private static HashMap<String,NotificationCenter> map = new HashMap<String,NotificationCenter>();
	
	
	public static void Put(String key,NotificationCenter event){
		map.put(key, event);
	}
	
	public static void RemoveEvent(String key){
		map.remove(key);
		
	}
	
	public static void fireEvent(String key,Object[] obj){
		map.get(key).NotificationCallfired(obj);
	}

}
