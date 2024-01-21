package me.jishuna.minetweaks.tweak.mob.head;

import me.jishuna.jishlib.config.annotation.ConfigEntry;

public class MobHeadData {

    @ConfigEntry("name")
    private final String name;
    @ConfigEntry("texture")
    private final String texture;

    public MobHeadData() {
        this(null, null);
    }

    public MobHeadData(String name, String texture) {
        this.name = name;
        this.texture = texture;
    }

    public String getName() {
        return this.name;
    }

    public String getTexture() {
        return this.texture;
    }

}
