package me.jishuna.minetweaks.tweak;

public abstract class TickingTweak extends Tweak {
    protected TickingTweak(String name, Category category) {
        super(name, category);
    }

    public abstract void tick();
}
