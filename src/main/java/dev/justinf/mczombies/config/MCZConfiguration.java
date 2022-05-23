package dev.justinf.mczombies.config;

import dev.justinf.mczombies.MCZombies;
import dev.justinf.mczombies.util.BaseConfiguration;

public class MCZConfiguration extends BaseConfiguration {

    public MCZConfiguration(MCZombies plugin) {
        super(plugin, "config.yml");
    }

    public <E> E get(MCZConVar<E> conVar) {
        return (E) config.get(conVar.path(), conVar.def());
    }
}