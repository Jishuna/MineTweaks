package me.jishuna.minetweaks.tweaks.misc;

import org.bukkit.FluidCollisionMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.RayTraceResult;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import me.jishuna.minetweaks.nms.NMSManager;

@RegisterTweak(name = "swing_through_grass")
public class SwingThroughGrassTweak extends Tweak {
	public SwingThroughGrassTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEvent.class, this::onInteract);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Misc/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onInteract(PlayerInteractEvent event) {
		if (event.useItemInHand() == Result.DENY)
			return;
		if (event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.LEFT_CLICK_BLOCK
				|| event.getClickedBlock() == null || !event.getClickedBlock().isPassable())
			return;

		Player player = event.getPlayer();
		RayTraceResult result = player.getWorld().rayTrace(player.getEyeLocation(),
				player.getEyeLocation().getDirection(), 3.5, FluidCollisionMode.NEVER, true, 0,
				entity -> entity.getUniqueId() != player.getUniqueId());

		if (result != null && result.getHitEntity() != null && result.getHitEntity()instanceof LivingEntity entity) {
			NMSManager.getAdapter().attack(player, entity);
		}
	}
}
