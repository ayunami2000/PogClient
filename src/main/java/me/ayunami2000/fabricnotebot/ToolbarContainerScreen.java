package me.ayunami2000.fabricnotebot;

import net.minecraft.block.*;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class ToolbarContainerScreen extends GenericContainerScreen {
    public ToolbarContainerScreen(GenericContainerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    @Nullable
    private Slot getSlotAt(double x, double y) {
        for(int i = 0; i < this.handler.slots.size(); ++i) {
            Slot slot = (Slot)this.handler.slots.get(i);
            if (this.isPointOverSlot(slot, x, y) && slot.isEnabled()) {
                return slot;
            }
        }

        return null;
    }
    private boolean isPointOverSlot(Slot slot, double pointX, double pointY) {
        return this.isPointWithinBounds(slot.x, slot.y, 16, 16, pointX, pointY);
    }
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Slot itemSlot = this.getSlotAt(mouseX, mouseY);
        if(itemSlot == null)return true;
        if(itemSlot.getIndex() == 8) ToolbarPeek.scrollNum--;
        if(itemSlot.getIndex() == 44) ToolbarPeek.scrollNum++;
        ToolbarPeek.scrollNum = Math.max(0,Math.min(6,ToolbarPeek.scrollNum));
        if(itemSlot.getIndex() >= 9 && itemSlot.getIndex() <= 35){
            ItemStack theItem = itemSlot.getStack();
            if(ToolbarPeek.viewingContainer){
                client.player.getInventory().insertStack(theItem.copy());
                if(client.player.getAbilities().creativeMode){
                    ToolbarPeek.scrollNum = 0;
                    ToolbarPeek.viewingContainer = false;
                    Main.updateInventory();
                }
                return true;
            }
            try {
                Block block = ((BlockItem) theItem.getItem()).getBlock();
                if ((block instanceof ShulkerBoxBlock) || (block instanceof ChestBlock) || (block instanceof BarrelBlock) || (block instanceof DispenserBlock) || (block instanceof HopperBlock) || (block instanceof AbstractFurnaceBlock)) {
                    ToolbarPeek.scrollNum = 0;
                    ToolbarPeek.containerInventory = ItemContentUtils.getItemsInContainer(theItem);
                    ToolbarPeek.viewingContainer = true;
                }else{
                    client.player.getInventory().insertStack(theItem.copy());
                    if(client.player.getAbilities().creativeMode){
                        ToolbarPeek.scrollNum = 0;
                        ToolbarPeek.viewingContainer = false;
                        Main.updateInventory();
                    }
                }
            }catch(Exception e){return true;}
        }
        return true;
    }
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return true;
    }
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return true;
    }
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        if (this.client.options.inventoryKey.matchesKey(keyCode, scanCode) || keyCode == 256) {
            ToolbarPeek.scrollNum=0;
            ToolbarPeek.viewingContainer = false;
            this.close();
            return true;
        }
        return true;
    }
    protected boolean handleHotbarKeyPressed(int keyCode, int scanCode) {
        return true;
    }
}
