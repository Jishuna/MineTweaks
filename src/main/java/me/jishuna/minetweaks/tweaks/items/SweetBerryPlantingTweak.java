package me.jishuna.minetweaks.tweaks.items;

import org.bukkit.Material;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("sweet_berry_planting")
public class SweetBerryPlantingTweak extends Tweak {
	public SweetBerryPlantingTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEvent.class, EventPriority.HIGH, this::onInteract);
	}
	
	@Override
	public boolean isToggleable() {
		return true;
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Items/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onInteract(PlayerInteractEvent event) {
		if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getItem() == null
				|| event.getItem().getType() != Material.SWEET_BERRIES)
			return;

		if (!isDisabled(event.getPlayer()) && !event.getPlayer().isSneaking())
			event.setUseInteractedBlock(Result.DENY);
	}
}
