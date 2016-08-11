package in.dailytalent.www.chemistryinhindi.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import in.dailytalent.www.chemistryinhindi.R;


public class SplashScreen extends Activity {
    private static final long DELAY = 2000;
    private static final int MSG_CONTINUE = 1234;
    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SplashScreen.MSG_CONTINUE /*1234*/:
                    SplashScreen.this._continue();
                    return;
                default:
                    return;
            }
        }
    };

    protected void onCreate(Bundle args) {
        super.onCreate(args);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.splashscreen);
        MediaPlayer.create(this, R.raw.startvoice).start();
        ((ImageView) findViewById(R.id.imageView1)).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animzoom));
        this.mHandler.sendEmptyMessageDelayed(MSG_CONTINUE, DELAY);
    }

    protected void onDestroy() {
        this.mHandler.removeMessages(MSG_CONTINUE);
        super.onDestroy();
    }

    private void _continue() {
        startActivity(new Intent(this, IndexActivity.class));
        finish();
    }
}
