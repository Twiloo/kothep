module fr.twiloo.iut.kothep {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens fr.twiloo.iut.kothep to javafx.fxml;
    exports fr.twiloo.iut.kothep;
}