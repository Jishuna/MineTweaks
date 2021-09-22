package me.jishuna.minetweaks.api.module;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ModuleManager {
	
	private Map<String, TweakModule> modules = new HashMap<>();
	
	public void registerModule(TweakModule module) {
		this.modules.put(module.getName(), module);
	}
	
	public TweakModule getModule(String key) {
		return this.modules.get(key);
	}
	
	public Optional<TweakModule> getModuleOptional(String key) {
		return Optional.ofNullable(getModule(key));
	}

	public Collection<TweakModule> getModules() {
		return this.modules.values();
	}

}
