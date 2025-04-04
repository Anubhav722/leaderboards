package models;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Game {
    private static final Logger logger = Logger.getLogger(Game.class.getName());

    private final String gameId;
    private final Map<String, Leaderboard> leaderboards;

    public Game(String gameId) {
        this.gameId = gameId;
        this.leaderboards = new ConcurrentHashMap<>();
        logger.info("Created new game: " + gameId);
    }

    public String getGameId() {
        return gameId;
    }

    public void addLeaderboard(Leaderboard leaderboard) {
        leaderboards.put(leaderboard.getLeaderboardId(), leaderboard);
        logger.info("Added leaderboard: " + leaderboard.getLeaderboardId() + " to game: " + gameId);
    }

    public Map<String, Leaderboard> getLeaderboards() {
        return leaderboards;
    }
}
