package me.ayunami2000.fabricnotebot;

import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

import static me.ayunami2000.fabricnotebot.Main.mc;

public class BasicHacks {
    public static Boolean basicHacksEnabled=false;//no matter what this is the try catch will error and set it back to false
    private static Vec3d addVelocity = Vec3d.ZERO;
    public static void tick(){
        try {
            if (basicHacksEnabled) {
                //mc.player.pushSpeedReduction = 1.0F;
                //if(mc.player.fallDistance>2f&&!mc.player.isFallFlying())mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket(true));
                if ((mc.player.getStatusEffect(StatusEffects.SLOWNESS) != null || mc.player.getStatusEffect(StatusEffects.BLINDNESS) != null)) {
                    if (mc.options.forwardKey.isPressed()
                            && mc.player.getVelocity().x > -0.15 && mc.player.getVelocity().x < 0.15
                            && mc.player.getVelocity().z > -0.15 && mc.player.getVelocity().z < 0.15) {
                        mc.player.setVelocity(mc.player.getVelocity().add(addVelocity));
                        addVelocity = addVelocity.add(new Vec3d(0, 0, 0.05).rotateY(-(float)Math.toRadians(mc.player.getYaw())));
                    } else addVelocity = addVelocity.multiply(0.75, 0.75, 0.75);
                }
                if (Main.doesBoxTouchBlock(mc.player.getBoundingBox(), Blocks.SOUL_SAND)) {
                    Vec3d m = new Vec3d(0, 0, 0.125).rotateY(-(float) Math.toRadians(mc.player.getYaw()));
                    if (!mc.player.getAbilities().flying && mc.options.forwardKey.isPressed()) {
                        mc.player.setVelocity(mc.player.getVelocity().add(m));
                    }
                }
                if (Main.doesBoxTouchBlock(mc.player.getBoundingBox().offset(0,-0.02,0), Blocks.SLIME_BLOCK)) {
                    Vec3d m1 = new Vec3d(0, 0, 0.1).rotateY(-(float) Math.toRadians(mc.player.getYaw()));
                    if (!mc.player.getAbilities().flying && mc.options.forwardKey.isPressed()) {
                        mc.player.setVelocity(mc.player.getVelocity().add(m1));
                    }
                }
                if (Main.doesBoxTouchBlock(mc.player.getBoundingBox(), Blocks.COBWEB)) {
                    Vec3d m2 = new Vec3d(0, -1, 0.9).rotateY(-(float) Math.toRadians(mc.player.getYaw()));
                    if (!mc.player.getAbilities().flying && mc.options.forwardKey.isPressed()) {
                        mc.player.setVelocity(mc.player.getVelocity().add(m2));
                    }
                }
            }
        }catch(Exception e){
            basicHacksEnabled=false;
        }
    }
}
