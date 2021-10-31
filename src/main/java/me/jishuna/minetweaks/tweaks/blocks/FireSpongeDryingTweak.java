package me.jishuna.minetweaks.tweaks.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.items.ItemUtils;
import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "fire_dry_sponge")
public class FireSpongeDryingTweak extends Tweak {

	public FireSpongeDryingTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEvent.class, this::onInteract);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Blocks/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onInteract(PlayerInteractEvent event) {
		if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();
		ItemStack item = event.getItem();

		if (item == null)
			return;

		if (block.getType() == Material.WET_SPONGE
				&& item.getType() == Material.FLINT_AND_STEEL) {
			block.setType(Material.SPONGE);
			event.setUseItemInHand(Result.DENY);
			ItemUtils.reduceDurability(event.getPlayer(), item, event.getHand());
		}
	}
}
