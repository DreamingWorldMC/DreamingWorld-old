package net.dreamingworld.gameplay.manacraft.items;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFireball;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class HotStaff implements Listener {

    public HotStaff() {
        ItemStack item = new ItemStack(Material.GOLD_SPADE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "Hot staff");

        meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
        meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_PURPLE + "Press to summon fireball.");
        lore.add(ChatColor.DARK_PURPLE + "Uses 2 durability per use");
        meta.setLore(lore);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("hot_staff", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { " I ", "IBI", " B " });
        recipe.setCustomIngredient('I', "hot_sword");
        recipe.setCustomIngredient('B', "uranium");

        DreamingWorld.getInstance().getCraftingManager().registerCraft(recipe);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (DreamingWorld.getInstance().getItemManager().checkItemAuthenticity(e.getPlayer().getItemInHand(), "hot_staff")) {
            if (e.getPlayer().getItemInHand().getDurability() > 31)
                return;

            Location eyeLoc = e.getPlayer().getEyeLocation();
            Vector vec = eyeLoc.getDirection().normalize();
            eyeLoc.add(vec.toLocation(e.getPlayer().getWorld(), eyeLoc.getYaw(), eyeLoc.getPitch()));

            CraftFireball fb = (CraftFireball) e.getPlayer().getWorld().spawnEntity(eyeLoc.subtract(0, 0.25, 0), EntityType.FIREBALL);
            fb.setDirection(new Vector());

            e.getPlayer().getItemInHand().setDurability((short) (e.getPlayer().getItemInHand().getDurability() + 2));
        }
    }

}
