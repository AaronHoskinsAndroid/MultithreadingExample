package examples.aaronhoskins.com.multithreadingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements Callback {
    static int thread = 0;
    TextView tvRunnableDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvRunnableDisplay = findViewById(R.id.tvRunnableDisplay);

        Runnable calcSomething = new Runnable() {
            @Override
            public void run() {
                int localId = thread;
                thread++;
                final String TAG = "TAG_" + localId;
                Log.d(TAG , "Begin Counter");
                for(int i = 0 ; i < 5 ; i++) {
                    Log.d(TAG, String.valueOf(i));
                    tvRunnableDisplay.setText("HERE");
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

//        Thread thread = new Thread(calcSomething);
//        Thread thread1 = new Thread(calcSomething);
//        thread.start();
        runOnUiThread(calcSomething);
//        thread1.start();
        Thread thread3 = new Thread(new ExampleRunnable("TAG_THREAD_01", this));
        thread3.start();

        ExampleAsyncTask exampleAsyncTask = new ExampleAsyncTask();
        exampleAsyncTask.execute();

        LooperDemoThread looperDemoThread;
        //instantiate the looper, and handle results from the looper to the main Looper
        looperDemoThread = new LooperDemoThread(new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //get info back out of message received
                Bundle bundle = msg.getData();
                Log.d("TAG", bundle.getString("key"));
            }
        });
        looperDemoThread.start();
        looperDemoThread.workerThreadHandler.sendMessage(new Message());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final MessageEvent msgEvent){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, msgEvent.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }, 3000);

    }

    @Override
    public void onResult(String string) {
        Log.d("TAG", string);
        tvRunnableDisplay.setText(string);
    }
}
