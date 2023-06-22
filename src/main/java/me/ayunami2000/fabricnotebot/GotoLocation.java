package me.ayunami2000.fabricnotebot;

import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static me.ayunami2000.fabricnotebot.Main.inGameLog;
import static me.ayunami2000.fabricnotebot.Main.mc;

public class GotoLocation {
    private static Boolean mustLookAtFirst=false;
    public static Boolean isGoingToLocation=false;
    public static String lastInput="";
    private static Vec3d finalLoc=Vec3d.ZERO;
    private static Double moveSpeed=0.5;
    public static void parseLocation(){
        parseLocation(lastInput);
    }
    public static void parseLocation(String s){
        s=Main.ultraTrim(s);
        if(!s.replaceFirst("^((~[-\\d\\.]*|[-\\d\\.]+) ){2}(~[-\\d\\.]*|[-\\d\\.]+)$","").isEmpty()){
            inGameLog("Error: Unable to parse coordinates!");
            return;
        }
        String[] stringXYZ=s.split(" ");
        Double[] xyz=new Double[3];
        Double[] playerPos=new Double[]{mc.player.getX(),mc.player.getY(),mc.player.getZ()};
        for(int i=0;i<3;i++){
            String theNumber=stringXYZ[i];
            Boolean isLocal=theNumber.startsWith("~");
            if(isLocal)theNumber=theNumber.substring(1);
            if(theNumber.isEmpty())theNumber="0";
            try{
                if(theNumber.indexOf(".")!=theNumber.lastIndexOf("."))throw new Exception();
                xyz[i]=Double.parseDouble(theNumber)+(isLocal?playerPos[i]:0);
            }catch(Exception e){
                e.printStackTrace();
                inGameLog("Error: Unable to parse coordinates!");
                return;
            }
        }
        gotoLocation(new Vec3d(xyz[0],xyz[1],xyz[2]));
    }
    public static void gotoLocation(Vec3d loc){
        if(Main.areAnyHacksEnabled()){
            if (isGoingToLocation) {
                isGoingToLocation = false;
                inGameLog("Goto location is now disabled.");
            } else {
                inGameLog("Error: You must disable other hacks before using GotoLocation!");
            }
            return;
        }
        if(mc.player.hasVehicle()||mc.player.isRiding()){
            inGameLog("Error: You can't use GotoLocation while riding something!");
            return;
        }
        //until vertical movement is implemented
        loc=new Vec3d(loc.x,mc.player.getY(),loc.z);
        if(loc.isInRange(mc.player.getPos(),1)){
            inGameLog("Error: You are already at this location!");
            return;
        }
        mc.player.setVelocity(0,0,0);
        mc.player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES,loc);
        finalLoc=loc;
        mustLookAtFirst=true;
        isGoingToLocation=true;
    }
    public static void gotoLocation(BlockPos blockLoc){
        gotoLocation(new Vec3d(blockLoc.getX(),blockLoc.getY(),blockLoc.getZ()));
    }
    public static void tick(){
        if(isGoingToLocation){
            if(mustLookAtFirst){
                mustLookAtFirst=false;
                mc.player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES,finalLoc);
                return;
            }
            mc.player.setVelocity(0,0,0);
            mc.player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES,finalLoc);
            Double distanceToLoc=finalLoc.squaredDistanceTo(mc.player.getPos());
            BlockPos blockPos=new BlockPos(finalLoc);
            //ischunkloaded just returns true :/
            if(distanceToLoc>256||!mc.world.isChunkLoaded(blockPos.getX()>>4,blockPos.getZ()>>4)){
                inGameLog("Error: That location is too far away!");
                isGoingToLocation=false;
                mc.player.setVelocity(0,0,0);
                return;
            }
            HitResult rayTraceBlock=mc.getCameraEntity().raycast(Math.min(distanceToLoc,16),mc.getTickDelta(),true);
            BlockState theBlockState=mc.world.getBlockState(new BlockPos(rayTraceBlock.getPos()));
            if(theBlockState.getMaterial().isSolid()){
                inGameLog("Error: Unable to walk in a straight line to the destination!");
                isGoingToLocation=false;
                mc.player.setVelocity(0,0,0);
                return;
            }
            Vec3d currPos=mc.player.getPos();
            Vec3d posAngle=finalLoc.subtract(mc.player.getPos());
            posAngle=new Vec3d(posAngle.x>posAngle.z?1:(posAngle.x/posAngle.z),0,posAngle.x>posAngle.z?(posAngle.z/posAngle.x):1);
            //set speed in blocks to move
            posAngle=posAngle.multiply(moveSpeed);
            mc.player.setVelocity(0,0,moveSpeed);
            Vec3d newLoc=currPos.add(posAngle);
            mc.player.setPos(newLoc.x,newLoc.y,newLoc.z);
            if(finalLoc.isInRange(newLoc,1)){
                isGoingToLocation=false;
                //mc.player.move(MovementType.PISTON,);
                mc.player.setVelocity(0,0,0);
                mc.player.setPos(finalLoc.x,finalLoc.y,finalLoc.z);
                inGameLog("Successfully traveled to location!");
            }
        }
    }
}
