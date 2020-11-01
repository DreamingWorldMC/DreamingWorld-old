package net.dreamingworld;

import net.dreamingworld.alloys.*;
import net.dreamingworld.commands.CommandDwgive;
import net.dreamingworld.crafting.CraftingManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class DreamingWorld extends JavaPlugin {

    private static DreamingWorld inst;

    public static DreamingWorld getInstance() {
        return inst;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final ChatColor primaryColor = ChatColor.GREEN;
    public static final ChatColor secondaryColor = ChatColor.AQUA;

    private ItemManager itemManager;
    private AlloyManager alloyManager;
    private CraftingManager craftingManager;

    public void onEnable() {
        inst = this;

        itemManager = new ItemManager();
        alloyManager = new AlloyManager();
        craftingManager = new CraftingManager();

        Bukkit.getPluginManager().registerEvents(alloyManager, this);
        Bukkit.getPluginManager().registerEvents(craftingManager, this);

        new CommandDwgive();

        alloyManager.registerAlloy(Material.COAL_ORE, new Ignium());
        alloyManager.registerAlloy(Material.COAL_ORE, new Energium());
        alloyManager.registerAlloy(Material.IRON_ORE, new Uranium());
        alloyManager.registerAlloy(Material.IRON_ORE, new Manium());

        getLogger().info("k-pop is shit");
    }

    public void onDisable() {

    }


    public ItemManager getItemManager() {
        return itemManager;
    }

    public CraftingManager getCraftingManager() {
        return craftingManager;
    }
}
