package me.jishuna.minetweaks.tweak.block;

import java.util.Collections;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class BeehiveDisplayTweak extends Tweak {

    public BeehiveDisplayTweak() {
        this.name = "beehive-display";
        this.category = Category.BLOCK;
        this.listenedEvents = Collections.singletonList(PlayerInteractEvent.class);
    }

    @Override
    public void handleEvent(EventContext<?> context) {
        PlayerInteractEvent event = (PlayerInteractEvent) context.getEvent();
        if (event.getHand() != EquipmentSlot.HAND || event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = event.getItem();
        if (item != null && !item.getType().isAir()) {
            return;
        }

        Block block = event.getClickedBlock();

        if (block.getBlockData() instanceof Beehive hiveData && block.getState() instanceof org.bukkit.block.Beehive hiveState) {
            String message = MessageAPI.get("beehive-display.message", hiveData.getHoneyLevel(), hiveData.getMaximumHoneyLevel(), hiveState.getEntityCount(), hiveState.getMaxEntities());
            context.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }
    }

}
