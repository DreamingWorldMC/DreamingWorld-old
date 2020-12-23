package net.dreamingworld.core.commands;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandOverrides implements Listener {

    public CommandOverrides() {
        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
        String m = e.getMessage();
        Player p = e.getPlayer();

        if (m.startsWith("/version") || m.startsWith("/about") || m.startsWith("/ver")) {
            e.setCancelled(true);

            p.sendMessage(Util.formatString("$(PC)PaperSpigot version: $(SC)" + Bukkit.getServer().getBukkitVersion()));
            p.sendMessage(Util.formatString("$(PC)DreamingWorld plugin version: $(SC)" + DreamingWorld.getInstance().getDescription().getVersion()));
        } else if ((m.startsWith("/reload") || m.startsWith("/rl")) && p.hasPermission("bukkit.command.reload")) {
            e.setCancelled(true);

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.kickPlayer(Util.formatString("$(PC)Do not worry friends, server is reloading :)"));
            }

            Bukkit.getServer().reload();
        }
    }
}
