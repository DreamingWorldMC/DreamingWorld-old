package net.dreamingworld.gameplay.manacraft.researches.geminfusion;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import net.dreamingworld.gameplay.manacraft.researches.geminfusion.altar.MainPedestal;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class GemInfusionResearch extends Research {

    public GemInfusionResearch() {

        id = "gem_infusion";
        name = "Gem infusion";
        description = "Infusing stuff with gems";

        items = new HashMap<>();
        items.put("hot thing found in ore", "ignium");
        items.put("Like idk is mystic also it is dropped from grass", "mystic_peddle");
        items.put("hot thing found in ore", "ignium");
        items.put("under dirt not in underground form", Material.COBBLESTONE.toString());
        items.put("Light as air leaf", Material.FEATHER.toString());
        items.put("Steam is generating with it.", "boiler");
        items.put("A better source of fuel than ignium", "hard_coal");
        items.put("Cobble + furnace", Material.STONE.toString());

        book = (BookMeta) (new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage("Infusing items with gems and stuff");

        DreamingWorld.getInstance().getResearchManager().addParent("gem_infusion", "mana_gem");

        DreamingWorld.getInstance().getBlockManager().registerBlock(new MainPedestal());
    }
}
