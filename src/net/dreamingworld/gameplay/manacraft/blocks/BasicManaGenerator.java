package net.dreamingworld.gameplay.manacraft.blocks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.UtilItems;
import net.dreamingworld.core.blocks.CustomBlock;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.mana.ManaContainer;
import net.dreamingworld.core.ui.ChestUI;
import net.dreamingworld.core.ui.SlotInteractType;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class BasicManaGenerator extends ManaContainer {

    public static class FusionResult {

        public final int manaOutput;
        public final int fusionTime;

        public final int drop0Chance;
        public final ItemStack drop0;

        public final int drop1Chance;
        public final ItemStack drop1;

        public FusionResult(int manaOutput, int fusionTime, int drop0Chance, ItemStack drop0, int drop1Chance, ItemStack drop1) {
            this.manaOutput = manaOutput;
            this.fusionTime = fusionTime;

            this.drop0Chance = drop0Chance;
            this.drop0 = drop0;
            this.drop1Chance = drop1Chance;
            this.drop1 = drop1;
        }
    }


    private Map<Player, Tuple<ChestUI, Location>> viewers;
    private static Map<ItemStack, FusionResult> results;

    static {
        results = new HashMap<>();
    }


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

        viewers = new HashMap<>();
    }


    public static void addResult(ItemStack item, FusionResult result) {
        results.put(item, result);
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.isCancelled())
            return;

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        if (id.equals(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()))) {
            e.setCancelled(true);

            if ("true".equals(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(e.getClickedBlock().getLocation(), "locked"))) {
                e.getPlayer().sendMessage(Util.formatString("$(PC)Mana generator is generating mana now. $(SC)Don`t disturb."));
                return;
            }

            ChestUI ui = new ChestUI("Basic Mana Generator", 3);
            ui.fill(UtilItems.nothing());

            ui.putItem(4, 1, new ItemStack(Material.AIR));
            ui.setSlotInteractType(2, 1, SlotInteractType.PUT_ONLY);

            ItemStack manameter = new ItemStack(Material.STAINED_GLASS_PANE);
            manameter.setDurability((short) 3);
            ItemMeta meta = manameter.getItemMeta();
            meta.setDisplayName(getMana(e.getClickedBlock().getLocation()) + "/" + getMaxMana(e.getClickedBlock().getLocation()) + " lmml");
            manameter.setItemMeta(meta);

            ui.putItem(4, 2, manameter);

            ui.show(e.getPlayer());
            viewers.put(e.getPlayer(), new Tuple<>(ui, e.getClickedBlock().getLocation()));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player))
            return;

        if (!viewers.containsKey(e.getWhoClicked()))
            return;

        if (e.getAction() != InventoryAction.PLACE_SOME && e.getAction() != InventoryAction.PLACE_ONE && e.getAction() != InventoryAction.PLACE_ALL)
            return;

        Tuple<ChestUI, Location> t = viewers.get(e.getWhoClicked());

        ChestUI ui = t.a();
        Location loc = t.b();

        if ("true".equals(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(loc, "locked")))
            return;

        if (e.getSlot() != 13)
            return;

        ItemStack item = e.getCursor();

        if (item == null || item.getType() == Material.AIR)
            return;

        FusionResult res = null;

        for (Map.Entry<ItemStack, FusionResult> entry : results.entrySet())
            if (entry.getKey().isSimilar(item))
                res = entry.getValue();

        if (res == null)
            return;

        int amount = item.getAmount();
        e.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));

        e.getWhoClicked().closeInventory();

        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(loc, "locked", "true");

        FusionResult finalRes = res;
        Bukkit.getScheduler().runTaskLaterAsynchronously(DreamingWorld.getInstance(), () -> {
            Location dropLocation = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()).add(0.5, 1.0, 0.5);

            for (int i = 0; i <= amount; i++) {
                int random1 = ThreadLocalRandom.current().nextInt(0, 101);
                if (random1 < finalRes.drop0Chance)
                    dropLocation.getWorld().dropItem(dropLocation, finalRes.drop0);

                int random2 = ThreadLocalRandom.current().nextInt(0, 101);
                if (random2 < finalRes.drop1Chance)
                    dropLocation.getWorld().dropItem(dropLocation, finalRes.drop1);

                int currentMana = getMana(loc);
                int available = getMaxMana(loc) - currentMana;
                int toStore = Math.min(available, finalRes.manaOutput);

                setMana(loc, currentMana + toStore);

                if (toStore > available)
                    PacketWizard.sendParticle(EnumParticle.EXPLOSION_HUGE, loc, (toStore - available) / 10);

                DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(loc, "locked", "false");
            }
        }, res.fusionTime * amount);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        viewers.remove(e.getPlayer());
    }


    @Override
    public void place(Block block) {
        setMaxMana(block.getLocation(), 1000);
        setMana(block.getLocation(), 0);

        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(block.getLocation(), "maxOutput", "20");
    }

    @Override
    public void tick(Location location) {
        manaTick(location);

        if ("true".equals(DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(location, "locked")))
            PacketWizard.sendParticle(EnumParticle.CRIT_MAGIC, location.add(0.5, 0.5, 0.5), 50);
        else
            PacketWizard.sendParticle(EnumParticle.ENCHANTMENT_TABLE, location.add(0.5, 0.5, 0.5), 100);
    }
}
