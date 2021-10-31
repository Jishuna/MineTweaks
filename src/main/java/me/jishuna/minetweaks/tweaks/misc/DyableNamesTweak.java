package me.jishuna.minetweaks.tweaks.misc;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import net.md_5.bungee.api.ChatColor;

@RegisterTweak(name = "dyable_names")
public class DyableNamesTweak extends Tweak {
	public DyableNamesTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEntityEvent.class, this::onInteractEntity);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Misc/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onInteractEntity(PlayerInteractEntityEvent event) {
		if (event.isCancelled())
			return;

		if (!event.getPlayer().isSneaking() || !(event.getRightClicked()instanceof LivingEntity livingEntity))
			return;

		if (livingEntity.getCustomName() == null)
			return;

		ItemStack item;

		if (event.getHand() == EquipmentSlot.HAND) {
			item = event.getPlayer().getEquipment().getItemInMainHand();
		} else {
			item = event.getPlayer().getEquipment().getItemInOffHand();
		}

		if (!item.getType().toString().endsWith("_DYE"))
			return;

		ChatColor color = dyeToChatColor(item.getType());
		if (color.toString().equals(org.bukkit.ChatColor.getLastColors(livingEntity.getCustomName())))
			return;

		event.setCancelled(true);
		livingEntity.setCustomName(color + ChatColor.stripColor(livingEntity.getCustomName()));
		
		if (event.getPlayer().getGameMode() != GameMode.CREATIVE)
			item.setAmount(item.getAmount() - 1);
	}

	private ChatColor dyeToChatColor(Material type) {
		return switch (type) {
		case WHITE_DYE -> ChatColor.WHITE;
		case ORANGE_DYE -> ChatColor.GOLD;
		case MAGENTA_DYE -> ChatColor.of("#C968C3");
		case LIGHT_BLUE_DYE -> ChatColor.AQUA;
		case YELLOW_DYE -> ChatColor.YELLOW;
		case LIME_DYE -> ChatColor.GREEN;
		case PINK_DYE -> ChatColor.LIGHT_PURPLE;
		case GRAY_DYE -> ChatColor.DARK_GRAY;
		case LIGHT_GRAY_DYE -> ChatColor.GRAY;
		case CYAN_DYE -> ChatColor.DARK_AQUA;
		case PURPLE_DYE -> ChatColor.DARK_PURPLE;
		case BLUE_DYE -> ChatColor.BLUE;
		case BROWN_DYE -> ChatColor.of("#794521");
		case GREEN_DYE -> ChatColor.DARK_GREEN;
		case RED_DYE -> ChatColor.RED;
		case BLACK_DYE -> ChatColor.BLACK;
		default -> ChatColor.WHITE;
		};
	}
}
