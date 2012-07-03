/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cs103.gui;

/**
 *
 * @author <a href=mailto:valery@agrologic.com>Valery Manakhimov  (valery)</a>
 * @version $CellgateConnector: 1.0 $
 */
import java.awt.*;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Windows {
    public static void centerOnScreen(Window window) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(
            (d.width - window.getSize().width) / 2,
            (d.height - window.getSize().height) / 2);
    }

    public static void setWindowsLAF(Window window) {
        String laf = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        try {
            UIManager.setLookAndFeel(laf);
            SwingUtilities.updateComponentTreeUI(window);
          } catch (Exception ex) {
          }
    }
}