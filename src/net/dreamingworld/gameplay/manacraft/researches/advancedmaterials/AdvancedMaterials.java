package net.dreamingworld.gameplay.manacraft.researches.advancedmaterials;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import net.dreamingworld.gameplay.manacraft.researches.vapour.Boiler;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class AdvancedMaterials extends Research {

    public AdvancedMaterials() {
        id = "advanced_materials";
        name = "Advanced materials";
        description = "Advanced materials";

        items = new HashMap<>();
        items.put("hot thing found in ore", "ignium");
        items.put("under dirt not in underground form", Material.COBBLESTONE.toString());
        items.put("Baby wheat", Material.SEEDS.toString());

        book = (BookMeta)(new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage( "Materials are very important on your way to become stronger");

        book.addPage( "Ignium: \n \n Hardness: soft \n Mana conductivity: 7 \n \n Additional notes: very hot");

        book.addPage( "Manium: \n \n Hardness: soft \n Mana conductivity: 8 \n \n Additional notes: Breaks easily when mana flows throw it");

        book.addPage( "Energium: \n \n Hardness: Hard \n Mana conductivity: 2 \n \n Additional notes: Contains a lot of mana but when destroyed may explode");

        book.addPage( "Uranium: \n \n Hardness: Hard \n Mana conductivity: 2 \n \n Additional notes: Very radioactive but very useful for generating heat");

        book.addPage( "Mystic peddle: \n \n Hardness: Hard \n Mana conductivity: 0 \n \n Additional notes: Hmmmmmmmm");

        DreamingWorld.getInstance().getResearchManager().addParent("advanced_materials", "basics");
    }

}
