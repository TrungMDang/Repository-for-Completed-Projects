package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import tools.BrushProperties;
import tools.Line;
import tools.Tools;



/**
 * @author Trung
 * @version November 4, 2014
 */
@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {
    /** A toolkit.*/
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();

    /***/
    private static final int WIDTH = 550;

    /***/
    private static final int HEIGHT = 300;

    /***/
    private static final Float DEFAULT_STROKE_WIDTH = 5f;

    /***/
    private Tools myCurrentTool;

    /***/
    private Color myCurrentColor;

    /***/
    private Stroke myCurrentStroke;

    /***/
    private Point myInitialMouseLocation;

    /***/
    private Point myMouseLocation;

    /***/
    private List<BrushProperties> myBrushPropertiesList;

    /***/
    private Tools[] myTools;
    /**
     * 
     */
    public DrawingPanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        final MyMouseListener mouseListener = new MyMouseListener();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        setUp();
    }
    public void setCurrentTool(final Tools theTool) {
        myCurrentTool = theTool;
    }
    public BrushProperties getBrushProperties() {
        if (myBrushPropertiesList.size() >= 1)
            return myBrushPropertiesList.get(myBrushPropertiesList.size() - 1);
        else
            return null;
    }
    public List<BrushProperties> getBrushPropertiesList() {
        return myBrushPropertiesList;
    }

    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                     RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(myCurrentColor);
        g2d.setStroke(new BasicStroke(DEFAULT_STROKE_WIDTH));
        
        if (myCurrentTool.getShape() != null) {
            myBrushPropertiesList.add(new BrushProperties(myCurrentTool, 
                                                          myCurrentColor, myCurrentStroke));
            for (final BrushProperties bP : myBrushPropertiesList) {
                bP.getTool().paintTool(g2d, bP.getColor(), bP.getStroke());
            }
        }
        
    }
    /**
     * 
     */
    private void setUp() {
        myCurrentTool = new Line();
        myCurrentColor = Color.BLACK;
        myCurrentStroke = new BasicStroke(DEFAULT_STROKE_WIDTH);
        myBrushPropertiesList = new ArrayList<>();
        final Tools[] tools = {new Line()};
        myTools = tools;
    }
    /**
     * @author Trung Dang
     * @version November 4, 2014
     */
    private class MyMouseListener extends MouseAdapter {

        @Override
        public void mousePressed(final MouseEvent theEvent) {
            myCurrentTool.setStartingPoint(theEvent.getPoint());
        }

        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            myCurrentTool.setStartingPoint(null);
        }

        @Override
        public void mouseMoved(final MouseEvent theEvent) {
            myCurrentTool.setEndingPoint(theEvent.getPoint());
        }
        @Override
        public void mouseDragged(final MouseEvent theEvent) {
            myCurrentTool.setEndingPoint(theEvent.getPoint());
            repaint();
        }


    }
}
