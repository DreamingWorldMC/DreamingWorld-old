package net.dreamingworld.gameplay.manacraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.manacraft.armor.GreenIronArmor;
import net.dreamingworld.gameplay.manacraft.blocks.*;
import net.dreamingworld.gameplay.manacraft.items.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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

        // Armor
        new GreenIronArmor();

        // Blocks
        DreamingWorld.getInstance().getBlockManager().registerBlock(new BasicManaGenerator());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new IronTreeSapling());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new IronLeafBlock());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new GreenIronMaker());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new IronWoodBlock());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new ManaCapacitor());

        // Fusion
        BasicManaGenerator.addResult(DreamingWorld.getInstance().getItemManager().get("ignium"), new BasicManaGenerator.FusionResult(100, 100, 1, new ItemStack(Material.COAL), 1, DreamingWorld.getInstance().getItemManager().get("hard_coal")));

        long time = System.currentTimeMillis() - begin;

        DreamingWorld.getInstance().getLogger().info("Manacraft initialized [" + time + " ms]");
    }
}
