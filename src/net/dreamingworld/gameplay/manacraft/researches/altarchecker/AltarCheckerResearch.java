package net.dreamingworld.gameplay.manacraft.researches.altarchecker;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import net.dreamingworld.gameplay.manacraft.researches.geminfusion.ManaCoveredStick;
import net.dreamingworld.gameplay.manacraft.researches.geminfusion.altar.AltarActivator;
import net.dreamingworld.gameplay.manacraft.researches.geminfusion.altar.MainPedestal;
import net.dreamingworld.gameplay.manacraft.researches.geminfusion.altar.Pedestal;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class AltarCheckerResearch extends Research {

    public AltarCheckerResearch() {

        id = "altar_checker";
        name = "altar checker";
        description = "When you need to check before doing.";

        items = new HashMap<>();
        items.put("Hot thing found in ore", "ignium");
        items.put("Covered in mana I am. Stick I am", "mana_covered_stick");

        book = (BookMeta) (new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage("Checks if craft be stable or not.");

        book.addPage("Altar cheacker:\n\n" +
                "Instability: 0\n\n" +
                "Gems required:\n" +
                " - Mana gem: 3\n\n" +
                " - Water gem: 3\n\n" +
                "Main item: Mana covered stick\n\n" +
                "Items required:\n" +
                " - Mana ingot\n" +
                " - Diamond");

        DreamingWorld.getInstance().getResearchManager().addParent("altar_checker", "gem_infusion");

        new AltarChecker();
    }
}
