package net.dreamingworld.gameplay.manacraft.researches.astrum;

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

public class AstrumSword implements Listener {

    public AstrumSword() {
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.WHITE + "Astrum sword");

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "Well they exist...");
        lore.add(ChatColor.BLUE + "[Deals 10 damage]");
        meta.setLore(lore);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("astrum_sword", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { " I ", " I ", " B " });
        recipe.setCustomIngredient('I', "astrum_ingot");
        recipe.setCustomIngredient('B', "advanced_rod");

        recipe.setResearch("astrum");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
        DreamingWorld.getInstance().getCustomWeaponManager().addWeapon("astrum_sword", 10);
        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getItem() != null && TagWizard.getItemTag(e.getItem(), "id") != null && TagWizard.getItemTag(e.getItem(), "id").equals("astrum_sword")) {
            e.getItem().setDurability((short) 0);
        }
    }
}
