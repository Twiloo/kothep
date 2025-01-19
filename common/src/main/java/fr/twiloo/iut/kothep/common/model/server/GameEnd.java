package fr.twiloo.iut.kothep.common.model.server;

public final class GameEnd extends GamePacket<GameEnd.Content> {
    public GameEnd(GameEnd.Content content) {
        super(content);
    }

    public record Content() { }
}
