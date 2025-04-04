package exceptions;

public class LeaderboardNotFoundException extends LeaderboardException{
    public LeaderboardNotFoundException(String gameId, String leaderboardId) {
        super("Leaderboard " + leaderboardId + " not found in game " + gameId);
    }
}
