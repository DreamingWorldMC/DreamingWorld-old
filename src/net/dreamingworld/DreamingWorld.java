package net.dreamingworld;

import net.dreamingworld.core.blocks.BlockManager;
import net.dreamingworld.core.ui.ChestUI;
import net.dreamingworld.gameplay.alloys.*;
import net.dreamingworld.core.ItemManager;
import net.dreamingworld.core.alloys.AlloyManager;
import net.dreamingworld.core.commands.CommandDwgive;
import net.dreamingworld.core.crafting.CraftingManager;
import net.dreamingworld.gameplay.manacraft.Manacraft;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DreamingWorld extends JavaPlugin implements Listener {

    private static DreamingWorld inst;

    public static DreamingWorld getInstance() {
        return inst;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final ChatColor primaryColor = ChatColor.GREEN;
    public static final ChatColor secondaryColor = ChatColor.AQUA;

    public static final String dataDirectory = "DreamingWorld/data/";

    private ItemManager itemManager;
    private AlloyManager alloyManager;
    private BlockManager blockManager;
    private CraftingManager craftingManager;

    ChestUI ui;

    public void onEnable() {
        inst = this;

        itemManager = new ItemManager();
        alloyManager = new AlloyManager();
        blockManager = new BlockManager();
        craftingManager = new CraftingManager();

        Bukkit.getPluginManager().registerEvents(alloyManager, this);
        Bukkit.getPluginManager().registerEvents(blockManager, this);
        Bukkit.getPluginManager().registerEvents(craftingManager, this);

        new CommandDwgive();

        alloyManager.registerAlloy(Material.COAL_ORE, new Ignium());
        alloyManager.registerAlloy(Material.COAL_ORE, new Energium());
        alloyManager.registerAlloy(Material.IRON_ORE, new Uranium());
        alloyManager.registerAlloy(Material.IRON_ORE, new Manium());
        alloyManager.registerAlloy(Material.LONG_GRASS, new MysticPeddle());

        Manacraft.initialize();

        ui = new ChestUI("Test UI", 2);

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

    public BlockManager getBlockManager() {
        return blockManager;
    }
}
