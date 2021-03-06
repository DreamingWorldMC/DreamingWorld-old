package net.dreamingworld;

import net.dreamingworld.core.*;
import net.dreamingworld.core.blocks.BlockManager;
import net.dreamingworld.core.chat.ChatManager;
import net.dreamingworld.core.commands.*;
import net.dreamingworld.core.crafting.RecipeBook;
import net.dreamingworld.core.crafting.SmeltingManager;
import net.dreamingworld.core.customdamage.CustomWeapon;
import net.dreamingworld.core.customentities.EntityManager;
import net.dreamingworld.core.customdamage.CustomArmor;
import net.dreamingworld.core.customdamage.CustomDamage;
import net.dreamingworld.core.customfood.FoodManager;
import net.dreamingworld.core.geminfusion.GemInfusionManager;
import net.dreamingworld.core.guilds.Guilds;
import net.dreamingworld.core.guilds.CommandGuild;
import net.dreamingworld.core.manainfusion.ManaInfusionManager;
import net.dreamingworld.core.npcs.RandomTeleportNpc;
import net.dreamingworld.core.ranks.RankManager;
import net.dreamingworld.core.research.ResearchManager;
import net.dreamingworld.core.structures.StructureManager;
import net.dreamingworld.core.vanity.VanityManager;
import net.dreamingworld.gameplay.alloys.*;
import net.dreamingworld.core.alloys.AlloyManager;
import net.dreamingworld.core.crafting.CraftingManager;
import net.dreamingworld.gameplay.bosskills.BossKills;
import net.dreamingworld.gameplay.fishing.Fishing;
import net.dreamingworld.gameplay.foodcraft.Foodcraft;
import net.dreamingworld.gameplay.manacraft.Manacraft;
import net.dreamingworld.gameplay.obelisks.Obelisks;
import net.dreamingworld.gameplay.qolgear.QOLGear;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class DreamingWorld extends JavaPlugin implements Listener {

    private static DreamingWorld inst;

    public static DreamingWorld getInstance() {
        return inst;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public static List<String> playerNames = new ArrayList<>();
    public static Map<CommandSender, CommandSender> lastSenders = new HashMap<>();

    public static final ChatColor primaryColor = ChatColor.GREEN;
    public static final ChatColor secondaryColor = ChatColor.AQUA;

    public static final String dataDirectory = "DreamingWorld/data/";

//    private FishManager fishManager;
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
    private CustomWeapon customWeapon;
    private SmeltingManager smeltingManager;
    private StructureManager structureManager;
    private Guilds guildManager;
    private ResearchManager researchManager;
    private ChatManager chatManager;
    private RankManager rankManager;
    private GemInfusionManager gemInfusionManager;
    private VanityManager vanityManager;

    public void onEnable() {
        inst = this;

        long begin = System.currentTimeMillis();

        for (Player p : Bukkit.getOnlinePlayers()) {
            playerNames.add(p.getName());
        }

//        fishManager = new FishManager();
        itemManager = new ItemManager();
        alloyManager = new AlloyManager();
        blockManager = new BlockManager();
        craftingManager = new CraftingManager();
        customArmor = new CustomArmor();
        customDamage = new CustomDamage();
        entityManager = new EntityManager();
        foodManager = new FoodManager();
        manaInfusionManager = new ManaInfusionManager();
        customWeapon = new CustomWeapon();
        smeltingManager = new SmeltingManager();
        structureManager = new StructureManager();
        guildManager = new Guilds();
        researchManager = new ResearchManager();
        chatManager = new ChatManager();
        rankManager = new RankManager();
        gemInfusionManager = new GemInfusionManager();
        vanityManager = new VanityManager();

        new AutoBroadcast();

//        Bukkit.getPluginManager().registerEvents(fishManager, this);
        Bukkit.getPluginManager().registerEvents(alloyManager, this);
        Bukkit.getPluginManager().registerEvents(blockManager, this);
        Bukkit.getPluginManager().registerEvents(craftingManager, this);
        Bukkit.getPluginManager().registerEvents(customDamage, this);
        Bukkit.getPluginManager().registerEvents(entityManager, this);
        Bukkit.getPluginManager().registerEvents(foodManager, this);
        Bukkit.getPluginManager().registerEvents(smeltingManager, this);
        Bukkit.getPluginManager().registerEvents(guildManager, this);
        Bukkit.getPluginManager().registerEvents(chatManager, this);
        Bukkit.getPluginManager().registerEvents(rankManager, this);
        Bukkit.getPluginManager().registerEvents(this, this);

        new CommandDwgive();
        new CommandDwsummon();
        new CommandRecipes();
        new CommandDwstructure();
        new CommandGuild();
        new CommandDwresearch();
        new TeleportCommands();
        new CommandSystemInfo();
        new CommandSpawn();
        new CommandOverrides();
        new CommandWebsite();
        new CommandRules();

        new RandomTeleportNpc();

        alloyManager.registerAlloy(Material.COAL_ORE, new Ignium());
        alloyManager.registerAlloy(Material.COAL_ORE, new Energium());
        alloyManager.registerAlloy(Material.IRON_ORE, new Uranium());
        alloyManager.registerAlloy(Material.IRON_ORE, new Manium());
        alloyManager.registerAlloy(Material.LONG_GRASS, new MysticPeddle());

        UtilItems.initialize();
        Manacraft.initialize();
        Fishing.initialize();
        Foodcraft.initialize();
        BossKills.initialize();
        QOLGear.initialize();

        new Obelisks();

        researchManager.initializeResearchItems(); // Please keep this after anything which creates new researches

        recipeBook = new RecipeBook();

        long time = System.currentTimeMillis() - begin;

        getLogger().info("k-pop is shit [" + time + " ms]");
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelAllTasks();
        getBlockManager().getBlockDataManager().saveAll();
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage(Util.formatString("$(PC)Welcome to $(SC)DreamingWorld$(PC) alpha!"));
        PacketWizard.tellraw(e.getPlayer(), "[\"\",{\"text\":\"You can join our \",\"color\":\"gold\"},{\"text\":\"Discord\",\"underlined\":true,\"color\":\"blue\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://discord.gg/xUVwfFS6pX\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click!\"}}}]");

        playerNames.add(e.getPlayer().getName());
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        playerNames.remove(e.getPlayer().getName());
    }


//    public FishManager getFishManager() {
//        return fishManager;
//    }

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

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public FoodManager getFoodManager() {
        return foodManager;
    }

    public RecipeBook getRecipeBook() {
        return recipeBook;
    }

    public ManaInfusionManager getManaInfusionManager() {
        return manaInfusionManager;
    }

    public CustomDamage getCustomDamageManager() {
        return customDamage;
    }

    public CustomWeapon getCustomWeaponManager() {
        return customWeapon;
    }

    public SmeltingManager getSmeltingManager() {
        return smeltingManager;
    }

    public StructureManager getStructureManager() {
        return structureManager;
    }

    public Guilds getGuildManager() {
        return guildManager;
    }

    public ResearchManager getResearchManager() { return researchManager; }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public GemInfusionManager getGemInfusionManager() {
        return gemInfusionManager;
    }

    public VanityManager getVanityManager() {
        return vanityManager;
    }
}
