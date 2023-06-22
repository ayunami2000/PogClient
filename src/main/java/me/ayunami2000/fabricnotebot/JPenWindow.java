package me.ayunami2000.fabricnotebot;

/*import javax.swing.*;

import jpen.*;
import jpen.demo.StatusReport;
import jpen.event.PenListener;
import jpen.owner.multiAwt.AwtPenToolkit;*/

public class JPenWindow /*implements PenListener*/ {
    /*
    private JFrame f = null;
    JPenWindow() {
        JLabel l = new JLabel("Move the pen or mouse over me!");
        AwtPenToolkit.addPenListener(l, this);
        f = new JFrame("TabletDraw");
        f.getContentPane().add(l);
        f.setExtendedState( f.getExtendedState()|JFrame.MAXIMIZED_BOTH );
        f.setOpacity(0.2f);
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        f.setVisible(false);
        System.out.println(new StatusReport(AwtPenToolkit.getPenManager()));
    }

    public void setVisible(Boolean visible){
        f.setVisible(visible);
    }

    public void penButtonEvent(PButtonEvent ev) {
        //System.out.println(ev);
    }

    public void penKindEvent(PKindEvent ev) {
        //System.out.println(ev);
    }

    public void penLevelEvent(PLevelEvent ev) {
        //System.out.println(ev);
        TabletDraw.penDownLoc[0]=0;
        TabletDraw.penDownLoc[1]=0;
        TabletDraw.penPressure=0;
        for (PLevel pLevel : ev.levels) {
            if(pLevel.getType() == PLevel.Type.X){
                TabletDraw.penDownLoc[0]=pLevel.value;
            }else if(pLevel.getType() == PLevel.Type.Y){
                TabletDraw.penDownLoc[1]=pLevel.value;
            }else if(pLevel.getType() == PLevel.Type.PRESSURE){
                TabletDraw.penPressure=pLevel.value;
            }
        }
        float vx = 12.8F * ((TabletDraw.penDownLoc[0] / (float) f.getWidth()) - 0.5F),
                vy = 7.2F * ((TabletDraw.penDownLoc[1] / (float) f.getHeight()) - 0.5F),
                vz = TabletDraw.penPressure / 8191.0F;
        System.out.println(vx+","+vy+","+vz);
    }

    public void penScrollEvent(PScrollEvent ev) {
        //System.out.println(ev);
    }

    public void penTock(long availableMillis) {
        //System.out.println("TOCK - available period fraction: " + availableMillis);
    }
    */
}
