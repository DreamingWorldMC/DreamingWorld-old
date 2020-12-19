package net.dreamingworld.gameplay.manacraft.researches.personalmanacapacitors;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.blocks.CustomBlock;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.geminfusion.GemInfusionRecipe;
import net.dreamingworld.core.mana.ManaContainer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.MathHelper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Charger extends ManaContainer {

    public Charger() {
        id = "charger";
        item = new ItemStack(Material.GOLD_PLATE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Charger");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Infuses items with mana");

        meta.setLore(lore);
        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        GemInfusionRecipe recipe = new GemInfusionRecipe();

        recipe.setResearch("personal_mana_capacitors");

        recipe.setInstability(0f);

        recipe.setResult(item);

        Map<String, Integer> gems = new HashMap<>();

        gems.put("mana", 5);

        recipe.setGems(gems);

        recipe.setMainItem(Material.IRON_BLOCK.toString());

        List<String> items = new ArrayList<>();

        items.add("mana_ingot");
        items.add("mana_ingot");
        items.add("mana_battery");

        recipe.setItems(items);

        DreamingWorld.getInstance().getGemInfusionManager().addRecipe(recipe);
    }


    @Override
    public void place(Block block) {
        setMaxMana(block.getLocation(), 1000);
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
                    if (TagWizard.getItemTag(item, "max_mana") != null) {
                        if (Integer.parseInt(TagWizard.getItemTag(item, "max_mana")) > Integer.parseInt(TagWizard.getItemTag(item, "mana"))) {
                            int manaToGive = Integer.parseInt(TagWizard.getItemTag(item, "max_mana")) - Integer.parseInt(TagWizard.getItemTag(item, "mana"));
                            manaToGive = MathHelper.clamp(manaToGive, 0, 100);
                            manaToGive = MathHelper.clamp(manaToGive, 0, getMana(location));
                            setMana(location, getMana(location) - manaToGive);

                            TagWizard.addItemTag(item, "mana", String.valueOf(manaToGive + Integer.parseInt(TagWizard.getItemTag(item, "mana"))));

                            List<String> lore = item.getItemMeta().getLore();

                            lore.set(Integer.parseInt(TagWizard.getItemTag(item, "mana_line")), Util.formatString("&b[&f" + TagWizard.getItemTag(item, "mana") + "&b/&f" + TagWizard.getItemTag(item, "max_mana") + "lmml&b]"));
                            ItemMeta meta = item.getItemMeta();
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                        }
                    }
                }
            }
        }
    }
}
