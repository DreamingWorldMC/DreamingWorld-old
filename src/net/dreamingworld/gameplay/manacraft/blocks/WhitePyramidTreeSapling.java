package net.dreamingworld.gameplay.manacraft.blocks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.blocks.CustomBlock;
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

public class WhitePyramidTreeSapling extends CustomBlock {

    public WhitePyramidTreeSapling() {
        id = "white_pyramid_tree_sapling";
        item = new ItemStack(Material.SAPLING);
        item.setDurability((short) 2);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.WHITE + "White pyramid tree sapling");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);


        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.VILLAGER_ANGRY, location.add(0.5, 0.5, 0.5), 1);
    }

    @EventHandler
    public void onGrow(StructureGrowEvent e) {
        if (id.equals(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getLocation()))) {
            e.setCancelled(true);
            Trees.growWhitePyramidTree(e.getLocation());
        }
    }
}
