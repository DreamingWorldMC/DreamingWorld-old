package net.dreamingworld.gameplay.alloys;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.alloys.OreAlloy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MysticPeddle extends OreAlloy {

    public MysticPeddle() {
        chance = 4;
        dropOnBreak = true;
        dropOnSmelt = true;

        item = new ItemStack(Material.FLINT);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "Mystic Peddle");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("mysticPeddle", item);
    }
}
