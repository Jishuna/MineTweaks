package me.jishuna.minetweaks;

import org.bukkit.scheduler.BukkitRunnable;

import me.jishuna.minetweaks.api.tweak.TweakManager;

public class TickingTweakRunnable extends BukkitRunnable {

	private final TweakManager manager;

	public TickingTweakRunnable(TweakManager manager) {
		this.manager = manager;
	}

	@Override
	public void run() {
		manager.tickAll();
	}

}
