package services;

import data.BaseStorage;
import models.Game;
import models.Leaderboard;

import java.util.Map;

public class LeaderboardService {
    private BaseStorage storage;

    public LeaderboardService(BaseStorage storage) {
        this.storage = storage;
    }

    public void createGame(String gameId) {
        storage.createGame(gameId);
    }

    public void createLeaderboard(String gameId, String leaderboardId, long startTime, long endTime) {
        storage.createLeaderboard(gameId, leaderboardId, startTime, endTime);
    }

    public void submitScore(String gameId, String playerId, int score) {
        Game game = storage.getGame(gameId);

        for (Map.Entry<String, Leaderboard> mapElement: game.getLeaderboards().entrySet()) {
            Leaderboard leaderboard = mapElement.getValue();
            leaderboard.updateScore(playerId, score);
        }
    }

    public Map<String, Integer> getTopPlayers(String gameId, String leaderboardId, int topN) {
        Leaderboard leaderboard = storage.getLeaderboard(gameId, leaderboardId);
        return leaderboard.getTopPlayers(topN);
    }

    public Map<String, Integer> listPlayersNext(String gameId, String leaderboardId, String playerId, int nPlayers) {
        Leaderboard leaderboard = storage.getLeaderboard(gameId, leaderboardId);
        return leaderboard.listPlayersNext(playerId, nPlayers);
    }

    public Map<String, Integer> listPlayersPrev(String gameId, String leaderboardId, String playerId, int nPlayers) {
        Leaderboard leaderboard = storage.getLeaderboard(gameId, leaderboardId);
        return leaderboard.listPlayersPrev(playerId, nPlayers);
    }

}
