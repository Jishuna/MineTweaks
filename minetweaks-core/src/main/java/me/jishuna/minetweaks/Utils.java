package me.jishuna.minetweaks;

import org.bukkit.EntityEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import me.jishuna.jishlib.message.MessageAPI;

public class Utils {
    public static String getDisplayString(boolean value) {
        return value ? MessageAPI.get("tweak.enabled") : MessageAPI.get("tweak.disabled");
    }

    public static boolean reduceDurability(LivingEntity entity, ItemStack item, EquipmentSlot slot) {
        ItemMeta meta = item.getItemMeta();

        if (meta instanceof Damageable damagable) {
            damagable.setDamage(damagable.getDamage() + 1);

            if (damagable.getDamage() > item.getType().getMaxDurability()) {
                switch (slot) {
                case HEAD:
                    entity.playEffect(EntityEffect.BREAK_EQUIPMENT_HELMET);
                    break;
                case CHEST:
                    entity.playEffect(EntityEffect.BREAK_EQUIPMENT_CHESTPLATE);
                    break;
                case FEET:
                    entity.playEffect(EntityEffect.BREAK_EQUIPMENT_BOOTS);
                    break;
                case HAND:
                    entity.playEffect(EntityEffect.BREAK_EQUIPMENT_MAIN_HAND);
                    break;
                case LEGS:
                    entity.playEffect(EntityEffect.BREAK_EQUIPMENT_LEGGINGS);
                    break;
                case OFF_HAND:
                    entity.playEffect(EntityEffect.BREAK_EQUIPMENT_OFF_HAND);
                    break;
                }

                entity.getEquipment().setItem(slot, null);
                return true;
            }

            item.setItemMeta(damagable);
        }
        return false;
    }

    public static ShapedRecipe copyRecipe(ShapedRecipe original, ItemStack result) {
        ShapedRecipe newRecipe = new ShapedRecipe(original.getKey(), result);
        newRecipe.shape(original.getShape());
        original.getChoiceMap().forEach(newRecipe::setIngredient);
        newRecipe.setGroup(original.getGroup());

        return newRecipe;
    }
}
