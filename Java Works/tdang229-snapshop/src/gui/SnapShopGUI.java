/*
 * TCSS 305 � Autumn 2014 
 * Assignment 4 - SnapShop
 */

package gui;

import filters.EdgeDetectFilter;
import filters.EdgeHighlightFilter;
import filters.Filter;
import filters.FlipHorizontalFilter;
import filters.FlipVerticalFilter;
import filters.GrayscaleFilter;
import filters.SharpenFilter;
import filters.SoftenFilter;
import image.PixelImage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class creates a GUI to manipulate images. The GUI consists of 7 buttons each associated
 * <br> with a filter and 3 buttons corresponding to Open, Save, and Close image. The effects 
 * <br> of filters are cumulative.
 * 
 * @author Trung Dang
 * @version October 26, 2014
 */
public class SnapShopGUI {
    /** A toolkit.*/
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();

    /** Hold the screen size of the current display.*/
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    /** Hold the width of the screen.*/
    private static final int SCREEN_WIDTH = SCREEN_SIZE.width;

    /** Hold the height of the screen.*/
    private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;

    /** A constant for Open button's name.*/
    private static final String OPEN = "Open";
    
    /** A constant for Save as button's name.*/
    private static final String SAVE_AS = "Save as...";
    
    /** A constant for Close image button's name.*/
    private static final String CLOSE_IMAGE = "Close image";

    /** A scale used to set the frame relative to the display size.*/
    private static final int SCALE = 3;

    /** A field to hold all others components.*/
    private final JFrame myFrame;

    /** A label will be used to pub the image into.*/
    private final JLabel myLabel;

    /** The top component of myFrame which is BorderLayout.NORTH.*/
    private final JPanel myTop;

    /** The bottom component of myFrame which is BorderLayout.SOUTH.*/
    private final JPanel myBottom;

    /** A file chooser to select the image file as well as save an image file. */
    private final JFileChooser myFileChooser;

    /** An image that will be filtered. */
    private PixelImage myFilteredImage;

    /**
     * Default constructor for SnapShopGUI. Take no arguments.
     */
    public SnapShopGUI() {
        myFileChooser = new JFileChooser(".");
        myLabel = new JLabel();
        myTop = new JPanel();
        myBottom = new JPanel();
        myFrame = new Frame();
    }

    /**
     * This method will be called my SnapShopMain. 
     */
    public void start() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                myFrame.setVisible(true);
            }
        });
    }

    /**
     * This class creates a JFrame that contains all 10 buttons ant their associated listeners.
     * 
     * @author Trung Dang
     * @version October 26, 2014
     */
    @SuppressWarnings("serial")
    class Frame extends JFrame {
        
        /** A button for EdgeDetectFilter. */
        private JButton myEdgeDetect;
        
        /** A button for EdgeHighlightFilter. */
        private JButton myEdgeHighlight;
        
        /** A button for flipHorizontalFilter. */
        private JButton myFlipHorizontal;
        
        /** A button for flipVerticalFilter. */
        private JButton myFlipVertical;
        
        /** A button for grayScaleFilter. */
        private JButton myGrayScale;
        
        /** A button for sharpenFilter. */
        private JButton mySharpen;
        
        /** A button for softenFilter. */
        private JButton mySoften;
        
        /** A button for Open dialog. */
        private JButton myOpen;
        
        /** A button for Save as dialog. */
        private JButton mySaveAs;
        
        /** A button for Close image dialog. */
        private JButton myCloseImage;
        
        /**
         * A constructor that initializes a frame at the center of user's screen.
         */
        public Frame() {
            super("TCSS 305 SnapShop");
            super.setDefaultCloseOperation(EXIT_ON_CLOSE);
            super.setSize(SCREEN_WIDTH / SCALE, SCREEN_HEIGHT / SCALE);
            populateFrame();
            super.pack();
            super.setLocation(SCREEN_WIDTH / 2 - getWidth() / 2, SCREEN_HEIGHT / 2
                                                                 - getHeight() / 2);
        }

        /**
         * A helper method to add all the components to myFrame.
         */
        private void populateFrame() {
            
            myEdgeDetect = createFilterButton(new EdgeDetectFilter());
            myEdgeHighlight = createFilterButton(new EdgeHighlightFilter());
            myFlipHorizontal = createFilterButton(new FlipHorizontalFilter());
            myFlipVertical = createFilterButton(new FlipVerticalFilter());
            myGrayScale = createFilterButton(new GrayscaleFilter());
            mySharpen = createFilterButton(new SharpenFilter());
            mySoften = createFilterButton(new SoftenFilter());
            
            myTop.add(myEdgeDetect);
            myTop.add(myEdgeHighlight);
            myTop.add(myFlipHorizontal);
            myTop.add(myFlipVertical);
            myTop.add(myGrayScale);
            myTop.add(mySharpen);
            myTop.add(mySoften);
            add(myTop, BorderLayout.NORTH);

            myOpen = createButton(OPEN);
            mySaveAs = createButton(SAVE_AS);
            myCloseImage = createButton(CLOSE_IMAGE);
            myBottom.add(myOpen);
            myBottom.add(mySaveAs);
            myBottom.add(myCloseImage);
            add(myBottom, BorderLayout.SOUTH);

            myLabel.setLayout(new BorderLayout());
            
        }

        /**
         * Create filter buttons with associated filter names.
         * 
         * @param theFilter The filter object this button is associated with
         * @return A button associated with theFilter
         */
        private JButton createFilterButton(final Filter theFilter) {
            final JButton button = new JButton(theFilter.getDescription());
            button.setEnabled(false);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent theEvent) {
                    theFilter.filter(myFilteredImage);
                    myLabel.setIcon(new ImageIcon(myFilteredImage));
                }
            });
            return button;
        }

        /**
         * Create Open, Save as, and Close Image buttons.
         * 
         * @param theName The name of the button
         * @return A button with name theName
         */
        private JButton createButton(final String theName) {
            JButton button = null;
            button = new JButton(theName);
            if (theName.equals(OPEN)) {
                button.setEnabled(true);
            } else {
                button.setEnabled(false);
            }
            button.addActionListener(new ButtonListener());
            return button;
        }

        /**
         * This class creates ActionListener associated with Open, Save as, and Close image
         * <br>buttons.
         * 
         * @author Trung Dang
         * @version October 26, 2014
         */
        class ButtonListener implements ActionListener {
            
            /** The initial image when first loaded from a file.*/
            private PixelImage myImageFromFile;
            
            /**
             * @param theEvent The event this button generates (Open, Save as, or Close image)
             */
            public void actionPerformed(final ActionEvent theEvent) {
                final String buttonName = ((JButton) theEvent.getSource()).getText();
                if (buttonName.equals(OPEN)) {
                    final int val = myFileChooser.showOpenDialog(myFrame);
                    if (val == JFileChooser.APPROVE_OPTION) {
                        try {
                            myImageFromFile = PixelImage.load(myFileChooser.getSelectedFile());
                            myFilteredImage = PixelImage.load(myFileChooser.getSelectedFile());
                            final ImageIcon image = new ImageIcon(myImageFromFile);
                            myLabel.setHorizontalAlignment(JLabel.CENTER);
                            myLabel.setVerticalAlignment(JLabel.CENTER);
                            myLabel.setIcon(image);
                            myFrame.add(myLabel, BorderLayout.CENTER);
                            myFrame.pack();
                            myFrame.setLocation(SCREEN_WIDTH / 2 - myFrame.getWidth() / 2,
                                                SCREEN_HEIGHT / 2 - myFrame.getHeight() / 2);
                            
                            setButtonEnable(true);
                        } catch (final IOException e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Warning!",
                                                          JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else if (buttonName.equals(SAVE_AS)) {
                    if (myLabel.getIcon() == null) {
                        JOptionPane.showMessageDialog(myFrame, "There is no file to be saved",
                                                      "Error!", JOptionPane.ERROR_MESSAGE);
                    } else {
                        final int val = myFileChooser.showSaveDialog(myFrame);
                        if (val == JFileChooser.APPROVE_OPTION) {
                            actionConfirm(myFilteredImage, myFileChooser.getSelectedFile());
                        }
                    }

                } else if (buttonName.equals(CLOSE_IMAGE)) {
                    myLabel.setIcon(null);
                    myFilteredImage = null;
                    setButtonEnable(false);
                    myFrame.pack();
                }
            }

            /**
             * Do the appropriate actions when user clicks Open in JFileChooser dialog.
             * 
             * @param theFilteredImage An image after filtered (if there is no filter applied, 
             *        <br>the default is an image that has been opened).
             * @param theFile A file needs to be checked
             */
            private void actionConfirm(final PixelImage theFilteredImage, final File theFile) {
                int response = 0;
                if (theFile.exists()) {
                    response = JOptionPane.showConfirmDialog(myFileChooser, 
                                                             "File already exists. "
                                                    + "Overwrite existing file?",
                                                  "Warning", JOptionPane.OK_CANCEL_OPTION,
                                                  JOptionPane.WARNING_MESSAGE);
                }
                if (response == JOptionPane.OK_OPTION || !theFile.exists()) {
                    try {
                        theFilteredImage.save(theFile);
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            /**
             * Set all the buttons to their appropriate state (enabled or disabled).
             * 
             * @param theHasImage A boolean value indicating that there is an opened image
             */
            private void setButtonEnable(final boolean theHasImage) {
                if (theHasImage) {
                    myEdgeDetect.setEnabled(true);
                    myEdgeHighlight.setEnabled(true);
                    myFlipHorizontal.setEnabled(true);
                    myFlipVertical.setEnabled(true);
                    myGrayScale.setEnabled(true);
                    mySharpen.setEnabled(true);
                    mySoften.setEnabled(true);
                    mySaveAs.setEnabled(true);
                    myCloseImage.setEnabled(true);
                } else {
                    myEdgeDetect.setEnabled(false);
                    myEdgeHighlight.setEnabled(false);
                    myFlipHorizontal.setEnabled(false);
                    myFlipVertical.setEnabled(false);
                    myGrayScale.setEnabled(false);
                    mySharpen.setEnabled(false);
                    mySoften.setEnabled(false);
                    mySaveAs.setEnabled(false);
                    myCloseImage.setEnabled(false);
                }
            }
        } //end of class ButtonListener
    } //end of class Frame
}
