package net.dreamingworld.gameplay.manacraft.researches.geminfusion.altar;

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
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AltarActivator implements Listener {

    public AltarActivator() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_PURPLE + "Altar activator");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.WHITE + "Shift + click to suck in all gems in to altar activator");
        lore.add("");
        lore.add(ChatColor.WHITE + "Gems:");
        lore.add(Util.formatString("&7- &eAir gems: &f0"));
        lore.add(Util.formatString("&7- &eAir gems: &f0"));
        lore.add(Util.formatString("&7- &4Fire gems: &f0"));
        lore.add(Util.formatString("&7- &bMana gems: &f0"));
        lore.add(Util.formatString("&7- &8Earth gems: &f0"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem("altar_activator", item);


        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { " I ", "I I", " B " });
        recipe.setCustomIngredient('I', "mana_ingot");
        recipe.setCustomIngredient('B', "ignium");

        recipe.setResearch("gem_infusion");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        int slot = 0;
        if (e.getPlayer().isSneaking() && e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (e.getPlayer().getFireTicks() < 2) {

                if (TagWizard.getItemTag(e.getItem(), "version") == null || !TagWizard.getItemTag(e.getItem(), "version").equals("awfme")) {
                    TagWizard.addItemTag(e.getPlayer().getItemInHand(), "air", "0");
                    TagWizard.addItemTag(e.getPlayer().getItemInHand(), "water", "0");
                    TagWizard.addItemTag(e.getPlayer().getItemInHand(), "fire", "0");
                    TagWizard.addItemTag(e.getPlayer().getItemInHand(), "mana", "0");
                    TagWizard.addItemTag(e.getPlayer().getItemInHand(), "earth", "0");

                    TagWizard.addItemTag(e.getPlayer().getItemInHand(), "version", "awfme");

                    e.getPlayer().getItemInHand().setType(Material.FLINT);
                    return;
                }

                for (ItemStack x : e.getPlayer().getInventory().getContents()) {
                    if (x != null && x.getItemMeta() != null && TagWizard.getItemTag(x, "id") != null && TagWizard.getItemTag(x, "id").endsWith("_gem")) {
                        if (TagWizard.getItemTag(x, "id").equals("air_gem")) {
                            TagWizard.addItemTag(e.getPlayer().getItemInHand(), "air", String.valueOf(x.getAmount() + Integer.parseInt(TagWizard.getItemTag(e.getPlayer().getItemInHand(), "air"))));
                        } else if (TagWizard.getItemTag(x, "id").equals("water_gem")) {
                            TagWizard.addItemTag(e.getPlayer().getItemInHand(), "water", String.valueOf(x.getAmount() + Integer.parseInt(TagWizard.getItemTag(e.getPlayer().getItemInHand(), "water"))));
                        } else if (TagWizard.getItemTag(x, "id").equals("fire_gem")) {
                            TagWizard.addItemTag(e.getPlayer().getItemInHand(), "fire", String.valueOf(x.getAmount() + Integer.parseInt(TagWizard.getItemTag(e.getPlayer().getItemInHand(), "fire"))));
                        } else if (TagWizard.getItemTag(x, "id").equals("earth_gem")) {
                            TagWizard.addItemTag(e.getPlayer().getItemInHand(), "earth", String.valueOf(x.getAmount() + Integer.parseInt(TagWizard.getItemTag(e.getPlayer().getItemInHand(), "earth"))));
                        } else if (TagWizard.getItemTag(x, "id").equals("mana_gem")) {
                            TagWizard.addItemTag(e.getPlayer().getItemInHand(), "mana", String.valueOf(x.getAmount() + Integer.parseInt(TagWizard.getItemTag(e.getPlayer().getItemInHand(), "mana"))));
                        }

                        e.getPlayer().getInventory().clear(slot);
                    }
                slot++;
                }
                e.getPlayer().setFireTicks(150);

                List<String> lore = e.getPlayer().getItemInHand().getItemMeta().getLore();

                lore.set(3, Util.formatString("&7- &eAir gems: &f" + Integer.parseInt(TagWizard.getItemTag(e.getPlayer().getItemInHand(), "air"))));
                lore.set(4, Util.formatString("&7- &1Water gems: &f" + Integer.parseInt(TagWizard.getItemTag(e.getPlayer().getItemInHand(), "water"))));
                lore.set(5, Util.formatString("&7- &4Fire gems: &f" + Integer.parseInt(TagWizard.getItemTag(e.getPlayer().getItemInHand(), "fire"))));
                lore.set(6, Util.formatString("&7- &bMana gems: &f" + Integer.parseInt(TagWizard.getItemTag(e.getPlayer().getItemInHand(), "mana"))));
                lore.set(7, Util.formatString("&7- &8Earth gems: &f" + Integer.parseInt(TagWizard.getItemTag(e.getPlayer().getItemInHand(), "earth"))));

                ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
                meta.setLore(lore);
                e.getPlayer().getItemInHand().setItemMeta(meta);

            } else {
                e.getPlayer().sendMessage(ChatColor.DARK_RED + "You can't be on fire while doing that");
            }
        }
    }
}