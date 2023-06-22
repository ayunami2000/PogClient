package me.ayunami2000.fabricnotebot.mixin;

import me.ayunami2000.fabricnotebot.access.AccessMinecraftClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.HotbarStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient implements AccessMinecraftClient {
    @Mutable
    @Shadow @Final public HotbarStorage creativeHotbarStorage;

    @Override
    public void setCreativeHotbarStorage(HotbarStorage hotbarStorage) {
        this.creativeHotbarStorage = hotbarStorage;
    }
}
