package me.jishuna.minetweaks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.inventory.InventoryAPI;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.minetweaks.listener.EntityListeners;
import me.jishuna.minetweaks.listener.MiscListeners;
import me.jishuna.minetweaks.listener.PacketListener;
import me.jishuna.minetweaks.listener.PlayerListeners;
import me.jishuna.minetweaks.tweak.TweakRegistry;

public class MineTweaks extends JavaPlugin {
    private static boolean hasPackets;
    private TweakRegistry registry;

    @Override
    public void onEnable() {
        JishLib.initialize(this);
        MessageAPI.initialize("messages.lang");
        ConfigApi.initialize();
        InventoryAPI.initialize();

        PluginManager manager = Bukkit.getPluginManager();
        hasPackets = manager.isPluginEnabled("ProtocolLib");

        this.registry = new TweakRegistry();

        manager.registerEvents(new PlayerListeners(this.registry), this);
        manager.registerEvents(new EntityListeners(this.registry), this);
        manager.registerEvents(new MiscListeners(this.registry), this);

        if (hasPackets) {
            PacketListener.register(this);
        }

        Bukkit.getScheduler().runTaskTimer(this, this.registry::tick, 5, 5);
    }

    public TweakRegistry getRegistry() {
        return this.registry;
    }

    public static boolean hasPackets() {
        return hasPackets;
    }
}
