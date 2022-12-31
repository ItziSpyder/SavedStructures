package me.improperissues.savedstructures.commands;

import me.improperissues.savedstructures.data.Structure;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class Tabs implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        String commandName = command.getName().toLowerCase().trim();

        switch (commandName) {
            case "structure":
                switch (args.length) {
                    case 1:
                        list.add("load");
                        list.add("save");
                        list.add("load_safely");
                        list.add("setair_safely");
                        list.add("delete");
                        list.add("setair");
                        list.add("givewand");
                        list.add("help");
                        list.add("getblocks");
                        list.add("timer");
                        break;
                    case 2:
                        return Structure.listFiles();
                    case 3:
                        switch (args[0]) {
                            case "timer":
                                list.add("setinterval");
                                list.add("resume");
                                break;
                        }
                        break;
                    case 4:
                        switch (args[2]) {
                            case "setinterval":
                                list.add("§8<seconds: int>");
                                break;
                            case "resume":
                                list.add("true");
                                list.add("false");
                                break;
                        }
                        break;
                }
                break;
        }

        return list;
    }
}
