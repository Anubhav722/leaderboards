package data;

import exceptions.GameAlreadyExistsException;
import exceptions.GameNotFoundException;
import exceptions.LeaderboardNotFoundException;
import models.Game;
import models.Leaderboard;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


public class InMemoryStorage implements BaseStorage {

    private final Map<String, Game> games = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger(InMemoryStorage.class.getName());

    // afterthoughts
    // players = {playerId: [LeaderboardScores]}
    // PlayerLeaderboardScore = gameId, playerId, leaderboardId
    // sortedScores

    @Override
    public synchronized Game getGame(String gameId) {
        return games.getOrDefault(gameId, null);
    }

    @Override
    public synchronized Leaderboard getLeaderboard(String gameId, String leaderboardId) {

        Game game = games.get(gameId);
        if (!game.getLeaderboards().containsKey(leaderboardId)) throw new LeaderboardNotFoundException(gameId, leaderboardId);
        return game.getLeaderboards().get(leaderboardId);
    }

    @Override
    public synchronized void createGame(String gameId) {
        if (games.containsKey(gameId)) throw new GameAlreadyExistsException(gameId);
        games.put(gameId, new Game(gameId));
        logger.info("Game with id: " + gameId + " created");
    }

    @Override
    public synchronized void createLeaderboard(String gameId, String leaderboardId, long startTime, long endTime) {
        if (!games.containsKey(gameId)) throw new GameNotFoundException(gameId);
        Game game = games.get(gameId);
        if (game.getLeaderboards().containsKey(leaderboardId)) {
            logger.warning(String.format("Leaderboard with id {0} already exists", leaderboardId));
            return;
        }
        game.addLeaderboard(new Leaderboard(leaderboardId, startTime, endTime));
        logger.info(String.format("Leaderboard created: {0} for game {1}", leaderboardId, gameId));
    }
}
