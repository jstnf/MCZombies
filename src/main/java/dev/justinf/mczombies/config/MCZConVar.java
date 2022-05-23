package dev.justinf.mczombies.config;

public class MCZConVar<E> {

    // VALUES
    public static MCZConVar<String> LOCALE = new MCZConVar<>("mczombies.locale", "en_US");

    private final String path;
    private final E def;

    MCZConVar(String path, E def) {
        this.path = path;
        this.def = def;
    }

    public String path() {
        return path;
    }

    public E def() {
        return def;
    }
}