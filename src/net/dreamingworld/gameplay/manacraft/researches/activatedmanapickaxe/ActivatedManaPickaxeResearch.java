package net.dreamingworld.gameplay.manacraft.researches.activatedmanapickaxe;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class ActivatedManaPickaxeResearch extends Research {

    public ActivatedManaPickaxeResearch() {

        id = "activated_mana_pickaxe";
        name = "Activating mana pickaxe";
        description = "A perfect pickaxe for now";

        items = new HashMap<>();
        items.put("hot thing found in ore", "ignium");
        items.put("Like idk is mystic also it is dropped from grass", "mystic_peddle");
        items.put("Well diamonds do break obsidian.", Material.DIAMOND_PICKAXE.toString());
        items.put("Mana found in ore.", "manium");

        book = (BookMeta) (new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage("You have activated it.");

        book.addPage("Activated mana pickaxe: \n\n" +
                "Instability:0.01\n\n" +
                "Main item: Mana pickaxe\n\n" +
                "Gems:\n" +
                "-Water: 5\n" +
                "-Earth: 10\n" +
                "-Mana: 10\n" +
                "-Air: 2\n");

        book.addPage("Items:\n" +
                "-Mana ingot\n" +
                "-Mana ingot\n" +
                "-Diamond" +
                "-Mana battery" +
                "-Obsidian" +
                "-Gold block");

        DreamingWorld.getInstance().getResearchManager().addParent("activated_mana_pickaxe", "mana_pickaxe");

        new ActivatedManaPickaxe();
    }
}