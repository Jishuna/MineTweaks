package me.jishuna.minetweaks.tweaks.mobs;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("silence_mobs")
public class SilenceMobsTweak extends Tweak {

	public SilenceMobsTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEntityEvent.class, EventPriority.HIGH, this::onInteract);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Mobs/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onInteract(PlayerInteractEntityEvent event) {
		if (event.isCancelled())
			return;
		ItemStack item;
		Player player = event.getPlayer();

		if (event.getHand() == EquipmentSlot.HAND) {
			item = player.getEquipment().getItemInMainHand();
		} else {
			item = player.getEquipment().getItemInOffHand();
		}

		if (!event.getRightClicked().isSilent() && item.getType() == Material.WHITE_WOOL) {
			event.setCancelled(true);
			event.getRightClicked().setSilent(true);
			player.playSound(player.getLocation(), Sound.BLOCK_WOOL_PLACE, 1f, 1f);
			
			if (player.getGameMode() != GameMode.CREATIVE)
				item.setAmount(item.getAmount() - 1);
		}

	}
}
