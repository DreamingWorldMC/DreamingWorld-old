package net.dreamingworld.gameplay.manacraft.researches.activatedmanaarmour;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.geminfusion.GemInfusionRecipe;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivatedManaArmour {

    public ActivatedManaArmour() {
        ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Helmet of vision");

        List<String> lore = new ArrayList<>();
        lore.add(Util.formatString("&b[&f0&b/&f8000lmml&b]"));

        meta.setLore(lore);

        item.setItemMeta(meta);

        TagWizard.addItemTag(item, "mana", "0");
        TagWizard.addItemTag(item, "max_mana", "8000");
        TagWizard.addItemTag(item, "mana_line", "0");

        DreamingWorld.getInstance().getItemManager().registerItem("activated_mana_helmet", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("activated_mana_helmet", 4);

        // RECIPE ----------------------

        GemInfusionRecipe recipe = new GemInfusionRecipe();

        recipe.setResearch("activated_mana_armour");

        recipe.setInstability(0.05f);

        recipe.setResult(item);

        Map<String, Integer> gems = new HashMap<>();

        gems.put("mana", 12);
        gems.put("air", 6);


        recipe.setGems(gems);

        recipe.setMainItem("mana_helmet");

        List<String> items = new ArrayList<>();

        items.add("mana_ingot");
        items.add("mana_ingot");
        items.add(Material.DIAMOND.toString());
        items.add("mana_battery");
        items.add(Material.DIAMOND_HELMET.toString());
        items.add("mana_covered_stick");
        items.add(Material.GOLD_BLOCK.toString());


        recipe.setItems(items);

        DreamingWorld.getInstance().getGemInfusionManager().addRecipe(recipe);


        // CHESTPLATE ============================================================================================================

        item = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta lmeta = (LeatherArmorMeta)item.getItemMeta();


        lmeta.setDisplayName(ChatColor.AQUA + "Chestplate of power");
        lmeta.setColor(Color.AQUA);

        lmeta.setLore(lore);

        item.setItemMeta(lmeta);

        TagWizard.addItemTag(item, "mana", "0");
        TagWizard.addItemTag(item, "max_mana", "8000");
        TagWizard.addItemTag(item, "mana_line", "0");

        DreamingWorld.getInstance().getItemManager().registerItem("activated_mana_chestplate", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("activated_mana_chestplate", 5);

        // RECIPE ----------------------

        recipe = new GemInfusionRecipe();

        recipe.setResearch("activated_mana_armour");

        recipe.setInstability(0.05f);

        recipe.setResult(item);

        gems = new HashMap<>();

        gems.put("mana", 12);
        gems.put("fire", 6);


        recipe.setGems(gems);

        recipe.setMainItem("mana_chestplate");

        items = new ArrayList<>();

        items.add("mana_ingot");
        items.add("mana_ingot");
        items.add("mana_ingot");
        items.add(Material.DIAMOND.toString());
        items.add("mana_battery");
        items.add(Material.DIAMOND_CHESTPLATE.toString());
        items.add("mana_covered_stick");
        items.add(Material.GOLD_BLOCK.toString());


        recipe.setItems(items);

        DreamingWorld.getInstance().getGemInfusionManager().addRecipe(recipe);


        // LEGGINGS ============================================================================================================


        item = new ItemStack(Material.LEATHER_LEGGINGS);
        lmeta = (LeatherArmorMeta)item.getItemMeta();

        lmeta.setDisplayName(ChatColor.AQUA + "Leggings of rabbit");
        lmeta.setColor(Color.AQUA);

        lmeta.setLore(lore);

        item.setItemMeta(lmeta);

        TagWizard.addItemTag(item, "mana", "0");
        TagWizard.addItemTag(item, "max_mana", "8000");
        TagWizard.addItemTag(item, "mana_line", "0");

        DreamingWorld.getInstance().getItemManager().registerItem("activated_mana_leggings", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("activated_mana_leggings", 5);

        // RECIPE ----------------------

        recipe = new GemInfusionRecipe();

        recipe.setResearch("activated_mana_armour");

        recipe.setInstability(0.05f);

        recipe.setResult(item);

        gems = new HashMap<>();

        gems.put("mana", 12);
        gems.put("water", 6);


        recipe.setGems(gems);

        recipe.setMainItem("mana_leggings");

        items = new ArrayList<>();

        items.add("mana_ingot");
        items.add("mana_ingot");
        items.add(Material.DIAMOND.toString());
        items.add("mana_battery");
        items.add(Material.DIAMOND_CHESTPLATE.toString());
        items.add("mana_covered_stick");
        items.add("mana_covered_stick");
        items.add(Material.GOLD_BLOCK.toString());
        items.add("explorer_boots");


        recipe.setItems(items);

        DreamingWorld.getInstance().getGemInfusionManager().addRecipe(recipe);


        // BOOTS ============================================================================================================


        item = new ItemStack(Material.LEATHER_BOOTS);
        lmeta = (LeatherArmorMeta)item.getItemMeta();

        lmeta.setDisplayName(ChatColor.AQUA + "Boots of ocelot");
        lmeta.setColor(Color.AQUA);

        lmeta.setLore(lore);

        item.setItemMeta(lmeta);

        TagWizard.addItemTag(item, "mana", "0");
        TagWizard.addItemTag(item, "max_mana", "8000");
        TagWizard.addItemTag(item, "mana_line", "0");

        DreamingWorld.getInstance().getItemManager().registerItem("activated_mana_boots", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("activated_mana_boots", 4);

        // RECIPE ----------------------

        recipe = new GemInfusionRecipe();

        recipe.setResearch("activated_mana_armour");

        recipe.setInstability(0.05f);

        recipe.setResult(item);

        gems = new HashMap<>();

        gems.put("mana", 12);
        gems.put("earth", 6);


        recipe.setGems(gems);

        recipe.setMainItem("mana_boots");

        items = new ArrayList<>();

        items.add("mana_ingot");
        items.add("mana_ingot");
        items.add("mana_ingot");
        items.add(Material.DIAMOND.toString());
        items.add("mana_battery");
        items.add(Material.DIAMOND_CHESTPLATE.toString());
        items.add("mana_covered_stick");
        items.add(Material.GOLD_BLOCK.toString());
        items.add("explorer_boots");


        recipe.setItems(items);

        DreamingWorld.getInstance().getGemInfusionManager().addRecipe(recipe);


        // LOOP ============================================================================================================


        Bukkit.getScheduler().runTaskTimer(DreamingWorld.getInstance(), () -> {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.getEquipment().getBoots() != null && TagWizard.getItemTag(p.getEquipment().getBoots(), "id") != null && TagWizard.getItemTag(p.getEquipment().getBoots(), "id").equals("activated_mana_boots")) {
                    p.removePotionEffect(PotionEffectType.SPEED);

                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 2));
                }
                if (p.getEquipment().getBoots() != null && TagWizard.getItemTag(p.getEquipment().getLeggings(), "id") != null && TagWizard.getItemTag(p.getEquipment().getLeggings(), "id").equals("activated_mana_leggings")) {
                    p.removePotionEffect(PotionEffectType.JUMP);

                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, 2));
                }
                if (p.getEquipment().getBoots() != null && TagWizard.getItemTag(p.getEquipment().getChestplate(), "id") != null && TagWizard.getItemTag(p.getEquipment().getChestplate(), "id").equals("activated_mana_chestplate")) {
                    p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);

                    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0));
                }
                if (p.getEquipment().getBoots() != null && TagWizard.getItemTag(p.getEquipment().getHelmet(), "id") != null && TagWizard.getItemTag(p.getEquipment().getHelmet(), "id").equals("activated_mana_helmet")) {
                    p.removePotionEffect(PotionEffectType.NIGHT_VISION);

                    p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 260, 0));
                }
            }
        }, 0, 40);
    }
}