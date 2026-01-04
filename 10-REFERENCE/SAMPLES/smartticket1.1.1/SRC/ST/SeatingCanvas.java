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

import java.io.*;
import java.util.*;
import javax.microedition.midlet.*;
import javax.microedition.io.*;
import javax.microedition.lcdui.*;

import shared.MessageConstants;

/**
 * Paints the seating plan for a particular movie theater.  
 */
public class SeatingCanvas extends Canvas {

    // Often-used colors.
    static final int WHITE = 0xffffff;
    static final int RED = 0xff6d00;
    static final int GREEN = 0x006d55;
    static final int BLUE = 0x0000ff;
    static final int BURGUNDY = 0xb60055;

    // Seat status values.
    static final byte AISLE = 9;
    static final byte AVAILABLE = 0;
    static final byte UNAVAILABLE = 1;
    static final byte BOOKED = 2;

    Display display;
    Command reserveCmd;

    // Seats and the number of seats in a row.
    byte[] seats;
    int rowSize = 1;
    int currentSeat;
    int selectedSeats;
    
    // Off-screen buffer.
    Image buffer;
    
    // Canvas and individual seat dimensions.
    int canvasW, canvasH, seatH, seatW;    

    // Margins for the seating plan.
    int marginT, marginB, marginL, marginR;     

    // Is the cursor on?
    Timer timer;        
    boolean cursor;

    /**
     * SeatingCanvas constructor.
     *
     * @param d   the display object.
     */
    public SeatingCanvas(Display d, Command reserveCmd) {
        display = d;
	this.reserveCmd = reserveCmd;
    }

    /**
     * Initialize the seating canvas variables.
     *
     * @exception IOException  when some type of network error occurs.
     */
    public void init(byte[] seats, int rowSize, String movieTitle, 
                     String showtime) {
        
        this.seats = seats;
        this.rowSize = rowSize;
        selectedSeats = 0;
        removeCommand(reserveCmd);
    
        canvasW = getWidth();
        canvasH = getHeight();        
        buffer = Image.createImage(canvasW, canvasH);
        Graphics g = buffer.getGraphics();
        Font f = g.getFont();
        
        // Paint the canvas background.
        g.setColor(WHITE);
        g.fillRect(0, 0, canvasW, canvasH);

        // Draw static textual information on canvas.
        g.setColor(BURGUNDY);
        g.drawString(SmartTicket.getMsg(MessageConstants.SCREEN), 
		     canvasW, 0, Graphics.TOP|Graphics.RIGHT);
        g.drawString(movieTitle, 0, canvasH, 
		     Graphics.BASELINE|Graphics.LEFT);
        g.drawString(showtime, canvasW, canvasH, 
                     Graphics.BASELINE|Graphics.RIGHT);
        
        // Adjust left and top margins to center seat plan.
        marginT = f.getHeight() + (canvasH % (seats.length / rowSize))/2;
        marginB = f.getHeight();
        marginL = (canvasW % rowSize)/2;
        marginR = 0;

        // Calculate seat width and height.
        seatW = (canvasW - (marginL + marginR)) / rowSize;        
        seatH = (canvasH - (marginT + marginB)) / ((seats.length / rowSize));

        for (currentSeat = seats.length-1; 
             seats[currentSeat] != AVAILABLE;
             currentSeat--);
    
        // Draw seat plan.
        for (int j = 0; j < seats.length; j++) {
            drawSeat(j);
        }
        drawSeatID();
    }
    
    /** 
     * Draw individual seat with current status.
     *
     * @param  seat  the seat number to draw.
     */
    void drawSeat(int seat) { drawSeat(seat, -1); }

    /**
     * Draw individual seat with over-riding color.
     *
     * @param  seat  the seat number to draw.
     * @param  color the color to draw seat.
     */    
    synchronized void drawSeat(int seat, int color) {
        int x = getX(seat);
        int y = getY(seat);

        // If no color was specified, pick an appropriate color.
        if (color == -1) {
            switch (seats[seat]) {
                case AVAILABLE:
                    color = WHITE;
                    break;
                case BOOKED:
                    color = BLUE;
                    break;
                case UNAVAILABLE:
                    color = RED;
                    break;
                default:
                    return;
            }
        }
        
        // Paint the seat and draw its border.
        if (seats[seat] != AISLE) {
            Graphics g = buffer.getGraphics();
            g.setColor(color);
            g.fillRect(x, y, seatW, seatH);
            g.setColor(GREEN);
            g.drawRect(x, y, seatW, seatH);
            repaint(x, y, seatW, seatH);
        }
    }

    /**
     * Draw the position on the screen.
     */
    synchronized void drawSeatID() {
        Graphics g = buffer.getGraphics();
        Font f = g.getFont();
        String pos = "R" + (currentSeat / rowSize) 
                     + " S" + (currentSeat % rowSize);
        g.setColor(WHITE);
        g.fillRect(0, 0, f.stringWidth(pos + "  "), f.getHeight());
        g.setColor(BLUE);
        g.drawString(pos, 0, 0, Graphics.TOP|Graphics.LEFT);
        repaint(0, 0, f.stringWidth(pos + "  "), f.getHeight());
    }

    /**
     * Draw the cursor object.
     */
    void drawCursor() {
        if (cursor) {
            drawSeat(currentSeat, BURGUNDY);
        } else {
            drawSeat(currentSeat);
        }
    }
        
    /**
     * Book or unbook the given seat.
     */
    void toggleSeat(int seat) {

        if (seats[seat] == BOOKED) {
            seats[seat] = AVAILABLE;
            selectedSeats--;
        } else if (seats[seat] == AVAILABLE) {
            seats[seat] = BOOKED;
            selectedSeats++;
        } else {
            return;
        }

        if (selectedSeats > 0) {
	    addCommand(reserveCmd);
	} else {
	    removeCommand(reserveCmd);
	}
    }
    
    /**
     * Return the X coordinate of the current seat
     *
     * @param  seat  the seat.
     * @return position of X
     */
    int getX(int seat) { return marginL + (seat % rowSize) * seatW; }
    
    /**
     * Return the Y coordinate of the current seat
     *
     * @param  seat  the seat.
     * @return position of Y
     */
    int getY(int seat) { return marginT + (seat / rowSize) * seatH; }
    
    public int[] getSelectedSeats() { 
        int[] result = new int[selectedSeats];
        for (int i = 0, j = 0; i != seats.length; i++) {
            if (seats[i] == BOOKED) {
                result[j++] = i;
            }
        }
        return result;
    }
    
    // Overridden methods

    protected void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, Graphics.TOP|Graphics.LEFT);
    }   

    protected void keyPressed(int keyCode) {

        int action = getGameAction(keyCode);
        int x, y, delta, newSeat, oldSeat;
        
        switch (action) {
            case Canvas.LEFT: delta = -1; break;
            case Canvas.RIGHT: delta = 1; break;
            case Canvas.UP: delta = -rowSize; break;
            case Canvas.DOWN: delta = rowSize; break;
            case Canvas.FIRE: 
                delta = 0;
                toggleSeat(currentSeat);
                break;
            default: return;
        }
        
        oldSeat = currentSeat;
        newSeat = currentSeat;
        
        while ((newSeat += delta) >= 0 && newSeat < seats.length) {
            if (seats[newSeat] == AVAILABLE || seats[newSeat] == BOOKED) {
                currentSeat = newSeat;
                break;
            }
        }

        // Draw new seat and re-draw old seat.
        drawCursor();
        drawSeat(oldSeat);
        drawSeatID();
    }

    protected void pointerPressed(int x, int y) {

        // Get the clicked position based on x and y coordinates.
        int clicked = (((y - marginT)/seatH)*rowSize) + ((x - marginL)/seatW);
        if (clicked < 0 || clicked > seats.length) {
            return;
        }

        int oldSeat = currentSeat;
        if (seats[currentSeat] == AVAILABLE || seats[currentSeat] == BOOKED) {
            currentSeat = clicked;
            toggleSeat(currentSeat);

            // Draw new seat and re-draw old seat.
            drawCursor();
            drawSeat(oldSeat);
            drawSeatID();
        }
    }
    
    protected void showNotify() {

        // Start the timer for the cursor.
        timer = new Timer();
        TimerTask task = new TimerTask() {
                public void run() {
                    cursor = !cursor;
		    drawCursor();
                }
            }; 
        timer.schedule(task, 750, 750);
    }

    protected void hideNotify() { 

        // Stop the timer for the cursor.
        timer.cancel(); 
    }
}
