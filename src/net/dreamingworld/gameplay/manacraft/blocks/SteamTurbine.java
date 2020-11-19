package net.dreamingworld.gameplay.manacraft.blocks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.mana.ManaContainer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SteamTurbine extends ManaContainer {

    public SteamTurbine()  {
        id = "steam_turbine";
        item = new ItemStack(Material.COBBLE_WALL);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Steam turbine");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.BLUE + "Generates mana from vapour");
        meta.setLore(lore);

        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "ISI", "SMS", "ISI" });
        recipe.setVanillaIngredient('S', Material.STICK);
        recipe.setVanillaIngredient('I', Material.IRON_INGOT);
        recipe.setCustomIngredient('M', "manium");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }

    @Override
    public void place(Block block) {
        setMaxMana(block.getLocation(), 1000);
        setMana(block.getLocation(), 0);

        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(block.getLocation(), "maxOutput", "20");
        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(block.getLocation(), "needsSteam", "true");
        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(block.getLocation(), "vapour", "1");
    }

    @Override
    public void tick(Location location) {
        manaTick(location);

        if (Integer.valueOf(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(location, "vapour")) >= 10) {
            if (location.getWorld().getBlockAt(new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ())) != null && DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ()), "needsSteam") != null && DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ()), "needsSteam").equals("true")) {
                DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ()), "vapour", String.valueOf(Integer.valueOf(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ()), "vapour")) + 8));
            }
            else {
                PacketWizard.sendParticle(EnumParticle.SMOKE_LARGE, new Location(location.getWorld(), location.getX() + 0.5, location.getY() + 1, location.getZ() + 0.5), 50);
            }

            DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(location, "vapour", String.valueOf(Integer.valueOf(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(location, "vapour"))  - 10));
            if (getMana(location) + 20 <= getMaxMana(location)) {
                setMana(location, getMana(location) + 20);
            }
        }
    }
}
