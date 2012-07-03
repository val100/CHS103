/*
 * LocaleChangeEvent.java
 *
 * Created on July 20, 2010, 11:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package cs103.gui;

import java.util.EventObject;
import java.util.Locale;

/**
 *
 * @author valery
 */
public class LocaleChangeEvent extends EventObject {

    private Locale locale;

    /**
     * Creates a new instance of LocaleChangeEvent.
     */
    public LocaleChangeEvent(final Object source) {
        super(source);
    }

    public LocaleChangeEvent(final Object source, final Locale locale) {
        super(source);
        this.locale = locale;
    }

    /**
     * Return locale.
     *
     * @return locale the locale
     */
    public final Locale getLocale() {
        return locale;
    }
}
