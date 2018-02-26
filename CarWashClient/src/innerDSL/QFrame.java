/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innerDSL;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.ImageIcon;

import javax.swing.JComponent;

/**
 * GUI metamodel: a top-level frame
 */
public class QFrame extends QComponent {
    /**
     * The title of the frame
     */
    private String name;
    private ImageIcon img;
    private Color color;
    /**
     * Create frame model with the given title
     * @param name the title of the frame
     */
    public QFrame(String name, ImageIcon img, Color color) { 
        this.name = name; 
        this.img = img;
        this.color= color;
    }
    /**
     * Get the name (frame title)
     */
    public String getName() {
    	return name;
    }
    /**
     * Create Swing GUI, since frames cannot be nested it is an error to call this method on this object
     */
    @Override protected JComponent create(ActionListener handler, Map<String,JComponent> componentMap) {
        throw new Error("Nested frames not allowed");
    }
    /**
     * For debugging
     */
    @Override public String toString() {
        return "Frame("+contents.get(0)+")";
    }
}