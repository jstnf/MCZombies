package dev.justinf.mczombies.locale;

import dev.justinf.mczombies.MCZombies;
import dev.justinf.mczombies.config.MCZConVar;
import dev.justinf.mczombies.util.BaseConfiguration;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class MCZLocale {

    private final String DEFAULT_LOCALE = "en_US";

    private final MCZombies plugin;
    private final Map<String, String> entries;

    public MCZLocale(MCZombies plugin) {
        this.plugin = plugin;
        entries = new HashMap<>();
    }

    public void load() {
        // Fill in locale with en_US defaults
        if (!merge(DEFAULT_LOCALE)) {
            plugin.getLogger().warning("Error loading default locale " + DEFAULT_LOCALE + ".");
        }

        String configuredLocale = plugin.getConfiguration().get(MCZConVar.LOCALE);
        if (!configuredLocale.equals(DEFAULT_LOCALE)) {
            plugin.getLogger().info("Detected non-default configured locale: " + configuredLocale);
            plugin.getLogger().info("Attempting to load " + configuredLocale);
            if (merge(configuredLocale)) {
                plugin.getLogger().info("Successfully loaded " + configuredLocale + "!");
            } else {
                plugin.getLogger().warning("Error loading configured locale " + configuredLocale + ".");
            }
        }
    }

    public boolean merge(String locale) {
        BaseConfiguration localeFile = new BaseConfiguration(plugin, "locale/" + locale + ".yml");
        if (!localeFile.initialize(false, true)) return false;

        for (String key : localeFile.getConfig().getKeys(true)) {
            entries.put(key, localeFile.getString(key, key));
        }
        return true;
    }

    public String raw(String reference) {
        String result = entries.getOrDefault(reference, reference);
        if (result == null) {
            return reference;
        }
        return result;
    }

    public String raw(String reference, String... args) {
        String manip = raw(reference);
        for (int i = 0; i < args.length; i++) {
            manip = manip.replace("{" + i + "}", args[i]);
        }
        return manip;
    }

    public String format(String reference) {
        return ChatColor.translateAlternateColorCodes('&', raw(reference));
    }

    public String format(String reference, String... args) {
        return ChatColor.translateAlternateColorCodes('&', raw(reference, args));
    }

    /*
    public String formatPrefixed(String reference, String... args) {
        return ChatColor.translateAlternateColorCodes('&', (iw.getConfiguration().getBoolean(IWConVar.USE_PREFIX_IN_MESSAGES) ? raw(IWRefs.GENERAL_PREFIX) : "") + raw(reference, args));
    }
     */

    public String formatNoColorArgs(String reference, String... args) {
        String manip = format(reference);
        for (int i = 0; i < args.length; i++) {
            manip = manip.replace("{" + i + "}", args[i]);
        }
        return manip;
    }

    /*
    public String formatNoColorArgsPrefixed(String reference, String... args) {
        String manip = formatPrefixed(reference);
        for (int i = 0; i < args.length; i++) {
            manip = manip.replace("{" + i + "}", args[i]);
        }
        return manip;
    }
     */
}