package me.jishuna.minetweaks.tweak.mob;

import java.util.Collections;
import org.bukkit.Material;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class UnsaddlePigTweak extends Tweak {

    public UnsaddlePigTweak() {
        this.name = "unsaddle-pigs";
        this.category = Category.MOB;
        this.listenedEvents = Collections.singletonList(PlayerInteractEntityEvent.class);
    }

    @Override
    public void handleEvent(EventContext<?> context) {
        if (!(context.getEntity() instanceof Pig pig)) {
            return;
        }

        Player player = context.getPlayer();
        if (!player.isSneaking() || !pig.hasSaddle() || !player.getEquipment().getItemInMainHand().getType().isAir()) {
            return;
        }

        ((Cancellable) context.getEvent()).setCancelled(true);
        pig.setSaddle(false);
        player.getEquipment().setItemInMainHand(new ItemStack(Material.SADDLE));
    }
}
