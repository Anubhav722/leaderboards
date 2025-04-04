package data;

import models.Game;
import models.Leaderboard;

public interface BaseStorage {
    Game getGame(String gameId);
    Leaderboard getLeaderboard(String gameId, String leaderboardId);
    void createGame(String gameId);
    void createLeaderboard(String gameId, String leaderboardId, long startTime, long endTime);
}
