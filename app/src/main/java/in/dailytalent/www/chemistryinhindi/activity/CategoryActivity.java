package in.dailytalent.www.chemistryinhindi.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import in.dailytalent.www.chemistryinhindi.R;
import in.dailytalent.www.chemistryinhindi.adapter.CatCursorAdapterNew;
import in.dailytalent.www.chemistryinhindi.dbhelper.ExternalDbOpenHelper;

public class CategoryActivity extends Activity {
    private static final String DB_NAME = MyPersonalData.dbname();
    private SQLiteDatabase database;
    String fr;
    private AdView mAdView;
    int subcatnumber;
    String title;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        mAdView = new AdView(this);
        mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
        mAdView.setAdSize(AdSize.BANNER);
        ((LinearLayout) findViewById(R.id.lone)).addView(this.mAdView, new LayoutParams(-1, -2));
        mAdView.loadAd(new Builder().build());
        database = new ExternalDbOpenHelper(this, DB_NAME).openDataBase();
        ListView listView = (ListView) findViewById(R.id.categorylist);
//rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});

        //  String rawquery="SELECT *,subcategory._id AS catid, subcategory.subcatname AS catnamea, COUNT(questions._id) AS qucount   FROM subcategory INNER JOIN  questions  ON(subcategory._id=questions.subcatid) group by subcategory._id";
       // String data=this.database.rawQuery("SELECT *,subcategory._id AS catid, subcategory.subcatname AS catnamea, COUNT(questions._id) AS qucount   FROM subcategory INNER JOIN  questions  ON(subcategory._id=questions.subcatid) group by subcategory._id",null);
        //Cursor cursor = database.rawQuery("select * from exectable", null);
        Cursor cursor=database.rawQuery("SELECT *,subcategory._id as catid, subcategory.subcatname as catnamea, COUNT(questions._id) as qucount FROM subcategory INNER JOIN  questions  ON(subcategory._id=questions.subcatid) group by subcategory._id",null);
        if(cursor.moveToFirst()){

           //Log.d("Cursor:"+cursor.getCount());

           cursor.getCount();
        }


        /*String sql = String.format("select col1 from table where col2=%s and col3=%s","value for col2", "value for col3");
Cursor cc = db.rawQuery(sql, null);*/

        /*String sql=String.format("SELECT *,subcategory._id AS catid, \" +\n" +
                "                \"subcategory.subcatname AS catnamea, COUNT(questions._id) AS qucount   \" +\n" +
                "                \"FROM subcategory INNER JOIN  questions  ON(subcategory._id=questions.subcatid)\" +\n" +
                "                \" group by subcategory._id");*/
        /*String sql=String.format("select * from subcategory ");
        Cursor cc = database.rawQuery(sql, null)*/;
     //   Log.e("Test Database","Database Data:"+cursor.getString(2));

       // listView.setAdapter(new CatCursorAdapterNew(this, R.layout.onecat, this.database.rawQuery(cursor, null), 0));
           // listView.setAdapter(new CatCursorAdapterNew(this, R.layout.onecat, this.database.rawQuery(cursor, null), 0));
            listView.setAdapter(new CatCursorAdapterNew(this,R.layout.onecat,cursor, 0));


        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent in = new Intent(CategoryActivity.this, QuestionlistActivity.class);
                Cursor catidfinder = CategoryActivity.this.database.rawQuery("select subcategory._id  from subcategory  LIMIT 1 OFFSET " + position, null);
                catidfinder.moveToFirst();
                CategoryActivity.this.subcatnumber = catidfinder.getInt(0);
                catidfinder.close();
                in.putExtra("subcatnumber", CategoryActivity.this.subcatnumber);
                Log.i(getClass().toString(), "Trying to add intent...............");
                CategoryActivity.this.startActivity(in);
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

    public void gohome(View v) {
        Intent intent = new Intent(this, IndexActivity.class);
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
