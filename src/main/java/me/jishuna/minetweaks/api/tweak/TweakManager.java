package me.jishuna.minetweaks.api.tweak;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.bukkit.event.Event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import me.jishuna.commonlib.utils.ReflectionUtils;
import me.jishuna.commonlib.utils.Version;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;

public class TweakManager {
	private static final Class<?> TYPE_CLASS = Tweak.class;

	private final Set<TickingTweak> tickingTweaks = new HashSet<>();
	private final Map<String, Tweak> tweaks = new TreeMap<>();
	private final Multimap<Class<? extends Event>, Tweak> eventMap = ArrayListMultimap.create();
	private final Set<String> categories = new HashSet<>();

	public TweakManager(MineTweaks plugin) {
		loadTweaks(plugin);
	}

	// Pretty sure this is not an unchecked cast
	@SuppressWarnings("unchecked")
	public void loadTweaks(MineTweaks plugin) {
		this.tweaks.clear();
		this.eventMap.clear();

		String version = Version.getServerVersion();

		for (Class<?> clazz : ReflectionUtils.getAllClassesInSubpackages("me.jishuna.minetweaks.tweaks",
				this.getClass().getClassLoader())) {
			if (!TYPE_CLASS.isAssignableFrom(clazz))
				continue;

			for (RegisterTweak annotation : clazz.getAnnotationsByType(RegisterTweak.class)) {
				try {
					Tweak tweak = ((Class<? extends Tweak>) clazz)
							.getDeclaredConstructor(MineTweaks.class, String.class)
							.newInstance((Object) plugin, (Object) annotation.value());

					if (!tweak.isVersionValid(version))
						continue;

					tweak.reload();
					registerTweak(tweak);

					categories.add(tweak.getCategory().replace(" ", "_"));
				} catch (ReflectiveOperationException | IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Collection<Tweak> getTweaksForEvent(Class<? extends Event> clazz) {
		return this.eventMap.get(clazz);
	}

	public void registerTweak(Tweak tweak) {
		this.tweaks.put(tweak.getName(), tweak);

		tweak.getEventClasses().forEach(clazz -> this.eventMap.put(clazz, tweak));

		if (tweak instanceof TickingTweak ticking)
			this.tickingTweaks.add(ticking);
	}

	public Set<String> getCategories() {
		return this.categories;
	}

	public Tweak getTweak(String key) {
		return this.tweaks.get(key);
	}

	public Optional<Tweak> getTweakOptional(String key) {
		return Optional.ofNullable(getTweak(key));
	}

	public Collection<Tweak> getTweaks() {
		return this.tweaks.values();
	}

	public void tickAll() {
		this.tickingTweaks.forEach(tweak -> {
			if (tweak.isEnabled())
				tweak.tick();
		});
	}

}
