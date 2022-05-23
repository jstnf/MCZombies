package dev.justinf.mczombies;

import dev.justinf.mczombies.config.MCZConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCZombies extends JavaPlugin {

    private MCZConfiguration configuration;

    @Override
    public void onEnable() {
        configuration = new MCZConfiguration(this);
        if (!configuration.onEnable()) return;
    }

    @Override
    public void onDisable() {

    }

    public MCZConfiguration getConfiguration() {
        return configuration;
    }
}
