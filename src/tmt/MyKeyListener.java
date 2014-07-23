/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmt;

import java.util.Timer;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author atul
 * @version 1.1
 */
class MyKeyListener implements NativeKeyListener {

    Time keyboardTime;
    String lastKeyCode = null;
    int counter = 0;

   

    MyKeyListener(Time KEYBOARD_MOUSE_TIME) {
        this.keyboardTime = KEYBOARD_MOUSE_TIME;
    }

    public void nativeKeyPressed(NativeKeyEvent nke) {
        if (!keyboardTime.isEnabled()) {
            keyboardTime.setEnabled(true);
        }
        System.out.println("Key Pressed Event ----> " + NativeKeyEvent.getKeyText(nke.getKeyCode()));
        if (lastKeyCode == null) {
            lastKeyCode = "" + nke.getKeyCode();
            counter++;
        }
        if (counter <= 50) {
            counter++;
            TimeTracker.handleResetOnKeyPressed();
            keyboardTime.reset();
            System.out.println(counter);
        }
        if (counter == 51) {
            TimeTracker.handleFoolingByUser();
            System.out.println("You r not Working");
            keyboardTime.reset();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent nke) {
        System.out.println("Key Released Event ----> " + NativeKeyEvent.getKeyText(nke.getKeyCode()));
        TimeTracker.handleResetOnKeyPressed();
    }

    public void nativeKeyTyped(NativeKeyEvent nke) {
        System.out.println("Key Typed Event ----> " + nke.getKeyText(nke.getKeyCode()));
    }
}
