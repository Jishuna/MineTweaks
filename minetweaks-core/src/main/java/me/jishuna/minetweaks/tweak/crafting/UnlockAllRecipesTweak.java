package me.jishuna.minetweaks.tweak.crafting;

import com.google.common.collect.Streams;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.event.player.PlayerJoinEvent;
import me.jishuna.jishlib.JishLib;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class UnlockAllRecipesTweak extends Tweak {

    public UnlockAllRecipesTweak() {
        this.name = "unlock-all-recipes";
        this.category = Category.CRAFTING;
        this.listenedEvents = Collections.singletonList(PlayerJoinEvent.class);
    }

    @Override
    public void handleEvent(EventContext<?> context) {
        CompletableFuture
                .supplyAsync(() -> Streams
                        .stream(Bukkit.recipeIterator())
                        .filter(Keyed.class::isInstance)
                        .map(Keyed.class::cast)
                        .map(Keyed::getKey)
                        .toList())
                .thenAccept(keys -> JishLib.run(() -> context.getPlayer().discoverRecipes(keys)));
    }
}
