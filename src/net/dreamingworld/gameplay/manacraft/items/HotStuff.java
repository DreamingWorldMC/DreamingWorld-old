package net.dreamingworld.gameplay.manacraft.items;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class HotStuff implements Listener {

    public HotStuff() {

        ItemStack item = new ItemStack(Material.GOLD_SPADE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "Hot sword");

        meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
        meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Press to summon fireball.");
        lore.add(ChatColor.GRAY + "Uses 2 durability per use");
        meta.setLore(lore);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("hot_stuff", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { " I ", "IBI", " B " });
        recipe.setCustomIngredient('I', "hot_sword");
        recipe.setCustomIngredient('B', "uranium");

        DreamingWorld.getInstance().getCraftingManager().registerCraft(recipe);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (DreamingWorld.getInstance().getItemManager().checkItemAuthenticity(e.getPlayer().getItemInHand(), "hot_stuff") && e.getClickedBlock() != null) {
            e.getClickedBlock().getLocation().getWorld().spawnEntity(e.getClickedBlock().getLocation().add(0.5,2,0.5), EntityType.FIREBALL);
            e.getPlayer().getItemInHand().setDurability((short) (e.getPlayer().getItemInHand().getDurability() - 2));
        }
    }

}
