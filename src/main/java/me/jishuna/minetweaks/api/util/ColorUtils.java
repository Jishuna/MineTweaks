package me.jishuna.minetweaks.api.util;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public class ColorUtils {

	public static DyeColor dyeToDyeColor(Material type) {
		return switch (type) {
		case WHITE_DYE -> DyeColor.WHITE;
		case ORANGE_DYE -> DyeColor.ORANGE;
		case MAGENTA_DYE -> DyeColor.MAGENTA;
		case LIGHT_BLUE_DYE -> DyeColor.LIGHT_BLUE;
		case YELLOW_DYE -> DyeColor.YELLOW;
		case LIME_DYE -> DyeColor.GREEN;
		case PINK_DYE -> DyeColor.PINK;
		case GRAY_DYE -> DyeColor.GRAY;
		case LIGHT_GRAY_DYE -> DyeColor.GRAY;
		case CYAN_DYE -> DyeColor.CYAN;
		case PURPLE_DYE -> DyeColor.PURPLE;
		case BLUE_DYE -> DyeColor.BLUE;
		case BROWN_DYE -> DyeColor.BROWN;
		case GREEN_DYE -> DyeColor.GREEN;
		case RED_DYE -> DyeColor.RED;
		case BLACK_DYE -> DyeColor.BLACK;
		default -> DyeColor.WHITE;
		};
	}

}
