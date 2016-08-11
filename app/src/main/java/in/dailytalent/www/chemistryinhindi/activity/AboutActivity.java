package in.dailytalent.www.chemistryinhindi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import in.dailytalent.www.chemistryinhindi.R;


public class AboutActivity extends Activity {
    private AdView mAdView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        this.mAdView = new AdView(this);
        this.mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
        this.mAdView.setAdSize(AdSize.BANNER);
        ((LinearLayout) findViewById(R.id.lone)).addView(this.mAdView, new LayoutParams(-1, -2));
        this.mAdView.loadAd(new Builder().build());
    }

    public void gohome(View v) {
        Intent intent = new Intent(this, IndexActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
