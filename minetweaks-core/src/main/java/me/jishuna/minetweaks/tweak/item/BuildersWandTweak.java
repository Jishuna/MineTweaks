package me.jishuna.minetweaks.tweak.item;

import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.BoundingBox;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.pdc.PDCTypes;
import me.jishuna.jishlib.pdc.PDCUtils;
import me.jishuna.jishlib.util.ItemUtils;
import me.jishuna.jishlib.util.StringUtils;
import me.jishuna.minetweaks.NamespacedKeys;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.util.BlockFace;
import me.jishuna.minetweaks.util.ConnectedBlockProcessor;
import me.jishuna.minetweaks.util.UndoContext;

@RegisterTweak
public class BuildersWandTweak extends CustomItemTweak {
    private static final NamespacedKey KEY = NamespacedKey.fromString("minetweaks:builders_wand");
    private static final BlockFace[] FACES = Arrays
            .stream(BlockFace.values())
            .filter(face -> face.ordinal() < 10)
            .toArray(BlockFace[]::new);

    private final Map<UUID, Deque<UndoContext>> undoData = new HashMap<>();

    @ConfigEntry("max-blocks")
    @Comment("The maximum amount of blocks a builders wand can place at once")
    private int maxBlocks = 64;

    @ConfigEntry("max-undos")
    @Comment("The maximum number of undos that will be saved per player")
    @Comment("After this limit is reached the oldest undo will start being discarded")
    private int maxUndos = 5;

    @ConfigEntry("messages.place")
    @Comment("Allows changing the formatting of the place message")
    private String placeMessage = StringUtils.miniMessageToLegacy("<green>{0} <white>block(s) placed. <dark_aqua>[Click to Undo]");

    @ConfigEntry("messages.place-hover")
    @Comment("Allows changing the formatting of the place-hover message")
    private String placeHover = StringUtils.miniMessageToLegacy("<green>Click to Undo");

    @ConfigEntry("messages.no-undo")
    @Comment("Allows changing the formatting of the no-undo message")
    private String noUndo = StringUtils.miniMessageToLegacy("<red>Nothing to undo.");

    @ConfigEntry("messages.undo-success")
    @Comment("Allows changing the formatting of the undo-success message")
    private String undoSuccess = StringUtils.miniMessageToLegacy("<green>Undo complete.");

    public BuildersWandTweak() {
        super("builders-wand", Category.ITEM, ChatColor.WHITE + "Builders Wand", KEY);
        this.description = List
                .of(ChatColor.GRAY + "Allows the crafting of a builders wand with sticks and a diamond.",
                        ChatColor.GRAY + "Can be used to quickly place multiple blocks adjacent to the clicked face of a group of connected blocks.", "",
                        ChatColor.GREEN + "Click to view recipe.");
    }

    @EventHandler(ignoreCancelled = true)
    private void onCommand(PlayerCommandPreprocessEvent event) {
        if (!event.getMessage().equals("/mt undobuilderswand")) {
            return;
        }

        event.setCancelled(true);

        Player player = event.getPlayer();
        Deque<UndoContext> undos = this.undoData.get(player.getUniqueId());
        if (undos == null || undos.isEmpty()) {
            player.sendMessage(this.noUndo);
            return;
        }

        undos.pollFirst().undo();
        player.sendMessage(this.undoSuccess);
    }

    @EventHandler(ignoreCancelled = true)
    private void onInteract(PlayerInteractEvent event) {
        if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK
                || !Objects.equals(PDCUtils.get(NamespacedKeys.CUSTOM_ITEM, PDCTypes.NAMESPACE, event.getItem()), KEY)) {
            return;
        }

        event.setCancelled(true);

        Block source = event.getClickedBlock();
        org.bukkit.block.BlockFace face = event.getBlockFace();
        Material type = source.getType();

        ConnectedBlockProcessor processor = new ConnectedBlockProcessor(source, this.maxBlocks, block -> check(block, face, type), FACES);
        Collection<Block> blocks = processor.process();
        if (blocks.isEmpty()) {
            return;
        }

        placeBlocks(blocks, face, source.getBlockData(), event.getPlayer(), event.getItem(), event.getHand());
    }

    private boolean check(Block block, org.bukkit.block.BlockFace face, Material type) {
        return block.getType() == type && block.getRelative(face).isEmpty();
    }

    private void placeBlocks(Collection<Block> blocks, org.bukkit.block.BlockFace face, BlockData data, Player player, ItemStack item, EquipmentSlot hand) {
        System.out.println("run");
        final World world = player.getWorld();
        final PlayerInventory inventory = player.getInventory();
        final ItemStack required = new ItemStack(data.getPlacementMaterial(), 1);
        final UndoContext undoContext = new UndoContext(player);
        final boolean creative = player.getGameMode() == GameMode.CREATIVE;
        int placed = 0;

        for (Block block : blocks) {
            if (!creative && !inventory.containsAtLeast(required, 1)) {
                break; // Out of items
            }

            Block target = block.getRelative(face);
            if (!data.isSupported(target) || !world.getNearbyEntities(BoundingBox.of(target)).isEmpty()) {
                continue; // Entity in the way / invalid location
            }

            target.setBlockData(data);
            inventory.removeItem(required);
            placed++;
            undoContext.addBlock(target);

            if (ItemUtils.reduceDurability(player, item, hand)) {
                break; // Tool broke
            }
        }

        if (placed > 0) {
            saveUndo(player.getUniqueId(), undoContext);

            BaseComponent component = StringUtils.combineComponents(TextComponent.fromLegacyText(MessageFormat.format(this.placeMessage, placed)));
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(this.placeHover)));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mt undobuilderswand"));

            player.spigot().sendMessage(component);
        }
    }

    private void saveUndo(UUID id, UndoContext context) {
        Deque<UndoContext> undos = this.undoData.computeIfAbsent(id, k -> new ArrayDeque<>());
        while (undos.size() >= this.maxUndos) {
            undos.pollLast();
        }

        undos.offerFirst(context);
    }

    @Override
    protected Recipe getDefaultRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(KEY, new ItemStack(Material.DIAMOND_SHOVEL));
        recipe.shape("002", "010", "100");
        recipe.setIngredient('1', Material.STICK);
        recipe.setIngredient('2', Material.DIAMOND);

        return recipe;
    }
}
