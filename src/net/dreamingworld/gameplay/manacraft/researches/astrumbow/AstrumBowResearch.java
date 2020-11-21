package net.dreamingworld.gameplay.manacraft.researches.astrumbow;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import net.dreamingworld.gameplay.manacraft.researches.hotpeddle.HotPeddle;
import net.dreamingworld.gameplay.manacraft.researches.hotpeddle.HotStaff;
import net.dreamingworld.gameplay.manacraft.researches.hotpeddle.HotSword;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class AstrumBowResearch extends Research {

    public AstrumBowResearch() {

        id = "astrum_bow";
        name = "Astrum bow";
        description = "When shooting earthly objects is not enough";

        items = new HashMap<>();
        items.put("hot thing found in ore", "ignium");
        items.put("Like idk is mystic also it is dropped from grass", "mystic_peddle");
        items.put("Sticks and string and not a rod", Material.BOW.toString());
        items.put("Light and nearly hair but...", Material.FEATHER.toString());

        book = (BookMeta) (new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage("Good for murdering those pesky creatures.");


        DreamingWorld.getInstance().getResearchManager().addParent("astrum_bow", "astrum");

        new AstrumBow();

    }
}