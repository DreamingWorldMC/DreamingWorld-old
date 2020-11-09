package net.dreamingworld.core.customdamage;



import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.minecraft.server.v1_8_R3.MathHelper;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.HashMap;
import java.util.Map;

public class CustomDamage implements Listener {

    private Map<EntityDamageEvent.DamageCause, String> deathMsg;

    public CustomDamage() {
        deathMsg = new HashMap<>();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            double startDamage = e.getDamage();
            int armorPoints = 0;
            double removeDMG = 0;
            ItemStack[] armor = ((Player)e.getEntity()).getInventory().getArmorContents();

            for (ItemStack armorItem : armor) {
                if (armorItem == null)
                    continue;

                if (DreamingWorld.getInstance().getCustomArmor().getPiece(TagWizard.getItemTag(armorItem, "id")) != -1 )
                    armorPoints += DreamingWorld.getInstance().getCustomArmor().getPiece(TagWizard.getItemTag(armorItem, "id"));

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
                    if (armorItem.getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL))
                        armorPoints += armorItem.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL);
                    if (armorItem.getItemMeta().hasEnchant(Enchantment.PROTECTION_FIRE)) {
                        if (e.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || e.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK) || e.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) {
                            armorPoints += armorItem.getEnchantments().get(Enchantment.PROTECTION_FIRE) * 3;
                        }
                    }
                    if (armorItem.getItemMeta().hasEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
                        if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) || e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
                            armorPoints += armorItem.getEnchantments().get(Enchantment.PROTECTION_EXPLOSIONS) * 3;
                        }
                    }
                    if (armorItem.getItemMeta().hasEnchant(Enchantment.PROTECTION_FALL) && e.getCause() == EntityDamageEvent.DamageCause.FALL)
                        removeDMG += armorItem.getEnchantments().get(Enchantment.PROTECTION_FALL) * 5;
                    if (armorItem.getItemMeta().hasEnchant(Enchantment.PROTECTION_PROJECTILE) && e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE)
                        armorPoints += armorItem.getEnchantments().get(Enchantment.PROTECTION_PROJECTILE) * 3;

                }
            }

            removeDMG += startDamage * MathHelper.clamp(armorPoints, 0, 20) * 3 / 100;

            if (armorPoints > 20)
                removeDMG += startDamage * MathHelper.clamp(armorPoints, 20, 75) * 0.5 / 100;

            e.setDamage(0);

            int resDamage = MathHelper.clamp((int) startDamage - (int) removeDMG, 0, 10000);


            if (!((Player) e.getEntity()).hasPotionEffect(PotionEffectType.ABSORPTION)) {
                if (((Player) e.getEntity()).getHealth() - resDamage > 0) {
                    ((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getHealth() - resDamage);
                }
                else {
                    ((Player) e.getEntity()).setHealth(0);

                    String deathMessage;

                    if (deathMsg.containsKey(e.getCause())) {
                        deathMessage = deathMsg.get(e.getCause());
                    }
                    else {
                        deathMessage = "&7 died from &7&kIDKDONTADDEDYET";
                    }

                    Bukkit.broadcastMessage(Util.formatString("&9" + ((Player) e.getEntity()).getDisplayName() + deathMessage));
                }
            }
            else {
                for (PotionEffect x : ((Player) e.getEntity()).getActivePotionEffects()) {
                    if (x.getType().equals(PotionEffectType.ABSORPTION) && e.getDamage() > 0) {
                        ((Player) e.getEntity()).removePotionEffect(PotionEffectType.ABSORPTION);
                        ((Player) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, x.getDuration(), x.getAmplifier() - 1));
                    }
                }
            }
        }
    }

    public void addDeathMessage(EntityDamageEvent.DamageCause d, String s) {
        deathMsg.put(d, s);
    }

}
