package me.jishuna.minetweaks.tweaks.items;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("armor_swap")
public class ArmorSwapTweak extends Tweak {
	public ArmorSwapTweak(MineTweaks plugin, String name) {
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
		Player player = event.getPlayer();
		if (event.useItemInHand() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_AIR || isDisabled(player))
			return;

		ItemStack item = event.getItem();
		if (item == null || item.getType().isAir())
			return;

		EquipmentSlot slot = getSlot(item);

		if (slot == null)
			return;

		EntityEquipment equipment = player.getEquipment();

		ItemStack current = equipment.getItem(slot);

		if (current == null || current.getType().isAir())
			return;

		equipment.setItem(event.getHand(), current);
		equipment.setItem(slot, item);
	}

	public EquipmentSlot getSlot(ItemStack item) {

		if (EnchantmentTarget.ARMOR_HEAD.includes(item))
			return EquipmentSlot.HEAD;

		if (EnchantmentTarget.ARMOR_TORSO.includes(item))
			return EquipmentSlot.CHEST;

		if (EnchantmentTarget.ARMOR_LEGS.includes(item))
			return EquipmentSlot.LEGS;

		if (EnchantmentTarget.ARMOR_FEET.includes(item))
			return EquipmentSlot.FEET;

		return null;
	}
}
