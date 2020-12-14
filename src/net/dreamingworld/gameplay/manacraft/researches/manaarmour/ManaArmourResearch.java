package net.dreamingworld.gameplay.manacraft.researches.manaarmour;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import net.dreamingworld.gameplay.manacraft.researches.manapickaxe.ManaPickaxe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class ManaArmourResearch extends Research {

    public ManaArmourResearch() {

        id = "mana_armour";
        name = "Mana armour";
        description = "A perfect way of defence";

        items = new HashMap<>();
        items.put("hot thing found in ore", "ignium");
        items.put("Like idk is mystic also it is dropped from grass", "mystic_peddle");
        items.put("Well diamonds do break obsidian.", Material.DIAMOND_PICKAXE.toString());

        book = (BookMeta) (new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage("Good defence");

        DreamingWorld.getInstance().getResearchManager().addParent("mana_armour", "personal_mana_capacitor");

        new ManaArmour();
    }
}