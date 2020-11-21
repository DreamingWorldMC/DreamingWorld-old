package net.dreamingworld.gameplay.manacraft.researches.vapour;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class Vapour extends Research {
    public Vapour() {
        id = "vapour";
        name = "Steam generation";
        description = "Making steam!";

        items = new HashMap<>();
        items.put("Something magical found in ore", "manium");
        items.put("mana generating thing", "basic_mana_generator");
        items.put("Core for manipulating with mana", "mana_core");
        items.put("hot thing found in ore", "ignium");
        items.put("light and white and ends with r", Material.FEATHER.toString());

        book = (BookMeta)(new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage( "Make sure there is static water underneath; use ignium as fuel or hard coal as better fuel.");

        DreamingWorld.getInstance().getResearchManager().addParent("vapour", "mana_manipulation");

        DreamingWorld.getInstance().getBlockManager().registerBlock(new Boiler());
    }
}
