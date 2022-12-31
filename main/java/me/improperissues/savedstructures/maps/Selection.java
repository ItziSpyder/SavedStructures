package me.improperissues.savedstructures.maps;

import org.bukkit.entity.Player;

import org.bukkit.Location;
import java.util.HashMap;

public class Selection {

    private static HashMap<String,Location> selection1 = new HashMap<>();
    private static HashMap<String,Location> selection2 = new HashMap<>();

    public static boolean hasFullSelection(Player player) {
        return selection1.containsKey(player.getName()) && selection2.containsKey(player.getName());
    }

    public static Location getSelection1(Player player) {
        if (!selection1.containsKey(player.getName())) return null;
        return selection1.get(player.getName());
    }

    public static Location getSelection2(Player player) {
        if (!selection2.containsKey(player.getName())) return null;
        return selection2.get(player.getName());
    }

    public static void setSelection1(Player player, Location location) {
        selection1.put(player.getName(),location);
        player.sendMessage("§fSelection: §7FIRST selection set!");
    }

    public static void setSelection2(Player player, Location location) {
        selection2.put(player.getName(),location);
        player.sendMessage("§fSelection: §7SECOND selection set!");
    }
}
