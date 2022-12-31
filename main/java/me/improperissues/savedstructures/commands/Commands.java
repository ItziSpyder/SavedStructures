package me.improperissues.savedstructures.commands;

import me.improperissues.savedstructures.data.Config;
import me.improperissues.savedstructures.data.Structure;
import me.improperissues.savedstructures.data.StructureFile;
import me.improperissues.savedstructures.maps.Selection;
import me.improperissues.savedstructures.other.Items;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName().toLowerCase().trim();

        try {
            switch (commandName) {
                case "structure":
                    long start = System.currentTimeMillis();
                    switch (args[0]) {
                        case "load":
                            File file = Structure.getFile(args[1]);
                            Structure structure = StructureFile.load(file);
                            structure.restore();
                            double end = Math.ceil((System.currentTimeMillis() - start) / 10.0) / 100;
                            sender.sendMessage("§fLoaded: §7" + file.getPath() + " §7(§f" + structure.getBlocks().length + " §7blocks, §f" + end + " §7seconds)");
                            return true;
                        case "setair":
                            File airFile = Structure.getFile(args[1]);
                            Structure air = StructureFile.load(airFile);
                            air.setAir();
                            double end1 = Math.ceil((System.currentTimeMillis() - start) / 10.0) / 100;
                            sender.sendMessage("§fLoaded: §7" + airFile.getPath() + " §7(§f" + air.getBlocks().length + " §7blocks, §f" + end1 + " §7seconds)");
                            return true;
                        case "load_safely":
                            File loadSafelyFile = Structure.getFile(args[1]);
                            Structure loadSafely = StructureFile.load(loadSafelyFile);
                            loadSafely.restoreSafely();
                            double end3 = Math.ceil((System.currentTimeMillis() - start) / 10.0) / 100;
                            sender.sendMessage("§fLoaded: §7" + loadSafelyFile.getPath() + " §7(§f" + loadSafely.getBlocks().length + " §7blocks, §f" + end3 + " §7seconds)");
                            return true;
                        case "setair_safely":
                            File airSafelyFile = Structure.getFile(args[1]);
                            Structure airSafely = StructureFile.load(airSafelyFile);
                            airSafely.setAirSafely();
                            double end4 = Math.ceil((System.currentTimeMillis() - start) / 10.0) / 100;
                            sender.sendMessage("§fLoaded: §7" + airSafelyFile.getPath() + " §7(§f" + airSafely.getBlocks().length + " §7blocks, §f" + end4 + " §7seconds)");
                            return true;
                        case "getblocks":
                            File blocksFile = Structure.getFile(args[1]);
                            Structure blocks = StructureFile.load(blocksFile);
                            sender.sendMessage("§fBlocks: §7(§f" + blocks.getBlocks().length + " total§7) §f"
                                    + blocks.getMap().toString().toLowerCase().trim().replaceAll("\\{","§7{§f")
                                    .replaceAll("}","§7}§f").replaceAll(",","§7,§f")
                            );
                            return true;
                        case "save":
                            if (!Selection.hasFullSelection((Player) sender)) {
                                sender.sendMessage("§fSave: §7You have not made a region selection yet! §e/structure givewand");
                                return true;
                            }
                            Structure saving = Structure.getFromCorners(args[1],Selection.getSelection1(((Player) sender)),Selection.getSelection2(((Player) sender)));
                            saving.save();
                            double end2 = Math.ceil((System.currentTimeMillis() - start) / 10.0) / 100;
                            sender.sendMessage("§fSave: §7Saved " + saving.getName() + " to " + saving.getFile().getPath() + " ! §7(§f" + saving.getBlocks().length + " §7blocks, §f" + end2 + " §7seconds)");
                            return true;
                        case "delete":
                            File deleteFile = Structure.getFile(args[1]);
                            deleteFile.delete();
                            sender.sendMessage("§fDeleted: §7" + deleteFile.getPath());
                            return true;
                        case "givewand":
                            if (!(sender instanceof Player)) {
                                sender.sendMessage("§fSelection: §7You must be a player to do this!");
                                return false;
                            }
                            ((Player) sender).getInventory().setItemInMainHand(Items.SELECTION);
                            sender.sendMessage("§fSelection: §7Gave a selection wand!");
                            return true;
                        case "help":
                            sender.sendMessage("\n \n§fStructures and help:\n§7 "
                                    + "§l§o§nSavedStructures§7 aims to save certain chunks of land into §l§o§n.mcbuild§7 files, "
                                    + "and be able to load them back in again. This is nothing different from a "
                                    + "§l§o§nstructure block§7, except for the fact that §l§o§nstorage data are not saved§7 for "
                                    + "performance reasons, however water logs and rotations are. Structures also "
                                    + "have §l§o§nNO limit§7, and the only limit is how well your server is able to handle "
                                    + "the tasks.! You will also be able to §l§o§nimport other .mcbuild§7 files! \n \n§fCommands: "
                                    + "§7/structure [ save | load | setair | delete | givewand | help | timer ] <file name>\n \n "
                            );
                            return true;
                        case "timer":
                            Structure editing = StructureFile.load(Structure.getFile(args[1]));
                            switch (args[2]) {
                                case "resume":
                                    boolean resume = Boolean.parseBoolean(args[3]);
                                    Config.setResume(editing.getName(),resume);
                                    sender.sendMessage("§fConfig: §7Resume timer for " + editing.getName() + ": §f" + Config.getResume(editing.getName()));
                                    return true;
                                case "setinterval":
                                    int interval = Integer.parseInt(args[3]);
                                    Config.setInterval(editing.getName(),interval);
                                    sender.sendMessage("§fConfig: §7Timer interval for " + editing.getName() + ": §f" + Config.getInterval(editing.getName()));
                                    return true;
                            }
                            return true;
                    }
                    return false;
            }
        } catch (Exception exception) {
            String message = "§4Command error: §c";
            if (exception instanceof NullPointerException) message += "File not found";
            else if (exception instanceof ClassCastException) message += "Error while loading file";
            else if (exception instanceof IndexOutOfBoundsException) message += "Incomplete command, not enough arguments!";
            else message += exception.getMessage();
            sender.sendMessage(message);
        }

        return false;
    }
}
