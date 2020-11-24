package net.dreamingworld.gameplay.manacraft.researches.astrum;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class Astrum extends Research {
    public Astrum() {

        id = "astrum";
        name = "Astrum";
        description = "Looking at the skies";

        items = new HashMap<>();
        items.put("Hot thing found in ore", "ignium");
        items.put("Dust from the middle of the system, from where?", "star_dust");
        items.put("Capacitor for mana", "mana_capacitor");
        items.put("Block in anvil craft", Material.IRON_BLOCK.toString());
        items.put("Dirt", Material.DIRT.toString());

        book = (BookMeta) (new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage("Hot peddle was found to be pretty interesting. \n When touching radiation it produces a pretty big spark and it is also similar to iron");


        DreamingWorld.getInstance().getResearchManager().addParent("astrum", "advanced_tools");

        new StarDust();
        new AstrumIngot();
        new AstrumArmor();
        new AstrumSword();
    }
}
