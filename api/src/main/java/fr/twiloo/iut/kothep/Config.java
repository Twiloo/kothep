package fr.twiloo.iut.kothep;

public enum Config {
    BASE_ELO(1200),
    ELO_DIFFERENCE_FACTOR(400.0);

    public final Object value;

    Config(Object value) {
        this.value = value;
    }
}
