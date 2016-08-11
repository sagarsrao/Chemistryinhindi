package in.dailytalent.www.chemistryinhindi.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import in.dailytalent.www.chemistryinhindi.R;
import in.dailytalent.www.chemistryinhindi.adapter.QuCursorAdapterNew;
import in.dailytalent.www.chemistryinhindi.dbhelper.ExternalDbOpenHelper;


public class QuestionlistActivity extends Activity {
    private static final String DB_NAME = MyPersonalData.dbname();
    private SQLiteDatabase database;
    String fr;
    private AdView mAdView;
    int maincatnumber;
    int subcatnumber;
    String title;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        this.mAdView = new AdView(this);
        this.mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
        this.mAdView.setAdSize(AdSize.BANNER);
        ((LinearLayout) findViewById(R.id.lone)).addView(this.mAdView, new LayoutParams(-1, -2));
        this.mAdView.loadAd(new Builder().build());
        this.database = new ExternalDbOpenHelper(this, DB_NAME).openDataBase();
        Bundle extras = getIntent().getExtras();
        this.maincatnumber = extras.getInt("maincatnumber");
        this.subcatnumber = extras.getInt("subcatnumber");
        Cursor chapCursor = this.database.rawQuery("SELECT *, subcategory.subcatname AS catname FROM  subcategory  WHERE subcategory._id=" + this.subcatnumber + " LIMIT 1", null);
        chapCursor.moveToFirst();
        String catname = chapCursor.getString(1);
        chapCursor.close();
        ((Button) findViewById(R.id.buttonhome)).setText(catname);
        ListView listView = (ListView) findViewById(R.id.categorylist);
        listView.setAdapter(new QuCursorAdapterNew(this, R.layout.onequ, this.database.rawQuery("SELECT  *, questions._id AS quid ,questions.qu AS quname FROM questions INNER JOIN subcategory ON (subcategory._id=questions.subcatid) WHERE subcategory._id= " + this.subcatnumber, null), 0));
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent in = new Intent(QuestionlistActivity.this, QuestionAnswerActivity.class);
                int pa = position;
                Cursor catidfinder = QuestionlistActivity.this.database.rawQuery("select questions._id  from questions WHERE questions.subcatid=" + QuestionlistActivity.this.subcatnumber + "  LIMIT 1 OFFSET " + pa, null);
                catidfinder.moveToFirst();
                int qunumber = catidfinder.getInt(0);
                catidfinder.close();
                in.putExtra("subcatnumber", QuestionlistActivity.this.subcatnumber);
                in.putExtra("qunumber", qunumber);
                in.putExtra("pa", pa);
                Log.i(getClass().toString(), "Trying to add intent...............");
                QuestionlistActivity.this.startActivity(in);
            }
        });
    }

    public void sharenow(View v) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
        intent.putExtra("android.intent.extra.SUBJECT", "Chemistry in hindi App");
        intent.putExtra("android.intent.extra.TEXT", this.title + "\n" + this.fr + "\n Find at - \n https://play.google.com/store/apps/details?id=in.dailytalent.www.chemistryinhindi");
        startActivity(Intent.createChooser(intent, "How do you want to share?"));
    }

    @SuppressLint({"InlinedApi"})
    public void gohome(View v) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    protected void onPause() {
        this.mAdView.pause();
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        this.mAdView.resume();
    }

    protected void onDestroy() {
        this.mAdView.destroy();
        super.onDestroy();
    }
}
