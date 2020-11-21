package net.dreamingworld.gameplay.manacraft.researches.steamtomana;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import net.dreamingworld.gameplay.manacraft.researches.vapour.Boiler;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class SteamToMana extends Research {
    public SteamToMana() {
        id = "steam_to_mana";
        name = "Steam turbine";
        description = "Makes mana from steam!";

        items = new HashMap<>();
        items.put("found under ground and pretty week but very good for breaking", Material.GOLD_INGOT.toString());
        items.put("So there was mana in ingot", "mana_ingot");
        items.put("A blade forged from iron", Material.IRON_SWORD.toString());

        book = (BookMeta)(new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage( "Uses vapour from boiler to generate mana then gives part of the mana it received to block above it. \n \n Useful to generate massive amounts of mana");

        DreamingWorld.getInstance().getResearchManager().addParent("steam_to_mana", "vapour");

        DreamingWorld.getInstance().getBlockManager().registerBlock(new SteamTurbine());
    }
}
