package net.dreamingworld.gameplay.manacraft.researches.plantsandcreatures;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import net.dreamingworld.gameplay.manacraft.researches.plantsandcreatures.whitepyramidtree.*;
import net.dreamingworld.gameplay.trees.IronTree;
import net.dreamingworld.gameplay.trees.WhitePyramidTree;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class PlantsAndCreatures extends Research {
    public PlantsAndCreatures() {
        id = "plants_and_creatures";
        name = "Plants and creatures";
        description = "plants and creatures";

        items = new HashMap<>();
        items.put("Something magical found in ore", "manium");
        items.put("yellow plant in the grass", Material.YELLOW_FLOWER.toString());

        book = (BookMeta)(new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage( "Living organisms are fascinating, especially when they are alive and well, there bodies work smooth and normally perfectly. \n \nWhat if you could create them yourself?");

        DreamingWorld.getInstance().getResearchManager().addParent("plants_and_creatures", "basics");

        DreamingWorld.getInstance().getBlockManager().registerBlock(new WhitePlanks());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new HolyLeafBlock());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new WhiteLogBlock());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new WhitePyramidTreeSapling());

        // Structures
        DreamingWorld.getInstance().getStructureManager().registerStructure("white_pyramid_tree", new WhitePyramidTree());
    }
}
