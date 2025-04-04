package models;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class Player {

    private final String playerId;
    private final Map<String, Integer> leaderboardScores;

    public Player(String playerId) {
        this.playerId = playerId;
        this.leaderboardScores = new ConcurrentHashMap<>();
    }

    public String getPlayerId() {
        return playerId;
    }

    public void updateScore(String leaderboardId, int score) {
        leaderboardScores.put(leaderboardId, Math.max(score, leaderboardScores.getOrDefault(leaderboardId, 0)));
    }

    public Integer getScore(String leaderboardId) {
        return leaderboardScores.getOrDefault(leaderboardId, 0);
    }
}
