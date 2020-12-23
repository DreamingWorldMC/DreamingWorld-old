package net.dreamingworld.gameplay.manacraft.researches.manamanipulation;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ManaGlass implements Listener {

    public ManaGlass() {
        ItemStack item = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_PURPLE + "Mana glass");

        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Shows stats about block you just clicked");
        lore.add(ChatColor.GRAY + "I think you should not try to fill it...");

        meta.setLore(lore);
        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("mana_glass", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { " G ", "MSM", " G " });
        recipe.setVanillaIngredient('S', Material.THIN_GLASS);
        recipe.setVanillaIngredient('G', Material.GOLD_INGOT);
        recipe.setCustomIngredient('M', "manium");

        recipe.setResearch("mana_manipulation");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getItem() == null || e.getClickedBlock() == null || !DreamingWorld.getInstance().getItemManager().checkItemAuthenticity(e.getItem(), "mana_glass")) {
            return;
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            e.setCancelled(true);

            String maxmana = DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(e.getClickedBlock().getLocation(), "capacity");
            String mana = DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(e.getClickedBlock().getLocation(), "storedMana");
            String inputs = DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(e.getClickedBlock().getLocation(), "inputs");
            String vapour = DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(e.getClickedBlock().getLocation(), "vapour");
            String fuel = DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(e.getClickedBlock().getLocation(), "fuel_left");

            if (mana != null) {
                e.getPlayer().sendMessage(Util.formatString("&bContains: &7" + mana + " §9lmml"));
            }

            if (maxmana != null) {
                e.getPlayer().sendMessage(Util.formatString("&bCan contain: &7" + maxmana + " §9lmml"));
            }

            if (inputs != null) {
                e.getPlayer().sendMessage(Util.formatString("&bCapacitors it takes mana from: &7" + inputs));
            }

            if (vapour != null) {
                e.getPlayer().sendMessage(Util.formatString("&bIt has: &7" + vapour + " §9units of vapour left"));
            }

            if (fuel != null) {
                e.getPlayer().sendMessage(Util.formatString("&bIt has: &7" + fuel + " §9units of fuel left"));
            }
        }
    }
}
