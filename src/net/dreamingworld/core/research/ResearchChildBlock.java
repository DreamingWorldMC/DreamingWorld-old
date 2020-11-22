package net.dreamingworld.core.research;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.blocks.BlockDataManager;
import net.dreamingworld.core.blocks.CustomBlock;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.ui.ChestUI;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ResearchChildBlock extends CustomBlock implements Listener {

    public ResearchChildBlock() {
        id = "research_child_block";
        item = new ItemStack(Material.ENCHANTMENT_TABLE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Research finder");

        List<String> lore = new ArrayList<>();
        lore.add(Util.formatString("&7Used to find more stuff to research"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "NNN", "GNG", "NGN" });
        recipe.setVanillaIngredient('G', Material.GOLD_INGOT);
        recipe.setCustomIngredient('N', "white_planks");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
}

    @Override
    public void place(Block block) {
    }

    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.ENCHANTMENT_TABLE, location.add(0.5, 0.5, 0.5), 10);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getClickedBlock() == null || DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()) == null || !"research_child_block".equals(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()))) {
            return;
        }
        e.setCancelled(true);
        if (e.getItem() != null && TagWizard.getItemTag(e.getItem(), "id") != null && TagWizard.getItemTag(e.getItem(), "id").equals("final_research")) {
            PacketWizard.sendParticle(EnumParticle.VILLAGER_HAPPY, e.getClickedBlock().getLocation().add(0.5, 1, 0.5), 10);

            ChestUI ui = new ChestUI("Choose research", 1);

            ItemStack testItem = new ItemStack(Material.STAINED_GLASS_PANE);
            testItem.setDurability((short)7);

            TagWizard.addItemTag(testItem, "ui", "research_child_block");

            ui.putItem(0, testItem);
            ui.putItem(8, testItem);


            Integer i = 1;
            for (String s : DreamingWorld.getInstance().getResearchManager().getResearchChildren(TagWizard.getItemTag(e.getItem(), "research"))) {
                ui.putItem(i, DreamingWorld.getInstance().getResearchManager().getResearchItem(s));
                i++;
            }
            ui.show(e.getPlayer());
        }
        else {
            e.getPlayer().sendMessage(ChatColor.DARK_RED + "That is not a thing you can research.");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onClickUI(InventoryClickEvent e) {
        if (e.getInventory().getItem(0) != null && TagWizard.getItemTag(e.getInventory().getItem(0), "ui") != null && TagWizard.getItemTag(e.getInventory().getItem(0), "ui").equals("research_child_block")) {
            e.setCancelled(true);
            if (TagWizard.getItemTag(e.getCurrentItem(), "ui") == null && e.getClick() == ClickType.RIGHT || TagWizard.getItemTag(e.getCurrentItem(), "ui") == null && e.getClick() == ClickType.LEFT) {
                for (ItemStack x : e.getWhoClicked().getInventory().getContents()) {
                    if (x != null && x.getItemMeta() != null && TagWizard.getItemTag(x, "id") != null && TagWizard.getItemTag(x, "id").equals("empty_research_paper")) {
                        if (e.getWhoClicked().getInventory().firstEmpty() != -1) {
                            if (x.getAmount() > 1) {
                                x.setAmount(x.getAmount() - 1);
                                e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                                return;
                            } else {
                                e.getWhoClicked().sendMessage(ChatColor.DARK_RED + "You need at least 2 research papers in your inventory");
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
