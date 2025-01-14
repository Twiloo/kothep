module fr.twiloo.iut.kothep {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;

    exports fr.twiloo.iut.kothep;
    opens resources.assets.textures;
    opens resources.assets.sounds;
    opens fr.twiloo.iut.kothep to javafx.fxml;
}