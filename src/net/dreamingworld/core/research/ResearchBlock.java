package net.dreamingworld.core.research;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.blocks.BlockDataManager;
import net.dreamingworld.core.blocks.CustomBlock;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import java.util.List;

public class ResearchBlock extends CustomBlock implements Listener {

    public ResearchBlock() {
        id = "research_block";
        item = new ItemStack(Material.WORKBENCH);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Research table");

        List<String> lore = new ArrayList<>();
        lore.add(Util.formatString("&7Used to complete researches"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "NNN", " G ", "GGG" });
        recipe.setVanillaIngredient('G', Material.GOLD_INGOT);
        recipe.setCustomIngredient('N', "white_planks");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
}

    @Override
    public void place(Block block) {
        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(block.getLocation(), "current_research", "non");
        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(block.getLocation(), "current_stage", "non");
    }

    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.ENCHANTMENT_TABLE, location.add(0.5, 0.5, 0.5), 10);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getClickedBlock() == null || DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()) == null || !"research_block".equals(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()))) {
            return;
        }
        e.setCancelled(true);
        BlockDataManager bdm = DreamingWorld.getInstance().getBlockManager().getBlockDataManager();


        if (bdm.getBlockTag(e.getClickedBlock().getLocation(), "current_research").equals("non") && e.getItem() != null) {
            if (TagWizard.getItemTag(e.getItem(), "id") != null && TagWizard.getItemTag(e.getItem(), "id").equals("research")) {
                e.getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
                bdm.setBlockTag(e.getClickedBlock().getLocation(), "current_research", TagWizard.getItemTag(e.getItem(), "research"));
            }
            else {
                e.getPlayer().sendMessage(ChatColor.DARK_RED + "That is not a thing you can research.");
            }
        }


    }
}