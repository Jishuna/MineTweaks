package me.jishuna.minetweaks;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EventContext<T extends Event> {

    private final T event;
    private final Entity entity;
    private final Player player;

    public EventContext(T event, Entity entity, Player player) {
        this.event = event;
        this.entity = entity;
        this.player = player;
    }

    public static <T extends Event> Builder<T> create(T event) {
        Builder<T> builder = new Builder<>();
        builder.event = event;

        return builder;
    }

    public T getEvent() {
        return this.event;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Player getPlayer() {
        return this.player;
    }

    public static class Builder<T extends Event> {
        private T event;
        private Entity entity;
        private Player player;

        public Builder<T> entity(Entity entity) {
            this.entity = entity;
            return this;
        }

        public Builder<T> player(Player player) {
            this.player = player;
            return this;
        }

        public EventContext<T> build() {
            return new EventContext<>(this.event, this.entity, this.player);
        }
    }
}
