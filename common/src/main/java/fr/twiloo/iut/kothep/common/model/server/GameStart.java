package fr.twiloo.iut.kothep.common.model.server;

import java.util.List;

public final class GameStart extends GamePacket<GameStart.Content> {
    public GameStart(Content content) {
        super(content);
    }

    public record Content(List<Player> players) {

        public record Player(int id, String pseudo) { }
    }
}
