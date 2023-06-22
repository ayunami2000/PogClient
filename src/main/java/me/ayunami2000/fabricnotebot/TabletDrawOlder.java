package me.ayunami2000.fabricnotebot;

//import java.io.*;
//import java.util.Base64;

import java.awt.*;

public class TabletDrawOlder {
    public static Boolean tabletDrawEnabled=false;
    public static Boolean playerPen=true;
    /*public static JPenWindow jPenWindow=null;
    public static float[] penDownLoc = new float[]{0,0};
    public static float penPressure = 0;*/
    /*private static String pythonProgram=new String(Base64.getDecoder().decode("aW1wb3J0IG9zLCB0aW1lLCBzeXMsIHN1YnByb2Nlc3MKZnJvbSBQeVF0NS5RdENvcmUgaW1wb3J0ICoKZnJvbSBQeVF0NS5RdEd1aSBpbXBvcnQgKgpmcm9tIFB5UXQ1LlF0V2lkZ2V0cyBpbXBvcnQgKgoKCmNsYXNzIFByZXNzdXJlQmFyKFFXaWRnZXQpOgogICAgZGVmIF9faW5pdF9fKHNlbGYsIHBhcmVudD1Ob25lKToKICAgICAgICBzdXBlcigpLl9faW5pdF9fKCkKICAgICAgICBzZWxmLnBlbl9wcmVzc3VyZSA9IDAKICAgICAgICBmcmFtZV9yZWN0ID0gYXBwLmRlc2t0b3AoKS5mcmFtZUdlb21ldHJ5KCkKICAgICAgICBzZWxmLnByb2dyZXNzID0gUVByb2dyZXNzQmFyKHNlbGYpCiAgICAgICAgc2VsZi5yZXNpemUoMzAwLCAzMCkKICAgICAgICBzZWxmLnByb2dyZXNzLnNldEdlb21ldHJ5KDAsIDAsIDMwMCwgMjUpCiAgICAgICAgc2VsZi5wcm9ncmVzcy5zZXRNYXhpbXVtKDEwMCkKICAgICAgICBzZWxmLnNldFdpbmRvd1RpdGxlKCJQZW4gUHJlc3N1cmUgVmlld2VyIikKICAgICAgICBzZWxmLnNldFdpbmRvd09wYWNpdHkoMC4yKQogICAgICAgIAogICAgZGVmIHRhYmxldEV2ZW50KHNlbGYsIHRhYmxldEV2ZW50KToKICAgICAgICBzZWxmLnBlbl9wcmVzc3VyZSA9IGludCh0YWJsZXRFdmVudC5wcmVzc3VyZSgpICogMTAwKQoKICAgICAgICBpZih0YWJsZXRFdmVudC50eXBlKCkgPT0gUVRhYmxldEV2ZW50LlRhYmxldFByZXNzKToKICAgICAgICAgICAgc2VsZi5wZW5Jc0Rvd24gPSBUcnVlCgogICAgICAgIHNlbGYucHJvZ3Jlc3Muc2V0VmFsdWUoc2VsZi5wZW5fcHJlc3N1cmUpCiAgICAgICAgcHJpbnQoc2VsZi5wZW5fcHJlc3N1cmUpCiAgICAgICAgdGFibGV0RXZlbnQuYWNjZXB0KCkKICAgICAgICBzZWxmLnVwZGF0ZSgpCgoKYXBwID0gUUFwcGxpY2F0aW9uKHN5cy5hcmd2KQpteVdpbmRvdyA9IFByZXNzdXJlQmFyKCkKbXlXaW5kb3cuc2hvdygpCmFwcC5leGVjKCk="));
    private static File pythonFile=Main.modpath.resolve("PenPressure.py").toFile();
    private static Process penProcess=null;
    private static BufferedReader penReader = null;
    public static Integer penPressure = 0;
    public static String penPressureStr = "";*/
    public static void tick(){
        /*
        if(tabletDrawEnabled){
            Main.mc.mouse.unlockCursor();
        }
        */
        //if(tabletDrawEnabled){
            /*
            new Thread(() -> {
                try {
                    try {
                        penPressure = Integer.parseInt(penReader.readLine());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
            }).start();
            */
        //}

    }
    //public static void initStuff(){
        //jPenWindow=new JPenWindow();
        //writeFile();
    //}
/*
    public static void writeFile(){
        File directory=Main.modpath.toFile();
        if (!directory.exists())directory.mkdir();
        if(!pythonFile.exists()) {
            try {
                PrintWriter writer = new PrintWriter(pythonFile, "UTF-8");
                writer.println(pythonProgram);
                writer.close();
            } catch (IOException e) {
            }
            //pythonFile=Main.modpath.resolve("PenPressure.py").toFile();
        }
    }
*/
    public static void toggleTabletDraw(){
        Main.inGameLog("Error: TabletDraw is currently disabled due to Java 16 hav big gay.");
        /*
        Main.inGameLog((tabletDrawEnabled?"Dis":"En")+"abling TabletDraw");
        if(tabletDrawEnabled){
            //disable

            tabletDrawEnabled=false;
        }else{
            //enable

            tabletDrawEnabled=true;
        }*/
        /*
        Main.inGameLog((tabletDrawEnabled?"Dis":"En")+"abling TabletDraw");
        if(tabletDrawEnabled){
            //disable
            jPenWindow.setVisible(false);
            penDownLoc = new float[]{0,0};
            tabletDrawEnabled=false;
        }else{
            //enable
            jPenWindow.setVisible(true);
            tabletDrawEnabled=true;
        }*/
/*
        Main.inGameLog((tabletDrawEnabled?"Dis":"En")+"abling TabletDraw");
        if(tabletDrawEnabled){
            penProcess.destroyForcibly();
            tabletDrawEnabled=false;
        }else{
            try {
                penProcess = new ProcessBuilder("python","\""+pythonFile.getAbsolutePath()+"\"").start();
                new Thread(() -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(penProcess.getInputStream()))) {
                        //penReader = reader;
                        String oldPenPressureStr = penPressureStr;
                        while ((penPressureStr = reader.readLine()) != null) {
                            if(oldPenPressureStr != penPressureStr){
                                try {
                                    //penPressure = Integer.parseInt(penPressureStr);
                                    //System.out.println(penPressure);
                                    System.out.println(penPressureStr);
                                }catch(NumberFormatException e){}
                            }
                        }
                        reader.close();
                        //penProcess.waitFor();
                        if(tabletDrawEnabled)toggleTabletDraw();
                        //String line;
                        //while (penProcess.isAlive()) {
                        //    if((line = reader.readLine()) != null) {
                        //        try {
                        //            penPressure = Integer.parseInt(line);
                        //        } catch (NumberFormatException e) {
                        //            e.printStackTrace();
                        //        }
                        //    }
                        //}
                        //if(tabletDrawEnabled)toggleTabletDraw();
                        //
                    } catch (Exception e) {}
                }).start();
                tabletDrawEnabled=true;
            }catch(IOException e){
                e.printStackTrace();
            }
        }
*/
    }
}
