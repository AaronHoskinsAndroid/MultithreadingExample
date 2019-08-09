package examples.aaronhoskins.com.multithreadingexample;

import android.os.AsyncTask;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class ExampleAsyncTask extends AsyncTask<Void, String, String> {
    public static final String TAG = "TAG_ASYNC";
    private int timesToRun;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "Setting up task");
        timesToRun = 5;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = "";
        for(int i = 0 ; i < timesToRun ; i++) {
            result = result + "-- RUN "  +i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(result);
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.d(TAG, values[0]);

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        EventBus.getDefault().post(new MessageEvent(s));
    }
}
