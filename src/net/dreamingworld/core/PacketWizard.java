package net.dreamingworld.core;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PacketWizard {

    public static void sendParticle(EnumParticle type, Location location, int count) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, false, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0.5f, 0.5f, 0.5f, 1.0f, count);

        for (Entity player : location.getWorld().getNearbyEntities(location, 64, 64, 64)) {
            if (player instanceof CraftPlayer) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public static void sendParticle(EnumParticle type, Location location, int count, float distance, float speed) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, false, (float) location.getX(), (float) location.getY(), (float) location.getZ(), distance, distance, distance, speed, count);

        for (Entity player : location.getWorld().getNearbyEntities(location, 64, 64, 64)) {
            if (player instanceof CraftPlayer) {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public static void sendActionBar(Player player, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}"), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void tellraw(Player player, String json) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(json), (byte) 0);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
