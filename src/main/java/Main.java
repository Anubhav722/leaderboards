import data.InMemoryStorage;
import services.LeaderboardService;

public class Main {
    public static void main(String[] args) {
        InMemoryStorage storage = new InMemoryStorage();
        LeaderboardService service = new LeaderboardService(storage);

        service.createGame("game_1");
        service.createLeaderboard("game_1", "lb_1", System.currentTimeMillis() / 1000 - 10, System.currentTimeMillis() / 1000 + 600);

        System.out.println("Adding 200 for player_1");
        service.submitScore("game_1", "player_1", 200);
        System.out.println("Adding 300 for player_1");
        service.submitScore("game_1", "player_1", 300);
        System.out.println("Top 5 players: " + service.getTopPlayers("game_1", "lb_1", 5));

        System.out.println("Adding 500 for player_2");
        service.submitScore("game_1", "player_2", 500);

        System.out.println("Top 1 players: " + service.getTopPlayers("game_1", "lb_1", 1));

        System.out.println("Top next 2 players: " + service.listPlayersNext("game_1", "lb_1", "player_1", 5));

        System.out.println("Adding 600 for player_2");
        service.submitScore("game_1", "player_2", 600);

        System.out.println("Top next 2 players: " + service.listPlayersNext("game_1", "lb_1", "player_2", 5));

        System.out.println("Top prev 2 players: " + service.listPlayersPrev("game_1", "lb_1", "player_2", 5));

        service.createLeaderboard("game_1", "lb_2", System.currentTimeMillis() / 1000 - 10, System.currentTimeMillis() / 1000 + 600);

        System.out.println("Adding 600 for player_2");
        service.submitScore("game_1", "player_2", 600);

        System.out.println("Top next 2 players in lb_1: " + service.listPlayersNext("game_1", "lb_1", "player_2", 5));
        System.out.println("Top next 2 players in lb_2: " + service.listPlayersNext("game_1", "lb_2", "player_2", 5));
        System.out.println("Top next 2 players in lb_2: " + service.listPlayersNext("game_1", "lb_2", "player_1", 5));
//        Thread t1 = new Thread(() -> service.submitScore("game_1", "player_1", 200));
//        Thread t2 = new Thread(() -> service.submitScore("game_1", "player_2", 180));
//
//        t1.start();
//        t2.start();
//
//        System.out.println("Top Players: " + service.getLeaderboard("game_1", "lb_1"));
    }

}
