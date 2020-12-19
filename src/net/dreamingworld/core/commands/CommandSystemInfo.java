package net.dreamingworld.core.commands;

import net.dreamingworld.core.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandSystemInfo implements CommandExecutor, TabCompleter {

    public CommandSystemInfo() {
        Bukkit.getPluginCommand("systeminfo").setExecutor(this);
        Bukkit.getPluginCommand("systeminfo").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        Process uname = null;

        try {
            uname = Runtime.getRuntime().exec("uname -srm");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String kernel = null;

        if (uname != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(uname.getInputStream()));

            try {
                kernel = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        kernel = kernel == null ? "n/a" : kernel;

        String java = System.getProperty("java.version");

        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        String memory = usedMemory / 1024 / 1024 + "MB / " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB";
        String cores = String.valueOf(Runtime.getRuntime().availableProcessors());

        sender.sendMessage(Util.formatString("$(PC)===== $(SC)System Info $(PC)====="));

        sender.sendMessage(Util.formatString("$(PC)Kernel: $(SC)" + kernel));
        sender.sendMessage(Util.formatString("$(PC)Java version: $(SC)" + java));
        sender.sendMessage(Util.formatString("$(PC)RAM (used/total): $(SC)" + memory));
        sender.sendMessage(Util.formatString("$(PC)Available CPU cores: $(SC)" + cores));

        sender.sendMessage(Util.formatString("$(PC)====================="));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        return new ArrayList<>();
    }
}
