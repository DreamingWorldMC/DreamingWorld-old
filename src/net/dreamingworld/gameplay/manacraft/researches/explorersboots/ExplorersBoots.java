package net.dreamingworld.gameplay.manacraft.researches.explorersboots;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.customentities.EntityGeneration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExplorersBoots{
    public ExplorersBoots () {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_PURPLE + "Explorer's boots");

        List<String> lore = new ArrayList<>();
        lore.add(Util.formatString("Gives you speed and jump boost when wearing."));

        meta.setLore(lore);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("explorer_boots", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("explorer_boots", 3);
        DreamingWorld.getInstance().getCustomArmor().addHeadPiece("explorer_boots");

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "M M", "I I", "D D" });
        recipe.setCustomIngredient('I', "mana_ingot");
        recipe.setVanillaIngredient('D', Material.FEATHER);
        recipe.setCustomIngredient('M', "advanced_rod");
        recipe.setResearch("explorer_boots");
        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);


        Bukkit.getScheduler().runTaskTimer(DreamingWorld.getInstance(), () -> {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.getEquipment().getBoots() != null && TagWizard.getItemTag(p.getEquipment().getBoots(), "id") != null && TagWizard.getItemTag(p.getEquipment().getBoots(), "id").equals("explorer_boots")) {
                    p.removePotionEffect(PotionEffectType.SPEED);
                    p.removePotionEffect(PotionEffectType.JUMP);

                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 2));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 2));
                }
            }

        }, 0, 12);
    }
}
