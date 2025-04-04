package exceptions;

public class InvalidScoreException extends LeaderboardException{
    public InvalidScoreException(int score) {
        super("Score " + score + " is invalid");
    }
}
