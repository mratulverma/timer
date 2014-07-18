/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmt;

import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author atul
 */
public class MyWindowName {

    public interface Kernel32 extends StdCallLibrary {

        Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);

        /**
         * Retrieves the number of milliseconds that have elapsed since the system was started.
         * @see http://msdn2.microsoft.com/en-us/library/ms724408.aspx
         * @return number of milliseconds that have elapsed since the system was started.
         */
        public WinNT.HANDLE OpenProcess(int fdwAccess, boolean fInherit, int IDProcess);
    };

    public interface User32 extends StdCallLibrary {

        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

        public WinDef.HWND GetForegroundWindow();

        public int GetWindowThreadProcessId(WinDef.HWND handle, IntByReference processId);

        /**
         * Contains the time of the last input.
         * @see http://msdn.microsoft.com/library/default.asp?url=/library/en-us/winui/winui/windowsuserinterface/userinput/keyboardinput/keyboardinputreference/keyboardinputstructures/lastinputinfo.asp
         */
        public static class LASTINPUTINFO extends Structure {

            public int cbSize = 8;
            /// Tick count of when the last input event was received.
            public int dwTime;

            @Override
            protected List getFieldOrder() {
                return Arrays.asList(new String[]{"cbSize", "dwTime"});
            }
        }

        /**
         * Retrieves the time of the last input event.
         * @see http://msdn.microsoft.com/library/default.asp?url=/library/en-us/winui/winui/windowsuserinterface/userinput/keyboardinput/keyboardinputreference/keyboardinputfunctions/getlastinputinfo.asp
         * @return time of the last input event, in milliseconds
         */
        public boolean GetLastInputInfo(LASTINPUTINFO result);
    };

    public interface PsApi extends StdCallLibrary {

        int GetModuleFileNameExA(HANDLE process, HANDLE module,
                byte[] name, int i);
    }

    /**
     * Get the amount of milliseconds that have elapsed since the last input event
     * (mouse or keyboard)
     * @return idle time in milliseconds
     */
    public static String getCurrentWindowName() {
        String windowName = "self";
        try {
            PsApi psapi = (PsApi) Native.loadLibrary("psapi", PsApi.class);

            byte[] name = new byte[1024];
            WinDef.HWND currentWindowHandle = User32.INSTANCE.GetForegroundWindow();
            IntByReference pidRef = new IntByReference();
            User32.INSTANCE.GetWindowThreadProcessId(currentWindowHandle, pidRef);


            HANDLE process = Kernel32.INSTANCE.OpenProcess(0x0400 | 0x0010, false, pidRef.getValue());
            psapi.GetModuleFileNameExA(process, null, name, 1024);
            windowName = Native.toString(name);
           
            windowName = windowName.substring(windowName.lastIndexOf("\\") + 1);
        } catch (Exception ex) {
            return windowName;
        }
        return windowName;

    }

    ;
}
