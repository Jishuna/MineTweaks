package me.jishuna.minetweaks.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.tweak.TweakRegistry;

public class PacketListener extends PacketAdapter {
    private static final PacketType[] TYPES = { PacketType.Play.Server.WINDOW_ITEMS, PacketType.Play.Server.SET_SLOT };

    private final TweakRegistry registry;

    private PacketListener(MineTweaks plugin) {
        super(PacketAdapter.params().plugin(plugin).serverSide().optionAsync().types(TYPES));
        this.registry = plugin.getRegistry();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        this.registry.processPacket(event, event.getPacket());
    }

    public static void register(MineTweaks plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener(plugin));
    }
}
