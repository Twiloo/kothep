module fr.twiloo.iut.kothep {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.sql;

    exports fr.twiloo.iut.kothep;
    opens assets.textures;
    opens assets.sounds;
    opens fr.twiloo.iut.kothep to javafx.fxml;
}