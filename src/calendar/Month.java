package calendar;

import java.time.LocalDate;

public class Month {
	
	private LocalDate month;
	private Day[] days;
	
//	month is in range 1-12, year from 2000 and up
	public Month(int year, int month) {
		if (year < 2000 || month < 1 || month > 12) {
			throw new IllegalArgumentException("This calendar does not support dates prior to the year 2000");
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
	
	public int getYear() {
		return month.getYear();
	}
	
	public String getMonth() {
		return month.getMonth().toString();
	}
	
	// returns day i of the month
	public Day getDay(int i) {
		if (i > 0 && i <= month.lengthOfMonth()) {
			return days[i-1];
		} else {
			throw new IllegalArgumentException(i + " is not a date in " + getMonth());
		}
	}
	
	public Day[] getDays() {
		return days;
	}
	
	public LocalDate getMonthValue() {
		return month;
	}
	
	// inputting days into the attribute array.
	private void createDays() {
		for (int i = 0; i < days.length; i++) {
			days[i] = new Day(LocalDate.of(month.getYear(), month.getMonth(), i + 1));
		}
	}

}
