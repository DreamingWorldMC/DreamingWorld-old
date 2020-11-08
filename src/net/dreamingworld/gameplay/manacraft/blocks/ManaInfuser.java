package net.dreamingworld.gameplay.manacraft.blocks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.UtilItems;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.mana.ManaContainer;
import net.dreamingworld.core.ui.ChestUI;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ManaInfuser extends ManaContainer {

    public ManaInfuser() {
        id = "mana_infuser";
        item = new ItemStack(Material.GOLD_PLATE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Mana Infuser");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Infuses items with mana");

        meta.setLore(lore);
        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "IGI", "BCB", "BGB" });
        recipe.setVanillaIngredient('I', Material.DIAMOND);
        recipe.setVanillaIngredient('G', Material.GLASS);
        recipe.setCustomIngredient('C', "mana_core");
        recipe.setVanillaIngredient('B', Material.BRICK);
        recipe.setVanillaIngredient('G', Material.GOLD_INGOT);

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

    }


    @Override
    public void place(Block block) {
        setMaxMana(block.getLocation(), 10000);
        setMana(block.getLocation(), 0);

    }

    @Override
    public void tick(Location location) {
        manaTick(location);
        PacketWizard.sendParticle(EnumParticle.VILLAGER_HAPPY, location.add(0.5, -0.2, 0.5), 10);
    }
}
