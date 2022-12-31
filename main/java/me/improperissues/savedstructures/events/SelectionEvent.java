package me.improperissues.savedstructures.events;

import me.improperissues.savedstructures.maps.Selection;
import me.improperissues.savedstructures.other.Items;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class SelectionEvent implements Listener {

    static HashMap<String,Long> SELECTION_COOL = new HashMap<>();


    @EventHandler
    public static void PlayerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        try {
            Block block = e.getClickedBlock();
            ItemStack item = p.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            String display = Items.getDisplay(item);

            if (display.equals(Items.getDisplay(Items.SELECTION))) {
                e.setCancelled(true);
                if (SELECTION_COOL.containsKey(p.getName()) && SELECTION_COOL.get(p.getName()) > System.currentTimeMillis()) return;
                SELECTION_COOL.put(p.getName(),System.currentTimeMillis() + 200);
                switch (e.getAction()) {
                    case RIGHT_CLICK_BLOCK:
                        Selection.setSelection2(p,block.getLocation());
                        break;
                    case LEFT_CLICK_BLOCK:
                        Selection.setSelection1(p,block.getLocation());
                        break;
                }
            }
        } catch (NullPointerException exception) {}
    }
}
