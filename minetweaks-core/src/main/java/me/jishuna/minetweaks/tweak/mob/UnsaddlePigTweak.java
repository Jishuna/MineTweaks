package me.jishuna.minetweaks.tweak.mob;

import org.bukkit.Material;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class UnsaddlePigTweak extends Tweak {

    public UnsaddlePigTweak() {
        this.name = "unsaddle-pigs";
        this.category = Category.MOB;

        registerEventConsumer(PlayerInteractEntityEvent.class, this::onEntityInteract);
    }

    private void onEntityInteract(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Pig pig)) {
            return;
        }

        Player player = event.getPlayer();
        if (!player.isSneaking() || !pig.hasSaddle() || !player.getEquipment().getItemInMainHand().getType().isAir()) {
            return;
        }

        event.setCancelled(true);
        pig.setSaddle(false);
        player.getEquipment().setItemInMainHand(new ItemStack(Material.SADDLE));
    }
}
