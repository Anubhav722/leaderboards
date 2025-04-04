package models;

import exceptions.InvalidScoreException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Logger;

public class Leaderboard {

    private static final int MIN_SCORE = 1;
    private static final int MAX_SCORE = 1000000000;
    private static final Logger logger = Logger.getLogger(Leaderboard.class.getName());

    private final String leaderboardId;
    private final long startTime;
    private final long endTime;

    private final ConcurrentHashMap<String, Player> players;
    private final ConcurrentSkipListMap<Integer, Set<String>> sortedScores;

    public Leaderboard(String leaderboardId, long startTime, long endTime) {
        this.leaderboardId = leaderboardId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sortedScores = new ConcurrentSkipListMap<>();

        this.players = new ConcurrentHashMap<>();
        logger.info("Created leaderboard: " + leaderboardId);
    }

    public String getLeaderboardId() {
        return leaderboardId;
    }

    public boolean isActive() {
        long currentTime = System.currentTimeMillis() / 1000;
        return currentTime >= startTime && currentTime <= endTime;
    }

    public synchronized void updateScore(String playerId, int score) {

        if (score < MIN_SCORE || score > MAX_SCORE) throw new InvalidScoreException(score);

        if (!isActive()) {
            logger.info(String.format("Leaderboard {0} no longer active", leaderboardId));
            return;
        }

        if (!players.containsKey(playerId)) players.put(playerId, new Player(playerId));
        Player player = players.get(playerId);
        int oldScore = player.getScore(leaderboardId);

        if (score > oldScore) {
            player.updateScore(leaderboardId, score);

            if (sortedScores.containsKey(oldScore)) {
                sortedScores.get(oldScore).remove(playerId);
                if (sortedScores.get(oldScore).isEmpty()) sortedScores.remove(oldScore);
            }

            if (!sortedScores.containsKey(score)) sortedScores.put(score, new HashSet<>());
            sortedScores.get(score).add(playerId);
        }
    }

    public synchronized Map<String, Integer> getTopPlayers(int n) {

        Map<String, Integer> topNScores = new HashMap<>();
        int count = 0;
        for (Map.Entry<Integer, Set<String>> mapElement : sortedScores.descendingMap().entrySet()) {
            for (String userId : mapElement.getValue()) {
                if (count == n) break;
                topNScores.put(userId, mapElement.getKey());
                ++count;
            }
        }
        return topNScores;
    }

    public synchronized Map<String, Integer> listPlayersNext(String playerId, int n) {

        if (!players.containsKey(playerId)) {
            logger.warning("Player does not exist");
            return null;
        }

        int score = players.get(playerId).getScore(leaderboardId);
        Map<String, Integer> resultScores = new HashMap<>();

        resultScores.put(playerId, score);
        Set<String> userIds = sortedScores.get(score);
        if (userIds.size() >= n + 1) {
            for (String userId: userIds) {
                resultScores.put(userId, score);
                if (resultScores.size() == n+1) return resultScores;
            }
        }

        int curScore = score;

        while (sortedScores.higherKey(curScore) != null) {
            curScore = sortedScores.higherKey(curScore);
            userIds = sortedScores.get(curScore);
            for (String userId: userIds) {
                resultScores.put(userId, curScore);
                if (resultScores.size() == n+1) break;
            }
        }
        return resultScores;
    }

    public synchronized Map<String, Integer> listPlayersPrev(String playerId, int n) {
        if (!players.containsKey(playerId)) {
            logger.warning("Player does not exist");
            return null;
        }

        int score = players.get(playerId).getScore(leaderboardId);
        Map<String, Integer> resultScores = new HashMap<>();

        resultScores.put(playerId, score);
        Set<String> userIds = sortedScores.get(score);
        if (userIds.size() >= n + 1) {
            for (String userId: userIds) {
                resultScores.put(userId, score);
                if (resultScores.size() == n+1) return resultScores;
            }
        }

        int curScore = score;

        while (sortedScores.lowerKey(curScore) != null) {
            curScore = sortedScores.lowerKey(curScore);
            userIds = sortedScores.get(curScore);
            for (String userId: userIds) {
                resultScores.put(userId, curScore);
                if (resultScores.size() == n+1) break;
            }
        }
        return resultScores;
    }
}
