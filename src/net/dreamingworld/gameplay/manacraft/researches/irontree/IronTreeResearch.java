package net.dreamingworld.gameplay.manacraft.researches.irontree;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import net.dreamingworld.gameplay.manacraft.researches.manainfusion.ManaInfuser;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class IronTreeResearch extends Research {
    public IronTreeResearch() {
        id = "iron_tree";
        name = "Iron tree";
        description = "Iron trees and green iron";

        items = new HashMap<>();
        items.put("Something magical found in ore", "manium");
        items.put("hot stones liquid ice on them", Material.OBSIDIAN.toString());
        items.put("____ Tree", Material.IRON_INGOT.toString());

        book = (BookMeta)(new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage( "Making your own tree sapplings maybe out of iron but still this is useful, maybe you could get some special iron out of this");

        DreamingWorld.getInstance().getResearchManager().addParent("iron_tree", "plants_and_creatures");

        DreamingWorld.getInstance().getBlockManager().registerBlock(new IronTreeSapling());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new IronLeafBlock());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new IronWoodBlock());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new GreenIronMaker());

        new GreenIron();
        new GreenIronArmor();

        DreamingWorld.getInstance().getStructureManager().registerStructure("iron_tree", new IronTree());
    }
}
