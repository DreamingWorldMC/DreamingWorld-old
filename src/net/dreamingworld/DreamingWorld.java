package net.dreamingworld;

import net.dreamingworld.core.UtilItems;
import net.dreamingworld.core.blocks.BlockManager;
import net.dreamingworld.core.commands.CommandDwsummon;
import net.dreamingworld.core.commands.CommandRecipes;
import net.dreamingworld.core.crafting.RecipeBook;
import net.dreamingworld.core.customentities.EntityManager;
import net.dreamingworld.core.customdamage.CustomArmor;
import net.dreamingworld.core.customdamage.CustomDamage;
import net.dreamingworld.core.fishing.FishManager;
import net.dreamingworld.core.customfood.FoodManager;
import net.dreamingworld.core.manainfusion.ManaInfusionManager;
import net.dreamingworld.gameplay.alloys.*;
import net.dreamingworld.core.ItemManager;
import net.dreamingworld.core.alloys.AlloyManager;
import net.dreamingworld.core.commands.CommandDwgive;
import net.dreamingworld.core.crafting.CraftingManager;
import net.dreamingworld.gameplay.fishing.Fishing;
import net.dreamingworld.gameplay.foodcraft.Foodcraft;
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
    private EntityManager entityManager;
    private FoodManager foodManager;
    private RecipeBook recipeBook;
    private ManaInfusionManager manaInfusionManager;

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
        entityManager = new EntityManager();
        foodManager = new FoodManager();
        manaInfusionManager = new ManaInfusionManager();

        Bukkit.getPluginManager().registerEvents(fishManager, this);
        Bukkit.getPluginManager().registerEvents(alloyManager, this);
        Bukkit.getPluginManager().registerEvents(blockManager, this);
        Bukkit.getPluginManager().registerEvents(craftingManager, this);
        Bukkit.getPluginManager().registerEvents(customDamage, this);
        Bukkit.getPluginManager().registerEvents(entityManager, this);
        Bukkit.getPluginManager().registerEvents(foodManager, this);

        new CommandDwgive();
        new CommandDwsummon();
        new CommandRecipes();

        alloyManager.registerAlloy(Material.COAL_ORE, new Ignium());
        alloyManager.registerAlloy(Material.COAL_ORE, new Energium());
        alloyManager.registerAlloy(Material.IRON_ORE, new Uranium());
        alloyManager.registerAlloy(Material.IRON_ORE, new Manium());
        alloyManager.registerAlloy(Material.LONG_GRASS, new MysticPeddle());

        UtilItems.initialize();
        Manacraft.initialize();
        Fishing.initialize();
        Foodcraft.initialize();

        recipeBook = new RecipeBook();

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

    public EntityManager getEntityManager() { return entityManager; }

    public FoodManager getFoodManager() { return foodManager; }

    public RecipeBook getRecipeBook() {
        return recipeBook;
    }

    public ManaInfusionManager getManaInfusionManager() { return manaInfusionManager; }
}
