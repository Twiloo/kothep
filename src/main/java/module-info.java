module fr.twiloo.iut.kothep {
    requires com.almasb.fxgl.all;

    exports fr.twiloo.iut.kothep.client;
    opens assets.textures;
    opens assets.sounds;
}