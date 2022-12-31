package me.improperissues.savedstructures.maps;

import me.improperissues.savedstructures.data.Config;
import me.improperissues.savedstructures.data.Structure;
import me.improperissues.savedstructures.data.StructureFile;
import org.bukkit.Bukkit;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class ResetTimer {

    public static HashMap<String,Integer> RESET = new HashMap<>();

    public static void allAllKeys() {
        for (String name : Structure.listFiles()) if (!RESET.containsKey(name)) RESET.put(name,0);
    }

    public static void loopAllKeys() {
        allAllKeys();
        RESET.forEach((key,value) -> {
            try {
                if (Config.getResume(key)) {
                    int timeLeft = Config.getInterval(key) - value;
                    if (timeLeft == 0) {
                        Structure structure = StructureFile.load(Structure.getFile(key));
                        Bukkit.getServer().broadcastMessage("§fRegion: §7" + structure.getName() + " is resetting...");
                        structure.restoreSafely();
                        Bukkit.getServer().broadcastMessage("§fRegion: §7Successfully reset §f" + structure.getBlocks().length + " §7blocks!");
                        value = 0;
                        RESET.put(key,value);
                    }
                    RESET.put(key,value + 1);
                }
            } catch (NullPointerException | ConcurrentModificationException exception) {}
        });
    }
}
