package exceptions;

public class GameAlreadyExistsException extends LeaderboardException {

    public GameAlreadyExistsException(String gameId) {
        super("Game with id " + gameId + " already exists");
    }
}
