package me.jishuna.minetweaks.tweaks.items;

import org.bukkit.Tag;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "wearable_banners")
public class WearableBannersTweak extends Tweak {
	public WearableBannersTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(InventoryClickEvent.class, this::onClick);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Items/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onClick(InventoryClickEvent event) {
		if (event.getInventory() == null || !(event.getClickedInventory() instanceof PlayerInventory inventory)
				|| event.getRawSlot() != 5)
			return;

		ItemStack banner = event.getCursor();

		if (!Tag.BANNERS.isTagged(banner.getType()))
			return;

		event.getView().setCursor(event.getCurrentItem());
		inventory.setHelmet(banner);
		event.setCancelled(true);
	}
}
