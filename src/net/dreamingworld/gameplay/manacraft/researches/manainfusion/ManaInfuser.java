package net.dreamingworld.gameplay.manacraft.researches.manainfusion;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.mana.ManaContainer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ManaInfuser extends ManaContainer {

    public ManaInfuser() {
        id = "mana_infuser";
        item = new ItemStack(Material.GOLD_PLATE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Mana Infuser");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Infuses items with mana");

        meta.setLore(lore);
        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "G G", "GCG", "GIG" });
        recipe.setVanillaIngredient('I', Material.DIAMOND);
        recipe.setCustomIngredient('C', "mana_capacitor");
        recipe.setVanillaIngredient('G', Material.GOLD_INGOT);

        recipe.setResearch("mana_infusion");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }


    @Override
    public void place(Block block) {
        setMaxMana(block.getLocation(), 10000);
        setMana(block.getLocation(), 0);
    }

    @Override
    public void tick(Location location) {
        manaTick(location);
        PacketWizard.sendParticle(EnumParticle.VILLAGER_HAPPY, location.add(0.5, -0.2, 0.5), 10);

        if (location.getWorld().getBlockAt(location).isBlockIndirectlyPowered()) {
            Collection<Entity> entities = location.getWorld().getNearbyEntities(location.add(0, 1,0), 1, 1, 1);

            for (Entity entity : entities) {
                if (entity instanceof Item) {
                    ItemStack item = ((Item) entity).getItemStack();

                    if (DreamingWorld.getInstance().getManaInfusionManager().getTo(item) != null) {
                        int sm = Integer.parseInt(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(location, "storedMana"));

                        int canDrop = (int) Math.floor(sm / (double) DreamingWorld.getInstance().getManaInfusionManager().getTo(item).manaTakes);
                        int willDrop = Math.min(canDrop, item.getAmount());

                        if (willDrop <= 0) {
                            return;
                        }

                        ItemStack result = new ItemStack(DreamingWorld.getInstance().getManaInfusionManager().getTo(item).result);
                        result.setAmount(willDrop);

                        location.getWorld().dropItem(location, result);

                        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(location, "storedMana", String.valueOf(sm - willDrop * DreamingWorld.getInstance().getManaInfusionManager().getTo(item).manaTakes));
                        PacketWizard.sendParticle(EnumParticle.ENCHANTMENT_TABLE, location, DreamingWorld.getInstance().getManaInfusionManager().getTo(item).manaTakes / 10 * willDrop);

                        item.setAmount(item.getAmount() - willDrop);

                        if (item.getAmount() <= 0) {
                            entity.remove();
                        }
                    }
                }
            }
        }
    }
}

