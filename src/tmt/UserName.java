/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmt;

import com.sun.java.swing.plaf.windows.resources.windows;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.platform.win32.Secur32;
import com.sun.jna.platform.win32.Secur32.EXTENDED_NAME_FORMAT;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;
import java.lang.Object;

/**
 *
 * @author atul
 */
public class UserName {

    /*public interface Secur32 extends StdCallLibrary {

        Secur32 INSTANCE = (Secur32) Native.loadLibrary("Secur32", Secur32.class);

        public boolean GetUserNameEx(int nameFormat, char[] lpNameBuffer, IntByReference len);
    };*/
   

    public static String getCurrentUserName() {
        String userName ;

        IntByReference len = new IntByReference(1024);
        char[] name = new char[100];


        Secur32.INSTANCE.GetUserNameEx(2, name, len);


        userName = Native.toString(name);

        System.out.println(userName);

        return userName;
    }
}
