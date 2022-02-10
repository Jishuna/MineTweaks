package me.jishuna.minetweaks.tweaks.items;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "player_head_drops")
public class PlayerHeadDropTweak extends Tweak {
	private double chance;
	private String lore;

	public PlayerHeadDropTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerDeathEvent.class, this::onDeath);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Items/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.chance = config.getDouble("drop-chance", 20);
			this.lore = ChatColor.translateAlternateColorCodes('&', config.getString("head-lore", ""));
		});
	}

	private void onDeath(PlayerDeathEvent event) {
		if (event.getEntity().getKiller() == null)
			return;

		if (ThreadLocalRandom.current().nextDouble() * 100 >= this.chance)
			return;

		Player player = event.getEntity();
		Player killer = event.getEntity().getKiller();

		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
		ItemMeta meta = head.getItemMeta();

		meta.setDisplayName(ChatColor.RESET + player.getName() + "'s Head");
		((SkullMeta) meta).setOwningPlayer(player);

		if (!this.lore.isBlank()) {
			meta.setLore(Arrays.asList(this.lore.replace("%killer%", killer.getName())));
		}

		head.setItemMeta(meta);

		event.getDrops().add(head);

	}
}
