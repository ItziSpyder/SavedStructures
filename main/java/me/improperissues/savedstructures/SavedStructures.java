package me.improperissues.savedstructures;

import me.improperissues.savedstructures.commands.Commands;
import me.improperissues.savedstructures.commands.Tabs;
import me.improperissues.savedstructures.data.Structure;
import me.improperissues.savedstructures.events.SelectionEvent;
import me.improperissues.savedstructures.maps.ResetTimer;
import me.improperissues.savedstructures.other.Items;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class SavedStructures extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Structure.PARENTFOLDER.mkdirs();
        ResetTimer.allAllKeys();

        // Events
        getServer().getPluginManager().registerEvents(new SelectionEvent(),this);

        // File
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Commands
        getCommand("structure").setExecutor(new Commands());
        getCommand("structure").setTabCompleter(new Tabs());

        // Items
        Items.registerAll();

        // Loop
        new BukkitRunnable() {
            @Override
            public void run() {
                ResetTimer.loopAllKeys();
            }
        }.runTaskTimer(this,0,20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getInstance() {
        return Bukkit.getServer().getPluginManager().getPlugin("SavedStructures");
    }
}
