package fr.twiloo.iut.kothep;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public final class Connection implements Runnable {
    private final Server server;
    private final ServerSocket serverSocket;

    Connection(Server server) throws IOException {
        this.server = server;
        serverSocket = new ServerSocket(server.getPort());
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            Socket socketNewPlayer;
            try {
                socketNewPlayer = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Player newPlayer;
            try {
                newPlayer = new Player(server, socketNewPlayer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (!server.addPlayer(newPlayer)) {
                try {
                    newPlayer.closeSocket();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }
}
