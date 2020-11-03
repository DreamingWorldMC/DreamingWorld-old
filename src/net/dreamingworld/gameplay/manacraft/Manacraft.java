package net.dreamingworld.gameplay.manacraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.manacraft.blocks.BasicManaGenerator;
import net.dreamingworld.gameplay.manacraft.blocks.IronLeafBlock;
import net.dreamingworld.gameplay.manacraft.blocks.IronTreeSapling;
import net.dreamingworld.gameplay.manacraft.blocks.IronWoodBlock;
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

        // Blocks
        IronTreeSapling ironTreeSapling = new IronTreeSapling();
        IronLeafBlock ironLeafBlock = new IronLeafBlock();

        DreamingWorld.getInstance().getBlockManager().registerBlock(new BasicManaGenerator());
        DreamingWorld.getInstance().getBlockManager().registerBlock(ironTreeSapling);
        DreamingWorld.getInstance().getBlockManager().registerBlock(ironLeafBlock);
        DreamingWorld.getInstance().getBlockManager().registerBlock(new IronWoodBlock());

        // Events
        Bukkit.getPluginManager().registerEvents(new HotStaff(), DreamingWorld.getInstance());
        Bukkit.getPluginManager().registerEvents(ironTreeSapling, DreamingWorld.getInstance());
        Bukkit.getPluginManager().registerEvents(ironLeafBlock, DreamingWorld.getInstance());
    }
}
