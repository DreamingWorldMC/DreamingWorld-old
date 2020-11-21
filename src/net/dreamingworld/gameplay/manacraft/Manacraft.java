package net.dreamingworld.gameplay.manacraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.manacraft.armor.AstrumArmor;
import net.dreamingworld.gameplay.manacraft.researches.irontree.GreenIronArmor;
import net.dreamingworld.gameplay.manacraft.blocks.*;
import net.dreamingworld.gameplay.manacraft.items.*;
import net.dreamingworld.gameplay.manacraft.mobs.AstralCreature;
import net.dreamingworld.gameplay.manacraft.researches.irontree.*;
import net.dreamingworld.gameplay.manacraft.researches.manainfusion.ManaInfusion;
import net.dreamingworld.gameplay.manacraft.researches.manamanipulation.BasicManaGenerator;
import net.dreamingworld.gameplay.manacraft.researches.manamanipulation.ManaManipulation;
import net.dreamingworld.gameplay.manacraft.researches.plantsandcreatures.PlantsAndCreatures;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Manacraft {

    public static void initialize() {
        long begin = System.currentTimeMillis();

        // Researches
        DreamingWorld.getInstance().getResearchManager().addResearch(new ManaManipulation());
        DreamingWorld.getInstance().getResearchManager().addResearch(new ManaInfusion());
        DreamingWorld.getInstance().getResearchManager().addResearch(new PlantsAndCreatures());
        DreamingWorld.getInstance().getResearchManager().addResearch(new IronTreeResearch());

        // Items
        new HotPeddle();
        new HotSword();
        new AdvancedStick();
        new HotStaff();
        new HardCoal();
        new ManaIngot();
        new AstrumIngot();
        new StarDust();
        new AstrumSword();

        // Armor
        new AstrumArmor();

        // Blocks
        DreamingWorld.getInstance().getBlockManager().registerBlock(new Boiler());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new SteamTurbine());

        // Mobs
        DreamingWorld.getInstance().getEntityManager().addEntity("astral_creature", new AstralCreature());

        long time = System.currentTimeMillis() - begin;

        DreamingWorld.getInstance().getLogger().info("Manacraft initialized [" + time + " ms]");
    }
}
