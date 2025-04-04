package exceptions;

public class GameNotFoundException extends LeaderboardException {
    public GameNotFoundException(String gameId) {
        super("Game with id " + gameId + " does not exist");
    }
}
