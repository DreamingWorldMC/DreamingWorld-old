package net.dreamingworld.gameplay.alloys;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.alloys.OreAlloy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Ignium extends OreAlloy {

    public Ignium() {
        chance = 100;
        dropOnBreak = true;
        dropOnSmelt = true;

        item = new ItemStack(Material.INK_SACK);
        item.setDurability((short) 14);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "Ignium");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Hot... Very hot...");
        lore.add(ChatColor.GRAY + "No, it is not an amd gpu");
        meta.setLore(lore);

        meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("ignium", item);
    }
}
