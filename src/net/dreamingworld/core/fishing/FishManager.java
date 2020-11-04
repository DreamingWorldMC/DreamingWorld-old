package net.dreamingworld.core.fishing;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class FishManager implements Listener {

    public Set<CustomFishingLoot> seaLoot = new HashSet<>();

    public FishManager() {

    }


    public void registerLoot(CustomFishingLoot loot) {
        seaLoot.add(loot);
    }


    @EventHandler
    public void onCatch(PlayerFishEvent e) {
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            CustomFishingLoot item = randomizeFish();

            if (item != null) {
                item.catched(e.getPlayer(), e.getCaught().getLocation(), (Item) e.getCaught());
            }
        }
    }


    public CustomFishingLoot randomizeFish() {
        for (CustomFishingLoot loot : seaLoot) {
            int random = ThreadLocalRandom.current().nextInt(0, 101);

            if (random <= loot.getChance())
                return loot;
        }

        return null;
    }
}
