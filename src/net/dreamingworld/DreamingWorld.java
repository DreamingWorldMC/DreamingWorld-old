package net.dreamingworld;

import net.dreamingworld.core.UtilItems;
import net.dreamingworld.core.blocks.BlockManager;
import net.dreamingworld.core.customdamage.CustomArmor;
import net.dreamingworld.core.customdamage.CustomDamage;
import net.dreamingworld.core.fishing.FishManager;
import net.dreamingworld.gameplay.alloys.*;
import net.dreamingworld.core.ItemManager;
import net.dreamingworld.core.alloys.AlloyManager;
import net.dreamingworld.core.commands.CommandDwgive;
import net.dreamingworld.core.crafting.CraftingManager;
import net.dreamingworld.gameplay.fishing.Fishing;
import net.dreamingworld.gameplay.manacraft.Manacraft;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
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

    private FishManager fishManager;
    private ItemManager itemManager;
    private AlloyManager alloyManager;
    private BlockManager blockManager;
    private CraftingManager craftingManager;
    private CustomArmor customArmor;
    private CustomDamage customDamage;

    public void onEnable() {
        inst = this;

        long begin = System.currentTimeMillis();

        fishManager = new FishManager();
        itemManager = new ItemManager();
        alloyManager = new AlloyManager();
        blockManager = new BlockManager();
        craftingManager = new CraftingManager();
        customArmor = new CustomArmor();
        customDamage = new CustomDamage();


        Bukkit.getPluginManager().registerEvents(fishManager, this);
        Bukkit.getPluginManager().registerEvents(alloyManager, this);
        Bukkit.getPluginManager().registerEvents(blockManager, this);
        Bukkit.getPluginManager().registerEvents(craftingManager, this);
        Bukkit.getPluginManager().registerEvents(customDamage, this);

        new CommandDwgive();

        alloyManager.registerAlloy(Material.COAL_ORE, new Ignium());
        alloyManager.registerAlloy(Material.COAL_ORE, new Energium());
        alloyManager.registerAlloy(Material.IRON_ORE, new Uranium());
        alloyManager.registerAlloy(Material.IRON_ORE, new Manium());
        alloyManager.registerAlloy(Material.LONG_GRASS, new MysticPeddle());

        UtilItems.initialize();
        Manacraft.initialize();
        Fishing.initialize();

        long time = System.currentTimeMillis() - begin;

        getLogger().info("k-pop is shit [" + time + " ms]");
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelAllTasks();
    }


    public FishManager getFishManager() {
        return fishManager;
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

    public CustomArmor getCustomArmor() {
        return customArmor;
    }
}
