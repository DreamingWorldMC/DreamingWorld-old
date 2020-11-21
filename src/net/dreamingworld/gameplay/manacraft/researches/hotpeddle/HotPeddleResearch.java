package net.dreamingworld.gameplay.manacraft.researches.hotpeddle;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class HotPeddleResearch extends Research {

    public HotPeddleResearch() {

        id = "hot_peddle";
        name = "Hot peddle";
        description = "Hot peddle";

        items = new HashMap<>();
        items.put("hot thing found in ore", "ignium");
        items.put("Like idk is mystic also it is dropped from grass", "mystic_peddle");

        book = (BookMeta) (new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage("Hot peddle was found to be pretty interesting. \n When touching radiation it produces a pretty big spark and it is also similar to iron");


        DreamingWorld.getInstance().getResearchManager().addParent("hot_peddle", "advanced_materials");

        new HotPeddle();
        new HotStaff();
        new HotSword();

    }
}