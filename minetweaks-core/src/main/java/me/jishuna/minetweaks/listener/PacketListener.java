package me.jishuna.minetweaks.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.Registries;

public class PacketListener extends PacketAdapter {
    private static final PacketType[] TYPES = { PacketType.Play.Server.WINDOW_ITEMS, PacketType.Play.Server.SET_SLOT };

    private PacketListener(MineTweaks plugin) {
        super(PacketAdapter.params().plugin(plugin).serverSide().optionAsync().types(TYPES));
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Registries.TWEAK.processPacket(event, event.getPacket());
    }

    public static void register(MineTweaks plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener(plugin));
    }
}
