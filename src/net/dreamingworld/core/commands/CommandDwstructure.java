package net.dreamingworld.core.commands;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.structures.EasyBuilder;
import net.dreamingworld.core.structures.Structure;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;

public class CommandDwstructure implements CommandExecutor, TabCompleter {

    public CommandDwstructure() {
        Bukkit.getPluginCommand("dwstructure").setExecutor(this);
        Bukkit.getPluginCommand("dwstructure").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length != 1) {
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Fuck Windows");
            return true;
        }

        String id = args[0];

        Structure struct = DreamingWorld.getInstance().getStructureManager().getStructure(id);
        if (struct == null) {
            sender.sendMessage(Util.formatString("$(PC)Sorry, but $(SC)" + id + " $(PC)is not a correct DreamingWorld structure id"));
            return true;
        }

        Player player = (Player) sender;
        Block block = player.getTargetBlock(new HashSet<Material>() {{ add(Material.AIR); }}, 10);

        if (block == null) {
            sender.sendMessage(Util.formatString("$(PC)Sorry, but you should target a block!"));
            return true;
        }

        struct.generate(new EasyBuilder(block.getLocation()));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length == 1) {
            return Util.smartAutocomplete(DreamingWorld.getInstance().getStructureManager().getIds(), args);
        }

        return null;
    }
}
