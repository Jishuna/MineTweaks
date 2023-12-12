package me.jishuna.minetweaks.tweak.crafting;

import com.google.common.collect.Streams;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import me.jishuna.jishlib.JishLib;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class UnlockAllRecipesTweak extends Tweak {

    public UnlockAllRecipesTweak() {
        this.name = "unlock-all-recipes";
        this.category = Category.CRAFTING;

        registerEventConsumer(PlayerJoinEvent.class, this::onPlayerJoin);
    }

    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        CompletableFuture
                .supplyAsync(() -> Streams
                        .stream(Bukkit.recipeIterator())
                        .filter(Keyed.class::isInstance)
                        .map(Keyed.class::cast)
                        .map(Keyed::getKey)
                        .filter(player::hasDiscoveredRecipe)
                        .toList())
                .thenAccept(keys -> JishLib.run(() -> player.discoverRecipes(keys)));
    }
}
