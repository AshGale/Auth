package Auth.Data;

import java.util.HashMap;
import java.util.Map;

import Auth.Module.User;

public class DummyData {

	//this is the actual data holding part
	private static Map<Long, User> data = new HashMap<>();
	private static Long nextKey = new Long(1L);
	
	public static Map<Long, User> getData() {

		return data;
	}
	
	public static Long getNextKey() {

		return nextKey;
	}
}
