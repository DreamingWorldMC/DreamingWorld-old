package net.dreamingworld.gameplay.manacraft.researches.advancedtools;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.research.Research;
import net.dreamingworld.gameplay.manacraft.researches.hotpeddle.HotPeddle;
import net.dreamingworld.gameplay.manacraft.researches.hotpeddle.HotStaff;
import net.dreamingworld.gameplay.manacraft.researches.hotpeddle.HotSword;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;

public class AdvancedTools extends Research {

    public AdvancedTools() {

        id = "advanced_tools";
        name = "Advanced tools";
        description = "Your journey is still on";

        items = new HashMap<>();
        items.put("hot thing found in ore", "ignium");
        items.put("Like idk is mystic also it is dropped from grass", "mystic_peddle");
        items.put("S  T  I  C  K", Material.STICK.toString());
        items.put("Ohh what is there shining? And it is an ingot?", Material.GOLD_INGOT.toString());
        items.put("The flesh of an undead foe", Material.ROTTEN_FLESH.toString());
        items.put("Core for mana and stuff", "mana_core");

        book = (BookMeta) (new ItemStack(Material.WRITTEN_BOOK).getItemMeta());

        book.addPage("Advanced tools they are better than normals ones yet are they that much more useful?");


        DreamingWorld.getInstance().getResearchManager().addParent("hot_peddle", "advanced_materials");

        new AdvancedStick();

    }
}