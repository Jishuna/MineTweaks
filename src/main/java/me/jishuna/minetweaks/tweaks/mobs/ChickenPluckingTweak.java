package me.jishuna.minetweaks.tweaks.mobs;

import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "chicken_plucking")
public class ChickenPluckingTweak extends Tweak {

	public ChickenPluckingTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEntityEvent.class, this::onInteract);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Mobs/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onInteract(PlayerInteractEntityEvent event) {
		if (event.isCancelled() || event.getHand() != EquipmentSlot.HAND || !event.getPlayer().isSneaking())
			return;
		ItemStack item = event.getPlayer().getEquipment().getItemInMainHand();

		if (!item.getType().isAir())
			return;

		if (event.getRightClicked()instanceof Chicken chicken) {
			chicken.damage(1);
			chicken.getWorld().dropItem(chicken.getLocation(), new ItemStack(Material.FEATHER));
		}
	}
}
