package me.ayunami2000.fabricnotebot;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class VariableUtil {
    public static Random rand=new Random();
    private static Pattern patternRand=Pattern.compile("%rand%");
    private static Pattern patternRandBoth=Pattern.compile("%randboth%");
    private static Pattern patternRandNum=Pattern.compile("%rand(\\d+)%");
    private static Pattern patternRandNumBoth=Pattern.compile("%rand(\\d+)both%");
    private static Pattern patternNearestPlayerPos=Pattern.compile("%pos@p%");
    private static Pattern patternRandomPlayerPos=Pattern.compile("%pos@r%");
    private static Pattern patternNearestPlayerEyePos=Pattern.compile("%eyepos@p%");
    private static Pattern patternRandomPlayerEyePos=Pattern.compile("%eyepos@r%");
    private static Pattern patternNearestPlayerRot=Pattern.compile("%motion@p%");
    private static Pattern patternPlayerPos=Pattern.compile("%pos@@([a-zA-Z0-9_]+)%");
    private static Pattern patternPlayerEyePos=Pattern.compile("%eyepos@@([a-zA-Z0-9_]+)%");
    private static Pattern patternPlayerRot=Pattern.compile("%motion@@([a-zA-Z0-9_]+)%");
    private static Pattern patternSelfPlayerPos=Pattern.compile("%pos@s%");
    private static Pattern patternSelfPlayerEyePos=Pattern.compile("%eyepos@s%");
    private static Pattern patternSelfPlayerRot=Pattern.compile("%motion@s%");

    public static String setVariables(String cmd){
        String finalCmd=cmd;
        finalCmd=patternRand.matcher(finalCmd).replaceAll(mr->""+rand.nextFloat(1));
        finalCmd=patternRandBoth.matcher(finalCmd).replaceAll(mr->""+rand.nextFloat(-1,1));
        finalCmd=patternRandNum.matcher(finalCmd).replaceAll(mr->""+rand.nextInt(Integer.parseInt(mr.group(1))+1));
        finalCmd=patternRandNumBoth.matcher(finalCmd).replaceAll(mr->""+rand.nextInt(-Integer.parseInt(mr.group(1)),Integer.parseInt(mr.group(1))+1));
        finalCmd=patternNearestPlayerPos.matcher(finalCmd).replaceAll(mr->{
            List<AbstractClientPlayerEntity> pls = Main.mc.world.getPlayers();
            pls.sort(Comparator.comparing(o -> o.distanceTo(Main.mc.player)));
            Vec3d p=pls.stream().filter(pl -> pl!=Main.mc.player).findFirst().orElse(Main.mc.player).getPos();
            return "["+p.x+","+p.y+","+p.z+"]";
        });
        finalCmd=patternNearestPlayerEyePos.matcher(finalCmd).replaceAll(mr->{
            List<AbstractClientPlayerEntity> pls = Main.mc.world.getPlayers();
            pls.sort(Comparator.comparing(o -> o.distanceTo(Main.mc.player)));
            Vec3d p=pls.stream().filter(pl -> pl!=Main.mc.player).findFirst().orElse(Main.mc.player).getEyePos();
            return "["+p.x+","+p.y+","+p.z+"]";
        });
        finalCmd=patternNearestPlayerRot.matcher(finalCmd).replaceAll(mr->{
            List<AbstractClientPlayerEntity> pls = Main.mc.world.getPlayers();
            pls.sort(Comparator.comparing(o -> o.distanceTo(Main.mc.player)));
            Vec3d p=pls.stream().filter(pl -> pl!=Main.mc.player).findFirst().orElse(Main.mc.player).getRotationVector();
            return "["+p.x+","+p.y+","+p.z+"]";
        });
        finalCmd=patternRandomPlayerPos.matcher(finalCmd).replaceAll(mr->{
            List<AbstractClientPlayerEntity> pls = Main.mc.world.getPlayers();
            Vec3d p=pls.get(rand.nextInt(pls.size())).getPos();
            return "["+p.x+","+p.y+","+p.z+"]";
        });
        finalCmd=patternRandomPlayerEyePos.matcher(finalCmd).replaceAll(mr->{
            List<AbstractClientPlayerEntity> pls = Main.mc.world.getPlayers();
            Vec3d p=pls.get(rand.nextInt(pls.size())).getEyePos();
            return "["+p.x+","+p.y+","+p.z+"]";
        });
        finalCmd=patternPlayerPos.matcher(finalCmd).replaceAll(mr->{
            List<AbstractClientPlayerEntity> pls = Main.mc.world.getPlayers();
            Vec3d p=pls.stream().filter(pl -> pl.getName().getString().equalsIgnoreCase(mr.group(1))).findFirst().orElse(Main.mc.player).getPos();
            return "["+p.x+","+p.y+","+p.z+"]";
        });
        finalCmd=patternPlayerEyePos.matcher(finalCmd).replaceAll(mr->{
            List<AbstractClientPlayerEntity> pls = Main.mc.world.getPlayers();
            Vec3d p=pls.stream().filter(pl -> pl.getName().getString().equalsIgnoreCase(mr.group(1))).findFirst().orElse(Main.mc.player).getEyePos();
            return "["+p.x+","+p.y+","+p.z+"]";
        });
        finalCmd=patternPlayerRot.matcher(finalCmd).replaceAll(mr->{
            List<AbstractClientPlayerEntity> pls = Main.mc.world.getPlayers();
            Vec3d p=pls.stream().filter(pl -> pl.getName().getString().equalsIgnoreCase(mr.group(1))).findFirst().orElse(Main.mc.player).getRotationVector();
            return "["+p.x+","+p.y+","+p.z+"]";
        });
        finalCmd=patternSelfPlayerPos.matcher(finalCmd).replaceAll(mr->{
            Vec3d p=Main.mc.player.getPos();
            return "["+p.x+","+p.y+","+p.z+"]";
        });
        finalCmd=patternSelfPlayerEyePos.matcher(finalCmd).replaceAll(mr->{
            Vec3d p=Main.mc.player.getEyePos();
            return "["+p.x+","+p.y+","+p.z+"]";
        });
        finalCmd=patternSelfPlayerRot.matcher(finalCmd).replaceAll(mr->{
            Vec3d p=Main.mc.player.getRotationVector();
            return "["+p.x+","+p.y+","+p.z+"]";
        });
        return finalCmd;
    }
}
