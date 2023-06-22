package me.ayunami2000.fabricnotebot;

import me.ayunami2000.fabricnotebot.nbsapi.Instrument;
import me.ayunami2000.fabricnotebot.nbsapi.Layer;
import me.ayunami2000.fabricnotebot.nbsapi.Note;
import me.ayunami2000.fabricnotebot.nbsapi.Song;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ConvertNBS {

    public static List instruList= Arrays.asList("HARP","DRUM","SNARE","CLICK","BASS","FLUTE","BELL","GUITAR","CHIME","XYLOPHONE");
    public static void doConvert(){
        File nbsFile=Main.chooseFile(new String[]{"nbs","mid","midi"},"Note Block Studio Files OR Midi Files");
        if(nbsFile!=null&&nbsFile.exists()&&!nbsFile.isDirectory()) {
            String resStr=nbsFile.getName().toLowerCase(Locale.ROOT).endsWith(".nbs")?doLiveConvert(nbsFile):MidiConverter.midiToTxt(nbsFile);
            if (resStr==null||resStr=="") {
                Main.inGameLog("There was an error while converting your file.");
            } else {
                Main.inGameLog("File converted successfully!");
                File saveFile=Main.saveFile("txt","WWE/Inertia/Bleachhack/FabricNotebot Notebot Format");
                if(saveFile!=null){
                    try {
                        saveFile.createNewFile();
                        FileWriter myWriter = new FileWriter(saveFile);
                        myWriter.write(resStr);
                        myWriter.close();
                        Main.inGameLog("File saved successfully!");
                    }catch(Exception e){
                        Main.inGameLog("There was an error while saving your file.");
                    }
                };
            }
        }else{
            Main.inGameLog("There was an error while finding your file.");
        }
    }
    public static String doLiveConvert(File nbsFile){
        try{
            String resSongFile="";
            Map<Integer,ArrayList<String>> songLines=new HashMap<>();
            Song nbsSong = new Song(nbsFile);
            List<Layer> nbsSongBoard = nbsSong.getSongBoard();
            for (int i = 0; i < nbsSongBoard.size(); i++) {
                HashMap<Integer, Note> noteList = nbsSongBoard.get(i).getNoteList();
                for (Map.Entry note : noteList.entrySet()) {
                    Note noteInfo = (Note) note.getValue();
                    Integer noteKey=(int)((double)(int)note.getKey()/(5.0*((double)nbsSong.getTempo()/10000.0)));
                    if(!songLines.containsKey(noteKey))songLines.put(noteKey,new ArrayList<>());
                    ArrayList<String> tickLines=songLines.get(noteKey);
                    //keep notes within 2-octave range
                    Integer notePitch=Math.max(33,Math.min(57,noteInfo.getPitch()))-33;
                    tickLines.add(noteKey + ":" + notePitch + ":" + convertInstrument(noteInfo.getInstrument()) + "\n");
                    songLines.put(noteKey,tickLines);
                }
            }
            SortedSet<Integer> ticks = new TreeSet<>(songLines.keySet());
            for (Integer tick : ticks) {
                ArrayList<String> tickLines = songLines.get(tick);
                for(int i=0;i<tickLines.size();i++){
                    resSongFile+=tickLines.get(i);
                }
            }
            if(resSongFile.endsWith("\n"))resSongFile=resSongFile.substring(0,resSongFile.length()-1);
            return resSongFile;
        }catch(Exception e){
            Main.inGameLog("There was an error while converting your NBS.");
            return null;
        }
    }
    private static Integer convertInstrument(Instrument instr){
        Integer instrId=instruList.indexOf(instr.name());
        return instrId==-1?0:instrId;
    }
}
