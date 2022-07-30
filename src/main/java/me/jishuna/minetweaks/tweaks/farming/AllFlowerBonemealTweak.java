package me.jishuna.minetweaks.tweaks.farming;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import me.jishuna.minetweaks.api.util.FarmingUtils;

@RegisterTweak("all_flower_bonemeal")
public class AllFlowerBonemealTweak extends Tweak {

    public AllFlowerBonemealTweak(MineTweaks plugin, String name) {
        super(plugin, name);

        addEventHandler(PlayerInteractEvent.class, EventPriority.HIGH, this::onInteract);
    }

    @Override
    public void reload() {
        FileUtils.loadResource(getPlugin(), "Tweaks/Farming/" + this.getName() + ".yml").ifPresent(config -> {
            loadDefaults(config, true);
        });
    }

    private void onInteract(PlayerInteractEvent event) {
        if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Block block = event.getClickedBlock();
        ItemStack item = event.getItem();
        ItemStack itemdrop = new ItemStack(block.getType());

        if (item != null && item.getType() == Material.BONE_MEAL) {
            //FarmingUtils.handleTallPlant(item, block, height, event.getPlayer().getGameMode());
            if (block.getType() == Material.DANDELION) {
                block.getWorld().dropItem(block.getLocation().add(0.5, 0.5, 0.5) ,itemdrop);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount() - 1);
                }
            } else if (block.getType() == Material.POPPY) {
                block.getWorld().dropItem(block.getLocation().add(0.5, 0.5, 0.5) ,itemdrop);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount() - 1);
                }
            } else if (block.getType() == Material.BLUE_ORCHID) {
                block.getWorld().dropItem(block.getLocation().add(0.5, 0.5, 0.5) ,itemdrop);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount() - 1);
                }
            } else if (block.getType() == Material.ALLIUM) {
                block.getWorld().dropItem(block.getLocation().add(0.5, 0.5, 0.5) ,itemdrop);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount() - 1);
                }
            } else if (block.getType() == Material.AZURE_BLUET) {
                block.getWorld().dropItem(block.getLocation().add(0.5, 0.5, 0.5) ,itemdrop);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount() - 1);
                }
            } else if (block.getType() == Material.RED_TULIP) {
                block.getWorld().dropItem(block.getLocation().add(0.5, 0.5, 0.5) ,itemdrop);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount() - 1);
                }
            } else if (block.getType() == Material.ORANGE_TULIP) {
                block.getWorld().dropItem(block.getLocation().add(0.5, 0.5, 0.5) ,itemdrop);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount() - 1);
                }
            } else if (block.getType() == Material.WHITE_TULIP) {
                block.getWorld().dropItem(block.getLocation().add(0.5, 0.5, 0.5) ,itemdrop);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount() - 1);
                }
            } else if (block.getType() == Material.PINK_TULIP) {
                block.getWorld().dropItem(block.getLocation().add(0.5, 0.5, 0.5) ,itemdrop);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount() - 1);
                }
            } else if (block.getType() == Material.OXEYE_DAISY) {
                block.getWorld().dropItem(block.getLocation().add(0.5, 0.5, 0.5) ,itemdrop);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount() - 1);
                }
            } else if (block.getType() == Material.CORNFLOWER) {
                block.getWorld().dropItem(block.getLocation().add(0.5, 0.5, 0.5) ,itemdrop);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount() - 1);
                }
            } else if (block.getType() == Material.LILY_OF_THE_VALLEY) {
                block.getWorld().dropItem(block.getLocation().add(0.5, 0.5, 0.5) ,itemdrop);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount() - 1);
                }
            } else {
                return;
            }
        }
    }
}
