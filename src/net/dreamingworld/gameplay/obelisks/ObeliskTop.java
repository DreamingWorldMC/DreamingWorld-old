package net.dreamingworld.gameplay.obelisks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.blocks.CustomBlock;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class ObeliskTop extends CustomBlock {

    protected int airMC, earthMC, fireMC, waterMC;

    public ObeliskTop() {
        airMC = 5;
        earthMC = 5;
        fireMC = 5;
        waterMC = 5;
    }

    @Override
    public boolean blockBreak(Block block) {
        int airC = ThreadLocalRandom.current().nextInt(0, airMC);
        int earthC = ThreadLocalRandom.current().nextInt(0, earthMC);
        int fireC = ThreadLocalRandom.current().nextInt(0, fireMC);
        int waterC = ThreadLocalRandom.current().nextInt(0, waterMC);

        Location l = block.getLocation();
        World w = l.getWorld();

        ItemStack air = DreamingWorld.getInstance().getItemManager().get("air_gem");
        ItemStack earth = DreamingWorld.getInstance().getItemManager().get("earth_gem");
        ItemStack fire = DreamingWorld.getInstance().getItemManager().get("fire_gem");
        ItemStack water = DreamingWorld.getInstance().getItemManager().get("water_gem");

        air.setAmount(airC);
        earth.setAmount(earthC);
        fire.setAmount(fireC);
        water.setAmount(waterC);

        w.dropItem(l, air);
        w.dropItem(l, earth);
        w.dropItem(l, fire);
        w.dropItem(l, water);

        return false;
    }

    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.TOWN_AURA, location.add(0.5, 0.5, 0.5), 10);
    }
}
