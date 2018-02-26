/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innerDSL;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * QuickGUI allows concise description of a simple GUI (one frame, buttons, labels, organized into panels)
 * to be instantiated as a GUI that can be used from the application.
 * @author ups
 */
public class QuickGUI {

    /**
     * Builder class: provides convenience methods for constructing a GUI metamodel
     * @author ups
     */
    public static abstract class GUIModel {

        /**
         * The top-level frame
         */
        private QFrame top;
        /**
         * Create a frame with the given name and layout, containing the given nested model components,
         * and store it inside the builder
         * @param name title of the frame
         * @param layout layout used in the implicitly immediately nested panel
         * @param img jframe icon
         * @param color color of window
         * @param components the GUI components nested inside
         * @return the frame model
         */
        public QFrame frame(String name, int x, int y, ImageIcon img, Color color, QComponent ... components) {
            QPanel p = panel(x,y,color,components);
            QFrame frame = new QFrame(name,img,color);
            frame.add(p);
            top = frame;
            return frame; 
        }
        /**
         * Crate a panel with the given layout and the given nested components
         * @param layout the layout used in the panel
         * @param components the model GUI components nested inside
         * @return the panel model
         */
        public QPanel panel(int x, int y,Color color, QComponent ... components) {
            QPanel panel = new QPanel(x,y,color);
            for(QComponent c: components) panel.add(c);
            return panel; 
        }
        /**
         * Create a textArea with the given properties
         * @param spec the properties specifying the textArea
         * @return the textArea model
         */
          public QElement textArea(Parameter ... spec) { 
            return new QElement(QElement.Kind.TEXTAREA,spec);
        }
        /**
         * Create a button with the given properties
         * @param spec the properties specifying the button
         * @return the button model
         */
        public QElement button(Parameter ... spec) { 
            return new QElement(QElement.Kind.BUTTON,spec);
        }
        /**
         * Create a label with the given properties
         * @param spec the properties specifying the label
         * @return the label model
         */
        public QElement label(Parameter ... spec) { 
            return new QElement(QElement.Kind.LABEL,spec);
        }
        /**
         * Create a text property, typically visible in the GUI
         * @param text the property value
         * @return the property model
         */
        public Parameter text(String text) { return new Parameter(Parameter.Kind.TEXT,text); }
        /**
         * Create a name property, used internally to identify GUI elements
         * @param name the property name
         * @return the property model
         */
        public Parameter name(String name) { return new Parameter(Parameter.Kind.NAME,name); }
        /**
         * Get the top-level frame
         * @return the top-level frame model
         */
        public QFrame getTop() { build(); return (QFrame)top; }
        /**
         * Build a gui model, must have one and only one top-level frame
         */
        public abstract void build();
    }
    
    private JFrame frame;
    //to close the frame
    public void closeFrame(){
        frame.dispose();
    }

    /**
     * Map from names to corresponding Swing components
     */
    private Map<String,JComponent> componentMap = new HashMap<String,JComponent>();
    
    /**
     * Instantiate a Swing GUI based on the given model
     * @param model the model to instantiate from
     * @param handler the handler to use when handling GUI events
     */
    public QuickGUI(final GUIModel model, final ActionListener handler) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                buildGUI(model.getTop(),handler);
            }
        }); 
    }

    /**
     * Does the actual task of building the GUI for the given model and handler 
     * @param model to build the GUI from
     * @param handler for handling events
     */
    private void buildGUI(QFrame model, ActionListener handler) {
        // Create and set up the window.
        frame = new JFrame(model.getName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1280, 820);
        // Content must be a panel
        QPanel panel = (QPanel)model.getContents().get(0);
        JPanel gui = panel.create(handler,componentMap);
        //Create and set up the content pane.
        frame.setContentPane(gui);
        gui.setOpaque(true);
         //Display the window.
        frame.setVisible(true);
    }
    
    /**
     * Get the component with a given name
     * @param name the name of the component to find
     * @return the component with matching name, if any, signalling an error otherwise
     */
    public JComponent getComponent(String name) {
        JComponent component = componentMap.get(name);
        if(component==null) throw new Error("Component not found: "+name);
        return component;
    }
}
