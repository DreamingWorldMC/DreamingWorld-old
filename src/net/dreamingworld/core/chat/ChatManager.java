package net.dreamingworld.core.chat;

import net.dreamingworld.core.Util;
import net.dreamingworld.core.chat.commands.CommandChannel;
import net.dreamingworld.core.chat.commands.CommandTell;
import net.dreamingworld.core.chat.commands.InstantMessageCommands;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatManager implements Listener {

    private Map<UUID, ChannelType> channels;

    public ChatManager() {
        new CommandTell();
        new CommandChannel();
        new InstantMessageCommands();

        channels = new HashMap<>();
    }


    public void setPlayerChannel(Player player, ChannelType type) {
        channels.put(player.getUniqueId(), type);
    }

    public ChannelType getPlayerChannel(Player player) {
        if (!channels.containsKey(player.getUniqueId())) {
            setPlayerChannel(player, ChannelType.GLOBAL);
        }

        return channels.get(player.getUniqueId());
    }


    public void sendMessage(String msg, Player player, ChannelType channel) {
        String message = Util.formatString(channel == ChannelType.GLOBAL ? "$(PC)G  &r" : "$(SC)L  &r") + player.getDisplayName() + ": " + msg;

        for (Entity entity : (channel == ChannelType.GLOBAL ? Bukkit.getOnlinePlayers() : player.getNearbyEntities(100, 255, 100))) {
            entity.sendMessage(message);
        }

        if (channel == ChannelType.LOCAL) {
            player.sendMessage(message);
        }
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);

        Player player = e.getPlayer();
        ChannelType channel = getPlayerChannel(player);

        sendMessage(e.getMessage(), player, channel);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        setPlayerChannel(e.getPlayer(), ChannelType.GLOBAL);
    }
}
