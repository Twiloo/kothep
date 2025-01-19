package fr.twiloo.iut.kothep;

public enum Config {
    MAP_WIDTH(8192),
    MAP_HEIGHT(4096);

    public final Object value;

    Config(Object value) {
        this.value = value;
    }
}
