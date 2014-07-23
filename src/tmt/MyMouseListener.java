/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmt;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

/**
 *
 * @author atul
 */
public class MyMouseListener implements NativeMouseMotionListener {



    Time keyBoardTime;

    public MyMouseListener(Time keyBoardTime) {
        this.keyBoardTime = keyBoardTime;
    }

    public void nativeMouseMoved(NativeMouseEvent nme) {
        if (!keyBoardTime.isEnabled()) {
            keyBoardTime.setEnabled(true);
        }
        TimeTracker.handleResetOnMouseMotion();
        keyBoardTime.reset();
        
    }

    public void nativeMouseDragged(NativeMouseEvent nme) {
        
    }
}
