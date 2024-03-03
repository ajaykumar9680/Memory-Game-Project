package mindgame.model;

import java.io.Serializable;
import java.util.HashMap;

public class HighScore implements Serializable {
    private HashMap<String, Integer> highScores;

    public HighScore() {
        highScores = new HashMap<>();
        highScores.put("Easy Mode", 0);
        highScores.put("Medium Mode", 0);
        highScores.put("Hard Mode", 0);
    }

    public void setScores(String level, int score) {
        highScores.put(level, score);
    }

    public int getScores(String level) {
        Integer score = highScores.get(level);
        return score != null ? score : 0;
    }
}
