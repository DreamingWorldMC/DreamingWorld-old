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

public class Manium extends OreAlloy {

    public Manium() {
        chance = 5;
        dropOnBreak = false;
        dropOnSmelt = true;

        item = new ItemStack(Material.INK_SACK);
        item.setDurability((short) 6);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_AQUA + "Manium");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "A bit crystal like");
        meta.setLore(lore);

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("manium", item);
    }
}
