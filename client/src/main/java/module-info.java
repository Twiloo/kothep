module fr.twiloo.iut.kothep {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires fr.twiloo.iut.kothep.common;
    requires java.sql;

    exports fr.twiloo.iut.kothep;
    opens fr.twiloo.iut.kothep to javafx.fxml;
}