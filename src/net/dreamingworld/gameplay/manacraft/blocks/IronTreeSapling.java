package net.dreamingworld.gameplay.manacraft.blocks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.blocks.CustomBlock;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.stractures.EasyBuilder;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

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
        recipe.setCustomIngredient('A', "advanced_stick");
        recipe.setCustomIngredient('U', "uranium");

        DreamingWorld.getInstance().getCraftingManager().registerCraft(recipe);
    }

    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.FIREWORKS_SPARK, location.add(0.5, 0.5, 0.5), 1);
    }

    @EventHandler
    public void onGrow(StructureGrowEvent e) throws IOException {

        if (id.equals(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getLocation()))) {
            e.setCancelled(true);


            EasyBuilder builder = new EasyBuilder(e.getLocation());

            builder.buildCustomBlock(0, 0,0,"iron_wood", Material.BIRCH_FENCE);
            builder.buildCustomBlock(0, 1,0,"iron_wood", Material.BIRCH_FENCE);
            builder.buildCustomBlock(0, 2,0,"iron_wood", Material.BIRCH_FENCE);

            builder.buildCustomBlock(0, 3,0,"iron_wood", Material.LOG);

            builder.buildCustomBlock(1, 3,0,"iron_leaf", Material.LEAVES);
            builder.buildCustomBlock(-1, 3,0,"iron_leaf", Material.LEAVES);
            builder.buildCustomBlock(0, 3,1,"iron_leaf", Material.LEAVES);
            builder.buildCustomBlock(0, 3,-1,"iron_leaf", Material.LEAVES);

            builder.buildCustomBlock(0, 4,0,"iron_leaf", Material.LEAVES);
        }


    }

}
