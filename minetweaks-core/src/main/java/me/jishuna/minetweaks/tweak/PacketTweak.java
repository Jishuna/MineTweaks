package me.jishuna.minetweaks.tweak;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import java.util.Collection;

public abstract class PacketTweak extends Tweak {
    public abstract Collection<PacketType> getListenedPackets();

    public void handlePacket(PacketEvent event, PacketContainer packet) {
    }
}
