package net.dreamingworld.gameplay.manacraft.researches.geminfusion.altar;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.blocks.CustomBlock;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.geminfusion.GemInfsuionAnim;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;


public class MainPedestal extends CustomBlock {

    public MainPedestal()  {
        id = "main_pedestal";
        item = new ItemStack(Material.COBBLE_WALL);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Main pedestal");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "III", "IBI", "I I" });
        recipe.setCustomIngredient('I', "mana_ingot");
        recipe.setCustomIngredient('B', "pedestal");

        recipe.setResearch("gem_infusion");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }

    @Override
    public void place(Block block) {
        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(block.getLocation(), "item", "false");
    }

    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.CRIT_MAGIC, location.add(0.5, 1.5, 0.5), 10, 0.4f, 0.5f);
    }


    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null || e.getAction() != Action.RIGHT_CLICK_BLOCK || DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()) == null || !DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()).equals("main_pedestal") || e.getItem() == null)
            return;

        if (!(TagWizard.getItemTag(e.getItem(), "id") != null && (TagWizard.getItemTag(e.getItem(), "id").equals("altar_activator"))) && !(TagWizard.getItemTag(e.getItem(), "id") != null && (TagWizard.getItemTag(e.getItem(), "id").equals("altar_checker")))) {
            e.setCancelled(true);
            Item item = e.getClickedBlock().getWorld().dropItem(e.getClickedBlock().getLocation().add(0.5,1.5,0.5), e.getItem());
            item.setMetadata("#noDespawn", new FixedMetadataValue(DreamingWorld.getInstance(), "true"));
            item.setVelocity(new Vector(0,0,0));
            e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
        } else if (!(TagWizard.getItemTag(e.getItem(), "id") != null && (TagWizard.getItemTag(e.getItem(), "id").equals("altar_checker")))) { // here lies the shitty ton of code
            new GemInfsuionAnim(e);
        }
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent e) {
        if (e.getEntity().hasMetadata("#noDespawn")) {
            e.setCancelled(true);
        }
    }
}
