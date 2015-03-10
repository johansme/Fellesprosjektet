package calendar;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

public class Calendar {

	//Current here means the date etc. to be shown in the calendar.
	private LocalDate currentDate;
	private Month currentMonth;
	private List<Month> months;

	public Calendar() {
		currentDate = LocalDate.now();
		currentMonth = new Month(currentDate);
		months = new ArrayList<Month>();
		months.add(currentMonth);
		addPastMonths(5);
		addFutureMonths(8);
	}

	public LocalDate getCurrentDate() {
		return currentDate;
	}

	public Month getCurrentMonth() {
		return currentMonth;
	}
	
	public void setCurrentDate(LocalDate date) {
		if (date != null && date.isAfter(LocalDate.of(1999, 12, 31))) {
			currentDate = date;
			Month tempMonth = currentMonth;
			if (! currentDate.getMonth().toString().equals(currentMonth.getMonth())) {
				int i = months.indexOf(currentMonth);
				if (currentMonth.getMonthValue().isBefore(currentDate)) {
					for (int j=1; j<12; j++) {
						if (i == months.size()-1) {
							addFutureMonths(3);
						}
						tempMonth = months.get(i + 1);
						if (currentDate.getMonth()==months.get(i+1).getMonthValue().getMonth()) {
							currentMonth = tempMonth;
							return;
						}
						i++;

					}
				}
				else {
					for (int j=1; j<12; j++) {
						if (i==0) {
							addPastMonths(3);
							i=months.indexOf(tempMonth);
						}
						tempMonth = months.get(i - 1);
						if (currentDate.getMonth()==months.get(i-1).getMonthValue().getMonth()) {
							currentMonth = tempMonth;
							return;
						}
						i--;
					}
				}
			}
		}
	}

	public int getCurrentWeekNumber() {	
		return getWeekNumber(currentDate);
	}
	
	public static int getWeekNumber(LocalDate date) {
		WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
		int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
		return weekNumber;
	}

	public List<Month> getMonths() {
		return months;
	}

	public Month getNextMonth() {
		return getFutureMonth(1);
	}

	public Month getPreviousMonth() throws IllegalStateException {
		return getPastMonth(1);
	}

	// Jumps a specified number of months forward
	public Month getFutureMonth(int numberOfMonths) {
		int i = months.indexOf(currentMonth);
		if (i + numberOfMonths >= months.size()) {
			addFutureMonths(numberOfMonths);
		}
		currentMonth = months.get(i + numberOfMonths);
		currentDate = currentDate.plusMonths(numberOfMonths);
		currentDate = currentDate.minusDays(currentDate.getDayOfMonth()-1);
		return currentMonth;
	}
	
	public Month getPastMonth(int numberOfMonths) throws IllegalStateException {
		int i = months.indexOf(currentMonth);
		if (i < numberOfMonths) {
			addPastMonths(numberOfMonths);
		}
		i = months.indexOf(currentMonth);
		currentMonth = months.get(i - numberOfMonths);
		currentDate = currentDate.minusMonths(numberOfMonths);
		currentDate = currentDate.plusDays(currentDate.lengthOfMonth()-currentDate.getDayOfMonth());
		return currentMonth;
	}

	// adds a specified number of months to the end of the list
	public void addFutureMonths(int numberOfMonths) {
		Month month = months.get(months.size()-1);
		LocalDate date = month.getDay(1).getDate();
		for (int i = 1; i <= numberOfMonths; i++) {
			months.add(new Month(date.plusMonths(i)));
		}
	}
	
	public void addPastMonths(int numberOfMonths) throws IllegalStateException {
		Month month = months.get(0);
		LocalDate date = month.getDay(month.getDays().length).getDate();
		for (int i = 0; i < numberOfMonths; i++) {
			if (date.minusMonths(1).isAfter(LocalDate.of(1999, 12, 31))) {
				date = date.minusMonths(1);
				months.add(0, new Month(date));
			} else {
				throw new IllegalStateException("This calendar does not support dates before the year 2000");
			}
		}
	}
	
	
}
