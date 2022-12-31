package me.improperissues.savedstructures.data;

import org.bukkit.Bukkit;

import java.io.*;

public class StructureFile {

    public static void save(File file, Structure structure) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(structure);
            oos.close();
        } catch (Exception exception) {
            Bukkit.getServer().getLogger().warning(exception.toString());
        }
    }

    public static Structure load(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);

            Structure structure = (Structure) ois.readObject();
            ois.close();
            return structure;
        } catch (Exception exception) {
            Bukkit.getServer().getLogger().warning(exception.toString());
        }
        return null;
    }
}
