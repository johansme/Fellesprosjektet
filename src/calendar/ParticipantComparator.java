package calendar;

import java.util.Comparator;

public class ParticipantComparator implements Comparator<Participant> {

	@Override
	public int compare(Participant o1, Participant o2) {
		String s1 = o1.toString();
		s1 = s1.replaceAll(",", "");
		s1 = s1.replaceAll(";", "");
		String s2 = o2.toString();
		s2 = s2.replaceAll(",", "");
		s2 = s2.replaceAll(";", "");
		String[] s1a = s1.split(" ");
		String[] s2a = s2.split(" ");
		if (s1a[0].equals(s2a[0])) {
			if (s1a.length > 1 && s2a.length > 1) {
				return s1a[1].compareToIgnoreCase(s2a[1]);
			}
		}
		return s1a[0].compareToIgnoreCase(s2a[0]);
	}

}
