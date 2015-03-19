package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import login.LoginController;
import login.LoginMain;
import login.LoginManager;
import newAppointment.NewAppointmentController;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import server.Appointment;
import calendar.Calendar;

public class TestNewAppointment extends NewAppointmentController {
	
	protected Appointment app = new Appointment(); 
	protected LoginMain login = new LoginMain();
	protected Date date = new Date();
	protected Calendar calendar;
	
	@Before
	public void setUpGettersAndSetters() throws Exception {
		
		
		
		//setUp newAppointment setters
		Date start = date;
		Date end = date; 
		Date modified = date; 
		
		app.setId(10);
		app.setCreator(10);
		app.setDescription("This is a meeting");
		app.setStart(start);
		app.setEnd(end);
		app.setLocation("Drivhuset");
		app.setModified(modified);
		
//		Appointment.createAppointment(app);
//		appList = Appointment.getAppointmentsFromCreator(10);
//		System.out.println(app);
//		Appointment getApp = new Appointment(10);
//		System.out.println(getApp.getCreator());
//		//setUp
	}

	@After
	public void tearDown() throws Exception {
		
		
	}

	@Test
	public void testNewAppointmentGettersAndSetters() {
		
		try{
			
			//test the getters and setters
			assertEquals(10, app.getId()); 
			assertEquals(10, app.getCreator()); 
			assertEquals("This is a meeting", app.getDescription());
			assertEquals(10, app.getId());
			assertEquals(date, app.getStart());
			assertEquals(date, app.getEnd());
			assertEquals(date, app.getModified());
			
			//test creating new appointment in db
			
			//assertTrue(lm.checkLogin("nicage", "nicage"));
			
			//fail("Not yet implemented");
			
		}catch(Exception e){
			System.err.println("The test failed indeed.");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		
		Result result = JUnitCore.runClasses(TestNewAppointment.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		if(result.wasSuccessful()) System.out.println("All tests was succsessful!");
		else System.out.println("One or more tests was: " + result.wasSuccessful());
	}

}

