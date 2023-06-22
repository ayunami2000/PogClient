package me.ayunami2000.fabricnotebot;

import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import static me.ayunami2000.fabricnotebot.Main.mc;

public class Amogus {
    static String strnbt="{display:{Name:'{\"text\":\"sex\"}'},BlockEntityTag:{Items:[{Slot:0b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:1b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:2b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:3b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:4b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:5b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:6b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:7b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:8b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:9b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:10b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:11b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:12b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:13b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:14b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:15b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:16b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:17b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:18b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:19b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:20b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:21b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:22b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:23b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:24b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:25b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}},{Slot:26b,id:\"$\",Count:1b,tag:{display:{Name:'{\"text\":\"sex\"}'}}}]}}";

    public static boolean isenable=false;
    static BlockPos plyrbpos;

    public static void becomeSus(){
        ItemStack susItemHolder=new ItemStack(Items.BARREL);
        String newnbt=strnbt;
        for(int i=0;i<27;i++){
            newnbt=newnbt.replaceFirst("\\$",Registry.ITEM.get((int)Math.floor(Math.random()*Registry.ITEM.stream().count())).toString());
        }
        try{
            susItemHolder.setNbt(StringNbtReader.parse(newnbt));
        }catch(Exception e){}
        Main.loadItem(susItemHolder);
    }

    public static void bruhMoment(int mode){
        if(isenable){
            isenable=false;
            Main.inGameLog("disable amogus");
            return;
        }
        plyrbpos=mc.player.getBlockPos();
        Main.loadItem(new ItemStack(mode==0?Items.BLACK_BED:Items.OAK_STAIRS));
        Main.rightClickBlock(plyrbpos.down(1), Direction.UP);
        Main.loadItem(new ItemStack(Items.AIR));
        Main.rightClickBlock(plyrbpos.down(1), Direction.UP);
        //mc.interactionManager.attackBlock(plyrbpos.down(1), Direction.UP);
        if(mode==1){
            isenable=true;
            Main.inGameLog("enable amogus");
        }
    }

    public static void tick(){
        if(isenable){
            try{
                mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player,ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
                if(!(mc.world.getBlockState(plyrbpos.down(1)).getBlock() instanceof StairsBlock)){
                    Main.loadItem(new ItemStack(Items.OAK_STAIRS));
                    Main.rightClickBlock(plyrbpos.down(1), Direction.UP);
                    Main.loadItem(new ItemStack(Items.AIR));
                }
                Main.rightClickBlock(plyrbpos.down(1), Direction.UP);
                mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player,ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
            }catch(Exception e){
                isenable=false;
            }
        }
    }
}
