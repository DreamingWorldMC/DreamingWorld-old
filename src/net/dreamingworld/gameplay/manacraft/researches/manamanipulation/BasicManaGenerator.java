package net.dreamingworld.gameplay.manacraft.researches.manamanipulation;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.mana.ManaContainer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class BasicManaGenerator extends ManaContainer {

    private static final Map<ItemStack, Integer> results;

    static {
        results = new HashMap<>();
    }

    public BasicManaGenerator() {
        id = "basic_mana_generator";
        item = new ItemStack(Material.FURNACE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Basic Mana Generator");

        List<String> lore = new ArrayList<>();
        lore.add(Util.formatString("&7Outputs 20 lmml/tick"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "IGI", "ICI", "BGB" });
        recipe.setVanillaIngredient('I', Material.IRON_INGOT);
        recipe.setVanillaIngredient('G', Material.GOLD_INGOT);
        recipe.setVanillaIngredient('B', Material.IRON_BLOCK);
        recipe.setCustomIngredient('C', "mana_capacitor");

        recipe.setResearch("mana_manipulation");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }


    public static void addResult(ItemStack item, int fuelCount) {
        results.put(item, fuelCount);
    }


    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK || DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()) == null) {
            return;
        }

        ItemStack item = e.getItem();

        if (DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()).equals(id)) {
            e.setCancelled(true);

            if (e.getItem() == null) {
                return;
            }

            int fuelCount = -1;

            for (Map.Entry<ItemStack, Integer> et : results.entrySet()) {
                if (et.getKey().isSimilar(item)) {
                    fuelCount = et.getValue();
                    break;
                }
            }

            if (fuelCount == -1) {
                if (!(item.getItemMeta() != null && TagWizard.getItemTag(item, "id").equals("mana_glass"))) {
                    e.getPlayer().sendMessage(Util.formatString("$(SC)THIS $(PC)does not looks like a fuel"));
                }

                return;
            }

            if (!e.getPlayer().isSneaking()) {
                int f = Integer.parseInt(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(e.getClickedBlock().getLocation(), "fuel_left")) + fuelCount;
                DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(e.getClickedBlock().getLocation(), "fuel_left", String.valueOf(f));

                e.getPlayer().sendMessage(Util.formatString("$(PC)Added $(SC)" + fuelCount + " $(PC)units of fuel"));

                if (e.getPlayer().getInventory().getItemInHand().getAmount() > 1) {
                    e.getPlayer().getInventory().getItemInHand().setAmount(item.getAmount() - 1);
                } else {
                    e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                }
            } else {
                int f = Integer.parseInt(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(e.getClickedBlock().getLocation(), "fuel_left")) + fuelCount * e.getPlayer().getInventory().getItemInHand().getAmount();
                DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(e.getClickedBlock().getLocation(), "fuel_left", String.valueOf(f));
                e.getPlayer().sendMessage(Util.formatString("$(PC)Added $(SC)" + (fuelCount * e.getPlayer().getInventory().getItemInHand().getAmount()) + " $(PC)units of fuel"));
                e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
            }
        }
     }

    @Override
    public void place(Block block) {
        setMaxMana(block.getLocation(), 3000);
        setMana(block.getLocation(), 0);

        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(block.getLocation(), "maxOutput", "20");
        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(block.getLocation(), "fuel_left", "0");
        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(block.getLocation(), "progress", "0");
    }

    @Override
    public void tick(Location location) {
        manaTick(location);

        int fuel = Integer.parseInt(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(location, "fuel_left"));
        int progress = Integer.parseInt(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(location, "progress"));

        if (fuel > 0 && getMaxMana(location) > (getMana(location) + 2)) {
            if (progress > 5) {
                progress = 0;
                fuel -= 1;
                setMana(location, getMana(location) + 2);
                PacketWizard.sendParticle(EnumParticle.FLAME, location.add(0.5, 0.5, 0.5), 2);
            } else {
                progress++;
            }
        }

        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(location, "fuel_left", String.valueOf(fuel));
        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(location, "progress", String.valueOf(progress));
    }
}
