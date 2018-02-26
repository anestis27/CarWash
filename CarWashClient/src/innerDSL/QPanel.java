/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innerDSL;


import innerDSL.QuickGUI.GUIModel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;



/**
 * GUI metamodel: a nested panel organizing a number of nested components
 */
public class QPanel extends QComponent {
    /**
     * The layout of the panel
     */
    private int rows,cols;
    private Color color;
    /**
     * Create a panel model with the given layout
     * @param layout the layout of the panel
     */
    public QPanel(int r, int c,Color color) { 
        this.rows = r;
        this.cols = c;
        this.color = color;
    }
    /**
     * Create JPanel containing the nested components
     */
    public JPanel create(ActionListener handler, Map<String,JComponent> componentMap) {
        JPanel panel = new JPanel();
        panel.setLayout(getLayout(panel));
        panel.setBackground(color);
        for(QComponent c: contents) panel.add(c.create(handler,componentMap));
        return panel;
    }
    /**
     * Create layout manager
     * @param panel the panel to assign the given layout manager
     * @return the new layout manager
     */
    private LayoutManager getLayout(JPanel panel) {
        return new GridLayout(rows,cols);
    }
    /**
     * For debugging
     */
    @Override public String toString() {
        StringBuffer result = new StringBuffer("JPanel[");
        for(QComponent c: contents) result.append(c.toString()+",");
        result.append("]");
        return result.toString();
    }
}