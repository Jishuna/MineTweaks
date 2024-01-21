package me.jishuna.minetweaks.tweak.mob.head;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.entity.EntityIdentifier;
import me.jishuna.jishlib.entity.EntityIdentifiers;
import me.jishuna.jishlib.inventory.InventorySession;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.util.StringUtils;
import me.jishuna.minetweaks.inventory.MobHeadInventory;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.ToggleableTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class MobHeadsTweak extends Tweak implements ToggleableTweak {
    private final Map<EntityIdentifier, ItemStack> headCache = new LinkedHashMap<>();

    @ConfigEntry("drop-chance")
    @Comment("The chance, out of 100, for a mob to drop their head.")
    private int chance = 20;

    @ConfigEntry("gui-name")
    @Comment("Allows customizing the name of the mob head gui.")
    private String guiName = StringUtils.miniMessageToLegacy("<black>Mob Heads ({0})");

    @ConfigEntry("heads")
    @Comment("The head texture and name for each mob.")
    @Comment("Textures are from https://minecraft-heads.com, you can also use this site to find replacement textures.")
    private Map<EntityIdentifier, MobHeadData> textures = MobHeads.getDefaultHeads();

    private MobHeadInventory inventory;

    public MobHeadsTweak() {
        super("mob-heads", Category.MOB);
        this.description = List
                .of(ChatColor.GRAY + "Gives mobs a " + ChatColor.GREEN + "%chance%%" + ChatColor.GRAY + " chance to drop their heads when killed.",
                        ChatColor.GRAY + "These heads can be used for decoration or placed on a noteblock to have it produce the mobs sound.", "",
                        ChatColor.GREEN + "Click to view available heads.");
    }

    @Override
    public Map<String, Object> getPlaceholders() {
        return Map.of("%chance%", ChatColor.GREEN.toString() + this.chance);
    }

    @Override
    public void reload() {
        super.reload();

        cacheHeads();
        this.inventory = new MobHeadInventory(new LinkedHashSet<>(this.headCache.values()), this.guiName);
    }

    @Override
    public void onMenuClick(InventoryClickEvent event, InventorySession session) {
        if (this.inventory != null) {
            session.changeTo(this.inventory, true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onDeath(EntityDeathEvent event) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        LivingEntity entity = event.getEntity();
        if (entity.getKiller() == null || !isEnabled(entity.getKiller()) || random.nextInt(100) >= this.chance) {
            return;
        }

        EntityIdentifier identifier = EntityIdentifiers.getIdentifier(entity);
        ItemStack item = this.headCache.get(identifier);
        if (item == null) {
            return;
        }

        event
                .getDrops()
                .add(ItemBuilder
                        .modifyItem(item)
                        .modify(SkullMeta.class, meta -> meta.setNoteBlockSound(getSound(entity)))
                        .build());
    }

    private void cacheHeads() {
        this.headCache.clear();

        for (Entry<EntityIdentifier, MobHeadData> entry : this.textures.entrySet()) {
            EntityIdentifier identifier = entry.getKey();
            MobHeadData data = entry.getValue();
            EntityType type = identifier.getType();
            Material material = Material.PLAYER_HEAD;

            if (type == EntityType.ZOMBIE) {
                material = Material.ZOMBIE_HEAD;
            } else if (type == EntityType.SKELETON) {
                material = Material.SKELETON_SKULL;
            } else if (type == EntityType.CREEPER) {
                material = Material.CREEPER_HEAD;
            } else if (type == EntityType.PIGLIN) {
                material = Material.PIGLIN_HEAD;
            } else if (type == EntityType.ENDER_DRAGON) {
                material = Material.DRAGON_HEAD;
            } else if (type == EntityType.WITHER_SKELETON) {
                material = Material.WITHER_SKELETON_SKULL;
            }

            ItemBuilder builder = ItemBuilder
                    .create(material);

            if (material == Material.PLAYER_HEAD) {
                builder
                        .name(ChatColor.YELLOW + data.getName())
                        .skullTexture(data.getTexture());
            }

            this.headCache.put(identifier, builder.build());
        }

        this.textures.clear();
    }

    private NamespacedKey getSound(LivingEntity entity) {
        if (entity instanceof Mob mob && mob.getAmbientSound() != null) {
            return mob.getAmbientSound().getKey();
        }
        return entity.getHurtSound().getKey();
    }
}
