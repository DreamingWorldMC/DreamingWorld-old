package net.dreamingworld.core.geminfusion;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.gameplay.manacraft.researches.geminfusion.altar.AltarActivator;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class GemInfsuionAnim {

    GemInfusionRecipe recipe;

    int currentStep = 0;
    int neededSteps = 0;

    int stability = 0;

    BukkitTask task;

    public GemInfsuionAnim(PlayerInteractEvent e) {
        Item mainItem = null;
        List<Item> items = new ArrayList<>();

        stability = 5;

        for (Entity x : e.getClickedBlock().getWorld().getNearbyEntities(e.getClickedBlock().getLocation(), 35, 20, 35)) {
            if (x instanceof Item) {
                if (DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(x.getLocation().add(0,-1.5,0))!=null && DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(x.getLocation().add(0,-1.5,0)).equals("main_pedestal")) {
                    mainItem = (Item)x;
                } else if (DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(x.getLocation().add(0,-1.5,0))!=null && DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(x.getLocation().add(0,-1.5,0)).equals("pedestal")) {
                    if (DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(x.getLocation().add(0,-2.5,0))!=null) {
                        stability += DreamingWorld.getInstance().getGemInfusionManager().getStabilizer(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(x.getLocation().add(0,-2.5,0)));
                    }
                    for (Item y : items) {
                        if (y.getLocation().distance(x.getLocation())<1.5) {
                            stability -= 5;
                        }
                    }
                    items.add((Item)x);
                }
            }
        }
        if (mainItem != null && mainItem.getItemStack().getAmount() == 1) {
            recipe = DreamingWorld.getInstance().getGemInfusionManager().getPossibleRecipe(mainItem.getItemStack(), items);

            if (recipe != null && DreamingWorld.getInstance().getResearchManager().playerHasResearch(e.getPlayer(), recipe.research)) {
                neededSteps = recipe.items.size() * 5;

                for (Map.Entry<String, Integer> x : recipe.gems.entrySet()) {
                    neededSteps += x.getValue() / 2;
                }

                for (Map.Entry<String, Integer> x: recipe.gems.entrySet()) {
                    if (Integer.parseInt(TagWizard.getItemTag(e.getPlayer().getItemInHand(), x.getKey())) >= x.getValue()) {
                        TagWizard.addItemTag(e.getPlayer().getItemInHand(), x.getKey(), String.valueOf(Integer.parseInt(TagWizard.getItemTag(e.getPlayer().getItemInHand(), x.getKey())) - x.getValue()));
                    } else {
                        e.getPlayer().sendMessage(ChatColor.DARK_RED + "You don't have needed gems.");
                        return;
                    }
                    AltarActivator.updateDescription(e.getPlayer().getItemInHand());
                }

                for (Item y : items) {
                    y.remove();
                }
                mainItem.remove();
                task = Bukkit.getScheduler().runTaskTimer(DreamingWorld.getInstance(), () -> {
                    stability -= recipe.instability;

                    PacketWizard.sendParticle(EnumParticle.ENCHANTMENT_TABLE, e.getClickedBlock().getLocation().add(0.5, 1.5, 0.5), 5, 10, 0.1f);
                    PacketWizard.sendParticle(EnumParticle.CRIT_MAGIC, e.getClickedBlock().getLocation().add(0.5, 1.5, 0.5), 25, 0.1f, 0.1f);

                    if (stability < -15) {
                        e.getClickedBlock().getWorld().strikeLightning(e.getClickedBlock().getLocation());
                        PacketWizard.sendParticle(EnumParticle.WATER_BUBBLE, e.getClickedBlock().getLocation().add(0.5, 1.5, 0.5), 25, 0.1f, 0.1f);
                        PacketWizard.sendParticle(EnumParticle.VILLAGER_ANGRY, e.getClickedBlock().getLocation().add(0.5, 1.5, 0.5), 25, 0.1f, 0.1f);
                        for (Entity x : e.getClickedBlock().getWorld().getNearbyEntities(e.getClickedBlock().getLocation(), 25, 25, 25)) {
                            if (x instanceof Player) {
                                ((Player)x).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 100));
                                ((Player)x).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 100));
                            }
                        }

                        Bukkit.getScheduler().cancelTask(task.getTaskId());
                    }


                    currentStep++;

                    if (currentStep >= neededSteps) {
                        PacketWizard.sendParticle(EnumParticle.HEART, e.getClickedBlock().getLocation().add(0.5, 1.5, 0.5), 5, 0.1f, 0.1f);

                        Item it = e.getClickedBlock().getWorld().dropItem(e.getClickedBlock().getLocation().add(0.5, 1.5, 0.5), recipe.result);

                        it.setVelocity(new Vector(0, 0, 0));

                        Bukkit.getScheduler().cancelTask(task.getTaskId());
                    }
                }, 0, 4);
            }


        }
    }
}
