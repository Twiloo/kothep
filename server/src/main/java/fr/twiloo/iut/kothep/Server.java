package fr.twiloo.iut.kothep;

import java.io.IOException;
import java.util.ArrayList;

public final class Server {
    private final int port;
    private final ArrayList<Player> players = new ArrayList<>();
    private final Connection connection;
    private GameLoop gameLoop;
    private int playerCount;
    private static final int PLAYER_LIMIT = 2;

    public Server(int port) throws IOException {
        this.port = port;

        connection = new Connection(this);
        new Thread(connection).start();
    }

    public int getPort() {
        return port;
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    public boolean addPlayer(Player newPlayer) {
        if (playerCount == PLAYER_LIMIT)
            return false;
        Thread thread = new Thread(newPlayer);
        thread.start();
        players.add(newPlayer);
        playerCount++;
        if (playerCount == PLAYER_LIMIT)
            startGameLoop();
        return true;
    }

    public void disconnectPlayer(Player player) throws IOException {
        player.closeSocket();
        players.remove(player);
    }

    private void startGameLoop() {
        gameLoop = new GameLoop(players);
        Thread thread = new Thread(gameLoop);
        thread.start();
    }

    public void shutdown() throws IOException {
        for (Player player : players) {
            player.closeSocket();
        }
        connection.stop();
    }
}
