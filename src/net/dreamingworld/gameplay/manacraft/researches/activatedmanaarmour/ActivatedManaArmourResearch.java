package net.dreamingworld.gameplay.manacraft.researches.activatedmanaarmour;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class ActivatedManaArmourResearch extends Research {

    public ActivatedManaArmourResearch() {

        id = "activated_mana_armour";
        name = "Activating mana armour";
        description = "A perfect way of defence";

        items = new HashMap<>();
        items.put("hot thing found in ore", "ignium");
        items.put("Like idk is mystic also it is dropped from grass", "mystic_peddle");

        book = (BookMeta) (new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage("Good defence.");

        book.addPage("Helmet of vision: \n\n" +
                "Instability:0.05\n\n" +
                "Main item: Mana helmet\n\n" +
                "Gems:\n" +
                "-Mana: 12\n" +
                "-Air: 6");

        book.addPage("Items:\n" +
                "-Mana ingot\n" +
                "-Mana ingot\n" +
                "-Diamond\n" +
                "-Mana battery\n" +
                "-Mana covered stick\n" +
                "-Diamond helmet\n" +
                "-Gold block\n");

        book.addPage("");

        book.addPage("Chestplate of power: \n\n" +
                "Instability:0.05\n\n" +
                "Main item: Mana chestplate\n\n" +
                "Gems:\n" +
                "-Mana: 12\n" +
                "-Fire: 6");

        book.addPage("Items:\n" +
                "-Mana ingot\n" +
                "-Mana ingot\n" +
                "-Diamond\n" +
                "-Mana battery\n" +
                "-Obsidian\n" +
                "-Mana covered stick\n" +
                "-Diamond chestplate\n" +
                "-Gold block\n");

        book.addPage("");

        book.addPage("Leggings of rabbit: \n\n" +
                "Instability:0.05\n\n" +
                "Main item: Mana helmet\n\n" +
                "Gems:\n" +
                "-Mana: 12\n" +
                "-Water: 6");

        book.addPage("Items:\n" +
                "-Mana ingot\n" +
                "-Mana ingot\n" +
                "-Diamond\n" +
                "-Mana battery\n" +
                "-Obsidian\n" +
                "-Mana covered stick\n" +
                "-Mana covered stick\n" +
                "-Explorer's boots\n" +
                "-Diamond leggings\n" +
                "-Gold block\n");

        book.addPage("");

        book.addPage("Boots of ocelot: \n\n" +
                "Instability:0.05\n\n" +
                "Main item: Mana helmet\n\n" +
                "Gems:\n" +
                "-Mana: 12\n" +
                "-Earth: 6");

        book.addPage("Items:\n" +
                "-Mana ingot\n" +
                "-Mana ingot\n" +
                "-Mana ingot\n" +
                "-Diamond\n" +
                "-Mana battery\n" +
                "-Obsidian\n" +
                "-Mana covered stick\n" +
                "-Explorer's boots\n" +
                "-Diamond boots\n" +
                "-Gold block\n");

        DreamingWorld.getInstance().getResearchManager().addParent("activated_mana_armour", "mana_armour");

        new ActivatedManaArmour();
    }
}