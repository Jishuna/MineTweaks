package me.jishuna.minetweaks.tweak.mob;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Parrot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.entity.AxolotlIdentifier;
import me.jishuna.jishlib.entity.EntityIdentifier;
import me.jishuna.jishlib.entity.EntityIdentifiers;
import me.jishuna.jishlib.entity.MooshroomIdentifier;
import me.jishuna.jishlib.entity.ParrotIdentifier;
import me.jishuna.jishlib.inventory.InventorySession;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.util.StringUtils;
import me.jishuna.minetweaks.inventory.MobHeadInventory;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class MobHeadsTweak extends Tweak {
    private final Map<EntityIdentifier, ItemStack> headCache = new LinkedHashMap<>();

    @ConfigEntry("drop-chance")
    @Comment("The chance, out of 100, for a mob to drop their head.")
    private int chance = 100;

    @ConfigEntry("heads")
    @Comment("The head texture for each mob")
    private Map<EntityIdentifier, String> textures = getDefaultHeads();

    private MobHeadInventory inventory;

    public MobHeadsTweak() {
        super("mob-heads", Category.MOB);
        this.description = List.of(ChatColor.GRAY + "Gives mobs a chance to drop their heads when killed.");
    }

    @Override
    public void reload() {
        this.headCache.clear();
        super.reload();

        for (Entry<EntityIdentifier, String> entry : this.textures.entrySet()) {
            EntityIdentifier identifier = entry.getKey();
            this.headCache
                    .put(identifier, ItemBuilder
                            .create(Material.PLAYER_HEAD)
                            .name(ChatColor.YELLOW + StringUtils.capitalizeAll(identifier.getType().getKey().getKey().replace('_', ' ')) + " Head")
                            .skullTexture(entry.getValue())
                            .build());
        }

        this.inventory = new MobHeadInventory(this.headCache.values());
    }

    @Override
    public void onMenuClick(InventoryClickEvent event, InventorySession session) {
        if (this.inventory != null) {
            session.changeTo(this.inventory, true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onInteract(EntityDeathEvent event) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        LivingEntity entity = event.getEntity();
        if (entity.getKiller() == null || random.nextInt(100) >= this.chance) {
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

    private NamespacedKey getSound(LivingEntity entity) {
        if (entity instanceof Mob mob && mob.getAmbientSound() != null) {
            return mob.getAmbientSound().getKey();
        }
        return entity.getHurtSound().getKey();
    }

    private static Map<EntityIdentifier, String> getDefaultHeads() {
        Map<EntityIdentifier, String> map = new LinkedHashMap<>();

        map.put(new EntityIdentifier(EntityType.ALLAY), "e1c59dccde4b8535500dcf6794ca450663f607290e2510f6d8eb1e5eb71da5af");
        map.put(new AxolotlIdentifier(Axolotl.Variant.BLUE), "3e68dc59dbe767a5283164e3c94acc14badd34a857cec1a6c9f26a28ced29c83");
        map.put(new AxolotlIdentifier(Axolotl.Variant.CYAN), "d543bfa4ea2334667b6abfe40d852ecf55f8506a9c0c5ad8dd1e732f58d9c6c3");
        map.put(new AxolotlIdentifier(Axolotl.Variant.GOLD), "e17f8e58adf8e1c5ede450d2235cff612fc16c4b74490a0e7dbd5930b8c83e7f");
        map.put(new AxolotlIdentifier(Axolotl.Variant.LUCY), "7b910fbc216f724d29655155b2a3858a80f234a0cfed609e22fc670683ab777a");
        map.put(new AxolotlIdentifier(Axolotl.Variant.WILD), "8db22a0ea62d5d22867d852d01d396177b7a0d63e18cdc5e29ab39f5249c5074");
        map.put(new EntityIdentifier(EntityType.BAT), "3820a10db222f69ac2215d7d10dca47eeafa215553764a2b81bafd479e7933d1");
        map.put(new EntityIdentifier(EntityType.BEE), "4420c9c43e095880dcd2e281c81f47b163b478f58a584bb61f93e6e10a155f31");
        map.put(new EntityIdentifier(EntityType.BLAZE), "b20657e24b56e1b2f8fc219da1de788c0c24f36388b1a409d0cd2d8dba44aa3b");
        map.put(new EntityIdentifier(EntityType.ENDERMAN), "7a59bb0a7a32965b3d90d8eafa899d1835f424509eadd4e6b709ada50b9cf");
        map.put(new EntityIdentifier(EntityType.ENDERMITE), "5bc7b9d36fb92b6bf292be73d32c6c5b0ecc25b44323a541fae1f1e67e393a3e");
        map.put(new EntityIdentifier(EntityType.IRON_GOLEM), "e13f34227283796bc017244cb46557d64bd562fa9dab0e12af5d23ad699cf697");
        map.put(new MooshroomIdentifier(MushroomCow.Variant.BROWN), "199cd80c0a353b181b6588e9d820671c59ed9f27f1cfcd2195e65b918fb65e47");
        map.put(new MooshroomIdentifier(MushroomCow.Variant.RED), "767ac842a8d12c02d8a9f0d803eda918dc4d0c80e0f2ea02b4b9a7581cd7a4b5");
        map.put(new ParrotIdentifier(Parrot.Variant.BLUE), "20e03b10c15ee5601423867dfb8bcbcbc919ca96c0eea63073ec8e795eabd05f");
        map.put(new ParrotIdentifier(Parrot.Variant.CYAN), "bc6471f23547b2dbdf60347ea128f8eb2baa6a79b0401724f23bd4e2564a2b61");
        map.put(new ParrotIdentifier(Parrot.Variant.GRAY), "a3c34722ac64496c9b84d0c54019daae6185d6094990133ad6810eea3d24067a");
        map.put(new ParrotIdentifier(Parrot.Variant.GREEN), "5fc9a3b9d5879c2150984dbfe588cc2e61fb1de1e60fd2a469f69dd4b6f6a993");
        map.put(new ParrotIdentifier(Parrot.Variant.RED), "5d1a168bc72cb314f7c86feef9d9bc7612365244ce67f0a104fce04203430c1d");
        map.put(new EntityIdentifier(EntityType.PHANTOM), "7e95153ec23284b283f00d19d29756f244313a061b70ac03b97d236ee57bd982");
        map.put(new EntityIdentifier(EntityType.PIG), "ff218ef4de0005eddfb77ac5d4ef52728779b5e4d747fba3b79a39a43b2fd0c4");
        map.put(new EntityIdentifier(EntityType.PUFFERFISH), "17152876bc3a96dd2a2299245edb3beef647c8a56ac8853a687c3e7b5d8bb");
        map.put(new EntityIdentifier(EntityType.WITCH), "7e71a6eb303ab7e6f70ed54df9146a80eadf396417cee9495773ffbebfad887c");
        map.put(new EntityIdentifier(EntityType.WOLF), "d0498de6f5b09e0ce35a7292fe50b79fce9065d9be8e2a87c7a13566efb26d72");

        return map;
    }
}
