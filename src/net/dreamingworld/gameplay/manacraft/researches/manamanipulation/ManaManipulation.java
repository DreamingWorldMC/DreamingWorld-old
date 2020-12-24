package net.dreamingworld.gameplay.manacraft.researches.manamanipulation;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class ManaManipulation extends Research {

    public ManaManipulation() {
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

        new ManaCore();
        new ManaFlowBinder();
        new ManaGlass();
        new HardCoal();

        DreamingWorld.getInstance().getBlockManager().registerBlock(new ManaCapacitor());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new BasicManaGenerator());

        BasicManaGenerator.addResult(new ItemStack(Material.COAL), 1);
        BasicManaGenerator.addResult(new ItemStack(Material.COAL_BLOCK), 10);
        BasicManaGenerator.addResult(DreamingWorld.getInstance().getItemManager().get("ignium"), 5);
        BasicManaGenerator.addResult(DreamingWorld.getInstance().getItemManager().get("hard_coal"), 20);
    }
}
