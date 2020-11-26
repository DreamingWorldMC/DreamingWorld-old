package net.dreamingworld.gameplay.manacraft.researches.managem;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class ManaGem extends Research {

    public ManaGem() {
        id = "mana_gem";
        name = "Mana gem";
        description = "Infusing mana in to a gem";

        items = new HashMap<>();
        items.put("hot thing found in ore", "ignium");
        items.put("under dirt not in underground form", Material.COBBLESTONE.toString());
        items.put("Light as air leaf", Material.FEATHER.toString());
        items.put("Steam is generating with it.", "boiler");
        items.put("A better source of fuel than ignium", "hard_coal");
        items.put("Cobble + furnace", Material.STONE.toString());

        book = (BookMeta)(new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage( "Infusing a blank gem with mana makes a mana gem. Now what else can we make blank");

        DreamingWorld.getInstance().getResearchManager().addParent("mana_gem", "mana_infusion");

        new BlankGem();
        new FireGem();
        new ManaGemItem();
    }

}
