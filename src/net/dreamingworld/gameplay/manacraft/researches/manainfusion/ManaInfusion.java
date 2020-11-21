package net.dreamingworld.gameplay.manacraft.researches.manainfusion;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class ManaInfusion extends Research {
    public ManaInfusion() {
        id = "mana_infusion";
        name = "Mana infusion";
        description = "Infusing mana in to stuff";

        items = new HashMap<>();
        items.put("Something magical found in ore", "manium");
        items.put("hot stones liquid ice on them", Material.OBSIDIAN.toString());
        items.put("Core for manipulating with mana", "mana_core");

        book = (BookMeta)(new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage( "Infusing mana in to different items like iron to mana ingot");

        DreamingWorld.getInstance().getResearchManager().addParent("mana_infusion", "mana_manipulation");

        DreamingWorld.getInstance().getBlockManager().registerBlock(new ManaInfuser());
        new ManaIngot();
    }
}
