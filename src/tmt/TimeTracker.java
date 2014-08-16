/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmt;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 *
 * @author atul
 */
public class TimeTracker {

    static final String GLOBAL_TIME = "timetracker_global";
    static final String ACTIVE_TIME = "timetracker_active";
    static final String PASSIVE_TIME = "timetracker_passive";
    static final String KEYBOARD_MOUSE_TIME = "timetracker_keyboard_mouse";
    static final long START_DELAY = 1000;
    static final long TIMER_INTERVAL = 1000;
    private static int INACTIVE_TIME = 15;
    static HashMap timeTrackerMap = new HashMap();
    private static boolean tracking = false;
    private static Timer timer;
    private static TimerTask timerTask;
    private static String previousAppName = null;
    private static String currentAppName;
    private static InetAddress ip;

    public static void startTimeTracker() throws SocketException, UnknownHostException {
        if (!tracking) {
            tracking = true;
            
            try {
                GlobalScreen.registerNativeHook();
                GlobalScreen globalScreen = GlobalScreen.getInstance();
                initializeRoutine();
                globalScreen.addNativeKeyListener(new MyKeyListener(getTimeByKey(KEYBOARD_MOUSE_TIME)));
                globalScreen.addNativeMouseMotionListener(new MyMouseListener(getTimeByKey(KEYBOARD_MOUSE_TIME)));
                String userName = UserName.getCurrentUserName();
                ip = InetAddress.getLocalHost();
		System.out.println("Current IP address : " + ip.getHostAddress());
 
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
 
		byte[] mac = network.getHardwareAddress();
 
		System.out.print("Current MAC address : ");
 
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
		}
		System.out.println(sb.toString());
            
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date));
            } catch (NativeHookException ex) {
                System.out.println("Failed to register Keyboard & Mouse Listeners");
                ex.printStackTrace();
                
            }
        } else {
            //throw new TimeTrackerRunningException();
        }
    }

    private static void initializeRoutine() {
        timeTrackerMap.put(GLOBAL_TIME, new Time(true));
        timeTrackerMap.put(ACTIVE_TIME, new Time(true));
        timeTrackerMap.put(PASSIVE_TIME, new Time(false));
        timeTrackerMap.put(KEYBOARD_MOUSE_TIME, new Time(true));

        timer = new Timer();
        timerTask = new MyTimerTask();
        timer.schedule(timerTask, START_DELAY, TIMER_INTERVAL);

    }

    public static HashMap getTimeTrackerMap() {
        return timeTrackerMap;
    }

    public static Time getTimeByKey(String key) {
        return (Time) timeTrackerMap.get(key);
    }

    public static boolean isTimeExists(String key) {
        return timeTrackerMap.containsKey(key);
    }

    public static boolean isTracking() {
        return tracking;
    }

    private static void handleApplicationTime() {
        if (getTimeByKey(ACTIVE_TIME).isEnabled()) {
            currentAppName = MyWindowName.getCurrentWindowName();
            if (previousAppName == null) {
                previousAppName = currentAppName;
            }
            if (!isTimeExists(currentAppName)) {
                timeTrackerMap.put(currentAppName, new Time());
                System.out.println("New Timer Created for - " + currentAppName);
            }

            if (isTimeExists(currentAppName)) {
                System.out.println("Timer found for - " + currentAppName);

                if (!currentAppName.equals(previousAppName)) {
                    getTimeByKey(previousAppName).setEnabled(false);
                    previousAppName = currentAppName;
                }

                if (!getTimeByKey(currentAppName).isEnabled()) {
                    getTimeByKey(currentAppName).setEnabled(true);
                }
            }
        }
    }

    public static void handleKeyboardAndMouseLogic() {
        if (getTimeByKey(KEYBOARD_MOUSE_TIME).getSeconds() == INACTIVE_TIME) {
            System.out.println("you are not working");
            getTimeByKey(ACTIVE_TIME).decrementBy(INACTIVE_TIME);
            getTimeByKey(currentAppName).decrementBy(INACTIVE_TIME);
            getTimeByKey(PASSIVE_TIME).incrementBy(INACTIVE_TIME);
            getTimeByKey(KEYBOARD_MOUSE_TIME).reset();
            getTimeByKey(PASSIVE_TIME).setEnabled(true);
            getTimeByKey(ACTIVE_TIME).setEnabled(false);
            getTimeByKey(currentAppName).setEnabled(false);
            getTimeByKey(KEYBOARD_MOUSE_TIME).setEnabled(false);
        }
    }

    public static void handleFoolingByUser() {
        if (!getTimeByKey(PASSIVE_TIME).isEnabled()) {
            getTimeByKey(PASSIVE_TIME).setEnabled(true);
        }
        if (getTimeByKey(ACTIVE_TIME).isEnabled() && getTimeByKey(currentAppName).isEnabled()) {
            getTimeByKey(ACTIVE_TIME).setEnabled(false);
            getTimeByKey(currentAppName).setEnabled(false);
        }

    }

    public static void handleResetOnKeyPressed() {
        if (!getTimeByKey(ACTIVE_TIME).isEnabled() && !getTimeByKey(currentAppName).isEnabled()) {
            getTimeByKey(ACTIVE_TIME).setEnabled(true);
            getTimeByKey(currentAppName).setEnabled(true);
        }
        if (getTimeByKey(PASSIVE_TIME).isEnabled()) {
            getTimeByKey(PASSIVE_TIME).setEnabled(false);
        }
    }

    public static void handleResetOnMouseMotion() {
        if (!getTimeByKey(ACTIVE_TIME).isEnabled()) {
            getTimeByKey(ACTIVE_TIME).setEnabled(true);
        }
        if (getTimeByKey(PASSIVE_TIME).isEnabled()) {
            getTimeByKey(PASSIVE_TIME).setEnabled(false);
        }
    }

    static class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            handleKeyboardAndMouseLogic();
            handleApplicationTime();
            handleKeyboardAndMouseLogic();

            for (Object key : timeTrackerMap.keySet()) {
                getTimeByKey(key.toString()).increment();
                System.out.println(key.toString() + " -- " + getTimeByKey(key.toString()));
            }

        }
    }
}
