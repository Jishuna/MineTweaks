package me.jishuna.minetweaks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.minetweaks.command.MineTweaksCommandHandler;
import me.jishuna.minetweaks.listener.PacketListener;

public class MineTweaks extends JavaPlugin {
    private static boolean hasPackets;

    @Override
    public void onEnable() {
        JishLib.initialize(this);
        MessageAPI.initialize("messages.lang");

        Registries.initialize(this);

        PluginManager manager = Bukkit.getPluginManager();
        hasPackets = manager.isPluginEnabled("ProtocolLib");

        if (hasPackets) {
            PacketListener.register(this);
        }

        getCommand("minetweaks").setExecutor(new MineTweaksCommandHandler(this));

        Bukkit.getScheduler().runTaskTimer(this, Registries.TWEAK::tick, 5, 5);
    }

    public static boolean hasPackets() {
        return hasPackets;
    }
}
