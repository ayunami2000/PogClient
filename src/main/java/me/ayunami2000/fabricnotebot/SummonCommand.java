package me.ayunami2000.fabricnotebot;

import net.minecraft.block.FluidBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.ArrayList;
import java.util.Arrays;

import static me.ayunami2000.fabricnotebot.Main.mc;

public class SummonCommand {
    public static String theSummonCommand="";
    public static void doSummon(Item spawnEggItem){
        //check if in gmc
        if(!mc.player.getAbilities().creativeMode){
            Main.inGameLog("Error: The summon command requires creative mode to work!");
            return;
        }
        //validate command & other stuff
        ArrayList<String> tmpCmd=new ArrayList(Arrays.asList(Main.ultraTrim(VariableUtil.setVariables(theSummonCommand)).replaceFirst("^\\/?(minecraft:)?summon ","").split("(( (~[-\\d\\.]*|[-\\d\\.]+)){3})| ",2)));
        String theEntity="";
        String theNbt="";
        if(tmpCmd.size()<2){
            String realEntity=Main.ultraTrim(tmpCmd.get(0));
            if(!realEntity.replaceFirst("^(minecraft:)?[A-Za-z_]+$","").isEmpty()) {
                Main.inGameLog("Error: The summon command appears to be invalid!");
                return;
            }
            tmpCmd=new ArrayList<>(Arrays.asList(realEntity,"{}"));
        }
        theEntity=tmpCmd.remove(0);
        theNbt=Main.ultraTrim(tmpCmd.get(0));
        if(theNbt.isEmpty())theNbt="{}";
        ItemStack oldItem=ItemStack.EMPTY;
        Integer theSlot=mc.player.getInventory().getEmptySlot();
        if(theSlot<0||theSlot>8){
            theSlot=8;
            oldItem=mc.player.getInventory().getStack(theSlot);
        }
        Integer prevSlot=mc.player.getInventory().selectedSlot;
        mc.player.getInventory().selectedSlot=theSlot;
        //allow spawning in liquids
        HitResult rayTraceBlock=mc.getCameraEntity().raycast(mc.interactionManager.getReachDistance(),mc.getTickDelta(),true);
        if(rayTraceBlock.getType()==HitResult.Type.MISS){
            //Main.inGameLog("Error: You must be looking at a nearby block to summon the entity at!");
            //return;
        }
        //generate spawn egg
        ItemStack theSpawnEgg=new ItemStack(spawnEggItem);

        NbtCompound eggNbt=new NbtCompound();
        NbtCompound entityTag;
        try {
            entityTag=StringNbtReader.parse(theNbt);
        }catch(Exception e){
            Main.inGameLog("Error: The summon command appears to be invalid!");
            return;
        }
        entityTag.putString("id",theEntity);
        eggNbt.put("EntityTag",entityTag);
        theSpawnEgg.setNbt(eggNbt);
        Main.loadItem(theSpawnEgg,theSlot);
        //place it immediately
        theSpawnEgg.use(mc.world,mc.player,Hand.MAIN_HAND);
        Vec3d rayTracePos;
        if(rayTraceBlock.getType()==HitResult.Type.MISS){
            Vec3d vec3d = mc.player.getCameraPosVec(mc.getTickDelta());
            Vec3d vec3d3 = new Vec3d(vec3d.x, 0, vec3d.z);
            HitResult rayTraceBlockTwo=mc.world.raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, mc.player));
            if(rayTraceBlockTwo.getType()== HitResult.Type.MISS){
                Main.inGameLog("Error: You must be looking at a nearby block or be above a block to summon the entity at!");
                return;
            }else{
                rayTracePos=rayTraceBlockTwo.getPos();
            }
        }else{
            rayTracePos=rayTraceBlock.getPos();
        }
        if(!(mc.world.getBlockState(new BlockPos(rayTracePos.x,rayTracePos.y,rayTracePos.z)).getBlock() instanceof FluidBlock)){
            mc.interactionManager.interactBlock(mc.player,Hand.MAIN_HAND,rayTraceBlock.getType()==HitResult.Type.MISS?new BlockHitResult(rayTracePos, Direction.UP,new BlockPos(rayTracePos.x,rayTracePos.y,rayTracePos.z),false):(BlockHitResult)rayTraceBlock);
        }else{
            mc.interactionManager.interactItem(mc.player,Hand.MAIN_HAND);
            mc.interactionManager.stopUsingItem(mc.player);
        }
        //delete spawn egg
        Main.loadItem(oldItem,theSlot);
        mc.player.getInventory().selectedSlot=prevSlot;
    }
    public static void onChange(String text){
        theSummonCommand=text;
    }
}
