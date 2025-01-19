package fr.twiloo.iut.kothep.common.model.server;

import java.util.HashMap;

public final class GameTick extends GamePacket<GameTick.Content> {
    public GameTick(Content content) {
        super(content);
    }

    public record Content(HashMap<Integer, Position> playersPositions) {

        public static final class Position {
            private int x;
            private int y;

            public Position(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }
        }
    }
}
