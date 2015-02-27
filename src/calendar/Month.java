package calendar;

import java.time.LocalDate;

public class Month {
	
	private LocalDate month;
	private Day[] days;
	
//	month is in range 1-12, year from 2000 and up
	public Month(int year, int month) {
		if (year < 2000 || month < 1 || month > 12) {
			throw new IllegalArgumentException("Month out of range");
		}
		this.month = LocalDate.of(year, month, 1);
		this.days = new Day[this.month.lengthOfMonth()];
		createDays();
	}
	
	public Month(LocalDate month) {
		this.month = month;
		this.days = new Day[this.month.lengthOfMonth()];
		createDays();
	}
	
	public String getMonth() {
		return month.getMonth().toString();
	}
	
	// returns day i of the month
	public Day getDay(int i) {
		//TODO add validation
		return days[i-1];
	}
	
	public Day[] getDays() {
		return days;
	}
	
	// inputting days into the attribute array.
	private void createDays() {
		for (int i = 0; i < days.length; i++) {
			days[i] = new Day(LocalDate.of(month.getYear(), month.getMonth(), i + 1));
		}
	}

}
