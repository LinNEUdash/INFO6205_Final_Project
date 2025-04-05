package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreDatabase {
    private static List<ScoreRecord> records = new ArrayList<>();
    
    public static void saveScore(String name, double timeSeconds) {
        records.add(new ScoreRecord(name, timeSeconds));
        Collections.sort(records, Comparator.comparingDouble(ScoreRecord::getTimeSeconds));
        System.out.println("Score saved: " + name + " - " + timeSeconds + " sec");
    }
    
    public static String getRanking() {
        StringBuilder sb = new StringBuilder();
        int rank = 1;
        for (ScoreRecord record : records) {
            sb.append(rank).append(". ").append(record.getName())
              .append(" - ").append(record.getTimeSeconds()).append(" sec\n");
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

