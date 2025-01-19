package fr.twiloo.iut.kothep;

import fr.twiloo.iut.kothep.common.model.server.GameStart;
import fr.twiloo.iut.kothep.common.model.server.GameTick;
import fr.twiloo.iut.kothep.common.model.server.PlayerAction;
import fr.twiloo.iut.kothep.common.model.server.PlayerActionPacket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public final class GameLoop implements Runnable {
    private final ArrayList<Player> players;
    private final HashMap<Integer, GameTick.Content.Position> playersPositions;
    private final HashMap<Integer, ArrayList<PlayerAction>> playersActions;
    private final HashMap<Integer, Velocity> playersVelocities;
    private boolean running;
    private static final int TICK_RATE = 128;
    private static final float MAX_VELOCITY = 1.0f;
    private static final float VELOCITY_INCREMENT = 0.05f;

    public GameLoop(ArrayList<Player> players) {
        this.players = new ArrayList<>();
        for (int i = 0; i < players.size() ; i++) {
            this.players.add(players.get(i));
            players.get(i).setId(i);
        }
        playersPositions = new HashMap<>();
        for (int i = 0; i < players.size() ; i++) {
            playersPositions.put(i, new GameTick.Content.Position((int) (Config.MAP_WIDTH.value)/2 + (((i & 1) == 0 ? i : -i) * 50), 50));
        }
        playersActions = new HashMap<>();
        for (int i = 0; i < players.size() ; i++) {
            playersActions.put(i, new ArrayList<>());
        }
        playersVelocities = new HashMap<>();
        for (int i = 0; i < players.size(); i++) {
            playersVelocities.put(i, new Velocity(0, 0));
        }
    }

    @Override
    public void run() {
        try {
            startGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long tickInterval = 1000/TICK_RATE;
        while (running) {
            long startTime = System.currentTimeMillis();

            GameTick gametick = getCurrentGameTick();
            players.forEach(player -> {
                try {
                    player.sendPacket(gametick);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime < tickInterval) {
                try {
                    Thread.sleep(tickInterval - elapsedTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        endGame();
    }

    public void receivePlayerAction(Player player, PlayerActionPacket.Content action) {
        ArrayList<PlayerAction> playerActions = playersActions.get(player.getId());
        if (playerActions.contains(action.playerAction()) && !action.active()) {
            playerActions.remove(action.playerAction());
        } else if (!playerActions.contains(action.playerAction()) && action.active()) {
            playerActions.add(action.playerAction());
        }
    }

    private GameTick getCurrentGameTick() {
        playersActions.forEach((playerId, playerActions) -> {
            for (PlayerAction playerAction : playerActions) {
                applyPlayerAction(playerId, playerAction);
            }
        });

        // apply movement based on current player velocities
        playersVelocities.forEach((playerId, velocity) -> {
            GameTick.Content.Position position = playersPositions.get(playerId);
            position.setX((int) (position.getX() + (velocity.x * 2)));
            position.setY((int) (position.getY() + (velocity.y * 2)));

            // gravity
            if (position.getY() > 0) {
                velocity.y -= VELOCITY_INCREMENT;
            }

            // don't go below ground
            if (position.getY() < 0) {
                position.setY(0);
                velocity.y = 0;
            }

            // slow down right/left if not going in said direction
            if (velocity.x > 0 && !playersActions.get(playerId).contains(PlayerAction.GO_RIGHT)) {
                velocity.x -= VELOCITY_INCREMENT;
            } else if (velocity.x < 0 && !playersActions.get(playerId).contains(PlayerAction.GO_LEFT)) {
                velocity.x += VELOCITY_INCREMENT;
            }
        });

        return new GameTick(new GameTick.Content(playersPositions));
    }

    private void applyPlayerAction(Integer playerId, PlayerAction playerAction) {
        Velocity velocity = playersVelocities.get(playerId);

        switch (playerAction) {
            case JUMP:
                if (playersPositions.get(playerId).getY() == 0) {
                    velocity.y = MAX_VELOCITY;
                }
                break;
            case GO_LEFT:
                velocity.x = Math.max(velocity.x - VELOCITY_INCREMENT, -MAX_VELOCITY);
                break;
            case GO_RIGHT:
                velocity.x = Math.min(velocity.x + VELOCITY_INCREMENT, MAX_VELOCITY);
                break;
        }
    }

    private static class Velocity {
        public float x;
        public float y;

        public Velocity(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    private void startGame() throws IOException {
        GameStart gameStart = new GameStart(new GameStart.Content(players.stream().map(player -> new GameStart.Content.Player(player.getId(), player.getPseudo())).toList()));
        for (Player player : players) {
            player.sendPacket(gameStart);
        }
    }

    private void endGame() {

    }
}
