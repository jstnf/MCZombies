package dev.justinf.mczombies.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class BaseConfiguration {

    protected final JavaPlugin plugin;
    protected YamlConfiguration config;

    protected final String filePath;

    public BaseConfiguration(JavaPlugin plugin, String filePath) {
        this.plugin = plugin;
        this.filePath = filePath;
    }

    /**
     * Attempt to read or create the configuration file.
     * Should this method return false, you should stop enabling the plugin as this method disables the plugin.
     * @return true if the file was read/created successfully, false otherwise.
     */
    public boolean onEnable() {
        plugin.getLogger().info("Reading file " + filePath + "...");
        if (!initialize()) {
            plugin.getLogger().severe("Unable to read the file " + filePath + ".");
            plugin.getLogger().severe(plugin.getName() + " will now be disabled.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return false;
        }
        plugin.getLogger().info("Successfully read " + filePath + "!");
        return true;
    }

    /**
     * Attempt to read or create the file and plugin folder.
     * Use this method instead of onEnable() for more control over this process.
     * @return true if the configuration was read/created successfully, false otherwise.
     */
    public boolean initialize() {
        return initialize(true, true);
    }

    /**
     * Safely attempts to read or create the file. (catches and prints exceptions)
     * Use this method instead of onEnable() for more control over this process.
     * @param tryPluginFolder Whether to attempt to create the plugin folder or not
     * @param verbose Whether initialization messages should be printed
     * @return true if the configuration was read/created successfully, false otherwise.
     */
    public boolean initialize(boolean tryPluginFolder, boolean verbose) {
        try {
            return unsafeInitialize(tryPluginFolder, verbose);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unsafeInitialize(boolean tryPluginFolder, boolean verbose) throws IOException, InvalidConfigurationException {
        config = new YamlConfiguration();

        if (tryPluginFolder) {
            if (generatePluginFolder()) {
                if (verbose) plugin.getLogger().info("Found the plugin folder!");
            } else {
                if (verbose) plugin.getLogger().warning("Something went wrong generating the plugin folder.");
                return false;
            }
        }

        File configFile = new File(plugin.getDataFolder() + File.separator + filePath);

        if (configFile.exists()) {
            if (verbose) plugin.getLogger().info("Found existing " + filePath + "!");
        } else {
            if (verbose) plugin.getLogger().info("Generating a new " + filePath + " file!");
            plugin.saveResource(filePath, false);
        }

        config.load(configFile);
        return true;
    }

    /**
     * Create the plugin folder.
     * @return true if the folder is present or was created, false if the folder does not exist and the folder was not created.
     */
    public boolean generatePluginFolder() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getLogger().info("Generating plugin folder...");
            return plugin.getDataFolder().mkdir();
        }

        return true;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    /* Build on these methods with however you define your config variables */
    public int getInt(String path, Object def) {
        return config.getInt(path, (int) def);
    }

    public double getDouble(String path, Object def) {
        return config.getDouble(path, (double) def);
    }

    public String getString(String path, Object def) {
        return config.getString(path, (String) def);
    }

    public boolean getBoolean(String path, Object def) {
        return config.getBoolean(path, (boolean) def);
    }
}