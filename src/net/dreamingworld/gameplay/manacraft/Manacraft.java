package net.dreamingworld.gameplay.manacraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.manacraft.blocks.BasicManaGenerator;
import net.dreamingworld.gameplay.manacraft.items.HotPeddle;
import net.dreamingworld.gameplay.manacraft.items.HotStaff;
import net.dreamingworld.gameplay.manacraft.items.HotSword;
import org.bukkit.Bukkit;

public class Manacraft {

    public static void initialize() {
        // Blocks
        DreamingWorld.getInstance().getBlockManager().registerBlock(new BasicManaGenerator());

        // Items
        new HotPeddle();
        new HotSword();

        Bukkit.getPluginManager().registerEvents(new HotStaff(), DreamingWorld.getInstance());
    }
}
