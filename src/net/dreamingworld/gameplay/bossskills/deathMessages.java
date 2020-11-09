package net.dreamingworld.gameplay.bossskills;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.customdamage.CustomDamage;
import org.bukkit.event.entity.EntityDamageEvent;

public class deathMessages {
    public deathMessages () {
        CustomDamage cd = DreamingWorld.getInstance().getCustomDamage();

        cd.addDeathMessage(EntityDamageEvent.DamageCause.FALL, " &f Fallen to his death");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, " &f Exploded");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, " &f Exploded");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.ENTITY_ATTACK, " &f has been killed by an entity");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.VOID, " &f Died in the void");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.FIRE, " &f Died to fire");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.LAVA, " &f Tried swimming in hot water");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.PROJECTILE, " &f Died to a projectile");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.DROWNING, " &f Tried breathing water");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.MAGIC, " &f Died to magic");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.LIGHTNING, " &f was struck by lightning");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.STARVATION, " &f Starved to death");

        cd.addDeathMessage(EntityDamageEvent.DamageCause.WITHER, " &f Withered away");
    }
}
