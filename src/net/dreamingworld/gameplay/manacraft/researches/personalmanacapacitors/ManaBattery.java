package net.dreamingworld.gameplay.manacraft.researches.personalmanacapacitors;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ManaBattery {
    public ManaBattery() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Mana battery");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add(Util.formatString("&b[&f0&b/&f200lmml&b]"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        TagWizard.addItemTag(item, "mana", "0");
        TagWizard.addItemTag(item, "max_mana", "200");
        TagWizard.addItemTag(item, "mana_line", "0");

        DreamingWorld.getInstance().getItemManager().registerItem("mana_battery", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { " G ", "GCG", "GSG" });
        recipe.setCustomIngredient('G', "mana_ingot");
        recipe.setCustomIngredient('C', "mana_core");
        recipe.setCustomIngredient('S', "uranium");

        recipe.setResearch("personal_mana_capacitors");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }
}
