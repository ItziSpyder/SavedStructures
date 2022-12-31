package me.improperissues.savedstructures.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;

import java.io.Serializable;

public class SavedBlock implements Serializable {

    private String world, material, data;
    private int x, y, z;

    public SavedBlock(Block block) {
        this.world = block.getWorld().getName();
        this.material = block.getType().name();
        this.data = block.getBlockData().getAsString();
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
    }

    public void restore() {
        Material type = Material.valueOf(material);
        Location loc = new Location(Bukkit.getWorld(world),x,y,z);
        Block block = loc.getBlock();
        block.setType(type);
        setBlockData(block,this.data);
    }

    public void setAir() {
        Location loc = new Location(Bukkit.getWorld(world),x,y,z);
        loc.getBlock().setType(Material.AIR);
    }

    public String getWorld() {
        return world;
    }

    public String getMaterial() {
        return material;
    }

    public String getData() {
        return data;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public static void setBlockData(Block block, String blockDataString) {
        // needs to have a valid block data string
        if (!blockDataString.contains("]")) return;
        // extracting data from block data string
        BlockData blockData = block.getBlockData();
        String name = block.getType().name().toLowerCase();
        String filtered = blockDataString.replaceAll("minecraft:" + name + "\\[","").replaceAll("]","");
        String[] data = filtered.split(",");
        // applying data to the block itself
        for (String value : data)
            try {
                String[] values = value.toUpperCase().split("=");
                if (values[0].equals("FACING") && blockData instanceof Directional) ((Directional) blockData).setFacing(BlockFace.valueOf(values[1]));
                if (values[0].equals("LEVEL") && blockData instanceof Levelled) ((Levelled) blockData).setLevel(Integer.parseInt(values[1]));
                if (values[0].equals("HALF") && blockData instanceof Bisected) ((Bisected) blockData).setHalf(Bisected.Half.valueOf(values[1]));
                if (values[0].equals("WATERLOGGED") && blockData instanceof Waterlogged) ((Waterlogged) blockData).setWaterlogged(Boolean.parseBoolean(values[1]));
                if (values[0].equals("OPEN") && blockData instanceof Openable) ((Openable) blockData).setOpen(Boolean.parseBoolean(values[1]));
                if (values[0].equals("SHAPE") && blockData instanceof Stairs) ((Stairs) blockData).setShape(Stairs.Shape.valueOf(values[1]));
                if (values[0].equals("POWERED") && blockData instanceof Powerable) ((Powerable) blockData).setPowered(Boolean.parseBoolean(values[1]));
                if (values[0].equals("TYPE") && blockData instanceof Slab) ((Slab) blockData).setType(Slab.Type.valueOf(values[1]));
                if (values[0].equals("HINGE") && blockData instanceof Door) ((Door) blockData).setHinge(Door.Hinge.valueOf(values[1]));
                if (values[0].equals("PART") && blockData instanceof Bed) ((Bed) blockData).setPart(Bed.Part.valueOf(values[1]));
                if (values[0].equals("ROTATION") && blockData instanceof Rotatable) ((Rotatable) blockData).setRotation(BlockFace.valueOf(values[1]));
                if (blockData instanceof MultipleFacing) ((MultipleFacing) blockData).setFace(BlockFace.valueOf(values[0]),Boolean.parseBoolean(values[1]));
            } catch (IllegalArgumentException | NullPointerException | ClassCastException exception) {}
        block.setBlockData(blockData);
    }
}
