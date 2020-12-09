package net.dreamingworld.gameplay.manacraft.researches.geminfusion;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.geminfusion.GemInfusionRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManaCoveredStick {
    public ManaCoveredStick() {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Mana covered stick");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("mana_covered_stick", item);

        GemInfusionRecipe recipe = new GemInfusionRecipe();

        recipe.setReserach("gem_infusion");

        recipe.setInstability(0f);

        recipe.setResult(item);

        Map<String, Integer> gems = new HashMap<>();

        gems.put("mana", 3);

        recipe.setGems(gems);

        recipe.setMainItem("advanced_rod");

        List<String> items = new ArrayList<>();

        items.add("mana_ingot");
        items.add("mana_ingot");
        items.add("mana_ingot");

        recipe.setItems(items);

        DreamingWorld.getInstance().getGemInfusionManager().addRecipe(recipe);
    }
}
