package me.jishuna.minetweaks.tweaks.mobs;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Breedable;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("poison_potato_baby_mobs")
public class PoisonPotatoBabyMobs extends Tweak {

	public PoisonPotatoBabyMobs(MineTweaks plugin, String name) {
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
		if (event.getHand() == EquipmentSlot.HAND) {
			item = event.getPlayer().getEquipment().getItemInMainHand();
		} else {
			item = event.getPlayer().getEquipment().getItemInOffHand();
		}

		if (event.getRightClicked()instanceof Breedable breedable && !breedable.isAdult()
				&& item.getType() == Material.POISONOUS_POTATO) {
			event.setCancelled(true);
			breedable.setAgeLock(true);
			breedable.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
			event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1f, 1f);

			if (event.getPlayer().getGameMode() != GameMode.CREATIVE)
				item.setAmount(item.getAmount() - 1);
		}

	}
}
