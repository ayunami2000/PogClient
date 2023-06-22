package me.ayunami2000.fabricnotebot;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.enums.Instrument;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.EntityPose;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static me.ayunami2000.fabricnotebot.Main.mc;

public class NotebotPlayer {
    public static Boolean essOrVanilla=true;
    public static Boolean autoRepair=true;
    public static Boolean dontReadFromFile=true;
    public static File realDefaultNotebotFile=Main.modpath.resolve("FabricNotebotSong.txt").toFile();
    public static File defaultNotebotFile=realDefaultNotebotFile;
    public static Boolean notebotEnabled=false;
    private static Boolean notebotBuildPart0=false,notebotBuildPart1=false,notebotBuildPart2=false,notebotBuildPart3=false,notebotBuildPart4=false;
    private static BlockPos startingPos;
    private static Direction startingDir;
    public static String theDefaultSong = "0:0:0\n1:1:0\n2:2:0\n3:3:0\n4:4:0\n5:5:0\n6:6:0\n7:7:0\n8:8:0\n9:9:0\n10:10:0\n11:11:0\n12:12:0\n13:13:0\n14:14:0\n15:15:0\n16:16:0\n17:17:0\n18:18:0\n19:19:0\n20:20:0\n21:21:0\n22:22:0\n23:23:0\n24:24:0\n25:0:1\n26:1:1\n27:2:1\n28:3:1\n29:4:1\n30:5:1\n31:6:1\n32:7:1\n33:8:1\n34:9:1\n35:10:1\n36:11:1\n37:12:1\n38:13:1\n39:14:1\n40:15:1\n41:16:1\n42:17:1\n43:18:1\n44:19:1\n45:20:1\n46:21:1\n47:22:1\n48:23:1\n49:24:1\n50:0:2\n51:1:2\n52:2:2\n53:3:2\n54:4:2\n55:5:2\n56:6:2\n57:7:2\n58:8:2\n59:9:2\n60:10:2\n61:11:2\n62:12:2\n63:13:2\n64:14:2\n65:15:2\n66:16:2\n67:17:2\n68:18:2\n69:19:2\n70:20:2\n71:21:2\n72:22:2\n73:23:2\n74:24:2\n75:0:3\n76:1:3\n77:2:3\n78:3:3\n79:4:3\n80:5:3\n81:6:3\n82:7:3\n83:8:3\n84:9:3\n85:10:3\n86:11:3\n87:12:3\n88:13:3\n89:14:3\n90:15:3\n91:16:3\n92:17:3\n93:18:3\n94:19:3\n95:20:3\n96:21:3\n97:22:3\n98:23:3\n99:24:3\n100:0:4\n101:1:4\n102:2:4\n103:3:4\n104:4:4\n105:5:4\n106:6:4\n107:7:4\n108:8:4\n109:9:4\n110:10:4\n111:11:4\n112:12:4\n113:13:4\n114:14:4\n115:15:4\n116:16:4\n117:17:4\n118:18:4\n119:19:4\n120:20:4\n121:21:4\n122:22:4\n123:23:4\n124:24:4\n125:0:5\n126:1:5\n127:2:5\n128:3:5\n129:4:5\n130:5:5\n131:6:5\n132:7:5\n133:8:5\n134:9:5\n135:10:5\n136:11:5\n137:12:5\n138:13:5\n139:14:5\n140:15:5\n141:16:5\n142:17:5\n143:18:5\n144:19:5\n145:20:5\n146:21:5\n147:22:5\n148:23:5\n149:24:5\n150:0:6\n151:1:6\n152:2:6\n153:3:6\n154:4:6\n155:5:6\n156:6:6\n157:7:6\n158:8:6\n159:9:6\n160:10:6\n161:11:6\n162:12:6\n163:13:6\n164:14:6\n165:15:6\n166:16:6\n167:17:6\n168:18:6\n169:19:6\n170:20:6\n171:21:6\n172:22:6\n173:23:6\n174:24:6\n175:0:7\n176:1:7\n177:2:7\n178:3:7\n179:4:7\n180:5:7\n181:6:7\n182:7:7\n183:8:7\n184:9:7\n185:10:7\n186:11:7\n187:12:7\n188:13:7\n189:14:7\n190:15:7\n191:16:7\n192:17:7\n193:18:7\n194:19:7\n195:20:7\n196:21:7\n197:22:7\n198:23:7\n199:24:7\n200:0:8\n201:1:8\n202:2:8\n203:3:8\n204:4:8\n205:5:8\n206:6:8\n207:7:8\n208:8:8\n209:9:8\n210:10:8\n211:11:8\n212:12:8\n213:13:8\n214:14:8\n215:15:8\n216:16:8\n217:17:8\n218:18:8\n219:19:8\n220:20:8\n221:21:8\n222:22:8\n223:23:8\n224:24:8\n225:0:9\n226:1:9\n227:2:9\n228:3:9\n229:4:9\n230:5:9\n231:6:9\n232:7:9\n233:8:9\n234:9:9\n235:10:9\n236:11:9\n237:12:9\n238:13:9\n239:14:9\n240:15:9\n241:16:9\n242:17:9\n243:18:9\n244:19:9\n245:20:9\n246:21:9\n247:22:9\n248:23:9\n249:24:9\n250:0:10\n251:1:10\n252:2:10\n253:3:10\n254:4:10\n255:5:10\n256:6:10\n257:7:10\n258:8:10\n259:9:10\n260:10:10\n261:11:10\n262:12:10\n263:13:10\n264:14:10\n265:15:10\n266:16:10\n267:17:10\n268:18:10\n269:19:10\n270:20:10\n271:21:10\n272:22:10\n273:23:10\n274:24:10\n275:0:11\n276:1:11\n277:2:11\n278:3:11\n279:4:11\n280:5:11\n281:6:11\n282:7:11\n283:8:11\n284:9:11\n285:10:11\n286:11:11\n287:12:11\n288:13:11\n289:14:11\n290:15:11\n291:16:11\n292:17:11\n293:18:11\n294:19:11\n295:20:11\n296:21:11\n297:22:11\n298:23:11\n299:24:11\n300:0:12\n301:1:12\n302:2:12\n303:3:12\n304:4:12\n305:5:12\n306:6:12\n307:7:12\n308:8:12\n309:9:12\n310:10:12\n311:11:12\n312:12:12\n313:13:12\n314:14:12\n315:15:12\n316:16:12\n317:17:12\n318:18:12\n319:19:12\n320:20:12\n321:21:12\n322:22:12\n323:23:12\n324:24:12\n325:0:13\n326:1:13\n327:2:13\n328:3:13\n329:4:13\n330:5:13\n331:6:13\n332:7:13\n333:8:13\n334:9:13\n335:10:13\n336:11:13\n337:12:13\n338:13:13\n339:14:13\n340:15:13\n341:16:13\n342:17:13\n343:18:13\n344:19:13\n345:20:13\n346:21:13\n347:22:13\n348:23:13\n349:24:13\n350:0:14\n351:1:14\n352:2:14\n353:3:14\n354:4:14\n355:5:14\n356:6:14\n357:7:14\n358:8:14\n359:9:14\n360:10:14\n361:11:14\n362:12:14\n363:13:14\n364:14:14\n365:15:14\n366:16:14\n367:17:14\n368:18:14\n369:19:14\n370:20:14\n371:21:14\n372:22:14\n373:23:14\n374:24:14\n375:0:15\n376:1:15\n377:2:15\n378:3:15\n379:4:15\n380:5:15\n381:6:15\n382:7:15\n383:8:15\n384:9:15\n385:10:15\n386:11:15\n387:12:15\n388:13:15\n389:14:15\n390:15:15\n391:16:15\n392:17:15\n393:18:15\n394:19:15\n395:20:15\n396:21:15\n397:22:15\n398:23:15\n399:24:15";
    public static String theSong = theDefaultSong;
    private static String[] instruments=new String[]{"harp","basedrum","snare","hat","bass","flute","bell","guitar","chime","xylophone","iron_xylophone","cow_bell","didgeridoo","bit","banjo","pling"};
    private static Integer stageX=0,stageY=0,stageYreal=0,stageZ=0;
    private static Map<Integer, Map<Integer, ArrayList<Integer>>> songNotes = new HashMap<>();
    private static ArrayList<Integer[]> buildNotesArr= new ArrayList<>();
    private static Map<Integer[],BlockPos> notePositions= new HashMap<>();
    private static Integer realCurrentTick=0,maxTick=0;
    public static ButtonWidget notebotToggleBtn=null;
    public static Boolean notebotInit=false;
    private static Boolean prevNoGravity=false;
    private static Integer part4ticktimeout=0;
    private static BlockPos tmpBlock=null;
    private static Integer buildSlotLock=8;
    public static Boolean loopSong=false;
    private static Boolean doLoop=true;//for sudden song ending

    public static void playASong() {
        if(Main.areAnyHacksEnabled()){
            if(notebotEnabled){
                notebotEnabled=false;
                updateNotebotToggleText(notebotToggleBtn);
                Main.inGameLog("Notebot is now disabled.");
            }else{
                Main.inGameLog("Error: You must disable other hacks before using Notebot!");
            }
            return;
        }
        if(tmpBlock!=null)return;//wait for notebot to reset
        updateFile();
        if(theSong==""){
            Main.inGameLog("Error: No songs are in the notebot file!");
            return;
        }
        if (!mc.player.getAbilities().creativeMode){
            Main.inGameLog("Error: You must be in creative mode to use notebot!");
            return;
        }
        notebotInit=true;
        updateNotebotToggleText(notebotToggleBtn);
        Main.inGameLog("Notebot is now " + (notebotEnabled ? "en" : "dis") + "abled.");
        doLoop=true;
        notebotBuildPart0=false;
        notebotBuildPart1=false;
        notebotBuildPart2=false;
        notebotBuildPart3=false;
        notebotBuildPart4=false;
        part4ticktimeout=0;
        stageX=0;
        stageY=0;
        stageYreal=0;
        stageZ=0;
        maxTick=0;
        realCurrentTick=0;
        tmpBlock=null;
        prevNoGravity=mc.player.hasNoGravity();
        mc.player.setNoGravity(true);
        startingPos=mc.player.getBlockPos();
        startingDir=mc.player.getHorizontalFacing();
        recenterPlayer();
        int emptySlot=mc.player.getInventory().getEmptySlot();
        buildSlotLock=emptySlot==-1?8:emptySlot;
        String[] songLines=theSong.replaceAll("^\\n+|\\n+$","").split("\n");
        buildNotesArr= new ArrayList<>();
        songNotes = new HashMap<>();
        for(int i=0;i<songLines.length;i++){
            Map<Integer, Map<Integer,ArrayList<Integer>>> localSongNotes = new HashMap<>();
            String[] noteParts=songLines[i].split(":");
            Integer instr=Integer.parseInt(noteParts[2]);
            Integer note=Integer.parseInt(noteParts[1]);
            Boolean doesItReallyAlreadyHave=false;
            for(Integer[] buildNotesItem:buildNotesArr){
                if(buildNotesItem[0]==instr&&buildNotesItem[1]==note)doesItReallyAlreadyHave=true;
            }
            if(!doesItReallyAlreadyHave)buildNotesArr.add(new Integer[]{instr,note});
            Map<Integer,ArrayList<Integer>> otherParts=new HashMap<>();
            ArrayList<Integer> newNoteList=new ArrayList<Integer>();
            newNoteList.add(note);
            otherParts.put(instr,newNoteList);
            localSongNotes.put(Integer.parseInt(noteParts[0]), otherParts);
            localSongNotes.forEach((key, value) -> songNotes.merge(key, value, (v1, v2) -> {
                v1.forEach((keyy, valuee) -> v2.merge(keyy, valuee, (v3, v4) -> {
                    v4.addAll(v3);
                    return v4;
                }));
                return v2;
            }));
        }
        notePositions= new HashMap<>();
        notebotBuildPart2=true;
        notebotEnabled=true;
        notebotInit=false;
    }
    public static void tick(){
        try {
            if (notebotEnabled) {
                if(!notebotBuildPart3)mc.player.getInventory().selectedSlot=buildSlotLock;
                if(notebotBuildPart0){
                    notebotBuildPart0 = false;
                    notebotBuildPart1 = true;
                }else if (notebotBuildPart1) {
                    notebotBuildPart1 = false;
                    Main.rightClickBlock(tmpBlock,Direction.UP);
                    notebotBuildPart2 = true;
                } else if (notebotBuildPart2) {
                    //getting items
                    Integer theNumber=(stageY*81)+(stageZ*9)+stageX;
                    if (theNumber>=buildNotesArr.size()){
                        notebotBuildPart2=false;
                        setSneaking(false);
                        mc.player.setNoGravity(prevNoGravity);
                        Main.loadItem(ItemStack.EMPTY,buildSlotLock);
						Main.sendChatOrCmd(essOrVanilla?"/gms":"/gamemode survival");
                        maxTick = Collections.max(songNotes.keySet());
                        part4ticktimeout=0;
                        notebotBuildPart4=true;
                        Main.inGameLog("Now playing the song...");
                        return;
                    }
                    BlockPos blockPos=startingPos.add(0,(stageY==0&&(stageX==0||stageX==8)&&(stageZ==0||stageZ==8))?0:-1,0);
                    switch(startingDir){
                        case NORTH:
                            blockPos=blockPos.add(4-stageX,stageYreal,stageZ-4);
                            break;
                        case SOUTH:
                            blockPos=blockPos.add(stageX-4,stageYreal,4-stageZ);
                            break;
                        case EAST:
                            blockPos=blockPos.add(4-stageZ,stageYreal,4-stageX);
                            break;
                        case WEST:
                            blockPos=blockPos.add(stageZ-4,stageYreal,stageX-4);
                            break;
                        default:
                            blockPos=blockPos.add(4-stageX,stageYreal,stageZ-4);
                    }
                    Integer[] currentNote = buildNotesArr.get(theNumber);
                    BlockState theBlock=mc.world.getBlockState(blockPos);
                    if(theBlock.getBlock() instanceof NoteBlock){
                        Instrument blockInstrument=theBlock.get(NoteBlock.INSTRUMENT);
                        Integer blockNote=theBlock.get(NoteBlock.NOTE);
                        if(instruments[currentNote[0]]==blockInstrument.asString()&&currentNote[1]==blockNote){
                            Main.inGameLog("Block already placed, skipping...");
                            notePositions.put(buildNotesArr.get(theNumber), blockPos);
                            mc.interactionManager.attackBlock(blockPos.up(), Direction.UP);
                            stageX++;
                            if(stageX==9){
                                stageX=0;
                                stageZ++;
                                if(stageZ==9){
                                    stageZ=0;
                                    stageYreal+=(stageY==0?3:2);
                                    stageY+=1;
                                }
                            }
                            return;
                        }
                    }
                    ItemStack noteblocks = new ItemStack(Blocks.NOTE_BLOCK);
                    NbtCompound noteblocknbt = new NbtCompound();
                    NbtCompound noteblockblocknbt = new NbtCompound();
                    noteblockblocknbt.putString("instrument", instruments[currentNote[0]]);
                    noteblockblocknbt.putInt("note", currentNote[1]);
                    noteblocknbt.put("BlockStateTag", noteblockblocknbt);
                    noteblocks.setNbt(noteblocknbt);
                    Vec3d currPos=mc.player.getPos().add(-0.5,0,-0.5);
                    Vec3d blockPosVec3d=new Vec3d(blockPos.getX(),blockPos.getY(),blockPos.getZ());
                    double rangeBlockDist=currPos.add(0,mc.player.getEyeHeight(mc.player.getPose()),0).distanceTo(blockPosVec3d);
                    double blockDist=currPos.add(0,0.5,0).distanceTo(blockPosVec3d);
                    Main.inGameLog(String.valueOf(blockDist));
                    if ((rangeBlockDist <= mc.interactionManager.getReachDistance()+1.0F && (blockDist >= 1.25F/*1.1313709*/))) {
                        Main.loadItem(noteblocks,buildSlotLock);
                        if(!mc.player.hasNoGravity())mc.player.setNoGravity(true);
                        notePositions.put(buildNotesArr.get(theNumber), blockPos);
                        mc.interactionManager.attackBlock(blockPos.up(), Direction.UP);
                        mc.interactionManager.attackBlock(blockPos, Direction.UP);
                        mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player,ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
                        tmpBlock=blockPos;
                        stageX++;
                        if(stageX==9){
                            stageX=0;
                            stageZ++;
                            if(stageZ==9){
                                stageZ=0;
                                stageYreal+=(stageY==0?3:2);
                                stageY+=1;
                            }
                        }
                        notebotBuildPart2=false;
                        notebotBuildPart0=true;//add 2 tick delay
                    }else{
                        mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player,ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
                    }
                } else if (notebotBuildPart3) {
                    //playing song
                    if (mc.player.getAbilities().creativeMode){
                        Main.inGameLog("Error: Turning off notebot since you changed to creative mode!");
                        doLoop=false;
                        realCurrentTick = maxTick;
                    }else{
                        //mc.player.pushSpeedReduction = 1.0F;
                        if(mc.player.isInSneakingPose()&&!mc.options.sneakKey.isPressed())mc.player.setPose(EntityPose.STANDING);
                        setSneaking(false);
                        Boolean doSneak=false;
                        if (songNotes.containsKey(realCurrentTick)) {
                            for (Integer instr : songNotes.get(realCurrentTick).keySet()) {
                                for (Integer note : songNotes.get(realCurrentTick).get(instr)) {
                                    for (Integer[] instrNotePair : notePositions.keySet()) {
                                        if (instrNotePair[0] == instr && instrNotePair[1] == note) {
                                            doSneak=true;
                                            BlockPos blockPos = notePositions.get(instrNotePair);
                                            if(autoRepair) {
                                                BlockState theBlock = mc.world.getBlockState(blockPos);
                                                Instrument blockInstrument = null;
                                                Integer blockNote = null;
                                                if (theBlock.getBlock() instanceof NoteBlock) {
                                                    blockInstrument = theBlock.get(NoteBlock.INSTRUMENT);
                                                    blockNote = theBlock.get(NoteBlock.NOTE);
                                                }
                                                if (blockInstrument == null || blockNote == null || blockInstrument.asString() != instruments[instr] || blockNote != note) {
                                                    Main.inGameLog("Block missing, scanning...");
													Main.sendChatOrCmd(essOrVanilla?"/gmc":"/gamemode creative");
                                                    stageX=0;
                                                    stageY=0;
                                                    stageYreal=0;
                                                    stageZ=0;
                                                    notebotBuildPart3=false;
                                                    notebotBuildPart2=true;
                                                }
                                                //todo: check above noteblocks for not air
                                            }
                                            mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos, Direction.UP));
                                        }
                                    }
                                }
                            }
                        }
                        if(doSneak)setSneaking(true);
                    }
                    if (realCurrentTick >= maxTick) {
                        if(loopSong&&doLoop){
                            Main.inGameLog("Looping song...");
                            realCurrentTick=0;
                        }else {
                            doLoop=true;
                            notebotEnabled = false;
                            updateNotebotToggleText(notebotToggleBtn);
                            notebotBuildPart0 = false;
                            notebotBuildPart1 = false;
                            notebotBuildPart2 = false;
                            notebotBuildPart3 = false;
                            notebotBuildPart4 = false;
                            part4ticktimeout = 0;
                            stageX = 0;
                            stageY = 0;
                            stageYreal = 0;
                            stageZ = 0;
                            maxTick = 0;
                            realCurrentTick = 0;
                            tmpBlock = null;
                            setSneaking(false);
                            mc.player.setNoGravity(prevNoGravity);
                            Main.inGameLog("The song has ended! Turning off notebot.");
							Main.sendChatOrCmd(essOrVanilla ? "/gmc" : "/gamemode creative");
                        }
                    } else {
                        realCurrentTick++;
                    }
                }else if(notebotBuildPart4){
                    //waiting a few ticks before playing song to let the commands go through
                    switch(part4ticktimeout){
                        case 3:
                            Vec3d offsetPos=new Vec3d(startingPos.getX()+0.5,startingPos.getY(),startingPos.getZ()+0.5);
							Main.sendChatOrCmd("/tp "+offsetPos.getX()+" "+offsetPos.getY()+" "+offsetPos.getZ());
                            break;
                        case 13:
                            recenterPlayer();
                            notebotBuildPart4=false;
                            part4ticktimeout=0;
                            notebotBuildPart3=true;
                            return;
                    }
                    part4ticktimeout++;
                }
            }else{
                if(tmpBlock!=null)throw new Exception();
            }
        }catch(Exception e){
            notebotEnabled=false;
            updateNotebotToggleText(notebotToggleBtn);
            notebotBuildPart0=false;
            notebotBuildPart1=false;
            notebotBuildPart2=false;
            notebotBuildPart3=false;
            notebotBuildPart4=false;
            part4ticktimeout=0;
            stageX=0;
            stageY=0;
            stageYreal=0;
            stageZ=0;
            maxTick=0;
            realCurrentTick=0;
            tmpBlock=null;
            try {
                setSneaking(false);
                mc.player.setNoGravity(prevNoGravity);
				Main.sendChatOrCmd(essOrVanilla?"/gmc":"/gamemode creative");
            }catch(Exception ee){}
        }
    }
    public static void updateFile(){
        File directory=Main.modpath.toFile();
        if (!directory.exists())directory.mkdir();
        if(dontReadFromFile)return;
        File songFile = defaultNotebotFile;
        try {
            theSong="";
            if(songFile.exists()){
                Scanner myReader = new Scanner(songFile);
                while (myReader.hasNextLine()) {
                    theSong+="\n"+myReader.nextLine();
                }
                myReader.close();
                theSong=theSong.replaceFirst("\n","");
            }else{
                return;
            }
        } catch (IOException e) {}
        if(theSong.trim().replaceAll("[\n \\s]","")=="")theSong="";
    }
    public static void updateNotebotToggleText(ButtonWidget btn){
        if(btn!=null&&btn.visible)btn.setMessage(Text.of("Notebot (§"+((notebotInit||notebotEnabled)?"aEn":"cDis")+"abled§r)"));
    }
    private static void recenterPlayer(){
        mc.player.setPos(startingPos.getX()+0.5,startingPos.getY(),startingPos.getZ()+0.5);
    }
    private static void setSneaking(Boolean toggle){
        mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player,toggle?ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY:ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
    }
    public static void skip(int ticks){
        if(notebotBuildPart3){
            realCurrentTick=Math.min(realCurrentTick+ticks,maxTick);
            Main.inGameLog("Skipped ahead 1 second (20 ticks)!");
        }else{
            Main.inGameLog("Error: Not playing right now!");
        }
    }
    public static void loop(){
        loopSong=!loopSong;
        Main.inGameLog("Looping has been "+(loopSong?"en":"dis")+"abled!");
    }
}
