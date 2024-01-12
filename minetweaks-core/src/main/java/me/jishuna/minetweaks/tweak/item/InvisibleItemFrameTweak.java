package me.jishuna.minetweaks.tweak.item;

import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.Utils;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class InvisibleItemFrameTweak extends Tweak {

    @ConfigEntry("enable-applying")
    @Comment("Allows players to apply invisibility to item frames using splash potions")
    private boolean enableApplying = true;

    @ConfigEntry("enable-removing")
    @Comment("Allows players to remove invisibility from item frames using splash water bottles")
    private boolean enableRemoving = true;

    public InvisibleItemFrameTweak() {
        super("invisible-item-frames", Category.ITEM);
        this.description = List
                .of(ChatColor.GRAY + "Allows players to make item frames invisible using invisibility splash potions.",
                        ChatColor.GRAY + "Splash water bottles can be used to remove invisibility from item frames.", "",
                        ChatColor.GRAY + "Invisibility Application: %applying%",
                        ChatColor.GRAY + "Invisibility Removal: %removal%");

        registerEventConsumer(PotionSplashEvent.class, this::onPotionSplash);
    }

    @Override
    public Map<String, Object> getPlaceholders() {
        return Map
                .of("%applying%", Utils.getDisplayString(this.enableApplying),
                        "%removal%", Utils.getDisplayString(this.enableRemoving));
    }

    private void onPotionSplash(PotionSplashEvent event) {
        ThrownPotion potion = event.getPotion();

        if (potion.getEffects().isEmpty() && this.enableRemoving) {
            setVisible(potion, true);
            return;
        }

        if (!this.enableApplying) {
            return;
        }

        for (PotionEffect effect : potion.getEffects()) {
            if (effect.getType().equals(PotionEffectType.INVISIBILITY)) {
                setVisible(potion, false);
                break;
            }
        }
    }

    private void setVisible(ThrownPotion source, boolean visible) {
        for (Entity entity : source.getNearbyEntities(3.5, 2, 3.5)) {
            if (entity instanceof ItemFrame frame) {
                frame.setVisible(visible);
            }
        }
    }
}
