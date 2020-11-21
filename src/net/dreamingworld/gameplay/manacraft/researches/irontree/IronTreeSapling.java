package net.dreamingworld.gameplay.manacraft.researches.irontree;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.blocks.CustomBlock;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.structures.EasyBuilder;
import net.dreamingworld.gameplay.trees.Trees;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class IronTreeSapling extends CustomBlock {

    public IronTreeSapling () {
        id = "iron_tree_sapling";
        item = new ItemStack(Material.SAPLING);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_GREEN + "Iron tree sapling");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "III", "AUA", " U " });
        recipe.setVanillaIngredient('I', Material.IRON_BLOCK);
        recipe.setCustomIngredient('A', "advanced_rod");
        recipe.setCustomIngredient('U', "uranium");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.FIREWORKS_SPARK, location.add(0.5, 0.5, 0.5), 1);
    }

    @EventHandler
    public void onGrow(StructureGrowEvent e) {
        if (id.equals(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getLocation()))) {
            e.setCancelled(true);
            Trees.growIronTree(e.getLocation());
        }
    }
}
