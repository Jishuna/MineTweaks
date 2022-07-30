package me.jishuna.minetweaks.tweaks.misc;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.RayTraceResult;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import me.jishuna.minetweaks.nms.NMSManager;

@RegisterTweak("swing_through_grass")
public class SwingThroughGrassTweak extends Tweak {
	private Set<Material> blacklist;

	public SwingThroughGrassTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEvent.class, EventPriority.HIGH, this::onInteract);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Misc/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.blacklist = new HashSet<>();

			for (String key : config.getStringList("blacklist")) {
				Material material = Material.matchMaterial(key.toUpperCase());

				if (material != null) {
					blacklist.add(material);
				}
			}
		});
	}

	private void onInteract(PlayerInteractEvent event) {
		if (event.useItemInHand() == Result.DENY)
			return;

		Block block = event.getClickedBlock();
		if (event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.LEFT_CLICK_BLOCK || block == null
				|| !block.isPassable() || blacklist.contains(block.getType()))
			return;

		Player player = event.getPlayer();
		RayTraceResult result = player.getWorld().rayTrace(player.getEyeLocation(),
				player.getEyeLocation().getDirection(), 3.5, FluidCollisionMode.NEVER, true, 0,
				entity -> entity.getUniqueId() != player.getUniqueId());

		if (result != null && result.getHitEntity() != null && result.getHitEntity() instanceof LivingEntity entity) {
			NMSManager.getAdapter().attack(player, entity);
		}
	}
}
