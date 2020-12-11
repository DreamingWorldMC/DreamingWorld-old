package net.dreamingworld.gameplay.manacraft.researches.personalmanacapacitors;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class PersonalManaCapacitors extends Research {
    public PersonalManaCapacitors() {
        id = "personal_mana_capacitors";
        name = "Personal mana capacitors";
        description = "Tools which don't have durability.";

        items = new HashMap<>();
        items.put("hot thing found in ore", "ignium");
        items.put("Like idk is mystic also it is dropped from grass", "mystic_peddle");
        items.put("Capacitor.", "mana_capacitor");

        book = (BookMeta) (new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage("Charging items and other stuff.");

        book.addPage("Charger:\n\n" +
                "Instability: 0\n\n" +
                "Gems required:\n" +
                " - Mana gem: 5\n\n" +
                "Main item: Iron block\n\n" +
                "Items required:\n" +
                " - Mana ingot\n" +
                " - Mana ingot \n" +
                " - Mana battery");


        DreamingWorld.getInstance().getResearchManager().addParent("personal_mana_capacitors", "gem_infusion");

        new Charger();
        new ManaBattery();
    }
}
