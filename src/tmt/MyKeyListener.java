/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmt;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author atul
 * @version 1.1
 */
class MyKeyListener implements NativeKeyListener{

        Time keyBoardTime;
        static int lastKeyCode;

        public MyKeyListener(Time keyBoardTime){
            this.keyBoardTime = keyBoardTime;
        }

        public void nativeKeyPressed(NativeKeyEvent nke) {
            if(!keyBoardTime.isEnabled()){
                keyBoardTime.setEnabled(true);
            }
            Timer_frame.activateOnKeyPress();
            keyBoardTime.reset();
            System.out.println("Key Pressed Event ----> " + NativeKeyEvent.getKeyText(nke.getKeyCode()));
        }

        public void nativeKeyReleased(NativeKeyEvent nke) {
            System.out.println("Key Released Event ----> " + NativeKeyEvent.getKeyText(nke.getKeyCode()));
        }

        public void nativeKeyTyped(NativeKeyEvent nke) {
            System.out.println("Key Typed Event ----> " + nke.getKeyText(nke.getKeyCode()));
        }
      
    }