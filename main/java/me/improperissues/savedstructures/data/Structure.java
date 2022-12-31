package me.improperissues.savedstructures.data;

import me.improperissues.savedstructures.SavedStructures;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Structure implements Serializable {

    public static File PARENTFOLDER = new File("plugins/SavedStructures/structures");
    private String name;
    private SavedBlock[] blocks;


    public static List<String> listFiles() {
        List<String> list = new ArrayList<>();
        File[] files = PARENTFOLDER.listFiles();
        if (files == null) return list;
        for (File file : files) list.add(file.getName().replaceAll(".mcbuild",""));
        return list;
    }

    public static Structure getFromCorners(String name, Location corner1, Location corner2) {
        int minX = Math.min(corner1.getBlockX(),corner2.getBlockX());
        int maxX = Math.max(corner1.getBlockX(),corner2.getBlockX());
        int minY = Math.min(corner1.getBlockY(),corner2.getBlockY());
        int maxY = Math.max(corner1.getBlockY(),corner2.getBlockY());
        int minZ = Math.min(corner1.getBlockZ(),corner2.getBlockZ());
        int maxZ = Math.max(corner1.getBlockZ(),corner2.getBlockZ());
        List<SavedBlock> blockList = new ArrayList<>();
        for (int x = minX; x <= maxX; x ++)
            for (int y = minY; y <= maxY; y ++)
                for (int z = minZ; z <= maxZ; z ++) {
                    Location loc = new Location(corner1.getWorld(),x,y,z);
                    blockList.add(new SavedBlock(loc.getBlock()));
                }
        return new Structure(name,blockList.toArray(new SavedBlock[0]));
    }

    public static File getFile(String name) {
        File file = new File("plugins/SavedStructures/structures/" + name + ".mcbuild");
        return file;
    }

    public Structure(String name, SavedBlock[] blocks) {
        this.name = name;
        this.blocks = blocks;
    }

    public void restore() {
        for (SavedBlock block : blocks) block.restore();
    }

    public void restoreSafely() {
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if (i >= blocks.length) this.cancel();
                for (int j = 0; j < 3000; j ++) {
                    try {
                        SavedBlock block = blocks[i];
                        block.restore();
                    } catch (IndexOutOfBoundsException | NullPointerException exception) {
                        break;
                    }
                    i++;
                }
            }
        }.runTaskTimer(SavedStructures.getInstance(),0,1);
    }

    public void setAir() {
        for (SavedBlock block : blocks) block.setAir();
    }

    public void setAirSafely() {
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if (i >= blocks.length) this.cancel();
                for (int j = 0; j < 3000; j ++) {
                    try {
                        SavedBlock block = blocks[i];
                        block.setAir();
                    } catch (IndexOutOfBoundsException | NullPointerException exception) {
                        break;
                    }
                    i++;
                }
            }
        }.runTaskTimer(SavedStructures.getInstance(),0,1);
    }

    public void save() {
        StructureFile.save(this.getFile(),this);
    }

    public HashMap<String,Integer> getMap() {
        HashMap<String,Integer> map = new HashMap<>();
        for (SavedBlock block : blocks) {
            int count = 0;
            count = (map.get(block.getMaterial()) == null ? count : map.get(block.getMaterial()));
            count ++;
            map.put(block.getMaterial(),count);
        }
        return map;
    }

    public void delete() {
        File file = this.getFile();
        file.delete();
    }

    public File getFile() {
        File file = new File(PARENTFOLDER, this.name + ".mcbuild");
        try {
            if (!file.getParentFile().exists()) file.getParentFile().mkdir();
            if (!file.exists()) file.createNewFile();
        } catch (Exception exception) {
            Bukkit.getServer().getLogger().warning(exception.toString());
        }
        return file;
    }

    public String getName() {
        return name;
    }

    public SavedBlock[] getBlocks() {
        return blocks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBlocks(SavedBlock[] blocks) {
        this.blocks = blocks;
    }
}
