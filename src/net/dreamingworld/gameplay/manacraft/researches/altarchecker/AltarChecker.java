package net.dreamingworld.gameplay.manacraft.researches.altarchecker;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.geminfusion.GemInfusionRecipe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AltarChecker implements Listener {

    public AltarChecker() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_PURPLE + "Altar checker");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.WHITE + "Click on the an altar to know if the infusion will work.");

        meta.setLore(lore);
        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem("altar_checker", item);

        GemInfusionRecipe recipe = new GemInfusionRecipe();

        recipe.setResearch("altar_checker");

        recipe.setInstability(0f);

        recipe.setResult(item);

        Map<String, Integer> gems = new HashMap<>();

        gems.put("mana", 3);
        gems.put("earth", 3);

        recipe.setGems(gems);

        recipe.setMainItem("mana_covered_stick");

        List<String> items = new ArrayList<>();

        items.add("mana_ingot");
        items.add(Material.DIAMOND.toString());

        recipe.setItems(items);

        DreamingWorld.getInstance().getGemInfusionManager().addRecipe(recipe);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        if (TagWizard.getItemTag(e.getItem(), "id") == null || !TagWizard.getItemTag(e.getItem(), "id").equals("altar_checker")) {
            return;
        }

        Item mainItem = null;
        List<Item> items = new ArrayList<>();

        float stability = 5;

        for (Entity x : e.getClickedBlock().getWorld().getNearbyEntities(e.getClickedBlock().getLocation(), 35, 20, 35)) {
            if (x instanceof Item) {
                if (DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(x.getLocation().add(0, -1.5, 0)) != null && DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(x.getLocation().add(0, -1.5, 0)).equals("main_pedestal")) {
                    mainItem = (Item) x;
                } else if (DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(x.getLocation().add(0, -1.5, 0)) != null && DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(x.getLocation().add(0, -1.5, 0)).equals("pedestal")) {
                    if (DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(x.getLocation().add(0, -2.5, 0)) != null) {
                        stability += DreamingWorld.getInstance().getGemInfusionManager().getStabilizer(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(x.getLocation().add(0, -2.5, 0)));
                    }
                    for (Item y : items) {
                        if (y.getLocation().distance(x.getLocation()) < 1.5) {
                            stability -= 5;
                        }
                    }
                    items.add((Item) x);
                }
            }
        }
        if (mainItem != null && mainItem.getItemStack().getAmount() == 1) {
            GemInfusionRecipe recipe = DreamingWorld.getInstance().getGemInfusionManager().getPossibleRecipe(mainItem.getItemStack(), items);

            if (recipe != null) {
                int neededSteps = recipe.getItems().size() * 5;

                for (Map.Entry<String, Integer> x : recipe.getGems().entrySet()) {
                    neededSteps += x.getValue() / 2;
                }

                e.getPlayer().sendMessage(Util.formatString("&fStarting stability: &b" + stability));
                e.getPlayer().sendMessage(Util.formatString("&fEnding stability: &b" + (stability - (recipe.getInstability()*neededSteps))));
                e.getPlayer().sendMessage(Util.formatString("&fFinal item: &b" + recipe.getResult().getItemMeta().getDisplayName()));
            } else {
                e.getPlayer().sendMessage(ChatColor.DARK_RED + "Can not find needed recipe.");
            }
        }
    }
}
