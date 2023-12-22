package me.jishuna.minetweaks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.inventory.InventoryAPI;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.minetweaks.command.MineTweaksCommandHandler;
import me.jishuna.minetweaks.listener.EntityListeners;
import me.jishuna.minetweaks.listener.MiscListeners;
import me.jishuna.minetweaks.listener.PacketListener;
import me.jishuna.minetweaks.listener.PlayerListeners;

public class MineTweaks extends JavaPlugin {
    private static boolean hasPackets;

    @Override
    public void onEnable() {
        JishLib.initialize(this);
        MessageAPI.initialize("messages.lang");
        ConfigApi.initialize();
        InventoryAPI.initialize();

        PluginManager manager = Bukkit.getPluginManager();
        hasPackets = manager.isPluginEnabled("ProtocolLib");

        manager.registerEvents(new PlayerListeners(), this);
        manager.registerEvents(new EntityListeners(), this);
        manager.registerEvents(new MiscListeners(), this);

        if (hasPackets) {
            PacketListener.register(this);
        }

        getCommand("minetweaks").setExecutor(new MineTweaksCommandHandler(this));

        Bukkit.getScheduler().runTaskTimer(this, Registries.TWEAKS::tick, 5, 5);
    }

    public static boolean hasPackets() {
        return hasPackets;
    }
}
