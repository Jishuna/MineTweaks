package me.jishuna.minetweaks.tweaks.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.Version;
import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "sign_editing")
public class SignEditTweak extends Tweak {

	public SignEditTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEvent.class, this::onInteract);

		addInvalidVersions(Version.V1_17_R1);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Blocks/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (!player.isSneaking() ||  event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();
		BlockState state = block.getState();

		if (!(state instanceof Sign sign))
			return;

		player.openSign(sign);
	}
}
