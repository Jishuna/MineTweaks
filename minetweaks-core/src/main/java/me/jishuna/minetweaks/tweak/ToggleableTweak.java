package me.jishuna.minetweaks.tweak;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import me.jishuna.jishlib.pdc.CollectionType;
import me.jishuna.jishlib.pdc.PDCTypes;

public interface ToggleableTweak {
    static final NamespacedKey KEY = NamespacedKey.fromString("mintweaks:disabled");
    static final CollectionType<Set<String>, String> TWEAK_SET = new CollectionType<>(HashSet::new, PDCTypes.STRING);

    default boolean toggle(Player player) {
        String name = ((Tweak) this).getName();
        Set<String> disabled = player.getPersistentDataContainer().getOrDefault(KEY, TWEAK_SET, new HashSet<>());

        boolean enabled = true;
        if (disabled.contains(name)) {
            disabled.remove(name);
        } else {
            disabled.add(name);
            enabled = false;
        }

        player.getPersistentDataContainer().set(KEY, TWEAK_SET, disabled);
        return enabled;
    }

    default boolean isEnabled(Player player) {
        String name = ((Tweak) this).getName();
        Set<String> disabled = player.getPersistentDataContainer().get(KEY, TWEAK_SET);

        return disabled == null || !disabled.contains(name);
    }
}
