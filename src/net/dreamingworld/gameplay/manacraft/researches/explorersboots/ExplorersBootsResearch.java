package net.dreamingworld.gameplay.manacraft.researches.explorersboots;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class ExplorersBootsResearch extends Research {

    public ExplorersBootsResearch() {
        id = "explorer_boots";
        name = "Explorer's boots";
        description = "Exploration made easy";

        items = new HashMap<>();
        items.put("hot thing found in ore", "ignium");
        items.put("under dirt not in underground form", Material.COBBLESTONE.toString());
        items.put("Light as air leaf", Material.FEATHER.toString());
        items.put("Leather", Material.LEATHER.toString());

        book = (BookMeta)(new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage( "Very good for exploration yet not a good source of armour");

        DreamingWorld.getInstance().getResearchManager().addParent("explorer_boots", "advanced_tools");

        new ExplorersBoots();
    }

}
