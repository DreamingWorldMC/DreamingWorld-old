package net.dreamingworld.gameplay.blocks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.blocks.CustomBlock;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BasicManaGenerator extends CustomBlock {

    public BasicManaGenerator() {
        id = "basic_mana_generator";
        item = new ItemStack(Material.FURNACE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Basic Mana Generator");

        List<String> lore = new ArrayList<>();
        lore.add(Util.formatString("&7Outputs &420 lmml&r/&7tick"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "IGI", "NBN", "IGI" });
        recipe.setVanillaIngredient('I', Material.IRON_INGOT);
        recipe.setVanillaIngredient('G', Material.GOLD_INGOT);
        recipe.setVanillaIngredient('B', Material.IRON_BLOCK);
        recipe.setCustomIngredient('N', "ignium");

        DreamingWorld.getInstance().getCraftingManager().registerCraft(recipe);
    }

    @Override
    public void tick(Location location) {
        location.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 100);
    }
}
