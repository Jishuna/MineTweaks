package me.jishuna.minetweaks.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.minetweaks.api.module.TweakModule;

public class RecipeModule extends TweakModule {

	private static final String[] WOOD_TYPES = new String[] { "OAK", "BIRCH", "SPRUCE", "JUNGLE", "DARK_OAK", "ACACIA",
			"CRIMSON", "WARPED" };

	public RecipeModule(JavaPlugin plugin) {
		super(plugin, "recipes");
		
		addSubModule("unlock-on-join");
		addSubModule("dispensers-from-droppers");
		addSubModule("uncompress-quartz");
		addSubModule("red-sand-iron");
		addSubModule("red-sand-dye");
		addSubModule("hide-bundle");
		addSubModule("leather-bundle");
		addSubModule("rotten-flesh-to-leather");
		addSubModule("more-trapdoors");
		addSubModule("more-stairs");

		addEventHandler(PlayerJoinEvent.class, this::onJoin);
	}

	// Recipe changes
	@Override
	public void reload() {
		super.reload();

		if (!isEnabled())
			return;

		if (getBoolean("dispensers-from-droppers", true)) {
			ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getOwningPlugin(), "dropper_dispenser"),
					new ItemStack(Material.DISPENSER));
			recipe.shape("012", "132", "012");
			recipe.setIngredient('1', Material.STICK);
			recipe.setIngredient('2', Material.STRING);
			recipe.setIngredient('3', Material.DROPPER);
			Bukkit.addRecipe(recipe);
		}

		if (getBoolean("uncompress-quartz", true)) {
			ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(getOwningPlugin(), "uncompress_quartz"),
					new ItemStack(Material.QUARTZ, 4));
			recipe.addIngredient(Material.QUARTZ_BLOCK);
			Bukkit.addRecipe(recipe);
		}

		if (getBoolean("red-sand-iron", true)) {
			ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(getOwningPlugin(), "redden_sand"),
					new ItemStack(Material.RED_SAND));
			recipe.addIngredient(Material.SAND);
			recipe.addIngredient(Material.IRON_NUGGET);
			Bukkit.addRecipe(recipe);
		}

		if (getBoolean("red-sand-dye", false)) {
			ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(getOwningPlugin(), "redden_sand_dye"),
					new ItemStack(Material.RED_SAND));
			recipe.addIngredient(Material.SAND);
			recipe.addIngredient(Material.RED_DYE);
			Bukkit.addRecipe(recipe);
		}

		if (getBoolean("hide-bundle", true)) {
			ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getOwningPlugin(), "hide_bundle"),
					new ItemStack(Material.BUNDLE));
			recipe.shape("121", "202", "222");
			recipe.setIngredient('1', Material.STRING);
			recipe.setIngredient('2', Material.RABBIT_HIDE);
			Bukkit.addRecipe(recipe);
		}

		if (getBoolean("leather-bundle", false)) {
			ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getOwningPlugin(), "leather_bundle"),
					new ItemStack(Material.BUNDLE));
			recipe.shape("121", "202", "222");
			recipe.setIngredient('1', Material.STRING);
			recipe.setIngredient('2', Material.LEATHER);
			Bukkit.addRecipe(recipe);
		}

		if (getBoolean("rotten-flesh-to-leather", false)) {
			FurnaceRecipe recipe = new FurnaceRecipe(new NamespacedKey(getOwningPlugin(), "rotten_flesh_leather"),
					new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH, 0.1f,
					getInt("rotten-flesh-cook-time", 200));
			Bukkit.addRecipe(recipe);
		}

		if (getBoolean("more-trapdoors", true)) {
			for (String wood : WOOD_TYPES) {
				Material plank = Material.matchMaterial(wood + "_PLANKS");
				Material trapdoor = Material.matchMaterial(wood + "_TRAPDOOR");

				if (plank == null || trapdoor == null)
					continue;

				Bukkit.getRecipesFor(new ItemStack(trapdoor)).forEach(recipe -> {
					if (recipe instanceof Keyed keyed) {
						Bukkit.removeRecipe(keyed.getKey());
					}
				});

				ShapedRecipe recipe = new ShapedRecipe(
						new NamespacedKey(getOwningPlugin(), wood.toLowerCase() + "_trapdoors"),
						new ItemStack(trapdoor, getInt("more-trapdoors-amount", 12)));
				recipe.setGroup("wooden_trapdoor");
				recipe.shape("111", "111");
				recipe.setIngredient('1', plank);
				Bukkit.addRecipe(recipe);
			}
		}

		if (getBoolean("more-stairs", true)) {
			setupStairs();
		}
	}

	// This game has a lot of stairs
	private void setupStairs() {
		handleStairs(Material.CUT_COPPER, Material.CUT_COPPER_STAIRS);
		handleStairs(Material.EXPOSED_CUT_COPPER, Material.EXPOSED_CUT_COPPER_STAIRS);
		handleStairs(Material.WEATHERED_CUT_COPPER, Material.WEATHERED_CUT_COPPER_STAIRS);
		handleStairs(Material.OXIDIZED_CUT_COPPER, Material.OXIDIZED_CUT_COPPER_STAIRS);
		handleStairs(Material.WAXED_CUT_COPPER, Material.WAXED_CUT_COPPER_STAIRS);
		handleStairs(Material.WAXED_EXPOSED_CUT_COPPER, Material.WAXED_EXPOSED_CUT_COPPER_STAIRS);
		handleStairs(Material.WAXED_WEATHERED_CUT_COPPER, Material.WAXED_WEATHERED_CUT_COPPER_STAIRS);
		handleStairs(Material.WAXED_OXIDIZED_CUT_COPPER, Material.WAXED_OXIDIZED_CUT_COPPER_STAIRS);
		handleStairs(Material.COBBLESTONE, Material.COBBLESTONE_STAIRS);
		handleStairs(Material.COBBLED_DEEPSLATE, Material.COBBLED_DEEPSLATE_STAIRS);
		handleStairs(Material.DEEPSLATE_BRICKS, Material.DEEPSLATE_BRICK_STAIRS);
		handleStairs(Material.POLISHED_DEEPSLATE, Material.POLISHED_DEEPSLATE_STAIRS);
		handleStairs(Material.DEEPSLATE_TILES, Material.DEEPSLATE_TILE_STAIRS);
		handleStairs(Material.BLACKSTONE, Material.BLACKSTONE_STAIRS);
		handleStairs(Material.POLISHED_BLACKSTONE, Material.POLISHED_BLACKSTONE_STAIRS);
		handleStairs(Material.POLISHED_BLACKSTONE_BRICKS, Material.POLISHED_BLACKSTONE_BRICK_STAIRS);
		handleStairs(Material.PURPUR_BLOCK, Material.PURPUR_STAIRS);
		handleStairs(Material.PRISMARINE, Material.PRISMARINE_STAIRS);
		handleStairs(Material.PRISMARINE_BRICKS, Material.PRISMARINE_BRICK_STAIRS);
		handleStairs(Material.DARK_PRISMARINE, Material.DARK_PRISMARINE_STAIRS);
		handleStairs(Material.STONE, Material.STONE_STAIRS);
		handleStairs(Material.MOSSY_COBBLESTONE, Material.MOSSY_COBBLESTONE_STAIRS);
		handleStairs(Material.DIORITE, Material.DIORITE_STAIRS);
		handleStairs(Material.GRANITE, Material.GRANITE_STAIRS);
		handleStairs(Material.ANDESITE, Material.ANDESITE_STAIRS);
		handleStairs(Material.POLISHED_DIORITE, Material.POLISHED_DIORITE_STAIRS);
		handleStairs(Material.POLISHED_GRANITE, Material.POLISHED_GRANITE_STAIRS);
		handleStairs(Material.POLISHED_ANDESITE, Material.POLISHED_ANDESITE_STAIRS);
		handleStairs(Material.SANDSTONE, Material.SANDSTONE_STAIRS);
		handleStairs(Material.SMOOTH_SANDSTONE, Material.SMOOTH_SANDSTONE_STAIRS);
		handleStairs(Material.RED_SANDSTONE, Material.RED_SANDSTONE_STAIRS);
		handleStairs(Material.SMOOTH_RED_SANDSTONE, Material.SMOOTH_RED_SANDSTONE_STAIRS);
		handleStairs(Material.STONE_BRICKS, Material.STONE_BRICK_STAIRS);
		handleStairs(Material.MOSSY_STONE_BRICKS, Material.MOSSY_STONE_BRICK_STAIRS);
		handleStairs(Material.END_STONE_BRICKS, Material.END_STONE_BRICK_STAIRS);
		handleStairs(Material.QUARTZ_BLOCK, Material.QUARTZ_STAIRS);
		handleStairs(Material.SMOOTH_QUARTZ, Material.SMOOTH_QUARTZ_STAIRS);
		handleStairs(Material.BRICKS, Material.BRICK_STAIRS);
		handleStairs(Material.NETHER_BRICKS, Material.NETHER_BRICK_STAIRS);
		handleStairs(Material.RED_NETHER_BRICKS, Material.RED_NETHER_BRICK_STAIRS);
		handleStairs(Material.OAK_PLANKS, Material.OAK_STAIRS);
		handleStairs(Material.BIRCH_PLANKS, Material.BIRCH_STAIRS);
		handleStairs(Material.SPRUCE_PLANKS, Material.SPRUCE_STAIRS);
		handleStairs(Material.JUNGLE_PLANKS, Material.JUNGLE_STAIRS);
		handleStairs(Material.ACACIA_PLANKS, Material.ACACIA_STAIRS);
		handleStairs(Material.DARK_OAK_PLANKS, Material.DARK_OAK_STAIRS);
		handleStairs(Material.CRIMSON_PLANKS, Material.CRIMSON_STAIRS);
		handleStairs(Material.WARPED_PLANKS, Material.WARPED_STAIRS);
	}

	// Stairs
	private void handleStairs(Material source, Material stair) {
		String group = null;

		for (Recipe oldRecipe : Bukkit.getRecipesFor(new ItemStack(stair))) {
			if (oldRecipe instanceof Keyed keyed && oldRecipe.getResult().getAmount() > 1) {
				Bukkit.removeRecipe(keyed.getKey());

				if (oldRecipe instanceof ShapedRecipe shaped && !shaped.getGroup().isBlank()) {
					group = shaped.getGroup();
				}
			}
		}

		ShapedRecipe recipe = new ShapedRecipe(
				new NamespacedKey(getOwningPlugin(), source.toString().toLowerCase() + "_stairs"),
				new ItemStack(stair, getInt("more-stairs-amount", 12)));

		if (group != null)
			recipe.setGroup(group);

		recipe.shape("100", "110", "111");
		recipe.setIngredient('1', source);
		Bukkit.addRecipe(recipe);
	}

	// Join recipes
	private void onJoin(PlayerJoinEvent event) {
		if (!getBoolean("unlock-on-join", true))
			return;

		Iterator<Recipe> iterator = Bukkit.recipeIterator();
		List<NamespacedKey> recipes = new ArrayList<>();

		while (iterator.hasNext()) {
			Recipe recipe = iterator.next();
			if (recipe instanceof Keyed keyed) {
				recipes.add(keyed.getKey());
			}
		}

		event.getPlayer().discoverRecipes(recipes);
	}
}