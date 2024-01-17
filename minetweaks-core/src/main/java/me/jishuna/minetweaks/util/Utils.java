package me.jishuna.minetweaks.util;

import me.jishuna.jishlib.message.MessageAPI;

public class Utils {
    public static String getDisplayString(boolean value) {
        return value ? MessageAPI.get("tweak.enabled") : MessageAPI.get("tweak.disabled");
    }
}
