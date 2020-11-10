package net.dreamingworld.gameplay.bosskills.items;

import net.dreamingworld.DreamingWorld;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WitherHeart {
    public WitherHeart() {
        ItemStack item = new ItemStack(Material.INK_SACK);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_GRAY + "Wither heart");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "You slayed it...");
        meta.setLore(lore);

        meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("wither_heart", item);
    }
}
