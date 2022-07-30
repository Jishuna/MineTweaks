package me.jishuna.minetweaks.tweaks.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

@RegisterTweak("beehive_display")
public class BeehiveDisplayTweak extends Tweak {

	private String message;

	public BeehiveDisplayTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEvent.class, EventPriority.HIGH, this::onInteract);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Blocks/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.message = ChatColor.translateAlternateColorCodes('&',
					config.getString("display-message", "Placeholder"));
		});
	}

	private void onInteract(PlayerInteractEvent event) {
		if (event.getHand() != EquipmentSlot.HAND || event.useInteractedBlock() == Result.DENY
				|| event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		ItemStack item = event.getItem();

		if (item != null && !item.getType().isAir())
			return;

		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		if (block.getBlockData() instanceof Beehive hiveData
				&& block.getState() instanceof org.bukkit.block.Beehive hiveState) {
			String honey = hiveData.getHoneyLevel() + "/" + hiveData.getMaximumHoneyLevel();
			String bees = hiveState.getEntityCount() + "/" + hiveState.getMaxEntities();
			String message = this.message.replace("%honey%", honey).replace("%bees%", bees);

			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
		}
	}
}
