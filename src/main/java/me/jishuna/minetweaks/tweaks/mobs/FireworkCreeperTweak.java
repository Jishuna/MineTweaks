package me.jishuna.minetweaks.tweaks.mobs;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("firework_creepers")
public class FireworkCreeperTweak extends Tweak {

	public FireworkCreeperTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(EntityExplodeEvent.class, this::onEntityExplode);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Mobs/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, false);
		});
	}

	private void onEntityExplode(EntityExplodeEvent event) {
		if (event.getEntityType() == EntityType.CREEPER) {
			event.blockList().clear();

			Firework firework = event.getEntity().getWorld().spawn(event.getLocation(), Firework.class);
			Random random = ThreadLocalRandom.current();

			FireworkMeta meta = firework.getFireworkMeta();

			int count = random.nextInt(3) + 1;
			for (int i = 0; i < count; i++) {
				FireworkEffect.Builder builder = FireworkEffect.builder().flicker(random.nextBoolean())
						.trail(random.nextBoolean()).with(Type.BURST).withColor(getRandomColor(random));

				if (random.nextBoolean()) {
					builder.withFade(getRandomColor(random));
				}
				meta.addEffect(builder.build());
			}

			firework.setFireworkMeta(meta);
			firework.detonate();
		}
	}

	private Color getRandomColor(Random random) {
		return Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	}
}
