package examples.aaronhoskins.com.multithreadingexample;

import android.util.Log;

public class ExampleRunnable implements Runnable {
    String threadID;
    Callback callback;
    public ExampleRunnable(String threadID, Callback callback) {
        this.threadID = threadID;
        this.callback = callback;
    }

    @Override
    public void run() {
        Log.d(threadID, "HELLO WORLD!!!");
        callback.onResult("Thread Completed " + threadID);
    }
}
