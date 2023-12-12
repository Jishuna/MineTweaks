package me.jishuna.minetweaks.tweak;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import org.bukkit.event.Event;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;

public abstract class Tweak {
    private static final File TWEAK_FOLDER = new File(JishLib.getPlugin().getDataFolder(), "Tweaks");

    @ConfigEntry("enabled")
    @Comment("Allows you to fully enable or disable this tweak")
    protected boolean enabled = true;

    private final Map<Class<? extends Event>, List<Consumer<? extends Event>>> eventConsumers = new HashMap<>();
    protected String name;
    protected Category category = Category.MISC;

    public void load() {
        ConfigApi
                .createReloadable(new File(TWEAK_FOLDER, this.category.name().toLowerCase() + File.separator + this.name + ".yml"), this)
                .saveDefaults()
                .load();
    }

    public <T extends Event> void registerEventConsumer(Class<T> clazz, Consumer<T> consumer) {
        this.eventConsumers.computeIfAbsent(clazz, k -> new ArrayList<>()).add(consumer);
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> void handleEvent(T event) {
        List<Consumer<? extends Event>> eventListeners = this.eventConsumers.get(event.getClass());
        if (eventListeners != null) {
            eventListeners.forEach(listener -> ((Consumer<T>) listener).accept(event));
        }
    }

    public Set<Class<? extends Event>> getEventClasses() {
        return Collections.unmodifiableSet(this.eventConsumers.keySet());
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Tweak other)) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }
}
