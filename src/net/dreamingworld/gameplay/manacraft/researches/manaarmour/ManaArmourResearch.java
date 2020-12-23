package net.dreamingworld.gameplay.manacraft.researches.manaarmour;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class ManaArmourResearch extends Research {

    public ManaArmourResearch() {

        id = "mana_armour";
        name = "Mana armour";
        description = "Armour.";

        items = new HashMap<>();
        items.put("hot thing found in ore", "ignium");
        items.put("Like idk is mystic also it is dropped from grass", "mystic_peddle");
        items.put("Well diamonds do break obsidian.", Material.DIAMOND_PICKAXE.toString());
        items.put("speed, jump and well boots.", "explorer_boots");

        book = (BookMeta) (new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage("Better defence");

        DreamingWorld.getInstance().getResearchManager().addParent("mana_armour", "personal_mana_capacitors");

        new ManaArmour();
    }
}