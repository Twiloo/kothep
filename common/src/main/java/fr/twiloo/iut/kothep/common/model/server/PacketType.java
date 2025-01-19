package fr.twiloo.iut.kothep.common.model.server;

public enum PacketType {
    PLAYER_ACTION(PlayerActionPacket.class),
    GAME_START(GameStart.class),
    GAME_TICK(GameTick.class),
    GAME_END(GameEnd.class),
    NEW_MESSAGE(null);

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
