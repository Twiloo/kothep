package fr.twiloo.iut.kothep.common;

/**
 * @param <C> content type
 */
public abstract class GamePacket<C> {
    protected final C content;

    public GamePacket(C content) {
        this.content = content;
    }

    public C getContent() {
        return content;
    }

    public PacketType getAction() {
        return PacketType.actionOf(this);
    }
}
