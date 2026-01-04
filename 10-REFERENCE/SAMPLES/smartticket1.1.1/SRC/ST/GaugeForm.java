/*
 * Copyright 1999-2002 Sun Microsystems, Inc. ALL RIGHTS RESERVED
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 * OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 * FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */

package st;

import javax.microedition.midlet.*;
import java.io.*;
import javax.microedition.lcdui.*;

import shared.MessageConstants;

/**
 * A form with a gauge to show progress.
 */
public class GaugeForm extends Form implements CommandListener {

    /** Maximum value of this gauge. */
    public static final int GAUGE_MAX = 9;
    
    static final Command stopCmd = new Command(
        SmartTicket.getMsg(MessageConstants.STOP), 
	Command.CANCEL, 10);

    // The gauge indicating progress.
    Gauge gauge;
    
    // Can the progress be stopped?
    boolean stopped;
    
    /**
     * Constructs a new gauge.
     *
     * @param  title  the title of this gauge.
     * @param  stoppable  indicates whether the progress can be stopped.
     */
    public GaugeForm(String title, boolean stoppable) {
        super(title);
        gauge = new Gauge("", false, GAUGE_MAX, 0);
        append(gauge);
        
        if (stoppable) {
            addCommand(stopCmd);
            setCommandListener(this);
        }
    }

    /**
     * Initializes this gauge.
     *
     * @param  title  the title of this gauge.
     * @param  stoppable  indicates whether the progress can be stopped.
     */
    public void init(String title, boolean stoppable) { 
        setTitle(title);
        stopped = false;
        gauge.setValue(0);
        
        if (stoppable) {
            addCommand(stopCmd);
            setCommandListener(this);
        } else {
            removeCommand(stopCmd);
            setCommandListener(this);
        }
    }
    
    /**
     * Update the gauge with the given value.
     *
     * @param  i  the new value for the gauge (between 0 and MAX_VALUE
     *            inclusive).
     */
    public void update(int i) throws ApplicationException {
        if (stopped) {
            throw new ApplicationException();
        }
        gauge.setValue(i);
    }
    
    /* Inherited methods. */

    public void commandAction(Command c, Displayable d) {
        stopped = true;
    }
}
