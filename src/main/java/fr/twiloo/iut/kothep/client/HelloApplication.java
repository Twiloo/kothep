package fr.twiloo.iut.kothep.client;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HelloApplication extends GameApplication {
    private Entity player;
    // Main method to start the application
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(600);
        settings.setHeight(600);
        settings.setTitle("Kothep game");
        settings.setVersion("0.1");
    }

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder()
                .at(300, 300)
                .view(new Rectangle(25, 25, Color.BLUE))
                .buildAndAttach();
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.RIGHT, () -> {
            player.translateX(5); // move right 5 pixels
        });

        FXGL.onKey(KeyCode.LEFT, () -> {
            player.translateX(-5); // move left 5 pixels
        });

        FXGL.onKey(KeyCode.UP, () -> {
            player.translateY(-5); // move up 5 pixels
        });

        FXGL.onKey(KeyCode.DOWN, () -> {
            player.translateY(5); // move down 5 pixels
        });
    }
}