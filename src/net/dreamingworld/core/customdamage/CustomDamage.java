package net.dreamingworld.core.customdamage;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.minecraft.server.v1_8_R3.MathHelper;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomDamage implements Listener {

    private final Map<EntityDamageEvent.DamageCause, String> deathMsg;

    private boolean now = false;

    public CustomDamage() {
        deathMsg = new HashMap<>();
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        double finalDamage = DreamingWorld.getInstance().getCustomWeaponManager().getWeapon(TagWizard.getItemTag(e.getBow(), "id"));

        if (finalDamage != -1) {
            if (e.getBow().getItemMeta().hasEnchant(Enchantment.DAMAGE_ALL)) {
                finalDamage += e.getBow().getItemMeta().getEnchantLevel(Enchantment.DAMAGE_ALL);
            }
        }

        e.getProjectile().setMetadata("damage", new FixedMetadataValue(DreamingWorld.getInstance(), finalDamage));
    }

    @EventHandler
    public void onEntityDamagedByEntity(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof LivingEntity)) {
            return;
        }

        double finalDamage = e.getDamage();

        if (e.getDamager() instanceof Arrow && e.getDamager().getMetadata("damage").get(0).asDouble() != -1) {
            finalDamage = e.getDamager().getMetadata("damage").get(0).asDouble() * ((((Arrow)e.getDamager()).getVelocity().getX() + ((Arrow)e.getDamager()).getVelocity().getY() + ((Arrow)e.getDamager()).getVelocity().getZ())/64);
        }

        if (e.getDamager() instanceof LivingEntity) {
            finalDamage = DreamingWorld.getInstance().getCustomWeaponManager().getWeapon(TagWizard.getItemTag(((LivingEntity) e.getDamager()).getEquipment().getItemInHand(), "id"));

            if (finalDamage == -1) {
                finalDamage = e.getDamage();
            }

            if (DreamingWorld.getInstance().getEntityManager().getDamage((LivingEntity) e.getDamager()) != -1) {
                finalDamage = DreamingWorld.getInstance().getEntityManager().getDamage((LivingEntity) e.getDamager());
            }

            if (((LivingEntity) e.getDamager()).getEquipment().getItemInHand() != null && ((LivingEntity) e.getDamager()).getEquipment().getItemInHand().hasItemMeta()) {
                if (((LivingEntity) e.getDamager()).getEquipment().getItemInHand().getItemMeta().hasEnchant(Enchantment.DAMAGE_ALL)) {
                    finalDamage += ((LivingEntity) e.getDamager()).getEquipment().getItemInHand().getItemMeta().getEnchantLevel(Enchantment.DAMAGE_ALL);
                } else if (((LivingEntity) e.getDamager()).getEquipment().getItemInHand().getItemMeta().hasEnchant(Enchantment.DAMAGE_UNDEAD)) {
                    if (e.getEntityType().equals(EntityType.SKELETON) || e.getEntityType().equals(EntityType.ZOMBIE) || e.getEntityType().equals(EntityType.PIG_ZOMBIE)) {
                        finalDamage += 3 * ((LivingEntity) e.getDamager()).getEquipment().getItemInHand().getItemMeta().getEnchantLevel(Enchantment.DAMAGE_UNDEAD);
                    }
                } else if (((LivingEntity) e.getDamager()).getEquipment().getItemInHand().getItemMeta().hasEnchant(Enchantment.DAMAGE_ARTHROPODS)) {
                    if (e.getEntityType().equals(EntityType.SPIDER) || e.getEntityType().equals(EntityType.CAVE_SPIDER)) {
                        finalDamage += 3 * ((LivingEntity) e.getDamager()).getEquipment().getItemInHand().getItemMeta().getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS);
                    }
                }
            }
            for (PotionEffect x : ((LivingEntity) e.getDamager()).getActivePotionEffects()) {
                if (x.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                    finalDamage *= 1 + (x.getAmplifier()*0.5);
                }
            }

            if (e.getDamager().getVelocity().getY() < -0.15) {
                finalDamage *= 1.5;
            }

            if (e.getEntity() instanceof Player) {
                if (((Player) e.getEntity()).isBlocking()) {
                    finalDamage *= 0.5;
                }
            }
        }

        EntityDamageEvent newEvent = new EntityDamageEvent(e.getEntity(), e.getCause(), e.getDamage());
        e.setDamage(0);
        if (e.getEntity() instanceof Player) {
            now = true;
            onPlayerDamage(newEvent);
        } else {
            if (((LivingEntity) e.getEntity()).getHealth() - finalDamage > 0) {
                ((LivingEntity) e.getEntity()).setHealth(((LivingEntity)e.getEntity()).getHealth() - finalDamage);
            } else {
                ((LivingEntity) e.getEntity()).setHealth(0);
            }
        }

    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getCause() != null && e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) && !now) {
            return;
        }

        now = false;

        if (e.getEntity().isDead()) {
            return;
        }

        if (e.getEntity() instanceof Player) {
            double startDamage = e.getDamage();
            int armorPoints = 0;
            double removeDMG = 0;

            ItemStack[] armor = ((Player) e.getEntity()).getInventory().getArmorContents();

            for (ItemStack armorItem : armor) {
                if (armorItem == null) {
                    continue;
                }

                if (DreamingWorld.getInstance().getCustomArmor().getPiece(TagWizard.getItemTag(armorItem, "id")) != -1) {
                    if (TagWizard.getItemTag(armorItem, "mana") == null) {
                        armorPoints += DreamingWorld.getInstance().getCustomArmor().getPiece(TagWizard.getItemTag(armorItem, "id"));
                    } else if (Integer.valueOf(TagWizard.getItemTag(armorItem, "mana")) > 0) {
                        armorPoints += DreamingWorld.getInstance().getCustomArmor().getPiece(TagWizard.getItemTag(armorItem, "id"));
                        TagWizard.addItemTag(armorItem, "mana", String.valueOf(Integer.valueOf(TagWizard.getItemTag(armorItem, "mana")) - (int)e.getDamage()*3));

                        List<String> lore = armorItem.getItemMeta().getLore();
                        lore.set(Integer.valueOf(TagWizard.getItemTag(armorItem, "mana_line")), Util.formatString("&b[&f" + TagWizard.getItemTag(armorItem, "mana") + "&b/&f1000lmml&b]"));
                        ItemMeta meta = armorItem.getItemMeta();
                        meta.setLore(lore);
                        armorItem.setItemMeta(meta);
                    }
                }

                else {
                    switch (armorItem.getType()) {
                        case DIAMOND_BOOTS: // diamond
                            armorPoints += 3;
                            break;
                        case DIAMOND_LEGGINGS:
                            armorPoints += 4;
                            break;
                        case DIAMOND_HELMET:
                            armorPoints += 4;
                            break;
                        case DIAMOND_CHESTPLATE:
                            armorPoints += 5;
                            break;

                        case IRON_BOOTS: // iron
                            armorPoints += 2;
                            break;
                        case IRON_LEGGINGS:
                            armorPoints += 3;
                            break;
                        case IRON_HELMET:
                            armorPoints += 3;
                            break;
                        case IRON_CHESTPLATE:
                            armorPoints += 3;
                            break;

                        case GOLD_BOOTS: // gold
                            armorPoints += 4;
                            break;
                        case GOLD_LEGGINGS:
                            armorPoints += 4;
                            break;
                        case GOLD_HELMET:
                            armorPoints += 4;
                            break;
                        case GOLD_CHESTPLATE:
                            armorPoints += 4;
                            break;

                        case LEATHER_BOOTS: // leather
                            armorPoints += 1;
                            break;
                        case LEATHER_LEGGINGS:
                            armorPoints += 1;
                            break;
                        case LEATHER_HELMET:
                            armorPoints += 1;
                            break;
                        case LEATHER_CHESTPLATE:
                            armorPoints += 2;
                            break;
                    }
                }

                if (armorItem.hasItemMeta()) {
                    if (armorItem.getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                        armorPoints += armorItem.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL);
                    }

                    if (armorItem.getItemMeta().hasEnchant(Enchantment.PROTECTION_FIRE) && (e.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || e.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK) || e.getCause().equals(EntityDamageEvent.DamageCause.LAVA))) {
                        armorPoints += armorItem.getEnchantments().get(Enchantment.PROTECTION_FIRE) * 3;
                    }

                    if (armorItem.getItemMeta().hasEnchant(Enchantment.PROTECTION_EXPLOSIONS) && (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) || e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION))) {
                        armorPoints += armorItem.getEnchantments().get(Enchantment.PROTECTION_EXPLOSIONS) * 3;
                    }

                    if (armorItem.getItemMeta().hasEnchant(Enchantment.PROTECTION_FALL) && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        removeDMG += armorItem.getEnchantments().get(Enchantment.PROTECTION_FALL) * 5;
                    }

                    if (armorItem.getItemMeta().hasEnchant(Enchantment.PROTECTION_PROJECTILE) && e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                        armorPoints += armorItem.getEnchantments().get(Enchantment.PROTECTION_PROJECTILE) * 3;
                    }

                    for (PotionEffect x : ((Player) e.getEntity()).getActivePotionEffects()) {
                        if (x.getType().equals(PotionEffectType.ABSORPTION)) {
                            armorPoints += x.getAmplifier() * 5;
                        }
                    }
                }
            }

            removeDMG += startDamage * MathHelper.clamp(armorPoints, 0, 20) * 3 / 100;
            e.setDamage(0);

            if (armorPoints > 20) {
                removeDMG += startDamage * MathHelper.clamp(armorPoints, 20, 75) * 0.5 / 100;
            }

            int resDamage = MathHelper.clamp((int) startDamage - (int) removeDMG, 0, 10000);

            if (((Player) e.getEntity()).getHealth() + ((CraftPlayer) e.getEntity()).getHandle().getAbsorptionHearts() - resDamage > 0) {
                ((Player) e.getEntity()).damage(resDamage);
            } else {
                String deathMessage = Util.formatString(((CraftPlayer) e.getEntity()).getDisplayName() + deathMsg.getOrDefault(e.getCause(), " &7died from &7&kIDKDONTADDEDYET"));

                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(deathMessage);
                }

                ((CraftPlayer) e.getEntity()).setHealth(0.0);
                ((CraftPlayer) e.getEntity()).setRealHealth(0.0);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        ((CraftPlayer) e.getEntity()).getHandle().setAbsorptionHearts(0);
    }

    public void addDeathMessage(EntityDamageEvent.DamageCause d, String s) {
        deathMsg.put(d, s);
    }
}
