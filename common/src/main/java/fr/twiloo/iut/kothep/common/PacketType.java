package fr.twiloo.iut.kothep.common;

public enum PacketType {
    ACTION_TICK(null),
    GAME_START(null),
    GAME_TICK(null),
    GAME_END(null);

    private final Class<? extends GamePacket<?>> requestClass;

    PacketType(Class<? extends GamePacket<?>> requestClass) {
        this.requestClass = requestClass;
    }

    public static PacketType actionOf(GamePacket<?> gamePacket) {
        for (PacketType packetType : values()) {
            if (packetType.requestClass.isAssignableFrom(gamePacket.getClass())) {
                return packetType;
            }
        }
        return null;
    }
}
