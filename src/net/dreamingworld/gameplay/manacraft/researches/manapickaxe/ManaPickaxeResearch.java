package net.dreamingworld.gameplay.manacraft.researches.manapickaxe;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class ManaPickaxeResearch extends Research {

    public ManaPickaxeResearch() {

        id = "mana_pickaxe";
        name = "Mana pickaxe";
        description = "A perfect pickaxe to last long time";

        items = new HashMap<>();
        items.put("hot thing found in ore", "ignium");
        items.put("Like idk is mystic also it is dropped from grass", "mystic_peddle");
        items.put("Well diamonds do break obsidian.", Material.DIAMOND_PICKAXE.toString());

        book = (BookMeta) (new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage("Good for murdering those pesky creatures.");


        DreamingWorld.getInstance().getResearchManager().addParent("mana_pickaxe", "personal_mana_capacitor");

        new ManaPickaxe();
    }
}