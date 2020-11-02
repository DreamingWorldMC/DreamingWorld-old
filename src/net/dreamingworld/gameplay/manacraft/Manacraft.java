package net.dreamingworld.gameplay.manacraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.manacraft.blocks.BasicManaGenerator;
import net.dreamingworld.gameplay.manacraft.items.HotPeddle;

public class Manacraft {

    public static void initialize() {
        // Blocks
        DreamingWorld.getInstance().getBlockManager().registerBlock(new BasicManaGenerator());

        // Items
        new HotPeddle();
    }
}
