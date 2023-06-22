package me.ayunami2000.fabricnotebot;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.lecturestudio.stylus.StylusAxesData;

public class TabletDraw {
    public static Boolean tabletDrawEnabled=false;
    public static Boolean playerPen=true;
    private static BlockPos originPos=null;
    private static Direction originDir=null;
    private static Vec3d lastPos=Vec3d.ZERO;
    private static Boolean origNoGravity=false;
    private static BlockPos whereToDraw=null;
    private static float[] penDownLoc=new float[]{0,0};
    public static void penEvent(StylusAxesData axesData){
        if(tabletDrawEnabled) {
            try{
                if(playerPen){
                    float vx = 12.8F * (((float) axesData.getX() / (float) Main.tabletDrawWindow.getWidth()) - 0.5F),
                            vy = 7.2F * (((float) axesData.getY() / (float) Main.tabletDrawWindow.getHeight()) - 0.5F);
                    if(axesData.getPressure()>0) {
                        float vz = (float)axesData.getPressure();
                        vx -= penDownLoc[0];
                        vy -= penDownLoc[1];
                        Main.mc.player.getInventory().selectedSlot = (int) Math.min(8, Math.max(0, Math.floor(vz * 9) - 1));
                        //mc.player.inventory.selectedSlot=(int)Math.min(8,Math.floor(10*vz-0.5F));
                        Vec3d ppos = Main.mc.player.getPos();
                        Main.mc.player.setVelocity((lastPos.x - ppos.x) + vx, 0, (lastPos.z - ppos.z) + vy);
                    }else{
                        //comment this out for fling (add button...?)
                        //todo: add fling toggle button
                        Main.mc.player.setVelocity(0, 0, 0);
                        lastPos = Main.mc.player.getPos();
                        penDownLoc=new float[]{vx,vy};
                    }
                }else if(axesData.getPressure()>0){
                    //fuck originPos
                    originPos=Main.mc.player.getBlockPos().add(0,1,0);
                    if (Main.mc.player.getInventory().selectedSlot==8||Block.getBlockFromItem(Main.mc.player.getInventory().getMainHandStack().getItem())!=Blocks.AIR) {
                        float reachDist = Main.mc.interactionManager.getReachDistance(),
                                reachDistFull = 2.0F * reachDist + 1.0F;
                        Integer xx = (int) (reachDistFull * ((float) Main.mc.getWindow().getWidth() - (float) axesData.getX()) / (float) Main.tabletDrawWindow.getWidth()),
                                yy = (int) (reachDistFull * ((float) Main.mc.getWindow().getHeight() - (float) axesData.getY()) / (float) Main.tabletDrawWindow.getHeight()),
                                zz = (int) (reachDist * (float) axesData.getPressure());
                        BlockPos blockPos = originPos;
                        switch (originDir) {
                            case NORTH:
                                blockPos = blockPos.add(reachDist - xx, yy - reachDist, zz - reachDist);
                                break;
                            case SOUTH:
                                blockPos = blockPos.add(xx - reachDist, yy - reachDist, reachDist - zz);
                                break;
                            case EAST:
                                blockPos = blockPos.add(reachDist - zz, yy - reachDist, reachDist - xx);
                                break;
                            case WEST:
                                blockPos = blockPos.add(zz - reachDist, yy - reachDist, xx - reachDist);
                                break;
                            default:
                                blockPos = blockPos.add(reachDist - xx, yy - reachDist, zz - reachDist);
                        }
                        whereToDraw=blockPos;
                    }
                }
            }catch(Exception e){
                revertStuff();
            }
        }
    }
    public static void toggleTabletDraw(){
        if(Main.areAnyHacksEnabled()){
            if(tabletDrawEnabled){
                revertStuff();
            }else{
                Main.inGameLog("Error: You must disable other hacks before using TabletDraw!");
            }
            return;
        }
        //if(canvas==null)initStuff();
        originPos=Main.mc.player.getBlockPos().add(0,1,0);
        originDir=Main.mc.player.getHorizontalFacing();
        origNoGravity=Main.mc.player.hasNoGravity();
        Main.mc.player.setNoGravity(true);
        lastPos=Main.mc.player.getPos();
        whereToDraw=null;
        penDownLoc=new float[]{0,0};
        Main.tabletDrawWindow.setVisible(true);
		Main.tabletDrawWindow.setSize(800, 450);
        tabletDrawEnabled=true;
        Main.inGameLog("TabletDraw is now enabled.");
    }
    public static void tick(){
        if(tabletDrawEnabled){
            try{
                Main.mc.player.setYaw((playerPen?Direction.NORTH:originDir).asRotation());
                Main.mc.player.setPitch(playerPen?90:0);
                Main.mc.mouse.unlockCursor();
                if(playerPen) {
                    Main.mc.player.setNoGravity(true);
                    //todo: make this not only check air if ok to place
                    if (Main.mc.world.getBlockState(Main.mc.player.getBlockPos().down()).isAir())
                        Main.rightClickBlock(Main.mc.player.getBlockPos().add(0, -1, 0), Direction.UP);
                }else if(whereToDraw!=null) {
                    if (Main.mc.player.getInventory().selectedSlot == 8) {
                        if (!Main.mc.player.getInventory().getStack(8).isEmpty()) {
                            Main.loadItem(ItemStack.EMPTY, 8);
                            Main.inGameLog("The last slot is designated for erasing!");
                        }
                        Main.mc.interactionManager.attackBlock(whereToDraw, Direction.UP);
                    } else {
                        Main.rightClickBlock(whereToDraw, Direction.UP);
                    }
                    whereToDraw=null;
                }
            }catch(Exception e){
                revertStuff();
            }
        }
    }
    private static void revertStuff(){
        tabletDrawEnabled=false;
        Main.inGameLog("TabletDraw is now disabled.");
        originPos=null;
        originDir=null;
        lastPos=Vec3d.ZERO;
        whereToDraw=null;
        penDownLoc=new float[]{0,0};
        // Main.tabletDrawWindow.dispose();
		Main.tabletDrawWindow.setVisible(false);
        try {
            Main.mc.player.setNoGravity(origNoGravity);
        }catch(Exception e){}
    }
    public static void initStuff(){
        /*
        if(canvas==null) {
            canvas = new TabletCanvas();
            jWinPointerReader = new JWinPointerReader(Main.clientTitle);//Main.getWindowTitle()
            jWinPointerReader.addPointerEventListener(canvas);
        }
        */
    }
}
