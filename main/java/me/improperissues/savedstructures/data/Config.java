package me.improperissues.savedstructures.data;

import me.improperissues.savedstructures.SavedStructures;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    static FileConfiguration CONFIG = SavedStructures.getInstance().getConfig();

    public static boolean getResume(String key) {
        return CONFIG.getBoolean("config.structures." + key + ".resume_timer");
    }

    public static void setResume(String key, boolean resume) {
        CONFIG.set("config.structures." + key + ".resume_timer",resume);
        SavedStructures.getInstance().saveConfig();
    }

    public static int getInterval(String key) {
        return CONFIG.getInt("config.structures." + key + ".timer_interval");
    }

    public static void setInterval(String key, int interval) {
        CONFIG.set("config.structures." + key + ".timer_interval",Math.abs(interval));
        SavedStructures.getInstance().saveConfig();
    }
}
