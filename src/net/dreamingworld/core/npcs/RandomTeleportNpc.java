package net.dreamingworld.core.npcs;

import com.mojang.authlib.GameProfile;
import net.dreamingworld.DreamingWorld;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RandomTeleportNpc implements Listener {

    private Location location;
    private float yaw;
    private float pitch;
    private List<String> players;

    public RandomTeleportNpc() {
        players = new ArrayList<>();

        location = new Location(Bukkit.getWorld("spawn"), -14.5, 119, -45.5);

        yaw = 0;
        pitch = -180;

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getPlayer().getWorld().getName().equals("spawn")) {
            if (e.getPlayer().getLocation().distance(location) < 64 && !players.contains(e.getPlayer().getName())) {
                players.add(e.getPlayer().getName());
                MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
                WorldServer nmsWorld = ((CraftWorld) e.getPlayer().getWorld()).getHandle();
                GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "§1Blimp captain");

                EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));

                npc.setLocation(location.getX(), location.getY(), location.getZ(), yaw, pitch);

                PlayerConnection connection = ((CraftPlayer) e.getPlayer()).getHandle().playerConnection;
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
                connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            }
        }
        else {
            if (players.contains(e.getPlayer().getName())) {
                players.remove(e.getPlayer().getName());
            }
        }
    }
}
