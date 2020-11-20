package net.dreamingworld.gameplay.manacraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.manacraft.armor.AstrumArmor;
import net.dreamingworld.gameplay.manacraft.armor.GreenIronArmor;
import net.dreamingworld.gameplay.manacraft.blocks.*;
import net.dreamingworld.gameplay.manacraft.items.*;
import net.dreamingworld.gameplay.manacraft.mobs.AstralCreature;
import net.dreamingworld.gameplay.manacraft.researches.manamanipulation.manaManipulation;
import net.dreamingworld.gameplay.trees.IronTree;
import net.dreamingworld.gameplay.trees.WhitePyramidTree;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Manacraft {

    public static void initialize() {
        long begin = System.currentTimeMillis();

        // Items
        new HotPeddle();
        new HotSword();
        new AdvancedStick();
        new HotStaff();
        new HardCoal();
        new GreenIron();
        new ManaIngot();
        new AstrumIngot();
        new StarDust();
        new AstrumSword();

        // Armor
        new GreenIronArmor();
        new AstrumArmor();

        // Blocks
        DreamingWorld.getInstance().getBlockManager().registerBlock(new BasicManaGenerator());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new IronTreeSapling());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new IronLeafBlock());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new GreenIronMaker());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new IronWoodBlock());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new ManaInfuser());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new HolyLeafBlock());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new WhiteLogBlock());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new Boiler());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new SteamTurbine());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new WhitePlanks());

        // Mobs
        DreamingWorld.getInstance().getEntityManager().addEntity("astral_creature", new AstralCreature());

        // Fusion
        BasicManaGenerator.addResult(DreamingWorld.getInstance().getItemManager().get("ignium"), new BasicManaGenerator.FusionResult(100, 100, 1, new ItemStack(Material.COAL), 1, DreamingWorld.getInstance().getItemManager().get("hard_coal")));

        // Structures
        DreamingWorld.getInstance().getStructureManager().registerStructure("iron_tree", new IronTree());
        DreamingWorld.getInstance().getStructureManager().registerStructure("white_pyramid_tree", new WhitePyramidTree());

        // Researches
        DreamingWorld.getInstance().getResearchManager().addResearch(new manaManipulation());

        long time = System.currentTimeMillis() - begin;

        DreamingWorld.getInstance().getLogger().info("Manacraft initialized [" + time + " ms]");
    }
}
