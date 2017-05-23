package com.noname.tmvien.kanjicards.utils;


import android.content.Context;

import com.noname.tmvien.kanjicards.ui.ApplicationConfigureActivity;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created by tmvien on 4/25/17.
 */

public class Log {
    private static int releaseLogLevel = ApplicationConfigureActivity.DEBUG_MODE ? android.util.Log.DEBUG : android.util.Log.INFO;
    private static FileHandler logFileHandler;

    public static synchronized void initLogFileHandler(Context context) {
        if (logFileHandler == null) {
            File target = new File(context.getExternalFilesDir(null), "log_%g.txt");
            try {
                logFileHandler = new FileHandler(target.getAbsolutePath(), 1024 * 1024, 10, true);
                logFileHandler.setFormatter(new KanjiCardsLogFileFormatter());
                logFileHandler.publish( new LogRecord(Level.ALL, "file logger initialized:" + target.getAbsolutePath()));
                Log.i("ProgOffice_LOGGER", "file logger initialized:" + target.getAbsolutePath());
            } catch (Exception e) {
                Log.e("ProgOffice_LOGGER", "file logger initialize failed", e);
            }
        }
    }

    public static int d(String tag, String msg) {
        return println(android.util.Log.DEBUG, tag, msg);
    }

    public static int e(String tag, String msg) {
        return println(android.util.Log.ERROR, tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (android.util.Log.ERROR >= releaseLogLevel) {
            publishToLogFile(tag + ": " + msg, tr);
            return android.util.Log.e(tag, msg, tr);
        }
        return 0;
    }

    public static int i(String tag, String msg) {
        return println(android.util.Log.INFO, tag, msg);
    }

    public static int v(String tag, String msg) {
        return println(android.util.Log.VERBOSE, tag, msg);
    }

    public static int w(String tag, String msg) {
        return println(android.util.Log.WARN, tag, msg);
    }

    public static int println(int priority, String tag, String msg) {
        if (priority >= releaseLogLevel) {
            publishToLogFile(tag + ": " + msg);
            return android.util.Log.println(priority, tag, msg);
        }
        return 0;
    }

    private static void publishToLogFile(String message) {
        if (logFileHandler == null) {
            return;
        }
        try {
            logFileHandler.publish(new LogRecord(Level.ALL, message));
        } catch (Exception e) {
        }
    }

    private static void publishToLogFile(String message, Throwable th) {
        if (logFileHandler == null) {
            return;
        }
        try {
            LogRecord record = new LogRecord(Level.ALL, message);
            record.setThrown(th);
            logFileHandler.publish(record);
        } catch (Exception e) {
        }
    }

    static class KanjiCardsLogFileFormatter extends Formatter {

        @Override
        public String format(LogRecord r) {
            StringBuilder sb = new StringBuilder();
            sb.append(MessageFormat.format("{0, date} {0, time} ",
                    new Object[]{new Date(r.getMillis())}));
            sb.append(r.getLevel().getName()).append(": ");
            sb.append(formatMessage(r)).append("\n");
            if (r.getThrown() != null) {
                sb.append("Throwable occurred: ");
                Throwable t = r.getThrown();
                PrintWriter pw = null;
                try {
                    StringWriter sw = new StringWriter();
                    pw = new PrintWriter(sw);
                    t.printStackTrace(pw);
                    sb.append(sw.toString());
                } finally {
                    try {
                        if (pw != null) {
                            pw.close();
                        }
                    } catch (Exception e) {}
                }
            }
            return sb.toString();
        }
    }
}
