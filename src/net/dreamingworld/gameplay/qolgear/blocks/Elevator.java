package net.dreamingworld.gameplay.qolgear.blocks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.blocks.CustomBlock;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;


public class Elevator extends CustomBlock {

    public Elevator()  {
        id = "elevator";
        item = new ItemStack(Material.WOOL);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Elevator");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "III", "IBI", "III" });
        recipe.setVanillaIngredient('I', Material.ENDER_PEARL);
        recipe.setVanillaIngredient('B', Material.WOOL);

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }

    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.CRIT_MAGIC, location.add(0.5, 0.5, 0.5), 3, 0.4f, 0.5f);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if (!"elevator".equals(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getPlayer().getLocation().add(0, -1, 0)))) {
            return;
        }
        if (e.isSneaking()) {
            for (int i = 2; i<10; i++) {
                if ("elevator".equals(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getPlayer().getLocation().add(0, -i, 0)))) {
                    e.getPlayer().teleport(e.getPlayer().getLocation().add(0, -i + 1, 0));
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onJump(PlayerMoveEvent e) {
        if (!"elevator".equals(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getPlayer().getLocation().add(0, -1, 0)))) {
            return;
        }
        if (e.getFrom().getY() < e.getTo().getY()) {
            for (int i = 2; i<10; i++) {
                if ("elevator".equals(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getPlayer().getLocation().add(0, i, 0)))) {
                    e.getPlayer().teleport(e.getPlayer().getLocation().add(0, i + 1, 0));
                    e.getPlayer().setVelocity(new Vector(0, 0, 0));
                    return;
                }
            }
        }
    }
}
