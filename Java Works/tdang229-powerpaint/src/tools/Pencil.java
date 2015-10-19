package tools;

import gui.DrawingPanel;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * @author Trung
 * @version November 4, 2014
 */
public class Pencil extends AbstractAction {
    
    /***/
    private final DrawingPanel myPanel;

    /**
     * @param thePanel 
     */
    public Pencil(final DrawingPanel thePanel) {
        super("Pencil");
        myPanel = thePanel;
        putValue(Action.SELECTED_KEY, true);
    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        
        myPanel.setCurrentTool((Tools) this);
        myPanel.repaint();
    }

//    @Override
//    public void record() {
//        // TODO Auto-generated method stub
//        
//    }

//    @Override
//    public Shape getShape() {
//        // TODO Auto-generated method stub
//        return null;
//    }

}
