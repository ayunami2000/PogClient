package me.ayunami2000.fabricnotebot.mixin;

import me.ayunami2000.fabricnotebot.SummonCommand;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(method = "onKey", at = @At(value = "INVOKE", target = "net/minecraft/client/util/InputUtil.isKeyPressed(JI)Z", ordinal = 5), cancellable = true)
    private void onKeyEvent(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo callbackInfo) {
        //if(Main.keyBinding.matchesKey(key,scanCode)&&action==1) NotebotPlayer.playASong();
        if(key==InputUtil.GLFW_KEY_M&&action==1) SummonCommand.doSummon(Items.AXOLOTL_SPAWN_EGG);
    }
}