package net.dreamingworld.gameplay.manacraft.blocks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.blocks.CustomBlock;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Boiler extends CustomBlock implements Listener {
    public Boiler() {
        id = "boiler";
        item = new ItemStack(Material.FURNACE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Boiler");

        List<String> lore = new ArrayList<>();
        lore.add(Util.formatString("&7Generates vapour and gives it to block on top"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "BBB", "NNN", "BGB" });
        recipe.setVanillaIngredient('G', Material.GOLD_INGOT);
        recipe.setVanillaIngredient('B', Material.IRON_BLOCK);
        recipe.setCustomIngredient('N', "ignium");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }

    @Override
    public void place(Block block) {
        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(block.getLocation(), "fuel_left", "0");
    }

    @Override
    public void tick(Location location) {
        if (0 < Integer.valueOf(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(location, "fuel_left"))) {
            DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(location, "fuel_left", String.valueOf(Integer.valueOf(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(location, "fuel_left")) - 1));

            if (DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ()), "needsSteam") != null && DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ()), "needsSteam").equals("true") && location.getWorld().getBlockAt(new Location(location.getWorld(), location.getX(), location.getY() - 1, location.getZ())).getType() == Material.STATIONARY_WATER) {
                DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ()), "vapour", String.valueOf(Integer.valueOf(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ()), "vapour")) + 1));
            }
            PacketWizard.sendParticle(EnumParticle.ENCHANTMENT_TABLE, location.add(0.5, 0.5, 0.5), 10);
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getItem() == null || e.getClickedBlock() == null || e.getAction() != Action.RIGHT_CLICK_BLOCK || DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()) == null || TagWizard.getItemTag(e.getItem(), "id") == null)
            return;

        if (DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()).equals("boiler") && !e.getPlayer().isSneaking()) {
            e.setCancelled(true);
            boolean takeItem = false;
            switch (TagWizard.getItemTag(e.getItem(), "id")) {
                case ("ignium"):
                    DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(e.getClickedBlock().getLocation(), "fuel_left", String.valueOf(Integer.valueOf(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(e.getClickedBlock().getLocation(), "fuel_left")) + 40));
                    takeItem = true;
                    break;
                case ("hard_coal"):
                    DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(e.getClickedBlock().getLocation(), "fuel_left", String.valueOf(Integer.valueOf(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(e.getClickedBlock().getLocation(), "fuel_left")) + 200));
                    takeItem = true;
                    break;
            }
            if (takeItem) {
                if (e.getPlayer().getInventory().getItemInHand().getAmount() > 1) {
                    e.getPlayer().getInventory().getItemInHand().setAmount(e.getPlayer().getInventory().getItemInHand().getAmount() - 1);
                }
                else {
                    e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                }
            }
        }
    }

}
