package me.jishuna.minetweaks.tweak;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import java.util.Collection;

public abstract class PacketTweak extends Tweak {
    protected PacketTweak(String name, Category category) {
        super(name, category);
    }

    public abstract Collection<PacketType> getListenedPackets();

    public void handlePacket(PacketEvent event, PacketContainer packet) {
    }
}
