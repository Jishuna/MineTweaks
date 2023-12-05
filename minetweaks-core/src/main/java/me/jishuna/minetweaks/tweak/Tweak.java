package me.jishuna.minetweaks.tweak;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bukkit.event.Event;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.config.reloadable.ConfigReloadable;
import me.jishuna.minetweaks.EventContext;

public abstract class Tweak {
    private static final File TWEAK_FOLDER = new File(JishLib.getPlugin().getDataFolder(), "Tweaks");
    @SuppressWarnings("unused")
    private ConfigReloadable<Tweak> reloadable;

    @ConfigEntry("enabled")
    @Comment("Allows you to fully enable or disable this tweak")
    protected boolean enabled = true;

    protected String name;
    protected Category category = Category.MISC;
    protected List<Class<? extends Event>> listenedEvents = Collections.emptyList();

    public void load() {
        this.reloadable = ConfigApi
                .createReloadable(new File(TWEAK_FOLDER, this.category.name().toLowerCase() + File.separator + this.name + ".yml"), this)
                .saveDefaults()
                .load();
    }

    public void handleEvent(EventContext<?> context) {
    }

    public List<Class<? extends Event>> getListenedEvents() {
        return this.listenedEvents;
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
