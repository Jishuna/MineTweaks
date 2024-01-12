package me.jishuna.minetweaks;

import org.bukkit.EntityEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import me.jishuna.jishlib.message.MessageAPI;

public class Utils {
    public static String getDisplayString(boolean value) {
        return value ? MessageAPI.get("tweak.enabled") : MessageAPI.get("tweak.disabled");
    }

    public static void reduceDurability(LivingEntity entity, ItemStack item, EquipmentSlot slot) {
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
                return;
            }

            item.setItemMeta(damagable);
        }
    }
}
