package net.dreamingworld.gameplay.manacraft.researches.activatedmanapickaxe;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.geminfusion.GemInfusionRecipe;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivatedManaPickaxe implements Listener {
    public ActivatedManaPickaxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Activated mana pickaxe");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "Uses 15 mana per block broken");
        lore.add(Util.formatString("&b[&f0&b/&f1000lmml&b]"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        TagWizard.addItemTag(item, "mana", "0");
        TagWizard.addItemTag(item, "max_mana", "30000");
        TagWizard.addItemTag(item, "mana_line", "1");

        DreamingWorld.getInstance().getItemManager().registerItem("activated_mana_pickaxe", item);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());

        GemInfusionRecipe recipe = new GemInfusionRecipe();

        recipe.setResearch("activated_mana_pickaxe");

        recipe.setInstability(0.01f);

        recipe.setResult(item);

        Map<String, Integer> gems = new HashMap<>();

        gems.put("mana", 10);
        gems.put("water", 5);
        gems.put("earth", 10);
        gems.put("air", 2);


        recipe.setGems(gems);

        recipe.setMainItem("mana_pickaxe");

        List<String> items = new ArrayList<>();

        items.add("mana_ingot");
        items.add("mana_ingot");
        items.add(Material.DIAMOND.toString());
        items.add(Material.OBSIDIAN.toString());
        items.add(Material.GOLD_BLOCK.toString());
        items.add("mana_battery");

        recipe.setItems(items);

        DreamingWorld.getInstance().getGemInfusionManager().addRecipe(recipe);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if ("activated_mana_pickaxe".equals(TagWizard.getItemTag(e.getPlayer().getItemInHand(), "id"))) {
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
        if ("activated_mana_pickaxe".equals(TagWizard.getItemTag(e.getItem(), "id"))) {
            e.getItem().setDurability((short) 0);
            e.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 80, 1));
        }
    }
}
