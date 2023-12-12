package me.jishuna.minetweaks.tweak.block;

import java.text.MessageFormat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.util.StringUtils;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class BeehiveDisplayTweak extends Tweak {

    @ConfigEntry("message")
    @Comment("Allows changing the format of the message sent to players")
    private final String message = StringUtils.miniMessageToLegacy("<gold>Honey: <yellow>{0}/{1}       <gold>Bees: <yellow>{2}/{3}");

    public BeehiveDisplayTweak() {
        this.name = "beehive-display";
        this.category = Category.BLOCK;

        registerEventConsumer(PlayerInteractEvent.class, this::onPlayerInteract);
    }

    private void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND || event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = event.getItem();
        if (item != null && !item.getType().isAir()) {
            return;
        }

        Block block = event.getClickedBlock();

        if (block.getBlockData() instanceof Beehive hiveData && block.getState() instanceof org.bukkit.block.Beehive hiveState) {
            BaseComponent[] component = TextComponent.fromLegacyText(MessageFormat.format(this.message, hiveData.getHoneyLevel(), hiveData.getMaximumHoneyLevel(), hiveState.getEntityCount(), hiveState.getMaxEntities()));
            event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
        }
    }
}
