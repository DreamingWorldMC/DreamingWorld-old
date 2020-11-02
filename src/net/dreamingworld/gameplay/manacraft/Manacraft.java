package net.dreamingworld.gameplay.manacraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.manacraft.blocks.BasicManaGenerator;
import net.dreamingworld.gameplay.manacraft.blocks.IronTreeSapling;
import net.dreamingworld.gameplay.manacraft.items.*;
import org.bukkit.Bukkit;

public class Manacraft {

    public static void initialize() {
        // Blocks
        IronTreeSapling ironTreeSapling = new IronTreeSapling();

        DreamingWorld.getInstance().getBlockManager().registerBlock(new BasicManaGenerator());
        DreamingWorld.getInstance().getBlockManager().registerBlock(ironTreeSapling);

        // Items
        new HotPeddle();
        new HotSword();
        new ManaCore();
        new HardCoal();
        new AdvancedStick();

        //register events
        Bukkit.getPluginManager().registerEvents(new HotStaff(), DreamingWorld.getInstance());
        Bukkit.getPluginManager().registerEvents(ironTreeSapling, DreamingWorld.getInstance());
    }
}
