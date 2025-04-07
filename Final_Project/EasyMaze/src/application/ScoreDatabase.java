package application;

//import java.util.Collections;
//import java.util.Comparator;

public class ScoreDatabase {
//    private static ListInterface<ScoreRecord> records = new ArrayListMaze<>();
    
	private static PriorityQueueScore<ScoreRecord> records = new PriorityQueueScore<>(new ScoreComparator());
	
//    public static void saveScore(String name, double timeSeconds) {
//        records.add(new ScoreRecord(name, timeSeconds));
//        Collections.sort(records, Comparator.comparingDouble(ScoreRecord::getTimeSeconds));
//        System.out.println("Score saved: " + name + " - " + timeSeconds + " sec");
//    }
	
	public static void saveScore(String name, double timeSeconds) {
		records.insert(new ScoreRecord(name, timeSeconds));
		System.out.println("Score saved: " + name + " - " + timeSeconds + " sec");
	}
    
    public static String getRanking() {
        PriorityQueueScore<ScoreRecord> temp = new PriorityQueueScore<>(new ScoreComparator());
//        ScoreRecord[] copy = records.toArray();
//        for (ScoreRecord rec : copy) {
//        	temp.insert(rec);
//        }
        Object[] copy = records.toArray();
        for (Object o : copy) {
        	ScoreRecord rec = (ScoreRecord) o;
        	temp.insert(rec);
        }
    	
    	StringBuilder sb = new StringBuilder();
        int rank = 1;
//        for (ScoreRecord record : records) {
//            sb.append(rank).append(". ").append(record.getName())
//              .append(" - ").append(record.getTimeSeconds()).append(" sec\n");
//            rank++;
//        }
        
        while (!temp.isEmpty()) {
        	ScoreRecord record = temp.removeMin();
        	sb.append(rank).append(". ").append(record.getName()).append(" - ").append(record.getTimeSeconds()).append(" sec\n");
        	rank++;
        }
        return sb.toString();
    }
}

class ScoreRecord {
    private String name;
    private double timeSeconds;
    
    public ScoreRecord(String name, double timeSeconds) {
        this.name = name;
        this.timeSeconds = timeSeconds;
    }
    
    public String getName() {
        return name;
    }
    
    public double getTimeSeconds() {
        return timeSeconds;
    }
}

