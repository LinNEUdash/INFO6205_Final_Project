package application;

import java.util.Comparator;

public class ScoreComparator implements Comparator<ScoreRecord>{
	@Override
	public int compare(ScoreRecord o1, ScoreRecord o2) {
		// make it in ascending order
		return Double.compare(o1.getTimeSeconds(), o2.getTimeSeconds());
	}
}
