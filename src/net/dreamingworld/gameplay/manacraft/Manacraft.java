package net.dreamingworld.gameplay.manacraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.manacraft.blocks.BasicManaGenerator;
import net.dreamingworld.gameplay.manacraft.items.HotPeddle;
import net.dreamingworld.gameplay.manacraft.items.HotSword;

public class Manacraft {

    public static void initialize() {
        // Blocks
        DreamingWorld.getInstance().getBlockManager().registerBlock(new BasicManaGenerator());

        // Items
        new HotPeddle();
        new HotSword();
    }
}
