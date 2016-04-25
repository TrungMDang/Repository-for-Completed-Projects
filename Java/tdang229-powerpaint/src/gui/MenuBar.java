/*
 * 
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tools.BrushProperties;

/**
 * @author Trung Dang
 * @version November 4, 2014
 */
@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

    /***/
    private static final int MAJOR_TICKS_SPACING = 5;

    /***/
    private static final int MINOR_TICKS_SPACING = 1;

    /***/
    private static final int MAX_THICKNESS = 20;

    /***/
    private final JMenu myToolsMenu;

    /***/
    private final ButtonGroup myButtonGroup;


    /**
     * 
     * 
     * @param theFrame The frame this Menu is attached to
     * @param thePanel The panel this Menu affects
     */
    public MenuBar(final JFrame theFrame, final DrawingPanel thePanel,
                   final BrushProperties theBrushProperties) {
        super();
        myToolsMenu = new JMenu("Tools");
        myButtonGroup = new ButtonGroup();
        setUpMenuBar(theFrame, thePanel, theBrushProperties);
    }

    /**
     * @param theFrame0 The frame this Menu is attached to
     */
    private void setUpMenuBar(final JFrame theFrame1, final DrawingPanel thePanel,
                              final BrushProperties theBrushProperties) {
        JMenu menu = new JMenu("File");
        addFileMenuItems(menu, theFrame1, thePanel);
        add(menu);
        menu = new JMenu("Options");
        addOptionsMenuItems(menu, thePanel, theBrushProperties);
        add(menu);
        addToolsMenuItems(myToolsMenu, thePanel);
        add(myToolsMenu);
        menu = new JMenu("Help");
        addHelpMenuItems(menu,thePanel);
        add(menu);
    }

    /**
     * @param theMenu
     * @param theFrame1
     */
    private void addFileMenuItems(final JMenu theMenu, final JFrame theFrame2,
                                  final DrawingPanel thePanel1) {
        final JMenuItem clear = new JMenuItem("Clear");
        clear.setMnemonic(KeyEvent.VK_C);
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                thePanel1.removeAll();
            }   
        });  
        theMenu.add(clear);
        theMenu.addSeparator();
        final JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_X);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                theFrame2.dispatchEvent(new WindowEvent(theFrame2,
                                                        WindowEvent.WINDOW_CLOSING));
            } 
        });
        theMenu.add(exit);
    }

    /**
     * @param theMenu1
     * @param thePanel2
     */
    private void addOptionsMenuItems(final JMenu theMenu1, final DrawingPanel thePanel2,
                                     final BrushProperties theBrushProperties) {
        final JCheckBoxMenuItem grid = new JCheckBoxMenuItem("Grid");
        grid.setMnemonic(KeyEvent.VK_G);
        grid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                

            }  
        });
        theMenu1.add(grid);

        theMenu1.addSeparator();
        final JMenu thicknessMenu = new JMenu("Thickness");
        thicknessMenu.setMnemonic(KeyEvent.VK_T);


        final JSlider thicknessSlider = new JSlider();
        thicknessSlider.setName("Thickness");
        thicknessSlider.setMaximum(MAX_THICKNESS);
        thicknessSlider.setMajorTickSpacing(MAJOR_TICKS_SPACING);
        thicknessSlider.setMinorTickSpacing(MINOR_TICKS_SPACING);
        thicknessSlider.setValue(MAJOR_TICKS_SPACING);
        thicknessSlider.setPaintTicks(true);
        thicknessSlider.setPaintLabels(true);
        thicknessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent arg0) {

                //Do something

            }
        });
        thicknessMenu.add(thicknessSlider);
        theMenu1.add(thicknessMenu);    
    }

    /**
     * @param theMenu2
     * @param thePanel3
     */
    private void addToolsMenuItems(final JMenu theMenu2, final DrawingPanel thePanel3) {
        final JMenuItem color = new JMenuItem("Color...");
        color.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }
            
        });
        myToolsMenu.add(color);
        myToolsMenu.addSeparator();       
    }
   
    /**
     * @param theMenu3
     * @param thePanel4
     */
    private void addHelpMenuItems(final JMenu theMenu3, final DrawingPanel thePanel4) {
        theMenu3.setMnemonic(KeyEvent.VK_H);
        final JMenuItem about = new JMenuItem("About...");
        about.setMnemonic(KeyEvent.VK_A);
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(thePanel4, "TCSS PowerPaint - Autumn 2014"
                                                + "\nAuthor: Trung Dang"
                                                + "\nIntructor: Alan Fowler"
                                                + "\nVersion: 1.11.07");
            }
        });
        theMenu3.add(about);
    }  
    /**
     * @param theAction
     */
    public void createMenuButtons(final Action theAction) {
        final JRadioButtonMenuItem button = new JRadioButtonMenuItem(theAction);
        myButtonGroup.add(button);
        myToolsMenu.add(button);
    }
}
