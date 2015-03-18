package calendar;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import newAppointment.Room;

import org.json.JSONObject;

import api.API;

public class Appointment extends shared.Appointment {

	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private int[] overlap = new int[2];
	private boolean opened=false;
	private boolean admin;
	private Day day;
	private Calendar calendar;
	private String attending;
	private List<String> roomList;
	private int roomCapacity;
	private HashMap<User, Boolean> users = new HashMap<User, Boolean>();
	private Appointment prev;
	private Appointment next;
	private List<Group> groups = new ArrayList<Group>();
	private boolean active;
	private boolean personal;
	
	private Room thaRoom;
	
	public Appointment(Calendar c) {
		super();
		//hahahahhahahahah:D		
		calendar = c;
		prev = null;
		next = null;		
	}

	public void addAppointmentToDay() {
		if (getDay() != null) {
			day.removeAppointment(this);
		}
		boolean added = false;
		if (calendar.getCurrentDate().getYear() == date.getYear() && calendar.getCurrentDate().getMonthValue() == date.getMonthValue()) {
			this.day = calendar.getCurrentMonth().getDay(date.getDayOfMonth());
			day.addAppointment(this);
			added = true;
		} else {
			for (Month month : calendar.getMonths()) {
				if (month.getYear() == date.getYear() && month.getMonthValue().getMonth()==date.getMonth()) {
					this.day = month.getDay(date.getDayOfMonth());
					day.addAppointment(this);
					added = true;
				}
			}
		}
		if (added == false) {
			if (date.isBefore(calendar.getMonths().get(0).getDay(1).getDate())) {
				int i = 0;
				if (date.getYear() == calendar.getMonths().get(0).getYear()) {
					i = calendar.getMonths().get(0).getDay(1).getDate().getMonthValue() - date.getMonthValue();
				} else {
					i = date.getMonthValue() + (calendar.getMonths().get(0).getYear() - date.getYear() - 1)*12 + (12 - calendar.getMonths().get(0).getDay(1).getDate().getMonthValue());
				}
				calendar.addPastMonths(i);
				this.day = calendar.getMonths().get(0).getDay(date.getDayOfMonth());
				day.addAppointment(this);
			} else {
				int i = 0;
				if (date.getYear() == calendar.getMonths().get(calendar.getMonths().size()-1).getYear()) {
					i = date.getMonthValue() - calendar.getMonths().get(calendar.getMonths().size()-1).getMonthValue().getMonthValue();
				} else {
					i = (12-(calendar.getMonths().get(calendar.getMonths().size()-1).getMonthValue().getMonthValue()))
							+(date.getMonthValue())
							+(12*((date.getYear()-1)-(calendar.getMonths().get(calendar.getMonths().size()-1).getYear())));
				}
				calendar.addFutureMonths(i);
				this.day = calendar.getMonths().get(calendar.getMonths().size()-1).getDay(date.getDayOfMonth());
				day.addAppointment(this);
			}
		}
	}

	public void setData(Calendar calendar) {
		if (calendar != null) {
			this.calendar = calendar;
		}
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setDescription(String d) {
		if (descriptionIsValid(d)) {
			description = d;
			sync();
			if (prev!=null) {
				if (prev.getDescription()!=d) {
					prev.setDescription(d);
				}
			}
			if (next!=null) {
				if (next.getDescription()!=d) {
					next.setDescription(d);
				}
			}
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDate(LocalDate d) {
		if (dateIsValid(d)) {
			date = d;
			sync();
		}
	}

	public LocalDate getDate() {
		return date;
	}


	public void setStartTime(LocalTime t) {
		if (startTimeIsValid(t)) {
			startTime = t;
			sync();
		}
	}

	public LocalTime getStartStartTime() {
		if (prev==null) {
			return startTime;
		}
		else {
			return prev.getStartStartTime();
		}
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}

	public void setEndTime(LocalTime t) {
		if (endTimeIsValid(t)) {
			endTime = t;
			sync();
		}
	}

	public LocalTime getEndEndTime() {
		if (next==null) {
			return endTime;
		}
		else {
			return next.getEndEndTime();
		}
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}

	public void setLocation(String l) {
		if (locationIsValid(l)) {
			location = l;
			sync();
			if (prev!=null) {
				if (prev.getLocation()!=l) {
					prev.setLocation(l);
				}
			}
			if (next!=null) {
				if (next.getLocation()!=l) {
					next.setLocation(l);
				}
			}
		}
	}

	public String getLocation() {
		return location;
	}

	public void setUsers(List<User> p) {
		if (p!=null) {
			for (User participant : p) {
				if (participantIsValid(participant)) {
					users.put(participant, false);
					if (prev!=null) {
						if (!prev.getUsers().contains(participant)) {
							prev.addUser(participant);
						}
					}
					if (next!=null) {
						if (!next.getUsers().contains(participant)) {
							next.addUser(participant);
						}
					}
				}
			}
		}
		else {
			users=null;
			if (prev!=null) {
				if (prev.getUsers()!=null) {
					prev.setUsers(null);
				}
			}
			if (next!=null) {
				if (next.getUsers()!=null) {
					next.setUsers(null);
				}
			}
		}
		sync();
	}
	
	public void addUser(User user) {
		users.put(user, false);
		sync();
		if (prev!=null) {
			if (!prev.getUsers().contains(user)) {
				prev.addUser(user);
			}
		}
		if (next!=null) {
			if (!next.getUsers().contains(user)) {
				prev.addUser(user);
			}
		}
	}
	

	public List<User> getUsers() {
		List<User> userList= new ArrayList<User>();
		for (User u : users.keySet()) {
			userList.add(u);
		}
		return userList;
	}

	private boolean descriptionIsValid(String d) {
		return true;
	}

	private boolean dateIsValid(LocalDate d) {
		if (LocalDate.now().isAfter(d)) {
			return false;
		}
		return true;
	}


	private boolean startTimeIsValid(LocalTime t) {
		if (endTime!=null) {
			if (t.isAfter(endTime)) {
				return false;
			}
		}
		return true;
	}

	private boolean endTimeIsValid(LocalTime t) {
		if (startTime!=null) {
			if (startTime.isAfter(t)) {
				return false;
			}
		}
		return true;
	}

	private boolean locationIsValid(String l) {
		return true;
	}

	private boolean participantIsValid(User participant) {
		return true;
	}

	public void calculateOverlap() {
		day.calculateOverlap();
	}
	
	public int[] getOverlap() {
		return overlap;
	}
	
	public void clearOverlap() {
		overlap[0]=0;
		overlap[1]=0;
	}
	
	
	public void addOverlap(int[] i) {
		overlap[0]+=i[0];
		overlap[1]+=i[1];
	}


	public int getID() {
		return id;
	}

	public boolean getOpened() {
		return opened;
	}

	public void setOpened(boolean b) {
		opened = b;
		sync();
		if (prev!=null) {
			if (prev.getOpened()!=b) {
				prev.setOpened(b);
			}
		}
		if (next!=null) {
			if (next.getOpened()!=b) {
				next.setOpened(b);
			}
		}
	}

	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean b) {
		admin = b;
		sync();
		if (prev!=null) {
			if (prev.getAdmin()!=b) {
				prev.setAdmin(b);
			}
		}
		if (next!=null) {
			if (next.getAdmin()!=b) {
				next.setAdmin(b);
			}
		}

	}

	public void setAttending(String s) {
		if (s=="Y" || s=="N" || s=="None" || s=="notAnswered") {
			attending=s;
			if (s=="Y") {
				users.replace(calendar.getLoggedInUser(), true);
			}
			else if (s=="N") {
				users.replace(calendar.getLoggedInUser(), false);
			}
			else if (s=="notAnswered") {
				users.replace(calendar.getLoggedInUser(), null);
			}
			sync();
		}
		if (prev!=null) {
			if (prev.getAttending()!=s) {
				prev.setAttending(s);
			}
		}
		if (next!=null) {
			if (next.getAttending()!=s) {
				next.setAttending(s);
			}
		}

	}
	
	public String getAttending() {
		return attending;
	}
	
	public Day getDay() {
		return day;
	}
	
	public void setCapacity(int capacity) {
		
		this.roomCapacity = capacity;
		sync();
		if (prev!=null) {
			if (prev.getCapacity()!=capacity) {
				prev.setCapacity(capacity);
			}
		}
		if (next!=null) {
			if (next.getCapacity()!=capacity) {
				next.setCapacity(capacity);
			}
		}

	}
	public int getCapacity() {
		
		return roomCapacity;
		
	}
	
	public void setRoomList(List<String> roomList)
	{
		//show only rooms that matches user specified capacity
		
		List<Integer> checkCapList = new ArrayList<Integer>();
		
		for(int i = 0; i < roomList.size(); i++)
		{
			      
			String thisRoom = roomList.get(i);
			thisRoom = thisRoom.replaceAll("[^0-9]+", " ");
			int specifiedRoomCap = Integer.parseInt(Arrays.asList(thisRoom.trim().split(" ")).get(0)); //det e baerre lekkert!
			if(getCapacity() <= specifiedRoomCap)
			{
				checkCapList.add(specifiedRoomCap);
			}
			System.out.println(specifiedRoomCap);
		}	
		
	
		this.roomList = roomList; 
		sync();
		
		if (prev!=null) {
			if (prev.getRoomList()!=roomList) {
				prev.setRoomList(roomList);
			}
		}
		if (next!=null) {
			if (next.getRoomList()!=roomList) {
				next.setRoomList(roomList);
			}
		}

		 
	}
	
	public List<String> getRoomList(){
		return roomList;
	}

	public boolean getUserAttending(User p) {
		return users.get(p);
	}
	
	public void setUserAttending(User p, boolean b) {
		if (users.get(p)!=b) {
			users.replace(p, !b, b);
			sync();
			if (prev!=null) {
				if (prev.getUserAttending(p)!=b) {
					prev.setUserAttending(p, b);
				}
			}
			if (next!=null) {
				if (next.getUserAttending(p)!=b) {
					next.setUserAttending(p, b);
				}
			}
		}

	}
	
	public Appointment getPrev() {
		return prev;
	}
	
	public Appointment getNext() {
		return next;
	}
	
	public void setPrev(Appointment a) {
		prev = a;
		sync();
	}
	
	public void setNext(Appointment a) {
		next = a;
		sync();
	}
	
	
	public LocalDate getStartDate() {
		if (prev==null) {
			return date;
		}
		else {
			return prev.getStartDate();
		}
	}
	
	public LocalDate getEndDate() {
		if (next==null) {
			return date;
		}
		else {
			return next.getEndDate();
		}
	}
	
	public void delete() throws IOException {
		if (prev!=null) {
			prev.setNext(null);
			prev.delete();
		}
		if (next!=null) {
			next.setPrev(null);
			next.delete();
		}
		day.removeAppointment(this);
		JSONObject o = new JSONObject();
		o.put("command", "delete");
		o.put("aid", id);
		API.call("/appointment", o, calendar.getSession());
	}
	
	public void addGroup(Group g) {
		groups.add(g);
		if (prev!=null) {
			if (!prev.getGroups().contains(g)) {
				prev.addGroup(g);
			}
		}
		if (next!=null) {
			if (!next.getGroups().contains(g)) {
				next.addGroup(g);
			}
		}

	}
	
	public void setGroups(List<Group> gr) {
		groups.clear();
		if (gr!=null && !gr.isEmpty()) {
			for (Group g : gr) {
				groups.add(g);
				if (prev!=null) {
					if (!prev.getGroups().contains(g)) {
						prev.addGroup(g);
					}
				}
				if (next!=null) {
					if (!next.getGroups().contains(g)) {
						next.addGroup(g);
					}
				}

			}

		}
		sync();
	}
	
	public List<Group> getGroups() {
		return groups;
	}
	
	public boolean getActive(boolean b) {
		if (personal) {
			return personal;
		}
		if (b) {
			findActive();
		}
		return active;
	}
	
	public void setActive(boolean b) {
		active = b;
		sync();
	}
	
	public void findActive() {
		if (groups!=null && !groups.isEmpty()) {
			for (Group g : groups) {
				if (g.getActive()) {
					active = true;
					break;
				}
				else {
					active = false;
				}
			}
			if (prev!=null) {
				if (prev.getActive(false)!=active) {
					prev.setActive(active);
				}
			}
			if (next!=null) {
				if (next.getActive(false)!=active) {
					next.setActive(active);
				}
			}
			sync();
			day.setActiveAppointments();
		}
	}
	
	public boolean getPersonal() {
		return personal;
	}
	
	public void setPersonal(boolean b) {
		personal = b;
		sync();
		if (prev!=null) {
			if (prev.getPersonal()!=b) {
				prev.setPersonal(b);
			}
		}
		if (next!=null) {
			if (next.getPersonal()!=b) {
				next.setPersonal(b);
			}
		}

	}
	
	private void sync() {
		JSONObject o = new JSONObject();
		o.put("command", "modify");
		o.put("app", this);
		try {
			API.call("/appointment", o, calendar.getSession());
		} catch (IOException e) {
			return;
		}
	}
	public void setRoom(Room rm){
		thaRoom = rm;
	}
	public void createInServer() {
		super.creator=calendar.getLoggedInUser().getId();
		LocalDateTime t = getStartDate().atTime(getStartStartTime());
		super.start=Date.from(t.atZone(ZoneId.systemDefault()).toInstant());
		t = getEndDate().atTime(getEndEndTime());
		super.end=Date.from(t.atZone(ZoneId.systemDefault()).toInstant());
		t = LocalDateTime.now();
		super.modified=Date.from(t.atZone(ZoneId.systemDefault()).toInstant());
		JSONObject obj = new JSONObject();
		obj.put("command", "create");
		obj.put("app", this.toJSON());
		JSONObject res;
		try {
			res = API.call("/appointment", obj, calendar.getSession());
			if (res.getBoolean("status")) {
				id = res.getInt("aid");
				if(thaRoom != null){
					JSONObject obj1 = new JSONObject();
					obj1.put("command", "reserve");
					obj1.put("aid", id);
					obj1.put("rid", thaRoom.getId());
					LocalDateTime st = date.atTime((startTime));

					LocalDateTime en = date.atTime(endTime);
					
					obj1.put("start", Date.from(st.atZone(ZoneId.systemDefault()).toInstant()).getTime());
					obj1.put("end", Date.from(en.atZone(ZoneId.systemDefault()).toInstant()).getTime());
					
					API.call("/rooms", obj1, calendar.getSession());
					
				}
			}
		} catch (IOException e) {
		}

	}
	
	
}
