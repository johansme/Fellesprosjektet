package calendarGUI;

import calendar.Appointment;
import calendar.Calendar;

public interface ControllerInterface {

	
	public void setData(Calendar calendar);
	public Calendar getData();
	public void setData(Calendar c, Appointment a);
	public void setFeedback();

}