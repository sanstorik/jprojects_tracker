package native_hooks;

import activities.Activity;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Timer;
import java.util.TimerTask;

class ActivityFocusListener implements ListenerInitializer {
    private static class Psapi {
        static { Native.register("psapi"); }
        public static native int GetModuleBaseNameW(WinNT.HANDLE hProcess, Pointer hmodule, char[] lpBaseName, int size);
    }

    private Activity _activity;
    private Timer _timer;
    private TimerTask _timerTask;
    private String _lastFocus;

    private static final int DELAY = 0;
    /**delay between checkouts in ms*/
    private static final int PERIOD = 250;

    ActivityFocusListener(Activity activity) {
        _activity = activity;
        reinitializeTimer();
    }

    @Override public void init() {
        _timer.schedule(_timerTask, DELAY, PERIOD);
    }

    @Override public void disable() {
        _timer.cancel();
        _timer.purge();
        _timerTask.cancel();

        reinitializeTimer();
    }

    private void reinitializeTimer() {
        _timer = new Timer();
        _timerTask = new TimerTask() {
            @Override public void run() {
                String currFocus = getCurrentContextTitle();

                if (_lastFocus == null ||_lastFocus.equals(currFocus)) {
                    _activity.getFocusContextAnalyzer().addTimeSpentOnContext(currFocus, PERIOD);
                }

                _lastFocus = currFocus;
            }
        };
    }

    private String getCurrentContextTitle() {
        String contextName = "nothing";

        if (Platform.isWindows()) {
            final char[] buffer = new char[512];

            IntByReference pointerByReference = new IntByReference();
            User32.INSTANCE.GetWindowThreadProcessId(User32.INSTANCE.GetForegroundWindow(), pointerByReference);

            WinNT.HANDLE process = Kernel32.INSTANCE.OpenProcess(Kernel32.PROCESS_QUERY_INFORMATION | Kernel32.PROCESS_VM_READ,
                    false, pointerByReference.getValue());

            Psapi.GetModuleBaseNameW(process, null, buffer, 512);
            contextName = Native.toString(buffer);
        }
        if (Platform.isMac()) {
            final String script="tell application \"System Events\"\n" +
                    "\tname of application processes whose frontmost is tru\n" +
                    "end";

            ScriptEngine appleScript = new ScriptEngineManager().getEngineByName("AppleScript");
            try {
                contextName = appleScript.eval(script).toString();
            } catch (ScriptException e) { /*empty*/}
        }

        return contextName;
    }
}
