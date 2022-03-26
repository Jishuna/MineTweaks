package me.jishuna.minetweaks.tweaks.blocks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import com.google.common.base.Enums;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("fast_leaf_decay")
public class FastLeafDecayTweak extends Tweak {
	private static final BlockFace[] FACES = new BlockFace[] { BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH,
			BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

	private final Set<Queue<Block>> decaySet = Collections.synchronizedSet(new HashSet<>());
	private int counter = 0;

	private int maxDistance;
	private DecayStyle style;

	public FastLeafDecayTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(BlockBreakEvent.class, EventPriority.HIGH, this::onBreak);

		Bukkit.getScheduler().runTaskTimer(plugin, this::handleDecay, 0, 1);
	}

	@Override
	public boolean isToggleable() {
		return true;
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Blocks/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.style = Enums.getIfPresent(DecayStyle.class, config.getString("decay-type").toUpperCase())
					.or(DecayStyle.RANDOM);
			this.maxDistance = config.getInt("max-distance", 12);
			this.maxDistance *= this.maxDistance;
		});
	}

	private void onBreak(BlockBreakEvent event) {
		if (event.isCancelled() || isDisabled(event.getPlayer()))
			return;

		Block block = event.getBlock();

		if (!Tag.LOGS.isTagged(block.getType()))
			return;

		Bukkit.getScheduler().runTaskLaterAsynchronously(getPlugin(), () -> {
			Queue<Block> queue = buildQueue(block);

			if (queue.isEmpty())
				return;

			this.decaySet.add(queue);
		}, 25);
	}

	private Queue<Block> buildQueue(Block block) {
		if (this.style == DecayStyle.RANDOM) {
			List<Block> list = new ArrayList<>();

			recurseLeaves(block.getLocation(), block, list);

			Collections.shuffle(list);
			return new ArrayDeque<>(list);
		} else {
			Comparator<Block> comparator;
			if (this.style == DecayStyle.BOTTOM_UP) {
				comparator = (a, b) -> a.getY() - b.getY();
			} else {
				comparator = (a, b) -> b.getY() - a.getY();
			}

			Queue<Block> queue = new PriorityQueue<>(comparator);
			recurseLeaves(block.getLocation(), block, queue);

			return queue;
		}
	}

	private void recurseLeaves(Location start, Block source, Collection<Block> collection) {
		for (BlockFace face : FACES) {
			Block target = source.getRelative(face);
			if (start.distanceSquared(target.getLocation()) > this.maxDistance)
				continue;

			if (!Tag.LEAVES.isTagged(target.getType()) || collection.contains(target))
				continue;

			BlockData data = target.getBlockData();

			if (!(data instanceof Leaves leaves) || leaves.isPersistent() || leaves.getDistance() < 7)
				continue;

			collection.add(target);
			recurseLeaves(start, target, collection);
		}
	}

	private void handleDecay() {
		counter = (counter + 1) % 5;

		Iterator<Queue<Block>> iterator = this.decaySet.iterator();
		while (iterator.hasNext()) {
			Queue<Block> blockQueue = iterator.next();

			if (this.style == DecayStyle.RANDOM) {
				handleRandomDecay(blockQueue);
			} else if (counter == 0) {
				handleSortedDecay(blockQueue);
			}

			if (blockQueue.isEmpty()) {
				iterator.remove();
			}
		}
	}

	private void handleRandomDecay(Queue<Block> blockQueue) {
		if (blockQueue.isEmpty())
			return;

		int count = (int) Math.ceil(blockQueue.size() / 40d);

		for (int i = 0; i < count; i++) {
			Block block = blockQueue.poll();
			if (block == null)
				break;

			block.getWorld().spawnParticle(Particle.BLOCK_DUST, block.getLocation().add(0.25, 0.25, 0.25), 5, 0.5, 0.5,
					0.5, 0, block.getBlockData());
			block.breakNaturally();
		}
	}

	private void handleSortedDecay(Queue<Block> blockQueue) {
		if (blockQueue.isEmpty())
			return;

		int y = blockQueue.peek().getY();
		Block block = blockQueue.poll();

		while (block != null) {
			if (block.getY() != y)
				break;

			block.getWorld().spawnParticle(Particle.BLOCK_DUST, block.getLocation().add(0.25, 0.25, 0.25), 5, 0.5, 0.5,
					0.5, 0, block.getBlockData());
			block.breakNaturally();

			block = blockQueue.poll();
		}
	}

	public static enum DecayStyle {
		RANDOM, TOP_DOWN, BOTTOM_UP;
	}
}
