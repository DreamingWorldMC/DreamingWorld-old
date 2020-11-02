package net.dreamingworld.gameplay.manacraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.manacraft.blocks.BasicManaGenerator;
import net.dreamingworld.gameplay.manacraft.items.HotPeddle;
import net.dreamingworld.gameplay.manacraft.items.HotStuff;
import net.dreamingworld.gameplay.manacraft.items.HotSword;
import org.bukkit.Bukkit;

public class Manacraft {

    private static HotStuff hotStuff;

    public static void initialize() {
        // Blocks
        DreamingWorld.getInstance().getBlockManager().registerBlock(new BasicManaGenerator());

        // Items
        new HotPeddle();

        new HotSword();

        hotStuff = new HotStuff();
        Bukkit.getPluginManager().registerEvents(hotStuff, DreamingWorld.getInstance());
    }
}
