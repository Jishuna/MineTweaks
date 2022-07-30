package me.jishuna.minetweaks.nms;

import org.bukkit.Bukkit;

import me.jishuna.commonlib.utils.Version;
import me.jishuna.minetweaks.MineTweaks;

public class NMSManager {

	private static NMSAdapter adapter;

	public static void initAdapater(MineTweaks plugin) {
		String version = Version.getServerVersion();

		try {
			adapter = (NMSAdapter) Class.forName("me.jishuna.minetweaks.nms." + version + ".NMSAdapter")
					.getDeclaredConstructor().newInstance();
			plugin.getLogger().info("Supported server version detected: " + version);
		} catch (ReflectiveOperationException e) {
			plugin.getLogger().severe("Server version \"" + version + "\" is unsupported! Please check for updates!");
			Bukkit.getPluginManager().disablePlugin(plugin);
		}
	}

	public static NMSAdapter getAdapter() {
		return adapter;
	}

}
