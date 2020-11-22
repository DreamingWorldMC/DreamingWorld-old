package net.dreamingworld.gameplay.manacraft.researches.astrumbow;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AstrumBow {

    public AstrumBow() {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.WHITE + "Astrum bow");

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "No one likes bow spammers except people with projectile protection. . .");
        meta.setLore(lore);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("astrum_bow", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "SI ", "S I", "SI " });
        recipe.setCustomIngredient('I', "astrum_ingot");
        recipe.setVanillaIngredient('S', Material.STRING);

        recipe.setResearch("astrum_bow");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
        DreamingWorld.getInstance().getCustomWeaponManager().addWeapon("astrum_bow", 12);
    }



}
