package fr.twiloo.iut.kothep;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class ClientApp extends GameApplication {
    private Entity player;
    // Main method to start the application
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        settings.setFullScreenAllowed(true);
        settings.setFullScreenFromStart(true);
        int windowHeight = screenSize.height;
        int windowWidth = screenSize.width;
//        settings.setApplicationMode(ApplicationMode.RELEASE);
        settings.setWidth(windowWidth);
        settings.setHeight(windowHeight);
        settings.setTitle("KOTHEP");
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
        FXGL.getInput().addAction(new UserAction("RIGHT") {
            @Override
            protected void onAction() {
                player.translateX(5);
            }
        }, KeyCode.RIGHT);

        FXGL.getInput().addAction(new UserAction("LEFT") {
            @Override
            protected void onAction() {
                player.translateX(-5);
            }
        }, KeyCode.LEFT);

        FXGL.getInput().addAction(new UserAction("UP") {
            @Override
            protected void onAction() {
                player.translateY(-5);
            }
        }, KeyCode.UP);

        FXGL.getInput().addAction(new UserAction("DOWN") {
            @Override
            protected void onAction() {
                player.translateY(5);
            }
        }, KeyCode.DOWN);
    }
}