package me.ayunami2000.fabricnotebot;

import me.ayunami2000.fabricnotebot.access.AccessMinecraftClient;
import me.ayunami2000.fabricnotebot.mixin.MixinMinecraftClient;
import net.minecraft.client.option.HotbarStorage;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;

import java.util.List;

import static me.ayunami2000.fabricnotebot.Main.mc;

public class ToolbarPeek {
    public static int scrollNum = 0;
    public static boolean viewingContainer = false;
    public static List<ItemStack> containerInventory = null;
    public static Inventory hotbarInventory = new ToolbarInventory();
    public static void openToolbarMenu(){
        GenericContainerScreenHandler toolbarScreenHandler = GenericContainerScreenHandler.createGeneric9x6(2147483647,mc.player.getInventory(),hotbarInventory);
        ToolbarContainerScreen toolbarScreen = new ToolbarContainerScreen(toolbarScreenHandler,mc.player.getInventory(), Text.of("amogus"));
        mc.setScreen(toolbarScreen);
    }
    public static void refreshHotbar(){
        ((AccessMinecraftClient)mc).setCreativeHotbarStorage(new HotbarStorage(mc.runDirectory, mc.getDataFixer()));
        Main.inGameLog("Refreshed your creative hotbar!");
    }
}
