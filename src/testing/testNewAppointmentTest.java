package testing;

import static org.junit.Assert.*;

import java.util.Date;

import login.LoginMain;
import newAppointment.NewAppointmentController;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import shared.Appointment;


public class testNewAppointmentTest extends NewAppointmentController {
	
	
	protected Appointment appointment;
	protected LoginMain login = new LoginMain();
	protected Date date = new Date();
	
	@Before
	public void setUp() throws Exception {
		
		
		//String[] args = {"nicage", "nicage"};
		//login.main(args);
		
		Date start = date;
		Date end = date; 
		Date modified = date; 
		appointment = new Appointment();
		appointment.setId(10);
		appointment.setCreator(10);
		appointment.setDescription("This is a meeting");
		appointment.setStart(start);
		appointment.setEnd(end);
		appointment.setLocation("Drivhuset");
		appointment.setModified(modified);
		
		
	}

	@After
	public void tearDown() throws Exception {
		
		
	}

	@Test
	public void testNewAppointmentGettersAndSetters() {
		
		try{
			
			assertEquals(10, appointment.getId()); 
			assertEquals(10, appointment.getCreator()); 
			assertEquals("Ts is a meeting", appointment.getDescription());
			assertEquals(10, appointment.getId());
			assertEquals(date, appointment.getStart());
			assertEquals(date, appointment.getEnd());
			assertEquals(date, appointment.getModified());
			
			//fail("Not yet implemented");
			
		}catch(Exception e){
			System.err.println("The test failed indeed.");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNewAppointmentSavedInDB(){
		//TODO test somethgin here
	}
	
	public static void main(String[] args) 
	{
		Result result = JUnitCore.runClasses(testNewAppointmentTest.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		if(result.wasSuccessful()) System.out.println("All tests was succsessful!");
		else System.out.println("One or more tests was: " + result.wasSuccessful());
	}

}
