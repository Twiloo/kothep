package fr.twiloo.iut.kothep.common.model.server;

public final class PlayerActionPacket extends GamePacket<PlayerActionPacket.Content> {
    public PlayerActionPacket(Content playerAction) {
        super(playerAction);
    }

    public record Content(PlayerAction playerAction, Boolean active) { }
}
