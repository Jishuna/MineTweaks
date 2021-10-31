package me.jishuna.minetweaks.tweaks.mobs;

import org.bukkit.Material;
import org.bukkit.entity.Snowman;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "replace_snowman_head")
public class SnowmanHeadTweak extends Tweak {

	public SnowmanHeadTweak(JavaPlugin plugin, String name) {
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
		if (event.isCancelled())
			return;
		ItemStack item;

		if (event.getHand() == EquipmentSlot.HAND) {
			item = event.getPlayer().getEquipment().getItemInMainHand();
		} else {
			item = event.getPlayer().getEquipment().getItemInOffHand();
		}

		if (event.getRightClicked()instanceof Snowman snowman && snowman.isDerp()
				&& item.getType() == Material.CARVED_PUMPKIN) {
			event.setCancelled(true);
			snowman.setDerp(false);
			item.setAmount(item.getAmount() - 1);
		}
	}
}
