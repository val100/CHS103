/*
 * CelRenderer.java
 *
 * Created on 7 ιεμι 2007, 08:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package cs103.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Alternate colors of each row
 *
 * @author Johanan
 */
public class CellRenderer extends DefaultTableCellRenderer {

    Color even = new Color(255, 255, 255);
    Color odd = new Color(255, 255, 170);
    Color system = new Color(236, 233, 216);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (column != 0) {
            setBackground(row % 2 == 0 ? even : odd);
            setFont(new Font("Tahoma-Bold", Font.PLAIN, 12));
        } else {
            setBackground(system);
            setFont(new Font("Tahoma-Bold", Font.PLAIN, 12));
        }
        if (isSelected) {
            if (column == 0) {
                //setBackground( Color.yellow );
                setForeground(Color.black);
            } else {
                setBackground(table.getSelectionBackground());
            }
        }
        return this;
    }
}