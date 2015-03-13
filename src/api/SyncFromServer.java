package api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import calendar.Calendar;
import calendar.Group;

public class SyncFromServer {
	
	
	public static void sync(Calendar c) throws IOException {
		List<Group> g = getGroups(c);
		getAppointments(c, g);
	}
	
	public static List<Group> getGroups(Calendar c) throws IOException {
		JSONObject o = new JSONObject();
		o.put("command", "get_from_user");
		o.put("uid", c.getLoggedInUser().getId());
		
		JSONObject res = API.call("/group", o, c.getSession());
		JSONArray gidArray = res.getJSONArray("gid");
		List<Group> g = new ArrayList<Group>();
//		for (int i=0; i<gidList.length(); i++) {
//			int id = (int)gidList.get(i);
//			JSONObject obj = new JSONObject();
//			obj.put("command", arg1)
//		}
		
		c.getLoggedInUser().setGroups(g);
		
		return g;
	}
	
	public static void getAppointments(Calendar c, List<Group> g) throws IOException {
		JSONObject o = new JSONObject();
		o.put("command", "");
		
		JSONObject res = API.call("/appointment", o, c.getSession());
		
	}

}
