package in.dailytalent.www.chemistryinhindi.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import in.dailytalent.www.chemistryinhindi.R;


public class IndexActivity extends Activity {
    private static final long delay = 2000;
    private AdView mAdView;
    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String TAG = "MyFirebaseMsgService";
    private Handler mExitHandler = new Handler();
    private Runnable mExitRunnable = new Runnable() {
        public void run() {
            IndexActivity.this.mRecentlyBackPressed = false;
        }
    };
    InterstitialAd mInterstitialAd;
    private boolean mRecentlyBackPressed = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homa);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAdView = new AdView(this);
        mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
        mAdView.setAdSize(AdSize.BANNER);
        ((LinearLayout) findViewById(R.id.lone)).addView(this.mAdView, new LayoutParams(-1, -2));
        mAdView.loadAd(new Builder().build());
        NativeExpressAdView adView = (NativeExpressAdView)findViewById(R.id.adView);
        AdRequest request = new Builder() .addTestDevice("AdRequest.DEVICE_ID_EMULATOR") .build(); adView.loadAd(request);
        String IntAdId = getResources().getString(R.string.int_ad_unit_id);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(IntAdId);
        requestNewInterstitial();
        this.mInterstitialAd.setAdListener(new AdListener() {
            public void onAdClosed() {
                IndexActivity.this.requestNewInterstitial();
                IndexActivity.this.startActivity(new Intent(IndexActivity.this, CategoryActivity.class));
            }
        });
    }

    private void requestNewInterstitial() {
        this.mInterstitialAd.loadAd(new Builder().addTestDevice("AdRequest.DEVICE_ID_EMULATOR").build());
    }

    public void gohome() {
        startActivity(new Intent(this, IndexActivity.class));
    }

    public void goabout() {
        startActivity(new Intent(this, AboutActivity.class));
    }

    public void goabouta(View v) {
        startActivity(new Intent(this, AboutActivity.class));
    }

    public void gousers (View v) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.addFlags(524288);
        intent.putExtra("android.intent.extra.SUBJECT", "Chemistry in hindi App");
        intent.putExtra("android.intent.extra.TEXT", "Very useful app for Student. You should try it \n https://play.google.com/store/apps/developer?id=SHANKARRAOPURA");
        startActivity(Intent.createChooser(intent, "How do you want to share?"));
    }

    public void gocat(View v) {
        if (this.mInterstitialAd.isLoaded()) {
            this.mInterstitialAd.show();
        } else {
            startActivity(new Intent(this, CategoryActivity.class));
        }
    }

    public void goapps(View v) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("market://search?q=pub:SHANKARRAOPURA"));
        startActivity(intent);
    }

    public void goabout (View v) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://www.facebook.com/kushi.yadav.9619"));
        startActivity(intent);
    }

    public void gouserscore (View v) {
        Intent i = new Intent("android.intent.action.SEND");
        i.setType("message/rfc822");
        i.putExtra("android.intent.extra.EMAIL", new String[]{"SHANKARRAOPURA2016@GMAIL.COM"});
        i.putExtra("android.intent.extra.SUBJECT", "Feedback from Chemistry in hindiApp");
        i.putExtra("android.intent.extra.TEXT", "Please write your feedback here.......");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendmaila() {
        Intent i = new Intent("android.intent.action.SEND");
        i.setType("message/rfc822");
        i.putExtra("android.intent.extra.EMAIL", new String[]{"SHANKARRAOPURA2016@GMAIL.COM"});
        i.putExtra("android.intent.extra.SUBJECT", "Feedback from Chemistry in hindi App");
        i.putExtra("android.intent.extra.TEXT", "Please write your feedback here.......");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }


    public void onBackPressed() {
        if (this.mRecentlyBackPressed) {
            this.mExitHandler.removeCallbacks(this.mExitRunnable);
            this.mExitHandler = null;
            super.onBackPressed();
            return;
        }
        this.mRecentlyBackPressed = true;
        Toast.makeText(this, "Press again to exit !", Toast.LENGTH_SHORT).show();
        this.mExitHandler.postDelayed(this.mExitRunnable, delay);
    }

}
