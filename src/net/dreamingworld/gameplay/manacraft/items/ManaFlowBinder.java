package net.dreamingworld.gameplay.manacraft.items;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ManaFlowBinder implements Listener {

    public ManaFlowBinder() {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_PURPLE + "Manaflow binder");

        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "This thing makes channels for mana in fourth dimension");
        lore.add(ChatColor.GRAY + "Or in fifth... Anyway it works fine");
        lore.add("");
        lore.add(Util.formatString("&5LMB &7to select input"));
        lore.add(Util.formatString("&5RMB &7to select block to take mana from"));
        meta.setLore(lore);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("mana_flow_binder", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { " SM", "SMS", "MS " });
        recipe.setVanillaIngredient('S', Material.STICK);
        recipe.setCustomIngredient('M', "manium");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @EventHandler
        public void onInteract(PlayerInteractEvent e) {
        if (e.getItem() == null || !DreamingWorld.getInstance().getItemManager().checkItemAuthenticity(e.getItem(), "mana_flow_binder"))
            return;

        if (e.getAction() == Action.RIGHT_CLICK_AIR && e.getPlayer().isSneaking()) {
            e.getPlayer().sendMessage(ChatColor.DARK_RED + "Binder has been reset");
        }

        if (e.getClickedBlock() == null)
            return;

        String loc = e.getClickedBlock().getWorld().getName() + "_" + e.getClickedBlock().getX() + "_" + e.getClickedBlock().getY() + "_" + e.getClickedBlock().getZ();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().isSneaking()) {
            DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(e.getClickedBlock().getLocation(), "inputs", null);
            e.getPlayer().sendMessage(ChatColor.DARK_RED + "Block's parent has been reset");
        }
        else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            e.setCancelled(true);
            TagWizard.addItemTag(e.getItem(), "input", loc);
            e.getPlayer().sendMessage(ChatColor.GREEN + "Input chosen successfully");
        } else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            e.setCancelled(true);
            TagWizard.addItemTag(e.getItem(), "parent", loc);
            e.getPlayer().sendMessage(ChatColor.GREEN + "Parent chosen successfully");
        } else {
            return;
        }

        String parent = TagWizard.getItemTag(e.getItem(), "parent");
        String input = TagWizard.getItemTag(e.getItem(), "input");

        if (parent == null || input == null)
            return;

        String[] parentCoords = parent.split("_");
        String[] inputCoords = input.split("_");

        if (!parentCoords[0].equals(inputCoords[0])) {
            e.getPlayer().sendMessage(Util.formatString("$(PC)Unable to bind blocks in several worlds."));
            return;
        }

        Location parentLocation = new Location(Bukkit.getWorld(parentCoords[0]), Integer.parseInt(parentCoords[1]), Integer.parseInt(parentCoords[2]), Integer.parseInt(parentCoords[3]));
        Location inputLocation = new Location(Bukkit.getWorld(inputCoords[0]), Integer.parseInt(inputCoords[1]), Integer.parseInt(inputCoords[2]), Integer.parseInt(inputCoords[3]));

        if (DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(parentLocation) == null || DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(inputLocation) == null)
            return;

        if  (parentLocation.equals(inputLocation)) {
            Bukkit.getLogger().info(ChatColor.DARK_RED + "Can't bind block to itself");
            return;
        }

        if (parentLocation.distance(inputLocation) > 15) {
            Bukkit.getLogger().info(ChatColor.DARK_RED + "Can't bind blocks which are far away");
            return;
        }

        String inputs = DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(parentLocation, "inputs");
        String newInputs = Util.addToJsonSet(inputs, inputCoords[1] + "_" + inputCoords[2] + "_" + inputCoords[3]);
        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(parentLocation, "inputs", newInputs);

        e.getPlayer().sendMessage(Util.formatString("$(PC)Mana containers bound successfully."));

        TagWizard.addItemTag(e.getItem(), "parent", null);
        TagWizard.addItemTag(e.getItem(), "input", null);
    }
}
