package me.jishuna.minetweaks.tweak.item;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.util.StringUtils;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class PlayerHeadsTweak extends Tweak {
    @ConfigEntry("head-name")
    @Comment("The custom name of dropped player heads.")
    private String headName = StringUtils.miniMessageToLegacy("<yellow>{0}'s Head");

    @ConfigEntry("head-lore")
    @Comment("The custom lore of dropped player heads.")
    private List<String> headLore = List.of(StringUtils.miniMessageToLegacy("<gray>Decapitated by {0}"));

    @ConfigEntry("drop-chance")
    @Comment("The chance, out of 100, for a mob to drop their head.")
    private int chance = 20;

    public PlayerHeadsTweak() {
        super("player-heads", Category.ITEM);
        this.description = List
                .of(ChatColor.GRAY + "Gives players a " + ChatColor.GREEN + "%chance%%" + ChatColor.GRAY + " chance to drop their heads when killed by another player.");
    }

    @Override
    public Map<String, Object> getPlaceholders() {
        return Map.of("%chance%", ChatColor.GREEN.toString() + this.chance);
    }

    @EventHandler(ignoreCancelled = true)
    private void onDeath(EntityDeathEvent event) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        LivingEntity entity = event.getEntity();
        if (entity.getKiller() == null || !(entity instanceof Player player) || random.nextInt(100) >= this.chance) {
            return;
        }

        event
                .getDrops()
                .add(ItemBuilder
                        .create(Material.PLAYER_HEAD)
                        .name(MessageFormat.format(this.headName, player.getName()))
                        .lore(this.headLore.stream().map(line -> MessageFormat.format(line, player.getKiller().getName())).toList())
                        .skullProfile(player.getPlayerProfile())
                        .build());
    }
}
