package net.dreamingworld.gameplay.manacraft.researches.manamanipulation;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class manaManipulation extends Research {
    public manaManipulation() {
        id = "mana_manipulation";
        name = "Mana manipulation";
        description = "Manipulating basic mana";

        items = new HashMap<>();
        items.put("Something magical found in ore", "manium");
        items.put("White and cool looking", "white_planks");
        items.put("Energy found in ore, that looks like an item", "energium");

        book = (BookMeta)(new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage( "Mana is a basic element on your way to master the world. \n It has it's own laws and mechanics but is very useful.");

        DreamingWorld.getInstance().getResearchManager().addParent("mana_manipulation", "basics");

        new ManaFlowBinder();
        new ManaCore();
        new ManaGlass();


        DreamingWorld.getInstance().getBlockManager().registerBlock(new ManaCapacitor());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new BasicManaGenerator());
    }
}
