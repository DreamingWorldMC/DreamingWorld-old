package net.dreamingworld.gameplay.manacraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.manacraft.blocks.*;
import net.dreamingworld.gameplay.manacraft.items.*;
import org.bukkit.Bukkit;

public class Manacraft {

    public static void initialize() {
        // Items
        new AdvancedStick();
        new HotPeddle();
        new HotSword();
        new ManaCore();
        new HardCoal();
        new GreenIron();

        // Blocks
        IronTreeSapling ironTreeSapling = new IronTreeSapling();
        IronLeafBlock ironLeafBlock = new IronLeafBlock();
        GreenIronMaker greenIronMaker = new GreenIronMaker();

        DreamingWorld.getInstance().getBlockManager().registerBlock(new BasicManaGenerator());
        DreamingWorld.getInstance().getBlockManager().registerBlock(ironTreeSapling);
        DreamingWorld.getInstance().getBlockManager().registerBlock(ironLeafBlock);
        DreamingWorld.getInstance().getBlockManager().registerBlock(greenIronMaker);
        DreamingWorld.getInstance().getBlockManager().registerBlock(new IronWoodBlock());

        // Events
        Bukkit.getPluginManager().registerEvents(new HotStaff(), DreamingWorld.getInstance());
        Bukkit.getPluginManager().registerEvents(ironTreeSapling, DreamingWorld.getInstance());
        Bukkit.getPluginManager().registerEvents(ironLeafBlock, DreamingWorld.getInstance());
        Bukkit.getPluginManager().registerEvents(greenIronMaker, DreamingWorld.getInstance());

        DreamingWorld.getInstance().getLogger().info("Manacraft initialized");
    }
}
