package me.ayunami2000.fabricnotebot.mixin;

import me.ayunami2000.fabricnotebot.Main;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    /*todo: make better*/
    @Inject(at = @At("HEAD"), method = "getPreferredEquipmentSlot", cancellable = true)
    private static void getPreferredEquipmentSlot(ItemStack itemStack, CallbackInfoReturnable<EquipmentSlot> cir) {
        EquipmentSlot equipmentSlot;
        Item item = itemStack.getItem();

        if (item != Blocks.CARVED_PUMPKIN.asItem() || !(((BlockItem) item).getBlock() instanceof AbstractSkullBlock)) {
            if (item instanceof ArmorItem) {
                equipmentSlot = ((ArmorItem) item).getSlotType();
            } else if (item == Items.ELYTRA) {
                equipmentSlot = EquipmentSlot.CHEST;
            } else {
                equipmentSlot = item == Items.SHIELD ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
            }
        } else {
            equipmentSlot = EquipmentSlot.HEAD;
        }

        if (Main.randomArmor && equipmentSlot == EquipmentSlot.MAINHAND) {
            cir.setReturnValue((new EquipmentSlot[]{EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.FEET,EquipmentSlot.LEGS})[(int)Math.floor(Math.random()*4)]);
        }
    }
}