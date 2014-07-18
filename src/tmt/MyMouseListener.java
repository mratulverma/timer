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
public class MyMouseListener implements NativeMouseMotionListener{
    Time keyBoardTime;

    MyMouseListener(Time keyBoardTime) {
        this.keyBoardTime = keyBoardTime;
    }
   
    public void nativeMouseMoved(NativeMouseEvent nme) {
          if(!keyBoardTime.isEnabled()){
                keyBoardTime.setEnabled(true);
    } 
          Timer_frame.activateOnMouseMotion();
            keyBoardTime.reset();
            System.out.println("mouse is moved --->" + NativeMouseEvent.NATIVE_MOUSE_MOVED);
 }
    public void nativeMouseDragged(NativeMouseEvent nme) {
         System.out.println("mouse is moved --->" + NativeMouseEvent.NATIVE_MOUSE_DRAGGED);
    }
}
