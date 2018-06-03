package Auth.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import Auth.Module.User;

public class DummyService {

	private Map<Long, User> data = DummyData.getData();
	private Long nextKey = DummyData.getNextKey();

	public DummyService() {
		if(data.isEmpty()) {
			System.out.println("init class");
			this.adddata(new User(0L,"Adam","adam64","pass123"));
			this.adddata(new User(0L,"Ben","ben123","secure3"));
			this.adddata(new User(0L,"Criss","criss456","super555"));
		}else {
			System.out.println("\n"+data);
		}
		
		
	}
	
	public ArrayList<User> alldata() {
		System.out.println("get all data of size "+data.size());
		
		return new ArrayList<User>(data.values());
	}
	
	// CRUD operations

	// C
	public User adddata(User user) {
		
		Long id = (long)data.size();
		if(user.getId() == 0) {
			user.setId(id);
		}
		data.put(id, user);
		return user;
	}

	// R
	public User getdata(Long id) {

		return data.get(id);
	}

	// U
	public User setdata(Long id, User user) {
		if(id >= data.size()) {
			return null;
		}else {
			data.put(id, user);
			return data.get(id);
		}
		
	}

	// D
	public User removedata(Long id) {
		User deletedUser = data.remove(id);
		
		Map<Long, User> newData = new HashMap<>();
		int dataSize = data.size();
		for (int i = 0; i <= data.size(); i++) {
			if(data.get((long)i) == null) {
				System.out.println("index was deleted at: " + i);
				dataSize++;
			}else {
				System.out.println("adding User: " + data.get((long)i));
				newData.put((long)newData.size(), data.get((long)i));
			}

		}
		System.out.println("\n"+ newData+"\n");
		//this is done so that the data.size for id's still works, id's are shifted though
		data.clear();
		for (int i = 0; i < newData.size(); i++) {
			this.adddata(newData.get((long)i));
		}		
		
		return deletedUser;
	}
	
	//add padding for json responce
	public String addJsonPadding(String key, String value) {
		return "{\""+key+"\":\""+value+"\"}";
	}

	public boolean checkCredentials(String username, String password) {
		
		boolean passwordMatch = false;
		boolean userExists = false;
				
		//loop though data for a user with same name
		for (long i = 0; i < data.size(); i++) {
			
			if(username.equals(data.get(i).getUserName())){
				userExists = true;
				passwordMatch = data.get(i).getPassword().equals(password);
			}
		}
		
		if (userExists) {
			if (passwordMatch) {
				System.out.println(username + " has logged in");
				return true;
			}
			else {
				return false;

			}
		}else {
			return false;//is a user, but not valid password
		}
	}
	
	
}









