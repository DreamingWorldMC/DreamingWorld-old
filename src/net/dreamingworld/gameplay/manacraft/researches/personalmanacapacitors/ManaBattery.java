package net.dreamingworld.gameplay.manacraft.researches.personalmanacapacitors;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ManaBattery {
    public ManaBattery() {
        ItemStack item = new ItemStack(Material.NETHER_BRICK_ITEM);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Mana battery");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        TagWizard.addItemTag(item, "mana", "0");
        TagWizard.addItemTag(item, "max_mana", "200");
        TagWizard.addItemTag(item, "mana_line", "0");

        List<String> lore = item.getItemMeta().getLore();
        lore.add(Util.formatString("&b[&f" + TagWizard.getItemTag(item, "mana" + "&b/&f" + TagWizard.getItemTag(item, "max_mana") + "&b]")));

        meta.setLore(lore);
        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem("mana_battery", item);
    }
}
