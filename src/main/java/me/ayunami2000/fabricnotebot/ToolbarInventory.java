package me.ayunami2000.fabricnotebot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static me.ayunami2000.fabricnotebot.Main.mc;

public class ToolbarInventory implements Inventory {
    @Override
    public int size() {
        return 54;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        int realSlot = slot - 9;
        if(realSlot < 0 || realSlot > 26){
            if(realSlot == -1 || realSlot == 35) return new ItemStack(Items.STONE_BUTTON);
            return new ItemStack(Items.BLACK_STAINED_GLASS_PANE);
        }
        int theRow = (int) Math.floor(realSlot/9.0) + ToolbarPeek.scrollNum;
        ItemStack theItemStack = ToolbarPeek.viewingContainer?ToolbarPeek.containerInventory.get(realSlot):mc.getCreativeHotbarStorage().getSavedHotbar(theRow).get(realSlot % 9);
        return theItemStack;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {

    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {

    }
}
