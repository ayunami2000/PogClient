package me.ayunami2000.fabricnotebot;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class SignNbt {
    public static boolean isDoing=false;
    private static int stage=0;
    private static BlockPos blockPos = null;

    public static void playerList() {
        if(isDoing){
            isDoing=false;
            Main.inGameLog("TruePlayerList/GetInventory/GetEnderchest disabled.");
            stage=-1;
        }else{
            if (Main.areAnyHacksEnabled()) {
                Main.inGameLog("Error: Already doing something!");
            }else{
                if (Main.mc.player.isCreative()) {
                    blockPos = Main.mc.player.getBlockPos();
                    if (Main.mc.world.getBlockState(blockPos).isAir()) {
                        stage=0;
                        isDoing=true;
                        Main.inGameLog("TruePlayerList enabled.");
                    } else {
                        Main.inGameLog("Error: You must be standing in air!");
                    }
                } else {
                    Main.inGameLog("Error: You must be in creative mode to do this!");
                }
            }
        }
    }
    public static void getInventory() {
        if(isDoing){
            isDoing=false;
            Main.inGameLog("GetInventory/TruePlayerList/GetEnderchest disabled.");
            stage=-1;
        }else{
            if (Main.areAnyHacksEnabled()) {
                Main.inGameLog("Error: Already doing something!");
            }else{
                if (Main.mc.player.isCreative()) {
                    blockPos = Main.mc.player.getBlockPos();
                    if (Main.mc.world.getBlockState(blockPos).isAir()) {
                        stage=4;
                        isDoing=true;
                        Main.inGameLog("GetInventory enabled.");
                    } else {
                        Main.inGameLog("Error: You must be standing in air!");
                    }
                } else {
                    Main.inGameLog("Error: You must be in creative mode to do this!");
                }
            }
        }
    }
    public static void getEnderchest() {
        if(isDoing){
            isDoing=false;
            Main.inGameLog("GetInventory/TruePlayerList/GetEnderchest disabled.");
            stage=-1;
        }else{
            if (Main.areAnyHacksEnabled()) {
                Main.inGameLog("Error: Already doing something!");
            }else{
                if (Main.mc.player.isCreative()) {
                    blockPos = Main.mc.player.getBlockPos();
                    if (Main.mc.world.getBlockState(blockPos).isAir()) {
                        stage=8;
                        isDoing=true;
                        Main.inGameLog("GetEnderchest enabled.");
                    } else {
                        Main.inGameLog("Error: You must be standing in air!");
                    }
                } else {
                    Main.inGameLog("Error: You must be in creative mode to do this!");
                }
            }
        }
    }

    public static void tick(){
        if(isDoing){
            switch(stage){
                case 0:
                    ItemStack signItem = new ItemStack(Items.OAK_SIGN);
                    try {
                        signItem.setNbt(StringNbtReader.parse("{BlockEntityTag:{Text1:'{\"selector\":\"@a[sort=nearest]\"}'}}"));
                    } catch (CommandSyntaxException e) {
                    }
                    Main.loadItem(signItem);
                    stage=1;
                    break;
                case 1:
                    if(Main.mc.player.getMainHandStack().isOf(Items.OAK_SIGN)) {
                        Main.rightClickBlock(blockPos, Direction.DOWN);
                        stage = 2;
                    }else{
                        Main.inGameLog("Waiting for item...");
                    }
                    break;
                case 2:
                    Main.loadItem(ItemStack.EMPTY);
                    stage=3;
                    break;
                case 3:
                    BlockState signBlockState = Main.mc.world.getBlockState(blockPos);
                    if (signBlockState.isOf(Blocks.OAK_SIGN) || signBlockState.isOf(Blocks.OAK_WALL_SIGN)) {
                        Text signText = ((SignBlockEntity) Main.mc.world.getBlockEntity(blockPos)).getTextOnRow(0, false);
                        if(signText.getString().trim()==""){
                            Main.inGameLog("Waiting for sign text...");
                        }else{
                            isDoing=false;
                            Main.inGameLog("Done!");
                            Main.mc.player.sendMessage(signText, false);
                            Main.mc.interactionManager.breakBlock(blockPos);
                        }
                    } else {
                        Main.inGameLog("Waiting for sign...");
                    }
                    break;
                case 4:
                    ItemStack signItemTwo = new ItemStack(Items.OAK_SIGN);
                    try {
                        signItemTwo.setNbt(StringNbtReader.parse("{BlockEntityTag:{Text1:'{\"nbt\":\"Inventory\",\"entity\":\"@p[distance=1..]\"}'}}"));
                    } catch (CommandSyntaxException e) {
                    }
                    Main.loadItem(signItemTwo);
                    stage=5;
                    break;
                case 5:
                    if(Main.mc.player.getMainHandStack().isOf(Items.OAK_SIGN)) {
                        Main.rightClickBlock(blockPos, Direction.DOWN);
                        stage = 6;
                    }else{
                        Main.inGameLog("Waiting for item...");
                    }
                    break;
                case 6:
                    Main.loadItem(ItemStack.EMPTY);
                    stage=7;
                    break;
                case 7:
                    BlockState signBlockStateTwo = Main.mc.world.getBlockState(blockPos);
                    if (signBlockStateTwo.isOf(Blocks.OAK_SIGN) || signBlockStateTwo.isOf(Blocks.OAK_WALL_SIGN)) {
                        Text signText = ((SignBlockEntity) Main.mc.world.getBlockEntity(blockPos)).getTextOnRow(0, false);
                        if(signText.getString().trim()==""){
                            Main.inGameLog("Waiting for sign text...");
                        }else{
                            isDoing=false;
                            Main.inGameLog("Done!");
                            try {
                                ItemStack theBox=new ItemStack(Items.SHULKER_BOX);
                                NbtCompound invNbt=StringNbtReader.parse("{BlockEntityTag:{Items:"+signText.getString()+"}}");
                                theBox.setNbt(invNbt);
                                Main.loadItem(theBox);
                            } catch (CommandSyntaxException e) {}
                            Main.mc.interactionManager.breakBlock(blockPos);
                        }
                    } else {
                        Main.inGameLog("Waiting for sign...");
                    }
                    break;
                case 8:
                    ItemStack signItemThree = new ItemStack(Items.OAK_SIGN);
                    try {
                        signItemThree.setNbt(StringNbtReader.parse("{BlockEntityTag:{Text1:'{\"nbt\":\"EnderItems\",\"entity\":\"@p[distance=1..]\"}'}}"));
                    } catch (CommandSyntaxException e) {
                    }
                    Main.loadItem(signItemThree);
                    stage=5;
                    break;
                default:
                    isDoing=false;
            }
        }
    }
}
