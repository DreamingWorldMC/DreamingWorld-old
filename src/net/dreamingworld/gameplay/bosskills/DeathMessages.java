package net.dreamingworld.gameplay.bosskills;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.customdamage.CustomDamage;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathMessages {
    public DeathMessages() {
        CustomDamage cd = DreamingWorld.getInstance().getCustomDamageManager();

        cd.addDeathMessage(EntityDamageEvent.DamageCause.FALL, " &ffallen to there death");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, " &fexploded");
        cd.addDeathMessage(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, " &fexploded");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.ENTITY_ATTACK, " &fhas been killed by an entity");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.VOID, " &fdied in the void");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.FIRE, " &fdied from fire");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.LAVA, " &ftried swimming in hot water");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.PROJECTILE, " &fdied from a projectile");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.DROWNING, " &ftried breathing water");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.MAGIC, " &fdied from magic");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.LIGHTNING, " &fwas struck by lightning");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.STARVATION, " &fstarved to death");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.WITHER, " &fwithered away");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.FIRE_TICK, " &fgot deep fried");
    }
}
