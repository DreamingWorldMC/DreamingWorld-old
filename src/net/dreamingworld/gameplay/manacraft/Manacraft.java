package net.dreamingworld.gameplay.manacraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.manacraft.Armor.GreenIronArmor;
import net.dreamingworld.gameplay.manacraft.blocks.*;
import net.dreamingworld.gameplay.manacraft.items.*;

public class Manacraft {

    public static void initialize() {
        long begin = System.currentTimeMillis();

        // Items
        new ManaFlowBinder();
        new AdvancedStick();
        new HotPeddle();
        new HotSword();
        new ManaCore();
        new HardCoal();
        new GreenIron();

        // armor
        new GreenIronArmor();

        // Blocks
        DreamingWorld.getInstance().getBlockManager().registerBlock(new BasicManaGenerator());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new IronTreeSapling());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new IronLeafBlock());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new GreenIronMaker());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new IronWoodBlock());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new ManaCapacitor());

        long time = System.currentTimeMillis() - begin;

        DreamingWorld.getInstance().getLogger().info("Manacraft initialized [" + time + " ms]");
    }
}
