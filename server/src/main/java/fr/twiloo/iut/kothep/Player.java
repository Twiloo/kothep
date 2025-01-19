package fr.twiloo.iut.kothep;

import fr.twiloo.iut.kothep.common.model.server.GamePacket;
import fr.twiloo.iut.kothep.common.model.server.PlayerActionPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public final class Player implements Runnable {
    private final Server server;
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private Integer id;
    private String pseudo;

    Player(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            socket.close();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        String pseudo;
        try {
            pseudo = (String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.pseudo = pseudo;

        while (true) {
            PlayerActionPacket packet;
            try {
                packet = (PlayerActionPacket) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            if (packet.getContent() == null) {
                try {
                    server.disconnectPlayer(this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            server.getGameLoop().receivePlayerAction(this, packet.getContent());
        }
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void sendPacket(GamePacket<?> packet) throws IOException {
        out.writeUnshared(packet);
        out.flush();
    }

    public void closeSocket() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
