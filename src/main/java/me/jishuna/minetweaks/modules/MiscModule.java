package me.jishuna.minetweaks.modules;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import me.jishuna.minetweaks.api.module.TweakModule;
import net.md_5.bungee.api.ChatColor;

public class MiscModule extends TweakModule {

	public MiscModule(JavaPlugin plugin) {
		super(plugin, "misc");

		addSubModule("remove-screen-fire");
		addSubModule("anvil-cobble-to-sand");
		addSubModule("dyeable-names");

		addEventHandler(PlayerInteractEntityEvent.class, this::onInteractEntity);
		addEventHandler(EntityChangeBlockEvent.class, this::onBlockLand);
		addEventHandler(EntityCombustEvent.class, this::onCombust);
	}

	private void onCombust(EntityCombustEvent event) {
		if (getBoolean("remove-screen-fire", true) && event.getEntity() instanceof Player player
				&& (player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)
						|| player.getGameMode() == GameMode.CREATIVE)) {
			event.setCancelled(true);
			player.setFireTicks(-20);
		}
	}

	private void onBlockLand(EntityChangeBlockEvent event) {
		if (getBoolean("anvil-cobble-to-sand", true) && event.getEntity() instanceof FallingBlock block) {
			Material material = block.getBlockData().getMaterial();
			if (material != Material.ANVIL && material != Material.CHIPPED_ANVIL && material != Material.DAMAGED_ANVIL)
				return;

			Block target = event.getBlock().getRelative(BlockFace.DOWN);

			if (target.getType() == Material.COBBLESTONE) {
				target.setType(Material.SAND);
				target.getWorld().spawnParticle(Particle.BLOCK_DUST, target.getLocation().add(0.5, 0.5, 0.5), 25, 0.3,
						0.3, 0.3, Material.COBBLESTONE.createBlockData());
			}
		}
	}

	private void onInteractEntity(PlayerInteractEntityEvent event) {
		if (event.isCancelled())
			return;

		if (getBoolean("dyeable-names", true)) {
			if (!event.getPlayer().isSneaking() || !(event.getRightClicked() instanceof LivingEntity livingEntity))
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
			item.setAmount(item.getAmount() - 1);
		}
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
