package net.dreamingworld.core;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketWizard {

    public static void sendParticle(EnumParticle type, Location location, int count) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, false, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0.5f, 0.5f, 0.5f, 1.0f, count);

        for (Player player : location.getWorld().getPlayers())
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
