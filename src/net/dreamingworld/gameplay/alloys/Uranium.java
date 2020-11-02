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

public class Uranium extends OreAlloy {

    public Uranium() {
        chance = 1;
        dropOnBreak = false;
        dropOnSmelt = true;

        item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_GREEN + "Uranium");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "A bit radioactive.");
        meta.setLore(lore);

        meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("uranium", item);
    }
}
