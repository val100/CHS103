/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs103.gui;

import javax.swing.JToggleButton;
import javax.swing.event.MouseInputAdapter;

/**
 * Title: ShowGraphEvent <br> Decription: <br> Copyright: Copyright (c) 2008 <br> Company: Agro Logic LTD. <br>
 *
 * @author Valery Manakhimov <br>
 * @version 1.0 <br>
 */
public class ShowGraphEvent extends MouseInputAdapter {

    public int i;
    public JToggleButton button;

    public ShowGraphEvent(int i, JToggleButton button) {
        this.i = i;
        this.button = button;
    }
}
