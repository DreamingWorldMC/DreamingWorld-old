package net.dreamingworld.gameplay.manacraft.researches.plantsandcreatures.whitepyramidtree;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.blocks.CustomBlock;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WhitePlanks extends CustomBlock {

    public WhitePlanks()  {
        id = "white_planks";
        item = new ItemStack(Material.WOOD);
        ItemMeta meta = item.getItemMeta();
        item.setDurability((short)2);

        meta.setDisplayName("White planks");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.BLUE + "It seems very good looking and white. Is it worth to touch?");
        meta.setLore(lore);

        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        item.setAmount(4);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "   ", " W ", "   " });
        recipe.setCustomIngredient('W', "white_log");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }

    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.VILLAGER_HAPPY, location.add(0.5, 0.5, 0.5), 2);
    }
}
