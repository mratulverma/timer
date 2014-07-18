/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmt;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;

/**
 *
 * @author atul
 */
public class JNA_Kernel {
    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary)
            Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"),
                               CLibrary.class);

        void printf(String format, Object... args);
    }

    public static void main(String[] args) {
//        CLibrary.INSTANCE.printf("Hello, World\n");
//        for (int i=0;i < args.length;i++) {
//            CLibrary.INSTANCE.printf("Argument %d: %s\n", i, args[i]);
//        }
        Kernel32 lib = Kernel32.INSTANCE;
        System.out.println(lib.GetCurrentProcessId());
    }


}
