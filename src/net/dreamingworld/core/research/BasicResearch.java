package net.dreamingworld.core.research;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class BasicResearch extends Research {

    public BasicResearch() {
        id = "basics";
        name = "Basics";
        description = "basics of research";

        items = new HashMap<>();
        items.put("Something hot found in ore.", "ignium");
        items.put("White and holy", "white_log");

        book = (BookMeta) (new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage("This is basic research which you can make using a crafting table and research using research table it is used to have other researches as it's children");
    }
}
