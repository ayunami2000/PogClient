package me.ayunami2000.fabricnotebot;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import static me.ayunami2000.fabricnotebot.Main.mc;

public class ImageBuilder {
    public static File realDefaultImageFile=Main.modpath.resolve("ImageBuilderImage.png").toFile();
    public static File defaultImageFile=realDefaultImageFile;
    public static Boolean isBuildingImage=false;
    public static Integer blockMode=0;
    public static BufferedImage buildImage;
    private static Integer imgX=0,imgY=0;
    private static Block[] concreteBlocks=new Block[]{
            Blocks.WHITE_CONCRETE,
            Blocks.ORANGE_CONCRETE,
            Blocks.MAGENTA_CONCRETE,
            Blocks.LIGHT_BLUE_CONCRETE,
            Blocks.YELLOW_CONCRETE,
            Blocks.LIME_CONCRETE,
            Blocks.PINK_CONCRETE,
            Blocks.GRAY_CONCRETE,
            Blocks.LIGHT_GRAY_CONCRETE,
            Blocks.CYAN_CONCRETE,
            Blocks.PURPLE_CONCRETE,
            Blocks.BLUE_CONCRETE,
            Blocks.BROWN_CONCRETE,
            Blocks.GREEN_CONCRETE,
            Blocks.RED_CONCRETE,
            Blocks.BLACK_CONCRETE
    };
    private static Block[] bannerBlocks=new Block[]{
            Blocks.WHITE_WALL_BANNER,
            Blocks.ORANGE_WALL_BANNER,
            Blocks.MAGENTA_WALL_BANNER,
            Blocks.LIGHT_BLUE_WALL_BANNER,
            Blocks.YELLOW_WALL_BANNER,
            Blocks.LIME_WALL_BANNER,
            Blocks.PINK_WALL_BANNER,
            Blocks.GRAY_WALL_BANNER,
            Blocks.LIGHT_GRAY_WALL_BANNER,
            Blocks.CYAN_WALL_BANNER,
            Blocks.PURPLE_WALL_BANNER,
            Blocks.BLUE_WALL_BANNER,
            Blocks.BROWN_WALL_BANNER,
            Blocks.GREEN_WALL_BANNER,
            Blocks.RED_WALL_BANNER,
            Blocks.BLACK_WALL_BANNER
    };
    private static Block[] stainedGlassBlocks=new Block[]{
            Blocks.WHITE_STAINED_GLASS,
            Blocks.ORANGE_STAINED_GLASS,
            Blocks.MAGENTA_STAINED_GLASS,
            Blocks.LIGHT_BLUE_STAINED_GLASS,
            Blocks.YELLOW_STAINED_GLASS,
            Blocks.LIME_STAINED_GLASS,
            Blocks.PINK_STAINED_GLASS,
            Blocks.GRAY_STAINED_GLASS,
            Blocks.LIGHT_GRAY_STAINED_GLASS,
            Blocks.CYAN_STAINED_GLASS,
            Blocks.PURPLE_STAINED_GLASS,
            Blocks.BLUE_STAINED_GLASS,
            Blocks.BROWN_STAINED_GLASS,
            Blocks.GREEN_STAINED_GLASS,
            Blocks.RED_STAINED_GLASS,
            Blocks.BLACK_STAINED_GLASS
    };
    private static Integer[][] rgbColors=new Integer[][]{
            new Integer[]{249, 255, 255},//white
            new Integer[]{249, 128, 29},//orange
            new Integer[]{198, 79, 189},//magenta
            new Integer[]{58, 179, 218},//light blue
            new Integer[]{255, 216, 61},//yellow
            new Integer[]{128, 199, 31},//lime
            new Integer[]{243, 140, 170},//pink
            new Integer[]{71, 79, 82},//gray
            new Integer[]{156, 157, 151},//light gray
            new Integer[]{22, 156, 157},//cyan
            new Integer[]{137, 50, 183},//purple
            new Integer[]{60, 68, 169},//blue
            new Integer[]{130, 84, 50},//brown
            new Integer[]{93, 124, 21},//green
            new Integer[]{176, 46, 38},//red
            new Integer[]{29, 28, 33}//black
    };
    private static Boolean imgBuildPart1=false,imgBuildPart2=false;
    private static String defaultImage="https://gnome.now.sh/gnome.png";
    private static BlockPos startingPos;
    private static Direction startingDir;
    private static Integer buildSlotLock=8;
    public static Integer maxSize=50;
    public static Integer[] imgSizes = new Integer[]{10,30,40,50,100,150,250,350,500,1000,1500,2000,2500,4000,5000};
    public static Integer currImgSize=3;
    public static void buildAnImage() {
        if(Main.areAnyHacksEnabled()){
            if(isBuildingImage){
                isBuildingImage=false;
                Main.inGameLog("ImageBuilder is now disabled.");
            }else{
                Main.inGameLog("Error: You must disable other hacks before using ImageBuilder!");
            }
            return;
        }
        if(imgBuildPart2)return;//wait for imagebuilder to reset
        if(updateImage()==null){
            Main.inGameLog("Error: Couldn't find that image!");
            return;
        }
        if (!mc.player.getAbilities().creativeMode){
            Main.inGameLog("Error: You must be in creative mode to use ImageBuilder!");
            return;
        }
        Main.clearHotbar();
        if(blockMode==3){
            String restxt="['[";
            for(int iy=0;iy<buildImage.getHeight();iy++){
                for(int ix=0;ix<buildImage.getWidth();ix++){
                    int ip=buildImage.getRGB(ix,iy),
                            iimgA=(ip>>24) & 0xff,
                            iimgR=(ip>>16) & 0xff,
                            iimgG=(ip>>8) & 0xff,
                            iimgB=ip & 0xff;
                    restxt+="{\"text\":\"⬛\",\"italic\":\"false\",\"color\":\""+String.format("#%02x%02x%02x", iimgR, iimgG, iimgB)+"\"},";
                }
                restxt=restxt.substring(0, restxt.length() - 1)+"]','[";
            }
            restxt=restxt.substring(0, restxt.length() - 3)+"]";
            try {
                ItemStack itemStack = new ItemStack(Items.PAPER);
                itemStack.setNbt(StringNbtReader.parse("{display:{Name:'{\"text\":\"Made by ayunami2000\",\"color\":\"green\",\"bold\":\"true\",\"italic\":\"false\"}',Lore:" + restxt + "}}"));
                Main.loadItem(itemStack);
                Main.inGameLog("Image item generated!");
            }catch(CommandSyntaxException e){
                Main.inGameLog("Error: Something went wrong!");
            }
            return;
        }
        imgX=0;
        imgY=0;
        imgBuildPart2=false;
        imgBuildPart1=true;
        startingPos=mc.player.getBlockPos();
        startingDir=mc.player.getHorizontalFacing();
        int emptySlot=mc.player.getInventory().getEmptySlot();
        buildSlotLock=emptySlot==-1?8:emptySlot;
        Main.inGameLog("ImageBuilder is now enabled.");
        isBuildingImage=true;
    }
    public static void tick(){
        try {
            if (isBuildingImage) {
                if(imgBuildPart1) {
                    imgBuildPart1=false;
                    imgBuildPart2=true;
                }else if(imgBuildPart2) {
                    BlockPos blockPos=startingPos;
                    if(blockMode==5){
                        if(imgX==3) {
                            imgX = 0;
                            imgY++;
                        }else if(imgX>0&&imgX<3){
                            if(mc.player.isSneaking()){
                                imgX++;
                            }else{
                                imgX=0;
                                imgY++;
                            }
                        }else{
                            Vec3d currPos = mc.player.getPos().add(-0.5, 0, -0.5);
                            Vec3d blockPosVec3d = Vec3d.of(blockPos);
                            double rangeBlockDist = currPos.add(0, mc.player.getEyeHeight(mc.player.getPose()), 0).distanceTo(blockPosVec3d);
                            double blockDist = currPos.add(0, 0.5, 0).distanceTo(blockPosVec3d);
                            Main.inGameLog(String.valueOf(blockDist));
                            if(rangeBlockDist <= mc.interactionManager.getReachDistance() + 1.0F && blockDist >= 1.25/*1.1313709*/) {
                                ItemStack theItem = new ItemStack(Items.ARMOR_STAND);
                                String restxt = "";
                                for (int ax = 0; ax < buildImage.getWidth(); ax++) {
                                    int ap = buildImage.getRGB(ax, buildImage.getHeight() - 1 - imgY),
                                            imgA = (ap >> 24) & 0xff,
                                            imgR = (ap >> 16) & 0xff,
                                            imgG = (ap >> 8) & 0xff,
                                            imgB = ap & 0xff;
                                    restxt += ("{\"text\":\"⬛\",\"color\":\"" + String.format("#%02x%02x%02x", imgR, imgG, imgB) + "\"},");
                                }
                                restxt = restxt.substring(0, restxt.length() - 1);
                                Vec3d pos = blockPosVec3d.add(Vec3d.of(startingDir.getVector()).multiply(imgY / 4.0));
                                theItem.setNbt(StringNbtReader.parse("{EntityTag:{Pos:[" + pos.x + "," + pos.y + "," + pos.z + "],CustomNameVisible:1b,Marker:1b,Invisible:1b,PersistenceRequired:1b,CustomName:'[" + restxt + "]'}}"));
                                Main.loadItem(theItem);
                                Main.rightClickBlock(blockPos, Direction.UP);
                                if(mc.player.isSneaking()){
                                    imgX++;
                                }else{
                                    imgX=0;
                                    imgY++;
                                }
                            }
                        }
                    }else {
                        switch(startingDir){
                            case NORTH:
                                blockPos=blockMode==4?blockPos.add(0,0,-imgY):blockPos.add(imgX/(blockMode==6?5:1),imgY/(blockMode==6?4:1),-1);
                                break;
                            case SOUTH:
                                blockPos=blockMode==4?blockPos.add(0,0,imgY):blockPos.add(-imgX/(blockMode==6?5:1),imgY/(blockMode==6?4:1),1);
                                break;
                            case EAST:
                                blockPos=blockMode==4?blockPos.add(imgY,0,0):blockPos.add(1,imgY/(blockMode==6?4:1),imgX/(blockMode==6?5:1));
                                break;
                            case WEST:
                                blockPos=blockMode==4?blockPos.add(-imgY,0,0):blockPos.add(-1,imgY/(blockMode==6?4:1),-imgX/(blockMode==6?5:1));
                                break;
                            default:
                                blockPos=blockMode==4?blockPos.add(0,0,-imgY):blockPos.add(imgX/(blockMode==6?5:1),imgY/(blockMode==6?4:1),-1);
                        }
                        Vec3d currPos = mc.player.getPos().add(-0.5, 0, -0.5);
                        int p = buildImage.getRGB(imgX, buildImage.getHeight() - 1 - imgY),
                                imgA = (p >> 24) & 0xff,
                                imgR = (p >> 16) & 0xff,
                                imgG = (p >> 8) & 0xff,
                                imgB = p & 0xff;
                        Block theBlock = rgbaToBlocks(imgR, imgG, imgB, imgA);
                        if (theBlock != Blocks.BARRIER) {
                            Vec3d blockPosVec3d = Vec3d.of(blockPos);
                            double rangeBlockDist = currPos.add(0, mc.player.getEyeHeight(mc.player.getPose()), 0).distanceTo(blockPosVec3d);
                            double blockDist = currPos.add(0, 0.5, 0).distanceTo(blockPosVec3d);
                            Main.inGameLog(String.valueOf(blockDist));
                            //skip invisible areas
                            if (blockMode == 2 && imgY % 2 == 0 && imgY < buildImage.getHeight()) {
                                imgX = 0;
                                imgY++;
                            } else if (rangeBlockDist <= mc.interactionManager.getReachDistance() + 1.0F && blockDist >= 1.25/*1.1313709*/) {
                                mc.player.getInventory().selectedSlot = buildSlotLock;
                                ItemStack theItem = new ItemStack(blockMode == 4 ? Items.ARMOR_STAND : (blockMode == 6 ? Items.OAK_SIGN : theBlock));
                                if (blockMode == 2) {
                                    Integer rotInt = 0;
                                    switch (startingDir) {
                                        case NORTH:
                                            rotInt = 0;
                                            break;
                                        case SOUTH:
                                            rotInt = 8;
                                            break;
                                        case EAST:
                                            rotInt = 4;
                                            break;
                                        case WEST:
                                            rotInt = 12;
                                            break;
                                        default:
                                    }
                                    if (imgY % 2 == 0 && imgY == buildImage.getHeight()) {
                                        theItem.setNbt(StringNbtReader.parse("{BlockStateTag:{rotation:\"" + rotInt + "\"}}"));
                                    } else if (imgY % 2 == 1) {
                                        int bp = buildImage.getRGB(imgX, buildImage.getHeight() - 2 - imgY),
                                                bimgA = (bp >> 24) & 0xff,
                                                bimgR = (bp >> 16) & 0xff,
                                                bimgG = (bp >> 8) & 0xff,
                                                bimgB = bp & 0xff;
                                        theItem.setNbt(StringNbtReader.parse("{BlockEntityTag:{Patterns:[{Color:" + rgbaToNumbers(bimgR, bimgG, bimgB, bimgA) + ",Pattern:\"hh\"}]},BlockStateTag:{rotation:\"" + rotInt + "\"}}"));
                                    }
                                    Main.loadItem(theItem);
                                    //Main.rightClickBlock(blockPos, startingDir);
                                    Main.rightClickBlock(blockPos, Direction.UP);
                                } else if (blockMode == 4) {
                                    String restxt = "";
                                    for (int ax = 0; ax < buildImage.getWidth(); ax++) {
                                        int ap = buildImage.getRGB(ax, buildImage.getHeight() - 1 - imgY),
                                                aimgA = (ap >> 24) & 0xff,
                                                aimgR = (ap >> 16) & 0xff,
                                                aimgG = (ap >> 8) & 0xff,
                                                aimgB = ap & 0xff;
                                        restxt += ("{\"text\":\"⬛\",\"color\":\"" + String.format("#%02x%02x%02x", aimgR, aimgG, aimgB) + "\"},").repeat(5);
                                    }
                                    restxt = restxt.substring(0, restxt.length() - 1);
                                    theItem.setNbt(StringNbtReader.parse("{EntityTag:{CustomNameVisible:1b,Marker:1b,Invisible:1b,PersistenceRequired:1b,CustomName:'[" + restxt + "]'}}"));
                                    Main.loadItem(theItem);
                                    Main.rightClickBlock(blockPos, Direction.UP);
                                    imgX--;
                                    imgY++;
                                } else if (blockMode == 6) {
                                    Integer rotInt = 0;
                                    switch (startingDir) {
                                        case NORTH:
                                            rotInt = 0;
                                            break;
                                        case SOUTH:
                                            rotInt = 8;
                                            break;
                                        case EAST:
                                            rotInt = 4;
                                            break;
                                        case WEST:
                                            rotInt = 12;
                                            break;
                                        default:
                                    }
                                    String signLines = "{BlockEntityTag:{Text1:'$t1',Text2:'$t2',Text3:'$t3',Text4:'$t4'},BlockStateTag:{rotation:\"" + rotInt + "\"}}";
                                    for (int i = 1; i < 5; i++) {
                                        String signLine = "[";
                                        if (buildImage.getHeight() - 5 + i - imgY < 0) {
                                            signLine += "";
                                        } else {
                                            for (int sx = 0; sx < 5; sx++) {
                                                if (imgX + sx >= buildImage.getWidth()) {
                                                    signLine += "{\"text\":\"█\",\"color\":\"black\"},".repeat(2);
                                                } else {
                                                    int sp = buildImage.getRGB(imgX + sx, buildImage.getHeight() - 5 + i - imgY),
                                                            simgA = (sp >> 24) & 0xff,
                                                            simgR = (sp >> 16) & 0xff,
                                                            simgG = (sp >> 8) & 0xff,
                                                            simgB = sp & 0xff;
                                                    signLine += ("{\"text\":\"█\",\"color\":\"" + String.format("#%02x%02x%02x", simgR, simgG, simgB) + "\"},").repeat(2);
                                                }
                                            }
                                        }
                                        if (signLine.equals("[")) {
                                            signLine = "";
                                        } else {
                                            signLine = signLine.substring(0, signLine.length() - 1) + "]";
                                        }
                                        signLines = signLines.replaceFirst("\\$t" + i, signLine);
                                    }
                                    theItem.setNbt(StringNbtReader.parse(signLines));
                                    Main.loadItem(theItem);
                                    //Main.rightClickBlock(blockPos, startingDir);
                                    Main.rightClickBlock(blockPos, Direction.UP);
                                    imgX += 4;//++ later on adds 1 more
                                } else {
                                    Main.loadItem(theItem);
                                    Main.rightClickBlock(blockPos, Direction.UP);
                                }
                                imgX++;
                                imgBuildPart2 = false;
                                imgBuildPart1 = true;//delay 1tick since successfully placed block
                            }
                        } else {
                            imgX++;
                        }
                        if (imgX >= buildImage.getWidth()) {
                            imgX = 0;
                            imgY++;
                            if (blockMode == 6) imgY += 3;//add 3 more to make +4
                        }
                    }
                    if(imgY>=(blockMode==6?(4*(1+(buildImage.getHeight()/4))):buildImage.getHeight())){
                        imgBuildPart2=false;
                        isBuildingImage=false;
                        Main.clearHotbar();
                        Main.updateInventory();
                        return;
                    }
                }
            }else{
                if(imgBuildPart2)throw new Exception();
            }
        }catch(Exception e){
            isBuildingImage=false;
            imgBuildPart1=false;
            imgBuildPart2=false;
            imgX=0;
            imgY=0;
        }
    }

    public static File updateImage(){
        File directory=Main.modpath.toFile();
        if (!directory.exists())directory.mkdir();
        File imageFile = defaultImageFile;
        try {
            if(imageFile.exists()){
                buildImage=ImageIO.read(imageFile);
            }else{
                buildImage=ImageIO.read(new URL(defaultImage));
                ImageIO.write(buildImage,"png",imageFile);
            }
        } catch (Exception e) {
            return null;
        }
        Integer imgWidth=buildImage.getWidth(),
                imgHeight=buildImage.getHeight();
        //Integer maxSize=blockMode==3?30:50;//didnt ask
        if(imgWidth>maxSize||imgHeight>maxSize) {
            Integer aspectRatio=imgWidth/imgHeight;
            buildImage = convertImageToBuffered(buildImage.getScaledInstance(aspectRatio<1?-1:maxSize, aspectRatio<1?maxSize:-1, Image.SCALE_DEFAULT));
        }
        return imageFile;
    }
    public static BufferedImage convertImageToBuffered(Image image){
        BufferedImage newImage=new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g=newImage.createGraphics();
        g.drawImage(image,0,0,null);
        g.dispose();
        return newImage;
    }
    public static Integer rgbaToNumbers(int r,int g,int b,int a){
        if(blockMode!=2&&a<85)return -1;
        int id=0;
        int shortestDistance=1000;
        switch(blockMode){
            case 0:
                for (int i = 0; i < ImageBlocks.blocks.length; i++) {
                    int dist = colorDistance(ImageBlocks.blockRGB[i][0], ImageBlocks.blockRGB[i][1], ImageBlocks.blockRGB[i][2], r, g, b);
                    if (dist < shortestDistance/*||(dist==shortestDistance&&Math.random()<0.5)*/) {
                        shortestDistance = dist;
                        id = i;
                    }
                }
                break;
            case 1:
            case 2:
                for (int i = 0; i < rgbColors.length; i++) {
                    int dist = colorDistance(rgbColors[i][0], rgbColors[i][1], rgbColors[i][2], r, g, b);
                    if (dist < shortestDistance) {
                        shortestDistance = dist;
                        id = i;
                    }
                }
                break;
        }

        return id;
    }
    public static Block rgbaToBlocks(int r,int g,int b,int a){
        if(blockMode==4)return Blocks.TNT;
        Integer rgbaNums=rgbaToNumbers(r,g,b,a);
        if(rgbaNums==-1)return Blocks.BARRIER;
        return (blockMode==2?bannerBlocks:(blockMode==1?(a<170?stainedGlassBlocks:concreteBlocks):ImageBlocks.blocks))[rgbaNums];
    }
    public static float colorDistance(double r1,double g1,double b1,double r2,double g2,double b2) {
        //return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
        return (float)Math.sqrt(Math.pow(r1 - r2, 2) + Math.pow(g1 - g2, 2) + Math.pow(b1 - b2, 2));
    }
    public static int colorDistance(int r1,int g1,int b1,int r2,int g2,int b2) {
        return (int)colorDistance((double)r1,(double)g1,(double)b1,(double)r2,(double)g2,(double)b2);
    }
}
