package me.ayunami2000.fabricnotebot.mixin;

import me.ayunami2000.fabricnotebot.NotebotOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

@Mixin(GameMenuScreen.class)
public abstract class MixinGameMenuScreen extends Screen {
    private MixinGameMenuScreen(Text text_1) {
        super(text_1);
    }
    @Inject(at = @At("TAIL"), method = "initWidgets()V")
    private void onInitWidgets(CallbackInfo ci) {
        addDrawableChild(new ButtonWidget(width / 2 - 102, height / 4 + 144 + -16, 204, 20, Text.of("FabricNotebot Options"), b -> client.setScreen(new NotebotOptionsScreen(this))));
    }
}