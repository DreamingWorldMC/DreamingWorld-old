package net.dreamingworld.gameplay.manacraft.researches.manapickaxe;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ManaPickaxe implements Listener {
    public ManaPickaxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Mana pickaxe");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "When ever breaks a block uses 10 mana");
        lore.add(Util.formatString("&b[&f0&b/&f1000lmml&b]"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        TagWizard.addItemTag(item, "mana", "0");
        TagWizard.addItemTag(item, "max_mana", "1000");
        TagWizard.addItemTag(item, "mana_line", "1");

        DreamingWorld.getInstance().getItemManager().registerItem("mana_pickaxe", item);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "GGG", " C ", " S " });
        recipe.setCustomIngredient('G', "mana_ingot");
        recipe.setCustomIngredient('C', "mana_battery");
        recipe.setCustomIngredient('S', "mana_covered_stick");

        recipe.setResearch("mana_pickaxe");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if ("mana_pickaxe".equals(TagWizard.getItemTag(e.getPlayer().getItemInHand(), "id"))) {
            if (Integer.parseInt(TagWizard.getItemTag(e.getPlayer().getItemInHand(), "mana")) - 10 >= 0) {
                TagWizard.addItemTag(e.getPlayer().getItemInHand(), "mana", String.valueOf(Integer.parseInt(TagWizard.getItemTag(e.getPlayer().getItemInHand(), "mana")) - 10));

                List<String> lore = e.getPlayer().getItemInHand().getItemMeta().getLore();

                lore.set(1, Util.formatString("&b[&f" + TagWizard.getItemTag(e.getPlayer().getItemInHand(), "mana") + "&b/&f1000lmml&b]"));
                ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
                meta.setLore(lore);
                e.getPlayer().getItemInHand().setItemMeta(meta);
            }
            else {
                e.getPlayer().sendMessage(ChatColor.DARK_RED + "You do not have enough mana to use this item");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if ("mana_pickaxe".equals(TagWizard.getItemTag(e.getItem(), "id"))) {
            e.getItem().setDurability((short) 0);
        }
    }
}
