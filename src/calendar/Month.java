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
	}
	
	public String getMonth() {
		return month.getMonth().toString();
	}
	
	public Day getDay(int i) {
		return null;
	}

}
