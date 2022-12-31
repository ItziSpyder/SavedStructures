package me.improperissues.savedstructures.other;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class Items {

    public static void registerAll() {
        setSELECTION();
    }

    public static String getDisplay(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null || meta.getDisplayName().trim().equals("")) return item.getType().name().toLowerCase();
        return meta.getDisplayName();
    }

    public static ItemStack SELECTION;

    static void setSELECTION() {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fStructures: §7SELECTION WAND");
        meta.setLore(new ArrayList<>(Arrays.asList(
                "§7§oUse this item to select",
                "§7§ocorners in a world and save",
                "§7§oand area as a structure file!"
        )));
        item.setItemMeta(meta);
        SELECTION = item;
    }
}
