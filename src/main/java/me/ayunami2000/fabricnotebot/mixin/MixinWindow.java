package me.ayunami2000.fabricnotebot.mixin;

import me.ayunami2000.fabricnotebot.Main;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class MixinWindow {
    @Inject(at = @At("HEAD"), method = "setPhase", cancellable = true)
    private void onSetPhase(String phase, CallbackInfo ci) {
        //System.out.println(phase);
        /*
        if(TabletDraw.jPenWindow==null&&phase=="Pre render") {
            TabletDraw.initStuff();
        }
        */
    }
    @Inject(at = @At("HEAD"), method = "setTitle", cancellable = true)
    private void onSetTitle(String title, CallbackInfo ci) {
        Main.clientTitle=title;
    }
}