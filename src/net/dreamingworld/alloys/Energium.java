package net.dreamingworld.alloys;

import net.dreamingworld.DreamingWorld;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Energium extends OreAlloy {

    public Energium() {
        chance = 10;
        dropOnBreak = true;
        dropOnSmelt = true;

        item = new ItemStack(Material.FLINT);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Energium");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "This stone is ignium but in flint");
        lore.add(ChatColor.GREEN + "100% eco friendly");
        meta.setLore(lore);

        meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("energium", item);
    }
}
